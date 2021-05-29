import java.util.ArrayList; 




public class new_alg{

    private Server[] servers; 
    private ArrayList<Server> arr_list_server = new ArrayList<Server>();






    new_alg(Server[] servers, ArrayList<Server> arr_list_server){
        this.servers = servers;
        this.arr_list_server = arr_list_server;
    }



public Server processed_alg(given_job dissected_job){

    Server[] sorted  = server_sort(servers);
    for(Server server: sorted){
        for(Server server1: servers){

            if(server.type == server1.type){
                
                if(server1.core_count >= dissected_job.core && server1.resource_disk >= dissected_job.disk && server1.resource_memory >= dissected_job.mem && server1.state != 4){
                    return server1;


                }

            }

        }
    for(Server server2: servers){
        Server temp = null;
        if(server.core_count >= dissected_job.core && server.resource_disk >= dissected_job.disk && server.resource_memory >= dissected_job.disk && server.state != 4){
            temp = server;
            temp.id = 0;
            return temp;
        }


    }


    }
    return null;



}

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

}