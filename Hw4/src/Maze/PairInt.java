package Maze;

public class PairInt {

	//Data Fields
	private int x;
	private int y;

	//methods
	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(Object p) {
		PairInt p1 = (PairInt) p;
		if(p1.getX() == this.x && p1.getY()== this.y) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + x + ", " + y + ")");
		return sb.toString();
	}
	
	public PairInt copy() {
		return new PairInt(this.x, this.y);
	}
	
	public static void main(String[] args) {
		PairInt np = new PairInt(3, 2);
		System.out.println(np);
	}
}
