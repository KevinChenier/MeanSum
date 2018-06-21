import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory; 
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * The view-controller class handles the display (tiles, buttons, etc.)
 * and the user input (actions from selections, clicks, etc.).
 * Methods:
 * setupListeners(), will initialize the components
 * goalObtainedOrNotObtained, check if the goal has been obtained, 
 * if the sum has surpassed the goal or if all the cases have been selected
 * sommeUpdate(): this method will reupdate the value of somme
 * reinitializeJLabels(), will reinitialize the JLabels
 * 
 *@author Kevin Chenier
 *@version E2018
 */
public class GameViewController extends JPanel {

	/**
	 * Instance of the game (logic, state, etc.)
	 */
	protected GameModel gameModel;

	/**
	 * A single tile panel displays all the tiles of the game
	 */
	private TilePanel tilePanel;
	
	/**
	 * A display bar that will contain all the JLabels
	 */
	
	protected JPanel displayBar;
	
	// All the UI components (labels, buttons, etc.)
	protected JButton nextButton, resetButton, restartButton;
	protected JLabel goalLabel, sommeLabel, goalAchievedLabel, goalNotAchievedLabel, resetJLabel, timerLabel, levelLabel;
	
	//The attributes that will hold the mouse position when pressed and released
	private int xMousePositionPressed, yMousePositionPressed, xMousePositionReleased, yMousePositionReleased;
	
	//A timer is created
	protected Chronometer chronometer = new Chronometer();
	
	private void setupListeners() {		
		
		nextButton.addActionListener(new ActionListener() {
			/** 
			 * If nextButton has been pressed, we clear the numberList (because we will add new numbers)
			 * and we clear the cases (because we will create new cases depending on the new numberList).
			 * After these have been cleared, we generate a new game with generateGame(), we create new cases
			 * with createCases(). We then set the JLabel text of goal to see what is the new goal.We reactivate
			 * the resetButton if it is not already enables. The somme JLabel is set to display that it is now 0,
			 * and goalAchieved and goalNotAchieved are erased. We make a call to reinitializeSomme() to set 
			 * the somme attribute to 0. We can now repaint the tile panel.
			 */
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				gameModel.getNumberList().clear();
				gameModel.getCases().clear();
				
				gameModel.generateGameTraining();
				gameModel.createCases();
				
				goalLabel.setText(Constants.GOALLABELTEXT + gameModel.getGoal());
				reinitializeJLabels();
				gameModel.reinitializeSomme();
				gameModel.unlockSum();
				
				timerLabel.setText(Constants.TIMELABELTEXT + "00:00");
				chronometer.resetTimer();
				chronometer.startTimer();
				
				resetJLabel.setText(Constants.RESETLABELTEXT + gameModel.getReset());
				resetButton.setEnabled(true);
				gameModel.reinitializeResets();
			
				System.out.println("Nombres training: " + gameModel.getDigits());
				
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
				gameModel.unlockSum();
				
				resetJLabel.setText(Constants.RESETLABELTEXT + gameModel.getReset());
				gameModel.incrementResets();
				
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
				
				gameModel.createGroup(gameModel.caseSelection(xMousePositionPressed, yMousePositionPressed), 
							gameModel.caseSelection(xMousePositionReleased, yMousePositionReleased));
				
				sommeUpdate();
				
				goalObtainedOrNotObtained();
				
				repaint();
			}
		});
		
	}

	private void goalObtainedOrNotObtained() {
		//If the goal is achieved, we set the JLabel goalAchieved text to tell the player that he won
		if(gameModel.checkIfGoalObtained()==true) {
			goalAchievedLabel.setText("YOU WON");
			
			/*
			 * If the player has won without selecting all the cases, we block the current sum
			 * by putting all the cases state and didCalculateSum to true, so that the player 
			 * can't lose when he already won, and we disable resetButton. If we are in arcade
			 * mode, we activate the nextButton
			 */
			gameModel.blockSum();
			resetButton.setEnabled(false);
			nextButton.setEnabled(true);
			chronometer.stopTimer();
			
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
			
			gameModel.blockSum();
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
			
			gameModel.blockSum();
		}
	}
	
	private void sommeUpdate() {
		gameModel.setSomme();
		sommeLabel.setText(Constants.SOMMELABELTEXT + gameModel.getSomme());
	}
	
	protected void reinitializeJLabels() {
		sommeLabel.setText(Constants.SOMMELABELTEXT + "0");
		goalAchievedLabel.setText("");
		goalNotAchievedLabel.setText("");
	}
	
	public GameViewController() {
		// Initialize our game model by constructing an instance
		gameModel = new GameModel();
		
		// The layout defines how components are displayed
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		tilePanel = new TilePanel(gameModel);
		this.add(tilePanel);
		
		displayBar = new JPanel();
		this.add(displayBar);
		displayBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, Constants.DISPLAYBAR_THICKNESS));
		displayBar.setSize(new Dimension (1,1));
		
		// Initialize all the UI components
		goalLabel = new JLabel(Constants.GOALLABELTEXT);
		displayBar.add(goalLabel);
		
		sommeLabel = new JLabel(Constants.SOMMELABELTEXT + "0");
		displayBar.add(sommeLabel);
		
		resetJLabel = new JLabel(Constants.RESETLABELTEXT + "0");
		displayBar.add(resetJLabel);
		
		timerLabel = chronometer.getTimeLabel();
		displayBar.add(timerLabel);
		
		levelLabel = new JLabel(Constants.LEVELLABELTEXT + gameModel.getLevel());
		displayBar.add(levelLabel);
		
		goalAchievedLabel = new JLabel("");
		displayBar.add(goalAchievedLabel);
		
		goalNotAchievedLabel = new JLabel("");
		displayBar.add(goalNotAchievedLabel);
		
		nextButton = new JButton(Constants.NEXTBUTTONTEXT);
		this.add(nextButton);
		
		resetButton = new JButton(Constants.RESETBUTTONTEXT);
		this.add(resetButton);

		setupListeners();
	}
	
}
