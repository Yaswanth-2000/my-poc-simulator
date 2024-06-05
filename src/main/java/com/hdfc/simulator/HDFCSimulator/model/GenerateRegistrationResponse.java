package com.hdfc.simulator.HDFCSimulator.model;

import lombok.Data;

@Data
public class GenerateRegistrationResponse {
	
	private String numberOfAttemptsRemaining;
	private String otpTransactionId;
	private String journeyId;
	private String taskId;
}
