/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import dao.BookDAO_impl;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Book;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class AddBookController implements Initializable {

    /**
     * Muesta la imagen de la carátula del libro.
     */
    @FXML
    private ImageView imgCover;
    /**
     * Campo para introducir el ISBN del libro.
     */
    @FXML
    private JFXTextField txtIsbn;
    /**
     * Campo para introducir el titulo del libro.
     */
    @FXML
    private JFXTextField txtTitle;
    /**
     * Campo para introducir el autor del libro.
     */
    @FXML
    private JFXTextField txtAuthor;
    /**
     * Campo para introducir la editorial del libro.
     */
    @FXML
    private JFXTextField txtPublisher;
    /**
     * Campo para introducir el año de la edición del libro.
     */
    @FXML
    private JFXTextField txtYearEdition;
    /**
     * Seleccionable para elegir el género del libro.
     */
    @FXML
    private JFXComboBox<String> cmbKind;

    /**
     * Crea un objeto Alert.
     */
    Alert alert;
    /**
     * Almacena la ruta de la foto.
     */
    private String routePhoto;
    /**
     * Muestra mensaje de error al introducir mal los datos en el campo.
     */
    @FXML
    private Label lblIsbn;
    /**
     * Muestra mensaje de error al introducir mal los datos en el campo.
     */
    @FXML
    private Label lblTitulo;
    /**
     * Muestra mensaje de error al introducir mal los datos en el campo.
     */
    @FXML
    private Label lblAutor;
    /**
     * Muestra mensaje de error al introducir mal los datos en el campo.
     */
    @FXML
    private Label lblEditorial;
    /**
     * Muestra mensaje de error al introducir mal los datos en el campo.
     */
    @FXML
    private Label lblAnioEdicion;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarGeneroLibros();
        requiredField(txtIsbn, "Campo obligatorio");
        requiredField(txtTitle, "Campo obligatorio");
        requiredField(txtAuthor, "Campo obligatorio");
        requiredField(txtYearEdition, "Campo obligatorio");
        
    }    

    /**
     * Selecciona la imagen desde un fichero.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerAddImage(MouseEvent event) {
        seleccionarCaratula();
    }

    /**
     * Limpia los campos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerCancel(ActionEvent event) {
        limpiarCampos();
    }

    /**
     * Almacena los datos en la BD.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerSave(ActionEvent event) {
        añadirLibro();
    }
    
    /**
     * Método para seleccionar la foto desde un cuadro de dialogo.
     */
    private void seleccionarCaratula() {
        FileChooser fileChooser = new FileChooser();
        // añadir extensiones
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("imagen", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            // imagen fuera del proyecto
            imgCover.setImage(new Image("file:///" + file.getAbsolutePath()));
            routePhoto = file.getAbsolutePath();
            System.out.println("probando la ruta " + routePhoto);
        }
    }
    
    /**
     * Limpia los campos.
     */
    private void limpiarCampos(){
        txtIsbn.clear();
        txtTitle.clear();
        txtAuthor.clear();
        txtPublisher.clear();
        txtYearEdition.clear();
        cmbKind.getPromptText();
        
        imgCover.setImage(new Image("image/noPhotoBook.png"));
    }
    
    /**
     * Añade un libro a la base de datos.
     */
    private void añadirLibro(){
        boolean result = false;

        int isbn = Integer.parseInt(txtIsbn.getText().trim());
        String title = txtTitle.getText().trim();
        String author = txtAuthor.getText().trim();
        String publisher = txtPublisher.getText().trim();
        int year_edition = Integer.parseInt(txtYearEdition.getText().trim());
        String kind = cmbKind.getValue();
        
        System.out.println("Añadiendo ruta " + routePhoto);

        Book book = new Book(isbn, title, author, publisher, year_edition, kind, routePhoto);
        BookDAO_impl bookDAO = new BookDAO_impl();

        result = bookDAO.add(book);

        System.out.println("Probando ver tabla " + new BibliotecaController().tablaSocio());

        if (result == true) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("NUEVO LIBRO.");
            alert.setHeaderText("Los datos del LIBRO se han guardado.");

            ButtonType btOK = new ButtonType("OK");

            alert.getButtonTypes().setAll(btOK);
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.get() == btOK) {
                limpiarCampos();
            }    
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NUEVO SOCIO.");
            alert.setHeaderText("¡ERROR al insertar los datos!.");
        }
    }
    
    /**
     * Llema el seleccionable de géneros.
     */
    public void llenarGeneroLibros(){
        ObservableList<String> listBook = FXCollections.observableArrayList("Thriller",
                "Romantica",
                "Aventura",
                "Terror",
                "Ciencia Ficción",
                "Infantil",
                "Biográfica",
                "Politica",
                "Varios");
        cmbKind.setItems(listBook);
    }
    
    /**
     * Valida la entrada de datos al campo del ISBN.
     * Restringe la entrada de datos no numéricos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerValidarISBN(KeyEvent event) {
        String regex = "^(97(8|9))?\\d{9}(\\d|X)$";
        String isbn = txtIsbn.getText() + event.getCharacter();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(isbn);
        boolean esBueno = matcher.matches();

        if (esBueno) {
            lblIsbn.setText("ISBN valido");
            lblIsbn.setStyle("-fx-text-fill: #4059a9");
        } else {
            lblIsbn.setText("ISBN no valido");
            lblIsbn.setStyle("-fx-text-fill: #D34336");
        }
        if (txtIsbn.getText().equals("")) {
            lblIsbn.setText("");
        }
    }
    /**
     * Valida la entrada de datos al campo del ISBN.
     * Restringe la entrada de datos no numéricos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerValidarEdicion(KeyEvent event) {
        String regex = "^\\d{4}$";
        String anioEdicion = txtYearEdition.getText() + event.getCharacter();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(anioEdicion);
        boolean esBueno = matcher.matches();

        if (esBueno) {
            lblAnioEdicion.setText("Año edición valido");
            lblAnioEdicion.setStyle("-fx-text-fill: #4059a9");
        } else {
            lblAnioEdicion.setText("Año edición no valido");
            lblAnioEdicion.setStyle("-fx-text-fill: #D34336");
        }
        if (txtYearEdition.getText().equals("")) {
            lblAnioEdicion.setText("");
        }
    }
    
    /**
     * Requerimos la entrada de datos en el campo.
     * @param textField elemento que requiere la introducción de datos.
     * @param message mensaje que queremo mostrar como aviso.
     */
    private void requiredField(JFXTextField textField, String message) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        textField.getValidators().add(validator);
        validator.setMessage(message);
        
        validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.WARNING).size("1em").styleClass("error").build());
        
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                textField.validate();
            }
        });
    }
    
}
