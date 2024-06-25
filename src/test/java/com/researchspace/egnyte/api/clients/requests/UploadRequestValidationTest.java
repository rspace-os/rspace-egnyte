package com.researchspace.egnyte.api.clients.requests;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.researchspace.egnyte.api2.EgnyteTestBase;
import com.researchspace.egnyte.api2.SpringConfig;

@ContextConfiguration(classes = { SpringConfig.class })
public class UploadRequestValidationTest extends EgnyteTestBase {

	@Before
	public void setUp() {	}

	@After
	public void cleanUp() {	}

	@Test
	public void validateQuery() {
		assertEquals(2, getConstraintViolationCount(null, null));
		assertEquals(1, getConstraintViolationCount(null, "/"));
		assertEquals(1, getConstraintViolationCount(new File("someFile.txt"), ""));
		assertEquals(0, getConstraintViolationCount(new File("someFile.txt"), "/"));		
	}

	private int getConstraintViolationCount(File file, String path) {
		SimpleFileUploadRequest request = new SimpleFileUploadRequest(path, file);
		return validate(request);
	}

}
