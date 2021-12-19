public class Coordinates {
	
	int x;
	int y;
	int z;

	public Coordinates(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public boolean sameCoordinates(Coordinates d) {
		return this.x == d.x && this.y == d.y && this.z == d.z;
	}

	public void updateWithOffset(Coordinates offset) {
		this.x = this.x + offset.x;
		this.y = this.y + offset.y;
		this.z = this.z + offset.z;
	}

	public void rotate90DegX() {
		int old_y = this.y;
		this.y = this.z;
		this.z = -old_y;
	}

	public void rotate90DegY() {
		int old_x = this.x;
		this.x = this.z;
		this.z = -old_x;
	}

	public void rotate90DegZ() {
		int old_x = this.x;
		this.x = this.y;
		this.y = -old_x;
	}
}
