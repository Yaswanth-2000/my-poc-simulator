package com.hdfc.simulator.HDFCSimulator.model;

import lombok.Data;

@Data
public class VkycResponse {

	private String captureLink;
	private String captureExpiresAt;
	private String journeyId;
	private String taskId;
}
