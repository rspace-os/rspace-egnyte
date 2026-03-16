package com.researchspace.egnyte.api2;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class ResponseError {
	
	HttpStatusCode httpStatus;
	String message;
	String responseAsString;

}
