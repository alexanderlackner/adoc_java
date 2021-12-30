import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Part1 {
    public static void main(String[] args) {
        ArrayList<Coordinates> east_facing = new ArrayList<Coordinates>();
        ArrayList<Coordinates> south_facing = new ArrayList<Coordinates>();
        int floor[][] = new int[137][139];
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            int x = 0, y = 0;
            String line;
            while ((line = br.readLine()) != null) {
                y = 0;
                for (int i = 0; i < line.length(); i++) {
                    switch (line.charAt(i)) {
                        case '.':
                            floor[x][y] = 0;
                            break;
                        case '>':
                            floor[x][y] = 1;
                            east_facing.add(new Coordinates(x, y));
                            break;
                        case 'v':
                            floor[x][y] = 2;
                            south_facing.add(new Coordinates(x, y));
                            break;
                        default: break;
                    }
                    y++;
                }
                x++;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        System.out.println(getStepsUntilStuck(floor, east_facing, south_facing));
    }

    public static int getStepsUntilStuck(int floor[][], ArrayList<Coordinates> east_facing, ArrayList<Coordinates> south_facing) {
        boolean has_changed = true;
        int steps = 0;
        int floor_copy[][] = copy_array(floor);
        while (has_changed) {
            steps++;
            has_changed = false;
            for (Coordinates cord : east_facing) {
                int new_east = getNextMoveEast(floor, cord.x, cord.y);
                if (new_east != -1) {
                    floor_copy[cord.x][cord.y] = 0;
                    cord.y = new_east;
                    floor_copy[cord.x][cord.y] = 1;
                    has_changed = true;
                }
            }
            floor = copy_array(floor_copy);
            for (Coordinates cord : south_facing) {
                int new_south = getNextMoveSouth(floor, cord.x, cord.y);
                if (new_south != -1) {
                    floor_copy[cord.x][cord.y] = 0;
                    cord.x = new_south;
                    floor_copy[cord.x][cord.y] = 1;
                    has_changed = true;
                }
            }
            floor = copy_array(floor_copy);
        }
        return steps;
    }

    public static int getNextMoveEast(int floor[][], int x, int y) {
        if (y<138 && floor[x][y+1] == 0)
            return y+1;
        if (y==138 && floor[x][0] == 0)
            return 0;
        return -1;
    }

    public static int getNextMoveSouth(int floor[][], int x, int y) {
        if (x<136 && floor[x+1][y] == 0)
            return x+1;
        if (x==136 && floor[0][y] == 0)
            return 0;
        return -1;
    }

    public static int[][] copy_array(int floor[][]) {
        int floor_copy[][] = new int[137][139];
        for (int i = 0; i < floor.length; i++) {
            floor_copy[i] = floor[i].clone();
        }
        return floor_copy;
    }

    public static class Coordinates {
        int x;
        int y;

        public Coordinates (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}