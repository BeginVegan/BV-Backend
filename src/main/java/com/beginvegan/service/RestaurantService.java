package com.beginvegan.service;

import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.FindException;
import com.beginvegan.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("restaurantService")
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * 전체 레스토랑을 조회한다.
     * @return 전체 레스토랑 리스트
     * @throws FindException
     */
    public List<RestaurantDTO> findRestaurant() throws FindException {
        return restaurantRepository.selectAllRestaurant();
    }

}
