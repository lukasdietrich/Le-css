package de.splashfish.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class ModificationWatcher {

	private long period;
	private Timer timer;
	public ModificationWatcher(int checktime) {
		period = checktime * 1000;
		timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override public void run() {
				ArrayList<File> hasChanged = new ArrayList<File>();
				for(FileWatcher watcher : watchers) {
					if(watcher.hasChanged())
						hasChanged.add(watcher.getFile());
				}
				
				File[] changedFiles = new File[hasChanged.size()];
				changedFiles = hasChanged.toArray(changedFiles);
				
				for(ModificationListener t : listeners){
                    t.fileModified(new ModificationEvent(changedFiles));
				}
			}
		}, 10000, period);
	}
	
	private ArrayList<FileWatcher> watchers = new ArrayList<FileWatcher>();
	public void addWatcher(File file) {
		watchers.add(new FileWatcher(file));
	}
	
	public void removeWatcher(File file) {
		for(int i = 0; i < watchers.size(); i++) {
			if(watchers.get(i).getFile().equals(file)) {
				watchers.remove(i);
				break;
			}
		}
	}
	
	// Listeners
	private LinkedList<ModificationListener> listeners = new LinkedList<ModificationListener>();

	public void addModificationListener(ModificationListener listener) {
		listeners.add(listener);
	}

	public boolean removeModificationListener(ModificationListener listener) {
		return listeners.remove(listener);
	}
	
}

class FileWatcher {
	
	private long timestamp;
	private File file;
	
	public FileWatcher(File file) {
		this.file = file;
		this.timestamp = file.lastModified();
	}
	
	public boolean hasChanged() {
		long current = file.lastModified();
		if(current != timestamp) {
			timestamp = current;
			return true;
		}
		return false;
	}
	
	public File getFile() {
		return file;
	}
	
}