package com.hdfc.simulator.HDFCSimulator.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.hdfc.simulator.HDFCSimulator.model.AadharDetails;
import com.hdfc.simulator.HDFCSimulator.model.AccountStatusResponse;
import com.hdfc.simulator.HDFCSimulator.model.CreateResponse;
import com.hdfc.simulator.HDFCSimulator.model.GenerateOtpModel;
import com.hdfc.simulator.HDFCSimulator.model.Otp;
import com.hdfc.simulator.HDFCSimulator.model.OtpRegistration;
import com.hdfc.simulator.HDFCSimulator.model.RegistrationRequest;

import lombok.Data;

@Component
@Data
public class Utility {

	Map<String, RegistrationRequest> customerDetails = new HashMap<String, RegistrationRequest>();
	Map<String, RegistrationRequest> customerDetails1 = new HashMap<String, RegistrationRequest>();
	Map<String, OtpRegistration> otpDetails = new HashMap<String, OtpRegistration>();
	Map<String,AccountStatusResponse > accountStatusResponse = new HashMap<String,AccountStatusResponse >();
	Map<String, String> valMap = new HashMap<>();
	Map<String,AccountStatusResponse> accountStatusResponseValueMap = new HashMap<>();
	Map<String, Otp> otpObj = new HashMap<String, Otp>();
	Map<String, AadharDetails> aadharMap= new HashMap<>();
	Set<String> uniqueCustomers = new HashSet<>();
	Map<String, Boolean> validated = new HashMap<>();
	Map<String, CreateResponse> createResponseMap= new HashMap<>();
	Set<String> panDb = new HashSet<>();
	
	
	Map<String,RegistrationRequest > applicationMap= new HashMap<>();
	
	public String generateRandomString() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public String genrateRandomAccessTocken() {
		final SecureRandom random = new SecureRandom();

	        int length = 32; // Specify the desired length of the access token
	        StringBuilder sb = new StringBuilder(length);
	        for (int i = 0; i < length; i++) {
	        	char randomChar = (char) (random.nextInt(26) + 'a');
	            sb.append(randomChar);
	        }
	        return sb.toString();
	}
	
	public String generateRandomAccountNumber() {
		List<String> randomAccountNumber = new ArrayList<>();
		randomAccountNumber.add("12345678912");
		randomAccountNumber.add("23456789101");
		randomAccountNumber.add("33456329101");
		randomAccountNumber.add("12456789101");
		randomAccountNumber.add("67856789101");
		randomAccountNumber.add("09826789101");
		randomAccountNumber.add("92872289101");
		randomAccountNumber.add("23457282762");
		Random random = new Random();
		int randomIndex = random.nextInt(randomAccountNumber.size());
		String randomString = randomAccountNumber.get(randomIndex);
		return randomString;
	}
	public String generateRandomIfscCode() {
		List<String> randomAccountNumber = new ArrayList<>();
		randomAccountNumber.add("Hdfc0000123");
		randomAccountNumber.add("Hdfc0000060");
		randomAccountNumber.add("Hdfc0000234");
		randomAccountNumber.add("Hdfc0000345");
		randomAccountNumber.add("Hdfc0000456");
		randomAccountNumber.add("Hdfc0000678");
		randomAccountNumber.add("Hdfc0000789");
		randomAccountNumber.add("Hdfc0000891");
		Random random = new Random();
		int randomIndex = random.nextInt(randomAccountNumber.size());
		String randomString = randomAccountNumber.get(randomIndex);
		return randomString;
	}
	
}
