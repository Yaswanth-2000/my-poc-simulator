	package com.hdfc.simulator.HDFCSimulator.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ResponsePojo {
	
	private String data;
	private String message;
	private int responseCode;
	private Object responseData;
}
