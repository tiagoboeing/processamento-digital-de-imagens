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

	// calcula media de uma cor
	// return -> double	
	public static Double mediaCores(double valorR, double valorG, double valorB) {
		return (valorR + valorG + valorB) /3;
	}

	// divide a imagem em quadrantes
	public static Image dividirInverter(Image img) {

		int largura = (int) img.getWidth();
		int altura = (int) img.getHeight();
		WritableImage wi = new WritableImage(largura, altura);
		PixelReader pr = img.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();

		int divAltura = Math.round(altura / 2);
		int divLargura = Math.round(largura / 2);

		// primeiro quadrante
		for(int i = 0; i < divLargura; i++){
			for(int j = 0; j < divAltura; j++){
                Color c = new Color(1, 0, 1, 1);
				Color oldCor = pr.getColor(largura - 1 - i, altura - 1 - j);
				pw.setColor(i, j, c);
			}
		}

        // segundo quadrante
        for(int i = divLargura; i < largura; i++){
            for(int j = 0; j < divAltura; j++){
                Color c = new Color(0.2, 0, 1, 1);
                Color oldCor = pr.getColor(i, j);
                pw.setColor(i, j, c);
            }
        }

        // quarto quadrante
        for(int i = 0; i < divLargura; i++){
            for(int j = divAltura; j < altura; j++){
                Color c = new Color(0.5, 1, 0, 1);
                Color oldCor = pr.getColor(largura - 1 - i, altura - 1 - j);
                pw.setColor(i, j, c);
            }
        }


        // quarto quadrante
        for(int i = divLargura; i < largura; i++){
            for(int j = divAltura; j < altura; j++){
                Color c = new Color(1, 1, 0, 1);
                Color oldCor = pr.getColor(i, j);
                pw.setColor(i, j, c);
            }
        }


//		for (int i = 0; i < divLargura; i++) {
//			for (int j = 0; j < divAltura; j++) {
//				Color oldCor = pr.getColor(i, j);
//				pw.setColor(i, j, oldCor);
//			}
//		}

//		for (int i = 0; i < divLargura; i++) {
//			for (int j = divAltura; j < altura; j++) {
//				Color oldCor = pr.getColor(largura - 1 - i, altura - 1 - j);
//				pw.setColor(i, j, oldCor);
//			}
//		}
		return wi;
	}

}
