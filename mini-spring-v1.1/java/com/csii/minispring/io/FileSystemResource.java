package com.csii.minispring.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.csii.minispring.utils.AssertUtils;
import com.csii.minispring.utils.StringUtils;
/**
 * FileSystemResource
 * @author Rongbo
 *
 */
public class FileSystemResource extends AbstractResource {
	private final File file;

	private final String path;

	public FileSystemResource(String path) {
		AssertUtils.notNull(path, "Path must not be null");
		this.file = new File(path);
		this.path = StringUtils.cleanPath(path);
	}

	@Override
	public String getDescription() {
		return "file system resource [" + this.path + "]";
	}

	@Override
	public File getFile() throws IOException {
		return this.file;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

}
