package com.beginvegan.repository;

import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
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
@Repository("ReviewRepository")
public class ReviewRepositoryMariaDB implements ReviewRepository {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<ReviewDTO> selectAllReviewByMemberEmail(String userEmail) throws FindException {
        log.info("selectAllReviewByMemberEmail 시작 - userEmail : " + userEmail);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            List<ReviewDTO> reviewList = sqlSession.selectList("com.beginvegan.mybatis.ReviewMapper.selectAllReviewByMemberEmail", userEmail);
            return reviewList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException("e.getMessage");

        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
            log.info("selectAllReviewByMemberEmail 종료");
        }
    }

    @Override
    public List<ReviewDTO> selectAllReviewByRestaurantId(int id) throws FindException {
        log.info("selectAllReviewByRestaurantId 시작 - restaurantId : " + id);

        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<ReviewDTO> reviewList = session.selectList("com.beginvegan.mybatis.ReviewMapper.selectAllReviewByRestaurantId", id);
            return reviewList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException("e.getMessage");

        } finally {
            if (session != null) {
                session.close();
            }
            log.info("selectAllReviewByRestaurantId 종료");
        }
    }

    @Override
    public void insertReview(ReviewDTO reviewInfo) throws AddException {
        log.info("insertReview 시작 - reviewInfo : " + reviewInfo.toString());

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.beginvegan.mybatis.ReviewMapper.insertReview", reviewInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddException("e.getMessage");
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("insertReview 종료");
        }
    }

    @Override
    public void deleteReview(int reviewNo) throws RemoveException {
        log.info("deleteReview 시작 - reviewNo : " + reviewNo);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.delete("com.beginvegan.mybatis.ReviewMapper.deleteReview", reviewNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoveException("e.getMessage");
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("deleteReview 종료");
        }
    }

    @Override
    public void updateReview(int reviewNo, ReviewDTO reviewInfo) throws ModifyException {
        log.info("updateReview 시작 - reviewNo : " + reviewNo + "/" + reviewInfo.toString());

        Map<String, Object> map = new HashMap<>();
        map.put("reviewNo", reviewNo);
        map.put("reviewInfo", reviewInfo);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.update("com.beginvegan.mybatis.ReviewMapper.updateReview", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModifyException("e.getMessage");
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("updateReview 종료");
        }
    }
}
