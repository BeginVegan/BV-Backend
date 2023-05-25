package com.beginvegan.repository;

import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.FindException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("RestaurantRepository")
public class RestaurantRepositoryMariaDB implements RestaurantRepository{

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<RestaurantDTO> selectAllRestaurant() throws FindException {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectAllRestaurant");
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace(); //나중에 하기
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
}
