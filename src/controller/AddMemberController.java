/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import dao.MemberDAO_impl;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Member;
import util.Camera;

/**
 * FXML Controller class
 * @author Carlos
 */
public class AddMemberController implements Initializable {

    /**
     * Campo para introducir el DNI.
     */
    @FXML
    private JFXTextField txtDni;
    /**
     * Campo para introducir el nombre del socio.
     */
    @FXML
    private JFXTextField txtFirstName;
    /**
     * Campo para introducir el apellido del socio.
     */
    @FXML
    private JFXTextField txtLastName;
    /**
     * Selecciona la fecha de nacimiento.
     */
    @FXML
    private JFXDatePicker dpBirthdate;
    /**
     * Campo para introducir el telefono.
     */
    @FXML
    private JFXTextField txtPhone;
    /**
     * Campo para introducir el movil.
     */
    @FXML
    private JFXTextField txtMobilePhone;
    /**
     * Campo para introducir el e-mail.
     */
    @FXML
    private JFXTextField txtEmail;
    /**
     * Campor para introducir la dirección.
     */
    @FXML
    private JFXTextField txtAddress;
    /**
     * Muestra la imagen.
     */
    @FXML
    private ImageView imgPhoto;
    /**
     * Nos va a mostrar un mensaje retroalimentación de la entrada del dni.
     */
    @FXML
    private Label lblDni;
    /**
     * Nos va a mostrar un mensaje retroalimentación de la entrada del nombre del socio.
     */
    @FXML
    private Label lblFirstName;
    /**
     * Nos va a mostrar un mensaje retroalimentación de la entrada del apellido del socio..
     */
    @FXML
    private Label lblLastName;
    /**
     * Nos va a mostrar un mensaje retroalimentación de la entrada del teléfono.
     */
    @FXML
    private Label lblPhone;
    /**
     * Nos va a mostrar un mensaje retroalimentación de la entrada del móvil.
     */
    @FXML
    private Label lblMobilePhone;
    /**
     * Nos va a mostrar un mensaje retroalimentación de la entrada del e-mail.
     */
    @FXML
    private Label lblEmail;
    /**
     * Nos va a mostrar un mensaje retroalimentación de la entrada de la dirección.
     */
    @FXML
    private Label lblAddress;
    
