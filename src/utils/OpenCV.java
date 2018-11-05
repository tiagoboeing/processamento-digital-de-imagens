package utils;

import javafx.scene.image.Image;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class OpenCV {

    // CARREGA BIBLIOTECAS DO OPENCV
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static Image detectPatterns(File file, String classificador) {

        CascadeClassifier faceDetector = new CascadeClassifier("./src/classificadores/" + classificador);
        Mat image = Imgcodecs.imread(file.getAbsolutePath());

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

//        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
        }

        MatOfByte mtb = new MatOfByte();
        Imgcodecs.imencode(".png", image, mtb);

        Image img = new Image(new ByteArrayInputStream(mtb.toArray()));
        return img;
    }

    public static File tonsDeCinza(File file){

        try {
            File input = file;
            BufferedImage image = ImageIO.read(input);

            byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
            mat.put(0, 0, data);

            Mat mat1 = new Mat(image.getHeight(),image.getWidth(), CvType.CV_8UC1);
            Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);

            byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
            mat1.get(0, 0, data1);
            BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
            image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);

            File output = new File("./src/imgs/temp/grayscale.png");
            ImageIO.write(image1, "png", output);

            return new File("./src/imgs/temp/grayscale.png");

        } catch (IOException e) {
            System.out.println("Problema no tratamento do arquivo");
            return null;
        }
    }

    public static void canny(){
        try {
            String inputFile = "./src/imgs/temp/temp.png";
            String outputFile = "./src/imgs/temp/canny.png";

            Mat matImgDst = new Mat();
            Mat matImgSrc = Imgcodecs.imread(inputFile);

            Imgproc.Canny(matImgSrc, matImgDst, 10, 100);
            Imgcodecs.imwrite(outputFile, matImgDst);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void prewitt(){
        try {
            String inputFile = "./src/imgs/temp/temp.png";
            String outputFile = "./src/imgs/temp/prewitt.png";

            Mat matImgDst = new Mat();
            Mat matImgSrc = Imgcodecs.imread(inputFile);

            int kernelSize = 9;

            Mat kernel = new Mat(kernelSize,kernelSize, CvType.CV_32F) {
                {
                    put(0,0,-1);
                    put(0,1,0);
                    put(0,2,1);

                    put(1,0-1);
                    put(1,1,0);
                    put(1,2,1);

                    put(2,0,-1);
                    put(2,1,0);
                    put(2,2,1);
                }
            };

            Imgproc.filter2D(matImgSrc, matImgDst, -1, kernel);
            Imgcodecs.imwrite(outputFile, matImgDst);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sobel(){
        try {
            String inputFile = "./src/imgs/temp/temp.png";
            String outputFile = "./src/imgs/temp/sobel.png";

            Mat matImgDst = new Mat();
            Mat matImgSrc = Imgcodecs.imread(inputFile);

            Imgproc.Canny(matImgSrc, matImgDst, 10, 100);
            Imgcodecs.imwrite(outputFile, matImgDst);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
