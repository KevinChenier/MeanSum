import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;

/**
 * The game model handles the logic of the game (generating the numbers, etc.).
 * The instance of the model is used by the view-controller module
 * to trigger actions (for example, generate a new game) and retrieve information
 * about the current status of the game (the digits, the goal, etc.).
 *
 */
public class GameModel {
	// TODO Add attributes (list of numbers, etc.)
	
	static ArrayList<Integer> numberList = new ArrayList<Integer>();
	
	Random random = new Random();
	
	private int groupNumber;
	private boolean stateSelection = false;
	
	// TODO Implement constructor and methods (generation of a game, etc.)
	
	private int getRandom(int min, int max) {
		
		if(min>max)
			throw new IllegalArgumentException("Max should be greater than min");
		
		return random.nextInt((max - min) + 1) + min;
	}
	
	public void generateGame() {
		
		groupNumber = getRandom(3,6);
		
		for(int i=0; i<groupNumber; i++) {
		
			if(Math.random()<=0.7) 
				numberList.add(getRandom(1,9));
			else
				numberList.add(getRandom(10,99));
		}
	}
	
	public String getDigits() {
		
		String listCaracters= "";
		
		for(int i=0; i<numberList.size(); i++)
			listCaracters = listCaracters + numberList.get(i);
		
		return listCaracters;
	}
	
	public void createCases() {
		
		int digitsLength = this.getDigits().length();
		
		for(int i=0; i<digitsLength; i++)
			Case.cases.add(new Case(Integer.parseInt(this.getDigits().substring(i, i+1)), this.stateSelection, i));
	}
	
	public void setCasesDimensions(TilePanel tilePanel, int largeurRectangle, int hauteurRectangle) {
			
		for(int i=0; i<this.getDigits().length(); i++) {
			Case.cases.get(i).setX0(i*largeurRectangle);
			Case.cases.get(i).setY0(0);
			Case.cases.get(i).setX1(largeurRectangle+i*largeurRectangle);
			Case.cases.get(i).setY1(hauteurRectangle);
		}
	}
	
	public boolean validSelection (int identifiantCase1, int identifiantCase2) {
		
		if(
		(identifiantCase1+1 == identifiantCase2 
		|| identifiantCase1-1 == identifiantCase2 
		|| identifiantCase1 == identifiantCase2)
		&&
		(identifiantCase1!=-1 
		&& identifiantCase2!=-1)
		) 
			return true;
		else
			return false;
	}
	
	public int getGoal() {
		
		int somme = 0;
		
		for(int i=0; i<numberList.size(); i++)
			somme += numberList.get(i);
		return somme;
	}
	
	public static void main(String[] args) {
		
		GameModel test = new GameModel();
		
		for(int i=0; i<50; i++) {
			
			test.generateGame();
						
			System.out.println("Liste" + i +": " + GameModel.numberList);
			
			System.out.println("Chaîne de caractères contenant les nombres générés: " + test.getDigits());
			
			GameModel.numberList.clear();
		}
	}
}
