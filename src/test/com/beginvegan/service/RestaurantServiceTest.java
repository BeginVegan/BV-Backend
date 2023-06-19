package com.beginvegan.service;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.FindException;
import com.beginvegan.repository.RestaurantRepository;
import com.beginvegan.repository.ReviewRepository;
import com.beginvegan.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;


@Slf4j
@SpringBootTest
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindRestaurant() throws Exception {
        // Act
        restaurantService.findRestaurant();

        // Assert
        verify(restaurantRepository, times(1)).selectAllRestaurant();
    }

    @Test
    void testAddRestaurant() throws Exception {
        // Arrange
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

        // Act
//        restaurantService.addRestaurant(restaurant);

        // Assert
//        verify(s3Service, times(1)).uploadMulti();
        verify(restaurantRepository, times(1)).insertRestaurant(restaurant);

    }

    @Test
    void testModifyRestaurant() throws Exception {
        // Arrange
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

        List<MenuDTO> menuList = new ArrayList();
        menuList.add(menu1);
        menuList.add(menu2);

        // Act
        restaurantService.modifyRestaurant(restaurant);

        // Assert
        verify(restaurantRepository, times(1)).updateRestaurant(restaurant);
        verify(restaurantRepository, times(1)).deleteRestaurantMenu(restaurant.getRestaurantNo());
        verify(restaurantRepository, times(1)).insertRestaurantMenu(restaurant.getRestaurantNo(), restaurant.getMenuList());
    }

    @Test
    void testRemoveRestaurant() throws Exception {
        // Arrange
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

        // Act
        restaurantService.removeRestaurant(restaurant.getRestaurantNo());

        // Assert
        verify(restaurantRepository, times(1)).deleteRestaurant(restaurant.getRestaurantNo());
    }

    @Test
    void testFindBestRestaurant() throws Exception {
        // Act
        restaurantService.findBestRestaurant();

        // Assert
        verify(restaurantRepository, times(1)).selectBestStarRestaurant();
        verify(restaurantRepository, times(1)).selectBestReviewRestaurant();
        verify(restaurantRepository, times(1)).selectBestReservationRestaurant();
    }

    @Test
    void testFindRestaurantByRestaurantNo() throws Exception {
        // Arrange
        String keyword = "강남 레스토랑".strip().replaceAll("\\s+"," ");
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("entireKeyword", keyword);
        String[] keywords = keyword.split(" ");
        searchMap.put("keywords", keywords);

        // Act
        restaurantService.findRestaurantByKeyword(keyword);

        // Assert
        verify(restaurantRepository, times(1)).selectAllRestaurantByKeyword(searchMap);
    }

    @Test
    void findAllAvailableReservationByRestaurantNo() throws Exception {
        int restaurantNo = 1;

        // Act
        restaurantService.findAllAvailableReservationByRestaurantNo(restaurantNo);

        // Assert
        verify(restaurantRepository, times(1)).selectAllReservationByRestaurantNo(restaurantNo);
    }

}
