package com.hdfc.simulator.HDFCSimulator.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdfc.simulator.HDFCSimulator.model.MasterDataRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MasterDataController {

	@PostMapping("/v2/master")
	public Object masterDetailes(@RequestBody MasterDataRequest masterDto)
			throws JsonParseException, JsonMappingException, IOException {
		log.info("Calling /v2/master to get the master Data from HDFC");
		ObjectMapper objectMapper = new ObjectMapper();
		Object result = null;
		ArrayList<Object> al = new ArrayList<>();
		Map<String, Object> jsonMap = objectMapper
				.readValue(getClass().getResourceAsStream("src/main/resources/Master.json"), Map.class);
//		 Map<String, Object> jsonMap = objectMapper.readValue(new File("src/main/resources/Master.json"), Map.class);
		if (masterDto.getCity() != null && masterDto.getCity().getCity_name() != null) {
			al = (ArrayList<Object>) jsonMap.get("city");
		} else if (masterDto.getRelationShip() != null && masterDto.getRelationShip().getCustomer_id() != null) {
			al = (ArrayList<Object>) jsonMap.get("relation_ship");
		} else if (masterDto.getMaritalStatus() != null && masterDto.getMaritalStatus().getMarital_status() != null) {
			al = (ArrayList<Object>) jsonMap.get("marital_status");
		} else if (masterDto.getProduct() != null && masterDto.getProduct().getGender() != null) {
			al = (ArrayList<Object>) jsonMap.get("product");
		} else if (masterDto.getBranch() != null && masterDto.getBranch().getAddress() != null) {
			al = (ArrayList<Object>) jsonMap.get("branch");
		} else if (masterDto.getEmployerType() != null
				&& masterDto.getEmployerType().getEmployment_description() != null) {
			al = (ArrayList<Object>) jsonMap.get("employee_type");
		} else if (masterDto.getEmail() != null && masterDto.getEmail().getEmail_id() != null) {
			al = (ArrayList<Object>) jsonMap.get("email_id");
		}

		else if (masterDto.getAnnualIncome() != null && masterDto.getAnnualIncome().getAnnual_income() != null) {
			al = (ArrayList<Object>) jsonMap.get("annual_income");
		} else if (masterDto.getResidenceType() != null && masterDto.getResidenceType().getResidence_type() != null) {
			al = (ArrayList<Object>) jsonMap.get("residence_type");
		} else if (masterDto.getFirmType() != null && masterDto.getFirmType().getFirm_type() != null) {
			al = (ArrayList<Object>) jsonMap.get("firm_type");
		} else if (masterDto.getSelfEmployedProcessional() != null
				&& masterDto.getSelfEmployedProcessional().getSelf_employed_professional() != null) {
			al = (ArrayList<Object>) jsonMap.get("self_employed_professional");
		} else if (masterDto.getOrganizationType() != null
				&& masterDto.getOrganizationType().getOrganization_type() != null) {
			al = (ArrayList<Object>) jsonMap.get("organization_type");
		} else if (masterDto.getNatureOfBusiness() != null
				&& masterDto.getNatureOfBusiness().getNature_of_business() != null) {
			al = (ArrayList<Object>) jsonMap.get("nature_of_businesses");
		}
		return al;
	}
//	@GetMapping("/v2/master")
//	public Object masterDetailes() throws JsonParseException, JsonMappingException, IOException {
//		log.info("Calling /v2/master to get all the master Data from HDFC");
//		 ObjectMapper objectMapper = new ObjectMapper();
//		 Object result =null;
//		 ArrayList<Object>al=new ArrayList<>();
//		 Map<String, Object> jsonMap = objectMapper.readValue(new File("src/main/resources/Master.json"), Map.class);
//		
//		 
//		return jsonMap;
//	}

	@GetMapping("/v2/master")
	public Object masterDetailes() throws JsonParseException, JsonMappingException, IOException {
		log.info("Calling /v2/master to get all the master Data from HDFC");
		ObjectMapper objectMapper = new ObjectMapper();
		Object result = null;

		try (InputStream inputStream = new ClassPathResource("Master.json").getInputStream()) {
			Map<String, Object> jsonMap = objectMapper.readValue(inputStream, Map.class);
			return jsonMap;
		} catch (IOException e) {
			log.info("Exception raised while reading the master Details");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading Master.json");
		}
	}
}
