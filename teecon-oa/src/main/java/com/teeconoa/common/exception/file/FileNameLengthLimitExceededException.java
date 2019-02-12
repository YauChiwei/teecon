package com.teeconoa.common.exception.file;

import org.apache.commons.fileupload.FileUploadException;

/**
*  Created by AndyYau
*  2019年2月7日 - 下午4:21:35
*  Company: Teecon
**/
public class FileNameLengthLimitExceededException extends FileUploadException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int length;
	private int maxLength;
	private String fileName;
	
	public FileNameLengthLimitExceededException(String fileName, int length, int maxLength) {
		super("file name : [" + fileName + "], length : [" + length + "], max length : [" + maxLength + "]");
		this.fileName = fileName;
		this.length = length;
		this.maxLength = maxLength;
	}

	public int getLength() {
		return length;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public String getFileName() {
		return fileName;
	}
	
	
}
