public class HeapSJF {
    private Node[] heap;
    private int size;
    private int numOfNodes;
    
    public HeapSJF(int size){
        this.size = size;
        numOfNodes = 0;
        heap = new Node[size];
    }
    public boolean isEmpty(){
        //return heap[0] == null;
        return numOfNodes == 0;
    }

    public Node getHeapNode(int index) {
        return heap[index];
    }
    
    public boolean insert(int index, int duration){
        if(numOfNodes == size){ 
            return false; 
        }
        Node newNode = new Node(index);
        newNode.setDuration(duration);
        heap[numOfNodes] = newNode;
        trickleUp(numOfNodes);
        numOfNodes++;
        return true;
    }
    public void trickleUp(int index){
       int parent = (index -1)/2;
       Node temp = heap[index];
       while(index > 0 && heap[parent].duration < temp.duration){
           heap[index] = heap[parent];
           index = parent;
           parent = (parent -1)/2;
       }
       heap[index] = temp;
    }
    public Node remove(){
        Node root = heap[0];
        heap[0] = heap[--numOfNodes];
        trickleDown(0);
        //numOfNodes--;
        return root;
    }
    
    public void trickleDown(int index){
        int largerChild;
        Node temp = heap[index];
        while(index < numOfNodes/2){
            int leftChild = 2*index + 1;
            int rightChild = 2* index + 2;
            if(rightChild < numOfNodes && heap[leftChild].duration < heap[rightChild].duration){
                largerChild = rightChild;
                
            }
            else{
                largerChild = leftChild;
            }
            if(temp.duration >= heap[largerChild].duration)
                break;
            
            heap[index] = heap[largerChild];
            index = largerChild;
        }
        heap[index] = temp;
    }

    public boolean change(int index, int newValue){
        if(index < 0 || index >= numOfNodes){
            return false;
        }
        int oldValue = heap[index].duration;
        heap[index].duration = newValue;
        
        if(newValue < oldValue)
            trickleDown(index);
        else
            trickleUp(index);
        return true;
    }

    public void display() { // debug only 
        for(int i = 0 ; i < numOfNodes ;i++){
            System.out.print(heap[i].duration + " ");
        }
        System.out.println("");
    }

    public void insertAt(int index, Node duration){
        heap[index] = duration;
        //numOfNodes++;
    }

    public void incrementSize(){ // method increments the number of nodes and the size 
        numOfNodes++;
    }

    public void displayArray(){ // debug only
        System.out.println("Duration Array (debug heap sort)");
        for(int i = 0 ; i < size ;i++) {
            System.out.print("Deadline: " + heap[i].deadline + "  Duration: ");
            System.out.println(heap[i].duration + " ");
        }
        System.out.println("");
    }
}