package com.beginvegan.service;

import com.beginvegan.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service("restaurantService")
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

}
