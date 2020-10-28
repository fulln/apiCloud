package me.fulln.base.common.enums.config;


import com.fasterxml.jackson.annotation.JsonValue;
import me.fulln.base.common.exception.ServiceException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Function;

/**
 * @AUthor: fulln
 * @Description: 枚举配置类  对应global枚举
 * @Date : Created in  18:46  2018/8/4.
 */
public interface EnumsConfig {

	String DEFAULT_CODE_NAME = "code";
	String DEFAULT_DATA_NAME = "data";
	String DEFAULT_MESSAGE_NAME = "message";

	/**
	 * 根据id查找对应的枚举
	 * handle 类型转换器
	 *
	 * @param enumClass
	 * @param code      数据库查出的类型
	 * @param <T>
	 * @return T
	 */
	static <T extends Enum<T>> T findEnumByCode(Class<T> enumClass, Integer code) {

		if (code == null) {
			throw new IllegalArgumentException("DisplayedEnum value should not be null");
		}
		if (!enumClass.isAssignableFrom(EnumsConfig.class)) {
			throw new IllegalArgumentException("illegal DisplayedEnum type");
		}

		T[] enums = enumClass.getEnumConstants();

		for (T t : enums) {
			EnumsConfig enumsConfig = (EnumsConfig) t;
			if (enumsConfig.getCode().equals(code)) {
				return (T) enumsConfig;
			}
		}
		throw new IllegalArgumentException("cannot parse integer: " + code + " to " + enumClass.getName());
	}

	/**
	 * 获取code
	 * json序列化 这个枚举的值默认填成code
	 * 方便直接用枚举做对应的处理
	 * @return java.lang.Integer
	 * @author fulln
	 * @date 00:52 2019-08-22
	 **/
	@JsonValue
	default Integer getCode() {
        return getField(DEFAULT_CODE_NAME, (code) -> Integer.valueOf(code.toString()));
	}

	default String getData() {
		return getField(DEFAULT_DATA_NAME,Object::toString);
	}

	default String getMessage() {
		return getField(DEFAULT_MESSAGE_NAME, Object::toString);
	}

	/**
	 * 获取指定字段
	 *
	 * @param defaultMessageName
	 * @return
	 */
	default <R> R getField(String defaultMessageName, Function<Object, R> function) {

		Field field = ReflectionUtils.findField(this.getClass(), defaultMessageName);
		if (field == null) {
			return null;
		}

		try {
			field.setAccessible(true);
			return Optional.of(field.get(this)).map(function).get();
		} catch (IllegalAccessException e) {
			throw new ServiceException("【枚举字段获取异常】",e);
		}
	}


}
