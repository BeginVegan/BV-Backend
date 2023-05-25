package com.beginvegan.controller;

import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.FindException;
import com.beginvegan.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("restaurant/*")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    /**
     * 모든 레스토랑 리스트를 가져온다.
     * @param session 세션정보
     * @return 레스토랑의 리스트와 상태정보
     * @throws FindException Restaurant 테이블에 데이터가 없을 때 발생하는 Exception
     */
    @GetMapping("list")
    public ResponseEntity<?> restaurantList(HttpSession session) throws FindException {
        List<RestaurantDTO> restaurantList = restaurantService.findRestaurant();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }
}
