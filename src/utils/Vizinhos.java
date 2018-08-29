package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Entidades.CorMedia;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Vizinhos {
	
	// vizinho[0] = [R, G, B, media]
	static ArrayList<CorMedia> vizinhos = new ArrayList<CorMedia>();


	public static void retornaVizinhos(Image image, double posicaoX, double posicaoY) {
		
		try {
			
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(width, height);
			PixelWriter pw = wi.getPixelWriter();
			
			// largura X
			for(int contX = 0; contX < width; contX++) {
				
				// altura Y
				for(int contY = 0; contY < height; contY++) {

					// checa se está no pixel informado
					if(contX == posicaoX && contY == posicaoY) {
										
						// percorre todos os vizinhos
						for(int z = 0; z < 9; z++) {
							
							if(z == 0) {
								Color corVizinho = pr.getColor(contX-1, contY+1);
								vizinhos.add(CorMedia.novaCorMedia(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue(), PDIClass.mediaCores(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue())));
							}
							if(z == 1) { 
								Color corVizinho = pr.getColor(contX, contY-1);
								vizinhos.add(CorMedia.novaCorMedia(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue(), PDIClass.mediaCores(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue())));
							}
							if(z == 2) { 
								Color corVizinho = pr.getColor(contX+1, contY+1);
								vizinhos.add(CorMedia.novaCorMedia(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue(), PDIClass.mediaCores(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue())));
							}
							if(z == 3) { 
								Color corVizinho = pr.getColor(contX-1, contY);
								vizinhos.add(CorMedia.novaCorMedia(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue(), PDIClass.mediaCores(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue())));
							}
							if(z == 4) { 
								Color corVizinho = pr.getColor(contX, contY);
								vizinhos.add(CorMedia.novaCorMedia(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue(), PDIClass.mediaCores(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue())));
							}
							if(z == 5) { 
								Color corVizinho = pr.getColor(contX+1, contY);
								vizinhos.add(CorMedia.novaCorMedia(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue(), PDIClass.mediaCores(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue())));
							}
							if(z == 6) { 
								Color corVizinho = pr.getColor(contX-1, contY-1);
								vizinhos.add(CorMedia.novaCorMedia(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue(), PDIClass.mediaCores(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue())));
							}
							if(z == 7) { 
								Color corVizinho = pr.getColor(contX, contY-1);
								vizinhos.add(CorMedia.novaCorMedia(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue(), PDIClass.mediaCores(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue())));
							}
							if(z == 8) { 
								Color corVizinho = pr.getColor(contX+1, contY-1);
								vizinhos.add(CorMedia.novaCorMedia(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue(), PDIClass.mediaCores(corVizinho.getRed(), corVizinho.getGreen(), corVizinho.getBlue())));
							}	
						}
					}
	
				}
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}		
	}
	
//	public static void ordenaArrayList() {
//		Collections.sort (vizinhos, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                CorMedia p1 = (CorMedia) o1;
//                CorMedia p2 = (CorMedia) o2;
//                return p1.inicio < p2.inicio ? -1 : (p1.inicio > p2.inicio ? +1 : 0);
//            }
//        });
//	}
	
}
