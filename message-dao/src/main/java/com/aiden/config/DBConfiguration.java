package com.aiden.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Aiden
 * @version 1.0
 * @description
 * @date 2021-4-24 11:47:39
 */

@Configuration
@MapperScan(value = {"com.aiden.dao.mapper"})
@PropertySource(value = {"classpath:db-${spring.profiles.active}.properties"})
public class DBConfiguration {
}
