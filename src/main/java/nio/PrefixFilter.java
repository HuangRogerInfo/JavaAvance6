package nio;

import java.io.IOException;
import java.nio.file.*;

public class PrefixFilter implements DirectoryStream.Filter<Path> {
	private int n;
	
	public PrefixFilter(int n) {
		// TODO Auto-generated constructor stub
		this.n = n;
	}

	public boolean accept(Path entry) throws IOException {
		if(Files.size(entry) < n) {
			return false;
		}
		return true;
	}

}
