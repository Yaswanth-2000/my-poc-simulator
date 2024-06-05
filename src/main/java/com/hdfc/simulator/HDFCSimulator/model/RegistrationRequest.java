package com.hdfc.simulator.HDFCSimulator.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RegistrationRequest {

	private String pan;

	private String name;
	private String dob;
	private String mobileNumber;
	private Boolean loginSuspended;
	private Boolean suspensionExpired;
	private String type;
	private String journeyId;
	private String taskId;
	private String currentStep;
	private String applicationId;
	private String errorMessage; 

}
