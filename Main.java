import java.util.Scanner;
public class Main
{
    public static void main(String[] args)
    {
        Scanner inputScanner = new Scanner(System.in);

        BST bst = new BST();
        System.out.println("Loading last session...");
        try{
            bst.loadFromFile("dynamicSaveTree.txt");
        }catch(Exception e){
            System.out.println("No old training sessions to pull from, loading default tree...");
            try{
                bst.loadFromFile("defaultTree.txt");
            }catch(Exception f){
                System.out.println("Well that didn't even work. You must have a default tree in this directory for me to function.");
                System.exit(1);
            }
        }


        bst.printTree();

        System.out.println("Do you have a new plant to be identified (Y/N)");
        String userInput = inputScanner.nextLine().toUpperCase();
        while(userInput.equals("Y"))
        {
            bst.getAnswer();
            System.out.println("Do you have a new plant to be identified (Y/N)");
            userInput = inputScanner.nextLine().toUpperCase();
        }

        System.out.println("Thanks for playing");
        bst.writeToFile();
    }

}