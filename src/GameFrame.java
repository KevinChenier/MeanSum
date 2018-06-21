import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 * The game frame is the main window of the game. It instantiates the model
 * and the view-controller. The frame is filled by a single panel containing
 * all the elements, which is the {@link TrainingViewController} object.
 *
 */
public class GameFrame extends JFrame {
	
	/**
	 * Handle both the graphical interface (tiles, labels, buttons, etc.)
	 * and the user input (mouse events, button clicks, etc.)
	 */
	private TrainingViewController trainingViewController;
	private ArcadeViewController arcadeViewController;
	
	/**
	 * Initialize the main properties of the game window
	 */
	public void initUI() {
		setTitle("Mean Sum");
		setSize(800, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * The constructor instantiates the model and view-controller
	 * and builds the game window.
	 */
	public GameFrame() {
		// Initialize the interface
		initUI();
		
		// Initialize the views and set it as the main components for our tabs
		trainingViewController = new TrainingViewController();
		arcadeViewController = new ArcadeViewController();
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("TRAINING", trainingViewController);
		tabbedPane.add("ARCADE", arcadeViewController);
	
		setContentPane(tabbedPane);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameFrame game = new GameFrame();
				game.setVisible(true);
			}
		});
	}

}
