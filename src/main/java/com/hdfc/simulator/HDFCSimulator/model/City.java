package com.hdfc.simulator.HDFCSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
	private String city_id;
	private String city_name;
	private String country;
	private String state;
	private String state_id;
}
