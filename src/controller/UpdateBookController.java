/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.BookDAO_impl;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Book;
import util.Camera;

/**
 *
 * @author Carlos
 */
public class UpdateBookController implements Initializable{

    /**
     * Etiqueta que muestra el nombre del panel.
     */
    @FXML
    private Label lblPaneMember;
    /**
     * Muestra la imagen de la carátula.
     */
    @FXML
    private ImageView imgCover;
    /**
     * Campo para introducir el isbn del libro.
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
     * Campo para introducir el año de edición del libro.
     */
    @FXML
    private JFXTextField txtYearEdition;
    /**
     * Desplegable para seleccionar el género.
     */
    @FXML
    private JFXComboBox<String> cmbKind;

    /**
     * Crea un objeto alert
     * 
     */
    Alert alert;
    /**
     * Almacena la ruta de la carátula.
     */
    private String routePhoto;
    /**
     * Almacena el id del libro.
     */
    private int idBook;
    @FXML
    private Label lblIsbn;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblAutor;
    @FXML
    private Label lblEditorial;
    @FXML
    private Label lblAnioEdicion;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillComboKindBook();
    }

    /**
     * Añade la imagen seleccionada.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerAddImage(MouseEvent event) {
        selectPhoto();
    }

    /**
     * Limpia los campos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerCancel(ActionEvent event) {
        cleanFields();
    }

    /**
     * Guarda los datos del libro en la BD.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerSave(ActionEvent event) {
        updateBook();
        
    }
    
    /**
     * Abre la ventana de la webCam.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerOpenCamera(ActionEvent event) {
        Camera camera = new Camera();
        camera.openCapturePhoto("/view/CapturePhoto.fxml", "/style/bibliotecaStyle");
    }
    /**
     * Llena el desplegable con los géneros de los libros.
     */
    public void fillComboKindBook(){
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
     * Seleccionar la foto desde un cuadro de dialogo.
     */
    private void selectPhoto() {
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
    
    private void cleanFields(){
        txtIsbn.clear();
        txtTitle.clear();
        txtAuthor.clear();
        txtPublisher.clear();
        txtYearEdition.clear();
        cmbKind.getPromptText();
        lblAnioEdicion.setText("");
        lblAutor.setText("");
        lblEditorial.setText("");
        lblIsbn.setText("");
        lblTitulo.setText("");
        imgCover.setImage(new Image("image/noPhotoBook.png"));
    }
    
    /**
     * Muestra los datos del libro
     * @param book se le pasa como parametro un objeto libro.
     */
    public void showBook(Book book){
        idBook = book.getIdBook();
        txtIsbn.setText(String.valueOf(book.getIsbn()));
        txtTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthor());
        txtPublisher.setText(book.getPublisher());
        txtYearEdition.setText(String.valueOf(book.getYear_edition()));
        cmbKind.setValue(String.valueOf(book.getKind()));
        imgCover.setImage(new Image("file:///" + book.getCover()));
        routePhoto = book.getCover();
        String cover = book.getCover();
        System.out.println("######### " + cover);
    }
    
    /**
     * Actualiza la tabla libro en la BD.
     */
    private void updateBook(){
        boolean result = false;
        
        String isbn = txtIsbn.getText().trim();
        String title = txtTitle.getText().trim();
        String author = txtAuthor.getText().trim();
        String publisher = txtPublisher.getText().trim();
        int year_edition = Integer.parseInt(txtYearEdition.getText().trim());
        String kind = cmbKind.getValue();
        
        Book book = new Book(idBook, isbn, title, author, publisher, year_edition, kind, routePhoto);
        BookDAO_impl bookDAO = new BookDAO_impl();
        
        result = bookDAO.update(book);
        if(result == true){
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Actualizar Libro.");
            alert.setContentText("El Libro ha sido actualizado.");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Actualizar Libro.");
            alert.setHeaderText("¡ERROR al actualizar los datos!.");
            alert.showAndWait();
        }
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
}
