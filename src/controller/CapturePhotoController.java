/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import model.Member;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class CapturePhotoController implements Initializable {
    
    /**
     * Imagen del Socio.
     */
    @FXML
    private ImageView imgFoto;

    /**
     * Creamos objeto.
     */
    private Webcam webcam;
    /**
     * variable para comprobar si es capturada.
     */
    private boolean isCaptured = false;
    
    String routePhoto;
    /**
     * Comprueba si es capturado y la cierra
     */
    private void captureWebCam() {

        isCaptured = true;
        if(isCaptured){
            webcam.close();
        }
    }

    /**
     * Almacena la foto en el fichero que especifique el usuario.
     */
    private void savePhoto() {
        isCaptured = true;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagen (*.png)", "*.png"));
        fileChooser.setTitle("Guardar Imagen");
        File file = fileChooser.showSaveDialog(imgFoto.getScene().getWindow());
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imgFoto.getImage(), null), "PNG", file);
                
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Recarga la camara.
     */
    private void reloadWebCam() {

        isCaptured = false;
        webcam.open();
        new ThreadCamera().start();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCamera();
    }
    
    /**
     * Inicia la camara.
     */
    private void initCamera(){
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();
        new ThreadCamera().start();
    }

    /**
     * Reinicia la cámara.
     * @param event se le pasa como parametro la acción que realiza. 
     */
    @FXML
    private void handlerReloadWebCam(ActionEvent event) {
        reloadWebCam();
    }

    /**
     * Captura la imagen del socio.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerTakepictureWebCam(ActionEvent event) {
        captureWebCam();
    }

    /**
     * Guarda la imagen del socio.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerSave(ActionEvent event) {
        savePhoto();
    }
    
    /**
     * Clase interna que maneja el hilo.
     */
    private class ThreadCamera extends Thread {
        @Override
        public void run() {
            while(!isCaptured) {
                try {
                    imgFoto.setImage(SwingFXUtils.toFXImage(webcam.getImage(), null));
                    sleep(30);
                } catch (InterruptedException ex) {
                    System.out.println("Error al dormir el hilo. " + ex.getMessage());
                }
            }
        }
    }
    
}
    

