package com.hdfc.simulator.HDFCSimulator.model;

import lombok.Data;

@Data
public class Product {
	private int annualIncome;
    private int age;
    private String gender;
    private int occupationTypeId;
    private String channel;
    private String customerId;
}
