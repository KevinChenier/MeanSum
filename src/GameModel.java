import java.util.Random;
import java.util.ArrayList;

/**
 * The game model handles the logic of the game (generating the numbers, etc.).
 * The instance of the model is used by the view-controller module
 * to trigger actions (for example, generate a new game) and retrieve information
 * about the current status of the game (the digits, the goal, etc.). The main method
 * in this class is for unit tests only. This class creates a static ArrayList of type
 * integer containing the numbers generated by the number generator. This class creates 
 * a random object of Random type to select random numbers.
 * Methods:
 * getRandom(int min, int max), pick a number between min and max inclusively
 * generateGame(), generates a game with random numbers between 1 and 99 inclusively and
 * puts it in numberList
 * getDigits(), returns a string containing the numbers in numberList
 * createCases(), creates objects of type Case and puts it in an ArrayList of type Case
 * setCasesDimensions(TilePanel tilePanel, int widthRectangle, int heightRectangle), set the
 * dimensions of the cases that are in the ArrayList Case
 * validSelection (int identifiantCase1, int identifiantCase2), determines if two cases 
 * that have been selected is a valid selection
 * getGoal(), method used to calculate and getting the goal of the game
 * getAndSetSomme(),method used to calculate and getting the current sum
 * reinitializeSum(), used to set the somme attribut to 0
 * checkIfGoalObtained(), when called, it checks if the goal has been obtained
 * checkIfAllCasesSelected(), when called, it checks if all the cases have been selected
 * sommeBiggerThanGoal(), when called, it checks if the player has surpassed the goal
 * blockCurrentSum(), it blocks the modification of the sum
 * main(String[]), the main in this class is used for unit tests only
 * 
 * @author Kevin Ch�nier
 * @version E2018
 */
public class GameModel {
	
	//An ArrayList of type integer that will retain all the numbers generated by generateGame()
	static ArrayList<Integer> numberList = new ArrayList<Integer>();
	
	//An object of Random type that is used in getRandom() method to randomly chose a number
	Random random = new Random();
	
	//This attribut will retain a random number between 3 and 6 inclusively
	private int groupNumber;	
	
	//This attribut is used to initiate the state of the cases
	private boolean stateSelection = false;
	
	//The attribut somme will retain the current sum that the player has formed in the game
	private int somme = 0;
	
	/** This is used to randomly get a number between min and max, passed by parameters
	 * 
	 * @author Kevin Chenier
	 * @param min, the minimal number
	 * @param max, the maximal number
	 * @exception if max is lesser than min, then
	 * 				the parameters are not valid
	 * @return the randomly choosed number between min and max
	 */
	
	private int getRandom(int min, int max) {
		
		if(min>max)
			throw new IllegalArgumentException("Max should be greater than min");
		
		return random.nextInt((max - min) + 1) + min;
	}
	
	/** This method will generate a game by choosing random numbers and
	 * putting them in numberList. The total numbers selected will be 
	 * equal to groupNumber. Each number has a 70% chance to be between
	 * 1 and 9 inclusively, and a 30% chance to be between 10 and 99 inclusively
	 * 
	 * @author Kevin Chenier
	 */
	
	public void generateGame() {
		
		//groupNumber will randomly retain a number between 3 and 6 inclusively
		groupNumber = getRandom(3,6);
		
		//There will be groupNumber numbers in numberList
		for(int i=0; i<groupNumber; i++) {
			
			/*
			* 70% chances that the number will be between 1 and 9 inclusively, 30% chances to be 
			* between 10 and 99 inclusively
			*/			
			if(Math.random()<=0.7) 
				numberList.add(getRandom(1,9));
			else
				numberList.add(getRandom(10,99));
		}
	}
	
	/** This method returns a string containing each digit that are in 
	 * numberList
	 * 
	 * @author Kevin Chenier
	 * @return listCaracters, the string that contains the digits
	 */
	
	public String getDigits() {
		
		String listCaracters= "";
		
		//Gets each numbers that are in numberList and put it in listCaracters variable 
		for(int i=0; i<numberList.size(); i++)
			listCaracters = listCaracters + numberList.get(i);
		
		return listCaracters;
	}
	
	/** This method creates objects of type Case and puts them in the arraylist cases
	 * each case contains only one digit, each cases is set to false for their state of 
	 * selection, each cases contains an identifier.
	 * 
	 * @author Kevin Chenier
	 */
	
	public void createCases() {
	
		for(int i=0; i<getDigits().length(); i++)
			Case.cases.add(new Case(Integer.parseInt(this.getDigits().substring(i, i+1)), this.stateSelection, i));
	}
	
	/** This method creates objects of type Case and puts them in the arraylist cases
	 * each case contains only one digit, each cases is set to false for their state of 
	 * selection, each cases contains an identifier.
	 * 
	 * @author Kevin Chenier
	 */
	
	public void setCasesDimensions(TilePanel tilePanel, int widthRectangle, int heightRectangle) {
			
		//Mathematically, the cases dimensions are set to be dynamic with window size
		for(int i=0; i<this.getDigits().length(); i++) {
			Case.cases.get(i).setX0(i*widthRectangle);
			Case.cases.get(i).setY0(0);
			Case.cases.get(i).setX1(widthRectangle+i*widthRectangle);
			Case.cases.get(i).setY1(heightRectangle);
		}
	}
	
	/** This method compares two cases identifier and determines if the player
	 * made a valid selection when pressing and releasing the mouse
	 * 
	 * @author Kevin Chenier
	 * @return true or false, it returns true if selection is valid, false if not valid
	 */
	
