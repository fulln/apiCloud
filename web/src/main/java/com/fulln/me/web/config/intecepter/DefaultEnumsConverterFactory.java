package com.fulln.me.web.config.intecepter;


import com.fulln.me.api.common.enums.config.CloumsEnumsConfig;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author fulln
 * @program api
 * @description 前端String转后端枚举
 * @Date 2018-11-24 14:49
 * @Version 0.0.1
 **/
@Component
public class DefaultEnumsConverterFactory implements ConverterFactory<String, CloumsEnumsConfig> {

    private static final Map<Class, Converter> CONVERTER_MAP = new WeakHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <E extends CloumsEnumsConfig> Converter<String, E> getConverter(Class<E> targetType) {
        Converter result = CONVERTER_MAP.get(targetType);
        if (result == null) {
            result = new IntegerStrToEnum<>(targetType);
            CONVERTER_MAP.put(targetType, result);
        }
        return result;
    }


    class IntegerStrToEnum<E extends CloumsEnumsConfig> implements Converter<String, E> {

        private Class<E> type;
        private E[] enums;

        IntegerStrToEnum(Class<E> type) {

            if (type == null) {
                throw new IllegalArgumentException("Type argument cannot be null");
            } else {
                this.type = type;
                this.enums = type.getEnumConstants();
                if (this.enums == null) {
                    throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
                }
            }

        }



        @Override
        public E convert(String s) {
            if ("".equals(s)) {
                return null;
            }
            try {
                return this.enums[Integer.parseInt(s)];
            } catch (Exception var5) {
                throw new IllegalArgumentException("Cannot convert " + s + " to " + this.type.getSimpleName() + " by ordinal value.", var5);
            }
        }
    }

}