    /**
     * Crea el objeto alert.
     */
    Alert alert;
    /**
     * Almacena la ruta de la foto.
     */
    private String routePhoto;
    @FXML
    private AnchorPane apMember;
    @FXML
    private Label lblPaneMember;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requiredField(txtDni, "Campo obligatorio.");
        requiredField(txtFirstName, "Campo obligatorio.");
        requiredField(txtLastName, "Campo obligatorio.");
        requiredField(dpBirthdate, "Campo obligatorio.");
        requiredField(txtPhone, "Campo obligatorio.");
        requiredField(txtMobilePhone, "Campo obligatorio.");
        requiredField(txtEmail, "Campo obligatorio.");
        requiredField(txtAddress, "Campo obligatorio.");

    }

    /**
     * Método que limpia los campos de texto.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerCancel(ActionEvent event) {
        cleanFields();
    }

    /**
     * Guarda el socio añadido en la Base de datos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerSave(ActionEvent event) {
        addMember();
    }

    /**
     * Evento en el cual selecciona la foto a introducir en la Base de datos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handleSelectPhoto(MouseEvent event) {
        selectPhoto();
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
     * Método para limpiar los campos de texto
     */
    private void cleanFields() {
        txtDni.clear();
        txtFirstName.clear();
        txtLastName.clear();
        dpBirthdate.setValue(null);
        txtPhone.clear();
        txtMobilePhone.clear();
        txtEmail.clear();
        txtAddress.clear();
        lblFirstName.setText("");
        imgPhoto.setImage(new Image("image/noPhotoMember.png"));
    }

    /**
     * Método para seleccionar la foto desde un cuadro de dialogo.
     */
    private void selectPhoto() {
        FileChooser fileChooser = new FileChooser();
        // añadir extensiones
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("imagen", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            imgPhoto.setImage(new Image("file:///" + file.getAbsolutePath()));
            routePhoto = file.getAbsolutePath();
            System.out.println("probando la ruta " + routePhoto);
        }
    }

    /**
     * Método para añadir un socio a la Base de datos.
     */
    private void addMember() {
        boolean result = false;

        String dni = txtDni.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String birthdate = dpBirthdate.getValue().toString();
        int phone = Integer.parseInt(txtPhone.getText().trim());
        int mobilePhone = Integer.parseInt(txtMobilePhone.getText().trim());
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();

        System.out.println("Añadiendo ruta " + routePhoto);

        Member member = new Member(dni, firstName, lastName, birthdate, phone, mobilePhone, email, address, routePhoto);
        MemberDAO_impl memberDAO = new MemberDAO_impl();

        result = memberDAO.add(member);
        System.out.println(member.getDni());

        System.out.println("Probando ver tabla " + new BibliotecaController().tablaSocio());

        if (result == true) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("NUEVO SOCIO.");
            alert.setHeaderText("Los datos del SOCIO se han guardado.");

            ButtonType btOK = new ButtonType("OK");

            alert.getButtonTypes().setAll(btOK);
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.get() == btOK) {
                cleanFields();
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NUEVO SOCIO.");
            alert.setHeaderText("¡ERROR al insertar los datos!.");
        }
    }

    /**
     * Requiere la entrada de datos en los campos.
     * @param textField el campo al que queremos requerir los datos.
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

    /**
     * Requerimos la entrada de datos en el campo.
     * @param datePicker elemento que requiere la introducción de datos.
     * @param message mensaje que queremo mostrar como aviso.
     */
    private void requiredField(JFXDatePicker datePicker, String message) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        datePicker.getValidators().add(validator);
        validator.setMessage(message);
        
        validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.WARNING).size("1em").styleClass("error").build());
        
        datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                datePicker.validate();
            }
        });
    }

    /**
     * Valida que el dato se corresponde con lo esperado.
     * Restringe la entrada de datos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerValidateDNI(KeyEvent event) {
        String regex = "^([0-9]{8})[A-HJ-NP-TV-Za-hj-np-tv-z]{1}";
        String dni = txtDni.getText() + event.getCharacter();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dni);
        boolean esBueno = matcher.matches();

        if (esBueno) {
            lblDni.setText("Dni valido");
            lblDni.setStyle("-fx-text-fill: #4059a9");
        } else {
            lblDni.setText("Dni no valido");
            lblDni.setStyle("-fx-text-fill: #D34336");
        }
        if (txtDni.getText().equals("")) {
            lblDni.setText("");
        }
    }

    /**
     * Valida que el dato se corresponde con lo esperado.
     * Restringe la entrada de datos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handleValidarLetrasNombre(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        if ((!Character.isLetter(caracter)) && (!Character.isSpaceChar(caracter))) {
            event.consume();
            lblFirstName.setText("No se permiten números.");
            lblFirstName.setStyle("-fx-text-fill: #D34336");
        } else {
            lblFirstName.setText(" ");
        }
    }

    /**
     * Valida que el dato se corresponde con lo esperado.
     * Restringe la entrada de datos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handleValidarLetrasApellidos(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        if ((!Character.isLetter(caracter)) && (!Character.isSpaceChar(caracter))) {
            event.consume();
            lblLastName.setText("No se permiten números.");
            lblLastName.setStyle("-fx-text-fill: #D34336");
        } else {
            lblLastName.setText(" ");
        }
    }

    /**
     * Valida que el dato se corresponde con lo esperado.
     * Restringe la entrada de datos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerValidatePhone(KeyEvent event) {
        String regex = "^([9|8]{1}[0-9]{2})([0-9]{6})";
        String telefono = txtPhone.getText() + event.getCharacter();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telefono);
        boolean esBueno = matcher.matches();

        if (esBueno) {
            lblPhone.setText("Teléfono válido");
            lblPhone.setStyle("-fx-text-fill: #4059a9");
        } else {
            lblPhone.setText("Teléfono no válido");
            lblPhone.setStyle("-fx-text-fill: #D34336");
        }

        if (txtPhone.getText().equals("")) {
            lblPhone.setText("");
        }
    }

    /**
     * Valida que el dato se corresponde con lo esperado.
     * Restringe la entrada de datos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerValidateMobilePhone(KeyEvent event) {
        String regex = "^([6|7]{1}[0-9]{2})([0-9]{6})";
        String movil = txtMobilePhone.getText() + event.getCharacter();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(movil);
        boolean esBueno = matcher.matches();

        if (esBueno) {
            lblMobilePhone.setText("Móvil válido");
            lblMobilePhone.setStyle("-fx-text-fill: #4059a9");
        } else {
            lblMobilePhone.setText("Móvil no válido");
            lblMobilePhone.setStyle("-fx-text-fill: #D34336");
        }
        if (txtMobilePhone.getText().equals("")) {
            lblMobilePhone.setText("");
        }
    }

    /**
     * Valida que el dato se corresponde con lo esperado.
     * Restringe la entrada de datos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerValidateEmail(KeyEvent event) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        String email = txtEmail.getText() + event.getCharacter();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        boolean esBueno = matcher.matches();

        if (esBueno) {
            lblEmail.setText("E-mail válido");
            lblEmail.setStyle("-fx-text-fill: #4059a9");
        } else {
            lblEmail.setText("E-mail no válido");
            lblEmail.setStyle("-fx-text-fill: #D34336");
        }
        if (txtEmail.getText().equals("")) {
            lblEmail.setText("");
        }
    }

    /**
     * Valida que el dato se corresponde con lo esperado.
     * Restringe la entrada de datos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerValidateAddress(KeyEvent event) {
        String regex = "^[a-zA-Z0-9.//ºª/ -]+";
        String direccion = txtAddress.getText() + event.getCharacter();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(direccion);
        boolean esBueno = matcher.matches();

        if (esBueno) {
            lblAddress.setText("E-mail válido");
            lblAddress.setStyle("-fx-text-fill: #4059a9");
        } else {
            lblAddress.setText("E-mail no válido");
            lblAddress.setStyle("-fx-text-fill: #D34336");
        }
        if (txtAddress.getText().equals("")) {
            lblAddress.setText("");
        }
    }
}
