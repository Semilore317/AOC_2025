package Day_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GIftShop {
    public static long sumOfInvalidIds(List<String> ids) {
        long sumOfInvalidIds = 0;
        for (String id : ids) {
            // check the numbers within the range given
                // divide into two equal halves, if equal then invalid
            // if it is it's invalid -> counter++
            String start = String.valueOf(Long.parseLong(id.substring(0, id.indexOf('-'))));
            String end = String.valueOf(Long.parseLong(id.substring(id.indexOf('-') + 1)));

            for(long i = Long.parseLong(start); i <= Long.parseLong(end); i++) {
                long length = String.valueOf(i).length();

                if(String.valueOf(i).substring(0, Math.toIntExact((length / 2))).equals(String.valueOf(i).substring((int) (length/2), Math.toIntExact(length)))) {
                    sumOfInvalidIds += i;
                }
            }
        }

        return sumOfInvalidIds;
    }
    public static long sumOfInvalidIds_2(List<String> ids) {
        long sumOfInvalidIds = 0;
        // using string rotation

        for (String id : ids) {
            String start = String.valueOf(Long.parseLong(id.substring(0, id.indexOf('-'))));
            String end = String.valueOf(Long.parseLong(id.substring(id.indexOf('-') + 1)));

            for(long i = Long.parseLong(start); i <= Long.parseLong(end); i++) {
                long length = String.valueOf(i).length();

                String superString = String.valueOf(i).concat(String.valueOf(i));
                int index = superString.indexOf(String.valueOf(i), 1);

                if(index != -1 && index != String.valueOf(i).length()){
                    sumOfInvalidIds += i;
                }
            }
        }
        return sumOfInvalidIds;
    }
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\user\\Documents\\AOC_2025\\src\\Day_2\\input.txt");
        Scanner scanner = new Scanner(file);

        // find the indices of the hyphens and commas
        // split the string at those indices
        //rfsdList<String> list = new ArrayList<>();
        String ranges = scanner.nextLine();
        //list.add(Arrays.toString((ranges.split(",", 0))));
        List<String> list = Arrays.asList(ranges.split(","));
        //System.out.println(sumOfInvalidIds(list));
        System.out.println(sumOfInvalidIds_2(list));
    }
}
