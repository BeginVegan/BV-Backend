package com.beginvegan.repository;

import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReservationMenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;

import java.util.List;

public interface ReservationRepository {
    /**
     * 예약을 취가한다.
     * @param reservationInfo 예약 정보
     * @throws AddException DB에 예약 정보 추가에 실패할 경우 발생
     * @return 추가된 예약 정보
     * @Description
     * 동일한 시간대에 똑같은 사람이 예약할 경우
     */
    public ReservationDTO insertReservation(ReservationDTO reservationInfo) throws AddException;

    /**
     * 예약을 수정한다.
     * @param reservationInfo 예약 정보
     * @throws ModifyException DB에 예약 정보 수정에 실패할 경우 발생
     * @return 업데이트된 예약 정보
     */
    public ReservationDTO updateReservation(ReservationDTO reservationInfo) throws ModifyException;

    /**
     * 모든 예약을 조회한다.
     * @throws NullPointerException 예약테이블이 비어있을 경우
     * @return 전체 예약 목룍
     */
    public List<ReservationDTO> selectAllReservation() throws FindException;

    /**
     * 해당 유저의 모든 예약을 조회한다.
     * @throws NullPointerException 예약테이블이 비어있을 경우
     * @return 유저의 예약 목룍
     */
    public List<ReservationDTO> selectALLReservationByMemberEmail(String memberEmail) throws FindException;

    /**
     * 해당 예약넘버의 예약을 조회한다.
     * @throws NullPointerException 해당하는 예약이 없을 경우
     * @return 예약 번호에 해당하는 예약 정보
     */
    public ReservationDTO selectReservationByReservationNo(Integer reservationNo) throws FindException;

    /**
     * 예약 번호에 해당하는 예약을 취소한다.
     * @throws NullPointerException 예약테이블이 비어있을 경우
     * @return 취소된 예약의 번호
     */
    public Integer cancelReservation(Integer reservationNo) throws ModifyException;

    /**
     * 예약 번호에 해당하는 예약을 삭제한다.
     * @param reservationNo 예약 번호
     * @throws RemoveException DB에 예약 삭제 실패할 경우 발생
     * @return 삭제된 예약의 번호
     */
    public Integer deleteReservation(Integer reservationNo) throws RemoveException;

    /**
     * 다음 생성될 예약 번호를 가져온다
     * @return 다음 생성될 예약 번호
     */
    public Integer selectNextReservationNo();

}
