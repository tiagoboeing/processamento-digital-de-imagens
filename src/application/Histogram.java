package application;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;

public class Histogram {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void getGrafico(Image img, BarChart<String, Number> grafico){
		CategoryAxis eixoX = new CategoryAxis();
		NumberAxis eixoY = new NumberAxis();
	    eixoX.setLabel("Canal");       
	    eixoY.setLabel("valor");
	    
	    XYChart.Series vlr = new XYChart.Series();
	    vlr.setName("Intensidade");
	    int[] hist = histogramaUnico(img);
	    for (int i=0; i<hist.length; i++) {
	    	vlr.getData().add(new XYChart.Data(i+"", hist[i]/1000));
		}
	    grafico.getData().addAll(vlr);
	}

}
