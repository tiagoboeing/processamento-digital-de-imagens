package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PDIClass {
	
	public static Image tonsDeCinza(Image image) {
		
		try {
			
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(width, height);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					Color previousColor = pr.getColor(i, j);
					double media = (previousColor.getRed() + 
									previousColor.getGreen() + 
									previousColor.getBlue() 
									/ 3);
					
					Color newColor = new Color(media, media, media, previousColor.getOpacity());
					pw.setColor(i, j, newColor);
				}
			}
			
			return wi;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

}
