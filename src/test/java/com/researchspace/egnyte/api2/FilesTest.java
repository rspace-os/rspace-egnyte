package com.researchspace.egnyte.api2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.researchspace.egnyte.api.clients.requests.SimpleFileUploadRequest;
import com.researchspace.egnyte.api.model.FileDownloadResult;
import com.researchspace.egnyte.api.model.FileUploadResult;

import lombok.extern.slf4j.Slf4j;

@Ignore("requires accessToken in test.properties")
@ContextConfiguration(classes = {SpringConfig.class })
@Slf4j
public class FilesTest extends EgnyteTestBase {

    private static final String testFile = "src/test/resources/testFiles/testSmall.jpg";

    @Test
    public void fileUploadDownloadRoundTrip()  {
    	File originalfile = new File(testFile);
    	SimpleFileUploadRequest uploadRequest = new SimpleFileUploadRequest(topLevelTestFolder+"/"+originalfile.getName(), originalfile);
    	EgnyteResult<FileUploadResult> result = egnyteApi.uploadFile(token, uploadRequest);
    	assertNotNull(result.getResult().getChecksum());
    
    	EgnyteResult<FileDownloadResult>  downloaded = egnyteApi.downloadFile(token, topLevelTestFolder + "/" + originalfile.getName()); 
      log.info("Downloaded file to {}", downloaded.getResult().getDownloaded().getAbsolutePath());
    	// assert originalFile && downloadedFile are same length
    	assertEquals(originalfile.length(), downloaded.getResult().getDownloaded().length());
    }

}
