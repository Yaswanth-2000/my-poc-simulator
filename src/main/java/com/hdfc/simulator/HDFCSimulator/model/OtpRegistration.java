package com.hdfc.simulator.HDFCSimulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class OtpRegistration {

	private Long otp;
	@JsonProperty("otp_transaction_id")
	private String otpTransactionId;
	private String journeyId;
//	private String taskId;
	private int noOfAttemptsRemaining;
	
	private String otpCreatedAt;
}
