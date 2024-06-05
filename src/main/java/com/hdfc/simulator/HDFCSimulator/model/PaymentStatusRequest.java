package com.hdfc.simulator.HDFCSimulator.model;

import lombok.Data;

@Data
public class PaymentStatusRequest {

	private String mode;
	private String transactionId;
	private String journeyId;
	private String taskId;
}
