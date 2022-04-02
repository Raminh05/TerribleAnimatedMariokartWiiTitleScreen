/*
Name: Minh Nguyen
Unit Three Assignment -- Animation
Date: March 23rd, 2022
Description: Literally my Unit Two Project but with some extra animation in the loading bar and fade in effects.
WARNING: The output only works well in 1920x1080 for some reason
Fonts Used: Continuum Bold (Wii Logo), Mario Kart F2 (Game Logo)
*/

import hsa_ufa.Console;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class unitThreeAssignment {
	public static void main(String[] args) throws InterruptedException, IOException, LineUnavailableException, UnsupportedAudioFileException {
		
		// Declares console window with custom dimensions and name
		Console c = new Console(1920, 1080, 25, "Totally a Wii!");
		
		// Console dimension variables (advanced technique)
		int halfScreenWidth = c.getWidth()/2;
		int halfScreenHeight = c.getHeight()/2;

		// Range for random number generation
		int min = 0;
		int max = 1920;
		
		// Loading animation with edge detection physics

		int i = 0;          // The position of the star 
		int direction = 1; // Moves right by default
		int counter = 0;  // How many times the loop has completed
		
		// The little moving star at the very beginning of the animation 
		while (counter < 250) { // The window is 125 columns wide. We have to travel that distance twice with the bounce back
			synchronized (c) {
				c.clear();
				c.setCursor(30, i);
				c.print("*");
			}
			Thread.sleep(16);
			i+=direction;
			
			if (i > 125) {
				direction = direction * -1; // Bounce back logic -- Inverts direction of the star once it hits the edge of the screen
			}
			else if (i < 0) {
				direction = direction * -1; // Bounce back
			}
			counter++;
		}
		
		c.clear(); // Clears the little moving star

		// Font for loading bar
		c.setFont(new Font("Courier New", Font.ITALIC, 30));

		// Prints "Loading" and then add three dots after the word (- .... .- -. -.- ... / .- - ..- .-.. / ..-. --- .-. / - .... . / .. -.. . .-)
		for (int r = 0; r < 3; r++) {
			c.setColor(Color.BLACK);
			c.drawString("Loading", halfScreenWidth-100, 985); // Initial Word
			Thread.sleep(500);
			c.drawString(".", halfScreenWidth+20, 985);       // First dot
			Thread.sleep(500);
			c.drawString(".", halfScreenWidth+30, 985);      // Second Dot
			Thread.sleep(500);
			c.drawString(".", halfScreenWidth+40, 985);     // Third Dot
			Thread.sleep(800);
			c.setColor(Color.WHITE);                       // White rectangle overlay
			c.fillRect(1920/2-100, 963, 500, 30);         // Clears the loading word and starts again by overlapping a rectange on top of it

		}

		// BLue loading bar that randomly stops for realism 
		for (int x  = 0; x < 1920; x+=5) {
			synchronized (c) {
				c.setColor(Color.BLUE); // Loading bar is blue
				c.fillRect(0, 1020, x, 60);
			}

			int randomNum = (int)Math.floor(Math.random()*(max-min+1)+min); // Generates new random number through every interation

			if (x == randomNum || x == 1920/3) { // If the loading bar width is equal to the random number or is a third of the way through loading, pause. 
				Thread.sleep(700);
			}
			else { // Else, proceed like usual
				Thread.sleep(16);
			}
		}
		
		// Fades the loading bar away
		for (int j = 0; j < 50; j++) {
			c.setColor(new Color(255, 255, 255, j));
			c.fillRect(0, 1020, 1920, 60);
			Thread.sleep(16);
		}

		c.setBackgroundColor(Color.WHITE); // Set background colour to be white
		c.clear();                        // Clears loading bar phase
		
		// Loads picture1 -> Wiimote backdrop (drawImage)
		File picture1 = new File("src/wiimote.jpg");
		Image myImage = ImageIO.read(picture1);
		c.drawImage(myImage, halfScreenWidth-250, halfScreenHeight-350, 500, 500);
		
		// Start the rectangle completely clear and then make it more opaque in a for loop. (Fade in)
		for (int y = 0; y < 21; y++) {
			c.setColor(new Color(255, 255, 255, y)); // Custom colour for translucent rectangle
			c.fillRect(0, 0, 1920, 1080);
			Thread.sleep(16);
		}
		
		// Blue circle thingy/ring (advanced technique -- fillArc)
		c.setColor(new Color(29, 188, 227, 255));
		c.fillArc(halfScreenWidth-82, 210, 170, 170, 0, 360);
		c.setColor(Color.WHITE);
		c.fillArc(halfScreenWidth-67, 225, 140, 140, 0, 360);
		
		// Luigi's Kart Shadow
		c.setColor(new Color(141,139,140,255));
		c.fillArc(halfScreenWidth-350, 680, 300, 180, 0, -180);
		c.setColor(Color.WHITE);
		c.fillRect(halfScreenWidth-277, 770, 150, 30);
		
		// Mario's Kart Shadow
		c.setColor(new Color(141,139,140,255));
		c.fillArc(halfScreenWidth-10, 700, 300, 180, 0, -180);
		c.setColor(Color.WHITE);
		c.fillRect(halfScreenWidth+65, 790, 150, 30);
		
		// Wii Logo
		c.setColor(new Color(153,153,153,255));
		c.setFont(new Font("Continuum Bold", Font.PLAIN, 65)); // Font Name: Continuum Bold
		c.drawString("Wii", halfScreenWidth-45, 340);
		
		// Mario Kart Logo
		c.setFont(new Font("Mario Kart F2", Font.PLAIN, 45)); // Font Name: Mario Kart F2
		c.setColor(new Color(82,81,87,255));
		c.drawString("MARIOKART", halfScreenWidth-242, 285);
		
		// Wheel Shadows that are in perspective (advanced techniques -- fillPolygon with Arrays)
		c.setColor(new Color(141,139,140,255));
		c.fillPolygon(new int[] {halfScreenWidth-255, halfScreenWidth-175, halfScreenWidth-125, halfScreenWidth-200}, new int[] {800, 800, 720, 720}, 4);  // Luigi's Kart Base
		c.fillPolygon(new int[] {halfScreenWidth+70, halfScreenWidth+150, halfScreenWidth+200, halfScreenWidth+125}, new int[] {740, 740, 820, 820}, 4);  // Mario's Kart Base
		
		c.fillPolygon(new int[] {halfScreenWidth-350, halfScreenWidth-277, halfScreenWidth-252, halfScreenWidth-325}, new int[] {768, 768, 730, 730}, 4);  // Luigi's front left wheel
		c.fillPolygon(new int[] {halfScreenWidth+190, halfScreenWidth+265, halfScreenWidth+290, halfScreenWidth+215}, new int[] {750, 750, 785, 785}, 4); // Mario's front right wheel
		
		c.fillPolygon(new int[] {halfScreenWidth-280, halfScreenWidth-227, halfScreenWidth-202, halfScreenWidth-255}, new int[] {718, 718, 690, 690}, 4);  // Luigi's rear left wheel
		c.fillPolygon(new int[] {halfScreenWidth+150, halfScreenWidth+203, halfScreenWidth+228, halfScreenWidth+175}, new int[] {712, 712, 740, 740}, 4); // Mario's rear right wheel
		
		// Luigi's Bottom Right	(fill method)
		c.fillRect(halfScreenWidth-125, 738, 75, 30);
		
		// Mario's Bottom Left
		c.fillRect(halfScreenWidth-10, 758, 75, 30);
		
		// Rear rectangle wheels 
		c.fillRect(halfScreenWidth-125, 703, 75, 20); // Luigi's wheel
		c.fillRect(halfScreenWidth-10, 715, 75, 25); // Mario's wheel
		
		// picture2 -> Mario and Luigi sprites
		File picture2 = new File("src/weeeeeee.png");
		Image myImage2 = ImageIO.read(picture2);
		
		c.drawImage(myImage2, halfScreenWidth-285, halfScreenHeight-180, 500, 500);
		
		// Prints my name
		Thread.sleep(700); // Small pause for dramatic effect
		c.setFont(new Font("Arial", Font.PLAIN, 20));
		c.drawString("Minh Nguyen", 5, 1070);
		
		// Nintendo don't sue me (setCursor and print functions)
		c.drawString("Everything is owned by Nintendo", 1620, 1070);

		// Loads Game Title Screen Music (sound portion)
		File audioFile = new File("src/1-01 Title.wav");
		Clip sound1 = AudioSystem.getClip();
		sound1.open(AudioSystem.getAudioInputStream(audioFile));
		
		// Plays the music
		sound1.start();

		// Sets font for Press A Prompt
		c.setFont(new Font("Courier New", Font.ITALIC, 30));
		
		// Press A to Start Prompt
		// Blinks prompt 100 times
		for (int z = 0; z < 100; z++) {
			c.setColor(Color.BLACK);
			c.drawString("Press A to Start", halfScreenWidth-165, halfScreenHeight+400); 
			Thread.sleep(500);
			c.setColor(Color.WHITE);
			c.fillRect(1920/2-165, 930, 290, 25); 
			Thread.sleep(300);
		}
		
	}

}
