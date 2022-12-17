import java.io.Serializable;
import java.util.Scanner;

public class Page implements Serializable {
    Line firstLine;
    Line currentLine;
    Page nextPage;
    Page prevPage;

    int pageNumber;
    int numOfLines=0;


    public Page(String line, int pageNumber) {
        firstLine = new Line(line, ++numOfLines);
        currentLine = firstLine;
        nextPage = null;
        prevPage = null;
        this.pageNumber = pageNumber;
    }

    public void addLine(String textLine) {
        Line newLine = new Line(textLine, ++numOfLines);
        currentLine.setNextLine(newLine);
        currentLine = newLine;
//        numOfLines++;
    }

    public void showLines(int n) {
        currentLine = firstLine;
        for (int i = 0; i <n; i++) {
            currentLine.showText();
            currentLine = currentLine.getNextLine();
        }
    }


    public void setNextPage(Page page) {
        nextPage = page;
    }
    public void setPrevPage(Page prevPage) {
        this.prevPage = prevPage;
    }
    public Page getNextPage() {
        return nextPage;
    }

    public Page getPrevPage() {
        return prevPage;
    }

    public String getText() {
        String text = "";
        currentLine = firstLine;
        while(currentLine!=null){
            text += currentLine.getText() + "\n";
            currentLine = currentLine.getNextLine();
        }
        return text;
    }

    public int getPageNumber() {
        return pageNumber;
    }


    public void appendText(String str) {
        currentLine = firstLine;
        while(currentLine.nextLine!=null)
            currentLine = currentLine.getNextLine();
        //now currentLine points to the last line
        Scanner textScanner = new Scanner(str);
        while(textScanner.hasNextLine()){
            String textLine = textScanner.nextLine();
            addLine(textLine);
        }
    }

    public void insertLine(String str, int n) {
        Line newLine = new Line(str, ++numOfLines);
        currentLine = firstLine;
        if(n==1){
            newLine.setNextLine(firstLine);
            firstLine = newLine;
        }
        else{
            for (int i = 0; i < n - 2; i++) {
                currentLine = currentLine.getNextLine();
            }
            newLine.setNextLine(currentLine.getNextLine());
            currentLine.setNextLine(newLine);
        }
//        numOfLines++;
    }

    public void removeLine(int n) {
        currentLine = firstLine;
        if(n==1)
            firstLine = firstLine.getNextLine();
        else{
            for (int i = 0; i < n-2; i++)
                currentLine = currentLine.getNextLine();
            currentLine.setNextLine(currentLine.getNextLine().getNextLine());
        }
        numOfLines--;
    }

    public void replaceLine(int n, String str) {
        Line newLine = new Line(str, n);
        if(n==1){
            newLine.setNextLine(firstLine.getNextLine());
            firstLine = newLine;
        }
        else{
            currentLine = firstLine;
            for (int i = 0; i < n-2; i++) {
                currentLine = currentLine.getNextLine();
            }
            newLine.setNextLine(currentLine.getNextLine().getNextLine());
            currentLine.setNextLine(newLine);
        }
    }

    public void swapLines(int m, int n) {
        //make m<n
        if(n<m){
            int temp = m;
            m = n;
            n = temp;
        }

        //find line n
        Line lineBeforeN = firstLine;
        for (int i = 0; i < n-2; i++) {
            lineBeforeN = lineBeforeN.getNextLine();
        }
        Line lineN = lineBeforeN.getNextLine();
        Line lineAfterN = lineN.getNextLine();

        if(m==1){
            Line lineM = firstLine.clone();

            if(n-m==1){
                firstLine = lineN;
                lineN.setNextLine(lineM);
                lineM.setNextLine(lineAfterN);
            }
            else{
                lineN.setNextLine(lineM.getNextLine());
                firstLine = lineN;
                lineBeforeN.setNextLine(lineM);
                lineM.setNextLine(lineAfterN);
            }
        }
        else {
            Line lineBeforeM = firstLine;
            for (int i = 0; i < m-2; i++) {
                lineBeforeM = lineBeforeM.getNextLine();
            }
            Line lineM = lineBeforeM.getNextLine();

            if(n-m==1){
                lineBeforeM.setNextLine(lineN);
                lineN.setNextLine(lineM);
                lineM.setNextLine(lineAfterN);
            }else{
                lineN.setNextLine(lineM.getNextLine());
                lineBeforeM.setNextLine(lineN);

                lineM.setNextLine(lineAfterN);
                lineBeforeN.setNextLine(lineM);
            }
        }
        System.out.println(firstLine.getText());
    }

    void pp(){ //for test
        currentLine = firstLine.clone();
        currentLine.setText("vv");
        firstLine.showText();

//        currentLine = new Line(firstLine);


    }

    public void find(String str) {
        currentLine = firstLine;
        System.out.println("Page " + pageNumber + ":");
        while(currentLine!=null){
            if(currentLine.getText().contains(str)){
                System.out.println("\tLine " + currentLine.getLineNumber() + ": " + currentLine.getText());
            }
            currentLine = currentLine.getNextLine();
        }

    }

    public void findAndReplace(String s, String t) {
        currentLine = firstLine;
        while(currentLine!=null){
            if(currentLine.getText().contains(s)){
                String newStr = currentLine.getText();
                newStr = newStr.replaceAll(s,t);
                replaceLine(currentLine.getLineNumber(), newStr);
            }
            currentLine = currentLine.getNextLine();
        }
    }
}
