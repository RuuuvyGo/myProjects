package model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileMap {
	
	private Map<String,File> fileMap;

	public FileMap() {
		this.fileMap = new HashMap<String, File>(); 
	}

	public FileMap(Map<String, File> fileMap) {
		this.fileMap = fileMap;
	}
	
}
