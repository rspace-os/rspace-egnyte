package com.researchspace.egnyte.api.clients.requests;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.researchspace.egnyte.api2.EgnyteTestBase;
import com.researchspace.egnyte.api2.SpringConfig;

@ContextConfiguration(classes = { SpringConfig.class })
public class SearchRequestValidationTest extends EgnyteTestBase {

	@Before
	public void setUp() {
	}

	@After
	public void cleanUp() {
	}

	@Test
	public void validateQuery() {
		assertEquals(1, getConstraintViolationCount(null));
		assertEquals(2, getConstraintViolationCount(""));
		assertEquals(1, getConstraintViolationCount("1"));
		assertEquals(1, getConstraintViolationCount("2c"));
		assertEquals(0, getConstraintViolationCount("3 or more chars is OK"));
		assertEquals(0, getConstraintViolationCount(randomAlphabetic(99)));
		assertEquals(0, getConstraintViolationCount(randomAlphabetic(100)));
		assertEquals(1, getConstraintViolationCount(randomAlphabetic(101)));
	}

	@Test
	public void validateOtherConstraints() {
		SearchRequest request = createSearchRequest("okterm");
		request.setCount(-1);
		assertEquals(1, validate(request));
		request.setCount(0);
		assertEquals(1, validate(request));
		request.setCount(1);
		assertEquals(0, validate(request));
		request.setCount(21);
		assertEquals(1, validate(request));
		request.setCount(20);
		assertEquals(0, validate(request));
		
		
		request.setOffset(-1);
		assertEquals(1, validate(request));
		request.setOffset(0);
		assertEquals(0, validate(request));	
	}
	private int getConstraintViolationCount(String term) {
		SearchRequest request = createSearchRequest(term);
		return validate(request);
	}

	private int validate(SearchRequest request) {
		Set<ConstraintViolation<SearchRequest>> violations = validator.validate(request);
		return violations.size();
	}

}
