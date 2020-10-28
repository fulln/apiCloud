package me.fulln.base.common.utils;

import me.fulln.base.common.entity.GlobalResult;

/**
 * @author fulln
 * @description
 * @date  Created in  15:15  2020-04-29.
 */
public interface ServiceWrapper {


	/**
	 * 调用服务
	 *
	 * @return
	 */
	GlobalResult invokeService();

	/**
	 * 是否打印debug日志
	 */
	default boolean isPrintDebugLog() {
		return false;
	}

	/**
	 * 是否监控失败请求
	 */
	default boolean isMonitorFail() {
		return true;
	}

	/**
	 * 是否监控成功请求
	 */
	default boolean isMonitorSuccess() {
		return false;
	}

	/**
	 *超时时间
	 */

	default int timeout() {
		return 1000;
	}

	/**
	 *获取请求traceId
	 */

	default String getTraceId() {
		return null;
	}

	/**
	 *是否记录请求日志
	 */
	default Boolean isRecordInvokeLog() {
		return false;
	}

}
