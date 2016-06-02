package com.csii.minispring.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.csii.minispring.utils.AssertUtils;
import com.csii.minispring.utils.StringUtils;

/**
 * FileSystemResource
 * 
 * @author Rongbo
 * 
 */
public class FileSystemResource extends AbstractResource {

	public FileSystemResource(String path) {
		AssertUtils.notEmpty(path, "Path must not be null");
		this.path = StringUtils.cleanPath(path);
	}

	@Override
	public File getFile() throws IOException {
		return new File(path);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(new File(path));
	}

}
