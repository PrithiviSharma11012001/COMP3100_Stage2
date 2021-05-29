import java.util.ArrayList; 




public class new_alg{

    private Server[] servers; 
    private ArrayList<Server> arr_list_server = new ArrayList<Server>();






    new_alg(Server[] servers, ArrayList<Server> arr_list_server){
        this.servers = servers;
        this.arr_list_server = arr_list_server;
    }



public Server processed_alg(given_job dissected_job){




}

public Server server_sort(Server server[]){

    int num_length = server.length;

    for(int num = 0; num < num_length - 1; num++){

        int firstcheck = num; 
        for(int num1 = num + 1; num1 < num_length; num1++){

            if(server[num1].core_count < server[num].core_count){



            }

        }

    }

}

}