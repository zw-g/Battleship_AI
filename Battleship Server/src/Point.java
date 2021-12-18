// Point
// This is a class to store X/Y Coordinates for the game.
//
// Vic Berry
// November 1, 2021
//

public class Point {
	
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	public int getRow() {
		return x;
	}
	public void setRow(int x) {
		this.x = x;
	}
	public int getCol() {
		return y;
	}
	public void setCol(int y) {
		this.y = y;
	}
	

}
