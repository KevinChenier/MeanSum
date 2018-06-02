import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The tile panel displays all the tiles (one per digit) of the game.
 *
 */
public class TilePanel extends JPanel {

	/**
	 * The tile panel object holds a reference to the game model to
	 * request information to display (view) and to modify its state (controller)
	 */
	private GameModel gameModelHandle;

	/**
	 * A table of colours that can be used to draw the tiles
	 */
	private Color[] colours;
	
	/**
	 * Initialize an array of pre-set colours.
	 * The colours are picked to ensure readability and avoid confusion.
	 */
	private void initializeColours() {
		// Some tile colours in the '0xRRGGBB' format
		String[] tileColourCodes = new String[] {
				"0x89CFF0", "0xF4C2C2", "0xFFBF00", "0xFBCEB1",
    			"0x6495ED", "0x9BDDFF", "0xFBEC5D",	"0xFF7F50",
    			"0x00FFFF", "0x98777B", "0x99BADD", "0x654321"
    			};
		
		// Allocate and fill our colour array with the colour codes
		colours = new Color[tileColourCodes.length];
		for (int i = 0; i < colours.length; ++i)
			colours[i] = Color.decode(tileColourCodes[i]);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int largeurRectangle = 0;
		int centreRectangle;
		
		Font numberFont = new Font ("Arial Bold", Font.ROMAN_BASELINE, 80);
		g.setFont(numberFont);
		
		if(gameModelHandle.getDigits().length()!=0) 
			largeurRectangle = this.getWidth()/gameModelHandle.getDigits().length();
	
		// TODO Seek current game information from the model and draw the tiles accordingly
		
		System.out.println(gameModelHandle.getDigits());
		
		for(int i=0; i<gameModelHandle.getDigits().length(); i++) {
			
			centreRectangle = i*largeurRectangle + largeurRectangle/2;
			g.setColor(colours[i]);
			g.fillRoundRect(i*largeurRectangle, 0, largeurRectangle, 128, 30, 40);
			g.setColor(Color.BLACK);
			g.drawString(gameModelHandle.getDigits().substring(i, i+1),centreRectangle-20 , 96);
		}
		
	}
	
	public TilePanel(GameModel gameModel) {
		if (gameModel == null)
			throw new IllegalArgumentException("Should provide a valid instance of GameModel!");
		gameModelHandle = gameModel;
		
		// Initialize our array of tile colours
		initializeColours();
	}
	
}
