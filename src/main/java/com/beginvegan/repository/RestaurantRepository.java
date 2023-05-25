package com.beginvegan.repository;

import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.FindException;

import java.util.List;

public interface RestaurantRepository {
    /**
     * 전체 레스토랑을 조회한다.
     * @return 레스토랑의 리스트
     * @throws FindException Restaurant 테이블에 데이터가 없을 때 발생하는 Exception
     */
    public List<RestaurantDTO> selectAllRestaurant() throws FindException;
}
