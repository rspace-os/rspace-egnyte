package com.researchspace.egnyte.api2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import com.researchspace.egnyte.api.clients.requests.DeleteRequest;
import com.researchspace.egnyte.api.clients.requests.MoveRequest;
import com.researchspace.egnyte.api.clients.requests.SearchRequest;
import com.researchspace.egnyte.api.clients.requests.SimpleFileUploadRequest;
import com.researchspace.egnyte.api.clients.responses.EmptyResponse;
import com.researchspace.egnyte.api.clients.responses.FolderCreateResponse;
import com.researchspace.egnyte.api.model.EgnyteSearchResult;
import com.researchspace.egnyte.api.model.FolderListing;

import lombok.extern.slf4j.Slf4j;

@Ignore("requires accessToken in test.properties")
@ContextConfiguration(classes = {SpringConfig.class })
@Slf4j
public class FolderAcceptanceTests extends EgnyteTestBase {
	
	private File textFile = new File("src/test/resources/testFiles/textFile.txt");
	private String searchTerm = "hypothetical";

	@Test
	public void listFolder() throws EgnyteException {
		String path = topLevelTestFolder + "listFolderTest";
	    createFolder(path);
		EgnyteResult<FolderListing> listing = egnyteApi.listFolder(token, topLevelTestFolder);
		assertTrue(listing.getResult().getTotalCount() > 0);	
	}

	@Test
	public void createFolder() throws EgnyteException {
		String path = topLevelTestFolder + "createFolderTest";
		EgnyteResult<FolderCreateResponse> folder = createFolder(path);
		log.info(folder.toString());
		assertNotNull( folder.getResult().getFolderID());		
	}

	@Test
	public void moveFolder() throws EgnyteException {
		String path = topLevelTestFolder + "test_move";
		EgnyteResult<FolderCreateResponse> toMove = createFolder(path);
		String destination = topLevelTestFolder + "test_moved";
		EgnyteResult<FolderCreateResponse> toMoveDest = createFolder(destination);
		MoveRequest request = new MoveRequest(path, destination);
	  EgnyteResult<EmptyResponse> resp = egnyteApi.moveFolder(token, request);
		assertEquals( HttpStatus.OK, resp.getStatusCode());
		assertTrue( resp.isSuccessful());
	}

	@Test
	public void deleteFolder() throws EgnyteException {
		String path = topLevelTestFolder + "test_delete";
		EgnyteResult<FolderCreateResponse> folder = createFolder(path);
		assertNotNull(folder.getResult().getFolderID());

		DeleteRequest request = new DeleteRequest(path);
		EgnyteResult<EmptyResponse> resp = egnyteApi.deleteItem(token, request);
		assertEquals( HttpStatus.OK, resp.getStatusCode());
		assertTrue( resp.isSuccessful());
	}

	@Test
	public void searchTestByFile() throws EgnyteException {
		String path = topLevelTestFolder + "test_search";
		createFolder(path);
		SearchRequest srch = createSearchRequest("test");
		EgnyteResult<EgnyteSearchResult> resp = egnyteApi.search(token, srch);
		assertTrue(resp.getResult().getTotalCount() > 0);
		assertTrue( resp.isSuccessful());
	}
	
	@Test
	public void searchTestByFullText() throws EgnyteException, InterruptedException {
		SearchRequest srch = createSearchRequest("textFile");
		Long initialHitCount = egnyteApi.search(token, srch).getResult().getTotalCount();
		egnyteApi.uploadFile(token,
				new SimpleFileUploadRequest(topLevelTestFolder +"/" + textFile.getName(), textFile));
  
		EgnyteResult<EgnyteSearchResult> resp = egnyteApi.search(token, srch);
		assertTrue( resp.isSuccessful());
		assertTrue(isTrueEventually(()->  getSearchHitCount(srch) 
				>= initialHitCount +1, 10, 20));		
	}
	
	@Test
	public void searchTestByFullTextRestrictedToFolder() throws EgnyteException, InterruptedException {
		SearchRequest srch = createSearchRequest(searchTerm);
		Long initialHitCount = egnyteApi.search(token, srch).getResult().getTotalCount();
		uploadFileToFolder("folder1");
		uploadFileToFolder("folder2");
		// unrestricted search produces 2 hits
		assertTrue(isTrueEventually(()->  getSearchHitCount(srch) 
				>= initialHitCount + 2, 10, 20));
		//search top level folder....should have both hits. Search should handle /
		srch.setFolder(StringUtils.removeEnd(topLevelTestFolder, "/"));
		assertEquals(2, getSearchHitCount(srch));
		srch.setFolder(topLevelTestFolder);
		assertEquals(2, getSearchHitCount(srch));
		
		//search 1 folder.... get 1 hit:
		srch.setFolder(topLevelTestFolder + "folder1");
		assertEquals(1, getSearchHitCount(srch));
		// now create empty folder, should be no hits
		egnyteApi.createFolder(token, topLevelTestFolder +"folder3");
		srch.setFolder(topLevelTestFolder  + "folder3");
		assertEquals(0, getSearchHitCount(srch));
	}

	private long getSearchHitCount(SearchRequest srch) {
		return egnyteApi.search(token, srch).getResult().getTotalCount().longValue();
	}

	private void uploadFileToFolder(String folder) {
		egnyteApi.uploadFile(token,
				new SimpleFileUploadRequest(topLevelTestFolder  + folder +"/" + textFile.getName(), textFile));
	}
}
