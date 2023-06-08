package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;

import java.util.List;

public interface MenuRepository {

    void insertMenu(MenuDTO menuInfo) throws AddException;

    void insertAllMenu(List<MenuDTO> menuList) throws AddException;

    MenuDTO selectMenuByMenuNo(int menuNo) throws FindException;

    List<MenuDTO> selectAllMenuByRestaurantNo(int restaurantNo) throws FindException;

    void deleteMenuByMenuNo(int menuNo) throws RemoveException;

    void deleteAllMenuByRestaurantNo(int RestaurantNo) throws RemoveException;

}
