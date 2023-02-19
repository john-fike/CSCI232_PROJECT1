import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        Node root;
        Scanner in = new Scanner(System.in);
        String yesorno = "Y";

        BST bst = new BST();

        //file sent in
        FileManager file = new FileManager();
        file.loadFromFile();

        //if no file sent in
        root = new Node("In the forest");
        root.setLeft(new Node("mushroom"));
        root.setRight(new Node("daisy"));

        //System.out.println(root.getName());
        //System.out.println(root.getLeft().getName());
        //System.out.println(root.getRight().getName());

        //is it yes or no
        while(yesorno.equals("Y"))
        {
            //ask if it is the child characteristic
            System.out.println("Do you have a new plant to be identified (Y/N)");
            yesorno = in.nextLine();
            if(yesorno.equals("Y")){bst.getAnswer(root);}
        }

        file.writeToFile();

        System.out.println("Thanks for playing");
    }


}