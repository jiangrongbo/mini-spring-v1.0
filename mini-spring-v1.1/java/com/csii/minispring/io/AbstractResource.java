package com.csii.minispring.io;

import java.io.IOException;

public abstract class AbstractResource implements Resource {

	@Override
	public boolean exists() { 
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isReadable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isOpen() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long lastModified() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFilename() {
		throw new UnsupportedOperationException();
	}
 
}
