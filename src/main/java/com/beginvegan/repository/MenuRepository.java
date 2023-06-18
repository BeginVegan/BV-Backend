package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;

import java.util.List;

public interface MenuRepository {

    /**
     * 메뉴 하나를 추가한다.
     * @param menuInfo 추가할 메뉴 정보
     * @return 추가한 데이터의 개수
     * @throws AddException 데이터 추가에 실패할 경우 발생
     */
    int insertMenu(MenuDTO menuInfo) throws AddException; //TODO: 리턴값 없애기?

    /**
     * 메뉴 여러개를 추가한다.
     * @param menuList 추가할 메뉴 정보 리스트
     * @throws AddException 데이터 추가에 실패할 경우 발생
     */
    void insertAllMenu(List<MenuDTO> menuList) throws AddException;

    /**
     * menuNo와 일치하는 메뉴의 정보를 반환한다.
     * @param menuNo 조회할 메뉴 번호
     * @return 메뉴 정보
     * @throws FindException 데이터 조회에 실패할 경우 발생
     */
    MenuDTO selectMenuByMenuNo(int menuNo) throws FindException;

    /**
     * 식당의 모든 메뉴를 반환한다.
     * @param restaurantNo 메뉴를 조회할 식당 번호
     * @return 메뉴 리스트
     * @throws FindException 데이터 조회에 실패할 경우 발생
     */
    List<MenuDTO> selectAllMenuByRestaurantNo(int restaurantNo) throws FindException;

    /**
     * 메뉴 하나를 삭제한다.
     * @param menuNo 삭제할 메뉴 번호
     * @throws RemoveException 데이터 삭제에 실패할 경우 발생
     */
    void deleteMenuByMenuNo(int menuNo) throws RemoveException;

    /**
     * 식당의 모든 메뉴를 삭제한다.
     * @param RestaurantNo 메뉴를 전부 삭제할 식당 번호
     * @throws RemoveException 데이터 삭제에 실패할 경우 발생
     */
    void deleteAllMenuByRestaurantNo(int RestaurantNo) throws RemoveException;

    /**
     * 다음 menuNo를 조회한다.
     * @return 다음 menuNo
     * @throws FindException 데이터 조회에 실패할 경우 발생
     */
    int selectNextMenuNo() throws FindException;
}
