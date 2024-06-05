package com.hdfc.simulator.HDFCSimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.simulator.HDFCSimulator.model.AadharDetails;
import com.hdfc.simulator.HDFCSimulator.model.AccountStatusRequest;
import com.hdfc.simulator.HDFCSimulator.model.AccountStatusResponse;
import com.hdfc.simulator.HDFCSimulator.model.AmlDetails;
import com.hdfc.simulator.HDFCSimulator.model.CommunicationAddress;
import com.hdfc.simulator.HDFCSimulator.model.CreateRequest;
import com.hdfc.simulator.HDFCSimulator.model.CscDetails;
import com.hdfc.simulator.HDFCSimulator.model.CustomerDetails;
import com.hdfc.simulator.HDFCSimulator.model.Nominee;
import com.hdfc.simulator.HDFCSimulator.model.NomineeDetails;
import com.hdfc.simulator.HDFCSimulator.model.PermenantAddress;
import com.hdfc.simulator.HDFCSimulator.model.PersonalDetails;
import com.hdfc.simulator.HDFCSimulator.model.RegistrationRequest;
import com.hdfc.simulator.HDFCSimulator.model.SelectedBranch;
import com.hdfc.simulator.HDFCSimulator.model.StatusENUM;
import com.hdfc.simulator.HDFCSimulator.util.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v2/ekyc")
@CrossOrigin
public class AadhaarDedupeController {
	@Autowired
	Utility utility;

//	@PostMapping("/aadhaarValidation")
	public AccountStatusResponse aadhaarValidation(AccountStatusRequest asr,
			String journeyId, String currentStep) {
		AccountStatusResponse accountStatusResponse;
		RegistrationRequest registrationRequest = utility.getCustomerDetails().get(journeyId);
		accountStatusResponse = new AccountStatusResponse();
		if(registrationRequest.getPan().equalsIgnoreCase("SKTPB1003A") || registrationRequest.getPan().equalsIgnoreCase("SKTPB1004A")
				|| registrationRequest.getMobileNumber().equalsIgnoreCase("+918117172401") || registrationRequest.getMobileNumber().equalsIgnoreCase("+918117172402")) {
			log.info("Fails because the Acount with Pan "+registrationRequest.getPan()+" or mobile number "+registrationRequest.getMobileNumber()+" is already created");
			return null;
		} else {
			if (utility.getAccountStatusResponse().get(asr.getCustomerId()) == null) {
				accountStatusResponse = getAadhaarDetails(asr.getCustomerId(), currentStep);
				PermenantAddress permanentDetails = setPermanentDetails();
				CustomerDetails customerDetails = new CustomerDetails();
				customerDetails.setPermanentAddress(permanentDetails);
				utility.getAccountStatusResponse().put(asr.getCustomerId(), accountStatusResponse);
			} else {
				accountStatusResponse = utility.getAccountStatusResponse().get(asr.getCustomerId());
				utility.getAccountStatusResponse().put(asr.getCustomerId(), accountStatusResponse);
			}
		}
		return accountStatusResponse;
 
	}
	
	public AccountStatusResponse aadhaarValidationPost(AccountStatusResponse accountStatusResponse, String currentStep) {
		if (utility.getAccountStatusResponse().get(accountStatusResponse.getCustomerId()) == null) {
			accountStatusResponse = getAadhaarDetails(accountStatusResponse.getCustomerId(), currentStep);
			utility.getAccountStatusResponse().put(accountStatusResponse.getCustomerId(), accountStatusResponse);
		} else {
			accountStatusResponse = utility.getAccountStatusResponse().get(accountStatusResponse.getCustomerId());
			utility.getAccountStatusResponse().put(accountStatusResponse.getCustomerId(), accountStatusResponse);
		}
		return accountStatusResponse;
 
	}
 
	public AadharDetails adhaarDetails() {
		AadharDetails aadharDetails = new AadharDetails();
		aadharDetails.setAadharPhoto(null);
		aadharDetails.setDateOfBirth("1999-07-05");
		aadharDetails.setGender("M");
		aadharDetails.setIsAadharVerified(true);
		aadharDetails.setIsDedupeVerified(true);
		aadharDetails.setName("Kiran Kumar");
		return aadharDetails;
	}
 
