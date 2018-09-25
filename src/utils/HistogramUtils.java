package utils;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class HistogramUtils {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void getGrafico(Image img, BarChart<String, Number> grafico) {
        CategoryAxis eixoX = new CategoryAxis();
        NumberAxis eixoY = new NumberAxis();
        eixoX.setLabel("Canal");
        eixoY.setLabel("valor");
        XYChart.Series vlr = new XYChart.Series();
        vlr.setName("Intensidade");
        int[] hist = histogramaUnico(img);
        for (int i = 0; i < hist.length; i++) {
            vlr.getData().add(new XYChart.Data(i + "", hist[i]));
        }
        grafico.getData().addAll(vlr);
    }

    public static int[] histograma(Image img, int canal) {
        int[] qt = new int [256];
        PixelReader pr = img.getPixelReader();
        int w = (int)img.getWidth();
        int h = (int)img.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (canal == 1)
                    qt[(int)(pr.getColor(i, j).getRed()*255)]++;
                else
                if (canal == 2)
                    qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
                else
                    qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
            }
        }
        return qt;
    }

    public static int[] histogramaUnico(Image img) {
        int[] qt = new int [256];
        PixelReader pr = img.getPixelReader();
        int w = (int)img.getWidth();
        int h = (int)img.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                qt[(int)(pr.getColor(i, j).getRed()*255)]++;
                qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
                qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
            }
        }
        return qt;
    }



}
