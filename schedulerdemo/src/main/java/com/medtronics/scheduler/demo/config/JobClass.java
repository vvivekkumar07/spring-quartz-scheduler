package com.medtronics.scheduler.demo.config;

import org.springframework.beans.factory.annotation.Autowired;

import com.medtronics.scheduler.demo.service.ServiceClass;

public class JobClass {

	@Autowired
	private ServiceClass service;

	public void run(String abc, long time) {
		System.out.println("job is running " + abc + "long value" + time);
		service.service(abc, time);
	}

}
