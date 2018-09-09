package application;

import java.io.File;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javafx.scene.control.TitledPane;
import utils.Adicao;
import utils.CorAtualUtils;
import utils.PDIClass;
import utils.ReducaoRuido;
import utils.Subtracao;


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
	
	@FXML TextField txtPosX;
	@FXML TextField txtPosY;
	
	// Adi��o e Subtra��o	
	@FXML TextField txtPorcImg1;
	@FXML TextField txtPorcImg2;
	
	@FXML TitledPane painelCor;
	
	// radio buttons
	@FXML RadioButton radioX;
	@FXML RadioButton radioCruz;
	@FXML RadioButton radio3x3;
	
	// vars auxiliares
	private File file;
	
	private Image img1;
	private Image img2;
	private Image imgResultado;
	
	// LIMIARIARIZA��O
	@FXML Slider slider;
	
	@FXML
	public void reducaoRuido() {
		
		WritableImage wi = new WritableImage((int)img1.getWidth(), (int)img1.getHeight());
		PixelWriter pw = wi.getPixelWriter();
		
		// vizinhan�a em cruz
		if(radio3x3.isSelected() == true) {

			for(int largura = 1; largura < (int)img1.getWidth()-1; largura++) {
				for(int altura = 1; altura < (int)img1.getHeight()-1; altura++) {
					
					ArrayList<Double> medianas = ReducaoRuido.reducao3x3(img1, largura, altura);
					
					Color corNova = new Color(medianas.get(0),
											medianas.get(1),
											medianas.get(2),
											1);
					
					pw.setColor(largura, altura, corNova);
					
				}
			}		
			
			imageViewResultado.setImage(wi);
			imageViewResultado.setFitWidth(wi.getWidth());
			
		} else if (radioX.isSelected() == true) {
			
			for(int largura = 1; largura < (int)img1.getWidth()-1; largura++) {
				for(int altura = 1; altura < (int)img1.getHeight()-1; altura++) {
					
					ArrayList<Double> medianas = ReducaoRuido.reducaoEmX(img1, largura, altura);
					
					Color corNova = new Color(medianas.get(0),
											medianas.get(1),
											medianas.get(2),
											1);
					
					pw.setColor(largura, altura, corNova);
					
				}
			}		
			
			imageViewResultado.setImage(wi);
			imageViewResultado.setFitWidth(wi.getWidth());
			
		} else if (radioCruz.isSelected() == true) {

			for(int largura = 1; largura < (int)img1.getWidth()-1; largura++) {
				for(int altura = 1; altura < (int)img1.getHeight()-1; altura++) {
					
					ArrayList<Double> medianas = ReducaoRuido.reducaoEmCruz(img1, largura, altura);
					
					Color corNova = new Color(medianas.get(0),
											medianas.get(1),
											medianas.get(2),
											1);
					
					pw.setColor(largura, altura, corNova);
					
				}
			}		
			
			imageViewResultado.setImage(wi);
			imageViewResultado.setFitWidth(wi.getWidth());
			
		} else {
			exibeMsg("Selecione um tipo de redu��o", "Aten��o", "� necess�rio selecionar uma t�cnica de aplica��o da redu��o de ru�do.", AlertType.WARNING);
		}

	}
	
	// sele��o de imagem
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
			exibeMsg("Salvar imagem.", "N�o � possivel salvar imagem.","N�o h� nenhuma imagem modificada.", AlertType.ERROR);
			
		}
	}
	
	private void verificaCor(Image img, int x, int y){
		try {
			Color cor = img.getPixelReader().getColor(x, y);
			lblR.setText("R: "+(int) (cor.getRed()*255));
			lblG.setText("G: "+(int) (cor.getGreen()*255));
			lblB.setText("B: "+(int) (cor.getBlue()*255));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// ao clicar em mostrar cor da posi��o selecionada
	public void mostraCorTela() {
		
		if(!txtPosX.getText().equals("") && !txtPosX.getText().equals("")) {
			
			Integer eixoX = Integer.parseInt(txtPosX.getText());
			Integer eixoY = Integer.parseInt(txtPosY.getText());
						
			exibeMsg("Cor atual", "Dados da cor selecionada", CorAtualUtils.RGBdaPosicao(img1, eixoX, eixoY), AlertType.CONFIRMATION);
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
	public void getPosition(MouseEvent evt) {
		ImageView iw = (ImageView)evt.getTarget();
		if (iw.getImage() != null) {
			
			painelCor.setExpanded(true);
			
			txtPosX.setText((int)evt.getX()+"");
			txtPosY.setText((int)evt.getY()+"");
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
				exibeMsg("Escala de Cinza", "Aviso", "Valor � menor que 100, foi igual a : " + (pcR+pcG+pcB), AlertType.WARNING);
			}
			
			imgResultado = PDIClass.tonsDeCinza(img1, pcR, pcG, pcB);
			atualizaImageResultado();
			
		}else {
			exibeMsg("Escala de Cinza", "Erro", "Somat�rio maior que 100", AlertType.ERROR);
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
	
	@FXML
	public void adicao() {
		
		String porcentagemImg1 = txtPorcImg1.getText();
		String porcentagemImg2 = txtPorcImg2.getText();;
		
		if(txtPorcImg1.getText().isEmpty()) { porcentagemImg1 = "50"; }
		if(txtPorcImg2.getText().isEmpty()) { porcentagemImg2 = "50"; }
		
		if((Double.parseDouble(porcentagemImg1) + Double.parseDouble(porcentagemImg2)) <= 100) {
			imgResultado = Adicao.adicao(img1, img2, porcentagemImg1, porcentagemImg2);
			atualizaImageResultado();
		} else {
			exibeMsg("Valores excedem 100%", "Erro", "Soma das duas porcentagem � maior que 100", AlertType.ERROR);
		}
		
	}

	@FXML
	public void subtracao() {
		imgResultado = Subtracao.subtracao(img1, img2);
		atualizaImageResultado();
	}
	
	private void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
		Alert alert = new Alert(tipo);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	
}
