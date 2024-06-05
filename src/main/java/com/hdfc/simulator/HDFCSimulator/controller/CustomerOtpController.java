package com.hdfc.simulator.HDFCSimulator.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hdfc.simulator.HDFCSimulator.model.GenerateOtpModel;
import com.hdfc.simulator.HDFCSimulator.model.Otp;
import com.hdfc.simulator.HDFCSimulator.model.OtpRegistration;
import com.hdfc.simulator.HDFCSimulator.model.RegistrationRequest;
import com.hdfc.simulator.HDFCSimulator.model.ResponsePojo;
import com.hdfc.simulator.HDFCSimulator.model.StatusENUM;
import com.hdfc.simulator.HDFCSimulator.model.ValidateOtpResponse;
import com.hdfc.simulator.HDFCSimulator.util.Utility;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v2/customer_otpservice")
public class CustomerOtpController {

	@Autowired
	Utility utility;

	ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	public boolean loginSuspended() {
		return false;
	}

	public boolean suspensionExpired() {
		return true;
	}

	public boolean otpSuccess() {
		return true;
	}

	public int generateRandomOtp(String mobileNumber) {
		List<Integer> otpsList = new ArrayList<>();
		otpsList.add(123456);
		otpsList.add(234567);
		otpsList.add(432158);
		otpsList.add(182839);
		otpsList.add(939330);
		otpsList.add(382921);
		otpsList.add(438392);
		otpsList.add(123833);
		otpsList.add(897354);
		otpsList.add(723456);
		otpsList.add(656767);
		otpsList.add(537389);
		Random random = new Random();
		int randomIndex = random.nextInt(otpsList.size());
		int randomOtp = otpsList.get(randomIndex);
		return randomOtp;
	}

	HashMap<String, String> hashMap = new HashMap<>();
	
	Set<String> reGeneratedOTP = new HashSet<>();

	@PostMapping("/generate-registration-otp")
	public GenerateOtpModel generateOtp(@RequestBody String gom) throws JsonMappingException, JsonProcessingException {
		log.info("Triggering generate-otp API :/v2/customer_otpservice/generate-registration-otp from HDFC");
		Long otp;
		GenerateOtpModel generateOtpModel = objectMapper.readValue(gom, GenerateOtpModel.class);
		RegistrationRequest registrationRequest = generateOtpModel.getRegistrationRequest();
		OtpRegistration otpRegistration = generateOtpModel.getOtpRegistration();
		String mobileNumber = registrationRequest.getMobileNumber();
		String otpTransactionId = utility.generateRandomString();
		String journeyId = utility.generateRandomString();
		String taskId = utility.generateRandomString();
		String panNumber = generateOtpModel.getRegistrationRequest().getPan();
		Otp otpClassObj = utility.getOtpObj().get(mobileNumber);
		try {
			if (registrationRequest.getCurrentStep().equals(StatusENUM.MOBILE_VERIFICATION_PENDING.name())) {
				if (registrationRequest.getJourneyId() == null && !hashMap.containsValue(mobileNumber)) {
					addCustomer(registrationRequest, journeyId, mobileNumber);
//					otp = (long) generateRandomOtp(mobileNumber);
					otp = (long) 123456;
					Otp otpObj = new Otp();
					otpObj.setOtp(otp);
					log.info("Generating OTP for the demonstartion purpose:" + otp);
					otpObj.setRetryCount(3);
					otpObj.setOtpValidate(otpSuccess());
					otpObj.setOtpTransactionId(otpTransactionId);

					utility.getOtpObj().put(journeyId, otpObj);
					utility.getOtpObj().put(mobileNumber, otpObj);
					
					otpRegistration.setJourneyId(journeyId);
					otpRegistration.setOtpTransactionId(otpTransactionId);
//					otpRegistration.setTaskId(taskId);
					otpRegistration.setNoOfAttemptsRemaining(otpObj.getRetryCount());
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//					otpRegistration.setOtpCreatedAt(LocalDateTime.now().format(formatter));
					generateOtpModel.setOtpRegistration(otpRegistration);
					utility.getOtpDetails().put(journeyId, otpRegistration);
					utility.getOtpDetails().put(mobileNumber, otpRegistration);
					RegistrationRequest registrationRequest1 = utility.getCustomerDetails1().get(mobileNumber);
					utility.getCustomerDetails1().put(mobileNumber, registrationRequest1);
					registrationRequest1.setErrorMessage("NO_ERROR");
					generateOtpModel.setRegistrationRequest(registrationRequest1);
					return generateOtpModel;
				} else if (hashMap.containsValue(mobileNumber) && otpClassObj.getRetryCount() != 0) {
					Otp otp2 = utility.getOtpObj().get(mobileNumber);
//					otp = (long) generateRandomOtp(mobileNumber);
					otp = (long) 123456;
					log.info("Generating OTP for the demonstartion purpose:" + otp);
					otp2.setOtp(otp);
					otp2.setRetryCount(otp2.getRetryCount() - 1);
					utility.getOtpObj().put(journeyId, otp2);
					utility.getOtpObj().put(mobileNumber, otp2);
//					otpRegistration.setTaskId(taskId);
					otpRegistration.setOtpTransactionId(otpTransactionId);
					otpRegistration.setNoOfAttemptsRemaining(otp2.getRetryCount());
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					otpRegistration.setOtpCreatedAt(LocalDateTime.now().format(formatter));
					generateOtpModel.setOtpRegistration(otpRegistration);
					RegistrationRequest registrationRequest2 = utility.getCustomerDetails1().get(mobileNumber);
					utility.getCustomerDetails1().put(mobileNumber, registrationRequest2);
					registrationRequest2.setErrorMessage("NO_ERROR");
					generateOtpModel.setRegistrationRequest(registrationRequest2);
					return generateOtpModel;
				} else {
					log.info("Login is Denied for the mobile number {} and pan {}", mobileNumber, panNumber);
					registrationRequest.setCurrentStep(StatusENUM.LOGIN_DECLINED.name());
					registrationRequest.setErrorMessage("Login is Denied");
					registrationRequest.setJourneyId(journeyId);
				}

			} else {
				log.info("Login is Denied for the mobile number {} and pan {}", mobileNumber, panNumber);
				registrationRequest.setCurrentStep(StatusENUM.LOGIN_DECLINED.name());
				registrationRequest.setErrorMessage("Login is Denied");
			}

		} catch (Exception e) {
			log.error("Unable to reach otp generation service:" + e.getMessage());
			registrationRequest.setCurrentStep(StatusENUM.LOGIN_DECLINED.name());
			registrationRequest.setErrorMessage("Unable to reach otp generation service");
		}
		return generateOtpModel;

	}

