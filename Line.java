import java.io.Serializable;

public class Line implements Serializable {
    String text;
    Line nextLine;
    int lineNumber;

    public Line(String str, int lineNumber){
        text = str;
        nextLine = null;
        this.lineNumber = lineNumber;
    }

    public Line(Line a){
        text = a.text;
        nextLine= a.nextLine;
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
    void setText(String s){
        text=s;
    }

    public int getLineNumber() {
        return lineNumber;
    }


    @Override
    public Line clone() {
        try {
            return (Line) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
