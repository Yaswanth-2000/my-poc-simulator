package com.hdfc.simulator.HDFCSimulator.model;

import lombok.Data;

@Data
public class Otp {

	private long otp;
	private int retryCount;
	private boolean otpValidate;
	private String otpTransactionId;
}
