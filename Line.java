public class Line {
    String text;
    Line nextLine;

    public Line(String str){
        text = str;
        nextLine = null;
    }

    public void setNextLine(Line line) {
        nextLine = line;
    }

    public Line getNextLine() {
        return nextLine;
    }
    public String getText() {
        return text;
    }


    public void showText() {
        System.out.println(text);
    }
}
