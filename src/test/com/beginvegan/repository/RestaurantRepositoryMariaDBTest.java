package com.beginvegan.repository;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RestaurantRepositoryMariaDBTest {

    @Autowired
    private RestaurantRepositoryMariaDB restaurantRepository;

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
            int restaurantMaxNo = restaurantRepository.selectMaxRestaurantNoTest() + 1;
            int insertedRestaurantNo = restaurantRepository.insertRestaurant(restaurant);

            Assertions.assertEquals(restaurantMaxNo, insertedRestaurantNo);
        } catch (AddException | FindException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSelectRestaurantByRestaurantNo() throws ParseException {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
        Timestamp openTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 8, 00, 00))).getTime());
        Timestamp closeTime = new Timestamp(sdf.parse(sdf.format(new java.util.Date(1970, 01, 01, 22, 00, 00))).getTime());

        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantNo(1);
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
            RestaurantDTO restaurantInfo = restaurantRepository.selectRestaurantByRestaurantNo(restaurant.getRestaurantNo());

            Assertions.assertEquals(restaurant, restaurantInfo);
        } catch (FindException e) {
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

//            for (RestaurantDTO rest : restaurantList) {
//                System.out.println(rest.getRestaurantNo());
//            }

            Assertions.assertTrue(restaurantList.contains(restaurant1));
            Assertions.assertTrue(restaurantList.contains(restaurant2));

            restaurantRepository.deleteRestaurant(restaurant1.getRestaurantNo());
            restaurantRepository.deleteRestaurant(restaurant2.getRestaurantNo());
        } catch (FindException | AddException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }
//
//    @Test
//    public void testInsertRestaurantMenu() throws AddException {
//        // Mock 데이터 생성
//        int restaurantNo = 1;
//        List<MenuDTO> mockMenuList = new ArrayList<>();
//        mockMenuList.add(new MenuDTO("Menu 1", 10.99));
//        mockMenuList.add(new MenuDTO("Menu 2", 8.99));
//
//        try {
//            // 메서드 실행
//            restaurantRepository.insertRestaurantMenu(restaurantNo, mockMenuList);
//        } catch (AddException e) {
//            fail("Exception thrown: " + e.getMessage());
//        }
//    }
//
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
            int insertedRestaurantNo = restaurantRepository.insertRestaurant(restaurant);
            restaurantRepository.deleteRestaurant(insertedRestaurantNo);
            RestaurantDTO selectedRestaurant = restaurantRepository.selectRestaurantByRestaurantNo(insertedRestaurantNo);
            Assertions.assertNull(selectedRestaurant);
        } catch (FindException | AddException | RemoveException e) {
            Assertions.fail("Exception thrown: " + e.getMessage());
        }
    }

}