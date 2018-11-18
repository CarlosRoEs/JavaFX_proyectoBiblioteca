/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import dao.BookDAO_impl;
import dao.LendingDAO_impl;
import dao.MemberDAO_impl;
import java.io.IOException;
import model.Member;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jdbc.ConnectionJDBC;
import model.Book;
import model.Lending;
import org.fxutils.viewer.JasperViewerFX;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class BibliotecaController implements Initializable {

    //############## MENU ##############
    /**
     * Botón para acceder la ventana home
     */
    @FXML
    private JFXButton btnHome;
    /**
     * Botón para acceder a la ventana Socio.
     */
    @FXML
    private JFXButton btnMember;
    /**
     * Botón para acceder a la ventana Libro.
     */
    @FXML
    private JFXButton btnBook;
    /**
     * Botón para acceder a la ventana Informes.
     */
    @FXML
    private JFXButton btnReports;
    /**
     * Etiqueta que muestra el titulo de la ventana,
     */
    @FXML
    private Label lblPane;

    // ############### MEMBER ###############
    /**
     * Panel del Socio que se encuentra dentro del Home.
     */
    @FXML
    private GridPane gpMember;
    /**
     * Campo para buscar un elemento socio.
     */
    @FXML
    private JFXTextField txtSearch;

    /**
     * Tabla donde se muestran datos del socio.
     */
    @FXML
    private TableView<Member> tblMember;
    /**
     * Columna donde se muestra el ID del Socio.
     */
    @FXML
    private TableColumn<Member, Integer> columID;
    /**
     * Columna donde se muestra el DNI del Socio.
     */
    @FXML
    private TableColumn<Member, String> columDni;
    /**
     * Columna donde se muestra el Nombre del Socio.
     */
    @FXML
    private TableColumn<Member, String> columFirstName;
    /**
     * Columna donde se muestra los Apellidos del Socio.
     */
    @FXML
    private TableColumn<Member, String> columLastName;
    /**
     * Columna donde se muestra el telefono del Socio.
     */
    @FXML
    private TableColumn<Member, Integer> columPhone;
    /**
     * Columna donde se muestra el movil del Socio.
     */
    @FXML
    private TableColumn<Member, Integer> columMobilePhone;
    /**
     * Columna donde se muestra el email del Socio.
     */
    @FXML
    private TableColumn<Member, String> columEmail;
    /**
     * Columna donde se muestra la dirección del Socio.
     */
    @FXML
    private TableColumn<Member, String> columAddress;

    /**
     * Almacena datos del socio.
     */
    private Member selectedMember;

    public Member getSelectedMember() {
        return selectedMember;
    }
    
    
    /**
     * Accede al controlador de AddSocio.
     */
    private AddMemberController memberController = new AddMemberController();

    //############### HOME ##############
    /**
     * Panel donde se encuentran los datos para añadir el prestamo.
     */
    @FXML
    private GridPane gpHome;
    /**
     * Combobox que muestra la fecha de retorno del libro.
     */
    @FXML
    private JFXDatePicker dpReturnDate;
    /**
     * Combobox que muestra la fecha en la que se realiza el prestamo del libro.
     */
    @FXML
    private JFXDatePicker dpLendingDate;
    /**
     * Combobox donde se muestra la fecha en la que se debe entregar el libro.
     */
    @FXML
    private JFXDatePicker dpDeliverDate;
    /**
     * Campo para buscar un socio.
     */
    @FXML
    private JFXTextField txtSearchMember;
    /**
     * Campo para mostrar el nombre del Socio.
     */
    @FXML
    private JFXTextField txtMemberName;
    /**
     * Campo para mostrar el apellido del Socio.
     */
    @FXML
    private JFXTextField txtMemberLastName;
    /**
     * Campo para mostrar el titulo del libro.
     */
    @FXML
    private JFXTextField txt_bookTitle;
    /**
     * Campo para buscar un libro.
     */
    @FXML
    private JFXTextField txtBookSearch;
    /**
     * Muestra la imagen del socio.
     */
    @FXML
    private ImageView imgPhotoMember;
    /**
     * Muestra la portada del libro.
     */
    @FXML
    private ImageView imgCoverBook;

    /**
     * Crea un objeto Alert
     */
    Alert alert;

    //################ BOOK ########################
    /**
     * Panel donde se muestran los datos del socio.
     */
    @FXML
    private GridPane gpBook;
    /**
     * Campo para buscar un libro.
     */
    @FXML
    private JFXTextField txtSearchBook;
    /**
     * Tabla que muestra los datos del libro.
     */
    @FXML
    private TableView<Book> tblBook;
    /**
     * Columna que muestra el ID del libro.
     */
    @FXML
    private TableColumn<Book, Integer> colum_idBook;
    /**
     * Columna que muestra el ISBN del libro.
     */
    @FXML
    private TableColumn<Book, String> colum_isbn;
    /**
     * Columna que muestra el titulo del libro.
     */
    @FXML
    private TableColumn<Book, String> colum_title;
    /**
     * Columna que muestra el autor del libro.
     */
    @FXML
    private TableColumn<Book, String> colum_author;
    /**
     * Columna que muestra la editorial del libro.
     */
    @FXML
    private TableColumn<Book, String> colum_editorial;
    /**
     * Columna que muestra el año de edición del libro del libro.
     */
    @FXML
    private TableColumn<Book, String> colum_yearEdition;
    /**
     * Columna que muestra el género del libro.
     */
    @FXML
    private TableColumn<Book, String> colum_kind;

    /**
     * Almacena datos del ibro.
     */
    private Book selectedBook;
    /**
     * Almacena datos del prestamo.
     */
    private Lending selectedLending;
    /**
     * Almacena datos del Socio.
     */
    private Member selectedListaMember;
    /**
     * Almacena datos dle libro.
     */
    private Book selectedListaBook;
    /**
     * Almacena la ruta de la foto.
     */
    private String routePhoto;
    /**
     * Almacena el id del Socio.
     */
    private int idMember;
    /**
     * Almacena el id del libro.
     */
    private int idBook;

    //################ LENDING ########################
    /**
     * Tabla que muestra los datos del prestamo.
     */
    @FXML
    private TableView<Lending> tblLending;
    /**
     * Columna que muestra el id del prestamo.
     */
    @FXML
    private TableColumn<Lending, Integer> colum_idLending;
    /**
     * Columna que muestra la fecha en la que se presta el libro.
     */
    @FXML
    private TableColumn<Lending, String> colum_lendingDate;
    /**
     * Columna que muestra la fecha de devolución del prestamo.
     */
    @FXML
    private TableColumn<Lending, String> colum_returnDate;
    /**
     * Columna que muesta la fecha prevista de la devolución del prestamo.
     */
    @FXML
    private TableColumn<Lending, String> colum_deliverDate;
    /**
     * Lista que muestra datos del socio.
     */
    @FXML
    private JFXListView<Member> lvSocio;
    /**
     * Campo que muestra el dni del socio.
     */
    @FXML
    private JFXTextField txtDni;
    /**
     * Lista que muestra datos del libro(isbn).
     */
    @FXML
    private JFXListView<Book> lvLibro;
    /**
     * Campo que muestra el isbn del libro.
     */
    @FXML
    private JFXTextField txtIsbn;

    //################ REPORTS ########################
    /**
     * Panel de Informes que se muestra al pulsar el boton informes.
     */
    @FXML
    private GridPane gpReports;
    /**
     * Selecciona el socio deseado para mostrar el reporte.
     */
    @FXML
    private JFXComboBox<Member> cmbSeleccionaSocio;
    /**
     * Combobox que muestra el tipo de informe que queremos del socio o del
     * libro.
     */
    @FXML
    private JFXComboBox<String> cmbSeleccionaTipoPrestamo;
    /**
     * Selecciona el libro del que queremos el informe.
     */
    @FXML
    private JFXComboBox<Book> cmbSeleccionaLibro;
    /**
     * Nos muestra datos del socio y del libro dependiendo de la elección del
     * combobox tipo prestamo.
     */
    @FXML
    private JFXComboBox<?> cmbDinamicoPrestamo;
    /**
     * Imagen
     */
    @FXML
    private ImageView imgButtonReportPrestamos;
    /**
     * Botón para mostrar el informe de los prestamos.
     */
    @FXML
    private JFXButton btnReportPrestamos;

    /**
     * Variable para almacenar la conexión a la BD.
     */
    private final Connection CONNECTION;
    
    /**
     * Constructor en el que se inicializa la variable final CONNECTION.
     */
    public BibliotecaController() {
        this.CONNECTION = new ConnectionJDBC().getConnection();;
    }

    /**
     * Crea un objeto Member.
     */
    Member member = new Member();
    /**
     * Crea un objeto Book
     */
    Book book = new Book();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /*
        ################# HOME ###############################################
         */
        añadeFechas();
        llenarListaDni();
        llenarListaIsbn();
        iniciarColumnaTablaPrestamos();
        seleccionarPrestamosActualizado();
        cargarTablaPrestamos();
//        seleccionarPrestamos();
        datosLista();
        llenarComboInformeSocio();
        llenarComboInformeLibro();
        llenarTipoPrestamo();
        lblPane.setText("HOME");


        /*
        ################# MEMBER ###############################################
         */
        iniciaColumnasTablaSocio();
        cargarTablaSocio();
        seleccionarSocio();

        /*
        ################# BOOK #################################################
         */
        iniciarColumnasTablaLibro();
        cargarTablaLibros();
        seleccionarLibro();

    }

    /*
    ############################################################################
                                HOME
    ############################################################################
     */
    /**
     * Cambia los paneles conforme se acciona un boton.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handerActionClick(ActionEvent event) {

        if (event.getSource() == btnHome) {
            lblPane.setText("HOME");
            recargarListaDni(new ActionEvent());
            recargarListaIsbn(new ActionEvent());
            gpHome.toFront();
        } else if (event.getSource() == btnMember) {
            lblPane.setText("SOCIO");
            gpMember.toFront();
        } else if (event.getSource() == btnBook) {
            lblPane.setText("LIBRO");
            gpBook.toFront();
        } else if (event.getSource() == btnReports) {
            lblPane.setText("INFORMES");
            recargarComboInfomeLibro();
            recargarComboInformSocio();
            gpReports.toFront();
        }
    }

    /**
     * Guardamos en la BD los datos del prestamo.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerSaveHome(ActionEvent event) {
        añadeDatosPrestamo();
    }

    /**
     * Actualiza los prestamos de la BD.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerActualizarPrestamo(ActionEvent event) {
        updatePrestamo();
    }

    /**
     * Buscador de socios para añadirle un prestamo.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerSearchMemberLending(KeyEvent event) {
        buscarDni();
    }

    /**
     * Limpia los campos de los textfield de datos del socio.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerClearDataMember(ActionEvent event) {
        limpiarCamposSocio();
    }

    /**
     * Limpia los campos de los textfield de datos del libro.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerClearDataBook(ActionEvent event) {
        limpiarCamposLibros();
    }

    /**
     * Selecciona socio del combobox.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handelSeleccionarSocio(MouseEvent event) {
        seleccionarDniLista();
    }

    /**
     * Selecciona libro del combobox.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerSeleccionarIsbn(MouseEvent event) {
        seleccionarIsbnLista();
    }

    /**
     * Busca el libro para añadirlo al prestamo.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerSearchBookLending(KeyEvent event) {
        buscarIsbn();
    }

    /**
     * Elimina el prestamo de la BD.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerEliminarPrestamo(ActionEvent event) {
        eliminarPrestamo();
    }

    /**
     * Limpia los campos de la ventana Home.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerLimpiarHome(ActionEvent event) {
        limpiarCamposLibros();
        limpiarCamposSocio();
        limpiarCamposPrestamos();
    }

    /**
     * Limpia los campos de los seleccionables del panel de informes.
     *
     * @param event
     */
    @FXML
    private void handlerLimpiarCamposInformes(ActionEvent event) {
        cmbDinamicoPrestamo.setValue(null);
        cmbSeleccionaTipoPrestamo.setValue(null);
        cmbSeleccionaLibro.setValue(null);
        cmbSeleccionaSocio.setValue(null);
        imgButtonReportPrestamos.setImage(new Image("/image/stop.png"));
    }

    /**
     * Método para llenar el combobox con datos de la base de datos.
     */
    private void llenarListaDni() {
        lvSocio.setItems(tablaSocio());

    }

    /**
     * Recarga el combo al cerrar la ventana de añadir Socio.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    private void recargarListaDni(ActionEvent event) {
        llenarListaDni();
    }

    /**
     * Método para buscar el socio para adjuntarse.
     */
    private void buscarDni() {
        FilteredList<Member> filterMember = new FilteredList<>(tablaSocio(), s -> true);
        lvSocio.setItems(filterMember);
        txtSearchMember.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterMember.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String dni = member.getDni().toLowerCase();

                return dni.contains(newValue.toLowerCase());
            });
        });
        SortedList<Member> sortData = new SortedList<>(filterMember);
        sortData.comparatorProperty().bind(tblMember.comparatorProperty());

        tblMember.setItems(sortData);
    }

    /**
     * Método que selecciona el dni del combobox.
     */
    private void seleccionarDniLista() {
        selectedListaMember = (Member) lvSocio.getSelectionModel().getSelectedItem();
        if (selectedListaMember != null) {
            txtMemberName.setText(selectedListaMember.getFirstName());
            txtMemberLastName.setText(selectedListaMember.getLastName());
            txtDni.setText(selectedListaMember.getDni());
            imgPhotoMember.setImage(new Image("file:///" + selectedListaMember.getPhoto()));
            idMember = selectedListaMember.getIdMember();
        }
    }

    /**
     * Limpia los datos de los campos del Socio.
     */
    private void limpiarCamposSocio() {
        txtMemberName.clear();
        txtMemberLastName.clear();
        txtDni.clear();
        txtSearchMember.clear();
        imgPhotoMember.setImage(new Image("image/noPhotoMember.png"));
    }

    /**
     * Limpia los textfield de los datos del libro.
     */
    private void limpiarCamposLibros() {
        txtIsbn.clear();
        txt_bookTitle.clear();
        txtBookSearch.clear();
        imgCoverBook.setImage(new Image("image/noPhotoBook.png"));
    }

    /**
     * Limpia los textfield de la ventana del Home.
     */
    private void limpiarCamposPrestamos() {
        añadeFechas();
        dpReturnDate.setValue(null);
    }

    /**
     * Añade las fechas de prestamos y de devolución a sus datepicker.
     */
    private void añadeFechas() {
        LocalDate localDate = LocalDate.now();
        dpLendingDate.setValue(localDate);
        dpDeliverDate.setValue(localDate.plusDays(7));
    }

    /**
     * Llena el combobox con los isbn guardados en la base de datos.
     */
    private void llenarListaIsbn() {
        lvLibro.setItems(tablaLibro());
    }

    /**
     * Recarga el combo al cerrar la ventana de añadir libros.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    private void recargarListaIsbn(ActionEvent event) {
        llenarListaIsbn();
    }

    /**
     * Busca los libros por isbn que se encuentran en la base de datos.
     */
    private void buscarIsbn() {
        FilteredList<Book> filterBook = new FilteredList<>(tablaLibro(), s -> true);
        lvLibro.setItems(filterBook);

        txtBookSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterBook.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String isbn = String.valueOf(member.getIsbn());

                return isbn.contains(newValue.toLowerCase());
            });
        });
        SortedList<Book> sortData = new SortedList<>(filterBook);
        sortData.comparatorProperty().bind(tblBook.comparatorProperty());

        tblBook.setItems(sortData);
    }

    /**
     * Selecciona el isbn de la lista.
     */
    private void seleccionarIsbnLista() {
        selectedListaBook = (Book) lvLibro.getSelectionModel().getSelectedItem();
        if (selectedListaBook != null) {
            txtIsbn.setText(selectedListaBook.getIsbn());
            txt_bookTitle.setText(selectedListaBook.getTitle());
            imgCoverBook.setImage(new Image("file:///" + selectedListaBook.getCover()));
            idBook = selectedListaBook.getIdBook();
        }
    }

    /**
     * Método para elmiminar el prestamo seleccionado de la tabla.
     */
    private void eliminarPrestamo() {
        LendingDAO_impl lendingDAO = new LendingDAO_impl();
        alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("Eliminar Prestamo");
        alert.setContentText("¿Quiere ELIMINAR el dato seleccionado.?.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            lendingDAO.remove(selectedLending);
            tblLending.setItems(tablaPrestamos());
            limpiarCamposPrestamos();
            alert.setHeaderText("Prestamo eliminado");
            alert.setContentText("Prestamo eliminado con exito.");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error al eliminar el socio.");
            alert.show();
        }
    }

    /**
     * Método para actualizar prestamos. Su función es añadir la fecha en la que
     * el socio devuelve el libro.
     */
    private void updatePrestamo() {
        boolean result = false;
        int idLendingUpdate = selectedLending.getIdLending();
        String lendingDate = dpLendingDate.getValue().toString();
        String deliverDate = dpDeliverDate.getValue().toString();
        String returnDate = dpReturnDate.getValue().toString();

        Lending lending = new Lending(idLendingUpdate, lendingDate, deliverDate, returnDate);
        LendingDAO_impl lendingDAO = new LendingDAO_impl();

        result = lendingDAO.update(lending);
        
        System.out.println(result);
        if (result == true) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Actualizar Prestamo.");
            alert.setContentText("El Prestamo ha sido actualizado.");
            alert.showAndWait();
            recargarTablaPrestamos(new ActionEvent());
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Actualizar Prestamo.");
            alert.setHeaderText("¡ERROR al actualizar los datos!.");
            alert.showAndWait();
        }

    }

    /**
     * Creamos el observable con la lista de miembros
     *
     * @return obsevableList
     */
    public ObservableList<Lending> tablaPrestamos() {
        LendingDAO_impl lendingDAO = new LendingDAO_impl();
        return FXCollections.observableArrayList(lendingDAO.getList());
    }

    /**
     * Iniciamos las Columnas.
     */
    public void iniciarColumnaTablaPrestamos() {
        colum_idLending.setCellValueFactory(new PropertyValueFactory("idLending"));
        colum_lendingDate.setCellValueFactory(new PropertyValueFactory("lendingDate"));
        colum_deliverDate.setCellValueFactory(new PropertyValueFactory("deliverDate"));
        colum_returnDate.setCellValueFactory(new PropertyValueFactory("returnDate"));
    }

    /**
     * Cargamos la tabla Libro.
     */
    public void cargarTablaPrestamos() {
        tblLending.setItems(tablaPrestamos());
    }

    /**
     * Metodo que recarga la tabla de libros
     *
     * @param event
     */
    public void recargarTablaPrestamos(ActionEvent event) {
        cargarTablaPrestamos();
    }

    /**
     * Busca datos del socio.
     *
     * @return los datos del socio
     */
    public Member dameSocio() {
        String sql = "SELECT dni, firstName, lastName, photo FROM Member WHERE idMember=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setInt(1, idMember);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                member.setDni(result.getString("dni"));
                member.setFirstName(result.getString("firstName"));
                member.setLastName(result.getString("lastName"));
                member.setPhoto(result.getString("photo"));
            }
            return member;
        } catch (SQLException ex) {

            return null;
        }

    }

    /**
     * Busca los datos del libro
     *
     * @return los datos del libro.
     */
    public Book dameLibro() {

        String sql = "SELECT isbn, title, cover FROM Book WHERE idBook=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setInt(1, idBook);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                book.setIsbn(result.getString("isbn"));
                book.setTitle(result.getString("title"));
                book.setCover(result.getString("cover"));
            }
            return book;
        } catch (SQLException ex) {
            System.out.println("Error al realizar la consulta buscarSocio " + ex.getMessage());

            return null;
        }
    }

