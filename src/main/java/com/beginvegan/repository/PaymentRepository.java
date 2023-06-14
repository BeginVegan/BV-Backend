package com.beginvegan.repository;

import com.beginvegan.dto.PaymentDTO;
import com.beginvegan.exception.*;

import java.util.List;

public interface PaymentRepository {

    /**
     * 결제번호(ImpUid)에 해당하는 결제정로블 조회한다.
     * @param ImpUid 결제번호
     * @throws FindException 결제번호에 해당하는 정보가 없을 경우
     * @return 결제번호의 결제정보
     */
    public PaymentDTO selectPaymentByImpUid(String impUid) throws FindException;

    /**
     * 주문번호에 해당하는 결제정로블 조회한다.
     * @param reservationNo 주문번호
     * @throws FindException 주문번호에 해당하는 정보가 없을 경우
     * @return 주문번호의 결제정보
     */
    public PaymentDTO selectPaymentByReservationNo(int reservationNo) throws FindException;

    /**
     * 모든 결제정로를 조회한다.
     * @throws FindException 결제테이블이 비어있을 경우
     * @return 전체 예약 목룍
     */
    public List<PaymentDTO> selectAllPayment() throws FindException;

    /**
     * 유저의 모든 결제정보를 조회한다.
     * @param memberEmail 유저이메일
     * @throws FindException 유저이메일에 해당하는 정보가 없을 경우
     * @return 유저의 결제정보
     */
    public List<PaymentDTO> selectALLPaymentByMemberEmail(String memberEmail) throws FindException;

    /**
     * 결제정보를 생성한다.
     * @param paymentDTO 생성할 결제 정보
     * @throws AddException 결제정보 생성에 실패했을 경우
     * @return 셍성된 결제정보
     */
    public PaymentDTO insertPayment(PaymentDTO paymentDTO) throws AddException;

    /**
     * 모든 예약을 조회한다.
     * @param paymentDTO 변경할 결제정보
     * @throws ModifyException 변경에 실패할 경우
     * @return 변경된 유저의 결제정보
     */
    public PaymentDTO updatePayment(PaymentDTO paymentDTO) throws ModifyException;

    /**
     * 예약 번호에 해당하는 결제를 취소한다.
     * @throws ModifyException 예약 번호에 해당하는 결제가 없을 경우
     * @return 취소된 결제의 번호
     */
    public Integer cancelPayment(Integer reservationNo) throws ModifyException;

    /**
     * 결제번호(ImpUid)에 해당하는 결제정로블 삭제한다.
     * @param impUid 결제번호
     * @throws RemoveException 삭제에 실패할 경우
     * @return 삭제된 결제번호
     */
    public String deletePayment(String impUid) throws RemoveException;
}
