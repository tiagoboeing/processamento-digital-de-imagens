package application;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;

public class SampleController {
	
	@FXML ImageView imageView1;
	@FXML ImageView imageView2;
	@FXML ImageView imageViewResultado;
	
	@FXML Label lblR;
	@FXML Label lblG;
	@FXML Label lblB;
	
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
	
}
