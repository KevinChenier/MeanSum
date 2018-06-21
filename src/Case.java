/**
 * This class is used to control the attributes in the cases. A case has a number, a state
 * an identifier, an attribute that tells if it is in a group, an attribute that tells if it
 * participated in the calculation of the current sum, and the dimensions of its rectangle in 
 * the tilePanel defined by X0, X1, Y0, Y1.
 * Methods:
 * setNumber(int number), set the number of the case
 * getNumber(), accessor to the number 
 * setState(boolean state), set the state of the case
 * getState(), accessor to the state
 * setIdentifiant(int identifiant), set the identifier of the case
 * getIdentifier(), accessor to the identifier
 * setX0, set the position of the rectangle's left side
 * setX1, set the position of the rectangle's right side
 * setY0, set the position of the rectangle's top side
 * setY1, set the position of the rectangle's bottom side
 * getX0, accessor of the rectangle's left side
 * getX1, accessor of the rectangle's right side
 * getY0, accessor of the rectangle's top side
 * getY1, accessor of the rectangle's bottom side
 * setIsInGroup(boolean isInGroup), if the case is i a group, it will be set to true
 * setIsInGroupWith(Case case1), method that will set a link between two cases in a group
 * isInGroup(), accessor to the attribute isInGroup
 * toString(), a string representation of the number in the case
 * 
 * @author Kevin Chenier
 * @version E2018
 */
public class Case {
	
	//Attributes of the class
	private int number, identifiant, X0, X1, Y0, Y1;
	private boolean state, isInGroup;
	private Case isInGroupWith;
	
		public Case(int number, boolean state, int identifiant) {
			this.number = number;
			this.state = state;
			this.identifiant = identifiant;
			
		}
		
		/**The method sets the number of the case
		 * 
		 * @param number, the number it will be attributed
		 * @author Kevin Chenier
		 */
		public void setNumber(int number) {
			this.number = number;
		}
		
		/**Accessor to the number of the case
		 * 
		 * @return number, the number in the case
		 * @author Kevin Chenier
		 */
		public int getNumber() {
			return this.number;
		}
		
		/**The method sets the state of the case
		 * 
		 * @param state, the state that the case is in, if it is selected, it will have to be true
		 * @author Kevin Ch�nier
		 */
		public void setState(boolean state) {
			this.state = state;
		}
		
		/**Accessor to the state of the case
		 * 
		 * @return state, the state of case
		 * @author Kevin Chenier
		 */
		public boolean getState() {
			return this.state;
		}
		
		/**The method sets the identifier of the case. It will depend on its position
		 * in the tile panel
		 * 
		 * @param identifiant, the identifier it will be given
		 * @author Kevin Ch�nier
		 */
		public void setIdentifiant(int identifiant) {
			this.identifiant = identifiant;
		}
		
		/**Accessor to the identifier of the case
		 * 
		 * @return identifiant, the identifier of the case
		 * @author Kevin Chenier
		 */
		public int getIdentifiant() {
			return this.identifiant;
		}
		
		/**This method will set the rectangle's left side
		 * 
		 * @param X0, rectangle's left side
		 * @author Kevin Ch�nier
		 */
		public void setX0(int X0) {
			this.X0=X0;
		}
		
		/**This method will set the rectangle's right side
		 * 
		 * @param X1, rectangle's right side
		 * @author Kevin Ch�nier
		 */
		public void setX1(int X1) {
			this.X1=X1;
		}
		
		/**This method will set the rectangle's top side
		 * 
		 * @param Y0, rectangle's top side
		 * @author Kevin Ch�nier
		 */
		public void setY0(int Y0) {
			this.Y0=Y0;
		}
		
		/**This method will set the rectangle's bottom side
		 * 
		 * @param Y1, rectangle's bottom side
		 * @author Kevin Ch�nier
		 */
		public void setY1(int Y1) {
			this.Y1=Y1;
		}
		
		/**Accessor to rectangle's left side
		 * 
		 * @author Kevin Chenier
		 * @return X0, rectangle's left side
		 */
		public int getX0() {
			return this.X0;
		}
		
		
		/**Accessor to rectangle's right side
		 * 
		 * @author Kevin Chenier
		 * @return X1, rectangle's right side
		 */
		public int getX1() {
			return this.X1;
		}
		
		/**Accessor to rectangle's top side
		 * 
		 * @author Kevin Chenier
		 * @return Y0, rectangle's top side
		 */
		public int getY0() {
			return this.Y0;
		}
		
		/**Accessor to rectangle's bottom side
		 * 
		 * @author Kevin Chenier
		 * @return Y1, rectangle's bottom side
		 */
		public int getY1() {
			return this.Y1;
		}
		
		/**This method is used when the case is in a group 
		 * 
		 * @param isInGroup, it will be true if it is in a group
		 * @author Kevin Ch�nier
		 */
		public void setIsInGroup(boolean isInGroup) {
			this.isInGroup=isInGroup;
		}
		
		/**This method sets a link between two cases that are in a group
		 * 
		 * @param case1, the case that is in a group with the other case
		 * @author Kevin Chenier
		 */
		public void setIsInGroupWith(Case case1) {
			this.isInGroupWith=case1;
		}
		
		/**Method that will return the case that is in a group with the one
		 * used with the method
		 * 
		 * @return isInGroupWith, the case that is a in the group
		 */
		public Case getIsInGroupWith() {
			return this.isInGroupWith;
		}
		
		/** Accessor to the isInGroup attribute
		 * 
		 * @return isInGroup, true if in group
		 * @author Kevin Ch�nier
		 */
		public boolean isInGroup() {
			return this.isInGroup;
		}
		
		/** String representation of the number in case
		 * 
		 * @return a string representation of the case's number
		 * @author Kevin Ch�nier
		 */	
		public String toString() {
			return "" + this.number;
		}
		
}
