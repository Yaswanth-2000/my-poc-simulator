package com.hdfc.simulator.HDFCSimulator.model;

import lombok.Data;

@Data
public class AadharDetails {

	private Byte aadharPhoto;
	private String dateOfBirth;
	private String gender;
	private Boolean isAadharVerified;
	private Boolean isDedupeVerified;
	private String name;
//	private boolean UIDAIValidation ;

}
