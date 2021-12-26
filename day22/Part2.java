import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.lang.Math;

public class Part2 {

    public static void main(String[] args) {
        ArrayList<Cube> cube_list = new ArrayList<Cube>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                cube_list.add(getCubeFromString(line));
            }
        } catch (Exception e) {
            System.out.println("crashed reading file: " + e.getMessage());
        }
        System.out.println("total: " + calculateTotal(cube_list));
    }

    public static long calculateTotal(ArrayList<Cube> cube_list) {
        ArrayList<Cube> activated_cubes = new ArrayList<Cube>();
        activated_cubes.add(cube_list.remove(0));

        for (Cube to_change: cube_list) {
            ArrayList<Cube> result = new ArrayList<Cube>();
            for (Cube activated_cube: activated_cubes) {
                if (to_change.isCompletlyInCube(activated_cube))
                    result.add(activated_cube);
            }
            activated_cubes.removeAll(result);
            result = new ArrayList<Cube>();
            if (to_change.on)
                result.add(to_change);
            for (Cube activated_cube: activated_cubes) {
                if (activated_cube.cubesOverlap(to_change)) {
                    if (activated_cube.on)
                        result.add(calculateIntersection(activated_cube, to_change, false));
                    else
                        result.add(calculateIntersection(activated_cube, to_change, true));
                }
            }
            activated_cubes.addAll(result);
        }
        for (Cube c : activated_cubes) {
            //System.out.println(c.on + " x=" + c.low_x + ".." + c.high_x + ",y=" + c.low_y + ".." + c.high_y + ",z=" + c.low_z + ".." + c.high_z);
        }

        long total = 0;
        for (Cube c : activated_cubes) {
            if (c.on)
                total += Math.abs(((long)c.high_x-c.low_x+1))*Math.abs((c.high_y-c.low_y+1))*Math.abs((c.high_z-c.low_z+1));
            else
                total -= Math.abs(((long)c.high_x-c.low_x+1))*Math.abs((c.high_y-c.low_y+1))*Math.abs((c.high_z-c.low_z+1));
        }
        return total;
    }

    public static Cube calculateIntersection (Cube cube_1, Cube cube_2, boolean cube_on) {
        int low_x, high_x, low_y, high_y, low_z, high_z;
        low_x = getBigger(cube_1.low_x, cube_2.low_x);
        high_x = getSmaller(cube_1.high_x, cube_2.high_x);

        low_y = getBigger(cube_1.low_y, cube_2.low_y);
        high_y = getSmaller(cube_1.high_y, cube_2.high_y);

        low_z = getBigger(cube_1.low_z, cube_2.low_z);
        high_z = getSmaller(cube_1.high_z, cube_2.high_z);

        return new Cube(low_x, high_x, low_y, high_y, low_z, high_z, cube_on);

    }

    public static int getSmaller(int x, int y) {
        if (x<y)
            return x;
        return y;
    }

    public static int getBigger(int x, int y) {
        if (x<y)
            return y;
        return x;
    }

    public static Cube getCubeFromString(String line) {
        int value_array[] = new int[6];
        int i = 0;
        boolean on;
        if (line.contains("on"))
            on = true;
        else
            on = false;
        Matcher matcher = Pattern.compile("-?\\d+").matcher(line);
        while(matcher.find()) {
            value_array[i] = Integer.parseInt(matcher.group());
            i++;
        }
        return new Cube(value_array[0], value_array[1], value_array[2], value_array[3], value_array[4], value_array[5], on);
    }
}