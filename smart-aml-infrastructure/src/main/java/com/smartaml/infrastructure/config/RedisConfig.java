package com.smartaml.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import java.util.Set;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration cfg = new RedisStandaloneConfiguration();
        // Host and port will be picked up from spring properties
        return new LettuceConnectionFactory(cfg);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory lf) {
        RedisTemplate<String, String> tpl = new RedisTemplate<>();
        tpl.setConnectionFactory(lf);
        tpl.setKeySerializer(new StringRedisSerializer());
        tpl.setValueSerializer(new StringRedisSerializer());
        return tpl;
    }

    @Bean
    public RedisTemplate<String, Set<String>> permissionRedisTemplate(LettuceConnectionFactory lf, ObjectMapper mapper) {
        RedisTemplate<String, Set<String>> tpl = new RedisTemplate<>();
        tpl.setConnectionFactory(lf);
        tpl.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<Set<String>> ser = new Jackson2JsonRedisSerializer<>(Set.class);
        ser.setObjectMapper(mapper);
        tpl.setValueSerializer(ser);
        return tpl;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory lf) {
        return new StringRedisTemplate(lf);
    }
}
