package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.*;
import lombok.Generated;
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
            if(restaurantList == null || restaurantList.isEmpty()) throw new FindException("식당 정보가 없습니다.");
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
            if(restaurantInfo == null) throw new FindException("해당 식당 정보가 없습니다.");
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
            if(menuList == null || menuList.isEmpty()) throw new FindException("해당 식당의 메뉴 정보가 없습니다.");
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
            if(restaurantInfo == null) throw new FindException("해당 식당과 식당의 메뉴 정보가 없습니다.");
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
    public List<RestaurantDTO> selectAllRestaurantByKeyword(Map searchMap) throws FindException {
        log.info("selectAllRestaurantByKeyword 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<RestaurantDTO> restaurantList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectAllRestaurantByKeyword", searchMap);
            if(restaurantList == null || restaurantList.isEmpty()) throw new FindException("해당 키워드 검색 결과가 없습니다.");
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
    public List<ReservationDTO> selectAllReservationByRestaurantNo(int restaurantNo) throws FindException {
        log.info("selectAllReservationByRestaurantNo 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<ReservationDTO> reservationList = session.selectList("com.beginvegan.mybatis.RestaurantMapper.selectAllReservationByRestaurantNo", restaurantNo);
            if(reservationList == null || reservationList.isEmpty()) throw new FindException("해당 식당으로 예약된 정보가 없습니다.");
            return reservationList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectAllReservationByRestaurantNo 종료");
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
            if(restaurantList == null || restaurantList.isEmpty()) throw new FindException("BestStar 식당 정보가 없습니다.");
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
            if(restaurantList == null || restaurantList.isEmpty()) throw new FindException("BestReview 식당 정보가 없습니다.");
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
            if(restaurantList == null || restaurantList.isEmpty()) throw new FindException("BestReservation 식당 정보가 없습니다.");
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

    @Override
    @Generated
    public void updateRestaurantStar() throws ModifyException {
        log.info("updateRestaurantStar 시작");
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            session.update("com.beginvegan.mybatis.RestaurantMapper.updateRestaurantStar");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModifyException(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
            log.info("updateRestaurantStar 종료");
        }
    }

    @Override
    public Integer selectNextRestaurantNo() {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            return sqlSession.selectOne("com.beginvegan.mybatis.RestaurantMapper.selectNextRestaurantNo");
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
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
