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
public class Lending {
    
    /**
     * Almacena el id del prestamo.
     */
    private int idLending;
    /**
     * Almacena la fecha de prestamo.
     */
    private String lendingDate;
    /**
     * Almacena la fecha prevista de devolución.
     */
    private String deliverDate;
    /**
     * Almacena la fecha de devolución del prestamo.
     */
    private String returnDate;
    /**
     * Almacena el id del libro.
     */
    private int idBook;
    /**
     * Almacena el id del Socio.
     */
    private int idMember;

    /**
     * Constructor sin parametros.
     */
    public Lending() {
    }

    /**
     * Constructor con parametros.
     * @param idLending
     * @param lendingDate
     * @param deliverDate
     * @param returnDate 
     */
    public Lending(int idLending, String lendingDate, String deliverDate, String returnDate) {
        this.idLending = idLending;
        this.lendingDate = lendingDate;
        this.deliverDate = deliverDate;
        this.returnDate = returnDate;
        
    }
    
    /**
     * Constructor con parametros.
     * @param lendingDate
     * @param deliverDate
     * @param returnDate
     * @param idBook
     * @param idMember 
     */
    public Lending(String lendingDate, String deliverDate, String returnDate, int idBook, int idMember) {
        this.lendingDate = lendingDate;
        this.deliverDate = deliverDate;
        this.returnDate = returnDate;
        this.idBook = idBook;
        this.idMember = idMember;
    }
    
    /**
     * Constructor con parametros.
     * @param lendingDate
     * @param deliverDate
     * @param idBook
     * @param idMember 
     */
    public Lending(String lendingDate, String deliverDate, int idBook, int idMember) {
        this.lendingDate = lendingDate;
        this.deliverDate = deliverDate;
        this.idBook = idBook;
        this.idMember = idMember;
    }

    /**
     * Constructor con parametros.
     * @param idLending
     * @param lendingDate
     * @param deliverDate
     * @param returnDate
     * @param idBook
     * @param idMember 
     */
    public Lending(int idLending, String lendingDate, String deliverDate, String returnDate, int idBook, int idMember) {
        this.idLending = idLending;
        this.lendingDate = lendingDate;
        this.deliverDate = deliverDate;
        this.returnDate = returnDate;
        this.idBook = idBook;
        this.idMember = idMember;
    }

    /**
     * Devuelve el id del prestamo.
     * @return id del prestamo.
     */
    public int getIdLending() {
        return idLending;
    }

    /**
     * Modifica el id del prestamo.
     * @param idLending 
     */
    public void setIdLending(int idLending) {
        this.idLending = idLending;
    }

    /**
     * Devuelve la fecha de prestamo.
     * @return fecha de prestamo.
     */
    public String getLendingDate() {
        return lendingDate;
    }

    /**
     * Modifica la fecha de prestamo.
     * @param lendingDate 
     */
    public void setLendingDate(String lendingDate) {
        this.lendingDate = lendingDate;
    }

    /**
     * Devuelve la fecha prevista de devolución.
     * @return fecha prevista de devolución.
     */
    public String getDeliverDate() {
        return deliverDate;
    }

    /**
     * Modifica la fecha prevista de devolución.
     * @param deliverDate 
     */
    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    /**
     * Devuelve la fecha de devolución.
     * @return fecha de devolución.
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * Modifica la fecha de devolución.
     * @param returnDate 
     */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Devuelve el id del libro.
     * @return id del libro.
     */
    public int getIdBook() {
        return idBook;
    }

    /**
     * Modifica el id del libro.
     * @param idBook 
     */
    public void setIdBook(int idBook) {
        this.idBook = idBook;
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
    
    
    
    
    
    
    
}


