package com.fulln.me.api.common.utils;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.GlobalEnums;
import lombok.extern.slf4j.Slf4j;


/**
 * @author fulln
 * @description  请求的封装
 * @date  Created in  14:48  2020-04-29.
 */
@Slf4j
public class reqWrapperUtils {

	public static GlobalResult commonExecuteService(Object request,ServiceWrapper wrapper){

		StringBuilder strBuff = new StringBuilder();

		long startTime = System.currentTimeMillis();

		StackTraceElement ste = new Exception().getStackTrace()[1];

		//服务方法名
		String methodName = ste.getMethodName();
		//服务类名
		String serviceName =ste.getClassName().substring(ste.getClassName().lastIndexOf(".")+1);

		String facadeName = serviceName.concat(".").concat(methodName);

		String reqStr =  GsonUtil.gsonString(request);
		String reqLogStr = reqStr + " == " + wrapper.getTraceId();
		strBuff.append("调用服务==").append(facadeName).append(";请求数据==").append(reqLogStr).append(";");
//		//数据检查
//		String checkResult = request.check();
		Exception ex = null;
//		String resultCode = null;
//		if (StringUtils.isEmpty(checkResult)) {
		GlobalResult response;
			try{
				//调用服务
				response = wrapper.invokeService();
			}catch(Exception e){//服务异常
				ex = e;
				response = GlobalEnums.SYS_ERROR.results();
			}
//		}else {//参数缺失
//			resultCode = BaseApiCode.OPERATE_PARAM_INVALID;
//			strBuff.append("检测到数据错误描述==").append(checkResult).append(";");
//		}



		String respStr = GsonUtil.gsonString(response);
		strBuff.append(";共耗时==").append(System.currentTimeMillis()-startTime)
				.append(";response==").append(respStr).append(";");
		printInvokeLog(ex, response.getCode(), strBuff);

		return response;
	}

	/**
	 *打印日志设置
	 */
	private static void printInvokeLog(Exception ex, int resultCode, StringBuilder strBuff){
		if(resultCode > 0){
			//调用成功
			log.info(strBuff.toString());
		}else{
			//服务异常
			if(ex != null){
				strBuff.append(";error_msg==").append(ex.getMessage());
				log.error(strBuff.toString(), ex);
			}else{
				log.info(strBuff.toString());
			}
		}
	}

}