//    /**
//     * Método para seleccionar un miembro.
//     */
//    private void seleccionarPrestamos() {
//
//        tblLending.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                selectedLending = (Lending) newValue;
//                limpiarCamposSocio();
//                limpiarCamposLibros();
//                idMember = selectedLending.getIdMember();
//                dameSocio();
//                idBook = selectedLending.getIdBook();
//                dameLibro();
//                dpLendingDate.setValue(LocalDate.parse(selectedLending.getLendingDate()));
//                dpDeliverDate.setValue(LocalDate.parse(selectedLending.getDeliverDate()));
//
//                txtDni.setText(member.getDni());
//                txtMemberName.setText(member.getFirstName());
//                txtMemberLastName.setText(member.getLastName());
//                imgPhotoMember.setImage(new Image("file:///" + member.getPhoto()));
//
//                txtIsbn.setText(book.getIsbn());
//                txt_bookTitle.setText(book.getTitle());
//                imgCoverBook.setImage(new Image("file:///" + book.getCover()));
//            }
//        });
//    }

    /**
     * Método para seleccionar un miembro.
     */
    private void seleccionarPrestamosActualizado() {

        tblLending.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedLending = (Lending) newValue;
                limpiarCamposSocio();
                limpiarCamposLibros();
                idMember = selectedLending.getIdMember();
                dameSocio();
                idBook = selectedLending.getIdBook();
                dameLibro();
                dpLendingDate.setValue(LocalDate.parse(selectedLending.getLendingDate()));
                dpDeliverDate.setValue(LocalDate.parse(selectedLending.getDeliverDate()));
                
                System.out.println(selectedLending.getReturnDate());
                txtDni.setText(member.getDni());
                txtMemberName.setText(member.getFirstName());
                txtMemberLastName.setText(member.getLastName());
                imgPhotoMember.setImage(new Image("file:///" + member.getPhoto()));

                txtIsbn.setText(book.getIsbn());
                txt_bookTitle.setText(book.getTitle());
                imgCoverBook.setImage(new Image("file:///" + book.getCover()));
                if(selectedLending.getReturnDate() != null)
                    dpReturnDate.setValue(LocalDate.parse(selectedLending.getReturnDate()));
                
            }
        });
    }

    /**
     * Método que añade a la BD las fechas de los prestamos, los daños y las
     * sanciones.
     */
    private void añadeDatosPrestamo() {
        boolean resultLending = false;

        String lendingDate = dpLendingDate.getValue().toString();
        String deliverDate = dpDeliverDate.getValue().toString();

        Lending lending = new Lending(lendingDate, deliverDate, idBook, idMember);

        LendingDAO_impl lendingDAO = new LendingDAO_impl();
        resultLending = lendingDAO.add(lending);

        if (resultLending == true) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DATOS PRESTAMO.");
            alert.setHeaderText("Los datos del PRESTAMO se han guardado.");

            ButtonType btOK = new ButtonType("OK");

            alert.getButtonTypes().setAll(btOK);
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.get() == btOK) {
                recargarTablaPrestamos(new ActionEvent());

            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DATOS PRESTAMO.");
            alert.setHeaderText("¡ERROR al insertar los datos!.");
        }
    }

    /*
    ############################################################################
                                MEMBER
    ############################################################################
     */
    /**
     * Abre la ventana para añadir el Socio.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerOpenAddMember(ActionEvent event) {
        AbrirVentanaSocio();
    }

    /**
     * Abre la ventana para editar el socio.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerOpenEditMember(ActionEvent event) {
        abrirVentanaEdicionSocio();
    }

    /**
     * Elimina el socio de la BD.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerDeleteMember(ActionEvent event) {
        eliminarSocio();
    }

    /**
     * Evento de teclado para buscar un Socio.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerSearchMember(KeyEvent event) {
        buscarSocio();
    }

    /**
     * Abrimos la ventana para añadir Socio.
     */
    private void AbrirVentanaSocio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddMember.fxml"));
            Parent root = loader.load();
            //Parent root = FXMLLoader.load(getClass().getResource("/view/AddMember.fxml"));

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/style/bibliotecaStyle");
            stage.setScene(scene);
            stage.setTitle("GESTIÓN DE BIBLIOTECAS");
            stage.setResizable(false);
            stage.show();

            stage.setOnCloseRequest(event -> {
                recargarTablaSocio(new ActionEvent());
                recargarListaDni(new ActionEvent());
                
            });

        } catch (IOException e) {
            System.out.println("No se ha cargado la ventana.");
            e.printStackTrace();
        }
    }

    /**
     * Creamos el observable con la lista de miembros
     *
     * @return lista de Socios
     */
    public ObservableList<Member> tablaSocio() {
        MemberDAO_impl memberDAO = new MemberDAO_impl();
        return FXCollections.observableArrayList(memberDAO.getList());
    }

    /**
     * Iniciamos las columnas de la tabla Socio.
     */
    public void iniciaColumnasTablaSocio() {
        columID.setCellValueFactory(new PropertyValueFactory("idMember"));
        columDni.setCellValueFactory(new PropertyValueFactory("dni"));
        columFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        columLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        columPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        columMobilePhone.setCellValueFactory(new PropertyValueFactory("mobilePhone"));
        columEmail.setCellValueFactory(new PropertyValueFactory("email"));
        columAddress.setCellValueFactory(new PropertyValueFactory("address"));
    }

    /**
     * Cargamos la tabla Socio.
     */
    public void cargarTablaSocio() {
        tblMember.setItems(tablaSocio());
    }

    /**
     * Metodo que recarga la tabla de socio.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    public void recargarTablaSocio(ActionEvent event) {
        cargarTablaSocio();
    }

    /**
     * Método para buscar socio en la tabla
     */
    private void buscarSocio() {
        FilteredList<Member> filterMember = new FilteredList<>(tablaSocio(), s -> true);
        tblMember.setItems(filterMember);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterMember.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String dni = member.getDni().toLowerCase();
                String firstName = member.getFirstName().toLowerCase();
                String lastName = member.getLastName().toLowerCase();

                return dni.contains(newValue.toLowerCase());
            });
        });
        SortedList<Member> sortData = new SortedList<>(filterMember);
        sortData.comparatorProperty().bind(tblMember.comparatorProperty());

        tblMember.setItems(sortData);
    }

    /**
     * Método para seleccionar un miembro de la tabla
     */
    private void seleccionarSocio() {
        tblMember.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedMember = (Member) newValue;
            }
        });
    }

    /**
     * Abre la ventana para poder actualizar al socio.
     */
    private void abrirVentanaEdicionSocio() {

        if (selectedMember == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Debes seleccionar un socio en la tabla.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateMember.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            UpdateMemberController updateController = loader.getController();
            updateController.showMember(selectedMember);

            stage.setOnCloseRequest(event -> {
                recargarTablaSocio(new ActionEvent());

            });

            scene.getStylesheets().add("/style/bibliotecaStyle");
            stage.setScene(scene);
            stage.setTitle("GESTIÓN DE BIBLIOTECAS");
            stage.setResizable(false);
            stage.show();

//            stage.setOnCloseRequest(event -> {
//                refreshTableMember(new ActionEvent());
//            });
        } catch (IOException e) {
            System.out.println("No se ha cargado la ventana.");
            e.printStackTrace();
        }
    }

    /**
     * Elimina el socio seleccionado de la tabla.
     */
    private void eliminarSocio() {
        MemberDAO_impl memberDao = new MemberDAO_impl();
        alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("Eliminar Socio");
        alert.setContentText("¿Quiere ELIMINAR el dato seleccionado.?.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            memberDao.remove(selectedMember);
            tblMember.setItems(tablaSocio());
            limpiarCamposSocio();
            alert.setHeaderText("Socio eliminado");
            alert.setContentText("Socio eliminado con exito.");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error al eliminar el socio.");
            alert.show();
        }
    }

    /*
    ############################################################################
                                BOOK
    ############################################################################
     */
    /**
     * Abre la ventana para añadir el libro
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerOpenAddBook(ActionEvent event) {
        abrirVentanaLibros();
    }

    /**
     * Abre la ventana para editar el libro.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerOpenEditBook(ActionEvent event) {
        abrirVentanaEdicionLibro();
    }

    /**
     * Elimina el libro de la BD.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerDeleteBook(ActionEvent event) {
        eliminarLibro();
    }

    /**
     * Busca el libro.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    public void handlerSearchBook(KeyEvent event) {
        buscarLibro();
    }

    /**
     * Abre la ventana para añadir los libros.
     */
    private void abrirVentanaLibros() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddBook.fxml"));

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/style/bibliotecaStyle");
            stage.setScene(scene);
            stage.setTitle("GESTIÓN DE BIBLIOTECAS");
            stage.setResizable(false);
            stage.show();

            stage.setOnCloseRequest(event -> {
                recargarTablaLibros(new ActionEvent());
                recargarListaIsbn(new ActionEvent());
            });

        } catch (IOException e) {
            System.out.println("The window isn´t loaded.");
            e.printStackTrace();
        }
    }

    /**
     * Creamos el observable con la lista de miembros
     *
     * @return observableList
     */
    public ObservableList<Book> tablaLibro() {
        BookDAO_impl bookDAO = new BookDAO_impl();
        return FXCollections.observableArrayList(bookDAO.getList());
    }

    /**
     * Iniciamos las Columnas.
     */
    public void iniciarColumnasTablaLibro() {
        colum_idBook.setCellValueFactory(new PropertyValueFactory("idBook"));
        colum_isbn.setCellValueFactory(new PropertyValueFactory("isbn"));
        colum_title.setCellValueFactory(new PropertyValueFactory("title"));
        colum_author.setCellValueFactory(new PropertyValueFactory("author"));
        colum_editorial.setCellValueFactory(new PropertyValueFactory("publisher"));
        colum_yearEdition.setCellValueFactory(new PropertyValueFactory("year_edition"));
        colum_kind.setCellValueFactory(new PropertyValueFactory("kind"));
    }

    /**
     * Cargamos la tabla Libro.
     */
    public void cargarTablaLibros() {
        tblBook.setItems(tablaLibro());
    }

    /**
     * Recarga la tabla de libros
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    public void recargarTablaLibros(ActionEvent event) {
        cargarTablaLibros();
    }

    /**
     * Selecciona un socio de la tabla.
     */
    private void seleccionarLibro() {
        tblBook.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedBook = (Book) newValue;
            }
        });
    }

    /**
     * Abre la ventana para editar el libro.
     */
    private void abrirVentanaEdicionLibro() {

        if (selectedBook == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Debes seleccionar un libro en la tabla.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateBook.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            UpdateBookController updateController = loader.getController();
            updateController.showBook(selectedBook);

            stage.setOnCloseRequest(event -> {
                recargarTablaLibros(new ActionEvent());
            });

            scene.getStylesheets().add("/style/bibliotecaStyle");
            stage.setScene(scene);
            stage.setTitle("GESTIÓN DE BIBLIOTECAS");
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            System.out.println("The window isn´t loaded.");
            e.printStackTrace();
        }
    }

    /**
     * Busca el libro en la tabla.
     */
    private void buscarLibro() {
        FilteredList<Book> filterBook = new FilteredList<>(tablaLibro(), s -> true);
        tblBook.setItems(filterBook);

        txtSearchBook.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterBook.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String isbn = String.valueOf(book.getIsbn());
                String title = book.getTitle().toLowerCase();
                String author = book.getAuthor().toLowerCase();

                if (isbn.contains(newValue)) {
                    return true;
                } else if (title.contains(newValue.toLowerCase())) {
                    return true;
                } else if (author.contains(newValue.toLowerCase())) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Book> sortData = new SortedList<>(filterBook);
        sortData.comparatorProperty().bind(tblBook.comparatorProperty());

        tblBook.setItems(sortData);
    }

    /**
     * Elimina el libro seleccionado de la tabla.
     */
    private void eliminarLibro() {
        BookDAO_impl bookDAO = new BookDAO_impl();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setHeaderText("Eliminar Libro");
        alert.setContentText("¿Quiere ELIMINAR el dato seleccionado.?.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            bookDAO.remove(selectedBook);
            tblBook.setItems(tablaLibro());
            limpiarCamposSocio();
            llenarComboInformeLibro();
            alert.setHeaderText("Libro eliminado");
            alert.setContentText("Libro eliminado con exito.");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error al eliminar el Libro.");
            alert.show();
        }
    }

    /*
    ############################################################################
                                INFORMES
    ############################################################################
     */
    /**
     * Muestra la lista de los socios de la bibilioteca
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handleMostrarListaSocio(ActionEvent event) {

        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            JasperViewerFX viewerfx;

            viewerfx = new JasperViewerFX(stage, "Informe Socios", "/informes/ListadoSocios.jasper", new HashMap(), CONNECTION);
            viewerfx.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Muestra los datos del socio.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerMostrarDatosSocios(ActionEvent event) {

        String socio = cmbSeleccionaSocio.getSelectionModel().getSelectedItem().toString();
        System.out.println(socio);

        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            HashMap parametros = new HashMap();
            parametros.put("p_dni", socio);
            System.out.println(parametros);

            JasperViewerFX viewerfx;

            viewerfx = new JasperViewerFX(stage, "Informe Socio", "/informes/InformeSocio.jasper", parametros, CONNECTION);
            viewerfx.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Se obtinene datos de la lista Miembros.
     *
     * @return
     */
    private List<Member> datosLista() {
        List listaNombreSocios = new ArrayList();

        MemberDAO_impl memberDAO = new MemberDAO_impl();
        memberDAO.getList().forEach((datos) -> {
            listaNombreSocios.add(datos);
        });
        return listaNombreSocios;
    }

    /**
     * Llena el observableList de tipo Member.
     *
     * @return observableList
     */
    private ObservableList<Member> listaSocio() {
        return FXCollections.observableArrayList(datosLista());
    }

    /**
     * Llena el combo con datos del socio.
     */
    private void llenarComboInformeSocio() {
        cmbSeleccionaSocio.setItems(listaSocio());
    }
    
    /**
     * Recarga el seleccionableInformeSocio.
     */
    private void recargarComboInformSocio(){
        llenarComboInformeSocio();
    }

    /**
     * Muestra los datos de los libros de la biblioteca.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerMostrarDatosLibros(ActionEvent event) {
        String libro = cmbSeleccionaLibro.getSelectionModel().getSelectedItem().toString();
        System.out.println(libro);

        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            HashMap parametros = new HashMap();
            parametros.put("p_isbn", libro);
            System.out.println(parametros);

            JasperViewerFX viewerfx;

            viewerfx = new JasperViewerFX(stage, "Informe Libro", "/informes/InformeLibro.jasper", parametros, CONNECTION);
            viewerfx.show();

        } catch (Exception ex) {
            System.out.println("Error informe libros " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Muestra la lista de libros de la biblioteca.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handleMostrarListaLibros(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            JasperViewerFX viewerfx;

            viewerfx = new JasperViewerFX(stage, "Informe Libros", "/informes/ListadoLibros.jasper", new HashMap(), CONNECTION);
            viewerfx.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Accede a los datos del libro para un uso posterior.
     *
     * @return
     */
    private List<Book> datosListaLibros() {
        List listaDeLibros = new ArrayList();

        BookDAO_impl bookDAO = new BookDAO_impl();
        bookDAO.getList().forEach((datos) -> {
            listaDeLibros.add(datos);
        });
        return listaDeLibros;
    }

    /**
     * Llena el observableList de tipo Book.
     *
     * @return observableList
     */
    private ObservableList<Book> listaLibro() {
        return FXCollections.observableArrayList(datosListaLibros());
    }

    /**
     * Llena el combobox con una lista de libros.
     */
    private void llenarComboInformeLibro() {
        cmbSeleccionaLibro.setItems(listaLibro());
    }
    
    /**
     * Recarga los datos del desplegable libro.
     */
    private void recargarComboInfomeLibro(){
        llenarComboInformeLibro();
    }

    /**
     * Llena un el combobox para seleccionar el tipo de informe que se desea
     * mostrar.
     */
    private void llenarTipoPrestamo() {
        ObservableList<String> listaTipos = FXCollections.observableArrayList("Socio", "Libro");
        cmbSeleccionaTipoPrestamo.setItems(listaTipos);
    }

    /**
     * Llena el combobox según la opción seleccionada en el combobox
     * tipoPrestamos.
     */
    private void llenarComboDinamicoPrestamo() {
        String tipo = cmbSeleccionaTipoPrestamo.getValue();
        btnReportPrestamos.setDisable(true);

        if (tipo.equals("Socio")) {
            ObservableList socio = tablaSocio();
            cmbDinamicoPrestamo.setPromptText("Seleccione un socio");
            btnReportPrestamos.setDisable(false);
            imgButtonReportPrestamos.setImage(new Image("image/Member.png"));
            cmbDinamicoPrestamo.setItems(socio);
        } else if (tipo.equals("Libro")) {
            ObservableList libro = tablaLibro();
            cmbDinamicoPrestamo.setPromptText("Seleccione un libro");
            btnReportPrestamos.setDisable(false);
            imgButtonReportPrestamos.setImage(new Image("image/Books.png"));
            cmbDinamicoPrestamo.setItems(libro);
        }
    }

    /**
     * Se llena el combobox con los valores.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handlerLlenarComboDinamicoPrestamo(ActionEvent event) {
        llenarComboDinamicoPrestamo();
    }

    /**
     * Muestra la lista de prestamos de la biblioteca.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handleMostrarListaPrestamos(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            JasperViewerFX viewerfx;

            viewerfx = new JasperViewerFX(stage, "Informe Prestamos", "/informes/ListadoPrestamo.jasper", new HashMap(), CONNECTION);
            viewerfx.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Se muestra un informe más especifico de los prestamos de un libro o un
     * socio.
     *
     * @param event se le pasa como parametro la acción que realiza.
     */
    @FXML
    private void handleReportPrestamos(ActionEvent event) {

        if (cmbDinamicoPrestamo.getPromptText().equals("Seleccione un socio")) {
            String dni = cmbDinamicoPrestamo.getSelectionModel().getSelectedItem().toString();
            System.out.println("dni " + dni);

            try {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                HashMap parametros = new HashMap();
                parametros.put("p_dni", dni);
                System.out.println(parametros);

                JasperViewerFX viewerfx;

                viewerfx = new JasperViewerFX(stage, "Prestamos Socio", "/informes/InformePrestamoSocio.jasper", parametros, CONNECTION);
                viewerfx.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (cmbDinamicoPrestamo.getPromptText().equals("Seleccione un libro")) {
            String libro = cmbDinamicoPrestamo.getSelectionModel().getSelectedItem().toString();
            System.out.println(libro);

            try {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                HashMap parametros = new HashMap();
                parametros.put("p_isbn", libro);
                System.out.println(parametros);

                JasperViewerFX viewerfx;

                viewerfx = new JasperViewerFX(stage, "Prestamos Libro", "/informes/InformePrestamoLibros.jasper", parametros, CONNECTION);
                viewerfx.show();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
