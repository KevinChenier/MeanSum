import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ArcadeViewController extends JPanel {
	
	/**
	 * Instance of the game (logic, state, etc.)
	 */
	private GameModel gameModel;
	
	private Case cases;
	
	/**
	 * A single tile panel displays all the tiles of the game
	 */
	private TilePanel tilePanel;
	
	// All the UI components (labels, buttons, etc.)
	private JButton nextButton, resetButton;
	private JLabel goalLabel, sommeLabel, goalAchievedLabel, goalNotAchievedLabel;
	
	//The attributes that will hold the mouse position when pressed and released
	private int xMousePositionPressed, yMousePositionPressed, xMousePositionReleased, yMousePositionReleased;
	
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
				gameModel.getNumberList().clear();
				gameModel.getCases().clear();
				
				gameModel.generateGame();
				gameModel.createCases();
				
				goalLabel.setText("Goal: " + gameModel.getGoal());
				
				reinitializeJLabels();
				
				gameModel.reinitializeSomme();
		
				System.out.println("Nombres: " + gameModel.getDigits());
				
				repaint();	
			}
		});
		
		
		
		resetButton.addActionListener(new ActionListener() {
			/** 
			 * If reset has been pressed, we set the states of all cases to false and we
			 * set the isInGroup attribute to false. The somme 
			 * JLabel is set to display that it is now 0, and goalAchieved and goalNotAchieved are erased.
			 * We make a call to reinitializeSomme() to set the somme attribute to 0. We can now repaint the tile 
			 * panel.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i=0; i<gameModel.getCases().size(); i++) {
					gameModel.getCases().get(i).setState(false);
					gameModel.getCases().get(i).setIsInGroup(false);
				}
				
				reinitializeJLabels();
				
				gameModel.reinitializeSomme();
				
				repaint();
				
				
			}
		});
		
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
				
				createGroup(gameModel.caseSelection(xMousePositionPressed, yMousePositionPressed), 
							gameModel.caseSelection(xMousePositionReleased, yMousePositionReleased));
				
				sommeUpdate();
				
				goalObtainedOrNotObtained();
				
				repaint();
			}
		});
		
	}
	
	/**************************************************************************************/
	/***************NOUVELLES FONCTIONS, COMMENTER ET DÉCIDER OU LES METTRE!!!!!!!*********/
	
	private void goalObtainedOrNotObtained() {
		//If the goal is achieved, we set the JLabel goalAchieved text to tell the player that he won
		if(gameModel.checkIfGoalObtained()==true) {
			goalAchievedLabel.setText("YOU WON");
			
			/*
			 * If the player has won without selecting all the cases, we block the current sum
			 * by putting all the cases state and didCalculateSum to true, so that the player 
			 * can't lose when he already won
			 */
			
			gameModel.blockCurrentSum();
			
		/*
		 * If the goal has not been achieved and the player selected all the cases, then the player
		 * lost and we tell him with the JLabel goalNotAchieved
		 */
		} else if(gameModel.checkIfAllCasesSelected()==true ) {
			goalNotAchievedLabel.setText("YOU LOST, ALL CASES WERE SELECTED");
		
			/*
			 * If the player has lost, he can't change the value of the current sum
			 * anymore
			 */
			
			gameModel.blockCurrentSum();
			
		}
		
		/*
		 * If the current sum is bigger than goal, then the player lost and we tell him
		 * with goalNotAchieved JLabel	
		 */
		if(gameModel.sommeBiggerThanGoal()==true) {
			goalNotAchievedLabel.setText("YOU LOST, SUM IS BIGGER THAN GOAL");
			
			/*
			 * If the player has lost, he can't change the value of the current sum
			 * anymore
			 */
			
			gameModel.blockCurrentSum();
		}
	}
	
	
	
	private void sommeUpdate() {
		gameModel.setSomme();
		sommeLabel.setText("Somme: " + gameModel.getSomme());
	}
	
	private void createGroup(int caseIdentifiant1, int caseIdentifiant2) {
		/*
		 * If the mouse was not pressed or released on a case, we then tell the player
		 * that he has to release or press it on a case. It will catch ArrayOutOfBoundException
		 * because if a case identifier is not valid, it will return -1, Case.cases.get(-1) doesn't exist.
		 */
		try {
		
			/*
			 * These attributes will hold the cases that have been selected
			 */
			Case case1 =gameModel.getCases().get(caseIdentifiant1);
			Case case2 = gameModel.getCases().get(caseIdentifiant2);
			
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
	}
	
	private void reinitializeJLabels() {
		sommeLabel.setText("Somme: 0");
		goalAchievedLabel.setText("");
		goalNotAchievedLabel.setText("");
	}
	/**********************************************************************************************/
	
	
	public ArcadeViewController() {
		// Initialize our game model by constructing an instance
		gameModel = new GameModel();
		
		// The game is generated in the gameModel, with the cases
		gameModel.generateGame();
		gameModel.createCases();
		
		System.out.println("Nombres: " + gameModel.getDigits());
		
		// The layout defines how components are displayed
		// (here, stacked along the Y axis)
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		tilePanel = new TilePanel(gameModel);
		this.add(tilePanel);
		
		// Initialize all the UI components
		goalLabel = new JLabel("Goal: "+ gameModel.getGoal());
		this.add(goalLabel);
		
		sommeLabel = new JLabel("Somme: 0");
		this.add(sommeLabel);
		
		nextButton = new JButton("NEXT");
		this.add(nextButton);
		
		resetButton = new JButton("RESET");
		this.add(resetButton);
		
		goalAchievedLabel = new JLabel("");
		this.add(goalAchievedLabel);
		
		goalNotAchievedLabel = new JLabel("");
		this.add(goalNotAchievedLabel);
		
		setupListeners();
	}
	
}


