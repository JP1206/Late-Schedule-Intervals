import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    // declare global variables here, delete later
    private static LinkedList list = new LinkedList();  // holds the list of all the jobs done
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
    }

    public static void EDF() {  // Earliest Deadline First
        // first we sort the jobs by deadline so that d1 < d2 < d3 < ... < dN
        // implementing merge sort O(n lgn)
        
        // after sorting, we need to insert the job on the list while setting the start times and end times. 
    }

    public static void displayAllJobs(){    // DEBUG ONLY, DELETE LATER
        for(int i = 0; i < N ; i++){
            System.out.println("|| ID(" + i + ") || " + jobs[i][0] + " || " + jobs[i][1] + " ||");
        }
    }
}
