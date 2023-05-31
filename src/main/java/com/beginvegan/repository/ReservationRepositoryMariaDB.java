package com.beginvegan.repository;

import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReservationMenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository("ReservationRepository")
public class ReservationRepositoryMariaDB implements ReservationRepository{

    private final SqlSessionFactory sqlSessionFactory;

    public ReservationRepositoryMariaDB(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    };

    @Override
    public ReservationDTO insertReservation(ReservationDTO reservationInfo) throws AddException {
        log.info("insertReservation 시작 - reservationInfo" + reservationInfo.toString());

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.beginvegan.mybatis.ReservationMapper.insertReservation", reservationInfo);
            reservationInfo.setReservationNo(sqlSession.selectOne("com.beginvegan.mybatis.ReservationMapper.selectNextReservationNo"));
            return reservationInfo;
        } catch (Exception e) {
            throw new AddException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("insertReservation 종료");
        }
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationInfo) throws ModifyException {
        log.info("updateReservation 시작 - reservationInfo" + reservationInfo.toString());

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.update("com.beginvegan.mybatis.ReservationMapper.updateReservation", reservationInfo);
            return reservationInfo;
        } catch (Exception e) {
            throw new ModifyException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("updateReservation 종료");
        }
    }

    @Override
    public List<ReservationDTO> selectAllReservation() throws FindException {
        log.info("selectAllReservation 시작");

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            List<ReservationDTO> reservationList = sqlSession.selectList("com.beginvegan.mybatis.ReservationMapper.selectAllReservation");
            return reservationList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectAllReservation 종료");
        }
    }

    @Override
    public List<ReservationDTO> selectALLReservationByMemberEmail(String memberEmail) throws FindException {
        log.info("selectALLReservationByMemberEmail 시작 - memberEmail: " + memberEmail);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            List<ReservationDTO> reservationList = sqlSession.selectList("com.beginvegan.mybatis.ReservationMapper.selectALLReservationByMemberEmail", memberEmail);
            return reservationList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectALLReservationByMemberEmail 종료");
        }
    }

    @Override
    public ReservationDTO selectReservationByReservationNo(Integer reservationNo) throws FindException {
        log.info("selectReservationByReservationNo 시작 - reservationNo: " + reservationNo);

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            ReservationDTO reservation = sqlSession.selectOne("com.beginvegan.mybatis.ReservationMapper.selectReservationByReservationNo", reservationNo);
            return reservation;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectReservationByReservationNo 종료");
        }
    }

    @Override
    public Integer deleteReservation(Integer ReservationNo) throws RemoveException {
        log.info("deleteReservation 시작");

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.delete("com.beginvegan.mybatis.ReservationMapper.deleteReservation", ReservationNo);
            return ReservationNo;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoveException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("deleteReservation 종료");
        }
    }

    @Override
    public Integer selectNextReservationNo() {
        log.info("selectNextReservationNo 시작");

        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            return sqlSession.selectOne("com.beginvegan.mybatis.ReservationMapper.selectNextReservationNo");
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }

            log.info("selectNextReservationNo 종료");
        }
    }

}
