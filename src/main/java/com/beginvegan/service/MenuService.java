package com.beginvegan.service;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service("menuService")
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private S3Service S3Service;

    public int addMenu(MenuDTO menuInfo) throws AddException {
        return menuRepository.insertMenu(menuInfo);
    }
    public int addMenu(MenuDTO menuInfo, MultipartFile menuImage) throws AddException, IOException {
        String uploadUrl = S3Service.upload(menuImage,"menu/" + menuInfo.getRestaurantNo());
        menuInfo.setMenuPhotoDir(uploadUrl);
        return menuRepository.insertMenu(menuInfo);
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
