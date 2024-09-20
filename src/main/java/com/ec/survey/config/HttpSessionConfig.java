package com.ec.survey.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableRedisHttpSession // Enable Redis for HTTP session storage
public class HttpSessionConfig {

    private static final Logger logger = LoggerFactory.getLogger(HttpSessionConfig.class);

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        int redisPort = 6379;
        String redisHost = "172.22.0.2";
        String redisPassword = ""; // Assuming password is managed elsewhere or not used

        logger.info("Using Redis for HTTP session management");
        logger.info("Connecting to Redis at host: {} and port: {}", redisHost, redisPort);

        try {
            InetAddress address = InetAddress.getByName(redisHost);
            logger.info("Resolved Redis hostname {} to IP: {}", redisHost, address.getHostAddress());
        } catch (UnknownHostException e) {
            logger.error("Failed to resolve Redis hostname: {}", redisHost, e);
        }

        LettuceConnectionFactory redisConfig = new LettuceConnectionFactory(redisHost, redisPort);
        if (!redisPassword.isEmpty()) {
            redisConfig.setPassword(redisPassword);
            logger.debug("Using Redis with password");
        } else {
            logger.debug("No Redis password configured");
        }

        return redisConfig;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }


}
