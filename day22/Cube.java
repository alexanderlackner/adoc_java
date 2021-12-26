public class Cube {
    int low_x;
    int high_x;
    int low_y;
    int high_y;
    int low_z;
    int high_z;

    boolean on;

    public Cube (int low_x, int high_x, int low_y, int high_y, int low_z, int high_z, boolean on) {
        this.low_x = low_x;
        this.high_x = high_x;
        this.low_y = low_y;
        this.high_y = high_y;
        this.low_z = low_z;
        this.high_z = high_z;
        this.on = on;
    }

    public boolean cubesOverlap(Cube c) {
        if (this.low_x<=c.high_x && this.high_x>=c.low_x)
            if (this.low_y<=c.high_y && this.high_y>=c.low_y)
                if (this.low_z<=c.high_z && this.high_z>=c.low_z)
                    return true;

        return false;
    }

    public boolean isCompletlyInCube(Cube c) {
        if ((this.low_x<=c.low_x) && (this.high_x>=c.high_x))
            if ((this.low_y<=c.low_y) && (this.high_y>=c.high_y))
                if ((this.low_z<=c.low_z) && (this.high_z>=c.high_z))
                    return true;

        return false;
    }
}