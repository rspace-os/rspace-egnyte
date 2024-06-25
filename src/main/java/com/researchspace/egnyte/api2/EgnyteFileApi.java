package com.researchspace.egnyte.api2;

import com.researchspace.egnyte.api.clients.auth.Token;
import com.researchspace.egnyte.api.clients.requests.DeleteRequest;
import com.researchspace.egnyte.api.clients.requests.MoveRequest;
import com.researchspace.egnyte.api.clients.requests.SearchRequest;
import com.researchspace.egnyte.api.clients.requests.SimpleFileUploadRequest;
import com.researchspace.egnyte.api.clients.responses.EmptyResponse;
import com.researchspace.egnyte.api.clients.responses.FolderCreateResponse;
import com.researchspace.egnyte.api.model.EgnyteSearchResult;
import com.researchspace.egnyte.api.model.FileDownloadResult;
import com.researchspace.egnyte.api.model.FileUploadResult;
import com.researchspace.egnyte.api.model.FolderListing;
/**
 * Interface for Egnyte API file-related calls.
 * <p>
 * All methods require a valid access token.
 */
public interface EgnyteFileApi {

	/**
	 * Creates a new folder at the given path
	 * @param token
	 * @param egnyteFilePath
	 * @return
	 */
	EgnyteResult<FolderCreateResponse> createFolder(Token token, String egnyteFilePath);

	/**
	 * List contents of a folder
	 * @param token
	 * @param egnyteFilePath
	 * @return
	 */
	EgnyteResult<FolderListing> listFolder(Token token, String egnyteFilePath);

	/**
	 * Moves folder to new location
	 * @param token
	 * @param moveRequest
	 * @return
	 */
	EgnyteResult<EmptyResponse> moveFolder(Token token, MoveRequest moveRequest);

	/**
	 * Removes folder or file
	 * @param token
	 * @param deleteRequest
	 * @return
	 */
	EgnyteResult<EmptyResponse> deleteItem(Token token, DeleteRequest deleteRequest);

	/**
	 * 
	 * @param token
	 * @param searchRequest
	 * @return
	 * @throws IllegalArgumentException if search term is empty
	 */
	EgnyteResult<EgnyteSearchResult> search(Token token, SearchRequest searchRequest);

	/**
	 * Uploads file to a specific location
	 * @param token
	 * @param toUpload
	 * @return
	 */
	EgnyteResult<FileUploadResult> uploadFile(Token token, SimpleFileUploadRequest toUpload);

	/**
	 * Downloads file from Egnyte to a local temp file
	 * @param token
	 * @param pathToFileToDownload
	 * @return An EgnyteResult<FileDownloadResult> containing the downloaded file
	 */
	EgnyteResult<FileDownloadResult> downloadFile(Token token, String pathToFileToDownload);

}