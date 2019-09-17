package com.fulln.me.api.common.Mongo;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * @author fulln
 * @description  mongodb注解的condition
 * @date  Created in  23:05  2019-09-17.
 */
public class MongoCondition implements Condition
{
	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		String property = conditionContext.getEnvironment().getProperty("spring.data.mongodb.uri");
		if(StringUtils.isEmpty(property)){
			return false;
		}else {
			return true;
		}
	}
}
