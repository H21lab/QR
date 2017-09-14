import java.io.File;
import java.io.IOException;

import com.google.zxing.common.*;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class QRwitter {

	
	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.err.println("Usage: java -jar QRwritter.jar \"Meno Priezvisko\" \"Firma\" \"1.1.2012 - 31.12.2012\"");
			return;
		}
		
		
		// Create QR image
		
		// try {
		String data = null;
		try {
			data = RSAEncoder.encode(args[0] + " " + args[1] + " " + args[2]);
			System.out.println(data);
			System.out.println(RSADecoder.decode(data));
		} catch (Exception e1) { 
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		// get a byte matrix for the data
		ByteMatrix matrix = null;
		int h = 256;
		int w = 256;
		com.google.zxing.Writer writer = new QRCodeWriter();
		try {
			matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
		} catch (com.google.zxing.WriterException e) {
			System.out.println(e.getMessage());
		}

		// change this path to match
		String filePath = args[1] + " " + args[0] + "_QR.png";
		filePath = filePath.replaceAll(" ", "_");
		File file = new File(filePath);
		try {

			// create buffered image to draw to
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			// Draw QR
			byte[][] array = matrix.getArray();
			// iterate through the matrix and draw the pixels to the image
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int grayValue = array[y][x] & 0xff;
					image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
				}
			}
			
			File outputfile = new File(file.getAbsolutePath());
			ImageIO.write(image, "png", outputfile);

			System.out.println("Printing to " + file.getAbsolutePath());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		// Create QR image
		// change this path to match
		
		h = 256;
		w = 768;
		
		filePath =  args[1] + " " + args[0] + ".png";
		filePath = filePath.replaceAll(" ", "_");
		file = new File(filePath);
		try {

			// create buffered image to draw to
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			// Draw QR
			byte[][] array = matrix.getArray();
			// iterate through the matrix and draw the pixels to the image
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w/3; x++) {
					int grayValue = array[y][x] & 0xff;
					image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
				}
			}
			
			// White fill
			for (int y = 0; y < h; y++) {
				for (int x = w/3; x < w; x++) {
					image.setRGB(x, y, 0xFFFFFF);
				}
			}
			
			// Draw Text
			Graphics2D g = image.createGraphics();
		    g.setColor(Color.black);
		    g.setFont(new Font( "Arial", Font.BOLD, 20 ));
		    g.drawString(args[0], 4*w/10, 4*h/10);
		    g.setFont(new Font( "Arial", Font.BOLD, 16 ));
		    g.drawString(args[1], 4*w/10, 5*h/10);
		    g.setFont(new Font( "Arial", Font.BOLD, 16 ));
		    g.drawString("Platnost: " + args[2], 4*w/10, 6*h/10);
		    

			File outputfile = new File(file.getAbsolutePath());
			ImageIO.write(image, "png", outputfile);

			System.out.println("Printing to " + file.getAbsolutePath());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	
	public static void main_CreateQR(String[] args) {
		// try {
		String data = new String("Test");
		// get a byte matrix for the data
		ByteMatrix matrix = null;
		int h = 256;
		int w = 256;
		com.google.zxing.Writer writer = new QRCodeWriter();
		try {
			matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
		} catch (com.google.zxing.WriterException e) {
			System.out.println(e.getMessage());
		}

		// Create image
		// change this path to match
		String filePath = ".\\out.png";
		File file = new File(filePath);
		try {

			// create buffered image to draw to
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			byte[][] array = matrix.getArray();
			// iterate through the matrix and draw the pixels to the image
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int grayValue = array[y][x] & 0xff;
					image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
				}
			}
		
			File outputfile = new File(file.getAbsolutePath());
			ImageIO.write(image, "png", outputfile);

			System.out.println("Printing to " + file.getAbsolutePath());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
