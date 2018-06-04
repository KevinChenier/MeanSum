import java.util.ArrayList;

/**
 * This class is used to control the attributes in the cases
 * @author Kevin Chenier
 *
 */
public class Case {
	
	private int number;
	private boolean state;
	
	private int X0;
	private int X1;
	private int Y0;
	private int Y1;
	
	
	static public ArrayList<Case> cases = new ArrayList<Case>();
	
	public Case(int number, boolean state) {
		this.number = number;
		this.state = state;
		
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public void setX0(int X0) {
		this.X0=X0;
	}
	
	public void setX1(int X1) {
		this.X1=X1;
	}
	
	public void setY0(int Y0) {
		this.Y0=Y0;
	}
	
	public void setY1(int Y1) {
		this.Y1=Y1;
	}
	
	public int getX0() {
		return this.X0;
	}
	
	public int getX1() {
		return this.X1;
	}
	
	public int getY0() {
		return this.Y0;
	}
	
	public int getY1() {
		return this.Y1;
	}
	

}
