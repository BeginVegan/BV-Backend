package com.beginvegan.repository;

import com.beginvegan.dto.PaymentDTO;
import com.beginvegan.dto.ReservationMenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.siot.IamportRestClient.response.Payment;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
@Slf4j
@Repository("PaymentRepository")
public class PaymentRepositoryMariaDB implements PaymentRepository {
    private final SqlSessionFactory sqlSessionFactory;

    public PaymentRepositoryMariaDB(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public PaymentDTO selectPaymentByImpUid(String impUid) throws FindException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            PaymentDTO Payment = sqlSession.selectOne("com.beginvegan.mybatis.PaymentMapper.selectPaymentByImpUid", impUid);
            if(Payment == null) throw new FindException("결제 정보가 없습니다.");
            return Payment;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public PaymentDTO selectPaymentByReservationNo(int reservationNo) throws FindException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            PaymentDTO Payment = sqlSession.selectOne("com.beginvegan.mybatis.PaymentMapper.selectPaymentByReservationNo", reservationNo);
            if(Payment == null) throw new FindException("결제 정보가 없습니다.");
            return Payment;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public List<PaymentDTO> selectAllPayment() throws FindException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            List<PaymentDTO> PaymentList = sqlSession.selectList("com.beginvegan.mybatis.PaymentMapper.selectAllPayment");
            if(PaymentList == null || PaymentList.isEmpty()) throw new FindException("결제 정보가 없습니다.");
            return PaymentList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public List<PaymentDTO> selectALLPaymentByMemberEmail(String memberEmail) throws FindException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            List<PaymentDTO> PaymentList = sqlSession.selectList("com.beginvegan.mybatis.PaymentMapper.selectALLPaymentByMemberEmail", memberEmail);
            if(PaymentList == null || PaymentList.isEmpty()) throw new FindException("결제 정보가 없습니다.");
            return PaymentList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FindException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public PaymentDTO insertPayment(PaymentDTO paymentInfo) throws AddException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.insert("com.beginvegan.mybatis.PaymentMapper.insertPayment", paymentInfo);

            return paymentInfo;
        } catch (Exception e) {
            throw new AddException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public PaymentDTO updatePayment(PaymentDTO paymentInfo) throws ModifyException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.update("com.beginvegan.mybatis.PaymentMapper.updatePayment", paymentInfo);

            return paymentInfo;
        } catch (Exception e) {
            throw new ModifyException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public Integer cancelPayment(Integer reservationNo) throws ModifyException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.update("com.beginvegan.mybatis.PaymentMapper.cancelPayment", reservationNo);
            return reservationNo;
        } catch (Exception e) {
            throw new ModifyException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Override
    public String deletePayment(String impUid) throws RemoveException {
        SqlSession sqlSession = null;

        try {
            sqlSession = sqlSessionFactory.openSession();
            sqlSession.delete("com.beginvegan.mybatis.PaymentMapper.deletePayment", impUid);

            return impUid;
        } catch (Exception e) {
            throw new RemoveException(e.getMessage());
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
