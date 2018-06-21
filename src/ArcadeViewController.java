import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * The arcade viewcontroller will inherit all the methods and constructor from
 * the game viewcontroller. The constructor will be modified to include some specific functionality
 * to the arcade mode. The restartButton will be added to the arcade mode. The listener will
 * be implemented for restartButton and modified for nextButton.
 * 
 * @author Kevin Chenier
 * @version E2018
 */

public class ArcadeViewController extends GameViewController {
	
	private void setupListenersArcade() {
		
		restartButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				gameModel.getNumberList().clear();
				gameModel.getCases().clear();
				
				gameModel.reinitializeLevel();
				
				levelLabel.setText(Constants.LEVELLABELTEXT + gameModel.getLevel());
				
				gameModel.generateGameArcade();
				gameModel.createCases();
				
				timerLabel.setText(Constants.TIMELABELTEXT + "00:00");
				chronometer.resetTimer();
				chronometer.startTimer();
				
				goalLabel.setText(Constants.GOALLABELTEXT + gameModel.getGoal());
				reinitializeJLabels();
				gameModel.reinitializeSomme();
				gameModel.unlockSum();
				
				resetJLabel.setText(Constants.RESETLABELTEXT + gameModel.getReset());	
				resetButton.setEnabled(true);
				gameModel.reinitializeResets();
				
				repaint();
			}
		});
		
		nextButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				gameModel.getNumberList().clear();
				gameModel.getCases().clear();
				
				gameModel.incrementLevel();
				
				nextButton.setEnabled(false);
				
				if(gameModel.checkIfLevelMaxReached()==true) 
					gameModel.reinitializeLevel();
				
				levelLabel.setText(Constants.LEVELLABELTEXT + gameModel.getLevel());
				
				gameModel.generateGameArcade();
				gameModel.createCases();
				
				timerLabel.setText(Constants.TIMELABELTEXT + "00:00");
				chronometer.resetTimer();
				chronometer.startTimer();
				
				goalLabel.setText(Constants.GOALLABELTEXT + gameModel.getGoal());
				reinitializeJLabels();
				gameModel.reinitializeSomme();
				gameModel.unlockSum();
				
				resetJLabel.setText(Constants.RESETLABELTEXT + gameModel.getReset());	
				resetButton.setEnabled(true);
				gameModel.reinitializeResets();
				
				System.out.println("Nombres arcade: " + gameModel.getDigits());
					
				repaint();
			}
			
		});
			
	}
	
	public ArcadeViewController() {
		super();
		
		// The game is generated in the gameModel, with the cases
		gameModel.generateGameArcade();
		gameModel.createCases();
		
		goalLabel.setText(Constants.GOALLABELTEXT + gameModel.getGoal());
				
		System.out.println("Nombres arcade: " + gameModel.getDigits());
		
		//In the arcade mode, the nextButton is disabled until the level is completed
		nextButton.setEnabled(false);
		
		restartButton = new JButton(Constants.RESTARTBUTTONTEXT);
		this.add(restartButton);
		
		//On désactive le actionListener de la classe parent pour la remplacer par le actionListener de la classe enfant
		nextButton.removeActionListener(nextButton.getActionListeners()[0]);
		
		setupListenersArcade();
	}
	
}


