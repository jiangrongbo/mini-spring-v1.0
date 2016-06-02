package com.csii.minispring.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.csii.minispring.utils.AssertUtils;
import com.csii.minispring.utils.ClassUtils;
import com.csii.minispring.utils.StringUtils;

public class ClassPathResource extends AbstractResource {

	private ClassLoader classLoader;

	private Class clazz;

	public ClassPathResource(String path) {
		this(path, (ClassLoader) null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		AssertUtils.notEmpty(path, "path must not be null or empty.");
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		this.path = StringUtils.cleanPath(path);
		this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
	}

	public ClassPathResource(String path, Class clazz) {
		AssertUtils.notEmpty(path, "path must not be null or empty.");
		this.path = StringUtils.cleanPath(path);
		this.clazz = clazz;
	}

	public InputStream getInputStream() throws IOException {
		InputStream inputStream = null;
		if (this.clazz != null) {
			inputStream = this.clazz.getResourceAsStream(this.path);
		} else {
			inputStream = this.classLoader.getResourceAsStream(this.path);
		}
		if (inputStream == null) {
			throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist");
		}
		return inputStream;
	}

	@Override
	public File getFile() throws IOException {
		URL fileLocation = null;
		if (this.clazz != null) {
			fileLocation = this.clazz.getResource(this.path);
		} else {
			fileLocation = this.classLoader.getResource(this.path);
		}
		return new File(fileLocation.getFile());
	}


}
