import java.util.ArrayList;

public class Part1_2 {

    final static int energy_requirement[] = {1, 10, 100, 1000};

    public static void main(String[] args) {
        char layout[][] = {
                {'.','.','.','.','.','.','.','.','.','.','.'},
                {'#','#','D','#','C','#','D','#','B','#','#'},
                {'#','#','D','#','C','#','B','#','A','#','#'},
                {'#','#','D','#','B','#','A','#','C','#','#'},
                {'#','#','C','#','A','#','A','#','B','#','#'}
        };

        char small_layout[][] = {
                {'.','.','.','.','.','.','.','.','.','.','.'},
                {'#','#','D','#','C','#','D','#','B','#','#'},
                {'#','#','C','#','A','#','A','#','B','#','#'}
        };
        System.out.println(getCheapestSorting(small_layout, 0));
        System.out.println(getCheapestSorting(layout, 0));
    }

    private static int getCheapestSorting(char layout[][], int total) {
        int min = Integer.MAX_VALUE, current;
        boolean found_any_moves = false;

        if (sortingCorrect(layout))
            return total;

        char layout_copy[][] = copyArray(layout);

        for (int x = 0; x < layout_copy.length; x++) {
            for (int y = 0; y < layout_copy[0].length; y++) {
                if (layout_copy[x][y]>=65 && layout_copy[x][y]<=68) {
                    ArrayList<Triple> move_list = getMoveList(layout_copy, x, y);
                    for (Triple move : move_list) {
                        found_any_moves = true;
                        current = getCheapestSorting(makeMove(layout_copy, x, y, move.x, move.y), total+move.move_cost);
                        if (current != -1 && current<min)
                            min = current;
                    }
                }
            }
        }
        if(!found_any_moves)
            return -1;
        return min;
    }

    private static ArrayList<Triple> getMoveList(char layout[][], int x, int y) {
        ArrayList<Triple> move_list = new ArrayList<Triple>();
        char c = layout[x][y];
        //check if amphipod is happy where it is
        if (c-64 == y/2) {
            boolean chamber_correct = true;
            int temp_x = x;
            while(temp_x < layout.length) {
                if(layout[temp_x][y]-64 != y/2) {
                    chamber_correct = false;
                    break;
                }
                temp_x++;
            }
            if (chamber_correct)
                return move_list;
        }
        //if in hallway (x==0) check if can reach chamber and return move
        //if can not reach return empty list since there are no legal moves otherwise
        if (x==0) {
            int new_y = (c-64)*2;
            if(canEnterRoom(layout, new_y)) {
                int new_x = getFirstFreeIdx(layout, new_y);
                if (new_x != -1) {
                    int move_cost = canReach(layout, x, y, new_x, new_y);
                    if (move_cost != -1)
                        move_list.add(new Triple(new_x, new_y, move_cost));
                }
            }
            return move_list;
        //if in chamber check if can reach other destinationchamber and return move
        } else {
            int new_y = (c-64)*2;
            if(canEnterRoom(layout, new_y)) {
                int new_x = getFirstFreeIdx(layout, new_y);
                if (new_x != -1) {
                    int move_cost = canReach(layout, x, y, new_x, new_y);
                    if (move_cost != -1) {
                        move_list.add(new Triple(new_x, new_y, move_cost));
                        return move_list;
                    }
                }
            }
        }
        //otherwise check if it can reach any legal hallwaysquares
        int move_cost = canReach(layout, x, y, 0, 0);
        if (move_cost != -1)
            move_list.add(new Triple(0, 0, move_cost));
        move_cost = canReach(layout, x, y, 0, 10);
        if (move_cost != -1)
            move_list.add(new Triple(0, 10, move_cost));

        for (int new_y = 1; new_y < 10; new_y=new_y+2) {
            move_cost = canReach(layout, x, y, 0, new_y);
            if (move_cost != -1)
                move_list.add(new Triple(0, new_y, move_cost));
        }
        return move_list;
    }

    private static boolean canEnterRoom(char layout[][], int y) {
        for (int x = 1; x < layout.length ; x++) {
            if (layout[x][y] != '.' && layout[x][y]-64 != y/2)
                return false;
        }
        return true;
    }

    private static int getFirstFreeIdx(char layout[][], int y) {
        for (int x = 1; x < layout.length; x++) {
            if(x != 1 && layout[x][y] != '.')
                return x-1;
            if(x==layout.length-1)
                return layout.length-1;
        }
        return -1;
    }

    private static int canReach(char layout[][], int old_x, int old_y, int new_x, int new_y) {
        int total = 0, move_cost = energy_requirement[layout[old_x][old_y]-65];
        while(old_x!=0) {
            total++;
            old_x--;
            if (layout[old_x][old_y] != '.')
                return -1;
        }
        while(old_y!=new_y) {
            total++;
            if (old_y<new_y)
                old_y++;
            else
                old_y--;
            if (layout[old_x][old_y] != '.')
                return -1;
        }
        while(old_x!=new_x) {
            total++;
            old_x++;
            if (layout[old_x][old_y] != '.')
                return -1;
        }
        return total*move_cost;
    }

    private static boolean sortingCorrect(char layout[][]) {
        for (int x = 1; x < layout.length; x++) {
            for (int y = 2; y < layout[0].length-2; y=y+2) {
                if (layout[x][y]-64 != y/2)
                    return false;
            }
        }
        return true;
    }

    private static char[][] makeMove(char layout[][], int old_x, int old_y, int new_x, int new_y) {
        char layout_copy[][] = new char[layout.length][layout[0].length];
        for (int i = 0; i < layout.length; i++) {
            layout_copy[i] = layout[i].clone();
        }
        layout_copy[new_x][new_y] = layout_copy[old_x][old_y];
        layout_copy[old_x][old_y] = '.';
        return layout_copy;
    }

    private static char[][] copyArray(char layout[][]) {
        char layout_copy[][] = new char[layout.length][layout[0].length];
        for (int i = 0; i < layout.length; i++) {
            layout_copy[i] = layout[i].clone();
        }
        return layout_copy;
    }

    public static class Triple {
        int x;
        int y;
        int move_cost;

        public Triple(int x, int y, int move_cost) {
            this.x = x;
            this.y = y;
            this.move_cost = move_cost;
        }
    }
}