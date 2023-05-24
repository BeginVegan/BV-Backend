package com.beginvegan.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Slf4j
@Repository("ReservationRepository")
public class ReservationRepositoryMariaDB implements ReservationRepository{

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

}
