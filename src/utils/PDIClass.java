package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PDIClass {
	
	// preto e branco
	public static Image tonsDeCinza(Image image, double pcR, double pcG, double pcB) {
		
		try {
			
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(width, height);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					
					Color previousColor = pr.getColor(i, j);
					double media = ((previousColor.getRed() + 
									previousColor.getGreen() + 
									previousColor.getBlue()) 
									/ 3);
					
					if(pcR != 0 || pcG != 0 || pcB != 0 ) {
						media = (previousColor.getRed() * pcR
								+ previousColor.getGreen() * pcG
								+ previousColor.getBlue() * pcB)
								/100; //Media Ponderada do RGD
					}
					
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
	
	//Função de limiarização
	public static Image limiarizacao(Image imagem, double limiar) {
		try {
			int w = (int)imagem.getWidth(); //Largura
			int h = (int)imagem.getHeight(); //Altura
			
			PixelReader pr = imagem.getPixelReader(); //Com o pixelReader é possivel pegar as cores
			WritableImage wi = new WritableImage(w, h); //Serve para escrever na imagem
			PixelWriter pw = wi.getPixelWriter(); //Escrever o pixel. Utilizar o pw para gravar o que deseja
			
			for (int i=0; i<w; i++) {
				for (int j = 0; j < h; j++) {
					Color corAnterior = pr.getColor(i, j); //Consegue pegar a cor de um determinado pixel
					Color corNova;
					
					if(corAnterior.getRed() >= limiar) {
						corNova = new Color(1, 1, 1, corAnterior.getOpacity());
					}else {
						corNova = new Color(0, 0, 0, corAnterior.getOpacity());
					}
					pw.setColor(i, j, corNova);				
				}
			}
			
			return wi;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	//Função para converter imagem para negativa
	public static Image negativa(Image imagem) {
		try {
			int w = (int)imagem.getWidth(); //Largura
			int h = (int)imagem.getHeight(); //Altura
			
			PixelReader pr = imagem.getPixelReader(); //Com o pixelReader é possivel pegar as cores
			WritableImage wi = new WritableImage(w, h); //Serve para escrever na imagem
			PixelWriter pw = wi.getPixelWriter();//Escrever o pixel. Utilizar o pw para gravar o que deseja
			
			for (int i=0; i<w; i++) {
				for (int j=0; j<h; j++) {
					
					Color corAnterior = pr.getColor(i, j); //Consegue pegar a cor de um determinado pixel
					Color corNova;
					
					corNova = new Color(
								(1 - corAnterior.getRed()), 
								(1 - corAnterior.getGreen()),
								(1 - corAnterior.getBlue()),
								corAnterior.getOpacity()
							);
		
					pw.setColor(i, j, corNova);
							
										
				}
			}
			
			return wi;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	public static Image reducaoDeRuido(Image imagem) {
		try {
			int w = (int)imagem.getWidth(); //Largura
			int h = (int)imagem.getHeight(); //Altura
			
			PixelReader pr = imagem.getPixelReader(); //Com o pixelReader é possivel pegar as cores
			WritableImage wi = new WritableImage(w, h); //Serve para escrever na imagem
			PixelWriter pw = wi.getPixelWriter();//Escrever o pixel. Utilizar o pw para gravar o que deseja
			
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					
					Color corAnterior = pr.getColor(i, j); //Consegue pegar a cor de um determinado pixel
					Color corNova;
					
					corNova = new Color(
								(1 - corAnterior.getRed()), 
								(1 - corAnterior.getGreen()),
								(1 - corAnterior.getBlue()),
								corAnterior.getOpacity()
							);
		
					pw.setColor(i, j, corNova);
							
										
				}
			}
			
			return wi;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			
		}
	}
	
	// calcula media de uma cor
	// return -> double	
	public static Double mediaCores(double valorR, double valorG, double valorB) {
		return (valorR + valorG + valorB) /3;
	}
	
}
