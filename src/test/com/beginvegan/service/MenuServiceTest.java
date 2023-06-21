package com.beginvegan.service;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MenuRepository;
import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@Transactional
public class MenuServiceTest {

    @MockBean
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

    @Test
    void testAddMenu() throws Exception {
        // Arrange
        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);
        menu.setRestaurantNo(1);
        menu.setMenuName("테스트메뉴");
        menu.setMenuPrice(10000);
        menu.setMenuCategory("식사");
        menu.setMenuDetail("테스트메뉴 상세설명");

        // Act
        menuService.addMenu(menu);

        // Assert
        verify(menuRepository, times(1)).insertMenu(menu);
    }

    @Test
    void testAddMenuWithImage() throws Exception {
        // Arrange
        MultipartFile menuImage = new MockMultipartFile("menuImage", "menu.jpg", "image/jpeg", "image data".getBytes());

        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);
        menu.setRestaurantNo(1);
        menu.setMenuName("테스트메뉴");
        menu.setMenuPrice(10000);
        menu.setMenuCategory("식사");
        menu.setMenuDetail("테스트메뉴 상세설명");

        // Act
        menuService.addMenu(menu, menuImage);

        // Assert
        verify(menuRepository, times(1)).insertMenu(menu);
    }

    @Test
    void testAddAllMenu() throws Exception {
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
        menuService.addAllMenu(menuList);

        // Assert
        verify(menuRepository, times(1)).insertAllMenu(menuList);
    }

    @Test
    void testFindMenuByMenuNo() throws Exception {
        // Arrange
        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);
        menu.setRestaurantNo(1);
        menu.setMenuName("테스트메뉴");
        menu.setMenuPrice(10000);
        menu.setMenuCategory("식사");
        menu.setMenuDetail("테스트메뉴 상세설명");
        when(menuRepository.selectMenuByMenuNo(menu.getMenuNo())).thenReturn(menu);

        // Act
        menuService.findMenuByMenuNo(menu.getMenuNo());

        // Assert
        verify(menuRepository, times(1)).selectMenuByMenuNo(menu.getMenuNo());
    }

    @Test
    void testFindAllMenuByRestaurantNo() throws Exception {
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

        when(menuRepository.selectAllMenuByRestaurantNo(1)).thenReturn(menuList);

        // Act
        menuService.findAllMenuByRestaurantNo(1);

        // Assert
        verify(menuRepository, times(1)).selectAllMenuByRestaurantNo(1);
    }

    @Test
    void testRemoveMenuByMenuNo() throws Exception {
        // Arrange
        MenuDTO menu = new MenuDTO();
        menu.setMenuNo(1);

        // Act
        menuService.removeMenuByMenuNo(menu.getMenuNo());

        // Assert
        verify(menuRepository, times(1)).deleteMenuByMenuNo(menu.getMenuNo());
    }

    @Test
    void testRemoveAllMenuByRestaurantNo() throws RemoveException {
        // Act
        menuService.removeAllMenuByRestaurantNo(1);

        // Assert
        verify(menuRepository, times(1)).deleteAllMenuByRestaurantNo(1);
    }

}
