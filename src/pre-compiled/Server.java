public class Server {

	public int id;
	public String type;
	public int memory;
	public int cores;
	public int disk;



	Server(int id, String t, int m, int c, int d) {
		this.id = id;
		this.type = t;
		this.memory = m;
		this.cores = c;
		this.disk = d;
	}
}