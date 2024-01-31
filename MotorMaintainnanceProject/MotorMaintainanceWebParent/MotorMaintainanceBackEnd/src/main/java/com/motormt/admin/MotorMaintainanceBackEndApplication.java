package com.motormt.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EntityScan({"com.motormt.admin", "com.motormt.common.entity", "com.motormt.admin.account", "com.motormt.admin.security", "com.motormt.admin.service",
		"com.motormt.admin.bill", "com.motormt.admin.cart", "com.motormt.admin.checkout", "com.motormt.admin.schedule"})
@Component
@ComponentScan(basePackages = {"com.motormt.admin", "com.motormt.common.entity", "com.motormt.admin.account", "com.motormt.admin.security", "com.motormt.admin.service",
		"com.motormt.admin.bill", "com.motormt.admin.cart", "com.motormt.admin.checkout", "com.motormt.admin.schedule"})
public class MotorMaintainanceBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotorMaintainanceBackEndApplication.class, args);
	}

}
