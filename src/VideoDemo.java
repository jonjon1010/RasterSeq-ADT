/*
 * CST 751 Homework 2
 * Name: Jonathan Nguyen 
 *
 * I got help with Natasha the TA at her office hours with writing the buildVideo() and sloMo(int factor) method. 
 * Initially I was not having a clear understanding in writing the VideoDemo file, but Natasha made it clear that
 * in order to writing the method for buildVideo() I had to add the element from frames[] into the video raster. 
 * This was done by using three for loops in this method. 
 * We would need five copies for the frames[0] in the beginning, then after one copy of frames[1] - frames[16]. 
 * After that we would need five more copies of frames[0]. For the sloMo(int factor), in order to slow down 
 * the video we would have to add a factor of all the elements that are currently in the sequence. This was 
 * done by calling video.start() in the beginning of this method and having a loop that went through the current 
 * elements in the sequence, then another loop to add factor - 1 into our sequence. This will than slow down 
 * the video. 
 * 
 */

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.uwm.cs351.RasterSeq;
import edu.uwm.cs351.Raster;
import edu.uwm.cs351.Pixel;

public class VideoDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Raster[] frames = new Raster[17];
	private RasterSeq video = new RasterSeq();
	private static final int FRAMERATE = 25;
	private static final int PIXELWIDTH = 50;
	private static final int X = 850;
	private static final int Y = 500;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				VideoDemo x = new VideoDemo();
				x.setSize(X, Y);
				x.setLocationRelativeTo(null);
				x.setVisible(true);
				x.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				
				x.createFrames();
				x.buildVideo();
				x.playVideo();
				x.sloMo(4);
				x.playVideo();
			}
		});
		
	}
	
	@SuppressWarnings("serial")
	public VideoDemo() {
		this.setContentPane(new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int width = PIXELWIDTH;
				if(video.hasCurrent())
					video.getCurrent().draw(g, width);
			}
		}
		);
	}
	
	/*
	 * 
	 * Create a video that is a RasterSeq
	 * The video should consist of 5 copies of frames[0]
	 * followed by 1 copy each of frames[1] through frames[16]
	 * And finally 5 more copies of frames[0]
	 * You should use loops so that you don't have too many lines of code
	 */
	public void buildVideo() {
		video = new RasterSeq();
		//TODO: Implement this
		for(int i = 0; i < 5; i++) {
			video.addBefore(frames[0]);
		}
		for(int i = 1; i < frames.length; i++) {
			video.addBefore(frames[i]);
		}
		for(int i = 0; i < 5; i++) {
			video.addBefore(frames[0]);
		}
	}
	
	/*
	 * Slow down a video by a factor
	 * You should accomplish this by adding factor - 1 copies of each frame
	 * For example, if the factor is 2, you are doubling each frame
	 * Note: for these purposes, a copy can be a reference to the same Raster
	 * Also note: this operation on a dynamic array is not that efficient. Think about it.
	 */
	public void sloMo(int factor) {
		if(factor < 1)
			throw new IllegalArgumentException();
		//TODO: Implement this
		video.start();
		for(int i = 0; i < video.size(); i++) {
			for(int j = 0; j < factor - 1; j++) {
				video.addBefore(video.getCurrent());
			}
			video.advance();
			if(video.atEnd()) {
				return;
			}
		}
	}
	
	public void playVideo() {
		video.start();
		while(video.hasCurrent()) {
			this.getContentPane().paint(this.getGraphics());
			try {
	            Thread.sleep(1000/FRAMERATE);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
			video.advance();
		}
	}
	
	public void createFrames() {
		for(int i=0; i<17; i++)
			frames[i] = new Raster(16, 9);
		for(int i=1; i<17; i++)
			frames[i].addPixel(new Pixel(i-1,8,Color.BLACK));
	}
}
