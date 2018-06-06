import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The tile panel displays all the tiles (one per digit) of the game.
 *
 */
public class TilePanel extends JPanel {
	
	private int largeurRectangle;
	private int centreRectangle;

	/**
	 * The tile panel object holds a reference to the game model to
	 * request information to display (view) and to modify its state (controller)
	 */
	private GameModel gameModelHandle;

	/**
	 * A table of colours that can be used to draw the tiles
	 */
	private Color[] colours;
	
	Font numberFont = new Font ("Arial Bold", Font.ROMAN_BASELINE, 80);
	
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
		
		//A ENLEVER LORSQUE gameModelHandle SERA INTSANCIÉ AU DÉBUT
		largeurRectangle = 0;

		g.setFont(numberFont);
		
		//A ENLEVER LORSQUE gameModelHandle SERA INTSANCIÉ AU DÉBUT
		if(gameModelHandle.getDigits().length()!=0) 
			largeurRectangle = this.getWidth()/gameModelHandle.getDigits().length();
	
		// TODO Seek current game information from the model and draw the tiles accordingly
		
		for(int i=0; i<gameModelHandle.getDigits().length(); i++) {
			
			if(Case.cases.get(i).getState()==false) {
				
				paintRoundRectangle (g, Color.WHITE, largeurRectangle, this.getHeight()/2+20, i);
				centreRectangle = i*largeurRectangle + largeurRectangle/2;
				drawStringOnCases (g, Color.BLACK, gameModelHandle.getDigits().substring(i, i+1), centreRectangle-20, this.getHeight()/4+30);
			
			}
			
			else if(Case.cases.get(i).isInGroup()==true) {
				
				paintRoundRectangle (g, colours[i], largeurRectangle, this.getHeight()/2+20, i);
				centreRectangle = i*largeurRectangle + largeurRectangle/2;
				drawStringOnCases (g, Color.BLACK, gameModelHandle.getDigits().substring(i, i+1), centreRectangle-20, this.getHeight()/4+30);
				
				i++;
				
				paintRoundRectangle (g, colours[i-1], largeurRectangle, this.getHeight()/2+20, i);
				centreRectangle = i*largeurRectangle + largeurRectangle/2;
				drawStringOnCases (g, Color.BLACK, gameModelHandle.getDigits().substring(i, i+1), centreRectangle-20, this.getHeight()/4+30);
				
			}
			
			else {
				paintRoundRectangle (g, colours[i], largeurRectangle, this.getHeight()/2+20, i);
				centreRectangle = i*largeurRectangle + largeurRectangle/2;
				drawStringOnCases (g, Color.BLACK, gameModelHandle.getDigits().substring(i, i+1), centreRectangle-20, this.getHeight()/4+30);
			}
		}
		//Call to the method for setting the dimensions of the cases
		gameModelHandle.setCasesDimensions(this,largeurRectangle,this.getHeight()/2+20);
		
	}
	
	public int caseSelection (int positionX, int positionY) {
		
		int identifiant = 0;

		for(int i=0; i<gameModelHandle.getDigits().length(); i++) {
	
			if(positionX > Case.cases.get(i).getX0() && 
			positionX < Case.cases.get(i).getX1() &&
			positionY > Case.cases.get(i).getY0() && 
			positionY < Case.cases.get(i).getY1()) {
	
				identifiant = Case.cases.get(i).getIdentifiant();
				break;
				
			} else
				identifiant = -1;
		} 
		
		return identifiant;
	
	}
	
	private void paintRoundRectangle (Graphics g, Color colour, int width, int height, int position) {
		
		g.setColor(colour);
		g.fillRoundRect(position*width, 0, width, height, 50, 60);
		
	}
	
	private void drawStringOnCases (Graphics g, Color colour, String string, int x, int y) {
		
		g.setColor(Color.BLACK);
		g.drawString(string,x , y);
		
	}
		
	public TilePanel(GameModel gameModel) {
		if (gameModel == null)
			throw new IllegalArgumentException("Should provide a valid instance of GameModel!");
		gameModelHandle = gameModel;
		
		// Initialize our array of tile colours
		initializeColours();
	}
	
}
