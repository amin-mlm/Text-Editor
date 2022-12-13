import java.util.Scanner;

public class Page {
    Line firstLine;
    Line currentLine;
    Page nextPage;
    Page prevPage;

    int pageNumber;
    int numOfLines=0;


    public Page(String line, int pageNumber) {
        firstLine = new Line(line);
        currentLine = firstLine;
        nextPage = null;
        prevPage = null;
        this.pageNumber = pageNumber;
    }

    public void addLine(String text) {
        Line newLine = new Line(text);
        currentLine.setNextLine(newLine);
        currentLine = newLine;
        numOfLines++;
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
            currentLine = currentLine.getNextLine(); //now currentLine points to the last line
        Scanner textScanner = new Scanner(str);
        while(textScanner.hasNextLine()){
            String textLine = textScanner.nextLine();
            addLine(textLine);
        }
    }

    public void insertText(String str, int n) {
        Line newLine = new Line(str);
        currentLine = firstLine;
        if(n==1){
            newLine.setNextLine(firstLine);
            firstLine = newLine;
            numOfLines++;
        }
        else{
            for (int i = 0; i < n - 2; i++) {
                currentLine = currentLine.getNextLine();
            }
            newLine.setNextLine(currentLine.getNextLine());
            currentLine.setNextLine(newLine);
        }
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
}
