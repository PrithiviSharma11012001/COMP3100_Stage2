import java.net.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
// Use ./test-results "java TheClient" -o ru -n -c ../../configs/other/

public class TheClient {

	// Create global variables for the socket, and the input and output, which we
	// will need to write and read messages
	// between server and client
	private Socket socket = null;
	private BufferedReader input = null;
	private DataOutputStream out = null;
	private Server[] servers = new Server[1];
	private String inputString;
	private Boolean finished = false;
	private String[] new_arr;


	public TheClient() {
		try {
			socket = new Socket("localhost", 50000);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException i) {
			System.out.println("Error: " + i);
		} catch (IOException i) {
			System.out.println("Error: " + i);
		}
	}

	
	public static void main(String args[]) {
		TheClient client = new TheClient();
		client.run();
	}

	public void run() {
	
		write("HELO");
		inputString = read();
		
		write("AUTH " + System.getProperty("user.name"));
		
		inputString = read();
		File file = new File("ds-system.xml");
		readFile(file);
		
		write("REDY");
		inputString = read();
		//call to first algorithm.
		alt_alg();
		
		quit();
	}

	//First algorithm
	public void alt_alg() {
		
		if (inputString.equals("NONE")) {
			quit();
		} else {
			
			while (!finished) {
				
				if (inputString.equals("OK") || inputString.equals(".") || inputString.equals(".OK")) {
					write("REDY");
					inputString = read();
				}
				String[] splitMessage = inputString.split("\\s+");
				String firstWord = splitMessage[0];
				while (firstWord.equals("JCPL") || firstWord.equals("RESF") || firstWord.equals("RESR")) {
					write("REDY");
					inputString = read();

					splitMessage = inputString.split("\\s+");
					firstWord = splitMessage[0];
				}
				
				if (firstWord.equals("NONE")) {
					finished = true;
					break;
				}

				String[] jobSections = inputString.split("\\s+");
				serverfinal(jobSections);
				inputString = read();
				String num = jobSections[2];
				String scheduleMessage = "SCHD " + num + " " + new_arr[0] + " " + new_arr[1];
				write(scheduleMessage);
				inputString = read();
			}
		}
	}
	
	
	public void serverfinal(String[] job_info){
				write("GETS Avail " + job_info[4] + " " + job_info[5] + " " + job_info[6]);
				String jobString = read();
				String[] check_info = jobString.split("\\s+");
				int inst = Integer.parseInt(check_info[1]);
				write("OK");
				jobString = read();
				if (jobString.equals(".")) {

					write("GETS Capable " + job_info[4] + " " + job_info[5] + " " + job_info[6]);
					jobString = read();
					check_info = jobString.split("\\s+");
					inst = Integer.parseInt(check_info[1]);

					write("OK");
					jobString = read();
					String[] check_points = jobString.split("\\r?\\n");

					new_arr = check_points[0].split("\\s+");
					servercheck(jobString, inst, check_points);
					
				} else {
					String[] check_points1 = jobString.split("\\r?\\n");
					new_arr = check_points1[0].split("\\s+");
					servercheck(jobString, inst, check_points1);
				}
	}

	//second alg comparing disk, memory and corecount
	public void servercheck(String ds, int inst, String[] l){
		for (int num = 0; num < inst; num++){

			l = ds.split("\\r?\\n");
			String[] sec = l[0].split("\\s+");
			System.out.println(sec);
			if (Integer.parseInt(sec[4]) > Integer.parseInt(new_arr[4]) && Integer.parseInt(sec[5]) > Integer.parseInt(new_arr[5]) && Integer.parseInt(sec[6]) > Integer.parseInt(new_arr[6]) ){
				new_arr = sec;
			}
			
			if (inst - 1 == num){
				write("OK");
				break;
			}
			else {
				ds = read();
			}
		}
	}

	// This method parses through the XML file in t
	public void readFile(File file) {
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document systemDocument = builder.parse(file);
			systemDocument.getDocumentElement().normalize();

			NodeList serverNodeList = systemDocument.getElementsByTagName("server");
			servers = new Server[serverNodeList.getLength()];
			for (int i = 0; i < serverNodeList.getLength(); i++) {
				Element server = (Element) serverNodeList.item(i);
				String t = server.getAttribute("type");
				int c = Integer.parseInt(server.getAttribute("coreCount"));
				int m = Integer.parseInt(server.getAttribute("memory"));
				int d = Integer.parseInt(server.getAttribute("disk"));

				Server temp = new Server(i, t, m,c,d);
				servers[i] = temp;
			}
		} catch (Exception i) {
			i.printStackTrace();
		}

	}

	public String read() {
		String text = "";
		try {
            text = input.readLine();
			inputString = text;
		} catch (IOException i) {
			System.out.println("ERR: " + i);
		}
		return text;
	}


	public void write(String text) {
		try {
			out.write((text + "\n").getBytes());
			out.flush();
		} catch (IOException i) {
			System.out.println("ERR: " + i);
		}
	}

	


	public void quit() {
		try {
			write("QUIT");
			inputString = read();
			if (inputString.equals("QUIT")) {
				input.close();
				out.close();
				socket.close();
			}
		} catch (IOException i) {
			System.out.println("ERR: " + i);
		}
	}

}