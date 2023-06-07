package com.beginvegan.service;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("menuService")
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public void addMenu(MenuDTO menuInfo) throws AddException {
        menuRepository.insertMenu(menuInfo);
    }

    public void addAllMenu(List<MenuDTO> menuList) throws AddException {
        menuRepository.insertAllMenu(menuList);
    }

    public MenuDTO findMenuByMenuNo(int menuNo) throws FindException {
        return menuRepository.selectMenuByMenuNo(menuNo);
    }

    public List<MenuDTO> findAllMenuByRestaurantNo(int restaurantNo) throws FindException {
        return menuRepository.selectAllMenuByRestaurantNo(restaurantNo);
    }

    public void removeMenuByMenuNo(int menuNo) throws RemoveException {
        menuRepository.deleteMenuByMenuNo(menuNo);
    }

    public void removeAllMenuByRestaurantNo(int restaurantNo) throws RemoveException {
        menuRepository.deleteAllMenuByRestaurantNo(restaurantNo);
    }

}
