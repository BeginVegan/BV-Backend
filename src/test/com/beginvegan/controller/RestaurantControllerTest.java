package com.beginvegan.controller;

import com.beginvegan.dto.MemberDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.dto.ReviewDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.MemberRepository;
import com.beginvegan.repository.RestaurantRepository;
import com.beginvegan.service.MemberService;
import com.beginvegan.service.RestaurantService;
import com.beginvegan.service.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {
    @InjectMocks
    private RestaurantController restaurantController;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("restaurantList 테스트")
    @Test
    void restaurantListTest() throws FindException {
        // Arrange
        RestaurantDTO restaurantInfo = new RestaurantDTO();
        restaurantInfo.setRestaurantName("restaurantTest");

        List<RestaurantDTO> restaurantList = new ArrayList<>();
        restaurantList.add(restaurantInfo);

        when(restaurantService.findRestaurant()).thenReturn(restaurantList);

        // Act
        ResponseEntity<?> response = restaurantController.restaurantList();

        // Assert
        verify(restaurantService).findRestaurant();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantList, response.getBody());
    }

    @DisplayName("restaurantDetails 테스트")
    @Test
    void restaurantDetailsTest() throws FindException {
        // Arrange
        int restaurantNo = 1;

        Map<String, Object> restaurantInfo = new HashMap<>();
        RestaurantDTO restaurant = new RestaurantDTO();
        Map<String, Object> restaurantReviewMap = new HashMap<>();

        restaurantInfo.put("restaurant", restaurant);
        restaurantInfo.put("review", restaurantReviewMap);

        when(restaurantService.findRestaurantByRestaurantNo(restaurantNo)).thenReturn(restaurantInfo);

        // Act
        ResponseEntity<?> response = restaurantController.restaurantDetails(restaurantNo);

        // Assert
        verify(restaurantService).findRestaurantByRestaurantNo(restaurantNo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantInfo, response.getBody());
    }

    @DisplayName("getRestaurantBest 테스트")
    @Test
    void getRestaurantBestTest() throws FindException {
        // Arrange
        Map<String, Object> bestRestaurantInfo = new HashMap<>();

        when(restaurantService.findBestRestaurant()).thenReturn(bestRestaurantInfo);

        // Act
        ResponseEntity<?> response = restaurantController.getRestaurantBest();

        // Assert
        verify(restaurantService).findBestRestaurant();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bestRestaurantInfo, response.getBody());
    }

    @DisplayName("restaurantAdd 테스트")
    @Test
    void restaurantAddTest() throws IOException, AddException {
        // Arrange
        int resNo = 1;
        RestaurantDTO restaurantInfo = new RestaurantDTO();
        restaurantInfo.setRestaurantName("restaurantTest");
        List<MultipartFile> restaurantImages = new ArrayList<>();

        when(restaurantService.addRestaurant(restaurantInfo, Optional.of(restaurantImages))).thenReturn(resNo);

        // Act
        ResponseEntity<?> response = restaurantController.restaurantAdd(restaurantInfo, Optional.of(restaurantImages));

        // Assert
        verify(restaurantService).addRestaurant(restaurantInfo, Optional.of(restaurantImages));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resNo, response.getBody());
    }

    @DisplayName("restaurantModify 테스트")
    @Test
    void restaurantModifyTest() throws RemoveException, ModifyException, IOException, AddException {
        // Arrange
        int resNo = 1;
        RestaurantDTO restaurantInfo = new RestaurantDTO();
        restaurantInfo.setRestaurantName("restaurantTest");
        List<MultipartFile> restaurantImages = new ArrayList<>();

        // Act
        ResponseEntity<?> response = restaurantController.restaurantModify(restaurantInfo, Optional.of(restaurantImages));

        // Assert
        verify(restaurantService).modifyRestaurant(restaurantInfo, Optional.of(restaurantImages));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("restaurantRemove 테스트")
    @Test
    void restaurantRemoveTest() throws RemoveException {
        // Arrange
        int resNo = 1;
        // Act
        ResponseEntity<?> response = restaurantController.restaurantRemove(resNo);

        // Assert
        verify(restaurantService).removeRestaurant(resNo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("restaurantSearchList 테스트")
    @Test
    void restaurantSearchListTest() throws FindException {
        // Arrange
        String keyword = "testKeyword";
        List<RestaurantDTO> restaurantList = new ArrayList<>();

        when(restaurantService.findRestaurantByKeyword(keyword)).thenReturn(restaurantList);

        // Act
        ResponseEntity<?> response = restaurantController.restaurantSearchList(keyword);

        // Assert
        verify(restaurantService).findRestaurantByKeyword(keyword);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantList, response.getBody());
    }

    @DisplayName("reservationList 테스트")
    @Test
    void reservationListTest() throws FindException {
        // Arrange
        int resNo = 1;
        List<String> availableTimeList = new ArrayList<>();

        when(restaurantService.findAllAvailableReservationByRestaurantNo(resNo)).thenReturn(availableTimeList);

        // Act
        ResponseEntity<?> response = restaurantController.reservationList(resNo);

        // Assert
        verify(restaurantService).findAllAvailableReservationByRestaurantNo(resNo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availableTimeList, response.getBody());
    }

    @DisplayName("imageList 테스트")
    @Test
    void imageListTest() {
        // Arrange
        String dirName = "test/image.png";
        List<String> imageList = new ArrayList<>();

        when(s3Service.getRestaurantImages("restaurant/" + dirName)).thenReturn(imageList);

        // Act
        ResponseEntity<?> response = restaurantController.imageList(dirName);

        // Assert
        verify(s3Service).getRestaurantImages("restaurant/" + dirName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imageList, response.getBody());
    }
}