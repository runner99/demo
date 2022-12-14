//package com.runner.locks.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//@Component
//public class RedisLock {
//    @Autowired
//    StringRedisTemplate redisTemplate;
//
//    public boolean lock(String key, String value,int expireTime){
//        return set(key, value, "NX", "EX", expireTime);
//    }
//
//    public void unlock(String key, String value){
//        try {
//            String currentValue = (String) redisTemplate.opsForValue().get(key);
//            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
//                redisTemplate.opsForValue().getOperations().delete(key);
//
//            }
//        } catch (Exception e) {
//
//        }
//    }
//
//    public boolean set(final String key, final String value, final String nxxx, final String expx, final long time) {
//
//        Object result = redisTemplate.execute(new RedisCallback<Object>() {
//            @Override
//            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                redisConnection.execute("set",
//                        new byte[][] { serializer.serialize(key), serializer.serialize(value),
//                                serializer.serialize(nxxx), serializer.serialize(expx),
//                                SafeEncoder.encode(String.valueOf(time)) });
//                return true;
//            }
//
//        });
//
//        return (boolean) result;
//    }
//}
