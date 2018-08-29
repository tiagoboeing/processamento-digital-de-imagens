package Entidades;

public class CorMedia {

	double R;
	double G;
	double B;
	double media;
	
	public static CorMedia novaCorMedia(double R, double G, double B, double media) {
		
		CorMedia cm = new CorMedia();
		cm.setR(R);
		cm.setG(G);
		cm.setB(B);
		cm.setMedia(media);
		
		return cm;
	}
	
	
	public double getR() {
		return R;
	}
	public void setR(double r) {
		R = r;
	}
	public double getG() {
		return G;
	}
	public void setG(double g) {
		G = g;
	}
	public double getB() {
		return B;
	}
	public void setB(double b) {
		B = b;
	}
	public double getMedia() {
		return media;
	}
	public void setMedia(double media) {
		this.media = media;
	}
	
}
