package b100.asmloader;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Mod {
	
	public final String modid;
	public final File file;
	public Set<String> dependencies = new HashSet<>();
	
	public Mod(String modid, File modfile) {
		this.modid = modid;
		this.file = modfile;
	}
	
	@Override
	public int hashCode() {
		return modid.hashCode();
	}

}
