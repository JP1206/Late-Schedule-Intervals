public class Node {
    int start_time, deadline, duration, end_time, id; 
    Node next;
    Boolean late;

    /* 
    * start_time records whatever time the job started, 
    * deadline the supposed time the job needs to be done, 
    * duration is the process time they needed
    * end_time  this is when the process is done (start_time + duration)
    */

    public Node(int idNum){
        this.start_time = 0;
        this.deadline = 0;
        this.duration = 0;
        this.end_time = 0;
        this.id = idNum;
        this.next = null;
        this.late = false;
    }

    public Node setNext(Node point_to_next){
        return this.next = point_to_next;
    }

    public boolean setLate(){
        if(end_time > deadline){
            return this.late = true;
        } else {
            return this.late = false;
        }
    }

    public void set_data(int duration_inp, int start_time_inp){
        this.duration = duration_inp;
        this.start_time = start_time_inp;
        this.end_time = start_time + duration;
    }

    public int getID(){
        return this.id;
    }

}
