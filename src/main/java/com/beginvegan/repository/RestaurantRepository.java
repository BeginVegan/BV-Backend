package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.*;

import java.util.List;

public interface RestaurantRepository {
    /**
     * 전체 식당을 조회한다.(메뉴 제외)
     * @return 조회한 식당 리스트
     * @throws FindException Restaurant 테이블에 데이터가 없을 때 발생하는 Exception
     */
    List<RestaurantDTO> selectAllRestaurant() throws FindException;

    /**
     * restaurantNo와 일치하는 식당의 정보를 반환한다.(메뉴 제외)
     * @param restaurantNo 조회할 식당 번호
     * @return 식당 정보
     * @throws FindException 식당 정보 조회에 실패할 경우 발생
     */
    RestaurantDTO selectRestaurantByRestaurantNo(int restaurantNo) throws FindException;

    /**
     * DB에 새로운 식당을 추가한다.
     * @param restaurantInfo 추가할 식당 정보
     * @return insert한 식당의 restaurant_no
     * @throws AddException 데이터 추가에 실패할 경우 발생
     */
    int insertRestaurant(RestaurantDTO restaurantInfo) throws AddException;

    /**
     * DB에 새로운 메뉴를 추가한다.
     * @param restaurantNo 메뉴들이 속한 식당의 번호
     * @param menuList 추가할 메뉴들
     * @throws AddException 데이터 추가에 실패할 경우 발생
     */
    void insertRestaurantMenu(int restaurantNo, List<MenuDTO> menuList) throws AddException;

    /**
     * 특정 식당의 메뉴 전체를 조회한다.
     * @param restaurantNo 메뉴를 조회할 식당의 번호
     * @return 메뉴 리스트
     * @throws FindException 데이터 조회에 실패할 경우 발생
     */
    List<MenuDTO> selectAllMenuByRestaurantNo(int restaurantNo) throws FindException;

    /**
     * 식당 하나를 삭제한다.
     * @param restaurantNo 삭제할 식당 번호
     * @throws RemoveException 데이터 삭제에 실패할 경우 발생
     */
    void deleteRestaurant(int restaurantNo) throws RemoveException;

    /**
     * 식당 정보를 변경한다.
     * @param restaurantInfo 변경할 식당 정보
     * @throws ModifyException 데이터 변경에 실패할 경우 발생
     */
    void updateRestaurant(RestaurantDTO restaurantInfo) throws ModifyException;

    /**
     * 특정 식당의 메뉴를 모두 삭제한다.
     * @param restaurantNo 메뉴를 삭제할 식당 번호
     * @throws RemoveException 데이터 삭제에 실패할 경우 발생
     */
    void deleteRestaurantMenu(int restaurantNo) throws RemoveException;

    /**
     * restaurantNo와 일치하는 식당의 정보를 반환한다.(메뉴 포함)
     * @param restaurantNo 조회할 식당 번호
     * @return 메뉴를 포함한 식당 정보
     * @throws FindException 데이터 조회에 실패할 경우 발생
     */
    RestaurantDTO selectRestaurantMenuByRestaurantNo(int restaurantNo) throws FindException;

    /**
     * 평점이 높은 식당 10곳에 대한 뷰를 만든다.
     * @throws CreateException 뷰 생성에 실패할 경우 발생
     * @description Quartz Scheduler를 사용하여 특정 시각에 자동으로 실행되도록 한다.
     */
    void createBestStarView() throws CreateException;

    /**
     * 리뷰가 많은 식당 10곳에 대한 뷰를 만든다.
     * @throws CreateException 뷰 생성에 실패할 경우 발생
     * @description Quartz Scheduler를 사용하여 특정 시각에 자동으로 실행되도록 한다.
     */
    void createBestReviewView() throws CreateException;

    /**
     * 예약이 많은 식당 10곳에 대한 뷰를 만든다.
     * @throws CreateException 뷰 생성에 실패할 경우 발생
     * @description Quartz Scheduler를 사용하여 특정 시각에 자동으로 실행되도록 한다.
     */
    void createBestReservationView() throws CreateException;

    /**
     * 평점이 높은 식당에 대한 뷰를 조회한다.
     * @return 평점이 높은 식당 10개
     * @throws FindException 조회에 실패할 경우 발생
     */
    List<RestaurantDTO> selectBestStarRestaurant() throws FindException;

    /**
     * 리뷰가 많은 식당에 대한 뷰를 조회한다.
     * @return 리뷰가 많은 식당 10개
     * @throws FindException 조회에 실패할 경우 발생
     */
    List<RestaurantDTO> selectBestReviewRestaurant() throws FindException;

    /**
     * 예약이 많은 식당에 대한 뷰를 조회한다.
     * @return 예약이 많은 식당 10개
     * @throws FindException 조회에 실패할 경우 발생
     */
    List<RestaurantDTO> selectBestReservationRestaurant() throws FindException;
}
