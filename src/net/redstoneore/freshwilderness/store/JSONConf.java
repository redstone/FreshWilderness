package net.redstoneore.freshwilderness.store;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import net.redstoneore.freshwilderness.FreshWilderness;

public abstract class JSONConf<T extends JSONConf<T>> {
	
	// Convert the class to a string, required to POJO
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Field field : this.getClass().getFields()) {
			if (sb.length() > 1) sb.append(", ");
			
			try {
				sb.append(field.getName() + "=" + field.get(this.getClass()).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return this.getClass().getName() + " [" + sb.toString() + "]";
	}
	
	// Convert the class to Json
	public final String toJson() {
		return FreshWilderness.get().getGson().toJson(this);
	}
	
	// Save to a path
	public final Boolean saveTo(Path path) throws IOException {
		byte[] data = this.toJson().getBytes(Charset.defaultCharset());
		
		Files.write(path, data);
		
		return false;
	}
	
	// Load from a path
	public final Boolean loadFrom(Path path) throws IOException {
		
		if (! Files.exists(path)) {
			Files.createDirectories(path.getParent());
			return false;
		}
		
		String data = new String(Files.readAllBytes(path));
		
		JSONConf<?> res = (JSONConf<?>) FreshWilderness.get().getGson().fromJson(data, getClass());
		
		for (Field field : res.getClass().getFields()) {
			Field ourField;
			
			try {
				ourField = this.getClass().getField(field.getName());
				
				if (ourField == null) continue;
				ourField.set(this, field.get(res));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		res = null;
		
		return false;
	}
	
}