
import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class Editor implements Serializable{
    Page firstPage;
    Page currentPage;

    Stack<Editor> undos = new Stack<>();
    Stack<Editor> redos = new Stack<>();

    public void start() throws FileNotFoundException {
//        System.out.println("You are in page " + currentPage.getPageNumber());
//        System.out.println("No any page exists :(");

        parse("in.txt");
        save("out.text");

        Scanner sc = new Scanner(System.in);
        if(sc.nextLine().equals("1")){
            append("aaaaaaaaaaaaaa");
            remove(4);
            save("out.text");
        }
        if(sc.nextLine().equals("2")){
            undo();
            insert("bbbbbbbbbbb",6 );
            undo();
            undo();
            save("out.text");
        }
        if(sc.nextLine().equals("3")){
            redo();
            save("out.text");
        }
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
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        currentPage = firstPage;
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
        System.out.println("\"" + currentPage.getNumOfLines() + "\" lines exists in page " + currentPage.getPageNumber());
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
        currentPage = firstPage;
        while (currentPage!=null){
            currentPage.findAndReplace(s,t);
            currentPage = currentPage.getNextPage();
        }

    }
    public void undo(){
        redos.push(deepClone(this));
        Editor prevEditor = undos.pop();
        firstPage = prevEditor.getFirstPage();
        currentPage = prevEditor.getCurrentPage();
    }
    public void redo(){
        Editor prevEditor = redos.pop();
        firstPage = prevEditor.getFirstPage();
        currentPage = prevEditor.getCurrentPage();
    }
    private void addPage(String firstLine){
        Page newPage = new Page(firstLine, currentPage.getPageNumber()+1);
        newPage.setPrevPage(currentPage);
        currentPage.setNextPage(newPage);
        currentPage = newPage;
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

    void pp(){currentPage.pp();} //for test
}
