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
	
	// TODO Add all the other required UI components (labels, buttons, etc.)
	JButton nextButton;
	JButton reset;
	JButton start;
	
	JLabel goal;
	
	private int xMousePositionPressed;
	private int yMousePositionPressed;
	private int xMousePositionReleased;
	private int yMousePositionReleased;
	
	private int caseIdentifiant1;
	private int caseIdentifiant2;
	private Case case1;
	private Case case2;
	
	private void setupListeners() {		
		// TODO Set up the required listeners on the UI components (button clicks, etc.)
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameModel.generateGame();
				gameModel.createCases();
				
				goal.setText("Goal: " + gameModel.getGoal());
				
				System.out.println(gameModel.getDigits());
				
				repaint();
				
				remove(start);
			}
		});
		
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameModel.numberList.clear();
				Case.cases.clear();
				
				gameModel.generateGame();
				gameModel.createCases();
				
				goal.setText("Goal: " + gameModel.getGoal());
				
				System.out.println(gameModel.getDigits());
				
				repaint();	
			}
		});
		
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i=0; i<Case.cases.size(); i++) {
					Case.cases.get(i).setState(false);
					Case.cases.get(i).setIsInGroup(false);
				}
				
				repaint();
				
				
			}
		});
		
		// EXAMPLE: A mouse listener with a click event
		tilePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xMousePositionPressed = (int) tilePanel.getMousePosition().getX();
				yMousePositionPressed = (int) tilePanel.getMousePosition().getY();
			}
			
			@Override
			public void  mouseReleased(MouseEvent e) {
				xMousePositionReleased = (int) tilePanel.getMousePosition().getX();
				yMousePositionReleased = (int) tilePanel.getMousePosition().getY();
				
				caseIdentifiant1 = tilePanel.caseSelection(xMousePositionPressed, yMousePositionPressed);
				caseIdentifiant2 = tilePanel.caseSelection(xMousePositionReleased, yMousePositionReleased);
				
				case1 = Case.cases.get(caseIdentifiant1);
				case2 = Case.cases.get(caseIdentifiant2);
				
				if((gameModel.validSelection(caseIdentifiant1, caseIdentifiant2) == true 
				&& case1.getState() == false 
				&& case2.getState() == false)){
					
					case1.setState(true);
					case2.setState(true);
					
					if(!(case2.equals(case1))) {	
						
						case1.setIsInGroup(true);
						case2.setIsInGroup(true);
					}
				}
				repaint();
			}
		});
		
	}
	
	public GameViewController() {
		// TODO Initialize our game model by constructing an instance
		gameModel = new GameModel();
		
		// The layout defines how components are displayed
		// (here, stacked along the Y axis)
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		tilePanel = new TilePanel(gameModel);
		this.add(tilePanel);
		
		// TODO Initialize all the UI components
		goal = new JLabel("Goal: ");
		this.add(goal);
		
		start = new JButton("START");
		this.add(start);
		
		nextButton = new JButton("NEXT");
		this.add(nextButton);
		
		reset = new JButton("RESET");
		this.add(reset);
		
		setupListeners();
	}
	
}
