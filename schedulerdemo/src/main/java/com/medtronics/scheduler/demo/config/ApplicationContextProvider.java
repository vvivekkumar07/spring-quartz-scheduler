package com.medtronics.scheduler.demo.config;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;

	}

	@Bean(name = "schedulerFactory")
	public SchedulerFactory schedulerFactory() {
		return new StdSchedulerFactory();
	}

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
		System.out.println("creating job factory");
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		System.out.println("creating job factory");
		factory.setJobFactory(jobFactory);
		return factory;
	}

	@Bean
	public JobClass jobClass() {
		return new JobClass();
	}

}
