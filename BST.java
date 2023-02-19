import java.util.Scanner;

public class BST {
    Node root;
    public BST() {
       root = null;
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

    public String getLocation(Node current)
    {
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
}
