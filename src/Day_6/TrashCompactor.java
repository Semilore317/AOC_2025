package Day_6;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TrashCompactor {
    public static BigInteger getGrandTotal(List<List<String>> list){
        BigInteger grandTotal = BigInteger.ZERO;

        for(List<String> operationArray : list){
            BigInteger a = new BigInteger(operationArray.getFirst());
            BigInteger b = new BigInteger(operationArray.get(1));
            BigInteger c = new BigInteger(operationArray.get(2));
            BigInteger d = new BigInteger(operationArray.get(3));
            String operator = operationArray.getLast();

            if(operator.equals("+")){
                BigInteger sum = a.add(b).add(c).add(d);
                grandTotal = grandTotal.add(sum);
            }else if(operator.equals("*")){
                BigInteger quotient = a.multiply(b).multiply(c).multiply(d);
                grandTotal = grandTotal.add(quotient);
            }
        }
        return grandTotal;
    }

    public static BigInteger getCephalopodTotal(List<String> lines) {
        BigInteger grandTotal = BigInteger.ZERO;

        // pad lines with spaces so they are all the same length
        int maxWidth = 0;
        for (String line : lines) maxWidth = Math.max(maxWidth, line.length());

        // create a padded version to avoid IndexOutOfBounds exceptions
        List<String> grid = new ArrayList<>();
        for (String line : lines) {
            // pad with spaces to the right
            grid.add(String.format("%-" + maxWidth + "s", line));
        }

        int height = grid.size();
        // the last row holds the operators
        String operatorRow = grid.get(height - 1);

        // state variables for the loop
        List<BigInteger> currentBlockNumbers = new ArrayList<>();
        String currentOperator = "+";

        // go from Right (maxWidth-1) to Left (0)
        for (int x = maxWidth - 1; x >= 0; x--) {

            StringBuilder columnNumber = new StringBuilder();
            boolean hasDigit = false;

            // Scan top to bottom (ignoring the operator row)
            for (int y = 0; y < height - 1; y++) {
                char c = grid.get(y).charAt(x);
                if (Character.isDigit(c)) {
                    columnNumber.append(c);
                    hasDigit = true;
                }
            }

            // check for operator
            char opChar = operatorRow.charAt(x);
            if (opChar == '+' || opChar == '*') {
                currentOperator = String.valueOf(opChar);
            }

            if (hasDigit) {
                currentBlockNumbers.add(new BigInteger(columnNumber.toString()));
            }

            // if column in empty space or we're at the far left, we're at the end of a "problem block"
            if (!hasDigit || x == 0) {
                if (!currentBlockNumbers.isEmpty()) {
                    BigInteger blockResult = (currentOperator.equals("*")) ? BigInteger.ONE : BigInteger.ZERO;

                    for (BigInteger num : currentBlockNumbers) {
                        if (currentOperator.equals("+")) blockResult = blockResult.add(num);
                        else blockResult = blockResult.multiply(num);
                    }

                    grandTotal = grandTotal.add(blockResult);

                    // Reset for the next problem
                    currentBlockNumbers.clear();
                    currentOperator = "+"; // Reset operator
                }
            }
        }

        return grandTotal;
    }
    public static void main(String[] arg) throws FileNotFoundException {
        File file = new File("src/Day_6/input.txt");
        Scanner scanner = new Scanner(file);

        List<List<String>> list = new ArrayList<>();
        List<String> rawLines = new ArrayList<>();

        while(scanner.hasNextLine()){
            String line =  scanner.nextLine();
            rawLines.add(line);

            // whitespace handling
            if(line.trim().isBlank()){
                continue;
            }

            String[] parts = line.trim().split("\\s+");

            for(int i = 0; i < parts.length; i++){
                if(list.size() <= i){
                    list.add(new ArrayList<>());
                }

                list.get(i).add(parts[i].trim());
            }
        }
        System.out.println(list.getFirst());
        // {49, 83, 16, 83, *}
        System.out.println(getGrandTotal(list));
        System.out.println("Part 2: " + getCephalopodTotal(rawLines));

    }
}
