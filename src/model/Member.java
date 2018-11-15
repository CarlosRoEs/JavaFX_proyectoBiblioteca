/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Carlos
 */
public class Member {
    
    /**
     * Almacena el id del socio.
     */
    private int idMember;
    /**
     * Almacena el dni del socio.
     */
    private String dni;
    /**
     * Almacena el nombre del socio.
     */
    private String firstName;
    /**
     * Almacena el apellido del socio.
     */
    private String lastName;
    /**
     * Almacena la fecha de nacimiento.
     */
    private String birthdate;
    /**
     * Almacena el teléfono fijo.
     */
    private int phone;
    /**
     * Almacena el teléfono móvil.
     */
    private int mobilePhone;
    /**
     * Almacena el email.
     */
    private String email;
    /**
     * Almacena la dirección del socio.
     */
    private String address;
    /**
     * Almacena la foto del socio.
     */
    private String photo;

    /**
     * Constructor sin parametros.
     */
    public Member() {
    }
    
    /**
     * Constructor con parametros.
     * @param firstName
     * @param lastName 
     */
    public Member(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Constructor con parametros.
     * @param dni
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param phone
     * @param mobilePhone
     * @param email
     * @param address
     * @param photo 
     */
    public Member(String dni, String firstName, String lastName, String birthdate, int phone, int mobilePhone, String email, String address, String photo) {
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.phone = phone;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.address = address;
        this.photo = photo;
    }

    /**
     * Constructor con parametros.
     * @param idMember
     * @param dni
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param phone
     * @param mobilePhone
     * @param email
     * @param address
     * @param photo 
     */
    public Member(int idMember, String dni, String firstName, String lastName, String birthdate, int phone, int mobilePhone, String email, String address, String photo) {
        this.idMember = idMember;
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.phone = phone;
        this.mobilePhone = mobilePhone;
        this.email = email;
        this.address = address;
        this.photo = photo;
    }

    /**
     * Devuelve el id del socio.
     * @return id del socio.
     */
    public int getIdMember() {
        return idMember;
    }
    /**
     * Modifica el id del socio.
     * @param idMember 
     */
    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    /**
     * Devuelve el dni del socio.
     * @return dni.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Modifica el dni del socio.
     * @param dni 
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Devuelve el nombre del socio.
     * @return nombre del socio.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Modifica el nombre del socio.
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Devuelve el apellido del socio.
     * @return apellido.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Modifica el apellido del socio.
     * @param lastName 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Devuelve la fecha de nacimiento.
     * @return fecha de nacimiento.
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * Modifica la fecha de nacimiento.
     * @param birthdate 
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Devuelve el teléfono del socio.
     * @return teléfono del socio.
     */
    public int getPhone() {
        return phone;
    }

    /**
     * Modifica el teléfono del socio.
     * @param phone 
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     * Devuelve el móvil del socio.
     * @return el móvil del socio.
     */
    public int getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Modifica el móvil del socio.
     * @param mobilePhone 
     */
    public void setMobilePhone(int mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Devuelve el email del socio.
     * @return el email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifica el email.
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la dirección del socio.
     * @return la dirección.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Modifica la dirección del socio.
     * @param address 
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Devuelve la foto del socio.
     * @return la ruta donde se almacena la foto.
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Modifica la ruta de la foto.
     * @param photo 
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public String toString(){
        return getDni();
    }
  
}
