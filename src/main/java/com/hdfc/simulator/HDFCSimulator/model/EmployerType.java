package com.hdfc.simulator.HDFCSimulator.model;

import java.util.List;

import lombok.Data;
@Data
public class EmployerType {
	private String employee_type_code;
	private String employment_description;
	private List<Filed>field;
}
