package Day_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class PrintDepartment {
    public static int getTotalRollsOfPaper(List<List<String>> rolls){
        int totalPaper = 0;

        boolean[][] dp = new boolean[rolls.size()][rolls.get(0).size()];
        // conditions for validity
        // less than four of the adjacent cells must be "@"

        /*
        *   (1-1, j-1) (i-1,j) (i-1, j+1)
        *   (i,j-1)    (i,j) (i, j+1)
        *   (i+1, j-1) (i+1, j) (i+1, j+1)
        * */

        // NAIVE APPROACH
        //int totalRolls = rolls.get(0).size() * rolls.size();
        for(int i = 0; i < rolls.size(); i++){
            for(int j = 0; j < rolls.getFirst().size(); j++){
                // if its a corner piece or edge piece and it's "@", its already valid
                // its currently double checking
//                if((i == 0 || j == 0 || i == rolls.size() -1 || j == rolls.getFirst().size() - 1) && rolls.get(i).get(j).equals("@") && !dp[i][j]){
//                    dp[i][j] = true;
//                    totalPaper++;
//                }

                if(rolls.get(i).get(j).equals("@") && !dp[i][j]){

                    int[][] directions = {
                            {-1, -1}, {-1, 0}, {-1, 1},
                            { 0, -1}, /*{i,j}*/{ 0, 1},
                            { 1, -1}, { 1, 0}, { 1, 1}
                    };

                    int count = 0;
                    for (int[] dir : directions) {
                        int r = i + dir[0];
                        int c = j + dir[1];

                        // check bounds safely.
                        // if in bounds AND equals "@", increment.
                        if (r >= 0 && r < rolls.size() && c >= 0 && c < rolls.getFirst().size()) {
                            if (rolls.get(r).get(c).equals("@")) {
                                count++;
                            }
                        }
                    }

                    if (count < 4) {
                        dp[i][j] = true;
                        totalPaper++;
                    }
                }
            }
        }
        return totalPaper;
    }
    public static int solvePartTwo(List<List<String>> rolls) {
        int totalRemoved = 0;
        boolean changed;

        // continues as long as we keep finding paper to remove
        do {
            changed = false;
            List<int[]> deathNote = new ArrayList<>();

            // find every single paper that is currently valid to remove
            for (int i = 0; i < rolls.size(); i++) {
                for (int j = 0; j < rolls.get(0).size(); j++) {
                    // must be a paper ("@")
                    if (rolls.get(i).get(j).equals("@")) {
                        // check if it has less than four "@" neighbours
                        if (countNeighbors(rolls, i, j) < 4) {
                            deathNote.add(new int[]{i, j});
                        }
                    }
                }
            }

            // if we found any candidates, remove them all
            if (!deathNote.isEmpty()) {
                changed = true;
                totalRemoved += deathNote.size();

                for (int[] coords : deathNote) {
                    rolls.get(coords[0]).set(coords[1], "."); // Replace @ with .
                }
            }

        } while (changed);

        return totalRemoved;
    }

    private static int countNeighbors(List<List<String>> rolls, int r, int c) {
        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                { 0, -1},          { 0, 1},
                { 1, -1}, { 1, 0}, { 1, 1}
        };

        int count = 0;
        for (int[] dir : directions) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            // Bounds check
            if (nr >= 0 && nr < rolls.size() && nc >= 0 && nc < rolls.get(0).size()) {
                // Only count if it is currently paper
                if (rolls.get(nr).get(nc).equals("@")) {
                    count++;
                }
            }
        }
        return count;
    }
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/Day_4/input.txt");
        Scanner scanner = new Scanner(file);


        List<List<String>> list = new ArrayList<>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            list.add(line
                    .codePoints().mapToObj(Character :: toString)
                    .collect(Collectors.toList()));

        }
        System.out.println("Total Rolls Of Paper: " + getTotalRollsOfPaper(list));
        System.out.println("Total Paper Taken: " + solvePartTwo(list));
    }
}