	public AccountStatusResponse getAadhaarDetails(String customerId, String currentStep) {
		AccountStatusResponse accountStatusResponse = new AccountStatusResponse();
		AadharDetails aadharDetails = adhaarDetails();
		
		if(currentStep.equals(StatusENUM.NEW_CUSTOMER_GET.name())) {
			utility.getAadharMap().put(customerId, aadharDetails);
			accountStatusResponse.setAadharDetails(aadharDetails);
			PermenantAddress permenantAddress = new PermenantAddress();
			permenantAddress.setAddress1("B403 Thakur Road Dream Park");
			permenantAddress.setAddress2("Kandivali Mumbai");
			permenantAddress.setAddress3(null);
			permenantAddress.setCity("mumbai");
			permenantAddress.setCityId("ce2c0689-b060-4971-901c-f634c9f80f92");
			permenantAddress.setCountry("India");
			permenantAddress.setNearestLandmark("Dream Park");
			permenantAddress.setPinCode("400001");
			permenantAddress.setState("Maharashtra");
			permenantAddress.setStateId("ac902f19-0a35-4500-98f6-d6c9818e9405");
			
			CustomerDetails customerDetails = new CustomerDetails();
			customerDetails.setPermanentAddress(permenantAddress);
			accountStatusResponse.setCustomerDetails(customerDetails);
		} else if(currentStep.equals(StatusENUM.PROFILE_COMPLETED.name())) {
			accountStatusResponse.setAadharDetails(aadharDetails);
			getCustomerData(accountStatusResponse);
		}
		if(currentStep.equals(StatusENUM.ACCOUNT_CREATED.name())) {
			accountStatusResponse.setAadharDetails(aadharDetails);
			getCustomerData(accountStatusResponse);
			accountStatusResponse.setAccountNumber(utility.generateRandomAccountNumber());
//			accountStatusResponse.setCrmLeadId("6229156912");
		}
//		accountStatusResponse.setAccountNumber(utility.generateRandomAccountNumber());
//		accountStatusResponse.setCrmLeadId("6229156912");
		return accountStatusResponse;
	}
 
	private void getCustomerData(AccountStatusResponse accountStatusResponse) {
		accountStatusResponse.setAmbValues(null);
		// amlDetails
		AmlDetails amlDetails = new AmlDetails();
		amlDetails.setAnnualIncome(2);
		amlDetails.setAnnualIncomeDescription("50000-100000");
		amlDetails.setEmploymentType(2);
		amlDetails.setEmploymentTypeDescription("Salaried");
		amlDetails.setIncorporationDate(null);
		amlDetails.setNatureOfBusiness(null);
		amlDetails.setNatureOfBusiness(null);
		amlDetails.setNatureOfBusinessDescription(null);
		amlDetails.setOrganizationType(2);
		amlDetails.setOrganizationTypeDescription("Private Ltd");
		amlDetails.setResidenceType(2);
		amlDetails.setResidenceTypeDescription("Owned");
		amlDetails.setSelfEmployedSince(null);
		amlDetails.setSelfEmployeeCategory(null);
		amlDetails.setSelfEmployeeCategoryDescription(null);
		amlDetails.setSourceIncomeDescription("Salary");
		amlDetails.setSourceOfFunds(2);
		amlDetails.setTypeOfFirm(null);
		amlDetails.setTypeOfFirmDescription(null);
		accountStatusResponse.setAmlDetails(amlDetails);
 
		accountStatusResponse.setChannel("CSC");
		accountStatusResponse.setConsentDetails(null);
		
		accountStatusResponse.setCurrentStatus("SA_CREATED");
		accountStatusResponse.setCurrentStep(9);
 
		CustomerDetails customerDetails = new CustomerDetails();
		CommunicationAddress communicationAddress = new CommunicationAddress();
		communicationAddress.setAddress1("B403 Thakur Road Dream Park");
		communicationAddress.setAddress2("Kandivali Mumbai");
		communicationAddress.setAddress3(null);
		communicationAddress.setCityName("Mumbai");
		communicationAddress.setPinCode("400001");
		communicationAddress.setStateName("Maharashtra");
		customerDetails.setCommunicationAddress(communicationAddress);
		
		PersonalDetails personalDetails = new PersonalDetails();
		personalDetails.setCityOfBirth("Mumbai");
		personalDetails.setCityOfBirthId("ce2c0689-b060-4971-901c-f634c9f80f92");
		personalDetails.setEmailId("rushikesh.patil2@hdfcbank.com");
		personalDetails.setFathersName("father");
		personalDetails.setMaritalStatus(1);
		personalDetails.setMothersName("mother");
		personalDetails.setNearestLandmark("Dream Park");
		personalDetails.setPhoneNumber("********2217");
		personalDetails.setSpouseName(null);
		customerDetails.setPersonalDetails(personalDetails);
		customerDetails.setTaxAddress(communicationAddress);
 
		accountStatusResponse.setCustomerDetails(customerDetails);
		accountStatusResponse.setFdReferenceNumber(null);
		accountStatusResponse.setFixedDeposit(null);
		accountStatusResponse.setJourney("SA");
		accountStatusResponse.setNeftDetails(null);
 
		NomineeDetails nomineeDetails = new NomineeDetails();
		nomineeDetails.setGuardianAddress1(null);
		nomineeDetails.setGuardianAddress2(null);
		nomineeDetails.setGuardianAddress3(null);
		nomineeDetails.setGuardianCityId(null);
		nomineeDetails.setGuardianCityName(null);
		nomineeDetails.setGuardianDob(null);
		nomineeDetails.setGuardianName(null);
		nomineeDetails.setGuardianPinCode("None");
		nomineeDetails.setGuardianStateId(null);
		nomineeDetails.setGuardianStateName(null);
		nomineeDetails.setIsEdit(null);
		nomineeDetails.setIsMinor(false);
		nomineeDetails.setNomineeAddress1("B403 Thakur Road Dream Park");
		nomineeDetails.setNomineeAddress2("Kandivali Mumbai");
		nomineeDetails.setNomineeAddress3(null);
		nomineeDetails.setNomineeCityId("ce2c0689-b060-4971-901c-f634c9f80f92");
		nomineeDetails.setNomineeCityName("Mumbai");
		nomineeDetails.setNomineeDob("1995-12-05");
		nomineeDetails.setNomineeId("27fc7fdf-32f2-41fd-98e6-8b0716d8efce");
		nomineeDetails.setNomineeName("Brother");
		nomineeDetails.setNomineePinCode("400001");
//		nomineeDetails.setNomineeRelationShipId(5);
		nomineeDetails.setNomineeStateId("ac902f19-0a35-4500-98f6-d6c9818e9405");
		nomineeDetails.setNomineeStateName("Maharashtra");
		accountStatusResponse.setNomineeDetails(nomineeDetails);
 
		accountStatusResponse.setOdDetails(null);
		accountStatusResponse.setPgTxnId(null);
		accountStatusResponse.setSavingsAmount(null);
 
		SelectedBranch selectedBranch = new SelectedBranch();
		selectedBranch.setBranchAddress(
				"Hdfc Bank Ltd., Ground Floor, , Jehangir Building, M G Road, Fort, Mumbai, Mumbai, Maharashtra");
		selectedBranch.setBranchCity("Mumbai");
		selectedBranch.setBranchId("064f45f2-47fe-4708-b190-ab5447be4a04");
		selectedBranch.setBranchName("Fort - Nanik Motwani Marg");
		selectedBranch.setBranchPincode("400001");
		accountStatusResponse.setSelectedBranch(selectedBranch);
 
		accountStatusResponse.setSelectedProduct(null);
		accountStatusResponse.setSkippedModules("add_over_draft");
		accountStatusResponse.setTotalamount(null);
		
		
	}

