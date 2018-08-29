package application;

import java.io.File;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import utils.PDIClass;
import utils.Vizinhos;


public class SampleController {

	
	@FXML ImageView imageView1;
	@FXML ImageView imageView2;
	@FXML ImageView imageViewResultado;
	
	@FXML Label lblR;
	@FXML Label lblG;
	@FXML Label lblB;
	
	@FXML TextField txtPercR;
	@FXML TextField txtPercG;
	@FXML TextField txtPercB;
	
	// vars auxiliares
	private File file;
	// private Image img;
	
	private Image img1;
	private Image img2;
	private Image imgResultado;
	
	// LIMIARIARIZAÇÃO
	@FXML Slider slider;
	
	@FXML
	public void vizinhos() {
		Vizinhos.retornaVizinhos(img1, 200, 1200);
	}
	
	// seleção de imagem
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
		
		fileChooser.setInitialDirectory(new File("C:/Users/tiago/eclipse-workspace/processamento-digital-de-imagens/src/imgs"));
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
	
	// abrir arquivo 1
	@FXML
	public void abreImagem1() {
		file = selectImagem();
		if (file != null) {
			img1 = new Image(file.toURI().toString());
			
			// seta imageView 1
			imageView1.setImage(img1);
			imageView1.setFitWidth(img1.getWidth());
		}
	}
	
	// abrir arquivo 2
	@FXML
	public void abreImagem2() {
		file = selectImagem();
		if (file != null) {
			img2 = new Image(file.toURI().toString());
			
			// seta imageView 1
			imageView2.setImage(img2);
			imageView2.setFitWidth(img2.getWidth());
		}
	}
	
	
	private void atualizaImageResultado() {
		imageViewResultado.setImage(imgResultado);
		imageViewResultado.setFitWidth(imgResultado.getWidth());
		//imageViewResultado.setFitHeight(imgResultado.getHeight());	
	}
	
	
	@FXML
	public void salvar() {
		if(imgResultado != null) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.png"));
			fileChooser.setInitialDirectory(new File("C:/Users/tiago/eclipse-workspace/processamento-digital-de-imagens/src/imgs"));
			File file = fileChooser.showSaveDialog(null);
			
			if(file != null) {
				BufferedImage bImg = SwingFXUtils.fromFXImage(imgResultado, null);
				try {
					ImageIO.write(bImg, "PNG", file);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}else {
			exibeMsg("Salvar imagem.", "Não é possivel salvar imagem.","Não há nenhuma imagem modificada.", AlertType.ERROR);
			
		}
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
	
	
	// retorna vizinhos de um pixel
	private void vizinhos(Image img, int x, int y){
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
		imgResultado = PDIClass.tonsDeCinza(img1, 0, 0, 0);
		atualizaImageResultado();
	}
	
	@FXML
	public void tonsDeCinzaPonderada() {
		
		int pcR = Integer.parseInt(txtPercR.getText());
		int pcG = Integer.parseInt(txtPercG.getText());
		int pcB = Integer.parseInt(txtPercB.getText());

		if(pcR + pcG + pcB <= 100) {
			
			if(pcR + pcG + pcB < 100) {
				exibeMsg("Escala de Cinza", "Aviso", "Valor é menor que 100, foi igual a : " + (pcR+pcG+pcB), AlertType.WARNING);
			}
			
			imgResultado = PDIClass.tonsDeCinza(img1, pcR, pcG, pcB);
			atualizaImageResultado();
			
		}else {
			exibeMsg("Escala de Cinza", "Erro", "Somatório maior que 100", AlertType.ERROR);
		}
	}

	@FXML
	public void negativa() {
		imgResultado = PDIClass.tonsDeCinza(img1, 0, 0, 0);
		imgResultado = PDIClass.negativa(img1);
		atualizaImageResultado();
	}
	
	@FXML
	public void limiarizacao() {
		double valor = slider.getValue();
		valor = valor / 255;
		img1 = PDIClass.tonsDeCinza(img1,0,0,0);
		imgResultado = PDIClass.limiarizacao(img1,valor);
		atualizaImageResultado();		
	}
	
	
//	@FXML
//	public void limiarizacao() {
//		double valor = slider.getValue();
//		valor = valor / 255;
//		
//		img1 = PDIClass.tonsDeCinza(img1, 0, 0, 0);
//		imgResultado = PDIClass.limiarizacao(img1, valor);
//		atualizaImageResultado();
//	}
//	
	
	private void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msg);
		alert.showAndWait();
		
	}
	
	
}
