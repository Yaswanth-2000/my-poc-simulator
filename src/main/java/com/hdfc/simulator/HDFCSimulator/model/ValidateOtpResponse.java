package com.hdfc.simulator.HDFCSimulator.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ValidateOtpResponse {
	
	private String applicationId;
	private String accessToken;
	private String customerId;
	private String language;
	private String name;
	private String journeyId;
	private String taskId;
	private int retries;
	private boolean isValid;
	private String errorStatus;
}
