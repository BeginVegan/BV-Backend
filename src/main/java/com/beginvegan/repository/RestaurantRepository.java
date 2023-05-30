package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;

import java.util.List;

public interface RestaurantRepository {
    /**
     * 전체 식당을 조회한다.
     * @return 조회한 식당 리스트
     * @throws FindException Restaurant 테이블에 데이터가 없을 때 발생하는 Exception
     */
    public List<RestaurantDTO> selectAllRestaurant() throws FindException;

    /**
     * restaurantNo와 일치하는 식당의 정보를 반환한다.
     * @param restaurantNo 조회할 식당 번호
     * @return 식당 정보
     * @throws FindException 식당 정보 조회에 실패할 경우 발생
     */
    public RestaurantDTO selectRestaurantByRestaurantNo(int restaurantNo) throws FindException;

    /**
     * DB에 새로운 식당을 추가한다.
     * @param restaurantInfo 추가할 식당 정보
     * @return insert한 식당의 restaurant_no
     * @throws AddException 데이터 추가에 실패할 경우 발생
     */
    public int insertRestaurant(RestaurantDTO restaurantInfo) throws AddException;

    /**
     * DB에 새로운 메뉴를 추가한다.
     * @param restaurantNo 메뉴들이 속한 식당의 번호
     * @param menuList 추가할 메뉴들
     * @throws AddException 데이터 추가에 실패할 경우 발생
     */
    public void insertRestaurantMenu(int restaurantNo, List<MenuDTO> menuList) throws AddException;

    /**
     * 특정 식당의 메뉴 전체를 조회한다.
     * @param restaurantNo 메뉴를 조회할 식당의 번호
     * @return 메뉴 리스트
     * @throws FindException 데이터 조회에 실패할 경우 발생
     */
    public List<MenuDTO> selectAllMenuByRestaurantNo(int restaurantNo) throws FindException;

    /**
     * 식당 하나를 삭제한다.
     * @param restaurantNo 삭제할 식당 번호
     * @throws RemoveException 데이터 삭제에 실패할 경우 발생
     */
    public void deleteRestaurant(int restaurantNo) throws RemoveException;

    /**
     * 식당 정보를 변경한다.
     * @param restaurantInfo 변경할 식당 정보
     * @throws ModifyException 데이터 변경에 실패할 경우 발생
     */
    public void updateRestaurant(RestaurantDTO restaurantInfo) throws ModifyException;

    /**
     * 특정 식당의 메뉴를 모두 삭제한다.
     * @param restaurantNo 메뉴를 삭제할 식당 번호
     * @throws RemoveException 데이터 삭제에 실패할 경우 발생
     */
    public void deleteRestaurantMenu(int restaurantNo) throws RemoveException;

    /**
     * 예약순, 평점순, 리뷰순 정렬시 가장 높은 식당을 반환한다.
     * @return 예약순 10개, 평점순 10개, 리뷰순 10개를 합한 30개의 식당 데이터
     * @throws FindException 데이터 조회에 실패한 경우 발생
     */
    public List<RestaurantDTO> selectBestRestaurant() throws FindException;
}
