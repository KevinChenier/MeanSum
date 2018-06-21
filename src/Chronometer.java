import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Class that manages a chronometer
 * Methods:
 * getTimeLabel(), method that will return the time label that is created in this class
 * resetTimer(), resets the timer to 00:01
 * startTimer(), starts the timer
 * stopTimer(), stops the timer
 * 
 * @author Kevin Chenier
 * @version E2018
 */

public class Chronometer implements ActionListener {

	private  int seconde=1, minute;
	private JLabel timeLabel;   
	
	//We instantiate a timer with a delay
	Timer timer = new Timer(Constants.TIMERDELAY_MS, this);   
	
	public Chronometer(){ 

		timeLabel = new JLabel(Constants.TIMELABELTEXT + "00:00"); 
		timer.start();  

	}  
	
	/** 
	 * The timeLabel is managed according to the normal operation of a normal dial.
	 */
	
	public void actionPerformed(ActionEvent e){ 
		
		String secondeString; 
		String minuteString;
		
		//If the seconds are smaller than 10, we put a 0 string before the value of seconds
		if (seconde < 10)  
			secondeString = "0" + seconde;
		else 
			secondeString = "" + seconde;
	
		//If the minutes are smaller than 10, we put a 0 string before the value of seconds
		if (minute < 10)  
			minuteString = "0" + minute;
		else 
			minuteString = "" + minute;
		
		timeLabel.setText(Constants.TIMELABELTEXT + minuteString +":" + secondeString);	
		this.seconde++; 
		
		//If seconde is now 60, we change it to 0 and increment the minutes
		if(seconde == 60){ 
			minute ++; 
			seconde = 0;
		}
	} 
	
	/**This method will return the JLabel responsible for holding the information
	 * of the timer
	 * 
	 * @author Kevin Chenier
	 * @return timeLabel, the label that holds the time data
	 */

	public JLabel getTimeLabel() {
		return timeLabel;
	} 

	/**This method will restart the timer
	 * 
	 * @author Kevin Chenier
	 */
	
	public void resetTimer(){ 
		
		seconde = 1; 
		minute = 0; 
		timer.restart();
		
	}
	
	/**This method will start the timer
	 * 
	 * @author Kevin Chenier
	 */
	
	public void startTimer() {
		timer.start(); 
	}
	
	/**This method will stop the timer
	 * 
	 * @author Kevin Chenier
	 */
	
	public void stopTimer() {
		timer.stop();
	}
}