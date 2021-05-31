import java.net.*;
import java.io.*;
import java.util.ArrayList; 




public class new_alg{

    private Socket socket = null;
	private BufferedReader in = null;
	private DataOutputStream out = null;
    private Server[] servers; 
    private ArrayList<Server> arr_list_server = new ArrayList<Server>();
    private String newstring;
    private String[] split;
    private String starttime;
    






    new_alg(Server[] servers, ArrayList<Server> arr_list_server){
        this.servers = servers;
        this.arr_list_server = arr_list_server;
    }

    public new_alg(){

        try{

            socket = new Socket("localhost", 50000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream()); 

        }catch (UnknownHostException i) {
			System.out.println("Error: " + i);
		} catch (IOException i) {
			System.out.println("Error: " + i);
		}

    }


//Variation of the first fit algorithm where the start time is determined using
//LSTJ and used to run all jobs in parallel.

public Server processed_alg(given_job dissected_job){

    Server[] sorted  = server_sort(servers);
    Server temp = null;
    for(Server server: sorted){
        for(Server server1: arr_list_server){

            if(server.type == server1.type){
                
                if(server1.core_count >= dissected_job.core && server1.resource_disk >= dissected_job.disk && server1.resource_memory >= dissected_job.mem && server1.state != 4){
                    return server1;


                }
                
            //area where the start time of the job within the server is found.
            }else if(server.type != server1.type && server1.core_count >= dissected_job.core && server1.resource_disk >= dissected_job.disk && server1.resource_memory >= dissected_job.mem && server1.state != 4){

                write("LSTJ" + " " + server1.id + " " + server1.type);
                newstring = read();
                split = newstring.split("\\s+");
                starttime = split[2];
                

            } 
            
            

        }
        
        for(Server server2: servers){
        if(server2.core_count >= dissected_job.core && server2.resource_disk >= dissected_job.disk && server2.resource_memory >= dissected_job.disk && server2.state != 4){
            temp = server2;
            temp.id = 0;
            return temp;
        }
     

    }
    
}
return null;


}


//selection sort used to sort the server array in ascending order.

public Server[] server_sort(Server server[]){

    int num_length = server.length;

    for(int num = 0; num < num_length - 1; num++){

        int firstcheck = num; 
        for(int num1 = num + 1; num1 < num_length; num1++){

            if(server[num1].core_count < server[firstcheck].core_count){

                server[num] = server[num1];

            }
            Server temp = server[firstcheck];
            server[firstcheck] = server[num];
            server[num] = temp;
        }

    }

    return server;

}

public void write(String text) {
    try {
        out.write((text + "\n").getBytes());
        // System.out.print("SENT: " + text);
        out.flush();
    } catch (IOException i) {
        System.out.println("ERR: " + i);
    }
}

public String read() {
    String text = "";
    try {
        text = in.readLine();
        // System.out.print("RCVD: " + text);
        newstring = text;
    } catch (IOException i) {
        System.out.println("ERR: " + i);
    }
    return text;
}

}