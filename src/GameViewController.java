import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;

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
	
	private void setupListeners() {		
		// TODO Set up the required listeners on the UI components (button clicks, etc.)
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameModel.numberList.clear();
				gameModel.generateGame();
				repaint();
				
			}
		});
		
		// EXAMPLE: A mouse listener with a click event
		tilePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("PRESS");
			}
			
			@Override
			public void  mouseReleased(MouseEvent e) {
				System.out.println("RELEASED");
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
		nextButton = new JButton("NEXT");
		this.add(nextButton);
		
		setupListeners();
	}
	
}
