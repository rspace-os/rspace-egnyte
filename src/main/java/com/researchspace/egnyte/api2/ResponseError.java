package com.researchspace.egnyte.api2;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class ResponseError {
	
	HttpStatus httpStatus;
	String message;
	String responseAsString;

}
