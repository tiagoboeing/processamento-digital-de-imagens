package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Prova1 {

    public static Image moldura(Image img, double r, double g, double b, int largura) {

        int w = (int) img.getWidth();
        int h = (int) img.getHeight();

        WritableImage wi = new WritableImage(w, h);
        PixelReader pr = img.getPixelReader();
        PixelWriter pw = wi.getPixelWriter();

        Color corMoldura = new Color(r, g, b, 1);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                Color oldCor = pr.getColor(i, j);
                if (i < largura || i >= w - largura && i < w) {
                    pw.setColor(i, j, corMoldura);
                } else {
                    pw.setColor(i, j, oldCor);
                }
                if (j < largura || j >= h - largura && j < h) {
                    pw.setColor(i, j, corMoldura);
                }

            }
        }
        return wi;
    }

    public static Image divideInverteTonsCinza(Image image) {

        try{
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            int divAltura = Math.round(height / 2);

            // imagem final
            PixelReader pr = image.getPixelReader();
            WritableImage wi = new WritableImage(width, height);
            PixelWriter pw = wi.getPixelWriter();

            // aplica efeitos
            Image negativa = PDIClass.negativa(image);
            Image tonsDeCinza = PDIClass.tonsDeCinza(image, 0, 0, 0);

            // imagem negativa
            PixelReader prNegativa = negativa.getPixelReader();
            WritableImage wiNegativa = new WritableImage(width, height);
            PixelWriter pwNegativa = wiNegativa.getPixelWriter();

            // imagem tons de cinza
            PixelReader prTonsDeCinza = tonsDeCinza.getPixelReader();
            WritableImage wiTonsDeCinza = new WritableImage(width, height);
            PixelWriter pwTonsDeCinza = wiTonsDeCinza.getPixelWriter();

            // superior
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < divAltura; j++) {
                    Color cor = prNegativa.getColor(i, j);
                    pw.setColor(i, j, cor);
                }
            }

            // inferior
            for (int i = 0; i < width; i++) {
                for (int j = divAltura; j < height; j++) {
                    Color oldCor = prTonsDeCinza.getColor(i, j);
                    pw.setColor(i, j, oldCor);
                }
            }

            return wi;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String identificaFormas(Image image){

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage wi = new WritableImage(width, height);
        PixelReader pr = image.getPixelReader();
        PixelWriter pw = wi.getPixelWriter();

        String formaFinal = "";

        for (int altura = 0; altura < width; altura++) {
            for (int largura = 0; largura < height; largura++) {

                // procura primeiro pixel preto que encontrar
                Color cor = pr.getColor(largura, altura);
                if(cor.getRed() == 0 && cor.getGreen() == 0 && cor.getBlue() == 0 && cor.getOpacity() == 1){

                    // procura cor do vizinho na vertical
                    Color abaixo = pr.getColor(largura, altura+1);
                    Color direita = pr.getColor(largura+1, altura);

                    if((abaixo.getRed() == 0 && abaixo.getGreen() == 0 && abaixo.getBlue() == 0) && (direita.getRed() == 0 && direita.getGreen() == 0 && direita.getBlue() == 0)){
                        System.out.println("Quadrado");
                        formaFinal = "Quadrado";
                    } else {
                        System.out.println("Circulo");
                        formaFinal = "Circulo";
                    }

                }

            }
        }

        return formaFinal;

    }

}
