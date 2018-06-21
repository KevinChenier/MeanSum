/**
 * The training viewcontroller will inherit all the methods and constructor from
 * the game viewcontroller. The constructor will be modified to include some specific functionality
 * to the training mode
 * 
 * @author Kevin Chenier
 * @version E2018
 */

public class TrainingViewController extends GameViewController {
	
	public TrainingViewController() {
		super();
		
		// The game is generated in the gameModel, with the cases
		gameModel.generateGameTraining();
		gameModel.createCases();
		
		//The levelLabel is removed because it is not use in training mode
		displayBar.remove(levelLabel);
		
		goalLabel.setText(Constants.GOALLABELTEXT + gameModel.getGoal());
				
		System.out.println("Nombres training: " + gameModel.getDigits());
	}
	
}