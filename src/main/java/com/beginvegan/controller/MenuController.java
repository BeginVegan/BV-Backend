package com.beginvegan.controller;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @PostMapping
    public ResponseEntity<?> MenuAdd(@RequestPart(value = "menuDTO") MenuDTO menuInfo, @RequestPart(value = "menuImage", required = false) Optional<MultipartFile> menuImage) throws AddException, IOException {
        if (menuImage.isPresent()) {
            MultipartFile present = menuImage.get();
            return new ResponseEntity<>(menuService.addMenu(menuInfo, present), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(menuService.addMenu(menuInfo), HttpStatus.OK);
        }
    }

    @PostMapping("list")
    public ResponseEntity<?> MenuListAdd(@RequestBody List<MenuDTO> menuList) throws AddException {
        menuService.addAllMenu(menuList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{menuNo}")
    public ResponseEntity<?> menuOne(@PathVariable Integer menuNo) throws FindException {
        Map<String, Object> menuMap = new HashMap<>();
        menuMap.put("menuInfo", menuService.findMenuByMenuNo(menuNo));
        return new ResponseEntity<>(menuMap, HttpStatus.OK);
    }

    @GetMapping("list/{restaurantNo}")
    public ResponseEntity<?> menuList(@PathVariable Integer restaurantNo) throws FindException {
        Map<String, Object> menuMap = new HashMap<>();
        menuMap.put("menuList", menuService.findAllMenuByRestaurantNo(restaurantNo));
        return new ResponseEntity<>(menuMap, HttpStatus.OK);
    }

    @DeleteMapping("{menuNo}")
    public ResponseEntity<?> menuRemove(@PathVariable Integer menuNo) throws RemoveException {
        menuService.removeMenuByMenuNo(menuNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("list/{restaurantNo}")
    public ResponseEntity<?> menuListRemove(@PathVariable Integer restaurantNo) throws RemoveException {
        menuService.removeAllMenuByRestaurantNo(restaurantNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
