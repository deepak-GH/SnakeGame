package snake;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class SnakeGameClass extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3969144109837360198L;

	public SnakeGameClass()
	{
		initializeUI();
	}
	
	private void initializeUI()
	{
		add(new SnakeGameBoard());
		setResizable(false);
		pack();
		setTitle("SNAKE GAME!");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}	
	
	public static void main(String[] args) {

		EventQueue.invokeLater(()-> {
			JFrame snakeFrame=new SnakeGameClass();
			snakeFrame.setVisible(true);
		});
	}
}
