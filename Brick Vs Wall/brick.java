package brickVsWall;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class brick implements ActionListener, MouseListener, KeyListener{
	
	public static brick Brick;
	public final int WIDTH = 1200, HEIGHT = 800;
	public static final Color VERY_DARK_RED = new Color(153,0,0);
	public static final Color GREY_DARK = new Color(0,204,204);
	
	public Renderer renderer;
	public Random rand;
	public int ticks, yMotion, score, finalscore, maxscore = 0;
	public boolean gameOver, started = false;
	public Rectangle brick;

	
	public ArrayList<Rectangle> columns;

public brick(){
		
		
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20,this);
		
		renderer = new Renderer();
		rand = new Random();
		
		jframe.add(renderer);
		jframe.setTitle("Brick VS Wall");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);
		
		brick = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		
		timer.start();
		
	}
	
	public void addColumn(boolean start) {
		
		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);

		
		if(start) {
	
		columns.add(new Rectangle(WIDTH+ width + columns.size()*300,HEIGHT - height - 120, width, height ));
		columns.add(new Rectangle(WIDTH+ width + (columns.size() - 1) * 300, 0,width, HEIGHT - height - space));
		
		}
		
		else {
			
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x,0,width,HEIGHT - height -space));
		}
		
		
		
		}
	
	public void paintColumn(Graphics g, Rectangle column) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(column.x,column.y,column.width,column.height);
		
	}
	
	public void jump() {
		
		if(gameOver) {
			
			brick = new Rectangle(WIDTH/2 - 10, HEIGHT/2 - 10, 20, 20);
			columns.clear();
			yMotion = 0;
			score = 0;
			
			addColumn(true);
		    addColumn(true);
			addColumn(true);
			addColumn(true);
			

			gameOver = false;
		}
		if(!started) {
			
			started = true;
		}
		else if(!gameOver){
			
			if(yMotion > 0) {
				
				yMotion = 0;
			}
			
			yMotion -=10;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		int speed = 10;
		
		ticks++;
		
		if(started) {
		
		for(int i = 0; i < columns.size(); i++) {
			
			Rectangle column = columns.get(i);
			column.x -= speed;
		}
		
		if(ticks % 2 == 0 && yMotion < 15) {
			
			yMotion +=2;
			
			
		}
		
		for(int i = 0; i < columns.size(); i++) {
			
			Rectangle column = columns.get(i);
			
			if(column.x + column.width < 0) {
				
				columns.remove(column);
				
				if(column.y == 0) {
				
				addColumn(false);
			}
		 }
		
		}
		brick.y += yMotion;
		
		for(Rectangle column: columns) {
			
			if(column.y== 0 && brick.x + brick.width / 2 > column.x + column.width/2 - 5 && brick.x + brick.width / 2 < column.x + column.width/2 + 5 ) {
				
				score++;
			}
			
			if(brick.y == HEIGHT - 120) {
				
				gameOver = true;
			}
			if(column.intersects(brick)) {
				
				gameOver = true;
				
				
				if(brick.x <= column.x) {
				
				brick.x = column.x - brick.width;
				
				}
				
				else {
					
					if(column.y != 0) 
					{
						brick.y = column.y - brick.height;
					}
					
					else if(brick.y < column.height) {
						
						brick.y = column.height;
						
					}
					
				}
			}
			
			
		}
		
		if(brick.y > HEIGHT - 120 || brick.y < 0) {
			
			brick.y = HEIGHT - 120 - brick.height;
			
			
			
		}
		
		if(brick.y + yMotion >= HEIGHT - 120) {
			
			brick.y = HEIGHT - 120 - brick.height;
		}
		
		}
		renderer.repaint();

	
	
	}
	
	

	public void repaint(Graphics g) {
		
		g.setColor(GREY_DARK);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		g.setColor(Color.red.darker());
		g.fillRect(brick.x, brick.y, brick.width, brick.height);
		
		g.setColor(Color.orange.darker());
		g.fillRect(0,HEIGHT - 120 , WIDTH, 120);
		
		g.setColor(Color.green.darker());
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);
		
		for(Rectangle column: columns) {
			
			paintColumn(g,column);
		}
		
		if(gameOver) {
	
		g.setColor(Color.white);
		
		g.setFont(new Font("Arial", 1, 100));
		
		
		
		}
		
		if(!gameOver && !started) {
			
			g.drawString("Click to Start!", 75, HEIGHT / 2 - 50);
			
		
}
		
		if(gameOver) {
			
			g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
			g.drawString(String.valueOf(finalscore), WIDTH/2 - 25, 100);
			
		
		}
		
		if(!gameOver && started) {
			
			g.drawString(String.valueOf(score), WIDTH/2 - 25, 100);
			finalscore = score ; 
		}
		

	
	}
	



	public static void main(String[] args) {
		
		Brick = new brick();
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
		jump();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		
	}


	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
