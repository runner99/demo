package cn.mrworlds.lingjingshijie.messagecenter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.max-active}")
    private Integer maxActive;
    @Value("${spring.redis.maxIdle}")
    private Integer maxIdle;
    @Value("${spring.redis.maxWaitMillis}")
    private Long maxWait;
    @Value("${spring.redis.maxTotal}")
    private int maxTotal;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int maxWaitT;
    @Value("${spring.redis.softMinEvictableIdleTimeMillis}")
    private Long softMinEvictableIdleTimeMillis;
    @Bean
    public JedisPoolConfig jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxTotal (maxTotal);
        jedisPoolConfig.setMaxWaitMillis (maxWaitT);
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis (softMinEvictableIdleTimeMillis);
        return jedisPoolConfig;
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPool) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPool);

        connectionFactory.setHostName (hostName);
        connectionFactory.setPort (port);
        connectionFactory.setPassword (password);

        return connectionFactory;
    }


    @Bean
    public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object> ();
        template.setConnectionFactory(factory);

        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer ();

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer ();

        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
