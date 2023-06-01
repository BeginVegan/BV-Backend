package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RestaurantRepositoryMariaDBTest {

    @Autowired
    private RestaurantRepositoryMariaDB restaurantRepository;

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @Test
    public void quartzTest() throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
        Thread.sleep(1000000);
    }

    @Test
    public void testInsertRestaurant() throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp openTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());
        Timestamp closeTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 21, 30, 00))).getTime());

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantName("레스토랑1");
        restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant.setRestaurantAddressGu("종로구");
        restaurant.setRestaurantX(37.570034);
        restaurant.setRestaurantY(126.976785);
        restaurant.setRestaurantOpen(openTime);
        restaurant.setRestaurantClose(closeTime);
        restaurant.setRestaurantDetail("상세정보1");
        restaurant.setRestaurantAvgPrice(10000);
        restaurant.setRestaurantTable(10);
        restaurant.setRestaurantTableMember(4);
        restaurant.setRestaurantVeganLevel(2);
        restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant.setRestaurantStar(4.5);

        try {
            restaurantRepository.insertRestaurant(restaurant);
            RestaurantDTO selectedRestaurant = restaurantRepository.selectRestaurantByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertEquals(restaurant, selectedRestaurant);
            restaurantRepository.deleteRestaurant(restaurant.getRestaurantNo());
        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSelectRestaurantByRestaurantNo() throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp openTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());
        Timestamp closeTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 21, 30, 00))).getTime());

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantName("레스토랑1");
        restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant.setRestaurantAddressGu("종로구");
        restaurant.setRestaurantX(37.570034);
        restaurant.setRestaurantY(126.976785);
        restaurant.setRestaurantOpen(openTime);
        restaurant.setRestaurantClose(closeTime);
        restaurant.setRestaurantDetail("상세정보1");
        restaurant.setRestaurantAvgPrice(10000);
        restaurant.setRestaurantTable(10);
        restaurant.setRestaurantTableMember(4);
        restaurant.setRestaurantVeganLevel(2);
        restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant.setRestaurantStar(4.5);

        try {
            restaurantRepository.insertRestaurant(restaurant);
            RestaurantDTO selectedRestaurant = restaurantRepository.selectRestaurantByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertEquals(restaurant, selectedRestaurant);
            restaurantRepository.deleteRestaurant(restaurant.getRestaurantNo());
        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSelectAllRestaurant() throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp openTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());
        Timestamp closeTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 21, 30, 00))).getTime());

        RestaurantDTO restaurant1 = new RestaurantDTO();
        restaurant1.setRestaurantName("test1");
        restaurant1.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant1.setRestaurantAddressGu("종로구");
        restaurant1.setRestaurantX(37.570034);
        restaurant1.setRestaurantY(126.976785);
        restaurant1.setRestaurantOpen(openTime);
        restaurant1.setRestaurantClose(closeTime);
        restaurant1.setRestaurantDetail("상세정보1");
        restaurant1.setRestaurantAvgPrice(10000);
        restaurant1.setRestaurantTable(10);
        restaurant1.setRestaurantTableMember(4);
        restaurant1.setRestaurantVeganLevel(2);
        restaurant1.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant1.setRestaurantStar(4.5);

        RestaurantDTO restaurant2 = new RestaurantDTO();
        restaurant2.setRestaurantName("test2");
        restaurant2.setRestaurantAddress("서울특별시 강남구 강남대로 1");
        restaurant2.setRestaurantAddressGu("강남구");
        restaurant2.setRestaurantX(37.495985);
        restaurant2.setRestaurantY(127.027546);
        restaurant2.setRestaurantOpen(openTime);
        restaurant2.setRestaurantClose(closeTime);
        restaurant2.setRestaurantDetail("상세정보2");
        restaurant2.setRestaurantAvgPrice(15000);
        restaurant2.setRestaurantTable(8);
        restaurant2.setRestaurantTableMember(2);
        restaurant2.setRestaurantVeganLevel(3);
        restaurant2.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant2.setRestaurantStar(4.2);

        try {
            restaurantRepository.insertRestaurant(restaurant1);
            restaurantRepository.insertRestaurant(restaurant2);
            List<RestaurantDTO> restaurantList = restaurantRepository.selectAllRestaurant();

            Assertions.assertNotNull(restaurantList);
            Assertions.assertTrue(restaurantList.contains(restaurant1));
            Assertions.assertTrue(restaurantList.contains(restaurant2));

            restaurantRepository.deleteRestaurant(restaurant1.getRestaurantNo());
            restaurantRepository.deleteRestaurant(restaurant2.getRestaurantNo());
        } catch (FindException | AddException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testInsertRestaurantMenu() throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp openTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());
        Timestamp closeTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 21, 30, 00))).getTime());

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantName("레스토랑1");
        restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant.setRestaurantAddressGu("종로구");
        restaurant.setRestaurantX(37.570034);
        restaurant.setRestaurantY(126.976785);
        restaurant.setRestaurantOpen(openTime);
        restaurant.setRestaurantClose(closeTime);
        restaurant.setRestaurantDetail("상세정보1");
        restaurant.setRestaurantAvgPrice(10000);
        restaurant.setRestaurantTable(10);
        restaurant.setRestaurantTableMember(4);
        restaurant.setRestaurantVeganLevel(2);
        restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant.setRestaurantStar(4.5);

        MenuDTO menu1 = new MenuDTO();
        menu1.setMenuName("메뉴1");
        menu1.setMenuPrice(10000);
        menu1.setMenuCategory("식사");
        menu1.setMenuDetail("메뉴1 설명");
        menu1.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");

        MenuDTO menu2 = new MenuDTO();
        menu2.setMenuName("메뉴2");
        menu2.setMenuPrice(3000);
        menu2.setMenuCategory("음료");
        menu2.setMenuDetail("메뉴2 설명");
        menu2.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");

        List<MenuDTO> menuList = new ArrayList<>();
        menuList.add(menu1);
        menuList.add(menu2);

        try {
            restaurantRepository.insertRestaurant(restaurant);
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu2.setRestaurantNo(restaurant.getRestaurantNo());
            restaurantRepository.insertRestaurantMenu(restaurant.getRestaurantNo(), menuList);
            List<MenuDTO> selectedMenuList = restaurantRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());

            Assertions.assertEquals(selectedMenuList.get(0).getRestaurantNo(), menu1.getRestaurantNo());
            Assertions.assertEquals(selectedMenuList.get(0).getMenuName(), menu1.getMenuName());

            Assertions.assertEquals(selectedMenuList.get(1).getRestaurantNo(), menu2.getRestaurantNo());
            Assertions.assertEquals(selectedMenuList.get(1).getMenuName(), menu2.getMenuName());
        } catch (AddException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSelectAllMenuByRestaurantNo() throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp openTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());
        Timestamp closeTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 21, 30, 00))).getTime());

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantName("레스토랑1");
        restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant.setRestaurantAddressGu("종로구");
        restaurant.setRestaurantX(37.570034);
        restaurant.setRestaurantY(126.976785);
        restaurant.setRestaurantOpen(openTime);
        restaurant.setRestaurantClose(closeTime);
        restaurant.setRestaurantDetail("상세정보1");
        restaurant.setRestaurantAvgPrice(10000);
        restaurant.setRestaurantTable(10);
        restaurant.setRestaurantTableMember(4);
        restaurant.setRestaurantVeganLevel(2);
        restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant.setRestaurantStar(4.5);

        MenuDTO menu1 = new MenuDTO();
        menu1.setMenuName("메뉴1");
        menu1.setMenuPrice(10000);
        menu1.setMenuCategory("식사");
        menu1.setMenuDetail("메뉴1 설명");
        menu1.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");

        MenuDTO menu2 = new MenuDTO();
        menu2.setMenuName("메뉴2");
        menu2.setMenuPrice(3000);
        menu2.setMenuCategory("음료");
        menu2.setMenuDetail("메뉴2 설명");
        menu2.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");

        List<MenuDTO> menuList = new ArrayList<>();
        menuList.add(menu1);
        menuList.add(menu2);

        try {
            restaurantRepository.insertRestaurant(restaurant);
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu2.setRestaurantNo(restaurant.getRestaurantNo());
            restaurantRepository.insertRestaurantMenu(restaurant.getRestaurantNo(), menuList);
            List<MenuDTO> selectedMenuList = restaurantRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());

            Assertions.assertEquals(selectedMenuList.get(0).getRestaurantNo(), menu1.getRestaurantNo());
            Assertions.assertEquals(selectedMenuList.get(0).getMenuName(), menu1.getMenuName());

            Assertions.assertEquals(selectedMenuList.get(1).getRestaurantNo(), menu2.getRestaurantNo());
            Assertions.assertEquals(selectedMenuList.get(1).getMenuName(), menu2.getMenuName());
        } catch (AddException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateRestaurant() throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp openTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());
        Timestamp closeTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 21, 30, 00))).getTime());

        RestaurantDTO restaurant1 = new RestaurantDTO();
        restaurant1.setRestaurantName("test1");
        restaurant1.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant1.setRestaurantAddressGu("종로구");
        restaurant1.setRestaurantX(37.570034);
        restaurant1.setRestaurantY(126.976785);
        restaurant1.setRestaurantOpen(openTime);
        restaurant1.setRestaurantClose(closeTime);
        restaurant1.setRestaurantDetail("상세정보1");
        restaurant1.setRestaurantAvgPrice(10000);
        restaurant1.setRestaurantTable(10);
        restaurant1.setRestaurantTableMember(4);
        restaurant1.setRestaurantVeganLevel(2);
        restaurant1.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant1.setRestaurantStar(4.5);

        RestaurantDTO restaurant2 = new RestaurantDTO();
        restaurant2.setRestaurantName("test2");
        restaurant2.setRestaurantAddress("서울특별시 강남구 강남대로 1");
        restaurant2.setRestaurantAddressGu("강남구");
        restaurant2.setRestaurantX(37.495985);
        restaurant2.setRestaurantY(127.027546);
        restaurant2.setRestaurantOpen(openTime);
        restaurant2.setRestaurantClose(closeTime);
        restaurant2.setRestaurantDetail("상세정보2");
        restaurant2.setRestaurantAvgPrice(15000);
        restaurant2.setRestaurantTable(8);
        restaurant2.setRestaurantTableMember(2);
        restaurant2.setRestaurantVeganLevel(3);
        restaurant2.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant2.setRestaurantStar(4.2);

        try {
            restaurantRepository.insertRestaurant(restaurant1);
            restaurant2.setRestaurantNo(restaurant1.getRestaurantNo());
            restaurantRepository.updateRestaurant(restaurant2);
            RestaurantDTO selectedRestaruant = restaurantRepository.selectRestaurantByRestaurantNo(restaurant1.getRestaurantNo());
            Assertions.assertNotEquals(restaurant1, selectedRestaruant);
            Assertions.assertEquals(restaurant2, selectedRestaruant);
            restaurantRepository.deleteRestaurant(restaurant1.getRestaurantNo());
        } catch (AddException | FindException | ModifyException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSelectBestRestaurant() throws ParseException {

    }

    @Test
    public void testDeleteRestaurant() throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp openTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());
        Timestamp closeTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 21, 30, 00))).getTime());

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantName("레스토랑1");
        restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant.setRestaurantAddressGu("종로구");
        restaurant.setRestaurantX(37.570034);
        restaurant.setRestaurantY(126.976785);
        restaurant.setRestaurantOpen(openTime);
        restaurant.setRestaurantClose(closeTime);
        restaurant.setRestaurantDetail("상세정보1");
        restaurant.setRestaurantAvgPrice(10000);
        restaurant.setRestaurantTable(10);
        restaurant.setRestaurantTableMember(4);
        restaurant.setRestaurantVeganLevel(2);
        restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant.setRestaurantStar(4.5);

        try {
            restaurantRepository.insertRestaurant(restaurant);
            restaurantRepository.deleteRestaurant(restaurant.getRestaurantNo());
            Assertions.assertFalse(restaurantRepository.selectAllRestaurant().contains(restaurant));
        } catch (FindException | AddException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteRestaurantMenu() throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp openTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 10, 00, 00))).getTime());
        Timestamp closeTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 21, 30, 00))).getTime());

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantName("레스토랑1");
        restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant.setRestaurantAddressGu("종로구");
        restaurant.setRestaurantX(37.570034);
        restaurant.setRestaurantY(126.976785);
        restaurant.setRestaurantOpen(openTime);
        restaurant.setRestaurantClose(closeTime);
        restaurant.setRestaurantDetail("상세정보1");
        restaurant.setRestaurantAvgPrice(10000);
        restaurant.setRestaurantTable(10);
        restaurant.setRestaurantTableMember(4);
        restaurant.setRestaurantVeganLevel(2);
        restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant.setRestaurantStar(4.5);

        MenuDTO menu1 = new MenuDTO();
        menu1.setMenuName("메뉴1");
        menu1.setMenuPrice(10000);
        menu1.setMenuCategory("식사");
        menu1.setMenuDetail("메뉴1 설명");
        menu1.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");

        MenuDTO menu2 = new MenuDTO();
        menu2.setMenuName("메뉴2");
        menu2.setMenuPrice(3000);
        menu2.setMenuCategory("음료");
        menu2.setMenuDetail("메뉴2 설명");
        menu2.setMenuPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg");

        List<MenuDTO> menuList = new ArrayList<>();
        menuList.add(menu1);
        menuList.add(menu2);

        try {
            restaurantRepository.insertRestaurant(restaurant);
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu2.setRestaurantNo(restaurant.getRestaurantNo());
            restaurantRepository.insertRestaurantMenu(restaurant.getRestaurantNo(), menuList);
            restaurantRepository.deleteRestaurantMenu(restaurant.getRestaurantNo());
            List<MenuDTO> selectedMenuList = restaurantRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertTrue(selectedMenuList.isEmpty());
            restaurantRepository.deleteRestaurant(restaurant.getRestaurantNo());
            Assertions.assertFalse(restaurantRepository.selectAllRestaurant().contains(restaurant));
        } catch (FindException | AddException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }


}