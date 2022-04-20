package com.rfm.packagegeneration.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;

public class KryoRedisSeralizer implements RedisSerializer<Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(KryoRedisSeralizer.class);

	private static final int BUFFER_SIZE = 2048;
	private Pool<Kryo> kryoPool;

	public KryoRedisSeralizer(Pool<Kryo> kryoPool) {
		super();
		this.kryoPool = kryoPool;
	}

	@Override
	public byte[] serialize(Object t) throws SerializationException {
		LOGGER.info("Before: Kryo Pool Peek: {}, Free: {}", kryoPool.getPeak(), kryoPool.getFree());
		Kryo k = null;
		try {

			k = kryoPool.obtain();
			Output o = new Output(BUFFER_SIZE, -1);
			k.writeClassAndObject(o, t);
			byte[] bytes=o.toBytes();
			o.flush();o.close();
			return bytes;
		} finally {
			if (k != null) {
				kryoPool.free(k);kryoPool.clear();
			}
			LOGGER.info("After: Kryo Pool Peek: {}, Free: {}", kryoPool.getPeak(), kryoPool.getFree());
			
		}

	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null) {
			return null;
		}
		LOGGER.info("Before: Kryo Pool Peek: {}, Free: {}", kryoPool.getPeak(), kryoPool.getFree());
		Kryo k = null;

		try {
				k = kryoPool.obtain();

				Input i = new Input(bytes);
				Object obj=k.readClassAndObject(i);
				i.close();
				return obj;
			} finally {
				if (k != null) {
					kryoPool.free(k);kryoPool.clear();
				}
				LOGGER.info("After: Kryo Pool Peek: {}, Free: {}", kryoPool.getPeak(), kryoPool.getFree());
				
			}
	}

}
