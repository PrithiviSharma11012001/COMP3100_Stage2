public class given_job{

    public int submission_time; 
    public int job_id; 
    public int running_time_estimate; 
    public int core; 
    public int mem; 
    public int disk; 





    //job class constructor.
    given_job(int sub_time, int j_id,int rte, int core, int mem, int disk){

        this.submission_time = sub_time; 
        this.job_id = j_id; 
        this.running_time_estimate = rte; 
        this.core = core;
        this.mem = mem; 
        this.disk = disk; 

    }


}