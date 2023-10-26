public class Node {
    int deadline, duration, slack, id;
    Boolean late;

    /* 
    * start_time records whatever time the job started, 
    * deadline the supposed time the job needs to be done, 
    * duration is the process time they needed
    * end_time  this is when the process is done (start_time + duration)
    */

    public Node(int idNum){
        this.deadline = 0;
        this.duration = 0;
        this.id = idNum;
        this.late = false;
    }

    public void setDeadline(int val) {
        this.deadline = val;
    }

    public int getDeadline() {
        return this.deadline;
    }

    public void setDuration(int val) {
        this.duration = val;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getID(){
        return this.id;
    }

    public int setSlack(){
        this.slack = this.deadline - this.duration;
        return this.slack;
    }

    public int getSlack() {
        return this.slack;
    }
}
