/**
 =============================================================================
 Project:      =        repl
 Created:      =        1/28/16
 Author:       =        Tim Siwula <tcsiwula@usfca.edu>
 University:   =        University of San Francisco
 Class:        =        Computer Science 345: Programming Languages
 Liscense:     =        GPLv2
 Version:      =        0.001
 ==============================================================================
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Stack;

public class NestedReader
{
    StringBuilder buf;              // fill this as you process, character by character
    BufferedReader stdin;           // where are we reading from?

    public char getLookaheadCharacter()
    {
        return ((char)lookaheadCharacter);
    }

    public int getLookaheadCharacterInt()
    {
        return (lookaheadCharacter);
    }

    public char getCurrentChar()
    {
        char last = (char) buf.indexOf(String.valueOf(buf.length()-1));
        return last;
    }

    int lookaheadCharacter;          // current character of lookahead; reset upon each getNestedString() call
    Stack<Character> stack;
    int counter = 0;


    public void printBuf()
    {
        System.out.println("buf = ");
        System.out.println(buf.toString());
    }


    public NestedReader(BufferedReader stdin)
    {
        this.stdin = stdin;
        buf = new StringBuilder();
        stack = new Stack<Character>();
    }


    public String getNestedString() throws IOException
    {

        //        while(((getLookaheadCharacter() != '\n') && stack.isEmpty()) ||  (getLookaheadCharacter() != '\n'))
        //        while(stack.isEmpty() ||  (getLookaheadCharacter() != '\n'))
        //        {
        //            System.out.println("!stack.isEmpty() = " + !stack.isEmpty());
        //            System.out.println("(getLookaheadCharacter() != \n) = " +  (getLookaheadCharacter() != '\n'));

        while((getLookaheadCharacter() != '\n'))
        {
        switch ((char) lookaheadCharacter)
        {
        case '{':
            System.out.println("Case # 1.1" + " c = " + (char) lookaheadCharacter);
            stack.push('}');
            //System.out.println("pushed " + (char) lookaheadCharacter + "on to the stack in nested reader.");
            consume();
            break;
        case '[':
            System.out.println("Case # 1.2" + " c = " + (char) lookaheadCharacter);
            stack.push(']');
            // System.out.println("pushed " + (char) lookaheadCharacter + "on to the stack in nested reader.");
            consume();
            break;
        case '(':
            System.out.println("Case # 1.3");
            stack.push(')');
            //System.out.println("pushed " + (char) lookaheadCharacter + "on to the stack in nested reader.");
            //
            consume();
            break;

        // pop and check
        case '}':
            System.out.println("Case # 2.1 " + " c = " + (char) lookaheadCharacter);

            if (stack.empty())
            {
                consume();
                String result = buf.toString();
                //buf.setLength(0);
                return result;
            }

            if ((stack.pop()) == '}')
            {
                System.out.println("    Case # 2.1.1");
                consume();                                       // consume until the end of the line.
            } else
            {
                System.out.println("    Case # 2.1.2 - error");
                // System.out.println("Error detected. Top of stack mismatch lookahead.");
                consume();
                String result = buf.toString();
                buf.setLength(0);
                return result;      // error detected, return current buffer to Driver
            }
            break;

        case ']':
            System.out.println("Case # 2.2" + " c = " + (char) lookaheadCharacter);

            if ((stack.pop()) == ']')
            {
                System.out.println("    Case # 2.3.1" + " c = " + (char) lookaheadCharacter);
                consume();
                break;// consume until the end of the line.
            } else
            {
                System.out.println("    Case # 2.3.2 - error");
                String result = buf.toString();
                buf.setLength(0);
                return result;
            }

        case ')':
            System.out.println("Case # 2.3" + " c = " + (char) lookaheadCharacter);
            if ((stack.pop()) == ')')
            {
                System.out.println("    Case # 2.3.1");
                consume();                                       // consume until the end of the line.
            } else
            {
                System.out.println("    Case # 2.3.2 - error");
                return buf.toString();
            }
            break;

        // case 3 - string literal/char, consume until the closing character.
        // not sure how this is different from the default case.


        // case 4 - Comments.
        case '/':
            System.out.println("Case # 4" + " c = " + (char) lookaheadCharacter);
            consume();      // get the next character in pattern and see if it is also a /.

            if (((char) lookaheadCharacter) == '/')
            {
                System.out.println("    Case 4.1" + " c = " + (char) lookaheadCharacter);
                while ((char) lookaheadCharacter != '\n')            // while not a new line character,
                {
                    consume();                                       // consume until the end of the line.
                }
                break;
            }
            break;

        // case 5 - \n & empty stack. Return current buffer.
        case '\n':
        {
            if (stack.isEmpty())
            {
                //System.out.println("    Case # 5.1");
                String result = buf.toString();
                consume();
                buf.setLength(0);
                return result;
            } else
            {
                // System.out.println("    Case # 5.2");
                consume();
            }
            //break;
        }

        // case 6 - Default case. Consume.
        default:
            //System.out.println("Case # 6" + " c = " + (char) lookaheadCharacter);
            consume();
        }

    }
        //System.out.println("Case # 7");
        String result = buf.toString();
        buf.setLength(0);
        return result;
    }

     void consume() throws IOException
    {
        if ((char)lookaheadCharacter != '\u0000')
            buf.append((char)lookaheadCharacter);                      // adds character to buffer
        lookaheadCharacter = stdin.read();               // refill the lookahead character
       // System.out.println("lookaheadCharacter = " + (char)lookaheadCharacter);

        counter++;
    }


    private boolean isNewLine()
    {
        return false;
    }


    private static boolean isComment(String line)
    {
        if(line.matches("//") || line.contains("//"))
        {
            System.out.println("This is a comment");
            return true;
        }
        else
        {
            System.out.println("No comments detected");
            return false;
        }
    }

}