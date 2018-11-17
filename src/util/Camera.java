/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controller.BibliotecaController;
import controller.CapturePhotoController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Carlos
 */
public class Camera {
    
    BibliotecaController biblioteca = new BibliotecaController();
    
    public void openCapturePhoto(String urlFXML, String urlStyle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(urlFXML));
            Parent root = loader.load();
            //Parent root = FXMLLoader.load(getClass().getResource(urlFXML));

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(urlStyle);
            stage.setScene(scene);
            stage.show();

           
        } catch (IOException e) {
            System.out.println("No se ha cargado la ventana.");
            e.printStackTrace();
        }
    } 
}
