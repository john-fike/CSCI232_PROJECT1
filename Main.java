import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        Node root;
        Scanner in = new Scanner(System.in);
        String yesorno = "Y";

        BST bst = new BST();
        bst.loadFromFile("defaultTree.txt");
//        root = new Node("In the forest");
//        root.setLeft(new Node("mushroom"));
//        root.setRight(new Node("daisy"));
        bst.printTree();


//        while(yesorno.equals("Y"))
//        {
//            System.out.println("Do you have a new plant to be identified (Y/N)");
//            yesorno = in.nextLine();
//            if(yesorno.equals("Y")){bst.getAnswer(root);}
//        }
//
//        System.out.println("Thanks for playing");
    }

}