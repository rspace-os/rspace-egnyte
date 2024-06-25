package com.researchspace.egnyte.api.clients.requests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.researchspace.egnyte.api2.EgnyteTestBase;
import com.researchspace.egnyte.api2.SpringConfig;

@ContextConfiguration(classes = { SpringConfig.class })
public class MoveRequestValidationTest extends EgnyteTestBase {

	@Before
	public void setUp() {	}

	@After
	public void cleanUp() {	}

	@Test
	public void validateQuery() {
		assertEquals(2, getConstraintViolationCount(null, null));
		assertEquals(1, getConstraintViolationCount(null, "/"));
		assertEquals(1, getConstraintViolationCount("a", null));
		assertEquals(0, getConstraintViolationCount("/a", "/"));		
	}

	private int getConstraintViolationCount(String source, String dest) {
		MoveRequest request = new MoveRequest(source, dest);
		return validate(request);
	}

}
