package b100.asmloader.compat;

import java.lang.instrument.Instrumentation;
import java.net.URLClassLoader;

import b100.asmloader.ASMLoader;

public class BetaCraftIntegration {
	
	private static BetaCraftIntegration instance;
	
	private ASMLoader asmLoader;
	
	private URLClassLoader classLoader;
	
	public BetaCraftIntegration(ASMLoader asmLoader, Instrumentation instrumentation) {
		if(instance != null) {
			throw new IllegalStateException();
		}
		instance = this;
		this.asmLoader = asmLoader;
		
		
	}
	
	public static void preLaunch(URLClassLoader classLoader) {
		instance.classLoader = classLoader;
		
		instance.asmLoader.loadMods(classLoader);
	}

}
