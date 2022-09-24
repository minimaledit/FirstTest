import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner sin = new Scanner(System.in);
        String fileName = sin.nextLine();
        LinkedList<String> inputText = new LinkedList<>();
        try {
            Scanner in = new Scanner(new File(fileName));
            while (in.hasNext())
                inputText.add(in.nextLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(int i = 0; i<inputText.size(); i++) {
            String value = inputText.get(i);
            System.out.println(value);
        }
        try(FileWriter writer = new FileWriter("answer.out", false)) {
            for (int i = 0; i < inputText.size(); i++) {
                String value = inputText.get(i);
                try {
                    writer.write(value + '\n');
                    writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}