	private PermenantAddress setPermanentDetails() {
		PermenantAddress permenantAddress = new PermenantAddress();
		permenantAddress.setAddress1("B403 Thakur Road Dream Park");
		permenantAddress.setAddress2("Kandivali Mumbai");
		permenantAddress.setAddress3(null);
		permenantAddress.setCity("mumbai");
		permenantAddress.setCityId("ce2c0689-b060-4971-901c-f634c9f80f92");
		permenantAddress.setCountry("India");
		permenantAddress.setNearestLandmark("Dream Park");
		permenantAddress.setPinCode("400001");
		permenantAddress.setState("Maharashtra");
		permenantAddress.setStateId("ac902f19-0a35-4500-98f6-d6c9818e9405");
		return permenantAddress;
	}

	@GetMapping("/aadhaar-dedupe-check")
	public AadharDetails aadhaarDeduplication(@RequestParam String currentStep) {
		log.info("calling /v2/ekyc/aadhaar-dedupe-check for Aadhar DeDuplication from HDFC");

		AadharDetails aadharDetails = new AadharDetails();
		if (currentStep.equals(StatusENUM.UIDAI_VERIFICATION_COMPLETE.name())) {
			aadharDetails.setAadharPhoto(null);
			aadharDetails.setDateOfBirth("26-03-2000");
			aadharDetails.setGender("male");
			aadharDetails.setIsAadharVerified(true);
			aadharDetails.setIsDedupeVerified(true);
			aadharDetails.setName(null);
		} else {
			aadharDetails.setIsAadharVerified(false);
			aadharDetails.setIsDedupeVerified(false);
		}
		return aadharDetails;
	}

	public boolean customerBalckListed = false;

	@PostMapping("/factiva")
	public String verifyCustomerActive(@RequestBody CreateRequest asr) {
		log.info("calling /v2/ekyc/factiva API from HDFC");
		String applicationId = asr.getApplicationId();
		RegistrationRequest registrationRequest = utility.getApplicationMap().get(applicationId);
		if(registrationRequest.getPan().equalsIgnoreCase("SKTPB1001A") || registrationRequest.getPan().equalsIgnoreCase("SKTPB1002A")
				|| registrationRequest.getMobileNumber().equalsIgnoreCase("+918117172301") || registrationRequest.getMobileNumber().equalsIgnoreCase("+918117172302")) {
			log.info("Fails because the Acount with Pan "+registrationRequest.getPan()+" or mobile number "+registrationRequest.getMobileNumber()+" is a blacklisted customer");
			return StatusENUM.BLACKLISTED_CUSTOMER.name();
		} else {
			log.info("The Acount with Pan "+registrationRequest.getPan()+" or mobile number "+registrationRequest.getMobileNumber()+" is a whitelisted customer");
			return StatusENUM.WHITELISTED_CUSTOMER.name();
		}
	}

}
