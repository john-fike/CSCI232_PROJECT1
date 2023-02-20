import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;

public class BST {
    Node root;

    public BST() {
       root = null;
    }

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

    public void getAnswer(Node current)
    {
        Scanner in = new Scanner(System.in);

        //not empty
        if (current != null) {
            System.out.println("Is your plant " + current.getName() + " Y/N");
            String answer = in.nextLine();

            //is response Y or N
            if (answer.equals("Y"))
            {
                //the current has a left child(Is the characteristic and will now pass through yes characteristic/plant)
                if (current.getLeft() != null)
                {
                    getAnswer(current.getLeft());
                }
                //if current is yes and does not have a yes child, it must be a leaf
                else if (current.getLeft() == null){
                    System.out.println("Great");
                }

            //N response must mean it moves to the right
            } else if (answer.equals("N")) {
                //if it has a right child(N), pass through the ne characteristic
                if (current.getRight() != null)
                {
                    getAnswer(current.getRight());
                }
                //current does not have a right child, will ask to identify new plant
                else if (current.getRight() == null)
                {
                    String location = getLocation(current);
                    String original = current.getName();

                    System.out.println("I do not know any " +  location + "plants that is not a " + original);
                    System.out.println("What is the plant you are thinking of");
                    String name = in.nextLine();
                    System.out.println("What characteristic does a " + name + " have that " + original + " does not have?");
                    String character = in.nextLine();

                    //rearranges the last three part of the tree, current and children
                    current.setName(character);
                    current.setLeft(new Node(name));
                    current.setRight(new Node(original));

                    //getAnswer(root);
                    //should go back to while loop
                }
            } else {System.out.println("Not a valid response");}
        }
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
    //-nodeNames[] holds names of nodes in inorder order
    //-insert() is then used to insert the nodes using the values from the
    //second line of CSVs. These are the values created by reNumber.
    //-Each node is then assigned its corresponding name in nodeNames[]
    //////////////////////////////////////////////////////////////////////
    public void loadFromFile(String fileName) {
        try {
            FileReader file = new FileReader(fileName);
            Scanner inFile = new Scanner(file);
            String[] nodeNames;

            String loadedInNames = inFile.nextLine();
            System.out.println("Loading: " + loadedInNames);
            nodeNames = loadedInNames.split(",");

            //load next CSV line
            String treeOrder = inFile.nextLine();
            System.out.println("Loading: " + treeOrder);
            String[] tokens = treeOrder.split(",");
            for (String s : tokens){
                try{
                    System.out.println("Inserting: " + nodeNames[Integer.parseInt(s)]);
                    insert(Integer.parseInt(s));
                }catch (Exception e){
                    System.out.println("File formatted incorrectly! :(");
                }
            }
            inFile.close();
            Queue<Node> queue = new LinkedList<>();
            if (root != null) {
                queue.add(root);
                while (!queue.isEmpty()) {
                    Node node = queue.remove();
                    node.setName(nodeNames[Integer.parseInt(node.getName())]);
                    if (node.getLeft() != null) {
                        queue.add(node.getLeft());
                    }
                    if (node.getRight() != null) {
                        queue.add(node.getRight());
                    }
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found! :(");
        }
    }

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
                    System.out.println("Successfully wrote to the file.");
                    if(node.getLeft()!=null){queue.add(node.getLeft());}
                    if(node.getRight()!=null){queue.add(node.getRight());}
                }
            }
            outFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
