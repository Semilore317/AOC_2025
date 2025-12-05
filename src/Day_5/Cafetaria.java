package Day_5;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cafetaria {
    //    record Range(BigInteger start, BigInteger end) {};
    private static boolean isBetween(BigInteger val, BigInteger start, BigInteger end) {
        return val.compareTo(start) >= 0 && val.compareTo(end) <= 0;
    }

    public static int getFreshIngredients(List<BigInteger[]> freshIngredientsRange, List<BigInteger> ingredients) {
        int freshIngredients = 0;
        for (BigInteger ingredient : ingredients) {
            for (BigInteger[] range : freshIngredientsRange) {
                if (isBetween(ingredient, range[0], range[1])) {
                    freshIngredients++;
                    break;
                }
            }
        }

        return freshIngredients;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/Day_5/input.txt");
        Scanner scanner = new Scanner(file);

        List<BigInteger[]> rangeList = new ArrayList<>();
        List<BigInteger> idList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            // handle the gap between sections
            if (line.isEmpty()) {
                continue;
            }

            if (line.contains("-")) {
                String[] parts = line.split("-");
                // Parse and add to list
                BigInteger start = BigInteger.valueOf(Long.parseLong(parts[0].trim()));
                BigInteger end = BigInteger.valueOf(Long.parseLong(parts[1].trim()));
                //rangeList.add(new Range(start, end));
                rangeList.add(new BigInteger[]{start, end});
            } else {
                // If it's not empty and has no hyphen, it must be an ID
                idList.add(new BigInteger(line));
            }
        }
        System.out.println("Fresh Ingredients:" + getFreshIngredients(rangeList, idList));
    }
}
