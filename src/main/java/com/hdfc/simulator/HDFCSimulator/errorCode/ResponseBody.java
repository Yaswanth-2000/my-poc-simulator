package com.hdfc.simulator.HDFCSimulator.errorCode;

import org.springframework.http.HttpStatus;

public class ResponseBody {

	public int response_code;
	public String message;

	public ResponseBody(int response_code, String message) {
		this.response_code = response_code;
		this.message = message;
	}

//	public ResponseBody(HttpStatus status,String message) {
//		
//	}
	public int getResponse_code() {
		return response_code;
	}

	public void setResponse_code(int response_code) {
		this.response_code = response_code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
