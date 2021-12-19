import java.util.ArrayList;

public class Scanner {
	
	int identity;
	Coordinates offset;
	ArrayList<Coordinates> coordinates_list;

	public Scanner(ArrayList<Coordinates> coordinates_list, int identity) {
		this.offset = new Coordinates(0, 0, 0);
		this.identity = identity;
		this.coordinates_list = coordinates_list;
	}

	public void rotate90DegX() {
		for (Coordinates cord : this.coordinates_list) {
			cord.rotate90DegX();
		}
	}

	public void rotate90DegY() {
		for (Coordinates cord : this.coordinates_list) {
			cord.rotate90DegY();
		}
	}

	public void rotate90DegZ() {
		for (Coordinates cord : this.coordinates_list) {
			cord.rotate90DegZ();
		}
	}

	public void updateCoordinatesWithOffset() {
		for (Coordinates cord : this.coordinates_list) {
			cord.updateWithOffset(offset);
		}
	}
}
