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
        log.info("selectAllRestaurant 시작");
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
            log.info("selectAllRestaurant 종료");
        }
    }

    @Override
    public RestaurantDTO selectRestaurantByRestaurantNo(int restaurantNo) throws FindException {
        log.info("selectRestaurantByRestaurantNo 시작 - restaurantNo : " + restaurantNo);
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
            log.info("selectRestaurantByRestaurantNo 종료");
        }
    }

    @Override
    public int insertRestaurant(RestaurantDTO restaurantInfo) throws AddException {
        log.info("insertRestaurant 시작 - restaurantInfo : " + restaurantInfo.toString());
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
            log.info("insertRestaurant 종료");
        }
    }

    @Override
    public void insertRestaurantMenu(int restaurantNo, List<MenuDTO> menuList) throws AddException {
        log.info("insertRestaurantMenu 시작 - restaurantNo : " + restaurantNo + ", menuList : " + menuList.toString());
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
            log.info("insertRestaurantMenu 종료");
        }
    }

    @Override
    public List<MenuDTO> selectAllMenuByRestaurantNo(int restaurantNo) throws FindException {
        log.info("selectAllMenuByRestaurantNo 시작 - restaurantNo : " + restaurantNo);
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
            log.info("selectAllMenuByRestaurantNo 종료");
        }
    }

    @Override
    public void deleteRestaurant(int restaurantNo) throws RemoveException {
        log.info("deleteRestaurant 시작 - restaurantNo : " + restaurantNo);
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
            log.info("deleteRestaurant 종료");
        }
    }

    @Override
    public void deleteRestaurantMenu(int restaurantNo) throws RemoveException {
        log.info("deleteRestaurantMenu 시작 - restaurantNo : " + restaurantNo);
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
            log.info("deleteRestaurantMenu 종료");
        }
    }

    @Override
    public RestaurantDTO selectRestaurantMenuByRestaurantNo(int restaurantNo) throws FindException {
        log.info("selectRestaurantMenuByRestaurantNo 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            RestaurantDTO restaurantInfo = session.selectOne("com.beginvegan.mybatis.RestaurantMapper.selectRestaurantMenuByRestaurantNo", restaurantNo);
            return restaurantInfo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectRestaurantMenuByRestaurantNo 종료");
        }
    }

    @Override
    public List<RestaurantDTO> selectAllRestaurantByKeyword(String keyword) throws FindException {
        log.info("selectAllRestaurantByKeyword 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectAllRestaurantByKeyword", keyword);
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectAllRestaurantByKeyword 종료");
        }
    }

    @Override
    public List<RestaurantDTO> selectAllRestaurantByKeyword2(String keyword) throws FindException {
        log.info("selectAllRestaurantByKeyword2 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectAllRestaurantByKeyword2", keyword);
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectAllRestaurantByKeyword2 종료");
        }
    }

    @Override
    public List<RestaurantDTO> selectAllRestaurantByKeyword3(Map searchMap) throws FindException {
        log.info("selectAllRestaurantByKeyword3 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectAllRestaurantByKeyword3", searchMap);
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectAllRestaurantByKeyword3 종료");
        }
    }

    @Override
    public void updateRestaurant(RestaurantDTO restaurantInfo) throws ModifyException {
        log.info("updateRestaurant 시작 - RestaurantDTO : " + restaurantInfo.toString());
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
            log.info("updateRestaurant 종료");
        }
    }

    @Override
    public void createBestStarView() throws CreateException {
        log.info("createBestStarView 시작");

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
            log.info("createBestStarView 종료");
        }
    }

    @Override
    public void createBestReviewView() throws CreateException {
        log.info("createBestReviewView 시작");
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
            log.info("createBestReviewView 종료");
        }
    }

    @Override
    public void createBestReservationView() throws CreateException {
        log.info("createBestReservationView 시작");
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
            log.info("createBestReservationView 종료");
        }
    }

    @Override
    public List<RestaurantDTO> selectBestStarRestaurant() throws FindException {
        log.info("selectBestStarRestaurant 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectBestStarRestaurant");
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectBestStarRestaurant 종료");
        }
    }

    @Override
    public List<RestaurantDTO> selectBestReviewRestaurant() throws FindException {
        log.info("selectBestReviewRestaurant 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectBestReviewRestaurant");
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectBestReviewRestaurant 종료");
        }
    }

    @Override
    public List<RestaurantDTO> selectBestReservationRestaurant() throws FindException {
        log.info("selectBestReservationRestaurant 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectBestReservationRestaurant");
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectBestReservationRestaurant 종료");
        }
    }

    //아래 메소드는 단위 테스트를 위한 CRUD 메소드입니다.
    public List<RestaurantDTO> selectBestStarRestaurantTest() throws FindException {
        log.info("selectBestStarRestaurantTest 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectBestStarRestaurantTest");
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectBestStarRestaurantTest 종료");
        }
    }

    public List<RestaurantDTO> selectBestReviewRestaurantTest() throws FindException {
        log.info("selectBestReviewRestaurantTest 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectBestReviewRestaurantTest");
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectBestReviewRestaurantTest 종료");
        }
    }

    public List<RestaurantDTO> selectBestReservationRestaurantTest() throws FindException {
        log.info("selectBestReservationRestaurantTest 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectBestReservationRestaurantTest");
            return restaurantList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectBestReservationRestaurantTest 종료");
        }
    }

    public Integer selectNextMenuNo() {
        log.info("selectNextMenuNo 시작");

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            return sqlSession.selectOne("com.beginvegan.mybatis.RestaurantMapper.selectNextMenuNo");
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectNextMenuNo 종료");
        }
    }
}
