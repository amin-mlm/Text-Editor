import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args)  {
        Editor editor = new Editor();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file URL:");
        do {
            String address = scanner.nextLine();
            try {
                editor.parse(address);
                break;
            } catch (FileNotFoundException e) {
                System.out.println("File not found! Invalid URL.\nEnter the file URL:");
            }
        }while (true);

        System.out.println("What do you want to do (enter command number):");
        System.out.println("1.Where am I?");
        System.out.println("2.Go to previous page");
        System.out.println("3.Go to next page");
        System.out.println("4.How many lines");
        System.out.println("5.Show first n lines");
        System.out.println("6.Append text");
        System.out.println("7.Insert line");
        System.out.println("8.Remove line");
        System.out.println("9.Replace line m with string s");
        System.out.println("10.Swap line m with n");
        System.out.println("11.Find occurrences of string s");
        System.out.println("12.Find string s and replace with string t");
        System.out.println("13.Undo");
        System.out.println("14.Redo");
        System.out.println("15.save");
        System.out.println();
        System.out.println("0.Exit");

        int command = -1;
        do{
            try{
                command = scanner.nextInt();
                scanner.nextLine();
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
                        System.out.print("Enter n: ");
                        int lines = getNumberInRange(1,editor.getLines());
                        editor.show(lines);
                    }case 6->{
                        System.out.println("Enter your text: (separate your lines with '&' sign)");
                        String text = scanner.nextLine();
                        editor.append(text);
                    }case 7->{
                        System.out.println("Enter your text:");
                        String text = scanner.nextLine();
                        System.out.print("In line: ");
                        int line = getNumberInRange(1,editor.getLines()+1);
                        editor.insert(text, line);
                    }case 8->{
                        System.out.print("Enter the line number: ");
                        int line = getNumberInRange(1,editor.getLines());
                        editor.remove(line);
                    }case 9->{
                        System.out.println("Enter your text:");
                        String text = scanner.nextLine();
                        System.out.print("Replace with line: ");
                        int line = getNumberInRange(1,editor.getLines());
                        editor.replace(line, text);
                    }case 10->{
                        System.out.print("Enter line m: ");
                        int m = getNumberInRange(1,editor.getLines());
                        System.out.print("Enter line n: ");
                        int n = getNumberInRange(1,editor.getLines());
                        editor.swap(m,n);
                    }case 11->{
                        System.out.println("Enter the string:");
                        String str = scanner.nextLine();
                        editor.find(str);
                    }case 12->{
                        System.out.print("Enter the string you want to be replaced: ");
                        String s = scanner.nextLine();
                        System.out.print("Replace with: ");
                        String t = scanner.nextLine();
                        editor.findAndReplace(s,t);
                        System.out.println("Done!");
                    }case 13->{
                        editor.undo();
                    }case 14->{
                        editor.redo();
                    }case 15->{
                        System.out.println("Enter the file URL:");
                        String address = scanner.nextLine();
                        editor.save(address);
                    }
                }
            }catch (InputMismatchException e){
                scanner.nextLine(); //skip line(last scan was an integer.)
                System.out.println("Illegal Command! try again:");
                continue;
            }
        }while(command!=0);
    }

    public static int getNumberInRange(int firstLimit, int secondLimit){
        Scanner scanner = new Scanner(System.in);
        int line = scanner.nextInt();
        scanner.nextLine();
        while (line<firstLimit || line>secondLimit){
            System.out.println("You have only " + secondLimit + " lines! Enter between 1 and " + secondLimit + ": ");
            line = scanner.nextInt();
            scanner.nextLine();
        }
        return line;
    }

}
