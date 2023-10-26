import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    // declare global variables here, delete later
    //private static LinkedList listEDF = new LinkedList();  // holds the list of all the jobs done using the Earliest Deadline First algorithm
    private static int[][] jobs; // has the list of all the jobs, row by column
    private static int N;  // total amount of jobs

    public static void main (String[] args) throws Exception {
        // this gets the file path, dynamically (be sure to provide the file within the same folder)
        Path path = Paths.get("input2.txt");   // insert the name of the text file here, MUST BE within the same folder
        Path filepathG = path.toAbsolutePath();
        String filepathS = filepathG.toString();    // turns the Path into String to be input in File object

        // creates the file object and buffered reader
        File file = new File(filepathS);
        BufferedReader br = new BufferedReader(new FileReader(file));
        Scanner scanner = new Scanner(file);

        // n is the number of pairs (e.g. 2 means 2 men and 2 women)
        N = scanner.nextInt();
        
        // sets the dimension of the N rows and 2 columns
        jobs = new int[N][2];

        // insert the data to the N x 2 matrix
        for(int i = 0; i < N ; i++){
            jobs[i][0] = scanner.nextInt();
            jobs[i][1] = scanner.nextInt();
        }

        // prevents resource leak
        scanner.close();
        br.close();

        displayAllJobs();   // debug only

        if(switchDebug()) {
            EDF();  // earliest deadline first
            SJF(); // shortest job first
            LSF(); // least slack first
        } else {
            System.out.println("Experiments Only: ");
            EDF1();
            SJF1();
            LSF1();
        }
        
    }

    public static void LSF() {  // Least Slack First
        // first we sort the jobs by the slack (defined by deadline minus duration/process time) from the least to the greatest
        // implementation of heap sort, using HeapLSF.java
        HeapLSF h2 = new HeapLSF(N);

        for(int i = 0 ; i < N ; i++) {
            Node newNode = new Node(i);
            newNode.setDeadline(jobs[i][1]);
            newNode.setDuration(jobs[i][0]);
            newNode.setSlack();
            h2.insertAt(i , newNode);
            h2.incrementSize();
        }

        for(int i = N/2-1; i>=0 ; i--){
            h2.trickleDown(i);
        }

        for(int i = N-1; i >= 0 ; i--){
           Node largest = h2.remove();
           h2.insertAt(i, largest);
        }

        h2.displayArray();   // final sorted array, debug only
        // after sorting, we need to insert the job on the list while setting the start times and end times.
        int currentTime = 0;    // this is the current time, this is to keep track if the job is late and if we could still schedule the job
        int numOfLate = 0;  // this is to count how many jobs are going to be late, late defined when currentTime > job's deadline 
        for(int i = 0; i < N; i++) {
            Node pntr = h2.getHeapNode(i);
            if(currentTime <= pntr.getDeadline()) {
                System.out.println("current time is " + currentTime + " and the deadline is " + pntr.getDeadline() + " duration time: " + pntr.getDuration());    // debug, delete later
                currentTime += pntr.getDuration();
                System.out.println(currentTime + " current time");  // debug only, delete later

                // checks if the job is late
                if(currentTime > pntr.getDeadline()){
                    System.out.println("Loop(" + i + ") is late which has deadline of " + pntr.getDeadline());
                    numOfLate++;
                    pntr.late = true;
                }
            }
        }   // end of for loop
        System.out.println("Least Slack First: " + numOfLate + "\n");    // output number of late jobs
    }

    public static void SJF() {  // Shortest Job First
        // first we sort the jobs by the duration from the least to the greatest
        // implementation of heap sort, using HeapSJF.java
        HeapSJF h1 = new HeapSJF(N);

        for(int i = 0 ; i < N ; i++) {
            Node newNode = new Node(i);
            newNode.setDeadline(jobs[i][1]);
            newNode.setDuration(jobs[i][0]);
            h1.insertAt(i , newNode);
            h1.incrementSize();
        }

        for(int i = N/2-1; i>=0 ; i--){
            h1.trickleDown(i);
        }

        for(int i = N-1; i >= 0 ; i--){
           Node largest = h1.remove();
           h1.insertAt(i, largest);
        }

        h1.displayArray();   // final sorted array, debug only

        // after sorting, we need to insert the job on the list while setting the start times and end times.
        int currentTime = 0;    // this is the current time, this is to keep track if the job is late and if we could still schedule the job
        int numOfLate = 0;  // this is to count how many jobs are going to be late, late defined when currentTime > job's deadline 
        for(int i = 0; i < N; i++) {
            Node pntr = h1.getHeapNode(i);
            if(currentTime <= pntr.getDeadline()) {
                System.out.println("current time is " + currentTime + " and the deadline is " + pntr.getDeadline() + " duration time: " + pntr.getDuration());    // debug, delete later
                currentTime += pntr.getDuration();
                System.out.println(currentTime + " current time");  // debug only, delete later

                // checks if the job is late
                if(currentTime > pntr.getDeadline()){
                    numOfLate++;
                    pntr.late = true;
                }
            }
        }   // end of for loop
        System.out.println("Shortest Job/Duration First: " + numOfLate + "\n");    // output number of late jobs
    }

    public static void EDF() {  // Earliest Deadline First
        // first we sort the jobs by deadline so that d1 < d2 < d3 < ... < dN (least to greatest)
        // implementing heap sort, using Heap.java
        Heap h = new Heap(N);

        for(int i = 0 ; i < N ; i++) {
            Node newNode = new Node(i);
            newNode.setDeadline(jobs[i][1]);
            newNode.setDuration(jobs[i][0]);
            h.insertAt(i , newNode);
            h.incrementSize();
        }

        for(int i = N/2-1; i>=0 ; i--){
            h.trickleDown(i);
        }

        for(int i = N-1; i >= 0 ; i--){
           Node largest = h.remove();
           h.insertAt(i, largest);
        }

        h.displayArray();   // final sorted array, debug only

        // after sorting, we need to insert the job on the list while setting the start times and end times.
        int currentTime = 0;    // this is the current time, this is to keep track if the job is late and if we could still schedule the job
        int numOfLate = 0;  // this is to count how many jobs are going to be late, late defined when currentTime > job's deadline 
        for(int i = 0; i < N; i++) {
            Node pntr = h.getHeapNode(i);
            if(currentTime <= pntr.getDeadline()) {
                // debug, delete later
                System.out.println("current time is " + currentTime + " and the deadline is " + pntr.getDeadline() + " duration time: " + pntr.getDuration());
                currentTime += pntr.getDuration();
                System.out.println(currentTime + " current time");  // debug only, delete later

                // checks if the job is late
                if(currentTime > pntr.getDeadline()){
                    numOfLate++;
                    pntr.late = true;
                }
            }
        }   // end of for loop
        
        System.out.println("Earliest Deadline First: " + numOfLate + "\n");
    }

    public static void displayAllJobs(){    // DEBUG ONLY, DELETE LATER
        for(int i = 0; i < N ; i++){
            System.out.println("|| ID(" + i + ") || " + jobs[i][0] + " || " + jobs[i][1] + " ||");
        }
        System.out.println("");
    }

    // under here are for experiments only
    public static Boolean switchDebug() {   // this is only for debugging purposes 
        return true;    // turn to true to activate LSF(), false for LSF1()
    }

    public static void LSF1() {  // Least Slack First
        // first we sort the jobs by the slack (defined by deadline minus duration/process time) from the least to the greatest
        // implementation of heap sort, using HeapLSF.java
        HeapLSF h2 = new HeapLSF(N);

        for(int i = 0 ; i < N ; i++) {
            Node newNode = new Node(i);
            newNode.setDeadline(jobs[i][1]);
            newNode.setDuration(jobs[i][0]);
            newNode.setSlack();
            h2.insertAt(i , newNode);
            h2.incrementSize();
        }

        for(int i = N/2-1; i>=0 ; i--){
            h2.trickleDown(i);
        }

        for(int i = N-1; i >= 0 ; i--){
           Node largest = h2.remove();
           h2.insertAt(i, largest);
        }

        h2.displayArray();   // final sorted array, debug only
        // after sorting, we need to insert the job on the list while setting the start times and end times.
        int currentTime = 0;    // this is the current time, this is to keep track if the job is late and if we could still schedule the job
        int numOfLate = 0;  // this is to count how many jobs are going to be late, late defined when currentTime > job's deadline 
        for(int i = 0; i < N; i++) {
            Node pntr = h2.getHeapNode(i);
            System.out.println("current time is " + currentTime + " and the deadline is " + pntr.getDeadline() + " duration time: " + pntr.getDuration());    // debug, delete later
            currentTime += pntr.getDuration();
            System.out.println(currentTime + " current time");  // debug only, delete later

            // checks if the job is late
            if(currentTime > pntr.getDeadline()){
                System.out.println("Loop(" + i + ") is late which has deadline of " + pntr.getDeadline());
                numOfLate++;
                pntr.late = true;
            }
        }   // end of for loop
        System.out.println("Least Slack First: " + numOfLate + "\n");    // output number of late jobs
    }

    public static void SJF1() {  // Shortest Job First
        // first we sort the jobs by the duration from the least to the greatest
        // implementation of heap sort, using HeapSJF.java
        HeapSJF h1 = new HeapSJF(N);

        for(int i = 0 ; i < N ; i++) {
            Node newNode = new Node(i);
            newNode.setDeadline(jobs[i][1]);
            newNode.setDuration(jobs[i][0]);
            h1.insertAt(i , newNode);
            h1.incrementSize();
        }

        for(int i = N/2-1; i>=0 ; i--){
            h1.trickleDown(i);
        }

        for(int i = N-1; i >= 0 ; i--){
           Node largest = h1.remove();
           h1.insertAt(i, largest);
        }

        h1.displayArray();   // final sorted array, debug only

        // after sorting, we need to insert the job on the list while setting the start times and end times.
        int currentTime = 0;    // this is the current time, this is to keep track if the job is late and if we could still schedule the job
        int numOfLate = 0;  // this is to count how many jobs are going to be late, late defined when currentTime > job's deadline 
        for(int i = 0; i < N; i++) {
            Node pntr = h1.getHeapNode(i);
            System.out.println("current time is " + currentTime + " and the deadline is " + pntr.getDeadline() + " duration time: " + pntr.getDuration());    // debug, delete later
            currentTime += pntr.getDuration();
            System.out.println(currentTime + " current time");  // debug only, delete later

            // checks if the job is late
            if(currentTime > pntr.getDeadline()){
                System.out.println("Loop(" + i + ") is late which has deadline of " + pntr.getDeadline());
                numOfLate++;
                pntr.late = true;
            }
        }   // end of for loop
        System.out.println("Shortest Job/Duration First: " + numOfLate + "\n");    // output number of late jobs
    }

    public static void EDF1() {  // Earliest Deadline First
        // first we sort the jobs by deadline so that d1 < d2 < d3 < ... < dN (least to greatest)
        // implementing heap sort, using Heap.java
        Heap h = new Heap(N);

        for(int i = 0 ; i < N ; i++) {
            Node newNode = new Node(i);
            newNode.setDeadline(jobs[i][1]);
            newNode.setDuration(jobs[i][0]);
            h.insertAt(i , newNode);
            h.incrementSize();
        }

        for(int i = N/2-1; i>=0 ; i--){
            h.trickleDown(i);
        }

        for(int i = N-1; i >= 0 ; i--){
           Node largest = h.remove();
           h.insertAt(i, largest);
        }

        h.displayArray();   // final sorted array, debug only

        // after sorting, we need to insert the job on the list while setting the start times and end times.
        int currentTime = 0;    // this is the current time, this is to keep track if the job is late and if we could still schedule the job
        int numOfLate = 0;  // this is to count how many jobs are going to be late, late defined when currentTime > job's deadline 
        for(int i = 0; i < N; i++) {
            Node pntr = h.getHeapNode(i);
            System.out.println("current time is " + currentTime + " and the deadline is " + pntr.getDeadline() + " duration time: " + pntr.getDuration());    // debug, delete later
            currentTime += pntr.getDuration();
            System.out.println(currentTime + " current time");  // debug only, delete later

            // checks if the job is late
            if(currentTime > pntr.getDeadline()){
                System.out.println("Loop(" + i + ") is late which has deadline of " + pntr.getDeadline());
                numOfLate++;
                pntr.late = true;
            }
        }   // end of for loop
        
        System.out.println("Earliest Deadline First: " + numOfLate + "\n");
    }
    
}   // end of class
