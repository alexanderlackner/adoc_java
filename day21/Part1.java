public class Part1 {

    //most basic implementation I could think of
    public static void main(String[] args) {
        int p1_field = 7, p2_field = 4;
        int p1_score = 0, p2_score = 0;
        int die = 0, die_tracker = 0;

        while(p1_score < 1000 && p2_score < 1000) {
            for (int i = 0; i < 3; i++) {
                die = rollDie(die);
                die_tracker++;
                p1_field += die;
            }
            p1_field = calculateEndPoint(p1_field);
            p1_score += p1_field;
            if (p1_score>1000)
                break;

            for (int i = 0; i < 3; i++) {
                die = rollDie(die);
                die_tracker++;
                p2_field += die;
            }
            p2_field = calculateEndPoint(p2_field);
            p2_score += p2_field;
        }

        int loser = 0;
        if (p2_score>1000)
            loser = p1_score;
        else
            loser = p2_score;

        System.out.println(loser*die_tracker);
    }

    public static int rollDie(int die) {
        if (die<100)
            die++;
        else
            die = 1;
        return die;
    }

    public static int calculateEndPoint(int field) {
        if (field%10 == 0) {
            return 10;
        }
        return field%10;
    }
}