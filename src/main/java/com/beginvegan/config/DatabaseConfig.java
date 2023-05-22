package com.beginvegan.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Value("${datasource.driver-class-name}") private String setDriverClassName;
    @Value("${datasource.url}") private String setJdbcUrl;
    @Value("${datasource.username}") private String setUsername;
    @Value("${datasource.password}") private String setPassword;

    @Bean
    HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(setDriverClassName);
        config.setJdbcUrl(setJdbcUrl);
        config.setUsername(setUsername);
        config.setPassword(setPassword);
        config.setMinimumIdle(3);
        return config;
    }

    @Bean
    DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        org.springframework.core.io.Resource resource = new ClassPathResource("mybatis-config.xml");
        sqlSessionFactory.setConfigLocation(resource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));

        return sqlSessionFactory.getObject();
    }

}

