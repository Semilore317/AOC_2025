package Day_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;


public class Lobby {
    public static long totalOutputJoltage(BigInteger[] banks) {
        long totalOutputJoltage = 0; // changed to long to prevent overflow

        for (BigInteger bank : banks) {
            // conversion is a bit messy
            int[] bank_arr = bank.toString().chars().map(c -> c - '0').toArray();

            int maxJoltage = 0;

            for (int i = 0; i < bank_arr.length; i++) {
                for (int j = i + 1; j < bank_arr.length; j++) {
                    // Logic: Forms a 2-digit number from left-to-right
                    int currentJoltage = (bank_arr[i] * 10) + bank_arr[j];
                    if (currentJoltage > maxJoltage) {
                        maxJoltage = currentJoltage;
                    }
                }
            }
            totalOutputJoltage += maxJoltage;
        }

        return totalOutputJoltage;
    }
    public static BigInteger totalOutputJoltage_2(BigInteger[] banks) {
        BigInteger totalOutputJoltage = BigInteger.ZERO;

        for (BigInteger bank : banks) {
            // get the digits
            String numStr = bank.toString();
            int n = numStr.length();
            int k = 12; // we need a sequence of 12 out of 100 digits

            //  Monotonic Stack Strategy (Greedy) to find LARGEST subsequence
            // we can drop 100 -12 digits
            int drop = n - k;
            StringBuilder stack = new StringBuilder();

            for (char digit : numStr.toCharArray()) {
                // while stack has items, current digit > top of stack,
                // while drops still remain, pop the smaller digit
                while (drop > 0 && stack.length() > 0 && stack.charAt(stack.length() - 1) < digit) {
                    stack.deleteCharAt(stack.length() - 1);
                    drop--;
                }
                stack.append(digit);
            }

            // truncate to exactly k digits
            String resultStr = stack.substring(0, k);
            
            totalOutputJoltage = totalOutputJoltage.add(new BigInteger(resultStr));
        }

        return totalOutputJoltage;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String filePath = "C:\\Users\\user\\Documents\\AOC_2025\\src\\Day_3\\input.txt";
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        List<BigInteger> banksList = new ArrayList<>();
        while (scanner.hasNext()) {
            if (scanner.hasNextBigInteger()) { // Optional safety check
                banksList.add(new BigInteger(scanner.next()));
            } else {
                scanner.next();
            }
        }

        // Convert List to Array in one clean line
        BigInteger[] banks = banksList.toArray(new BigInteger[0]);

        System.out.println("Total output Joltage: " + totalOutputJoltage(banks));
        System.out.println("Total output Joltage_2: " + totalOutputJoltage_2(banks));
    }
}
