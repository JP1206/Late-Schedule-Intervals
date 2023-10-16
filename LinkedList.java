public class LinkedList {
    private Node head;
    
    public LinkedList(){    // constructor class, sets the head of the list to be null at first
        head = null;
    }

    public boolean isEmpty(){   // this just checks if the node is empty or not
        return head == null;
    }

    public void insert(int value){  // this function is only called when we know what the node and job is to be placed
        // value is the id of the node
        Node newNode = new Node(value);
        Node prev = null;
        Node curr = head;
        
        while(curr != null){    // finds the end of the list
            prev = curr;
            curr = curr.next;
        }
        if(prev == null){   // we are inserting at the beginning
            head = newNode;    
        }
        else{
           prev.next = newNode; // we found the location where to place it and now we can place it 
        }
        newNode.next = curr;
            
    }

    public void display(int choice){  // display the linked list and the necessary data
        Node curr = head;
        if(choice == 0){    // this is only for debugging purposes
            while(curr != null){
                System.out.print("Node(" + curr.id + ") Start Time: " + curr.start_time + " and End Time: " + curr.end_time + " ->");
                curr = curr.next;
            }
            System.out.println("");
        } else {    // insert proper output here as stated in the requirements
            System.out.println("Insert proper output here");    // PLACEHOLDER DELETE LATER
        }
    }
}
