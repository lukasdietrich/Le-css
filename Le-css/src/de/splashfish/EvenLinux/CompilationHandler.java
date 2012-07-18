package de.splashfish.EvenLinux;

import java.io.IOException;
import java.util.ArrayList;

import org.lesscss.LessCompiler;
import org.lesscss.LessException;

import de.splashfish.util.FileShift;

public class CompilationHandler {

	private LessCompiler 				compiler;
	
	private ArrayList<ItemDefinition> 	queue 		= new ArrayList<ItemDefinition>();
	private boolean 					active 		= false;
	
	private OverviewPane				parent;
	
	public CompilationHandler(LessCompiler compiler, OverviewPane parent) {
		this.parent = parent;
		this.compiler = compiler;
	}
	
	public void addToQueue(ItemDefinition fs) {
		if(!queue.contains(fs)) {
			queue.add(fs);
			invokeQueue();
		}
	}
	
	private void invokeQueue() {
		if(!active && queue.size() > 0) {
			active = true;
			new Thread() {
				@Override public void run() {
					parent.toggleProcess(true);
					compiler.init();
					while(!queue.isEmpty()) {
						ItemDefinition def = queue.get(0);
						FileShift shift = def.getShift();
						queue.remove(0);
						
						def.setState(ItemDefinition.WAIT);
						
						try {
							compiler.compile(shift.get(FileShift.FROM_FILE), shift.get(FileShift.TO_FILE), true);
							def.setState(ItemDefinition.TRUE);
							def.setUpdate(shift.get(FileShift.TO_FILE).lastModified());
						} catch (IOException e) {
							def.setError(e.getLocalizedMessage());
						} catch (LessException e) {
							def.setError(e.getLocalizedMessage());
						}
					}
					
					parent.toggleProcess(false);
					active = false;
					invokeQueue();
				}
			}.start();
		}
	}
	
}
