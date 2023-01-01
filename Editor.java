import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class Editor implements Serializable{
    Page firstPage;
    Page currentPage;

    Stack<Editor> undos = new Stack<>();
    Stack<Editor> redos = new Stack<>();

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
        currentPage = firstPage;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        currentPage = firstPage;
        System.out.println("Saved successfully at \"" + address + "\".");
    }
    public void where(){
        if(currentPage==null)
            System.out.println("No pages exists:(");
        else
            System.out.println("You are on page " + currentPage.getPageNumber());
    }
    public void nextPage(){
        undos.push(deepClone(this));
        Page nextPage = currentPage.getNextPage();
        if(nextPage==null){
            where();
        }else{
            currentPage = nextPage;
            where();
        }
    }
    public void previousPage(){
        undos.push(deepClone(this));
        Page prevPage = currentPage.getPrevPage();
        if(prevPage==null){
            where();
        }else{
            currentPage = prevPage;
            where();
        }
    }
    public void lines(){
        System.out.println("\"" + currentPage.getNumOfLines() + "\" lines exists in page " + currentPage.getPageNumber() + ".");
    }
    public void show(int n){
        currentPage.showLines(n);
    }
    public void append(String str){
        undos.push(deepClone(this));
        currentPage.appendText(str);
    }
    public void insert(String str, int n){
        undos.push(deepClone(this));
        currentPage.insertLine(str, n);
    }
    public void remove(int n){
        undos.push(deepClone(this));
        currentPage.removeLine(n);
    }
    public void replace(int n, String str){
        undos.push(deepClone(this));
        currentPage.replaceLine(n, str);
    }
    public void swap(int m, int n){
        undos.push(deepClone(this));
        currentPage.swapLines(m,n);
    }
    public void find(String str){
        currentPage = firstPage;
        while (currentPage!=null){
            currentPage.find(str);
            currentPage = currentPage.getNextPage();
        }
    }
    public void findAndReplace(String s, String t){
        undos.push(deepClone(this));
        Page tempPage = firstPage;
        while (tempPage!=null){
            tempPage.findAndReplace(s,t);
            tempPage = tempPage.getNextPage();
        }

    }
    public void undo(){
        if(undos.isEmpty()){
            System.out.println("No changes to undo!");
        }else{
            Editor prevEditor = undos.pop();
            redos.push(deepClone(this));
            firstPage = prevEditor.getFirstPage();
            currentPage = prevEditor.getCurrentPage();
            System.out.println("Changes are reverted successfully.");
        }
    }
    public void redo(){
        if(undos.isEmpty()){
            System.out.println("No changes to redo!");
        }else {
            Editor prevEditor = redos.pop();
            firstPage = prevEditor.getFirstPage();
            currentPage = prevEditor.getCurrentPage();
            System.out.println("Changes are made again successfully.");
        }
    }
    private void addPage(String firstLine){
        Page newPage = new Page(firstLine, currentPage.getPageNumber()+1);
        newPage.setPrevPage(currentPage);
        currentPage.setNextPage(newPage);
        currentPage = newPage;
    }

    public int getLines(){
        return currentPage.getNumOfLines();
    }
    public Page getFirstPage() {
        return firstPage;
    }
    public Page getCurrentPage() {
        return currentPage;
    }

    private Editor deepClone(Editor object){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);
            return (Editor) objectInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
