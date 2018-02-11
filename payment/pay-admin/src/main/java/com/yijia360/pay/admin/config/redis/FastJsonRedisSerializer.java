package com.yijia360.pay.admin.config.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastJsonRedisSerializer implements RedisSerializer<Object> {

	@Override
	public byte[] serialize(Object source) throws SerializationException {
		if (source == null) {
			return null;
		}
		return JSON.toJSONBytes(source, SerializerFeature.WriteClassName);
	}

	@Override
	public Object deserialize(byte[] source) throws SerializationException {
		if (source == null || source.length == 0) {
			return null;
		}
		return JSON.parse(source);
	}
}
