package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;

import java.util.List;

public interface RestaurantRepository {
    /**
     * 전체 레스토랑을 조회한다.
     * @return 레스토랑의 리스트
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
     * 식당 하나를 삭제한다.
     * @param restaurantNo 삭제할 식당 번호
     * @throws RemoveException 데이터 삭제에 실패할 경우 발생
     */
    public void deleteRestaurant(int restaurantNo) throws RemoveException;
}
