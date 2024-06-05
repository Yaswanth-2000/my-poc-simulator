package com.hdfc.simulator.HDFCSimulator.samplemodel;

import java.util.Map;

import com.hdfc.simulator.HDFCSimulator.model.Otp;

import lombok.Data;

@Data
public class CustomerDetails {
		
//		private String pan;
//		private String name;
//		private String mobileNumber;
//		private Boolean loginSuspended;
//		private Boolean suspensionExpired;
//		private Map<String, Otp> otpDetails;
	
	private boolean customerStatus;
	private String applicationId;
	private String customerType;

	}

