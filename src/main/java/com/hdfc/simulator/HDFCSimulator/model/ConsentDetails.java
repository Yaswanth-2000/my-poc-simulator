package com.hdfc.simulator.HDFCSimulator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ConsentDetails {

	@JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private String value;

    @JsonProperty("language")
    private String language;
}