	public boolean validSelection (int identifiantCase1, int identifiantCase2) {
		
		/*
		 * The selection is valid if cases identifiers difference is equal to 1
		 * or if it is the same case that was selected when pressing and releasing the mouse.
		 * The identifiers have to not be -1 too.
		 */
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
	
	/** This method compares two cases identifier and determines if the player
	 * made a valid selection when pressing and releasing the mouse
	 * 
	 * @author Kevin Chenier
	 * @return true or false, it returns true if selection is valid, false if not valid
	 */
	
	public int getGoal() {
		
		int goal = 0;
		
		for(int i=0; i<numberList.size(); i++)
			goal += numberList.get(i);
		return goal;
	}
	
	/** This method calculate the current sum that the player formed at a particular time
	 * 
	 * @author Kevin Chenier
	 */
	
	public void setSomme() {
		
		//Two variables that will retain the cases selected in the for loop
		Case case1;
		Case case2;
		
		//A variable that will retain the new number formed if two cases were valid to be a group
		int newNumber;
		
		for(int i=0; i<getDigits().length(); i++) {
			
			//We give to variable case1 the value of the object at Case.cases.get(i)
			case1=Case.cases.get(i);
			
			/*
			 * If the first case's state is true(has been selected) and has not yet been calculated in the 
			 * current sum, then we check if the first case is in a group. If it is in a group, then we 
			 * attribute to case2 the case that comes right after case1. We then form a new number with these 
			 * two cases and add it to the current sum. If the first case is NOT in a group
			 * then we only get its number with the method getNumber() and add it to the current sum
			 */
			if (case1.getState()==true && case1.getDidCalculateSomme() == false) {
				if(case1.isInGroup()==true) {
					case2 = Case.cases.get(i+1);
					
					/*
					 * We select the two strings that represent the number that is in the two cases, then we
					 * put them into a string that will be transformed into a int number with the function
					 * Integer.parseInt.
					 */
					newNumber=Integer.parseInt(case1.toString()+case2.toString());
					
					somme+=newNumber;
					
					/*
					 * The two cases were used to calculate the current sum, so we can change its didCalculateSum
					 * attribute to true
					 */
					case1.setDidCalculateSomme(true);
					case2.setDidCalculateSomme(true);
					
					/*
					 * We skip an iteration because we are no longer interested with the case that was in a
					 * group with Case.cases.get(i)
					 */
					i++;
				} else {
					somme+=case1.getNumber();
					
					//We set its didCalculateSum attribute to true 
					case1.setDidCalculateSomme(true);
				}
			}	
		}
	}
	
	/** This method will return the value of somme
	 * 
	 * @return somme, the current sum
	 * @author KevinCh�nier
	 */
	
	public int getSomme() {
		return this.somme;
	}
	
	/** This method is used to reinitialize the current sum to 0 when it is necessary to
	 * do so. This method also logically set all the cases attribute didCalculateSum to false.
	 * 
	 * @author KevinCh�nier
	 */
	public void reinitializeSomme() {
		
		somme = 0 ;
		
		for(int i = 0; i<getDigits().length(); i++) 
			Case.cases.get(i).setDidCalculateSomme(false);
		
	}
	
	/** This method compares the current sum to the goal. If the goal is reached, then
	 * the method returns true.
	 * 
	 * @author Kevin Chenier
	 * @return true or false, it returns true the goal has been achieved, or false if it
	 * has not been achieved
	 */
	
	public boolean checkIfGoalObtained() {
		if(somme==getGoal()) 
			return true;
		else
			return false;
	}
	
	/** This method checks if all the cases have been selected.
	 * 
	 * @author Kevin Chenier
	 * @return verdict, it will be true if all the cases have been selected
	 */
	
	public boolean checkIfAllCasesSelected() {

		//case1 will retain Case.cases.get(i) in for loop
		Case case1;
		
		//Verdict will retain the information about the result
		boolean verdict = false;
		
		int i = 0;
	
		/*
		 * If all cases are selected, then the verdict will remain true, else
		 * the verdict will be false and break the loop that goes until all cases
		 * were checked.
		 */
		
		while(i<getDigits().length()) {
			case1=Case.cases.get(i);
			
			if(case1.getState()==true)
				verdict = true;
			else {
				verdict = false;
				break;
			}
			i++;
		}
		return verdict;
	}
	
	/** This method will check if the current sum is bigger than goal
	 * 
	 * @author Kevin Chenier
	 * @return true or false, it returns true if the sum is bigger than goal
	 */
	
	public boolean sommeBiggerThanGoal() {
		if(somme>getGoal())
			return true;
		return false;
	}
	
	/** This method will block the state of the current sum
	 * by setting all the cases state to true and by setting its attributes
	 * didCalculateSum to true.
	 * 
	 * @author Kevin Chenier
	 */
	
	public void blockCurrentSum() {
		for(int i=0; i<getDigits().length(); i++) {
			Case.cases.get(i).setState(true);
			Case.cases.get(i).setDidCalculateSomme(true);
		}
	}
	
	/**
	 * UNIT TEST, DECOMMENT TO TEST generateGame() 50 TIMES!
	 */
	
	/*
	public static void main(String[] args) {
		
		GameModel test = new GameModel();
		
		for(int i=0; i<50; i++) {
			test.generateGame();
			System.out.println("Liste" + i +": " + GameModel.numberList);
			System.out.println("Cha�ne de caract�res contenant les nombres g�n�r�s: " + test.getDigits());
			GameModel.numberList.clear();
		}
	}
	
	*/
}
