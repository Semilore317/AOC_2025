package Day_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PrintingDepartment {
    public static int getAccessibleRollsOfPaper(List<List<String>> papers) {
        // paper rolls -> @
        // emtpy slots -> .
        // a roll of paper can only be accessed if there are less than 4 adjacent paper rolls

        // dp approach
        boolean[][] dp = new boolean[papers.get(0).size()][papers.size()];

        //base case
        dp[0][0] = true;
        for(int i=0;i<papers.size();i++){
            for(int j = i; j < papers.size(); j++){
                // pass some checks;
                // if "@" count in either direction < 4
                // then it is accessible
                if(papers.get(i).get(j).equals("Paper")){}
            }
        }

        return 0;
    }
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/Day_4/input.txt");
        Scanner scanner = new Scanner(file);

        // while lines exist in the text file
        // each line should be parsed as a list of string characters
        // the result should be a 2D list

        List<List<String>> list = new ArrayList<List<String>>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            list.add(line.codePoints()
                    .mapToObj(Character::toString)
                    .collect(Collectors.toList()));
        }

//        // sanity check
//        for(List<String> l: list){
//            for(String c: l){
//                System.out.print(c);
//            }
//            System.out.println("\n");
//        }

        System.out.println(getAccessibleRollsOfPaper(list));
    }
}
