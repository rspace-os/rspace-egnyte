package com.researchspace.egnyte.api2;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.function.Supplier;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.researchspace.egnyte.api.clients.auth.Token;
import com.researchspace.egnyte.api.clients.requests.DeleteRequest;
import com.researchspace.egnyte.api.clients.requests.SearchRequest;
import com.researchspace.egnyte.api.clients.responses.EmptyResponse;
import com.researchspace.egnyte.api.clients.responses.FolderCreateResponse;

import lombok.extern.slf4j.Slf4j;
/**
 * Base test class for Egnyte api acceptance tests
 * <br>
 * Sets access token and base URL from properties
 * <br>
 * Also creates a uniquely named folder in /Shared/RSpaceTest/
 */
@TestPropertySource(locations="classpath:/test.properties")
@Slf4j
public abstract class EgnyteTestBase extends AbstractJUnit4SpringContextTests {

    // set this on command line or via VM argument -DaccessToken=tju5wk4bfu4kr46tyafvewzm
    @Value("${accessToken}")
    private String accessToken="";
    private @Autowired Environment env;
    private static URL EGNYTE_URL = null;
    
     
	protected @Autowired Validator validator;
    
     // proxy to interpose a delay between requests
    class DelayAPIExecutor <T> implements InvocationHandler {
    	// 2 calls per second max.
 		private static final int API_CALL_DELAY = 750;
 		private T object;
 		public DelayAPIExecutor(T object) {
 			super();
 			this.object = object;
 		}
 		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
 			Thread.sleep(API_CALL_DELAY);
 			try {
 				return method.invoke(object, args);
 			} catch (InvocationTargetException e) {
 				throw e.getTargetException();
 			}
 		}
 	}

    EgnyteFileApi setUpDelayExecutingProxy(Class<?> clazz) {
		return (EgnyteFileApi)Proxy
                .newProxyInstance(clazz.getClassLoader(),
                		egnyteApi.getClass().getInterfaces(), new DelayAPIExecutor<>(egnyteApi));
	}
   
    Token token;

    String topLevelTestFolder = "/Shared/RSpaceTest/" + RandomStringUtils.randomAlphanumeric(5) + "/";
    EgnyteFileApi egnyteApi;


    @Before
    public void setUp () throws MalformedURLException, EgnyteException, InterruptedException {
        EGNYTE_URL = new URL(env.getProperty("egnyte.url"));
        token = new Token(accessToken, -1, "bearer");
        egnyteApi = new EgnyteApiImpl(EGNYTE_URL); 
        egnyteApi = setUpDelayExecutingProxy(getClass());
        // create tests folder
        createFolder(topLevelTestFolder);
    }

    @After
    public void cleanUp() throws EgnyteException, InterruptedException {
        deleteFolder(topLevelTestFolder);
    }

    /**
     * Creates a folder at given path
     * @param path
     * @return
     */
    EgnyteResult<FolderCreateResponse> createFolder(String path) {
       return egnyteApi.createFolder(token, path);
    }

    /**
     * Deletes folder from given path
     * @param path
     * @return
     */
    HttpStatus deleteFolder(String path) {
        DeleteRequest request = new DeleteRequest(path);
        EgnyteResult<EmptyResponse> resp = egnyteApi.deleteItem(token, request);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        return resp.getStatusCode();
    }
    /**
     * Invokes a Supplier that is expected to return true within the given number of attempts
     * @param isTrue
     * @param numRetries the number of attempts to make to call the supplier
     * @param pollIntervalSeconds the interval between calling the supplier, in seconds
     * @return <code>true</code> if the condition returns true within the number of attempts, false otherwise
     * @throws InterruptedException
     */
    public <T> Boolean isTrueEventually(Supplier<Boolean> isTrue, int numRetries, int pollIntervalSeconds) throws InterruptedException {
    	int currTry = 0;
    	while (currTry < numRetries) {
    		Thread.sleep(pollIntervalSeconds * 1000);
    		Boolean response = isTrue.get();
    		if (response) {
    			return Boolean.TRUE;
    		}
    		currTry++;
    	}
    	return Boolean.FALSE;
    }
    
	 protected SearchRequest createSearchRequest(String term) {
		return SearchRequest.builder().query(term).build();
	}
	 
	 protected <T> int validate(T request) {
			Set<ConstraintViolation<T>> violations = validator.validate(request);
			return violations.size();
		}


}
