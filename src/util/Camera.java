/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Carlos
 */
public class Camera {
    
    public void openCapturePhoto(String urlFXML, String urlStyle) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(urlFXML/*"/view/CapturePhoto.fxml"*/));

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(urlStyle/*"/style/bibliotecaStyle"*/);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("No se ha cargado la ventana.");
            e.printStackTrace();
        }
    } 
}
