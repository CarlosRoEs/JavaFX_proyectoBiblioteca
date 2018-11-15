/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author Carlos
 */
public class Biblioteca extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Biblioteca.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/style/bibliotecaStyle");
        
        stage.setOnCloseRequest(event -> {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setHeaderText("¿Quieres salir de la aplilcación?.");
            alerta.setContentText("Esta operación cerrará todas las ventanas abiertas del programa.");

            ButtonType btOk = new ButtonType("Ok");
            ButtonType btCancelar = new ButtonType("Cancelar");

            alerta.getButtonTypes().setAll(btOk, btCancelar);
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.get() == btOk) {
//                Cerrar Aplicación.
                System.exit(0);
            } else {
                event.consume();
            }
        });

        stage.setScene(scene);
        stage.setTitle("GESTIÓN DE BIBLIOTECAS");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
