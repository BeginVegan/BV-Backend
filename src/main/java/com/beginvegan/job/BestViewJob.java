package com.beginvegan.job;

import com.beginvegan.exception.CreateException;
import com.beginvegan.repository.RestaurantRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BestViewJob implements Job {

    /**
     * 베스트 식당에 대한 view를 생성한다.
     * @param context
     * @throws JobExecutionException
     * @description Quartz로 수행할 작업을 정의하기 위해 Job인터페이스의 execute() 함수를 override했다.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ApplicationContext applicationContext = null;
        try {
            applicationContext = (ApplicationContext)context.getScheduler().getContext().get("applicationContext");
            RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);
            restaurantRepository.createBestStarView();
            restaurantRepository.createBestReviewView();
            restaurantRepository.createBestReservationView();
        } catch (SchedulerException | CreateException e) {
            throw new RuntimeException(e);
        }
    }
}
