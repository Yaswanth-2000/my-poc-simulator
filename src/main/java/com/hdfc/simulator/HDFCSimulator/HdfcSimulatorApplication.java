package com.hdfc.simulator.HDFCSimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hdfc.simulator.HDFCSimulator.controller.AadhaarDedupeController;
import com.hdfc.simulator.HDFCSimulator.controller.CustomerOtpController;
import com.hdfc.simulator.HDFCSimulator.controller.FixedDepositController;
import com.hdfc.simulator.HDFCSimulator.controller.PaymentController;
import com.hdfc.simulator.HDFCSimulator.util.Utility;

import ch.qos.logback.classic.pattern.Util;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackageClasses = {AadhaarDedupeController.class, CustomerOtpController.class, FixedDepositController.class, Utility.class, PaymentController.class})
//@ComponentScan(basePackages = {"com.hdfc.simulator.HDFCSimulator.*"})
public class HdfcSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HdfcSimulatorApplication.class, args);
	}
	
	@Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.modules(new JavaTimeModule());
        return builder;
    }
	
	

}
