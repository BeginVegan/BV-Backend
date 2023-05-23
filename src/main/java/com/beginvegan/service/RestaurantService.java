package com.beginvegan.service;

import com.beginvegan.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("restaurantService")
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
}
