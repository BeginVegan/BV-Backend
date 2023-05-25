package com.beginvegan.repository;

import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.FindException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("ReviewRepository")
public class ReviewRepositoryMariaDB implements ReviewRepository {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<ReviewDTO> selectAllReviewByMemberEmail(String userEmail) throws FindException {
        SqlSession session = null;

        try {
            session = sqlSessionFactory.openSession();
            List<ReviewDTO> reviewList = session.selectList("com.beginvegan.mybatis.ReviewMapper.selectAllReviewByMemberEmail", userEmail);
            return reviewList;
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
