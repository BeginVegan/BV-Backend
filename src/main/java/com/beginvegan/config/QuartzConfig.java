package com.beginvegan.config;

import com.beginvegan.job.BestViewJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
public class QuartzConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setApplicationContextSchedulerContextKey("applicationContext");
        scheduler.setJobDetails(jobDetail());
        scheduler.setTriggers(trigger(jobDetail()));
        return scheduler;
    }

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(BestViewJob.class)
                .withIdentity("BestViewJob", Scheduler.DEFAULT_GROUP)
                .storeDurably(true)
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .withIdentity("BestViewJob", Scheduler.DEFAULT_GROUP)
                .withSchedule(cronSchedule("0 0 0 * * ?"))
                .forJob("BestViewJob")
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.YEAR))
                .build();
    }
}
