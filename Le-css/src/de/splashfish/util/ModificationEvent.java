package de.splashfish.util;

import java.io.File;

public class ModificationEvent {

	private File[] files;

	public ModificationEvent(File[] files) {
		this.files = files;
	}

	public File[] getFiles() {
		return files;
	}
	
}
