package com.beginvegan.job;

import com.beginvegan.exception.ModifyException;
import com.beginvegan.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StarJob implements Job {

    /**
     * 리뷰 평점을 집계하여 식당의 별점을 갱신한다.
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ApplicationContext applicationContext = null;
        try {
            applicationContext = (ApplicationContext)jobExecutionContext.getScheduler().getContext().get("applicationContext");
            RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);
            restaurantRepository.updateRestaurantStar();
        } catch (SchedulerException | ModifyException e) {
            throw new RuntimeException(e);
        }
    }
}
