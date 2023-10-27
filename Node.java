public class Node {
    int deadline, duration, slack, id;
    Boolean late;

    /* 
    * deadline is to keep track of the job's deadline, this variable will also be used to determine if the job is late or not
    * duration is the variable that keeps track how long the job needs to be finished. 
    *   This variable will help keep track and increment the current time as the loop is ongoing
    *  slack is defined to be the job's deadline minus duration. 
    *  id is not used as much but this is helpful when debugging and looking for a specific node
    *  Boolean late will store the boolean data if the node/job is late or not. 
    *   True if the job is late
    *   False if the job is NOT late
    *   A job or node is considered late only when the current time exceeds the deadline
    * 
    */

    public Node(int idNum){ // this is a constructor class when creating a node
        this.deadline = 0;
        this.duration = 0;
        this.id = idNum;
        this.late = false;
    }

    public void setDeadline(int val) {  // this sets the deadline of the current node or job
        this.deadline = val;
    }

    public int getDeadline() {  // gets or returns the deadline of the current node or job
        return this.deadline;
    }

    public void setDuration(int val) {  // this sets the duration of the current node or job
        this.duration = val;
    }

    public int getDuration() {  // gets or returns the duration of the current node or job
        return this.duration;
    }

    public int getID()  {   // debug only, this gets the id of the current node or job
        return this.id;
    }

    public int setSlack(){  // calculates the slack by subtracting the deadline with the duration, and sets the variable to the difference 
        this.slack = this.deadline - this.duration;
        return this.slack;
    }

    public int getSlack() { // gets or returns the slack of the current nore or job
        return this.slack;
    }
}
