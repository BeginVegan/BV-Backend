package com.beginvegan.service;

import com.beginvegan.dto.*;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.repository.MenuRepository;
import com.beginvegan.repository.ReservationRepository;
import com.beginvegan.repository.RestaurantRepository;
import com.beginvegan.repository.ReviewRepository;
import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;


@Slf4j
@SpringBootTest
@Transactional
public class RestaurantServiceTest {

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantService restaurantService;


//    private int RESTAURANT_NO;
//    private MockMultipartFile RESTAURANT_INFO = null;
//    private MockMultipartFile RESTAURANT_IMAGE = null;
//
//    private List<MultipartFile> imageList = new ArrayList<>();
//    RestaurantDTO restaurant = new RestaurantDTO();


    @BeforeEach
    void setUp() throws JsonProcessingException, AddException {
        // @Mock 이 붙은 필드를 Mock 객체로 초기화하고, @InjectMocks 이 붙은 필드에는 객체를 주입해준다.
//        MockitoAnnotations.openMocks(this);

//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());

        // 테스트용 레스토랑 생성
//        RestaurantDTO restaurant = new RestaurantDTO();
//        restaurant.setRestaurantName("테스트코드");
//        restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
//        restaurant.setRestaurantAddressGu("종로구");
//        restaurant.setRestaurantX(37.570034);
//        restaurant.setRestaurantY(126.976785);
//        restaurant.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
//        restaurant.setRestaurantClose(TimeUtil.toTime("21:30:00"));
//        restaurant.setRestaurantPhone("02-2323-1212");
//        restaurant.setRestaurantDetail("상세정보1");
//        restaurant.setRestaurantAvgPrice(10000);
//        restaurant.setRestaurantTable(10);
//        restaurant.setRestaurantTableMember(4);
//        restaurant.setRestaurantVeganLevel(2);
////        restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
//        restaurant.setRestaurantStar(4.5);
//        restaurantRepository.insertRestaurant(restaurant);
//        RESTAURANT_NO = restaurant.getRestaurantNo();


//        String jsonContent = objectMapper.writeValueAsString(restaurant);
//        MockMultipartFile restaurantInfo = new MockMultipartFile("restaurant", "restaurant.jpg", "application/json", jsonContent.getBytes());
//
//        imageList.add(new MockMultipartFile("restaurantImage1", "restaurant1.jpg", "image/jpeg", "image data".getBytes()));
//        imageList.add(new MockMultipartFile("restaurantImage2", "restaurant2.jpg", "image/jpeg", "image data".getBytes()));
//        imageList.add(new MockMultipartFile("restaurantImage3", "restaurant3.jpg", "image/jpeg", "image data".getBytes()));
////
//        RESTAURANT_INFO = restaurantInfo;
//        RESTAURANT_IMAGE = restaurantImage;
    }

//    @BeforeAll
//    void setTestData() throws JsonProcessingException {
//
//
//
//
//    }

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

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

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

//        String jsonContent = objectMapper.writeValueAsString(restaurant);
//        MockMultipartFile restaurantInfo = new MockMultipartFile("restaurant", "restaurant.jpg", "application/json", jsonContent.getBytes());

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(new MockMultipartFile("restaurantImage1", "restaurant1.jpg", "image/jpeg", "image data".getBytes()));
        imageList.add(new MockMultipartFile("restaurantImage2", "restaurant2.jpg", "image/jpeg", "image data".getBytes()));
        imageList.add(new MockMultipartFile("restaurantImage3", "restaurant3.jpg", "image/jpeg", "image data".getBytes()));

        // Act
        restaurantService.addRestaurant(restaurant, Optional.ofNullable(imageList));

        // Assert
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

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(new MockMultipartFile("restaurantImage1", "restaurant1.jpg", "image/jpeg", "image data".getBytes()));
        imageList.add(new MockMultipartFile("restaurantImage2", "restaurant2.jpg", "image/jpeg", "image data".getBytes()));
        imageList.add(new MockMultipartFile("restaurantImage3", "restaurant3.jpg", "image/jpeg", "image data".getBytes()));


        // Act
        restaurantService.modifyRestaurant(restaurant, Optional.ofNullable(imageList));

        // Assert
        verify(restaurantRepository, times(1)).updateRestaurant(restaurant);
//        verify(restaurantRepository, times(1)).deleteRestaurantMenu(restaurant.getRestaurantNo());
//        verify(restaurantRepository, times(1)).insertRestaurantMenu(restaurant.getRestaurantNo(), restaurant.getMenuList());
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
    void testApplyPhotoDir() {
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
        restaurant.setRestaurantStar(4.5);

        List<RestaurantDTO> restaurantList = new ArrayList<>();
        restaurantList.add(restaurant);

        restaurantService.applyPhotoDIr(restaurantList);
    }

