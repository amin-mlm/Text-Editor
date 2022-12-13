import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Editor {
    Page firstPage;
    Page currentPage;

    public void start() {
//        parse("in.txt");
//        save("out.txt");
        System.out.println("What Do You Want?");
        System.out.println("1.Parse\n2.save\n3.where");

    }

    public void parse(String address) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(address));
        String line;

        line = scanner.nextLine();
        firstPage = new Page(line, 1);
        currentPage = firstPage;

        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            if(line.equals("$")){
                line = scanner.nextLine();
                //addPage was here
                addPage(line);
            }else{
                currentPage.addLine(line);
            }
        }
        currentPage = firstPage; //set current page as first to be valid when "where()" is invoked
    }

    public void save(String address) {
        String text;
        try(PrintWriter pw = new PrintWriter(address)){
            currentPage = firstPage;
            while(currentPage!=null){
                text = currentPage.getText();
                pw.print(text);
                pw.println("$");
                currentPage = currentPage.getNextPage();
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        currentPage = firstPage; //set current page as first to be valid when "where()" is invoked
    }

    public void where(){
        if(currentPage!=null)
            System.out.println("You are in page " + currentPage.getPageNumber());
        else
            System.out.println("No any page exists :(");
    }

    public void nextPage(){
        currentPage = currentPage.getNextPage();
    }

    private void addPage(String firstLine){
        Page newPage = new Page(firstLine, currentPage.getPageNumber()+1);
        currentPage.setNextPage(newPage);
        currentPage = newPage;
    }
}
