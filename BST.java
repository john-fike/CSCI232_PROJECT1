import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
* Authors:
* Sarah Dolan
* John Fike (-02909708)
* */

public class BST {
    Node root;
    Node current;
    int inOrderCount;
    public BST() {
       root = current = null;
    }

    ////////////////////////////////////////////////////////////////////
    //insert method provided by Sean Yaw
    //reconfigured slightly to create a tree based on inOrder indicies
    //and names (instead of ints)
    ////////////////////////////////////////////////////////////////////
    public void insert(int newValue) {
        if (root == null) {
            root = new Node(Integer.toString(newValue));
        } else {
            Node currentNode = root;
            boolean placed = false;
            while (!placed) {
                if (newValue == Integer.parseInt(currentNode.getName())) {
                    placed = true;
                    // Don't insert repeated value.
                } else if (newValue < Integer.parseInt(currentNode.getName())) {
                    if (currentNode.getLeft() == null) {
                        currentNode.setLeft(new Node(Integer.toString(newValue)));
                        currentNode.getLeft().setParent(currentNode);
                        placed = true;
                    } else {
                        currentNode = currentNode.getLeft();
                    }
                } else {
                    if (currentNode.getRight() == null) {
                        currentNode.setRight(new Node(Integer.toString(newValue)));
                        currentNode.getRight().setParent(currentNode);
                        placed = true;
                    } else {
                        currentNode = currentNode.getRight();
                    }
                }
            }
        }
    }

    public void getAnswer()
    {
        Scanner inputScanner = new Scanner(System.in);
            System.out.println("Is your plant a " + current.getName() + " Y/N");
            String userInput = inputScanner.nextLine().toUpperCase();

            //is response Y or N
            if (userInput.equals("Y"))
            {
                //the current has a left child(Is the characteristic and will now pass through yes characteristic/plant)
                if (current.getLeft() != null)
                {
                    current = current.getLeft();
                    getAnswer();
                }
                //if current is yes and does not have a yes child, it must be a leaf
                else if (current.getLeft() == null){
                    System.out.println("Great");
                    current = root;
                }

            //N response must mean it moves to the right
            } else if (userInput.equals("N")) {
                //if it has a right child(N), pass through the ne characteristic
                if (current.getRight() != null)
                {
                    current = current.getRight();
                    getAnswer();
                }
                //current does not have a right child,  ask to identify new plant
                else if (current.getRight() == null)
                {
                    String location = getLocation(current);
                    String original = current.getName();

                    System.out.println("I do not know any " +  location + "plants that is not a " + original);
                    System.out.println("What is the plant you are thinking of?");
                    String name = inputScanner.nextLine();
                    System.out.println("What characteristic does a " + name + " have that " + original + " does not have?");
                    String character = inputScanner.nextLine();

                    //rearranges the last three part of the tree, current and children
                    current.setName(character);
                    current.setLeft(new Node(name));
                    current.setRight(new Node(original));
                    current = root;
                }
            } else {System.out.println("Not a valid response");}

    }

    public String getLocation(Node current) {
        String chars = "";
        while(current.getParent() != null)
        {
            if(current.getParent().getLeft() == current)
            {
                chars = current.getParent().getName() + ", " + chars;
                current = current.getParent();
            }
            else if(current.getParent().getRight() == current)
            {
                chars = "not " + current.getParent().getName() + ", " + chars;
                current = current.getParent();
            }
        }
        return chars;
    }

