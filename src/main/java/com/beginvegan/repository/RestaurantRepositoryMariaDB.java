package com.beginvegan.repository;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("RestaurantRepository")
public class RestaurantRepositoryMariaDB implements RestaurantRepository{

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
}
