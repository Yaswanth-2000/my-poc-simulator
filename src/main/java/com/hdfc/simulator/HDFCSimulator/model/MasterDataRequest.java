package com.hdfc.simulator.HDFCSimulator.model;

import lombok.Data;

@Data
public class MasterDataRequest {

	private City city;
	private MaritalStatus maritalStatus;
	private RrelationShip relationShip;
	private AnnualIncome annualIncome;
	private OccupationType occupationType;
	private EmployerType employerType;
	private SelfEmployerType selfEmployerType;
	private OrganizationType organizationType;
	private NatureOfBusiness natureOfBusiness;
	private Product product;
	private Branch branch;
	private Email email;
	private ResidenceType residenceType;
	private FirmType firmType;
	private SelfEmployedProcessional selfEmployedProcessional;
}
