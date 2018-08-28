package application;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import utils.PDIClass;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

public class SampleController {

	//LIMIARIARIZAÇÃO
	@FXML Slider slider;
	
	@FXML ImageView imageView1;
	@FXML ImageView imageView2;
	@FXML ImageView imageViewResultado;
	
	@FXML Label lblR;
	@FXML Label lblG;
	@FXML Label lblB;
	
	@FXML TextField txtPercR;
	@FXML TextField txtPercG;
	@FXML TextField txtPercB;
	
	private File file;
	private Image img;
	private Image img1;
	private Image img2;
	private Image imgResultado;
	
	
	@FXML
	public void abreImagem1() {
		file = selectImagem();
		if (file != null) {
			img = new Image(file.toURI().toString());
			
			// seta imageView 1
			imageView1.setImage(img);
			imageView1.setFitWidth(img.getWidth());
		}
	}
	
	@FXML
	public void abreImagem2() {
		file = selectImagem();
		if (file != null) {
			img = new Image(file.toURI().toString());
			
			// seta imageView 1
			imageView2.setImage(img);
			imageView2.setFitWidth(img.getWidth());
		}
	}
	
	
	private void atualizaImgResultado() {
		imageViewResultado.setImage(imgResultado);
		imageViewResultado.setFitWidth(imgResultado.getWidth());
		imageViewResultado.setFitHeight(imgResultado.getHeight());
	}
	
	
	@FXML
	private File selectImagem() {
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.getExtensionFilters().add(new FileChooser
									.ExtensionFilter(
										"Imagens", "*.jpg", "*.JPG", 
										"*.png", "*.PNG", 
										"*.gif", "*.GIF", 
										"*.bmp", "*.BMP"
									));
		
		fileChooser.setInitialDirectory(new File("C:\\Users\\tiago\\eclipse-workspace\\processamento-digital-de-imagens\\src\\imgs"));
		File imgSelect = fileChooser.showOpenDialog(null);
		
		try {
			if(imgSelect != null) {
				return imgSelect;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private void verificaCor(Image img, int x, int y){
		try {
			Color cor = img.getPixelReader().getColor(x-1, y-1);
			lblR.setText("R: "+(int) (cor.getRed()*255));
			lblG.setText("G: "+(int) (cor.getGreen()*255));
			lblB.setText("B: "+(int) (cor.getBlue()*255));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	@FXML
	public void rasterImg(MouseEvent evt) {
		ImageView iw = (ImageView)evt.getTarget();
		if (iw.getImage() != null) {
			verificaCor(iw.getImage(), (int)evt.getX(), (int)evt.getY());
		}
	}
	
	@FXML
	public void limpaLabel() {
		lblR.setText("R:");
		lblG.setText("G:");
		lblB.setText("B:");
	}
	
	
	@FXML
	public void tonsDeCinza() {
		imgResultado = PDIClass.tonsDeCinza(imgResultado, 0, 0, 0);
		atualizaImgResultado();
	}
	
	
	@FXML
	public void negativa() {
		imgResultado = PDIClass.tonsDeCinza(img1, 0, 0, 0);
		imgResultado = PDIClass.negativa(img1);
		atualizaImage3();
	}
	
	@FXML
	public void limiarizacao() {
		double valor = slider.getValue();
		valor = valor / 255;
		img1 = PDIClass.tonsDeCinza(img1,0,0,0);
		imgResultado = PDIClass.limiarizacao(img1, valor);
		atualizaImage3();
		
	}
	
	
	private void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msg);
		alert.showAndWait();
		
	}
	
	private void atualizaImage3() {
		imageViewResultado.setImage(imgResultado);
		imageViewResultado.setFitWidth(imgResultado.getWidth());
		imageViewResultado.setFitHeight(imgResultado.getHeight());	
	}
	
}
