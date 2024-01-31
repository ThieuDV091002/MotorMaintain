package com.motormt.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EntityScan({"com.motormt.common.entity", "com.motormt.site.customer", "com.motormt.site.schedule", "com.motormt.site.security",
		"com.motormt.site.service", "com.motormt.site"})
@Component
@ComponentScan({"com.motormt.common.entity", "com.motormt.site.customer", "com.motormt.site.schedule", "com.motormt.site.security",
		"com.motormt.site.service", "com.motormt.site"})
public class MotorMaintainanceFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotorMaintainanceFrontEndApplication.class, args);
	}

}
