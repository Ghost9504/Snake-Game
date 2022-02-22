
package ca2;

import java.awt.*;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.*;
import java.util.Random;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sebastian fernandes
 */
public final class GAmePanel extends JPanel implements ActionListener {
    
   
	static final int SCREEN_WIDTH = 1300/2;

	static final int SCREEN_HEIGHT = 750/2;

	static final int UNIT_SIZE = 25;

	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);

	static final int DELAY = 175;

	final int x[] = new int[GAME_UNITS];

	final int y[] = new int[GAME_UNITS];

	int bodyParts = 6;

	int applesEaten;

	int appleX;

	int appleY;

	char direction = 'R';

	boolean running = false;

	Timer timer;

	Random random;
	
public void easy(){}
        JButton button;
        JTextField text;

           JLabel label ;
        GAmePanel(String Uname){
            
            label = new JLabel();
            label.setText(Uname);

		random = new Random();

		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));

		this.setBackground(Color.black);

		this.setFocusable(true);

		this.addKeyListener(new MyKeyAdapter());

                
                button = new JButton("Submit");
                text = new JTextField();
                text.setPreferredSize(new Dimension(250,40));
                text.setText(Uname);
                String name =label.getText();
                System.out.println(name);
		startGame();
                
           
	}
        
        
        

	public void startGame() {

		newApple();

		running = true;

		timer = new Timer(DELAY,this);

		timer.start();

	}

        @Override
	public void paintComponent(Graphics g) {

            try {
                super.paintComponent(g);
                
                draw(g);
            } catch (SQLException ex) {
                Logger.getLogger(GAmePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

	}

	public void draw(Graphics g) throws SQLException {

		

		if(running) {

			/*

			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {

				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);

				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);

			}

			*/

			g.setColor(Color.red);

			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

		

			for(int i = 0; i< bodyParts;i++) {

				if(i == 0) {

					g.setColor(Color.green);

					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

				}

				else {

					g.setColor(new Color(45,180,0));

					//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));

					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

				}			

			}

			g.setColor(Color.red);

			g.setFont( new Font("Ink Free",Font.BOLD, 40));

			FontMetrics metrics = getFontMetrics(g.getFont());

			g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

		}

		else {

			gameOver(g);

		}

		

	}

        
        
        
	public void newApple(){

		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;

		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

	}

	public void move(){

		for(int i = bodyParts;i>0;i--) {

			x[i] = x[i-1];

			y[i] = y[i-1];

		}

		

		switch(direction) {

		case 'U' -> y[0] = y[0] - UNIT_SIZE;

		case 'D' -> y[0] = y[0] + UNIT_SIZE;

		case 'L' -> x[0] = x[0] - UNIT_SIZE;

		case 'R' -> x[0] = x[0] + UNIT_SIZE;

		}

		

	}

	public void checkApple() {

		if((x[0] == appleX) && (y[0] == appleY)) {

			bodyParts++;

			applesEaten++;

			newApple();

		}

	}

	public void checkCollisions() {

		//checks if head collides with body

		for(int i = bodyParts;i>0;i--) {

			if((x[0] == x[i])&& (y[0] == y[i])) {

				running = false;

			}

		}

		//check if head touches left border

		if(x[0] < 0) {

			running = false;

		}

		//check if head touches right border

		if(x[0] > SCREEN_WIDTH) {

			running = false;

		}

		//check if head touches top border

		if(y[0] < 0) {

			running = false;

		}

		//check if head touches bottom border

		if(y[0] > SCREEN_HEIGHT) {

			running = false;

		}

		

		if(!running) {

			timer.stop();

		}

	}

	public void gameOver(Graphics g) throws SQLException {

		//Score
 String name =label.getText();
		g.setColor(Color.red);

		g.setFont( new Font("Ink Free",Font.BOLD, 40));

		FontMetrics metrics1 = getFontMetrics(g.getFont());

		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

		//Game Over text

		g.setColor(Color.red);

		g.setFont( new Font("Ink Free",Font.BOLD, 75));

		FontMetrics metrics2 = getFontMetrics(g.getFont());

	
         
                
                g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
                
                
                
int check=JOptionPane.showConfirmDialog(null,"Do you want to continue?","Select",JOptionPane.YES_NO_OPTION);
if(check==0){
System.out.println(name);
        
       int score = applesEaten;
        String sql = "select * from game where username='"+name+"'";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/game","root","");
           java.sql.Statement pst= conn.createStatement();
       
      System.out.println("Done!");
//       Statement pst= (Statement) conn.createStatement();
       ResultSet rs = pst.executeQuery(sql);
       
        if(rs.next()){
                     int num = rs.getInt("ID");
      System.out.println(num);
           System.out.println("Done!!!!");
           int high=rs.getInt("easy");
           if(score>high){
            sql ="update game set easy='"+score+"' where ID = '"+num+"'  ";
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/game","root","");
           PreparedStatement p = conn.prepareStatement(sql);
        p.executeUpdate();
        System.out.println("Done!!!!");
        JOptionPane.showMessageDialog(null,"High score updated successfully!");
           }
       new Snakeframe(name);
        }
}
else{

    try{
    int score = applesEaten;
        String sql = "select * from game where username='"+name+"'";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/game","root","");
           java.sql.Statement pst= conn.createStatement();
       
      System.out.println("Done!");
//       Statement pst= (Statement) conn.createStatement();
       ResultSet rs = pst.executeQuery(sql);
       
        if(rs.next()){
                     int num = rs.getInt("ID");
      System.out.println(num);
           System.out.println("Done!!!!");
           int high=rs.getInt("easy");
           if(score>high){
            sql ="update game set easy='"+score+"' where ID = '"+num+"'  ";
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/game","root","");
           PreparedStatement p = conn.prepareStatement(sql);
        p.executeUpdate();
        System.out.println("Done!!!!");
        JOptionPane.showMessageDialog(null,"High score updated successfully!");
           }
        
        new Menu(name).setVisible(true);
         new GAmePanel(name).setVisible(false);
        }
        
        
    }catch(HeadlessException | SQLException e){}

}

        }
	@Override

	public void actionPerformed(ActionEvent e) {

		

		if(running) {

			move();

			checkApple();

			checkCollisions();

		}

		repaint();

	}

	

	public class MyKeyAdapter extends KeyAdapter{

		@Override

		public void keyPressed(KeyEvent e) {

			switch(e.getKeyCode()) {

			case KeyEvent.VK_LEFT -> {
                            if(direction != 'R') {
                                
                                direction = 'L';
                                
                            }
                        }

			case KeyEvent.VK_RIGHT -> {
                            if(direction != 'L') {
                                
                                direction = 'R';
                                
                            }
                        }

			case KeyEvent.VK_UP -> {
                            if(direction != 'D') {
                                
                                direction = 'U';
                                
                            }
                        }

			case KeyEvent.VK_DOWN -> {
                            if(direction != 'U') {
                                
                                direction = 'D';
                                
                            }
                        }

			}

		}

	}

}       

