package com.beginvegan.repository;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.ReservationMenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.*;
import com.beginvegan.util.TimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class RestaurantRepositoryMariaDBTest {

    @Autowired
    private RestaurantRepositoryMariaDB restaurantRepository;

    @Autowired
    private ReservationRepository reservationRepository;

//    @Autowired
//    SchedulerFactoryBean schedulerFactoryBean;

//    @Test
//    public void quartzTest() throws Exception {
//        Scheduler scheduler = schedulerFactoryBean.getScheduler();
//        scheduler.start();
//        Thread.sleep(1000000);
//    }

    @Test
    @Transactional
    public void testInsertRestaurant() {
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

        try {
            restaurantRepository.insertRestaurant(restaurant);
            RestaurantDTO selectedRestaurant = restaurantRepository.selectRestaurantByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertEquals(restaurant, selectedRestaurant);
            restaurant.setRestaurantName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            Assertions.assertThrows(AddException.class, () -> {restaurantRepository.insertRestaurant(restaurant);});
            restaurantRepository.deleteRestaurant(restaurant.getRestaurantNo());
        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectRestaurantByRestaurantNo() {
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

        try {
            restaurantRepository.insertRestaurant(restaurant);
            RestaurantDTO selectedRestaurant = restaurantRepository.selectRestaurantByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertEquals(restaurant, selectedRestaurant);
            Assertions.assertThrows(FindException.class, () -> {restaurantRepository.selectRestaurantByRestaurantNo(99999);});
            restaurantRepository.deleteRestaurant(restaurant.getRestaurantNo());
        } catch (AddException | FindException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectAllRestaurant() {
        RestaurantDTO restaurant1 = new RestaurantDTO();
        restaurant1.setRestaurantName("test1");
        restaurant1.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant1.setRestaurantAddressGu("종로구");
        restaurant1.setRestaurantX(37.570034);
        restaurant1.setRestaurantY(126.976785);
        restaurant1.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
        restaurant1.setRestaurantClose(TimeUtil.toTime("21:30:00"));
        restaurant1.setRestaurantPhone("02-2323-1212");
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
        restaurant2.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
        restaurant2.setRestaurantClose(TimeUtil.toTime("21:30:00"));
        restaurant2.setRestaurantPhone("02-2323-1212");
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
    @Transactional
    public void testInsertRestaurantMenu() {
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

            Assertions.assertThrows(AddException.class, () -> {restaurantRepository.insertRestaurantMenu(99999, menuList);});
        } catch (AddException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectAllMenuByRestaurantNo() {
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

            Assertions.assertThrows(FindException.class, () -> {restaurantRepository.selectAllMenuByRestaurantNo(99999);});
        } catch (AddException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectRestaurantMenuByRestaurantNo() {
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


        try {
            List<MenuDTO> menuList = new ArrayList<>();
            restaurantRepository.insertRestaurant(restaurant);

            // restaurantNo와 menuNo를 설정해줌
            menu1.setRestaurantNo(restaurant.getRestaurantNo());
            menu1.setMenuNo(restaurantRepository.selectNextMenuNo());
            menu2.setRestaurantNo(restaurant.getRestaurantNo());
            menu2.setMenuNo(restaurantRepository.selectNextMenuNo() + 1);
            menuList.add(menu1);
            menuList.add(menu2);
            restaurant.setMenuList((ArrayList)menuList);

            restaurantRepository.insertRestaurantMenu(restaurant.getRestaurantNo(), menuList);
            RestaurantDTO selectedRestaurantMenu = restaurantRepository.selectRestaurantMenuByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertNotNull(selectedRestaurantMenu);
            Assertions.assertEquals(selectedRestaurantMenu, restaurant);

            Assertions.assertThrows(FindException.class, () -> {restaurantRepository.selectRestaurantMenuByRestaurantNo(99999);});

        } catch (AddException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void selectAllRestaurantByKeyword() {
        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantName("박복자 볶음밥");
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

        try {
            restaurantRepository.insertRestaurant(restaurant);

            String keyword = "박복자";
            Map<String, Object> keywordMap = new HashMap<>();
            keywordMap.put("entireKeyword", keyword);
            String[] keywords = keyword.split(" ");
            keywordMap.put("keywords", keywords);

            restaurantRepository.insertRestaurant(restaurant);
            List<RestaurantDTO> selectedRestaurantList = restaurantRepository.selectAllRestaurantByKeyword(keywordMap);
            Assertions.assertNotNull(selectedRestaurantList);
            Assertions.assertTrue(selectedRestaurantList.contains(restaurant));

            keyword = "없는가게이름입니다이런가게는없어요";
            keywordMap.put("entireKeyword", keyword);
            keywords = keyword.split(" ");
            keywordMap.put("keywords", keywords);
            Assertions.assertThrows(FindException.class, () -> {restaurantRepository.selectAllRestaurantByKeyword(keywordMap);});
        } catch (AddException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void selectAllReservationByRestaurantNo() {
        try {
            RestaurantDTO restaurant = new RestaurantDTO();
            restaurant.setRestaurantName("박복자 볶음밥");
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

            int NextReservationNo = reservationRepository.selectNextReservationNo();
            List<ReservationMenuDTO> reservationMenuList = new ArrayList<>();
            ReservationMenuDTO reservationMenuDTO = new ReservationMenuDTO(1, restaurant.getRestaurantNo(), "메뉴1", 10000, "카테고리1", "메뉴1 설명", "https://bv-image.s3.ap-northeast-2.amazonaws.com/menu/sandwich.jpg", NextReservationNo, 1);
            reservationMenuList.add(reservationMenuDTO);
            ReservationDTO reservation = new ReservationDTO(
                    NextReservationNo,
                    "asdf@naver.com",
                    restaurant.getRestaurantNo(),
                    TimeUtil.toDateTime("2023-06-23 00:00:00"),
                    TimeUtil.toDateTime("2023-06-23 00:00:00"),
                    "매장",
                    2,
                    1000,
                    5000,
                    "예약",
                    reservationMenuList
            );
            reservationRepository.insertReservation(reservation);
            List<ReservationDTO> reservationList = restaurantRepository.selectAllReservationByRestaurantNo(restaurant.getRestaurantNo());
            Assertions.assertNotNull(reservationList);
            reservation.setReservationMenuList(null);
            Assertions.assertEquals(reservationList.get(0), reservation);
            Assertions.assertThrows(FindException.class, () -> {restaurantRepository.selectAllReservationByRestaurantNo(99999);});
        } catch (AddException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testUpdateRestaurant() {
        RestaurantDTO restaurant1 = new RestaurantDTO();
        restaurant1.setRestaurantName("test1");
        restaurant1.setRestaurantAddress("서울특별시 종로구 종로 1");
        restaurant1.setRestaurantAddressGu("종로구");
        restaurant1.setRestaurantX(37.570034);
        restaurant1.setRestaurantY(126.976785);
        restaurant1.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
        restaurant1.setRestaurantClose(TimeUtil.toTime("21:30:00"));
        restaurant1.setRestaurantPhone("02-2323-1212");
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
        restaurant2.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
        restaurant2.setRestaurantClose(TimeUtil.toTime("21:30:00"));
        restaurant2.setRestaurantPhone("02-2323-1212");
        restaurant2.setRestaurantDetail("상세정보2");
        restaurant2.setRestaurantAvgPrice(15000);
        restaurant2.setRestaurantTable(8);
        restaurant2.setRestaurantTableMember(2);
        restaurant2.setRestaurantVeganLevel(3);
        restaurant2.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
        restaurant2.setRestaurantStar(4.5);

        try {
            restaurantRepository.insertRestaurant(restaurant1);
            restaurant2.setRestaurantNo(restaurant1.getRestaurantNo());
            restaurantRepository.updateRestaurant(restaurant2);
            RestaurantDTO selectedRestaurant = restaurantRepository.selectRestaurantByRestaurantNo(restaurant1.getRestaurantNo());
            Assertions.assertNotEquals(restaurant1, selectedRestaurant);
            Assertions.assertEquals(restaurant2, selectedRestaurant);
            restaurantRepository.deleteRestaurant(restaurant1.getRestaurantNo());
        } catch (AddException | FindException | ModifyException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testDeleteRestaurant() {
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

        try {
            restaurantRepository.insertRestaurant(restaurant);
            restaurantRepository.deleteRestaurant(restaurant.getRestaurantNo());
            Assertions.assertFalse(restaurantRepository.selectAllRestaurant().contains(restaurant));
        } catch (FindException | AddException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testDeleteRestaurantMenu() {
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
            Assertions.assertThrows(FindException.class, () -> {restaurantRepository.selectAllMenuByRestaurantNo(restaurant.getRestaurantNo());});
            restaurantRepository.deleteRestaurant(restaurant.getRestaurantNo());
            Assertions.assertFalse(restaurantRepository.selectAllRestaurant().contains(restaurant));
        } catch (FindException | AddException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testCreateBestStarRestaurant() {
        try {
            restaurantRepository.createBestStarView();
            List<RestaurantDTO> restaurantList1 = restaurantRepository.selectBestStarRestaurant();
            List<RestaurantDTO> restaurantList2 = restaurantRepository.selectBestStarRestaurantTest();
            Assertions.assertNotNull(restaurantList1);
            Assertions.assertNotNull(restaurantList2);
            Assertions.assertEquals(restaurantList1, restaurantList2);
        } catch (CreateException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testCreateBestReviewRestaurant() {
        try {
            restaurantRepository.createBestReviewView();
            List<RestaurantDTO> restaurantList1 = restaurantRepository.selectBestReviewRestaurant();
            List<RestaurantDTO> restaurantList2 = restaurantRepository.selectBestReviewRestaurantTest();
            Assertions.assertNotNull(restaurantList1);
            Assertions.assertNotNull(restaurantList2);
            Assertions.assertEquals(restaurantList1, restaurantList2);
        } catch (CreateException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testCreateBestReservationRestaurant() {
        try {
            restaurantRepository.createBestReservationView();
            List<RestaurantDTO> restaurantList1 = restaurantRepository.selectBestReservationRestaurant();
            List<RestaurantDTO> restaurantList2 = restaurantRepository.selectBestReservationRestaurantTest();
            Assertions.assertNotNull(restaurantList1);
            Assertions.assertNotNull(restaurantList2);
            Assertions.assertEquals(restaurantList1, restaurantList2);
        } catch (CreateException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectBestStarRestaurant() {
        try {
            restaurantRepository.createBestStarView();
            List<RestaurantDTO> restaurantList1 = restaurantRepository.selectBestStarRestaurant();
            List<RestaurantDTO> restaurantList2 = restaurantRepository.selectBestStarRestaurantTest();
            Assertions.assertNotNull(restaurantList1);
            Assertions.assertNotNull(restaurantList2);
            Assertions.assertEquals(restaurantList1, restaurantList2);
        } catch (CreateException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectBestReviewRestaurant() {
        try {
            restaurantRepository.createBestReviewView();
            List<RestaurantDTO> restaurantList1 = restaurantRepository.selectBestReviewRestaurant();
            List<RestaurantDTO> restaurantList2 = restaurantRepository.selectBestReviewRestaurantTest();
            Assertions.assertNotNull(restaurantList1);
            Assertions.assertNotNull(restaurantList2);
            Assertions.assertEquals(restaurantList1, restaurantList2);
        } catch (CreateException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectBestReservationRestaurant() {
        try {
            restaurantRepository.createBestReservationView();
            List<RestaurantDTO> restaurantList1 = restaurantRepository.selectBestReservationRestaurant();
            List<RestaurantDTO> restaurantList2 = restaurantRepository.selectBestReservationRestaurantTest();
            Assertions.assertNotNull(restaurantList1);
            Assertions.assertNotNull(restaurantList2);
            Assertions.assertEquals(restaurantList1, restaurantList2);
        } catch (CreateException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSelectNextRestaurantNo() {
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

            int nextRestaurantNo = restaurantRepository.selectNextRestaurantNo();
            restaurantRepository.insertRestaurant(restaurant);
            Assertions.assertEquals(nextRestaurantNo, restaurant.getRestaurantNo());
        } catch (AddException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

}