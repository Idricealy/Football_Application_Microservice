package com.footballApplication.TeamService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableEurekaClient
public class TeamServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamServiceApplication.class, args);
	}

}
