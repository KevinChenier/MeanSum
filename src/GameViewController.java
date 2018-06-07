import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * The view-controller class handles the display (tiles, buttons, etc.)
 * and the user input (actions from selections, clicks, etc.).
 *
 */
public class GameViewController extends JPanel {

	/**
	 * Instance of the game (logic, state, etc.)
	 */
	private GameModel gameModel;

	/**
	 * A single tile panel displays all the tiles of the game
	 */
	private TilePanel tilePanel;
	
	// All the UI components (labels, buttons, etc.)
	JButton nextButton;
	JButton reset;
	
	JLabel goal;
	JLabel somme;
	JLabel goalAchieved;
	JLabel goalNotAchieved;
	
	//The attributes that will hold the mouse position when pressed and released
	
	private int xMousePositionPressed;
	private int yMousePositionPressed;
	private int xMousePositionReleased;
	private int yMousePositionReleased;
	
	//The attributes that will retain a case identifier
	
	private int caseIdentifiant1;
	private int caseIdentifiant2;
	
	//The attributes that will retain the cases
	private Case case1;
	private Case case2;
	
	private void setupListeners() {		
		
		nextButton.addActionListener(new ActionListener() {
			/** 
			 * If nextButton has been pressed, we clear the numberList (because we will add new numbers)
			 * and we clear the cases (because we will create new cases depending on the new numberList).
			 * After these have been cleared, we generate a new game with generateGame(), we create new cases
			 * with createCases(). We then set the JLabel text of goal to see what is the new goal. The somme 
			 * JLabel is set to display that it is now 0, and goalAchieved and goalNotAchieved are erased.
			 * We make a call to reinitializeSomme() to set the somme attribute to 0. We can now repaint the tile 
			 * panel.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				GameModel.numberList.clear();
				Case.cases.clear();
				
				gameModel.generateGame();
				gameModel.createCases();
				
				goal.setText("Goal: " + gameModel.getGoal());
				somme.setText("Somme: 0");
				goalAchieved.setText("");
				goalNotAchieved.setText("");
				
				gameModel.reinitializeSomme();
		
				System.out.println("Nombres: " + gameModel.getDigits());
				
				repaint();	
			}
		});
		
		
		
		reset.addActionListener(new ActionListener() {
			/** 
			 * If reset has been pressed, we set the states of all cases to false and we
			 * set the isInGroup attribute to false. The somme 
			 * JLabel is set to display that it is now 0, and goalAchieved and goalNotAchieved are erased.
			 * We make a call to reinitializeSomme() to set the somme attribute to 0. We can now repaint the tile 
			 * panel.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i=0; i<Case.cases.size(); i++) {
					Case.cases.get(i).setState(false);
					Case.cases.get(i).setIsInGroup(false);
				}
				
				somme.setText("Somme: 0");
				goalAchieved.setText("");
				goalNotAchieved.setText("");
				
				gameModel.reinitializeSomme();
				
				repaint();
				
				
			}
		});
		
		// EXAMPLE: A mouse listener with a click event
		tilePanel.addMouseListener(new MouseAdapter() {
			/**
			 * If the mouse is pressed, we get the position of when the mouse was PRESSED only
			 * and set the x and y position to xMousePositionPressed and yMousePositionPressed
			 * attributes respectively.
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				xMousePositionPressed = (int) tilePanel.getMousePosition().getX();
				yMousePositionPressed = (int) tilePanel.getMousePosition().getY();
			}
			/**
			 * If the mouse is released, we get the position of when the mouse was RELEASED only
			 * and set the x and y position to xMousePositionReleased and yMousePositionReleased
			 * attributes respectively.
			 */
			@Override
			public void  mouseReleased(MouseEvent e) {
				
				/*
				 * If the mouse was not released on a case, then we catch the exception
				 * NullpointerException and we tell the player that he has to to release
				 * on a valid place
				 */
			
				try {
					
				xMousePositionReleased = (int) tilePanel.getMousePosition().getX();
				yMousePositionReleased = (int) tilePanel.getMousePosition().getY();
				
				} catch (Exception NullPointerException) {
					System.err.println("Vous devez relâchez la souris sur une case");
				}
				
				/*
				 * These attributes will hold the case's identifier that has been selected when the mouse
				 * was pressed and released
				 */
				caseIdentifiant1 = tilePanel.caseSelection(xMousePositionPressed, yMousePositionPressed);
				caseIdentifiant2 = tilePanel.caseSelection(xMousePositionReleased, yMousePositionReleased);
				
				/*
				 * If the mouse was not pressed or released on a case, we then tell the player
				 * that he has to release or press it on a case. It will catch ArrayOutOfBoundException
				 * because if a case identifier is not valid, it will return -1, Case.cases.get(-1) doesn't exist.
				 */
				try {
				
					/*
					 * These attributes will hold the cases that have been selected
					 */
					case1 = Case.cases.get(caseIdentifiant1);
					case2 = Case.cases.get(caseIdentifiant2);
					
					/*
					 * If the two cases that were selected is a valid selection and they were not previously
					 * selected, we will set their state to true, and we will then check if the first case selected 
					 * when the mouse was pressed is not the same case selected when the mouse was released. If this
					 * is the case, then these two cases are not the same and they now form a group.
					 */
					if((gameModel.validSelection(caseIdentifiant1, caseIdentifiant2) == true 
					&& case1.getState() == false 
					&& case2.getState() == false)){
						
						/*
						 * If case1 is the same as case2, it will only do the same method setState on the same object
						 * two times without causing any problem to the program
						 */
						
						case1.setState(true);
						case2.setState(true);
						
						if(!(case2.equals(case1))) {	
							
							case1.setIsInGroup(true);
							case2.setIsInGroup(true);
						}
					}
				
				} catch (Exception ArrayIndexOutOfBoundsException) {
					System.err.println("Vous devez sélectionner les cases et non en dehors de ceux-ci");
				}
				
				somme.setText("Somme: " + gameModel.getAndSetSomme());
				
				//If the goal is achieved, we set the JLabel goalAchieved text to tell the player that he won
				if(gameModel.checkIfGoalObtained()==true) {
					goalAchieved.setText("YOU WON");
					
					/*
					 * If the player has won without selecting all the cases, we block the current sum
					 * by putting all the cases state to true, so that the player can't lose when he already
					 * won
					 */
					
					gameModel.makeAllStatesTrue();
					
				/*
				 * If the goal has not been achieved and the player selected all the cases, then the player
				 * lost and we tell him with the JLabel goalNotAchieved
				 */
				} else if(gameModel.checkIfAllCasesSelected()==true ) {
					goalNotAchieved.setText("YOU LOST, ALL CASES WERE SELECTED");
				
					/*
					 * If the player has lost, he can't change the value of the current sum
					 * anymore
					 */
					
					gameModel.makeAllStatesTrue();
					
				}
				
				/*
				 * If the current sum is bigger than goal, then the player lost and we tell him
				 * with goalNotAchieved JLabel	
				 */
				if(gameModel.sommeBiggerThanGoal()==true) {
					goalNotAchieved.setText("YOU LOST, SUM IS BIGGER THAN GOAL");
					
					/*
					 * If the player has lost, he can't change the value of the current sum
					 * anymore
					 */
					
					gameModel.makeAllStatesTrue();
				}
				
				repaint();
			}
		});
		
	}
	
	public GameViewController() {
		// TODO Initialize our game model by constructing an instance
		gameModel = new GameModel();
		
		gameModel.generateGame();
		gameModel.createCases();
		
		System.out.println("Nombres: " + gameModel.getDigits());
		
		// The layout defines how components are displayed
		// (here, stacked along the Y axis)
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		tilePanel = new TilePanel(gameModel);
		this.add(tilePanel);
		
		// Initialize all the UI components
		goal = new JLabel("Goal: "+ gameModel.getGoal());
		this.add(goal);
		
		somme = new JLabel("Somme: 0");
		this.add(somme);
		
		nextButton = new JButton("NEXT");
		this.add(nextButton);
		
		reset = new JButton("RESET");
		this.add(reset);
		
		goalAchieved = new JLabel("");
		this.add(goalAchieved);
		
		goalNotAchieved = new JLabel("");
		this.add(goalNotAchieved);
		
		setupListeners();
	}
	
}
