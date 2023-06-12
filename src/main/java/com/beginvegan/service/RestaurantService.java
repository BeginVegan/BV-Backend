package com.beginvegan.service;

import com.beginvegan.dto.ReservationDTO;
import com.beginvegan.dto.RestaurantDTO;
import com.beginvegan.exception.AddException;
import com.beginvegan.exception.FindException;
import com.beginvegan.exception.ModifyException;
import com.beginvegan.exception.RemoveException;
import com.beginvegan.repository.RestaurantRepository;
import com.beginvegan.repository.ReviewRepository;
import com.beginvegan.util.TimeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("restaurantService")
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private S3Service S3Service;

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
    public int addRestaurant(RestaurantDTO restaurantInfo, List<MultipartFile> restaurantImages) throws AddException, IOException {
        if(!restaurantImages.isEmpty()) {
            S3Service.uploadMulti(restaurantImages,"restaurant/" + restaurantRepository.selectNextRestaurantNo());
            restaurantInfo.setRestaurantPhotoDir("restaurant/" + restaurantRepository.selectNextRestaurantNo());
        }

        return restaurantRepository.insertRestaurant(restaurantInfo);
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
        RestaurantDTO restaurant = restaurantRepository.selectRestaurantMenuByRestaurantNo(restaurantNo);
        restaurantReviewMap.put("restaurant", restaurant);
        restaurantReviewMap.put("review", reviewRepository.selectAllReviewByRestaurantId(restaurant.getRestaurantNo()));
        return restaurantReviewMap;
    }

    /**
     * 이름/지역/메뉴가 검색어와 일치하는 식당을 반환한다.
     * @param keyword 검색할 문자열
     * @return 이름 or 지역 or 메뉴가 검색어와 일치하는 식당의 리스트
     * @throws FindException 조회에 실패할 경우 발생
     */
    public List<RestaurantDTO> findRestaurantByKeyword(String keyword) throws FindException {
        String dbKeyword = keyword.strip().replaceAll("\\s+"," ");
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("entireKeyword", dbKeyword);
        String[] keywords = keyword.split(" ");
        searchMap.put("keywords", keywords);
        return restaurantRepository.selectAllRestaurantByKeyword(searchMap);
    }

    public List<String> findAllAvailableReservationByRestaurantNo(int restaurantNo) throws FindException {
        LocalDateTime now = TimeUtil.dateTimeOfNow().withMinute(0).withSecond(0).withNano(0).plusHours(1);
        LocalDateTime nextMonth = now.plusMonths(1);
        List<LocalDateTime> availableTimeList = new ArrayList<>();
        List<ReservationDTO> reservationList = restaurantRepository.selectAllReservationByRestaurantNo(restaurantNo);

        // 현재 시각부터 다음달까지의 시간을 모두 구하여 리스트에 넣는다.
        while (now.isBefore(nextMonth)) {
            availableTimeList.add(now);
            now = now.plusHours(1);
        }

        // 예약시간만으로 구성된 리스트를 만든다.
        List<LocalDateTime> reservationTimeList = new ArrayList<>();
        for (ReservationDTO reservation : reservationList) {
            reservationTimeList.add(reservation.getReservationVisitTime());
        }

        // 전체 시간에서 예약된 시간을 제거한다.
        availableTimeList.removeAll(reservationTimeList);

        // String으로 변환한다.
        List<String> timeList = new ArrayList<>();
        for (LocalDateTime data : availableTimeList) {
            timeList.add(TimeUtil.localDateTimetoString(data));
        }

        return timeList;
    }

}
