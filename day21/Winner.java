public class Winner {
    long w1;
    long w2;

    public Winner(long w1, long w2) {
        this.w1 = w1;
        this.w2 = w2;
    }

    public void addWinner(Winner winner, int possibilities) {
        this.w1 += winner.w1*possibilities;
        this.w2 += winner.w2*possibilities;
    }

    public void addW1(long w1) {
        this.w1 += w1;
    }

    public void addW2(long w2) {
        this.w2 += w2;
    }
}