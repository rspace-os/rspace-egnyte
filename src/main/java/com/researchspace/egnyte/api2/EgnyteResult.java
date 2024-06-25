package com.researchspace.egnyte.api2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Data;
/**
 * Either successResult or error is set, other is null
 */
@Data
public class EgnyteResult<T> {

	private ResponseEntity<T> successResult;
	private ResponseError error;

	public boolean isSuccessful() {
		return successResult != null;
	}

	public EgnyteResult(ResponseEntity<T> resp) {
		this.successResult = resp;
	}

	public EgnyteResult(ResponseError error) {
		this.error = error;
	}
	/**
	 * Gets status either from success result or error.
	 * @return
	 */
	public HttpStatus getStatusCode() {
		return isSuccessful()?successResult.getStatusCode():error.getHttpStatus();
	}
	/**
	 * Shortcut to the response entity if aPI call was successful
	 * @return
	 */
	public T getResult () {
		return successResult.getBody();
	}

}
