package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public RestaurantDTO selectRestaurantByRestaurantNo(int restaurantNo) throws FindException {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            RestaurantDTO restaurantInfo = session.selectOne("com.beginvegan.mybatis.RestaurantMapper.selectRestaurantByRestaurantNo");
            return restaurantInfo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public int insertRestaurant(RestaurantDTO restaurantInfo) throws AddException {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            session.insert("com.beginvegan.mybatis.RestaurantMapper.insertRestaurant", restaurantInfo);
            return restaurantInfo.getRestaurantNo();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void insertRestaurantMenu(int restaurantNo, List<MenuDTO> menuList) throws AddException {
        SqlSession session = null;

        Map<String, Object> map = new HashMap<>();
        map.put("restaurantNo", restaurantNo);
        map.put("menuList", menuList);

        try {
            session = sqlSessionFactory.openSession();
            session.insert("com.beginvegan.mybatis.RestaurantMapper.insertRestaurantMenu", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteRestaurant(int restaurantNo) throws RemoveException {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            session.delete("com.beginvegan.mybatis.RestaurantMapper.deleteRestaurant", restaurantNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoveException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
