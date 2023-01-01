import java.io.*;

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

    public int getLineNumber() {
        return lineNumber;
    }


    public Line deepClone(Line object){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);
            return (Line) objectInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