	private void addCustomer(RegistrationRequest registrationRequest, String journeyId, String mobileNumber) {
		registrationRequest.setLoginSuspended(loginSuspended());
		registrationRequest.setSuspensionExpired(suspensionExpired());
		registrationRequest.setJourneyId(journeyId);
		registrationRequest.setMobileNumber(mobileNumber);
		utility.getCustomerDetails().put(journeyId, registrationRequest);
		utility.getCustomerDetails1().put(mobileNumber, registrationRequest);
		hashMap.put(mobileNumber, registrationRequest.getMobileNumber());
		hashMap.put(journeyId, registrationRequest.getJourneyId());

	}

	@PostMapping("/verify-registration-otp")
	public ValidateOtpResponse validateOtp(@RequestBody GenerateOtpModel generateOtpModel) {
		log.info("calling validate-otp API :/v2/customer_otpservice/verify-registration-otp from HDFC");
		OtpRegistration otpRegistration = generateOtpModel.getOtpRegistration();
		long validateOtp = otpRegistration.getOtp();
		String journeyId = otpRegistration.getJourneyId();
//		String taskId = otpRegistration.getTaskId();
		ValidateOtpResponse validateOtpResponse = new ValidateOtpResponse();
		Otp otp = utility.getOtpObj().get(journeyId);
		try {
			if (journeyId.equals(hashMap.get(journeyId)) && otp.getOtp() == validateOtp) {
				log.info("OTP Entered is a valid OTP");
				otp.setOtpValidate(true);
				utility.getValidated().put(generateOtpModel.getRegistrationRequest().getMobileNumber(), true);
				utility.getOtpObj().put(journeyId, otp);
				validateOtpResponse.setAccessToken(utility.genrateRandomAccessTocken());
				validateOtpResponse.setCustomerId(utility.generateRandomString());
				validateOtpResponse.setJourneyId(journeyId);
//				validateOtpResponse.setTaskId(taskId);
				validateOtpResponse.setRetries(otp.getRetryCount());
				validateOtpResponse.setValid(true);
//				validateOtpResponse.setName(generateOtpModel.getRegistrationRequest().getName());
				validateOtpResponse.setName("Riya Raj Patil");
				validateOtpResponse.setErrorStatus("NO_ERROR");
				validateOtpResponse.setApplicationId(utility.generateRandomString());
				utility.getApplicationMap().put(validateOtpResponse.getApplicationId(),
						generateOtpModel.getRegistrationRequest());
				utility.getValMap().put(journeyId, validateOtpResponse.getCustomerId());
			} else {
				validateOtpResponse.setErrorStatus(
						"Invalid OTP. We have regenerated the OTP. Please check your SMS and try with valid OTP again");
				validateOtpResponse.setValid(false);
				validateOtpResponse.setRetries(otp.getRetryCount());
				utility.getOtpObj().put(journeyId, otp);
				utility.getValidated().put(generateOtpModel.getRegistrationRequest().getMobileNumber(), true);
			}
			return validateOtpResponse;
		} catch (Exception e) {
			log.error("Unable to reach otp generation service:" + e.getMessage());
			validateOtpResponse.setErrorStatus("Unable to reach otp generation service");
			return validateOtpResponse;
		}
		
	}

	@PostMapping("/check-existing-customer")
	public Boolean checkExistingCustomner(@RequestBody RegistrationRequest cd ) {
		log.info("Triggering check-existing-customer API :/v2/customer_otpservice/check-existing-customer from HDFC");
		String pan = cd.getPan();
		String mobileNumber = cd.getMobileNumber();
		utility.getUniqueCustomers().add(pan);
		utility.getUniqueCustomers().add(mobileNumber);
        log.info("Determining if an Account has already been created for the PAN:" + pan +" & mob " + mobileNumber);
        if(pan.equalsIgnoreCase("SKTPB1005A") || pan.equalsIgnoreCase("SKTPB1006A")) { 
        	log.info("The Account with Pan "+pan+" is already created!");
            return false;
        }else if(mobileNumber.equalsIgnoreCase("+918117172217") || mobileNumber.equalsIgnoreCase("+918117172218")) {
        	log.info("The Account with mobile number "+mobileNumber+" is already created!");
            return false;
        } else {
            log.info("The Account with the PAN " + pan +" isn't created earlier, process continues...");
            return true;
        }
        
    }

}
