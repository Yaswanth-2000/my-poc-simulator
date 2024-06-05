package com.hdfc.simulator.HDFCSimulator.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdfc.simulator.HDFCSimulator.model.AadharDetails;
import com.hdfc.simulator.HDFCSimulator.model.AccountResponse;
import com.hdfc.simulator.HDFCSimulator.model.AccountStatusRequest;
import com.hdfc.simulator.HDFCSimulator.model.AccountStatusResponse;
import com.hdfc.simulator.HDFCSimulator.model.CreateRequest;
import com.hdfc.simulator.HDFCSimulator.model.CreateResponse;
import com.hdfc.simulator.HDFCSimulator.model.CustomerDetails;
import com.hdfc.simulator.HDFCSimulator.model.PermenantAddress;
import com.hdfc.simulator.HDFCSimulator.model.PostCustomerResponse;
import com.hdfc.simulator.HDFCSimulator.model.RegistrationRequest;
import com.hdfc.simulator.HDFCSimulator.model.SelectedBranch;
import com.hdfc.simulator.HDFCSimulator.model.StatusENUM;
import com.hdfc.simulator.HDFCSimulator.util.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v2/fixed_deposit")
public class FixedDepositController {
	@Autowired
	Utility utility;
	@Autowired
	AadhaarDedupeController aadhaarDedupeController;

	ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping("/fd-state")
	public String fdState(@RequestParam("currentStep") String currentStep, @RequestBody String requestData)
			throws JsonMappingException, JsonProcessingException {
		// ETB and NTB check
		if (currentStep.equals(StatusENUM.MOBILE_VERIFICATION_COMPLETE.name())) {
			log.info("Calling /v2/fixed_deposit/fd-state fdstate API for ETB and NTB check");
			if (!utility.getPanDb().contains(requestData)) {
				log.info("The customer - " + requestData + " is a new customer, the process continues");
				return StatusENUM.NEW_CUSTOMER.name();
			} else {
				log.info("The customer - " + requestData + " is an existing customer, process will be terminated");
				return StatusENUM.EXISTING_CUSTOMER.name();
			}

		}
		// UIDAI validation check getting aadhaar details - get customer
		if (currentStep.equals(StatusENUM.NEW_CUSTOMER_GET.name())) {
			log.info("Calling /v2/fixed_deposit/fdstate API for Aadhar Validation");

			AccountStatusRequest asr = objectMapper.readValue(requestData, AccountStatusRequest.class);
			AccountStatusResponse aadhaarValidation = aadhaarDedupeController.aadhaarValidation(asr, asr.getJourneyId(),
					currentStep);
			if (aadhaarValidation != null) {
				log.info("UIDAI verification has completed for this customer");
			} else {
				log.info("UIDAI verification has failed for this customer");
			}

			return objectMapper.writeValueAsString(aadhaarValidation);
		}

//		if(currentStep.equals(StatusENUM.PROFILE_COMPLETED_GET.name())) {
//			AccountStatusRequest asr = objectMapper.readValue(requestData, AccountStatusRequest.class);
//			AccountStatusResponse aadhaarValidation = aadhaarDedupeController.aadhaarValidation(asr, asr.getJourneyId(), currentStep);
//			log.info("Customer details are fetched the PROFILE is completed");
//			return objectMapper.writeValueAsString(aadhaarValidation);
//		}

		if (currentStep.equals(StatusENUM.PROFILE_COMPLETED_GET.name())) {
			AccountStatusResponse accountStatusResponse = null;
			AccountStatusRequest asr = objectMapper.readValue(requestData, AccountStatusRequest.class);
			// get custid from utility..
			accountStatusResponse = utility.getAccountStatusResponse().get(asr.getCustomerId());
			if (accountStatusResponse == null) {
				accountStatusResponse = aadhaarDedupeController.aadhaarValidation(asr, asr.getJourneyId(), currentStep);
			}
			log.info("Customer details are fetched the PROFILE is completed");
			return objectMapper.writeValueAsString(accountStatusResponse);
		}

		if (currentStep.equals(StatusENUM.ACCOUNT_CREATED.name())
				|| currentStep.equals(StatusENUM.ACCOUNT_ACTIVATED.name())) {
			AccountStatusRequest asr = objectMapper.readValue(requestData, AccountStatusRequest.class);
			AccountStatusResponse aadhaarValidation = aadhaarDedupeController.aadhaarValidation(asr, asr.getJourneyId(),
					currentStep);
			CreateResponse createResponse = utility.getCreateResponseMap().get(aadhaarValidation.getApplicationId());
			aadhaarValidation.setAccountNumber(createResponse.getAccountNumber());
			aadhaarValidation.setCrmLeadId("6229156912");
			aadhaarValidation.setCurrentStatus("ACCOUNT_CREATED");
			SelectedBranch selectedBranch = new SelectedBranch();
//			if (asre.getSelectedBranch().getBranchId().equals("064f45f2-47fe-4708-b190-ab5447be4a04")) {
			selectedBranch.setBranchAddress(
					"Hdfc Bank Ltd., Ground Floor, , Jehangir Building, M G Road, Fort, Mumbai, Mumbai, Maharashtra");
			selectedBranch.setBranchCity("Mumbai");
			selectedBranch.setBranchId("064f45f2-47fe-4708-b190-ab5447be4a04");
			selectedBranch.setBranchName("Fort - Nanik Motwani Marg");
			selectedBranch.setBranchPincode("400001");
//			}
			aadhaarValidation.setSelectedBranch(selectedBranch);
			log.info("Customer details are fetched the ACCOUNT is activated");
			return objectMapper.writeValueAsString(aadhaarValidation);
		}

		if (currentStep.equals(StatusENUM.NEW_CUSTOMER_POST.name())) {
			log.info("Calling /v2/fixed_deposit/fdstate API for Aadhar Validation");

			AccountStatusResponse asr = objectMapper.readValue(requestData, AccountStatusResponse.class);
			AccountStatusResponse aadhaarValidation = aadhaarDedupeController.aadhaarValidationPost(asr, currentStep);
			if (aadhaarValidation != null) {
				log.info("UIDAI verification has completed for this customer");
			} else {
				log.info("UIDAI verification has failed for this customer");
			}

			return objectMapper.writeValueAsString(aadhaarValidation);
		}

		if (currentStep.equals(StatusENUM.NEW_CUSTOMER_POST.name())) {
			log.info("Calling /v2/fixed_deposit/fdstate API for Aadhar Validation");

			AccountStatusResponse asr = objectMapper.readValue(requestData, AccountStatusResponse.class);
			AccountStatusResponse aadhaarValidation = aadhaarDedupeController.aadhaarValidationPost(asr, currentStep);
			if (aadhaarValidation != null) {
				log.info("UIDAI verification has completed for this customer");
			} else {
				log.info("UIDAI verification has failed for this customer");
			}

			return objectMapper.writeValueAsString(aadhaarValidation);
		}

		if (currentStep.equals(StatusENUM.PROFILE_IN_PROGRESS.name())) {
			log.info("Calling /v2/fixed_deposit/fdstate API for Saving customer details");
			log.info("Saved customer details successfully");
			log.info("Calling /v2/fixed_deposit/fdstate API for Saving Address details");
			log.info("Saved customer Address successfully");
			log.info("Calling /v2/fixed_deposit/fdstate API for Saving Nominee details");
			log.info("Saved customer Nominee successfully");
			return StatusENUM.PROFILE_COMPLETED.name();
		}

		// fd state to get account details
		if (currentStep.equals(StatusENUM.ACTIVATION_PENDING.name())) {
			log.info("Calling /v2/fixed_deposit/fdstate API for getting account details");

			String customerId = utility.getValMap().get(requestData);
			AccountStatusResponse accountStatusResponse = utility.getAccountStatusResponse().get(customerId);
			CreateResponse createResponse = utility.getCreateResponseMap()
					.get(accountStatusResponse.getApplicationId());
			AccountResponse acr = new AccountResponse();
			acr.setAccountNo(FixedDepositController.mask(createResponse.getAccountNumber()));
			acr.setBankCustomerId(FixedDepositController.mask(createResponse.getBankCustomerId()));
			acr.setCrmLeadId(FixedDepositController.mask("6229156912"));
			acr.setIfscCode(FixedDepositController.mask(createResponse.getIfscCode()));
			accountStatusResponse.setAccountNumber(acr.getAccountNo());
			accountStatusResponse.setCrmLeadId(acr.getCrmLeadId());
			utility.getAccountStatusResponse().put(customerId, accountStatusResponse);
			return new ObjectMapper().writeValueAsString(acr);
		}

		if (currentStep.equals(StatusENUM.PROFILE_COMPLETED.name())) {
			log.info("Calling /v2/fixed_deposit/fdstate API for Saving Location details");
			AccountStatusResponse asre = new ObjectMapper().readValue(requestData, AccountStatusResponse.class);
			asre.setAccountNumber(null);
			asre.setCrmLeadId(null);
			asre.setCurrentStatus("INITIAL_STAGE");

			SelectedBranch selectedBranch = new SelectedBranch();
//			if (asre.getSelectedBranch().getBranchId().equals("064f45f2-47fe-4708-b190-ab5447be4a04")) {
			selectedBranch.setBranchAddress(
					"Hdfc Bank Ltd., Ground Floor, , Jehangir Building, M G Road, Fort, Mumbai, Mumbai, Maharashtra");
			selectedBranch.setBranchCity("Mumbai");
			selectedBranch.setBranchId("064f45f2-47fe-4708-b190-ab5447be4a04");
			selectedBranch.setBranchName("Fort - Nanik Motwani Marg");
			selectedBranch.setBranchPincode("400001");
//			}
			asre.setSelectedBranch(selectedBranch);
			
			utility.getAccountStatusResponse().put(asre.getCustomerId(), asre);
//			asre.setAccountNumber(null);
//			asre.setCrmLeadId(null);
			PostCustomerResponse psr = new PostCustomerResponse();
			psr.setApplicationId(asre.getApplicationId());
			psr.setCustomerId(asre.getCustomerId());
			psr.setCurrentStatus("INITIAL_STAGE");
			psr.setStatus("Customer Details saved successfully");
			psr.setCurrentStep(9);

			AadharDetails aadharDetails = utility.getAadharMap().get(asre.getCustomerId());
			if (aadharDetails == null) {
				aadharDetails = new AadharDetails();
			}
			asre.setAadharDetails(aadharDetails);
			psr.setIsAadharVerified(aadharDetails.getIsAadharVerified());
			psr.setIsDedupeVerified(aadharDetails.getIsDedupeVerified());
			log.info("Saved Location details successfully");
			return new ObjectMapper().writeValueAsString(psr);
		}

		return null;
	}

