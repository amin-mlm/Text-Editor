public class Page {
    Line firstLine;
    Line currentLine;
    Page nextPage;

    int pageNumber;


    public Page(String line, int pageNumber) {
        firstLine = new Line(line);
        currentLine = firstLine;
        nextPage = null;
        this.pageNumber = pageNumber;
    }

    public void addLine(String text) {
        Line newLine = new Line(text);
        currentLine.setNextLine(newLine);
        currentLine = newLine;
    }

    public Page getNextPage() {
        return nextPage;
    }
    public void setNextPage(Page page) {
        nextPage = page;
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
}
