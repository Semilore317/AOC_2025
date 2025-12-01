package Day_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SecretEntrance {
    // parse through each line using scanner
    // if the line has R, add
    // else if it has L, subtract
    // each time the value == 0, password++
    public static int passwordFinder(List<String> input){
        int password = 0;
        int current_position = 50;
        //computation
        // read first char
        for (String s : input) {
            char first_char = s.charAt(0);
            int turns = Integer.parseInt(s.substring(1));
            if(first_char == 'L'){
                //current_position -= turns;
                current_position = Math.floorMod(current_position - turns, 100);
                //current_position = (current_position - turns)%100;
                System.out.println(current_position);
            }else if(first_char == 'R'){
                //current_position += turns;
                current_position = Math.floorMod(current_position + turns, 100);
                //current_position = (current_position+turns)%100;
                System.out.println(current_position);
            }
            if(current_position == 0){
                password++;
            }
        }
        return password;
    }
    public static int method_two(List<String> input){
        int password = 0;
        int current_postition = 50;
        // computation
        for (String s : input) {
            char first_char = s.charAt(0);
            int turns = Integer.parseInt(s.substring(1));
            if(first_char == 'L'){
                // decrement in units
                while(turns > 0){
                    current_postition = Math.floorMod(current_postition - 1, 100);
                    System.out.println(current_postition);
                    turns--;
                    if(current_postition == 0){
                        password++;
                    }
                }
            }else if(first_char == 'R'){
                // increment in units
                while(turns > 0){
                    current_postition = Math.floorMod(current_postition + 1, 100);
                    System.out.println(current_postition);
                    turns--;
                    if (current_postition == 0){
                        password++;
                    }
                }
            }
        }

        return password;
    }
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:/Users/user/Documents/AOC_2025/src/Day_1/input.txt");
        Scanner scanner = new Scanner(file);
        List<String> list = new ArrayList<>();

        while(scanner.hasNextLine()){
            list.add(scanner.nextLine());
        }
        //System.out.println(passwordFinder(list));
        System.out.println(method_two(list));
    }
}
