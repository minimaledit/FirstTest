import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
//
public class Program {
    public static boolean isNumber(char c)
    {
        if (c >= '0' && c <= '9')
            return true;
        else
            return false;
    }
    public static boolean isDec(char c)
    {
        if(c == '+' || c == '-' || c == '*'|| c == '/' || c == '(' || c == '^')
            return true;
        else return false;
    }
    public static void main(String[] args) {
        Scanner sin = new Scanner(System.in);
        String fileName = sin.nextLine();
        LinkedList<String> inputText = new LinkedList<>();
        LinkedList<String> outputText = new LinkedList<>();

        try {
            Scanner in = new Scanner(new File(fileName));
            while (in.hasNext())
                inputText.add(in.nextLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int j = 0; j < inputText.size(); j++) {
            String item = inputText.get(j);
            System.out.println(item);
            Stack stack = new Stack();
            Stack stack1 = new Stack();
            boolean flag = false;
            for (int i = 0; i < item.length(); i++) {
                char c = item.charAt(i);
                while (!isDec(c) && !isNumber(c)) {
                    ++i;
                    if (i < item.length())
                        c = item.charAt(i);
                    if (i == item.length())
                        break;
                }
                if (i == item.length())
                    break;
                StringBuilder SB = new StringBuilder("");
                if (c == '-' && flag) {
                    ++i;
                    if (i < item.length())
                        c = item.charAt(i);
                    SB.append('-');
                }
                while (isNumber(c) || c == '.') {
                    flag = false;
                    SB.append(c);
                    if (++i >= item.length()) break;
                    c = item.charAt(i);
                }
                if (!SB.toString().equals("")) {
                    stack.push(new BigDecimal(SB.toString()));

                }
                if (c == '^') {

                    if ((!stack1.empty()))
                        if (((char) stack1.peek() == '^')) {
                            stack.push(stack1.pop());
                        }
                }
                if (c == '*' || c == '/' || c == '+' || c == '-') {
                    if ((!stack1.empty()))
                        if (((char) stack1.peek() == '*') || ((char) stack1.peek() == '/') || ((char) stack1.peek() == '^')) {
                            stack.push(stack1.pop());
                        }
                }
                if (c == '+' || c == '-') {
                    if ((!stack1.empty()))
                        if (((char) stack1.peek() == '*') || ((char) stack1.peek() == '/') || ((char) stack1.peek() == '+') || ((char) stack1.peek() == '-') || ((char) stack1.peek() == '^')) {
                            stack.push(stack1.pop());
                        }
                }
                if (isDec(c)) {
                    stack1.push(c);
                    flag = true;
                }
                if (c == ')') {
                    char cha = c;
                    while (cha != '(') {
                        cha = (char) stack1.pop();
                        if (cha != '(' && cha != ')')
                            stack.push(cha);
                    }
                }
            }
            while (!stack1.empty()) {
                stack.push(stack1.pop());
            }
            Stack stack2 = new Stack<>();

            while (!stack.empty()) {
                stack2.push(stack.pop());
            }
            while (!stack2.empty()) {
                Object c = stack2.pop();
                if (c.getClass() == BigDecimal.class) {
                    stack1.push((BigDecimal) c);
                    continue;
                } else {
                    if ((char) c == '*' && (stack1.size() != 1))
                        stack1.push(((BigDecimal) stack1.pop()).multiply((BigDecimal) stack1.pop()));
                    if ((char) c == '^' && (stack1.size() != 1)) {
                        BigDecimal a = (BigDecimal) stack1.pop();
                        if (a.intValue() >= 1)
                            stack1.push(((BigDecimal) stack1.pop()).pow(a.intValue()));
                        else {
                            stack1.push(new BigDecimal(Math.pow(((BigDecimal) (stack1.pop())).doubleValue(), a.doubleValue())));
                        }
                    }
                    if ((char) c == '/' && (stack1.size() != 1)) {
                        Double a = ((BigDecimal) stack1.pop()).doubleValue();
                        stack1.push(new BigDecimal(((Double) (((BigDecimal) stack1.pop()).doubleValue() / a)).toString()));
                    }
                    if ((char) c == '+' && (stack1.size() != 1))
                        stack1.push(((BigDecimal) stack1.pop()).add((BigDecimal) stack1.pop()));
                    if ((char) c == '-') {
                        BigDecimal a = (BigDecimal) stack1.pop();
                        if (!stack1.empty() && stack1.peek().getClass() != char.class) {
                            stack1.push(((BigDecimal) stack1.pop()).subtract(a));
                        } else
                            stack1.push((a).multiply(new BigDecimal(-1)));
                    }
                }
            }
            String t = stack1.pop().toString();
            outputText.add(t);
        }
        try(FileWriter writer = new FileWriter("answer.out", false)) {
            while(!outputText.isEmpty()) {
                writer.write(outputText.get(0) + '\n');
                outputText.pop();
            }
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
