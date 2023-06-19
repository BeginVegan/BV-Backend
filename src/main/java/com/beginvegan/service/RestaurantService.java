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
import java.util.*;

@Slf4j
@Service("restaurantService")
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private S3Service s3Service;

    private static final String DEFAULT_PHOTO_DIR = "restaurant/default.png";
    private static final String S3_URL = "https://bv-image.s3.ap-northeast-2.amazonaws.com/";

    /**
     * 전체 식당을 조회한다.
     * @return 전체 식당 리스트
     * @throws FindException 조회에 실패한 경우 발생
     */
    public List<RestaurantDTO> findRestaurant() throws FindException {
        return restaurantRepository.selectAllRestaurant();
    }

    /**
     * 식당을 추가한다.
     * @param restaurantInfo 식당 정보
     * @throws AddException 추가에 실패한 경우 발생
     */
    public int addRestaurant(RestaurantDTO restaurantInfo, Optional<List<MultipartFile>> restaurantImages) throws AddException, IOException {
        if(restaurantImages.isPresent()) {
            s3Service.uploadMulti(restaurantImages.get(),"restaurant/" + restaurantRepository.selectNextRestaurantNo());
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
    public void modifyRestaurant(RestaurantDTO restaurantInfo, Optional<List<MultipartFile>> restaurantImages) throws ModifyException, RemoveException, AddException, IOException {
        if(restaurantImages.isPresent()) {
            s3Service.removeFolderS3(restaurantInfo.getRestaurantPhotoDir());
            s3Service.uploadMulti(restaurantImages.get(), restaurantInfo.getRestaurantPhotoDir());
        }
        restaurantRepository.updateRestaurant(restaurantInfo);
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

        bestMap.put("star", applyPhotoDIr(restaurantRepository.selectBestStarRestaurant()));
        bestMap.put("review", applyPhotoDIr(restaurantRepository.selectBestReviewRestaurant()));
        bestMap.put("reservation", applyPhotoDIr(restaurantRepository.selectBestReservationRestaurant()));

        return bestMap;
    }

    /**
     * restaurantPhotoDir에 S3 url을 붙여준다.
     * @param restaurantList url을 적용할 식당 리스트
     * @return S3 url이 적용된 식당 리스트
     */
    public List<RestaurantDTO> applyPhotoDIr(List<RestaurantDTO> restaurantList) {
        for (RestaurantDTO rest : restaurantList) {
            String fileName = DEFAULT_PHOTO_DIR;
            if (rest.getRestaurantPhotoDir() != null) {
                fileName = s3Service.getS3(rest.getRestaurantPhotoDir()).get(0);
            }
            rest.setRestaurantPhotoDir(S3_URL + fileName);
        }
        return restaurantList;
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
        // 모든 공백을 띄어쓰기 한칸으로 변경
        String dbKeyword = keyword.strip().replaceAll("\\s+"," ");
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("entireKeyword", dbKeyword);
        String[] keywords = dbKeyword.split(" ");
        searchMap.put("keywords", keywords);

        return applyPhotoDIr(restaurantRepository.selectAllRestaurantByKeyword(searchMap));
    }

    /**
     * 예약 가능한 시간을 모두 조회한다.
     * @param restaurantNo 식당 번호
     * @return 예약 가능한 시간을 문자열로 변환한 문자열 리스트
     * @throws FindException 데이터 조회에 실패할 경우 발생
     */
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
