package com.medtronics.scheduler.demo.config;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class JobConfig {
	public void scheduleJob() throws ClassNotFoundException, NoSuchMethodException {
		final long ONE_MINUTE_IN_MILLIS = 60000;
		// SchedulerFactory schedulerFactory = new StdSchedulerFactory();

		Scheduler scheduler = (Scheduler) ApplicationContextProvider.getApplicationContext()
				.getBean("schedulerFactoryBean");
		JobClass jobClass = (JobClass) ApplicationContextProvider.getApplicationContext().getBean("jobClass");
		System.out.println("scheduler bean" + scheduler);
		MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
		jobDetail.setTargetObject(jobClass);
		jobDetail.setTargetMethod("run");
		jobDetail.setArguments("abc", 123L);
		jobDetail.setName("MyJobDetail");
		jobDetail.setConcurrent(false);
		jobDetail.afterPropertiesSet();
		Date jobStartTime = new Date(new Date().getTime() + (1 * ONE_MINUTE_IN_MILLIS));
		// JobDetail jobDetail1= newJob(SimpleJob.class).withIdentity("job" + 1, "group"
		// + 1).build();
		System.out.println("job will start at " + jobStartTime);
		SimpleTrigger jobTrigger = newTrigger().withIdentity("trigger" + 1, "group" + 1).startAt(jobStartTime)
				.withSchedule(simpleSchedule()).build();
		try {
			System.out.println("scheduling job");
			scheduler.scheduleJob((JobDetail) jobDetail.getObject(), jobTrigger);
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
