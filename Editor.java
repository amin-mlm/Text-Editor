import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Editor {
    Page firstPage;
    Page currentPage;

    public void start() throws FileNotFoundException {
//        System.out.println("You are in page " + currentPage.getPageNumber());
//        System.out.println("No any page exists :(");

        parse("in.txt");
        save("out.txt");
//        remove(1);
//        remove(2);
//        remove(1);
//        remove(1);
//        save("out.txt");
//        remove(1);
//        remove(1);
//        nextPage();
//        remove(2);
//        previousPage();
//        remove(1);
//        save("out.txt");
//        insert("ggggggggg", 1);
//        insert("fffffffff", 2);
//        nextPage();
//        insert("aaaaaaaa",3);
//        save("out.txt");
//        append("aa bb\ncc");
//        nextPage();
//        append("dd ff\nee\nvv rr");
//        save("out.text");
//        nextPage();
//        show(3);

//        System.out.println("What Do You Want?");
//        System.out.println("1.Parse\n2.save\n3.where");

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

    public int where(){
        if(currentPage==null)
            return -1;
        else
            return currentPage.getPageNumber();
    }

    public void nextPage(){
        currentPage = currentPage.getNextPage();
    }
    public void previousPage(){
        currentPage = currentPage.getPrevPage();
    }
    public int lines(){
        return currentPage.numOfLines;
    }
    public void show(int n){
        currentPage.showLines(n);
    }
    public void append(String str){
        currentPage.appendText(str);
    }
    public void insert(String str, int n){
        currentPage.insertText(str, n);
    }
    public void remove(int n){
        currentPage.removeLine(n);
    }

    private void addPage(String firstLine){
        Page newPage = new Page(firstLine, currentPage.getPageNumber()+1);
        newPage.setPrevPage(currentPage);
        currentPage.setNextPage(newPage);
        currentPage = newPage;
    }
}
