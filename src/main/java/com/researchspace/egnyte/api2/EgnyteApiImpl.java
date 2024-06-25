package com.researchspace.egnyte.api2;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.removeEnd;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.researchspace.egnyte.api.clients.auth.Token;
import com.researchspace.egnyte.api.clients.requests.DeleteRequest;
import com.researchspace.egnyte.api.clients.requests.FolderCreateRequest;
import com.researchspace.egnyte.api.clients.requests.MoveRequest;
import com.researchspace.egnyte.api.clients.requests.SearchRequest;
import com.researchspace.egnyte.api.clients.requests.SimpleFileUploadRequest;
import com.researchspace.egnyte.api.clients.responses.EmptyResponse;
import com.researchspace.egnyte.api.clients.responses.FolderCreateResponse;
import com.researchspace.egnyte.api.model.EgnyteSearchResult;
import com.researchspace.egnyte.api.model.EntryType;
import com.researchspace.egnyte.api.model.FileDownloadResult;
import com.researchspace.egnyte.api.model.FileUploadResult;
import com.researchspace.egnyte.api.model.FolderListing;
import com.researchspace.egnyte.api.spring.LoggingRequestInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EgnyteApiImpl implements EgnyteFileApi {
	private RestTemplate restTemplate;
	private URL baseUrl;
	private final static String FILE_CONTENT="/pubapi/v1/fs-content/";
	private final static String FILES="/pubapi/v1/fs";
	private final static String SEARCH="/pubapi/v1/search";
	

	public EgnyteApiImpl(URL baseUrl) {
		 ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new
		 SimpleClientHttpRequestFactory());
		this.restTemplate = new RestTemplate(factory);
		this.baseUrl = baseUrl;
		restTemplate.setInterceptors(Collections.singletonList(new LoggingRequestInterceptor()));
	}

	/* (non-Javadoc)
	 * @see com.researchspace.egnyte.api2.EgnyteApi#createFolder(com.researchspace.egnyte.api.clients.auth.Token, java.lang.String)
	 */
	@Override
	public EgnyteResult<FolderCreateResponse> createFolder(Token token, String egnyteFilePath) {		
		String path = FILES + egnyteFilePath;
		HttpHeaders headers = addAuth(token);
		FolderCreateRequest request = new FolderCreateRequest();
		HttpEntity<FolderCreateRequest> requestEntity = new HttpEntity<>(request, headers);
		EgnyteResult<FolderCreateResponse> response = doRequest(()-> restTemplate.postForEntity(baseUrl + path, requestEntity,
				FolderCreateResponse.class));
		logResponse(response);
		return response;
	}
	
	/* (non-Javadoc)
	 * @see com.researchspace.egnyte.api2.EgnyteApi#listFolder(com.researchspace.egnyte.api.clients.auth.Token, java.lang.String)
	 */
	@Override
	public EgnyteResult<FolderListing>  listFolder(Token token, String egnyteFilePath) {
		String path = FILES + egnyteFilePath;
		HttpHeaders headers = addAuth(token);
		HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);
	
		EgnyteResult<FolderListing> response = doRequest(()-> restTemplate.exchange(baseUrl + path, HttpMethod.GET, requestEntity,
				FolderListing.class));
		logResponse(response);
		return response;		
	}

	
	@FunctionalInterface
	interface Requestor <T>{
		ResponseEntity<T>  request ();
	}
	
	/* (non-Javadoc)
	 * @see com.researchspace.egnyte.api2.EgnyteApi#moveFolder(com.researchspace.egnyte.api.clients.auth.Token, com.researchspace.egnyte.api.clients.requests.MoveRequest)
	 */
	@Override
	public EgnyteResult<EmptyResponse> moveFolder(Token token, MoveRequest moveRequest) {		
		String path = FILES + moveRequest.getSource();
		HttpHeaders headers = addAuth(token);	
		HttpEntity<MoveRequest> requestEntity = new HttpEntity<>(moveRequest, headers);
		EgnyteResult<EmptyResponse> response = doRequest(()-> restTemplate.postForEntity(baseUrl + path, requestEntity,
				EmptyResponse.class));
		return response;
	}

	/* (non-Javadoc)
	 * @see com.researchspace.egnyte.api2.EgnyteApi#deleteItem(com.researchspace.egnyte.api.clients.auth.Token, com.researchspace.egnyte.api.clients.requests.DeleteRequest)
	 */
	@Override
	public EgnyteResult<EmptyResponse> deleteItem(Token token, DeleteRequest deleteRequest) {	
		Validate.isTrue(!isBlank(deleteRequest.getPathToDelete()), "No path supplied to delete");
		String path = FILES + deleteRequest.getPathToDelete();
		HttpHeaders headers = addAuth(token);	
		HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);
		EgnyteResult<EmptyResponse> response = doRequest(()-> restTemplate.exchange(baseUrl + path, HttpMethod.DELETE, requestEntity,
				EmptyResponse.class));
		logResponse(response);
		return response;
	}
	
	/* (non-Javadoc)
	 * @see com.researchspace.egnyte.api2.EgnyteApi#search(com.researchspace.egnyte.api.clients.auth.Token, com.researchspace.egnyte.api.objects.SearchRequest)
	 */
	@Override
	public EgnyteResult<EgnyteSearchResult> search(Token token, SearchRequest searchRequest) {	
		Validate.isTrue(!isBlank(searchRequest.getQuery()), "Please supply a search query");
	
		HttpHeaders headers = new HttpHeaders();
		addBearerTokenHeader(token, headers);	
		HttpEntity<SearchRequest> requestEntity = new HttpEntity<>(null, headers);
		UriComponentsBuilder builder  = UriComponentsBuilder.fromHttpUrl(baseUrl + SEARCH)
		        .queryParam("query", searchRequest.getQuery())
		        .queryParam("type", EntryType.FILE.toString());
		        if(!isBlank(searchRequest.getFolder())) {
		        	//have to remove trailing / or there are no results!
		        	builder.queryParam("folder", removeEnd(searchRequest.getFolder(), "/"));
		        }  
		        URI uri = builder .build(true).toUri();
		log.info("Searching for url {}", uri.toString());
		EgnyteResult<EgnyteSearchResult> response =  
				doRequest(()-> restTemplate.exchange(uri, HttpMethod.GET, requestEntity, EgnyteSearchResult.class));
		logResponse(response);
		return response;
	}
	
	/* (non-Javadoc)
	 * @see com.researchspace.egnyte.api2.EgnyteApi#uploadFile(com.researchspace.egnyte.api.clients.auth.Token, com.researchspace.egnyte.api.clients.requests.SimpleFileUploadRequest)
	 */
	@Override
	public EgnyteResult<FileUploadResult> uploadFile(Token token, SimpleFileUploadRequest toUpload) {	
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", new FileSystemResource(toUpload.getFile()));
        HttpHeaders headers = addAuth(token);	
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String path = FILE_CONTENT + toUpload.getPath();
		HttpEntity<FileSystemResource>  entity = new HttpEntity<>(new FileSystemResource(toUpload.getFile()), headers);
		
		EgnyteResult<FileUploadResult> response = doRequest(()-> restTemplate.postForEntity(baseUrl + path, entity,
				FileUploadResult.class));
		logResponse(response);
		return response;
	}
	
	/* (non-Javadoc)
	 * @see com.researchspace.egnyte.api2.EgnyteApi#downloadFile(com.researchspace.egnyte.api.clients.auth.Token, java.lang.String)
	 */
	@Override
	public EgnyteResult<FileDownloadResult> downloadFile(Token token, String pathToFileToDownload) {	
		
        String path = FILE_CONTENT + pathToFileToDownload;
		// Optional Accept header
		RequestCallback requestCallback = request -> {request.getHeaders()
		        .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
		     addBearerTokenHeader(token, request.getHeaders());	
		};

		// Streams the response instead of loading it all in memory
		ResponseExtractor<FileDownloadResult> responseExtractor = response -> {
			File tempFile = File.createTempFile("egnyte", FilenameUtils.getName(pathToFileToDownload));
			IOUtils.copy(response.getBody(), new FileOutputStream(tempFile));
		    return new FileDownloadResult(tempFile);
		};
	
		EgnyteResult<FileDownloadResult> res = doRequest(()->{
			FileDownloadResult result = restTemplate.execute(baseUrl + path, HttpMethod.GET, requestCallback, responseExtractor);
		    return new ResponseEntity<>(result, HttpStatus.OK);
		});
		return res;
	}
	
	private<T> EgnyteResult<T> createErrorObject(HttpStatusCodeException e) {
		ResponseError error = new ResponseError(e.getStatusCode(),e.getMessage(),e.getResponseBodyAsString());
		return new EgnyteResult<>(error);
	}
	
	private HttpHeaders addAuth(Token token) {
		HttpHeaders headers = new HttpHeaders();
		addBearerTokenHeader(token, headers);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	private void addBearerTokenHeader(Token token, HttpHeaders headers) {
		headers.add("Authorization", "Bearer " + token.getAccess_token());
	}
	
	private void logResponse(EgnyteResult<?> response) {
		if(response.isSuccessful()) {
			log.info(response.getSuccessResult().getBody()!=null?response.getSuccessResult().getBody().toString():"empty body");
		} else {
			log.info(response.getError().getMessage());
		}
	}
	
	<T>EgnyteResult<T> doRequest (Requestor<T> req) {
		try {
			ResponseEntity<T> resp = req.request();
			return new EgnyteResult<>(resp);
		}catch (HttpClientErrorException | HttpServerErrorException e) {
			return createErrorObject(e);
		}catch (ResourceAccessException e) {
			ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST,e.getMessage(), "{\"message\": \"i/o exception\"}");
			return new EgnyteResult<>(error);
		}catch (RestClientException e) {
			ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST,e.getMessage(), "{\"message\": \"unknown rest client exception\"}");
			return new EgnyteResult<>(error);
		}catch (Exception e) {
			ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST,e.getMessage(), "{\"message\": \"unknown general exception\"}");
			return new EgnyteResult<>(error);
		}
	}

}
