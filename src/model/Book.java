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
public class Book {
    /**
     * Almacena el id del libro.
     */
    private int idBook;
    /**
     * Almacena el isbn del libro.
     */
    private int isbn;
    /**
     * Almacena el titulo del libro.
     */
    private String title;
    /**
     * Almacena el nombre del autor del libro.
     */
    private String author;
    /**
     * Almacena la editorial del libro.
     */
    private String publisher;
    /**
     * Almacena el año de edición del libro.
     */
    private int year_edition;
    /**
     * Almacena el género del libro.
     */
    private String kind;
    /**
     * Almacena la carátula del libro.
     */
    private String cover;

    /**
     * Constructor sin parametros.
     */
    public Book() {
    }
    
    /**
     * Constructor con parametros.
     * @param title titulo del libro
     * @param cover Portada del libro.
     */
    public Book(String title, String cover) {
        this.title = title;
        this.cover = cover;
    }
    
    /**
     * Constructor con parametros.
     * @param isbn
     * @param title
     * @param author
     * @param publisher
     * @param year_edition
     * @param kind
     * @param cover 
     */
    public Book(int isbn, String title, String author, String publisher, int year_edition, String kind, String cover) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year_edition = year_edition;
        this.kind = kind;
        this.cover = cover;
    }

    /**
     * Constructor con parametros.
     * @param idBook
     * @param isbn
     * @param title
     * @param author
     * @param publisher
     * @param year_edition
     * @param kind
     * @param cover 
     */
    public Book(int idBook, int isbn, String title, String author, String publisher, int year_edition, String kind, String cover) {
        this.idBook = idBook;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year_edition = year_edition;
        this.kind = kind;
        this.cover = cover;
    }

    /**
     * Devuelve el id del libro.
     * @return el id.
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
     * Devuelve el isbn del libro.
     * @return el isbn.
     */
    public int getIsbn() {
        return isbn;
    }
    
    /**
     * Modifica el isbn del libro.
     * @param isbn 
     */
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
    
    /**
     * Devulve el titulo del libro.
     * @return titulo del libro.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Modifica el titulo del libro.
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Delvuelve el nombre del autor.
     * @return autor.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Modifica el nombre del autor.
     * @param author 
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Devuelve el nombre de la editorial.
     * @return la editorial.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Modifica el nombre de la editorial.
     * @param publisher 
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Devuelve el año de la editorial.
     * @return el año de la editorial.
     */
    public int getYear_edition() {
        return year_edition;
    }

    /**
     * Modifica el año de la editorial.
     * @param year_edition 
     */
    public void setYear_edition(int year_edition) {
        this.year_edition = year_edition;
    }

    /**
     * Devuelve el género del libro.
     * @return el género.
     */
    public String getKind() {
        return kind;
    }

    /**
     * Modifica el género del libro.
     * @param kind 
     */
    public void setKind(String kind) {
        this.kind = kind;
    }
    
    /**
     * Devuelve la cáratula/portada del libro.
     * @return caratula/portada.
     */
    public String getCover() {
        return cover;
    }
    
    /**
     * Modifica la carátula del libro.
     * @param cover 
     */
    public void setCover(String cover) {
        this.cover = cover;
    }
    
    /**
     * 
     * @return el isbn como String. 
     */
    public String toString(){
        return String.valueOf(getIsbn());
    }
}