    ////////////////////////////////////////////////////////////////////
    //prints tree breadth-wise on one line, separated by spaces
    ////////////////////////////////////////////////////////////////////
    public void printTree(){
        Queue<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
            while (!queue.isEmpty()) {
                Node node = queue.remove();
                System.out.print(node.getName() + " ");
                if (node.getLeft() != null) {
                    queue.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    queue.add(node.getRight());
                }
            }
            System.out.println();
        }
    }

    ////////////////////////////////////////////////////////////////////
    //-loads in tree from 2 lines of CSVs stored in file fileName
    //-nodeNames[] holds names of nodes in breadth-wise order
    //-insert() is then used to insert the nodes in the correct order by
    //using the values from the second line of CSVs. These are the
    //values created by reNumber.
    //-The resultant tree is then iterated through breadth-wise and given
    //the correct name, overwriting inOrder values they wre originally
    //assigned
    //////////////////////////////////////////////////////////////////////
    public void loadFromFile(String fileName) {
        try {
            //open file to be loaded from
            FileReader file = new FileReader(fileName);
            Scanner inFile = new Scanner(file);
            String[] nodeNames;

            //grab names from first line and put them into nodeNames[]
            String loadedInNames = inFile.nextLine();
            System.out.println("Loading: " + loadedInNames);
            nodeNames = loadedInNames.split(",");

            //load next line of CSVs (inOrder indices)
            String treeOrder = inFile.nextLine();
            System.out.println("Loading: " + treeOrder);
            String[] tokens = treeOrder.split(",");

            //iterate through and use insert() to recreate a tree with the correct structure
            for (String s : tokens){
                try{
                    System.out.println("Inserting: " + nodeNames[Integer.parseInt(s)]);
                    insert(Integer.parseInt(s));
                }catch (Exception e){
                    System.out.println("File formatted incorrectly! :(");
                }
            }
            inFile.close();

            //iterate through the tree we just created, breadth-wise, and assign names to all the nodes using nodeNames[]
            Queue<Node> queue = new LinkedList<>();
            if (root != null) {
                queue.add(root);
                int bredthCounter=0;
                while (!queue.isEmpty()) {
                    Node node = queue.remove();
                    node.setName(nodeNames[bredthCounter]);
                    if (node.getLeft() != null) {
                        queue.add(node.getLeft());
                    }
                    if (node.getRight() != null) {
                        queue.add(node.getRight());
                    }
                    bredthCounter++;
                }
                //set up current pointer for first getAnswer session
                current = root;
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found! :(");
        }
    }

    //////////////////////////////////////////////////////////////////////
    //writes names to dynamicSaveTree.txt in breadthwise order
    //calls reNumber()
    //writes names (now the inOrder indices) again, one line down.
    //THIS METHOD RENAMES ALL NODES AND SHOULD ONLY BE CALLED AT THE END
    // OF A TRAINING SESSION
    // THIS METHOD ALWAYS WRITES TO A FILE CALLED "dynamicSaveTree.txt"
    //////////////////////////////////////////////////////////////////////
    public void writeToFile(){
        Queue<Node> queue = new LinkedList<>();
        try {
            PrintWriter outFile = new PrintWriter("dynamicSaveTree.txt");
            if (root != null) {
                queue.add(root);
                while (!queue.isEmpty()) {
                    Node node = queue.remove();
                    System.out.println("Writing " + node.getName() + " to file...");
                    outFile.print(node.getName() + ",");
                    if(node.getLeft()!=null){queue.add(node.getLeft());}
                    if(node.getRight()!=null){queue.add(node.getRight());}
                }
                System.out.println();
                outFile.println();
                reNumber();
                queue.add(root);
                while (!queue.isEmpty()) {
                    Node node = queue.remove();
                    System.out.println("Writing " + node.getName() + " to file...");
                    outFile.print(node.getName() + ",");
                    if(node.getLeft()!=null){queue.add(node.getLeft());}
                    if(node.getRight()!=null){queue.add(node.getRight());}
                }
                System.out.println("Save Complete\n");
            }
            outFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    //reassign name of nodes to their inOrder index (same as lab 4)
    public void reNumber(Node node){
        if(node!=null){
            reNumber(node.getLeft());
            System.out.println("Current node name: " + node.getName());
            node.setName(Integer.toString(inOrderCount));
            System.out.println("Set val to: " + inOrderCount);
            inOrderCount++;
            reNumber(node.getRight());
        }
    }
    public void reNumber() {
        inOrderCount = 0;
        reNumber(root);
    }


}