	@Autowired
	PaymentController paymentController;

	@PostMapping("/create-savings-account")
	public CreateResponse createSavingsAccount(@RequestBody CreateRequest cr, @RequestParam String journeyId) {
		log.info("Calling /v2/fixed_deposit/create-savings-account to create savings account from HDFC");
		String applicationId = cr.getApplicationId();
//		String journey = cr.getJourney();
//		String journeyId = cr.getJourneyId();
//		String taskId = cr.getTaskId();
		CreateResponse createResponse = new CreateResponse();
		createResponse.setAccountNumber(FixedDepositController.mask(utility.generateRandomAccountNumber()));
		createResponse.setBankCustomerId(FixedDepositController.mask(utility.generateRandomAccountNumber()));
		createResponse.setIfscCode(FixedDepositController.mask(utility.generateRandomIfscCode()));
		utility.getCreateResponseMap().put(applicationId, createResponse);

		RegistrationRequest registrationRequest = utility.getCustomerDetails().get(journeyId);

		utility.getPanDb().add(registrationRequest.getPan());
		Executors.newSingleThreadExecutor().execute(new Runnable() {

			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(2);
					paymentController.getAccountActivationStatus();
					TimeUnit.SECONDS.sleep(2);
					paymentController.initiatingVideokyc();
					TimeUnit.SECONDS.sleep(2);
					paymentController.checkAccountStatus();
					TimeUnit.SECONDS.sleep(2);
					paymentController.fdState();
				} catch (Exception e) {

				}
			}
		});

		return createResponse;
	}

	static String mask(String input) {
		return input.replaceAll(".(?=.{4})", "*");
	}
}
