package com.beginvegan.controller;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Multipart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Transactional
public class MenuControllerTest {

    @MockBean
    private MenuService menuService;

    @Autowired
    private MenuController menuController;

    @Test
    void testMenuAdd() throws Exception {
        // Arrange
        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);
        menu.setRestaurantNo(1);
        menu.setMenuName("테스트메뉴");
        menu.setMenuPrice(10000);
        menu.setMenuCategory("식사");
        menu.setMenuDetail("테스트메뉴 상세설명");

        MultipartFile menuImage = new MockMultipartFile("menuImage", "menu.jpg", "image/jpeg", "image data".getBytes());

        // Act
        ResponseEntity<?> response1 = menuController.MenuAdd(menu, Optional.ofNullable(menuImage));
        ResponseEntity<?> response2 = menuController.MenuAdd(menu, Optional.ofNullable(null));

        // Assert
        verify(menuService, times(1)).addMenu(menu, menuImage);
        verify(menuService, times(1)).addMenu(menu);

        Assertions.assertEquals(HttpStatus.OK, response1.getStatusCode());
        Assertions.assertEquals(HttpStatus.OK, response2.getStatusCode());
    }

    @Test
    void testMenuListAdd() throws Exception {
        // Arrange
        List<MenuDTO> menuList = new ArrayList<>();
        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);
        menu.setRestaurantNo(1);
        menu.setMenuName("테스트메뉴");
        menu.setMenuPrice(10000);
        menu.setMenuCategory("식사");
        menu.setMenuDetail("테스트메뉴 상세설명");
        menuList.add(menu);

        // Act
        ResponseEntity<?> response = menuController.MenuListAdd(menuList);

        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testMenuOne() throws Exception {
        // Arrange
        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);
        menu.setRestaurantNo(1);
        menu.setMenuName("테스트메뉴");
        menu.setMenuPrice(10000);
        menu.setMenuCategory("식사");
        menu.setMenuDetail("테스트메뉴 상세설명");
        when(menuService.findMenuByMenuNo(1)).thenReturn(menu);

        // Act
        ResponseEntity<?> response = menuController.menuOne(menu.getMenuNo());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        MenuDTO returnMenu = (MenuDTO)responseBody.get("menuInfo");

        // Assert
        verify(menuService, times(1)).findMenuByMenuNo(menu.getMenuNo());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(menu, returnMenu);
    }

    @Test
    void testMenuList() throws Exception {
        // Arrange
        int restaurantNo = 1;

        List<MenuDTO> menuList = new ArrayList<>();
        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);
        menu.setRestaurantNo(1);
        menu.setMenuName("테스트메뉴");
        menu.setMenuPrice(10000);
        menu.setMenuCategory("식사");
        menu.setMenuDetail("테스트메뉴 상세설명");
        menuList.add(menu);
        when(menuService.findAllMenuByRestaurantNo(restaurantNo)).thenReturn(menuList);


        // Act
        ResponseEntity<?> response = menuController.menuList(restaurantNo);
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        List<MenuDTO> returnMenuList = (List)responseBody.get("menuList");

        // Assert
        verify(menuService, times(1)).findAllMenuByRestaurantNo(restaurantNo);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(menuList, returnMenuList);
    }

    @Test
    void testMenuRemove() throws Exception {
        // Arrange
        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);
        menu.setRestaurantNo(1);
        menu.setMenuName("테스트메뉴");
        menu.setMenuPrice(10000);
        menu.setMenuCategory("식사");
        menu.setMenuDetail("테스트메뉴 상세설명");

        // Act
        ResponseEntity<?> response = menuController.menuRemove(menu.getMenuNo());

        // Assert
        verify(menuService, times(1)).removeMenuByMenuNo(menu.getMenuNo());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testMenuListRemove() throws Exception {
        // Arrange
        int restaurantNo = 1;

        List<MenuDTO> menuList = new ArrayList<>();
        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);
        menu.setRestaurantNo(1);
        menu.setMenuName("테스트메뉴");
        menu.setMenuPrice(10000);
        menu.setMenuCategory("식사");
        menu.setMenuDetail("테스트메뉴 상세설명");
        menuList.add(menu);

        // Act
        ResponseEntity<?> response = menuController.menuListRemove(restaurantNo);

        // Assert
        verify(menuService, times(1)).removeAllMenuByRestaurantNo(restaurantNo);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }



}
