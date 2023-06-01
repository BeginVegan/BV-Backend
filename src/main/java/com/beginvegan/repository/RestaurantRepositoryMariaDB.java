package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.*;
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
            RestaurantDTO restaurantInfo = session.selectOne("com.beginvegan.mybatis.RestaurantMapper.selectRestaurantByRestaurantNo", restaurantNo);
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
    public List<MenuDTO> selectAllMenuByRestaurantNo(int restaurantNo) throws FindException {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<MenuDTO> menuList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectAllMenuByRestaurantNo", restaurantNo);
            return menuList;
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

    @Override
    public void deleteRestaurantMenu(int restaurantNo) throws RemoveException {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            session.delete("com.beginvegan.mybatis.RestaurantMapper.deleteRestaurantMenu", restaurantNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoveException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void updateRestaurant(RestaurantDTO restaurantInfo) throws ModifyException {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            session.update("com.beginvegan.mybatis.RestaurantMapper.updateRestaurant", restaurantInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModifyException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<RestaurantDTO> selectBestRestaurant() throws FindException {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectBestRestaurant");
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
    public void createBestStarView() throws CreateException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.selectOne("com.beginvegan.mybatis.RestaurantMapper.createBestStarView");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void createBestReviewView() throws CreateException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.selectOne("com.beginvegan.mybatis.RestaurantMapper.createBestReviewView");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void createBestReservationView() throws CreateException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.selectOne("com.beginvegan.mybatis.RestaurantMapper.createBestReservationView");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //아래 메소드는 단위 테스트를 위한 CRUD 메소드입니다.
    public int selectMaxRestaurantNoTest() throws FindException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            return sqlSession.selectOne("com.beginvegan.mybatis.RestaurantMapper.selectMaxRestaurantNoTest");
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
