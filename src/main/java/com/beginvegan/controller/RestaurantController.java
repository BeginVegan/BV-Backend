package com.beginvegan.controller;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("restaurant")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    /**
     * 모든 레스토랑 리스트를 가져온다.
     * @return 레스토랑의 리스트와 상태정보
     * @throws FindException Restaurant 테이블에 데이터가 없을 때 발생하는 Exception
     */
    @GetMapping(value = "list")
    public ResponseEntity<?> restaurantList() throws FindException {
//        private int menuNo; // menu_no
//        private int restaurantNo; // restaurant_no
//        private String menuName; // menu_name
//        private int menuPrice; // menu_price
//        private String menuCategory; // menu_category
//        private String menuDetail; // menu_detail
//        private String menuPhotoDir; // menu_photo_dir
        MenuDTO menu = new MenuDTO();
        menu.setRestaurantNo(1234);
        menu.setMenuCategory("category");
        menu.setMenuName("name");
        menu.setMenuNo(123);
        menu.setMenuPrice(500);
        menu.setMenuPhotoDir("dir");
        menu.setMenuDetail("detail");

        ArrayList<MenuDTO> list = new ArrayList<>();
        list.add(menu);
        list.add(menu);
        list.add(menu);

        List<RestaurantDTO> restaurantList = restaurantService.findRestaurant();
        restaurantList = restaurantList.subList(0, 3);

        restaurantList.get(0).setMenuList(list);
        restaurantList.get(1).setMenuList(list);
        restaurantList.get(2).setMenuList(list);

        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    /**
     * 메뉴가 포함된 식당의 정보와 댓글을 가져온다.
     * @param restaurantNo 조회할 식당 번호
     * @return 식당의 상세정보와 댓글
     * @throws FindException 조회에 실패할 경우 발생
     */
    @GetMapping("{restaurantNo}")
    public ResponseEntity<?> restaurantDetails(@PathVariable Integer restaurantNo) throws FindException {
        Map<String, Object> restaurantInfo = restaurantService.findRestaurantByRestaurantNo(restaurantNo);
        return new ResponseEntity<>(restaurantInfo, HttpStatus.OK);
    }

    @GetMapping("best")
    public ResponseEntity<?> getRestaurantBest() throws FindException {
        Map<String, Object> bestRestaurant = restaurantService.findBestRestaurant();
        return new ResponseEntity<>(bestRestaurant, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> restaurantAdd(@RequestBody RestaurantDTO restaurantInfo) throws AddException {
        restaurantService.addRestaurant(restaurantInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> restaurantModify(@RequestBody RestaurantDTO restaurantInfo) throws ModifyException, AddException, RemoveException {
        restaurantService.modifyRestaurant(restaurantInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{restaurantNo}")
    public ResponseEntity<?> restaurantRemove(@PathVariable Integer restaurantNo) throws RemoveException {
        restaurantService.removeRestaurant(restaurantNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
