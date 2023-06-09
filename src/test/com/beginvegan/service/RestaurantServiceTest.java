package com.beginvegan.service;

import com.beginvegan.exception.FindException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;
    
    @Test
    void testFindAllAvailableReservationByRestaurantNo() throws FindException { //실행되는지만 보는거임.
        restaurantService.findAllAvailableReservationByRestaurantNo(368);
    }
}
