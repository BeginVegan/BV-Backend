package com.beginvegan.service;

import com.beginvegan.dto.MenuDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("restaurantService")
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * 전체 식당을 조회한다.
     * @return 전체 식당 리스트
     * @throws FindException 조회에 싫패한 경우 발생
     */
    public List<RestaurantDTO> findRestaurant() throws FindException {
        return restaurantRepository.selectAllRestaurant();
    }

    /**
     * 식당과 메뉴를 추가한다.
     * @param restaurantInfo 식당 정보
     * @throws AddException 추가에 실패한 경우 발생
     */
    @Transactional
    public void addRestaurant(RestaurantDTO restaurantInfo) throws AddException {
        restaurantRepository.insertRestaurant(restaurantInfo);
        restaurantRepository.insertRestaurantMenu(restaurantInfo.getRestaurantNo(), restaurantInfo.getMenuList());
    }

    /**
     * 식당과 메뉴를 수정한다.
     * @param restaurantInfo 수정할 식당 정보
     * @throws ModifyException 수정에 실패한 경우 발생
     */
    @Transactional
    public void modifyRestaurant(RestaurantDTO restaurantInfo) throws ModifyException, RemoveException, AddException {
        restaurantRepository.updateRestaurant(restaurantInfo);
        restaurantRepository.deleteRestaurantMenu(restaurantInfo.getRestaurantNo());
        restaurantRepository.insertRestaurantMenu(restaurantInfo.getRestaurantNo(), restaurantInfo.getMenuList());
    }

    /**
     * 식당을 삭제한다.
     * @param restaurantNo 삭제할 식당 번호
     * @throws RemoveException 삭제에 실패한 경우 발생
     */
    public void removeRestaurant(int restaurantNo) throws RemoveException {
        restaurantRepository.deleteRestaurant(restaurantNo);
    }

    /**
     * 평점, 리뷰, 예약 순위가 높은 식당을 반환한다.
     * @return 식당의 list가 들어있는 Map
     * @throws FindException 조회에 실패한 경우 발생
     */
    public Map<String, Object> findBestRestaurant() throws FindException {
        Map<String, Object> bestMap = new HashMap<>();
        bestMap.put("star", restaurantRepository.selectBestStarRestaurant());
        bestMap.put("review", restaurantRepository.selectBestReviewRestaurant());
        bestMap.put("reservation", restaurantRepository.selectBestReservationRestaurant());
        return bestMap;
    }

    /**
     * 식당의 상세정보를 반환한다.
     * @param restaurantNo 조회할 식당 번호
     * @return 식당의 상세정보와 댓글
     * @throws FindException 조회에 실패할 경우 발생
     */
    public Map<String, Object> findRestaurantByRestaurantNo(int restaurantNo) throws FindException {
        Map<String, Object> restaurantReviewMap = new HashMap<>();
        RestaurantDTO restaurant = restaurantRepository.selectRestaurantByRestaurantNo(restaurantNo);
        restaurant.setMenuList((ArrayList<MenuDTO>)restaurantRepository.selectAllMenuByRestaurantNo(restaurantNo));
        restaurantReviewMap.put("restaurant", restaurant);
        //restaurantReviewMap.put("review", reviewRepository.selectAllReviewByRestaurantNo(restaurant.getRestaurantNo());
        return restaurantReviewMap;
    }

}
