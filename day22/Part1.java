import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    public static void main(String[] args) {
        //represent each field with a byte to save memory
        byte cubes[][][] = new byte[101][101][101];
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                cubes = setCubesForString(line, cubes);
            }
        } catch (Exception e) {
            System.out.println("crashed reading file: " + e.getMessage());
        }
        int total = 0;
        for (int i = 0; i < 101; i++) {
            for (int j = 0; j < 101; j++) {
                for (int k = 0; k < 101; k++) {
                    total += cubes[i][j][k];
                }
            }
        }
        System.out.println("total: " + total);
    }

    public static byte[][][] setCubes(boolean on, int min_x, int max_x, int min_y, int max_y, int min_z, int max_z, byte cubes[][][]) {
        for (int x = reduceToArraySize(min_x), n = reduceToArraySize(max_x); x <= n ; x++) {
            for (int y = reduceToArraySize(min_y), m = reduceToArraySize(max_y); y <= m ; y++) {
                for (int z = reduceToArraySize(min_z), o = reduceToArraySize(max_z); z <= o ; z++) {
                    if (on)
                        cubes[x][y][z] = 1;
                    else
                        cubes[x][y][z] = 0;
                }
            }
        }
        return cubes;
    }

    public static byte[][][] setCubesForString(String line, byte[][][] cubes) {
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
        for(int j = 0; j < value_array.length; j = j+2) {
            if ((value_array[j]<-50 && value_array[j+1]<-50) || (value_array[j]>50 && value_array[j+1]>50)) {
                value_array[j] = 1;
                value_array[j+1] = 0;
            }
        }
        return setCubes(on, value_array[0], value_array[1], value_array[2], value_array[3], value_array[4], value_array[5], cubes);
    }

    public static int reduceToArraySize(int x) {
        x += 50;
        if (x>100)
           return 100;
        if (x<0)
            return 0;
        return x;
    }
}