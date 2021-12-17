public class Probe {

	int x_pos;
	int y_pos;
	int original_x_speed;
	int x_speed;
	int y_speed;

	//a Probe is constructed with an x speed in mind since these are easier to calculate
	public Probe(int x_speed) {
		this.x_pos = 0;
		this.y_pos = 0;
		this.original_x_speed =x_speed;
		this.x_speed = x_speed;
		this.y_speed = 0;
	}
	
	//updates the coordinates of the probe and reduces its speeds
	public void makeStep() {
		this.x_pos = this.x_pos + this.x_speed;
		this.y_pos = this.y_pos + this.y_speed;
		this.y_speed = this.y_speed-1;
		if (this.x_speed>0)
			this.x_speed = x_speed-1;
	}

	//resets the Probe to its starting position and resets its x speed
	public void resetProbe(){
		this.x_pos = 0;
		this.y_pos = 0;
		this.x_speed = this.original_x_speed;
		this.y_speed = 0;
	}
}
