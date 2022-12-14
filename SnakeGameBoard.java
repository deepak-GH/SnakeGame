package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGameBoard extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1782456368519452947L;
	private final int BOARD_WIDTH = 600;
	private final int BOARD_HEIGHT = 600;
	private final int DOT_SIZE = 10;
	//private final int ALL_DOTS = 900;
	private final int DELAY = 200;
	
	private final int xPosition[];
	private final int yPosition[];
	
	private int snakeSize;
	private int apple_x_pos;
	private int apple_y_pos;
	
	public boolean right = true;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;
	private boolean gameON = true;
	
	private Timer gameTimer;
	private Image ballImg;
	private Image appleImg;
	private Image headImg;
	
	//private ImageIcon appleIcon;
	
	public SnakeGameBoard()
	{
		this.yPosition = new int[BOARD_HEIGHT];
		this.xPosition = new int[BOARD_WIDTH];
		initializeSnakeBoard();
	}
		
	private void initializeSnakeBoard()
	{
		addKeyListener(new UserClickAdapter());
		setBackground(Color.black);
		setFocusable(true);
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		loadAllImages();
		initializeGame();
	}
		
	private class UserClickAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			int key = e.getKeyCode();
			if ((key == KeyEvent.VK_LEFT) && (!right))
			{
				left = true;
				up = false;
				down = false;
			}
			if ((key == KeyEvent.VK_RIGHT) && (!left))
			{
				right = true;
				up = false;
				down = false;
			}
			if ((key == KeyEvent.VK_UP) && (!down))
			{
				up = true;
				right = false;
				left = false;
			}
			if ((key == KeyEvent.VK_DOWN) && (!up))
			{
				down = true;
				right = false;
				left = false;
			}
		}

	}
	
    private void loadAllImages()
    {
    	ImageIcon ballIcon = new ImageIcon("src/gameIcons/ball-icon-png-4636.png");
		ballImg = ballIcon.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_AREA_AVERAGING);
			
		ImageIcon appleIcon = new ImageIcon("src/gameIcons/apple-logo-icon-14907.png");
		appleImg = appleIcon.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_SMOOTH);
			
		ImageIcon headIcon = new ImageIcon("src/gameIcons/anaconda.png");
		headImg = headIcon.getImage().getScaledInstance(DOT_SIZE, DOT_SIZE, Image.SCALE_AREA_AVERAGING);
	}
    	
    private void initializeGame()
    {
    	snakeSize = 3;
		for(int p=0;p<snakeSize;p++)
		{
			xPosition[p] = 50-p*DOT_SIZE;
			yPosition[p] = 50;
		}
		
		locateNewApple();
		gameTimer = new Timer(DELAY, this);
		gameTimer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		doDrawing(g);
		
	}

	private void doDrawing(Graphics g) {
		
		if(gameON)
		{
			//appleIcon.paintIcon(this, g, apple_x_pos, apple_y_pos);
			g.drawImage(appleImg, apple_x_pos, apple_y_pos, this);
			for(int p=0;p<snakeSize;p++)
			{
				if(p==0)
				{
					g.drawImage(headImg, xPosition[p], yPosition[p], this);
				}
				else
				{
					g.drawImage(ballImg, xPosition[p], yPosition[p], this);
				}
			}
			Toolkit.getDefaultToolkit().sync();
		}
		else
		{
			gameOver(g);
		}
	}

	private void gameOver(Graphics g) {
		
		g.setColor(Color.red);
		g.setFont(new Font("Algerian", Font.BOLD, 60));
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		g.drawString("GAME OVER!", (BOARD_WIDTH-metrics.stringWidth("GAME OVER!"))/2, BOARD_HEIGHT/2);
	}
	
	private void checkApple()
	{
		if((xPosition[0] == apple_x_pos) && (yPosition[0] == apple_y_pos))
		{
			snakeSize++;
			locateNewApple();			
		}
	}
	
	private void moveSnake()
	{
		for(int p=snakeSize;p>0;p--)
		{
			xPosition[p] = xPosition[(p-1)];
			yPosition[p] = yPosition[(p-1)];
		}
		if(left)
			xPosition[0] -= DOT_SIZE;
		if(right)
			xPosition[0] += DOT_SIZE;
		if(up)
			yPosition[0] -= DOT_SIZE;
		if(down)
			yPosition[0] += DOT_SIZE;
	}
	
	private void checkSnakeCollision()
	{
		for(int p=snakeSize;p>0;p--)
		{
			if((p>4) && (xPosition[0] == xPosition[p]) && (yPosition[0] == yPosition[p]))
				gameON = false;
		}
		if(yPosition[0]>=BOARD_HEIGHT)
			gameON=false;
		if(yPosition[0]<0)
			gameON=false;
		if(xPosition[0]>=BOARD_WIDTH)
			gameON=false;
		if(xPosition[0]<0)
			gameON=false;
		if(!gameON)
			gameTimer.stop();
	}
	
	private void locateNewApple() {
		
		int random = (int)(Math.random()*59);
		apple_x_pos = random*DOT_SIZE;
		
		random = (int)(Math.random()*59);
		apple_y_pos = random*DOT_SIZE;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(gameON)
		{
			checkApple();
			checkSnakeCollision();
			moveSnake();
		}
		repaint();
	}
}
