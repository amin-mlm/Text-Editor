import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Editor editor = new Editor();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write down the file URL:\n");
        String address = scanner.nextLine();
        editor.parse(address);

        System.out.println("What do you want to do (enter command number):");
        System.out.println("1.Where am I?");
        System.out.println("2.Go to previous page");
        System.out.println("3.Go to next page");
        System.out.println("4.How many lines");
        System.out.println("5.Show first n lines");
        System.out.println("6.Append text");
        System.out.println("7.Insert line");
        System.out.println("8.Replace line m with n");
        System.out.println("9.Find occurrences of string s");
        System.out.println("10.Find string s and replace with string t");
        System.out.println("11.undo");
        System.out.println("12.redo");
        System.out.println();
        System.out.println("0.Exit");

        int command = -1;
        do{
            try{
                command = scanner.nextInt();
                switch(command){
                    case 1->{
                        editor.where();
                    }case 2->{
                        editor.previousPage();
                    }case 3->{
                        editor.nextPage();
                    }case 4->{
                        editor.lines();
                    }case 5->{
                        editor.show(scanner.nextInt());
                    }case 6->{
                        System.out.println("Enter your text: (separate your lines with '&' sign)");
                        String text = scanner.nextLine();
                        editor.append(text);
                    }case 7->{
                        System.out.println("Enter your text:");
                        String text = scanner.nextLine();
                        System.out.print("In line: ");
                        int line = scanner.nextInt();
                        editor.insert(text, line);
                    }case 8->{
                        System.out.println("Enter your text:");
                        String text = scanner.nextLine();
                        System.out.print("Replace with line: ");
                        int line = scanner.nextInt();
                        editor.replace(line, text);
                    }case 9->{
                        System.out.println("Enter the string:");
                        String str = scanner.nextLine();
                        editor.find(str);
                    }case 10->{
                        System.out.print("Enter the string you want to replace: ");
                        String s = scanner.nextLine();
                        System.out.print("Replace with: ");
                        String t = scanner.nextLine();
                        editor.findAndReplace(s,t);
                    }case 11->{
                        editor.undo();
                    }case 12->{
                        editor.redo();
                    }
                }
            }catch (InputMismatchException e){
                System.out.println("Illegal Command! try again:");
                continue;
            }
        }while(command!=0);
    }

}
