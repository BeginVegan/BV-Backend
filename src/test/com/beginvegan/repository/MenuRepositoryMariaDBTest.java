package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.util.TimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MenuRepositoryMariaDBTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @Transactional
    public void testInsertMenu() {
        try {
            RestaurantDTO restaurant = new RestaurantDTO();
            restaurant.setRestaurantName("레스토랑1");
            restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
            restaurant.setRestaurantAddressGu("종로구");
            restaurant.setRestaurantX(37.570034);
            restaurant.setRestaurantY(126.976785);
            restaurant.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
            restaurant.setRestaurantClose(TimeUtil.toTime("21:30:00"));
            restaurant.setRestaurantPhone("02-2323-1212");
            restaurant.setRestaurantDetail("상세정보1");
            restaurant.setRestaurantAvgPrice(10000);
            restaurant.setRestaurantTable(10);
            restaurant.setRestaurantTableMember(4);
            restaurant.setRestaurantVeganLevel(2);
            restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
            restaurant.setRestaurantStar(4.5);
            restaurantRepository.insertRestaurant(restaurant);

            List<MenuDTO> menuList = new ArrayList();
            MenuDTO menu1 = new MenuDTO();
            menu1.setMenuNo(menuRepository.selectNextMenuNo());
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu1.setMenuName("메뉴1");
            menu1.setMenuPrice(10000);
            menu1.setMenuCategory("식사");
            menu1.setMenuDetail("메뉴1 설명");
            menu1.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu1);

            MenuDTO menu2 = new MenuDTO();
            menu2.setMenuNo(menuRepository.selectNextMenuNo() + 1);
            menu2.setRestaurantNo(99999);
            menu2.setMenuName("메뉴2");
            menu2.setMenuPrice(10000);
            menu2.setMenuCategory("식사");
            menu2.setMenuDetail("메뉴2 설명");
            menu2.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu2);

            int insertedRows = menuRepository.insertMenu(menu1);
            Assertions.assertEquals(1, insertedRows);

            MenuDTO selectedMenu = menuRepository.selectMenuByMenuNo(menu1.getMenuNo());
            Assertions.assertNotNull(selectedMenu);
            Assertions.assertEquals(selectedMenu, menu1);

            menuRepository.deleteMenuByMenuNo(menu1.getMenuNo());
            Assertions.assertThrows(FindException.class, () -> {menuRepository.selectMenuByMenuNo(menu1.getMenuNo());});
            Assertions.assertThrows(AddException.class, () -> {menuRepository.insertMenu(menu2);});

        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testInsertAllMenu() {
        try {
            RestaurantDTO restaurant = new RestaurantDTO();
            restaurant.setRestaurantName("레스토랑1");
            restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
            restaurant.setRestaurantAddressGu("종로구");
            restaurant.setRestaurantX(37.570034);
            restaurant.setRestaurantY(126.976785);
            restaurant.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
            restaurant.setRestaurantClose(TimeUtil.toTime("21:30:00"));
            restaurant.setRestaurantPhone("02-2323-1212");
            restaurant.setRestaurantDetail("상세정보1");
            restaurant.setRestaurantAvgPrice(10000);
            restaurant.setRestaurantTable(10);
            restaurant.setRestaurantTableMember(4);
            restaurant.setRestaurantVeganLevel(2);
            restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
            restaurant.setRestaurantStar(4.5);
            restaurantRepository.insertRestaurant(restaurant);

            List<MenuDTO> menuList = new ArrayList();
            MenuDTO menu1 = new MenuDTO();
            menu1.setMenuNo(menuRepository.selectNextMenuNo());
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu1.setMenuName("메뉴1");
            menu1.setMenuPrice(10000);
            menu1.setMenuCategory("식사");
            menu1.setMenuDetail("메뉴1 설명");
            menu1.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu1);

            MenuDTO menu2 = new MenuDTO();
            menu2.setMenuNo(menuRepository.selectNextMenuNo() + 1);
            menu2.setRestaurantNo(restaurant.getRestaurantNo());
            menu2.setMenuName("메뉴2");
            menu2.setMenuPrice(10000);
            menu2.setMenuCategory("식사");
            menu2.setMenuDetail("메뉴2 설명");
            menu2.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu2);

            menuRepository.insertAllMenu(menuList);
            List<MenuDTO> selectedMenuList = menuRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertNotNull(selectedMenuList);
            Assertions.assertEquals(selectedMenuList, menuList);

            menuRepository.deleteAllMenuByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertThrows(FindException.class, () -> {menuRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());});

            menuList.get(0).setRestaurantNo(99999);
            Assertions.assertThrows(AddException.class, () -> {menuRepository.insertAllMenu(menuList);});

        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectMenuByMenuNo() {
        try {
            RestaurantDTO restaurant = new RestaurantDTO();
            restaurant.setRestaurantName("레스토랑1");
            restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
            restaurant.setRestaurantAddressGu("종로구");
            restaurant.setRestaurantX(37.570034);
            restaurant.setRestaurantY(126.976785);
            restaurant.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
            restaurant.setRestaurantClose(TimeUtil.toTime("21:30:00"));
            restaurant.setRestaurantPhone("02-2323-1212");
            restaurant.setRestaurantDetail("상세정보1");
            restaurant.setRestaurantAvgPrice(10000);
            restaurant.setRestaurantTable(10);
            restaurant.setRestaurantTableMember(4);
            restaurant.setRestaurantVeganLevel(2);
            restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
            restaurant.setRestaurantStar(4.5);
            restaurantRepository.insertRestaurant(restaurant);

            List<MenuDTO> menuList = new ArrayList();
            MenuDTO menu1 = new MenuDTO();
            menu1.setMenuNo(menuRepository.selectNextMenuNo());
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu1.setMenuName("메뉴1");
            menu1.setMenuPrice(10000);
            menu1.setMenuCategory("식사");
            menu1.setMenuDetail("메뉴1 설명");
            menu1.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu1);

            MenuDTO menu2 = new MenuDTO();
            menu2.setMenuNo(menuRepository.selectNextMenuNo() + 1);
            menu2.setRestaurantNo(99999);
            menu2.setMenuName("메뉴2");
            menu2.setMenuPrice(10000);
            menu2.setMenuCategory("식사");
            menu2.setMenuDetail("메뉴2 설명");
            menu2.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu2);

            int insertedRows = menuRepository.insertMenu(menu1);
            Assertions.assertEquals(1, insertedRows);

            MenuDTO selectedMenu = menuRepository.selectMenuByMenuNo(menu1.getMenuNo());
            Assertions.assertNotNull(selectedMenu);
            Assertions.assertEquals(selectedMenu, menu1);

            menuRepository.deleteMenuByMenuNo(menu1.getMenuNo());
            Assertions.assertThrows(FindException.class, () -> {menuRepository.selectMenuByMenuNo(menu1.getMenuNo());});
            Assertions.assertThrows(AddException.class, () -> {menuRepository.insertMenu(menu2);});

        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectAllMenuByRestaurantNo() {
        try {
            RestaurantDTO restaurant = new RestaurantDTO();
            restaurant.setRestaurantName("레스토랑1");
            restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
            restaurant.setRestaurantAddressGu("종로구");
            restaurant.setRestaurantX(37.570034);
            restaurant.setRestaurantY(126.976785);
            restaurant.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
            restaurant.setRestaurantClose(TimeUtil.toTime("21:30:00"));
            restaurant.setRestaurantPhone("02-2323-1212");
            restaurant.setRestaurantDetail("상세정보1");
            restaurant.setRestaurantAvgPrice(10000);
            restaurant.setRestaurantTable(10);
            restaurant.setRestaurantTableMember(4);
            restaurant.setRestaurantVeganLevel(2);
            restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
            restaurant.setRestaurantStar(4.5);
            restaurantRepository.insertRestaurant(restaurant);

            List<MenuDTO> menuList = new ArrayList();
            MenuDTO menu1 = new MenuDTO();
            menu1.setMenuNo(menuRepository.selectNextMenuNo());
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu1.setMenuName("메뉴1");
            menu1.setMenuPrice(10000);
            menu1.setMenuCategory("식사");
            menu1.setMenuDetail("메뉴1 설명");
            menu1.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu1);

            MenuDTO menu2 = new MenuDTO();
            menu2.setMenuNo(menuRepository.selectNextMenuNo() + 1);
            menu2.setRestaurantNo(restaurant.getRestaurantNo());
            menu2.setMenuName("메뉴2");
            menu2.setMenuPrice(10000);
            menu2.setMenuCategory("식사");
            menu2.setMenuDetail("메뉴2 설명");
            menu2.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu2);

            menuRepository.insertAllMenu(menuList);
            List<MenuDTO> selectedMenuList = menuRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertNotNull(selectedMenuList);
            Assertions.assertEquals(selectedMenuList, menuList);

            menuRepository.deleteAllMenuByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertThrows(FindException.class, () -> {menuRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());});

            menuList.get(0).setRestaurantNo(99999);
            Assertions.assertThrows(AddException.class, () -> {menuRepository.insertAllMenu(menuList);});

        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testDeleteMenuByMenuNo() {
        try {
            RestaurantDTO restaurant = new RestaurantDTO();
            restaurant.setRestaurantName("레스토랑1");
            restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
            restaurant.setRestaurantAddressGu("종로구");
            restaurant.setRestaurantX(37.570034);
            restaurant.setRestaurantY(126.976785);
            restaurant.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
            restaurant.setRestaurantClose(TimeUtil.toTime("21:30:00"));
            restaurant.setRestaurantPhone("02-2323-1212");
            restaurant.setRestaurantDetail("상세정보1");
            restaurant.setRestaurantAvgPrice(10000);
            restaurant.setRestaurantTable(10);
            restaurant.setRestaurantTableMember(4);
            restaurant.setRestaurantVeganLevel(2);
            restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
            restaurant.setRestaurantStar(4.5);
            restaurantRepository.insertRestaurant(restaurant);

            List<MenuDTO> menuList = new ArrayList();
            MenuDTO menu1 = new MenuDTO();
            menu1.setMenuNo(menuRepository.selectNextMenuNo());
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu1.setMenuName("메뉴1");
            menu1.setMenuPrice(10000);
            menu1.setMenuCategory("식사");
            menu1.setMenuDetail("메뉴1 설명");
            menu1.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu1);

            MenuDTO menu2 = new MenuDTO();
            menu2.setMenuNo(menuRepository.selectNextMenuNo() + 1);
            menu2.setRestaurantNo(99999);
            menu2.setMenuName("메뉴2");
            menu2.setMenuPrice(10000);
            menu2.setMenuCategory("식사");
            menu2.setMenuDetail("메뉴2 설명");
            menu2.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu2);

            int insertedRows = menuRepository.insertMenu(menu1);
            Assertions.assertEquals(1, insertedRows);

            MenuDTO selectedMenu = menuRepository.selectMenuByMenuNo(menu1.getMenuNo());
            Assertions.assertNotNull(selectedMenu);
            Assertions.assertEquals(selectedMenu, menu1);

            menuRepository.deleteMenuByMenuNo(menu1.getMenuNo());
            Assertions.assertThrows(FindException.class, () -> {menuRepository.selectMenuByMenuNo(menu1.getMenuNo());});
            Assertions.assertThrows(AddException.class, () -> {menuRepository.insertMenu(menu2);});

        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testDeleteAllMenuByRestaurantNo() {
        try {
            RestaurantDTO restaurant = new RestaurantDTO();
            restaurant.setRestaurantName("레스토랑1");
            restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
            restaurant.setRestaurantAddressGu("종로구");
            restaurant.setRestaurantX(37.570034);
            restaurant.setRestaurantY(126.976785);
            restaurant.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
            restaurant.setRestaurantClose(TimeUtil.toTime("21:30:00"));
            restaurant.setRestaurantPhone("02-2323-1212");
            restaurant.setRestaurantDetail("상세정보1");
            restaurant.setRestaurantAvgPrice(10000);
            restaurant.setRestaurantTable(10);
            restaurant.setRestaurantTableMember(4);
            restaurant.setRestaurantVeganLevel(2);
            restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
            restaurant.setRestaurantStar(4.5);
            restaurantRepository.insertRestaurant(restaurant);

            List<MenuDTO> menuList = new ArrayList();
            MenuDTO menu1 = new MenuDTO();
            menu1.setMenuNo(menuRepository.selectNextMenuNo());
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu1.setMenuName("메뉴1");
            menu1.setMenuPrice(10000);
            menu1.setMenuCategory("식사");
            menu1.setMenuDetail("메뉴1 설명");
            menu1.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu1);

            MenuDTO menu2 = new MenuDTO();
            menu2.setMenuNo(menuRepository.selectNextMenuNo() + 1);
            menu2.setRestaurantNo(restaurant.getRestaurantNo());
            menu2.setMenuName("메뉴2");
            menu2.setMenuPrice(10000);
            menu2.setMenuCategory("식사");
            menu2.setMenuDetail("메뉴2 설명");
            menu2.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");
            menuList.add(menu2);

            menuRepository.insertAllMenu(menuList);
            List<MenuDTO> selectedMenuList = menuRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertNotNull(selectedMenuList);
            Assertions.assertEquals(selectedMenuList, menuList);

            menuRepository.deleteAllMenuByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertThrows(FindException.class, () -> {menuRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());});

            menuList.get(0).setRestaurantNo(99999);
            Assertions.assertThrows(AddException.class, () -> {menuRepository.insertAllMenu(menuList);});

        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

}
