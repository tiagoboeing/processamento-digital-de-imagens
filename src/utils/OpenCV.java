package utils;

import javafx.scene.image.Image;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import java.io.ByteArrayInputStream;
import java.io.File;

public class OpenCV {

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

}
