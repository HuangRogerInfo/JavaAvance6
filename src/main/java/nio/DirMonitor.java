package nio;

import java.io.IOException;
import java.nio.file.*;
import java.util.Iterator;

public class DirMonitor {
	private Path p;
	
	class PrefixFilter2 implements DirectoryStream.Filter<Path> {
		private int n;
		
		public PrefixFilter2(int n) {
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
	
	public DirMonitor(Path p) throws IOException {
		// TODO Auto-generated constructor stub
		if(!Files.isReadable(p) || !Files.isDirectory(p)) {
			throw new IOException();
		}
		
		this.p = p;
	}
	
	public void applyAction(String prefix, MyAction action) throws IOException{
		Path p = Paths.get(prefix);
		action.perform(p);
	}
	
	public void afficherFichiers() throws IOException {
		
		DirectoryStream.Filter<Path> pf = new DirectoryStream.Filter<Path>() {
		    public boolean accept(Path entry) throws IOException {
				if(Files.size(entry) < 554) {
					return false;
				}
				return true;
			}
		};
		
		DirectoryStream<Path> stream = Files.newDirectoryStream(p, pf);
		
		Iterator<Path> iterator = stream.iterator();
		
		while(iterator.hasNext()) {
			Path p = iterator.next();
			System.out.println(p); 
		}
		
        stream.close(); 
	}
	
	/*public void afficherFichiers2() throws IOException {
		DirectoryStream.Filter<Path> pf = new DirectoryStream.Filter<Path>() {
		    public boolean accept(Path entry) throws IOException {
				if(Files.size(entry) < 554) {
					return false;
				}
				return true;
			}
		};
		
		DirectoryStream<Path> stream = Files.newDirectoryStream(p, pf);
		
		Iterator<Path> iterator = stream.iterator();
		
		while(iterator.hasNext()) {
			Path p = iterator.next();
			this.applyAction(p.toString(),new MyAction() {

				public void perform(Path p) throws IOException {
					// TODO Auto-generated method stub
					
				}	
			});
			System.out.println(p); 
		}
        stream.close(); 
	}*/
	
	public int sizeOfFiles() throws IOException {
		int somme = 0;
		DirectoryStream<Path> stream = Files.newDirectoryStream(p);
		Iterator<Path> iterator = stream.iterator();
		
		while(iterator.hasNext()) {
			Path p = iterator.next();
			if(Files.isRegularFile(p)) {
				somme+= Files.size(p);
			}
		}
		
        stream.close(); 
		
		return somme;
	}
	
	public Path mostRecent() throws IOException {
		DirectoryStream<Path> stream = Files.newDirectoryStream(p);
		Iterator<Path> iterator = stream.iterator();
		
		Path preturn = iterator.next();
		while(iterator.hasNext()) {
			Path p = iterator.next();
			if(Files.getLastModifiedTime(preturn).compareTo(Files.getLastModifiedTime(p))<=0) {
				preturn = p;
			}
		}
		
		return preturn;
	}
	
	public static void main(String[] args) {
		Path p = Paths.get(".");
		
		try{
			DirMonitor dm = new DirMonitor(p);
			dm.afficherFichiers();
			System.out.println(dm.sizeOfFiles());
			System.out.println(dm.mostRecent());
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
