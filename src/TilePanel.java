import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The tile panel displays all the tiles (one per digit) of the game. This class also holds a method
 * that will retain a case that has been selected by the player. This class has attributes that will retain
 * the rectangle width and center. 
 * Methods:
 * paintComponent(Graphics g), used to make graphics
 * paintRoundRectangle(Graphics g, Color color, int width, int height, int position, this method is used to
 * paint round rectangle in the tile panel. 
 * drawString(Graphics g, Color color, String digit, int x, int y), this method is used to draw a digit
 * on each round rectangle.
 * 
 * @author Kevin Chenier
 * @version E2018
 */
public class TilePanel extends JPanel {
	
	private int largeurRectangle, centreRectangle;

	/**
	 * The tile panel object holds a reference to the game model to
	 * request information to display (view) and to modify its state (controller)
	 */
	private GameModel gameModelHandle;

	/**
	 * A table of colours that can be used to draw the tiles
	 */
	private Color[] colours;
	
	//A new font is used for digits that are written in round rectangles
	Font numberFont = new Font ("Arial Bold", Font.ROMAN_BASELINE, Constants.FONT_SIZE);
	
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

		//This will set the font of g to numberFont
		g.setFont(numberFont);
		
		/*
		 * The width of the rectangles will be equal to the width of the window divided by the number
		 * of digits that are in numberList
		 */
		try {
			largeurRectangle = this.getWidth()/gameModelHandle.getDigits().length();
		} catch (ArithmeticException e) {
			System.err.println("La liste de nombre est vide");
		}
		
		//This for loop is used to paint as much rectangle as there are digits in numberList
		for(int i=0; i<gameModelHandle.getDigits().length(); i++) {
			
			/*
			 * We first check if the goal has been obtained by the player. If it has been obtained
			 * we paint every rectangle, we paint its digits and we set the color's rectangles to
			 * green
			 */
			
			if(gameModelHandle.checkIfGoalObtained()==true) 
				paintRectanglesAndStrings (g, Color.GREEN, i);
			
			/*
			 * If the goal has not been obtained, we then check if all the cases have been selected by the player OR if
			 * the player has surpassed the goal with the current sum. If this is the case, we paint every rectangle,
			 * we paint its digits and we set the color's rectangles to red
			 */
		
			else if(gameModelHandle.checkIfAllCasesSelected()==true || gameModelHandle.sommeBiggerThanGoal()==true) 
				paintRectanglesAndStrings (g, Color.RED, i);
				
			/*
			 * If the goal has not been obtained yet, and the cases were not all selected by the
			 *  player or he did not yet surpassed the goal with his selections, then we check
			 *  other conditions
			 */
			
			 else {
				
				/*
				 * If the case has not been selected yet, we draw the non selected rectangle with the 
				 * white color and its digit accordingly
				 */
				
				 if(gameModelHandle.getCases().get(i).getState()==false) 
					 paintRectanglesAndStrings (g, Color.WHITE, i);
				
				/*
				 * If the case is in a group, we can paint the first rectangle with a color that is in
				 * colours[], we then skip an iterator to paint the other rectangle with same color as the
				 * case it is in a group with by picking the color coulour[iterator-1]
				 */
				
				 else if(gameModelHandle.getCases().get(i).isInGroup()==true) {
					paintRectanglesAndStrings (g, colours[i], i);
					i++;
					paintRectanglesAndStrings (g, colours[i-1], i);
				 }
				
				/*
				 * If all the other conditions were not met, then we can paint the rectangle with a color that is
				 * in colour[iterator] with its digit
				 */
				
				 else 
					paintRectanglesAndStrings (g, colours[i], i);
				
			}
		}
		//Call to the method for setting the dimensions of the cases
		gameModelHandle.setCasesDimensions(this,largeurRectangle,this.getHeight()/2+Constants.CORRECTION);	
	}
	
	/** This method paints a round rectangle on g Graphics 
	 * 
	 * @param g, the graphics that it will paint on
	 * @param colour, the color of the round rectangle
	 * @param width, the width of the rectangle
	 * @param height, the height of the rectangle
	 * @param position, the position of the rectangle in the window
	 */
	
	private void paintRoundRectangle (Graphics g, Color colour, int width, int height, int position) {
		
		g.setColor(colour);
		g.fillRoundRect(position*width, 0, width, height, 50, 60);
		
	}
	
	/** This method draws the digit of a rectangle
	 * 
	 * @param g, the graphics that it will paint on
	 * @param colour, the color of the string
	 * @param digit, the digit that will be drawn
	 * @param x, the x position of the string
	 * @param y, the y position of the string
	 */
	
	private void drawString (Graphics g, Color colour, String digit, int x, int y) {
		
		g.setColor(Color.BLACK);
		g.drawString(digit,x , y);
		
	}
	
	/** This method paints the rectangles and the string inside it
	 * 
	 * @param g, the graphics that it will paint on
	 * @param colour, the color of the rectangle
	 * @param iterator, the iterator that will be passed in parameter
	 * to draw the rectangles and the strings accordingly
	 */
	
	private void paintRectanglesAndStrings (Graphics g, Color colourRectangle, int iterator) {
		paintRoundRectangle (g, colourRectangle, largeurRectangle, this.getHeight()/2+Constants.CORRECTION, iterator);
		centreRectangle = iterator*largeurRectangle + largeurRectangle/2;
		drawString (g, Color.BLACK, gameModelHandle.getDigits().substring(iterator, iterator+1), centreRectangle-Constants.CORRECTION, this.getHeight()/4+Constants.CORRECTION);
	}
		
	public TilePanel(GameModel gameModel) {
		if (gameModel == null)
			throw new IllegalArgumentException("Should provide a valid instance of GameModel!");
		gameModelHandle = gameModel;
		
		// Initialize our array of tile colours
		initializeColours();
	}
	
}
