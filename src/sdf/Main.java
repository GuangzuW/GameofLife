package sdf;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        int[][] grid;
        int[] coordinateStart = new int[2];
        String[] splited;
        // int row;

        try (Scanner scanner = new Scanner(new FileReader(args[0]))) {
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (input.charAt(0) == '#') {
                    continue;
                } else if (input.charAt(0) == 'G') {
                    splited = input.trim().split(" ");
                    grid = new int[Integer.parseInt(splited[1])][Integer.parseInt(splited[2])];
                    while (scanner.hasNext()) {
                        input = scanner.nextLine();
                        switch (input.charAt(0)) {
                            case 'S':
                                splited = input.trim().split(" ");
                                coordinateStart[0] = Integer.parseInt(splited[1]);
                                coordinateStart[1] = Integer.parseInt(splited[2]);
                                break;
                            case 'D':
                                break;
                            case '#':
                                break;
                            default:
                                splited = input.split("");

                                for (int i=0;i<splited.length;i++){
                                    if (splited[i].charAt(0)==' ') {
                                        grid[coordinateStart[0]][i] = 0;
                                    } else{
                                        grid[coordinateStart[0]][i] = 1;
                                    }
                                }
                                coordinateStart[0] += 1;
                        };
                    }
                    System.out.println("Initial: ");
                    display(grid);
                    for (int i=0;i<5;i++){
                        nextGeneration(grid);
                    }
                    System.out.println("After 5 gennration: ");
                    display(grid);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void display(int[][] grid) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    System.out.printf("O ");
                } else {
                    System.out.printf("X ");
                }
            }
            System.out.println();
        }
    }

    static void nextGeneration(int[][] grid) {
        int[][] newArray = new int[grid.length + 2][grid[0].length + 2];// row and col +2 for edge issue
        // copy grid to newarray
        for (int i = 1; i <= grid.length; i++) {
            for (int j = 1; j <= grid[0].length; j++) {
                newArray[i][j] = grid[i - 1][j - 1];
            }
        }
        // loop every celll to check number of alive neighbour for each cell
        for (int i = 1; i <= grid.length; i++) {

            for (int j = 1; j <= grid[0].length; j++) {
                int neighbourAlive = 0;

                for (int m = -1; m <= 1; m++) {// loop every neighbour
                    for (int n = -1; n <= 1; n++) {
                        neighbourAlive += newArray[i + m][j + n];// count numbers of alive neighbour include self
                    }
                }
                neighbourAlive -= newArray[i][j];// mins self if alive

                if (grid[i-1][j-1] == 1) {
                    if (neighbourAlive <= 1 || neighbourAlive >= 4) {
                        grid[i-1][j-1] = 0;
                    } else {
                        continue;
                    }
                } else if (neighbourAlive == 3) {
                    grid[i-1][j-1] = 1;
                }
            }
        }
    }

}