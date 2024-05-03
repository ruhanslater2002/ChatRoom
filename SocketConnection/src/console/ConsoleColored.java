// COLORS OBJECT OUTPUTS ON THE CONSOLE WITH COLOR CODES

package console;


public class ConsoleColored {
    private static final String green = "\u001B[32m";
    private static final String yellow = "\u001B[33m";
    private static final String red = "\u001B[31m";
    private static final String lightBlue = "\u001B[94m";
    private static final String reset = "\u001B[0m";

    public static String color(String string, String color) {
        if (color.equals("green")) {
            string = green + string + reset;
            return string;
        }

        else if (color.equals("red")) {
            string = red + string + reset;
            return string;
        }

        else if (color.equals("yellow")) {
            string = yellow + string + reset;
            return string;
        }

        else if (color.equals("lightblue")) {
            string = lightBlue + string + reset;
            return string;
        }

        else {
            return string;
        }
    }
}
