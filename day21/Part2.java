public class Part2 {

    // 3: 111
    // 4: 112, 121, 211,
    // 5: 113, 122, 131, 212, 221, 311,
    // 6: 123, 132, 213, 222, 231, 312, 321,
    // 7: 133, 223, 232, 313, 322, 331,
    // 8: 233, 323, 332,
    // 9: 333

    //overview how many possibilities for each result there are
    final static int[] possibilities = new int[] {1, 3, 6, 7, 6, 3, 1};

    public static void main(String[] args) {
        Winner total_winner = calculateUniverses(7, 4, 0, 0, true);
        long max = 0;
        if (total_winner.w1> total_winner.w2)
            max = total_winner.w1;
        else
            max = total_winner.w2;
        System.out.println(max);
    }

    //calculate winner for every universe, group universes togheter that have the same field as result, for example universe 1,2,3 and 2,2,2
    public static Winner calculateUniverses(int p1_field, int p2_field, int p1_score, int p2_score, boolean p1_move) {
        Winner w = new Winner(0, 0);
        int temp_score, temp_field;
        //check if its p1 or p2s turn
        if (p1_move) {
            for (int i = 0; i < 7; i++) {
                temp_field = calculateEndPoint(p1_field + i + 3);
                temp_score = temp_field + p1_score;
                if (temp_score>=21)
                    w.addW1(1*possibilities[i]);
                else
                    w.addWinner(calculateUniverses(temp_field, p2_field, temp_score, p2_score, false), possibilities[i]);
            }
        } else {
            for (int i = 0; i < 7; i++) {
                temp_field = calculateEndPoint(p2_field + i + 3);
                temp_score = temp_field + p2_score;
                //multiply result of dimensions with
                if (temp_score>=21)
                    w.addW2(1*possibilities[i]);
                else
                    w.addWinner(calculateUniverses(p1_field, temp_field, p1_score, temp_score, true), possibilities[i]);
            }
        }
        return w;
    }

    public static int calculateEndPoint(int field) {
        if (field%10 == 0) {
            return 10;
        }
        return field%10;
    }
}