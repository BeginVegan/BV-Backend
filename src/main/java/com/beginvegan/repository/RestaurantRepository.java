package com.beginvegan.repository;

import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.FindException;

import java.util.List;

public interface RestaurantRepository {
    /**
     * 전체 레스토랑을 조회한다.
     * @return 레스토랑의 리스트
     */
    public List<RestaurantDTO> selectAllRestaurant() throws FindException;
}
