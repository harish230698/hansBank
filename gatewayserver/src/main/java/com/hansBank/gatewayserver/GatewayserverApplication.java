package com.hansBank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator hansBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){

		return routeLocatorBuilder.routes()
				.route(p->
						p.path("/hansBank/accounts/**")
						.filters(f->f.rewritePath("/hansBank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("x-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p->
						p.path("/hansBank/loans/**")
						.filters(f->f.rewritePath("/hansBank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("x-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p->
						p.path("/hansBank/cards/**")
						.filters(f->f.rewritePath("/hansBank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("x-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS")).build();
	}

}
