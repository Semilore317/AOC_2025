package Day_7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Laboratories {
    public static int getTotalBeamSplits(List<char[]> char_grid){
        int totalBeamSplits = 0;
        Set<Integer> activeBeams = new HashSet<>();
        // find the position of 'S' in first line

        int startCol = -1;
        char[] firstRow = char_grid.getFirst();

        for(int i = 0; i < char_grid.size(); i++){
            if(firstRow[i] == 'S'){
                startCol = i;
                break;
            }
        }

        activeBeams.add(startCol);

        for(int i = 0; i < char_grid.size(); i++){
            char[] currentRow =  char_grid.get(i);
            Set<Integer> nextRowBeams = new HashSet<>();

            for(int col: activeBeams){
                if(col < 0 || col >= currentRow.length){
                    continue;
                }

                char currentChar = currentRow[col];

                if(currentChar == '^'){
                    totalBeamSplits++;

                    nextRowBeams.add(col - 1);
                    nextRowBeams.add(col + 1);
                }else{
                    nextRowBeams.add(col);
                }
            }
            activeBeams = nextRowBeams;
        }
        // track the position of the rays falling using the hashset3
        return totalBeamSplits;
    }

    //section 2
    public static long getTotalTimelines(List<char[]> grid) {
        // key = Column Index
        // value = Count of distinct timelines existing at this column
        Map<Integer, Long> activeTimelines = new HashMap<>();

        // find 's' - couldnt bother to hardcode it
        int startCol = -1;
        char[] firstRow = grid.get(0);
        for (int i = 0; i < firstRow.length; i++) {
            if (firstRow[i] == 'S') {
                startCol = i;
                break;
            }
        }

        // intialize: 1 timeline exists at the start
        activeTimelines.put(startCol, 1L);

        for (int r = 1; r < grid.size(); r++) {
            if (activeTimelines.isEmpty()) break;

            char[] currentRow = grid.get(r);
            Map<Integer, Long> nextRowTimelines = new HashMap<>();

            for (Map.Entry<Integer, Long> entry : activeTimelines.entrySet()) {
                int col = entry.getKey();
                long count = entry.getValue();

                // timelines that fly off are terminate
                if (col < 0 || col >= currentRow.length) {
                    continue;
                }

                char currentChar = currentRow[col];

                if (currentChar == '^') {
                    // go left
                    nextRowTimelines.put(col - 1, nextRowTimelines.getOrDefault(col - 1, 0L) + count);
                    // go right
                    nextRowTimelines.put(col + 1, nextRowTimelines.getOrDefault(col + 1, 0L) + count);

                } else {
                    // no split
                    nextRowTimelines.put(col, nextRowTimelines.getOrDefault(col, 0L) + count);
                }
            }
            activeTimelines = nextRowTimelines;
        }

        long totalTimelines = 0;
        for (long count : activeTimelines.values()) {
            totalTimelines += count;
        }

        return totalTimelines;
    }
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/Day_7/input.txt");
        Scanner scanner =  new Scanner(file);

        List<char[]> grid = new ArrayList<>();

        while(scanner.hasNextLine()){
            char[] line = scanner.nextLine().toCharArray();
            grid.add(line);
        }
        System.out.println("Total Beam Splits:" +  getTotalBeamSplits(grid));
        System.out.println("Total Timelines:" +  getTotalTimelines(grid));
    }
}
