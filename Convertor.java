

import java.math.BigDecimal;
import java.util.Scanner;

public class Convertor {

    private static final Scanner scanner = new Scanner(System.in);
    private static int sourceBase;
    private static int targetBase;

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            String input = scanner.nextLine();
            if ("/exit".equals(input)) {
                return;
            } else {
                String[] bases = input.split(" ");
                sourceBase = Integer.parseInt(bases[0]);
                targetBase = Integer.parseInt(bases[1]);
                convert();
            }
        }
    }

    private static void convert() {
        while (true) {
            System.out.printf("Enter number in base %d to convert " +
                    "to base %d (To go back type /back)%n", sourceBase, targetBase);
            String input = scanner.nextLine();
            if ( "/back".equals(input)) {
                return;
            } else {
                String result;
                if (sourceBase == 10) {
                    result = convertFromDecimal(input, targetBase);
                } else {
                    result = convertFromDecimal(convertToDecimal(input, sourceBase), targetBase);
                }
                System.out.println("Conversion result: " + result);
            }
        }
    }

    private static String convertToDecimal(String number, int sourceBase) {
        StringBuilder num = new StringBuilder(String.valueOf(number)).reverse();
        BigDecimal result = BigDecimal.valueOf(0);
        for (int i = 0; i < num.length(); i++) {
            result = result.add(
                    BigDecimal.valueOf(getNumberFromSymbol(num.charAt(i)))
                            .multiply(BigDecimal.valueOf(sourceBase).pow(i)));
        }
        return result.toString();
    }

    private static String convertFromDecimal(String number, int targetBase) {
        BigDecimal num = new BigDecimal(number);
        BigDecimal base = BigDecimal.valueOf(targetBase);
        StringBuilder result = new StringBuilder();
        do {
            int remainder = num.remainder(base).intValue();
            result.append(getSymbolFromNumber(remainder));
            num = num.divide(base);
        } while (!num.equals(BigDecimal.ZERO));
        return result.reverse().toString();
    }

    private static int getNumberFromSymbol(char symbol) {
        return symbol >= '0' && symbol <= '9'
                ? Character.getNumericValue(symbol)
                : Character.toLowerCase(symbol) - 'a' + 10;
    }

    private static char getSymbolFromNumber(int number) {
        return number < 10
                ? (char) (number + '0')
                : (char) (number + 'a' - 10);
    }
}
