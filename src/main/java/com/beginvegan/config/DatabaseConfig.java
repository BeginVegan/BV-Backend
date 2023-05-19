package com.beginvegan.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://db-begin-vegan.cahc6jjdhfq8.ap-northeast-2.rds.amazonaws.com:3306/test_db");
        config.setUsername("root");
        config.setPassword("yh4LidyF!");
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
        return sqlSessionFactory.getObject();
    }

}

