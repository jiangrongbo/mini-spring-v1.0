package com.csii.minispring.context;

import com.csii.minispring.io.FileSystemResource;

public class FileSystemApplicationContext extends AbstractApplicationContext{
	public FileSystemApplicationContext(String configLocation) {
		this.configResource = new FileSystemResource(configLocation);
		refresh();
	}
	public void setConfigResource(FileSystemResource configResource) {
		this.configResource = configResource;
	}
}
