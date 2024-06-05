package com.hdfc.simulator.HDFCSimulator.model;

import lombok.Data;

@Data
public class AccountStatusRequest {

	private String customerId;
	private String journey;
	private String journeyId;
//	private String taskId;
}
