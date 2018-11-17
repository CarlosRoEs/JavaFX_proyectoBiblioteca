/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.MemberDAO_impl;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import model.Member;
import util.Camera;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class UpdateMemberController implements Initializable {

    /**
     * Muestra el nombre de la ventana.
     */
    @FXML
    private Label lblPaneMember;
    /**
     * Campo para introducir el Dni del Socio.
     */
    @FXML
    private JFXTextField txtDni;
    /**
     * Etiqueta dni que muestra mensaje de error.
     */
    @FXML
    private Label lblDni;
    /**
     * Campo para introducir el nombre del socio.
     */
    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private Label lblFirstName;
    /**
     * Campo para introducir el apellido del socio.
     */
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private Label lblLastName;
    /**
     * Calendario para introducir la fecha de nacimiento..
     */
    @FXML
    private JFXDatePicker dpBirthdate;
    /**
     * Campo para introducir el teléfono del socio.
     */
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private Label lblPhone;
    /**
     * Campo para introducir el móvil del socio.
     */
    @FXML
    private JFXTextField txtMobilePhone;
    @FXML
    private Label lblMobilePhone;
    /**
     * Campo para introducir el e-mail del socio.
     */
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private Label lblEmail;
    /**
     * Campo para introducir la dirección del socio.
     */
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private Label lblAddress;
    /**
     * Muestra la foto del socio.
     */
    @FXML
    private ImageView imgPhoto;

    Alert alert;
    private String routePhoto;
    private int idMember;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

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
    private void handlerValildateFirstName(KeyEvent event) {
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
    private void handlerValidateLastName(KeyEvent event) {
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

    /**
     * Limpia los campos.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerCancel(ActionEvent event) {
        cleanFields();
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
        lblAddress.setText("");
        lblDni.setText("");
        lblEmail.setText("");
        lblLastName.setText("");
        lblMobilePhone.setText("");
        lblPhone.setText("");
        imgPhoto.setImage(new Image("image/noPhotoMember.png"));
    }

    /**
     * Guarda los datos del socio en la BD.
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerSave(ActionEvent event) {
        updateMember();
    }

    /**
     * Selecciona y muestra la foto del socio.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handleSelectPhoto(MouseEvent event) {
        selectPhoto();
    }

    /**
     * Abre la ventana para acceder a la webCam.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerOpenCamera(ActionEvent event) {
        Camera camera = new Camera();
        camera.openCapturePhoto("/view/CapturePhoto.fxml", "/style/bibliotecaStyle");
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
            // imagen fuera del proyecto
            imgPhoto.setImage(new Image("file:///" + file.getAbsolutePath()));
            routePhoto = file.getAbsolutePath();
            System.out.println("probando la ruta " + routePhoto);
        }
    }

    /**
     * Muestra los datos del socio en sus campos correspondientes.
     *
     * @param member se le pasa un objeto con los datos del miembro.
     */
    public void showMember(Member member) {
//        Member member = new Member();
        idMember = member.getIdMember();
        System.out.println(idMember + " @@@@@@@@@@showMember");
        txtDni.setText(member.getDni());
        txtFirstName.setText(member.getFirstName());
        txtLastName.setText(member.getLastName());

        dpBirthdate.setValue(LocalDate.parse(member.getBirthdate()));
        txtPhone.setText(String.valueOf(member.getPhone()));
        txtMobilePhone.setText(String.valueOf(member.getMobilePhone()));
        txtEmail.setText(member.getEmail());
        txtAddress.setText(member.getAddress());
        imgPhoto.setImage(new Image("file:///" + member.getPhoto()));
        routePhoto = member.getPhoto();
    }

    /**
     * Actualiza los datos del socio en la BD.
     */
    private void updateMember() {
        boolean result = false;

        String dni = txtDni.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String birthdate = dpBirthdate.getValue().toString();
        int phone = Integer.parseInt(txtPhone.getText().trim());
        int mobilePhone = Integer.parseInt(txtMobilePhone.getText().trim());
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();

        Member member = new Member(idMember, dni, firstName, lastName, birthdate, phone, mobilePhone, email, address, routePhoto);
        MemberDAO_impl memberDAO = new MemberDAO_impl();

        result = memberDAO.update(member);
        System.out.println(member.getIdMember() + " ##############");
        System.out.println(result);
        if (result == true) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Actualizar Socio.");
            alert.setContentText("El Socio ha sido actualizado.");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Actualizar Socio.");
            alert.setHeaderText("¡ERROR al actualizar los datos!.");
            alert.showAndWait();
        }
    }
}
