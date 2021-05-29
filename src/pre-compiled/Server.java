

public class Server {

	public int id;
	public String type;
	public int server_limit;
	public int time_for_bootup; 
	public float hourly_rate;
	public int core_count;
	public int resource_memory; 
	public int resource_disk;
	public int state;


	public Server(int id, String t, int sl, int tfb, float hr, int c, int rm, int rd, int st) {
		this.id = id;
		this.type = t;
		this.server_limit = sl;
		this.time_for_bootup = tfb;
		this.hourly_rate = hr;
		this.core_count = c;
		this.resource_memory = rm; 
		this.resource_disk = rd;
		this.state = st;
	}


}