    @Test
    void testFindRestaurantByRestaurantNo() throws Exception {
        // Arrange
        RestaurantDTO restaurant = new RestaurantDTO();
        restaurant.setRestaurantNo(1);
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

        RestaurantDTO restaurant1 = new RestaurantDTO();
        restaurant.setRestaurantNo(2);
        restaurant1.setRestaurantName("레스토랑1");
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

        List<MultipartFile> imageList = new ArrayList<>();
        imageList.add(new MockMultipartFile("restaurantImage1", "restaurant1.jpg", "image/jpeg", "image data".getBytes()));
        imageList.add(new MockMultipartFile("restaurantImage2", "restaurant2.jpg", "image/jpeg", "image data".getBytes()));
        imageList.add(new MockMultipartFile("restaurantImage3", "restaurant3.jpg", "image/jpeg", "image data".getBytes()));

        List<ReviewDTO> reviewList = new ArrayList<>();
        ReviewDTO review = new ReviewDTO();
        review.setMemberEmail("test@naver.com");
        review.setReviewTime(TimeUtil.toDateTime("2023-05-25 00:00:00"));
        review.setReviewPhotoDir(null);
        review.setReviewContent("테스트용 리뷰 내용");
        review.setReservationNo(1);
        review.setRestaurantNo(restaurant.getRestaurantNo());
        review.setReviewStar(4);
        reviewList.add(review);

        when(restaurantRepository.selectRestaurantMenuByRestaurantNo(restaurant1.getRestaurantNo())).thenReturn(restaurant);
        when(reviewRepository.selectAllReviewByRestaurantId(restaurant1.getRestaurantNo())).thenThrow(FindException.class);
//        Assertions.assertThrows(FindException.class, () -> {restaurantService.findRestaurantByRestaurantNo(restaurant1.getRestaurantNo());});

        when(restaurantRepository.selectRestaurantMenuByRestaurantNo(restaurant.getRestaurantNo())).thenReturn(restaurant);
        when(reviewRepository.selectAllReviewByRestaurantId(restaurant.getRestaurantNo())).thenReturn(reviewList);
        restaurantService.findRestaurantByRestaurantNo(restaurant.getRestaurantNo());
        verify(restaurantRepository, times(1)).selectRestaurantMenuByRestaurantNo(restaurant.getRestaurantNo());
        verify(reviewRepository, times(1)).selectAllReviewByRestaurantId(restaurant.getRestaurantNo());

//        Assertions.assertThrows(FindException.class, () -> {restaurantService.findRestaurantByRestaurantNo(99999);});
    }

//    @Test
//    void testFindRestaurantByKeyword() throws Exception {
//        // Arrange
//        RestaurantDTO restaurant = new RestaurantDTO();
//        restaurant.setRestaurantName("레스토랑1");
//        restaurant.setRestaurantAddress("서울특별시 종로구 종로 1");
//        restaurant.setRestaurantAddressGu("종로구");
//        restaurant.setRestaurantX(37.570034);
//        restaurant.setRestaurantY(126.976785);
//        restaurant.setRestaurantOpen(TimeUtil.toTime("10:00:00"));
//        restaurant.setRestaurantClose(TimeUtil.toTime("21:30:00"));
//        restaurant.setRestaurantPhone("02-2323-1212");
//        restaurant.setRestaurantDetail("상세정보1");
//        restaurant.setRestaurantAvgPrice(10000);
//        restaurant.setRestaurantTable(10);
//        restaurant.setRestaurantTableMember(4);
//        restaurant.setRestaurantVeganLevel(2);
//        restaurant.setRestaurantPhotoDir("https://bv-image.s3.ap-northeast-2.amazonaws.com/restaurant/restaurant.jpg");
//        restaurant.setRestaurantStar(4.5);
//
//        String keyword = "강남 레스토랑".strip().replaceAll("\\s+"," ");
//        Map<String, Object> searchMap = new HashMap<>();
//        searchMap.put("entireKeyword", keyword);
//        String[] keywords = keyword.split(" ");
//        searchMap.put("keywords", keywords);
//
//        List<RestaurantDTO> restaurantList = new ArrayList<>();
//        restaurantList.add(restaurant);
//
//        when(restaurantRepository.selectAllRestaurantByKeyword(searchMap)).thenReturn(restaurantList);
//
//        // Act
//        restaurantService.findRestaurantByKeyword(keyword);
//
//        // Assert
//        verify(restaurantRepository, times(1)).selectAllRestaurantByKeyword(searchMap);
//    }

    @Test
    void findAllAvailableReservationByRestaurantNo() throws Exception {
        int restaurantNo = 1;

        // Act
        restaurantService.findAllAvailableReservationByRestaurantNo(restaurantNo);

        // Assert
        verify(restaurantRepository, times(1)).selectAllReservationByRestaurantNo(restaurantNo);
    }

}
