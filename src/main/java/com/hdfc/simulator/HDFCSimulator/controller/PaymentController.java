package com.hdfc.simulator.HDFCSimulator.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.extern.slf4j.Slf4j;

//@RestController
@Service
@CrossOrigin
@Slf4j
public class PaymentController {

//	@Autowired
//	Utility utility;
//
//	public boolean vkyc;
//	public boolean paymentStatus;

//	@GetMapping("/v1/payment-gateway/get-status")
	public void getAccountActivationStatus() {
		log.info("Calling /v1/payment-gateway/get-status for payment status");
//		String mode = paymentStatusRequest.getMode();
//		String journeyId = paymentStatusRequest.getJourneyId();
//		String taskId = paymentStatusRequest.getTaskId();
//		String transactionId = paymentStatusRequest.getTransactionId();
//		PaymentStatusResponse paymentResponse = new PaymentStatusResponse();
//		if(paymentStatus) {
//			paymentResponse.setStatus("success");
//			paymentResponse.setOrderBankRefNo(utility.generateRandomAccountNumber());
//			paymentResponse.setTaskId(taskId);
//			paymentResponse.setJourneyId(journeyId);
//			paymentResponse.setOrderId(utility.generateRandomString());
//			paymentResponse.setAmount(20000.0);
//			String currentStep = StatusENUM.FUNDING_RECEIVED.name();
//			paymentResponse.setCurrentStep(currentStep);
//			return ResponseEntity.ok(paymentResponse);
//		}else {
//			ResponsePojo errorCode = new ResponsePojo();
//			errorCode.setMessage("Server encountered unexpected error");
//			errorCode.setResponseCode(400);
//			return ResponseEntity.badRequest().body(errorCode);
//		}
	}

//	@GetMapping("/v1/video-kyc/get-application-vkyc-status-experience")
	public void initiatingVideokyc() {
		log.info("Calling /v1/video-kyc/get-application-vkyc-status-experience for getting application status for VKYC");
////		String taskId = createRequest.getTaskId();
////		String applicationId = createRequest.getApplicationId();
//////		String journeyId = createRequest.getJourneyId();
////		String journey = createRequest.getJourney();
//		VkycResponse vkycResponse = new VkycResponse();
//		PaymentStatusResponse paymentResponse = new PaymentStatusResponse();
//		if(vkyc) {
//			vkycResponse.setCaptureExpiresAt(null);
//			vkycResponse.setCaptureLink(null);
////			vkycResponse.setJourneyId(journeyId);
////			vkycResponse.setTaskId(taskId);
//			return ResponseEntity.ok(vkycResponse);
//		}else {
//			//we need to check here how manyv number of vkyc retries are available still
//			return ResponseEntity.badRequest().body(null);
//		}
	}

	// create an api for initiating vkyc (/v2/video-kyc/initiate-vkyc-experience)
//	@GetMapping("/v2/video-kyc/initiate-vkyc-experience")
	public void checkAccountStatus() {
		log.info("Calling /v2/video-kyc/initiate-vkyc-experience for initiating VKYC experience");
	}
	
	public void fdState(){
		log.info("Calling /v2/fixed-deposit/fd-state for Account activation query");
	}
}
