/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.BookDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConnectionJDBC;
import model.Book;

/**
 * 
 * @author Carlos
 */
public class BookDAO_impl implements BookDAO {

    /**
     * Variable final que almacena la conexion a la BD.
     */
    private final Connection CONNECTION;

    /**
     * Constructor que inicializa la variable CONNECTION
     */
    public BookDAO_impl() {
        this.CONNECTION = new ConnectionJDBC().getConnection();
    }

    /**
     * Añade un dato de tipo book a la BD.
     * @param book 
     * @return Verdadero o falso.
     */
    @Override
    public boolean add(Book book) {
        String sql = "INSERT INTO Book (isbn, title, author, publisher, year_edition, kind, cover) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setInt(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setInt(5, book.getYear_edition());
            preparedStatement.setString(6, book.getKind());
            preparedStatement.setString(7, book.getCover());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException ex) {
            System.out.println("Error establishing connection." + ex.getMessage());
            ex.printStackTrace();

            return false;
        } finally {
            try {
                preparedStatement.close();
                CONNECTION.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources." + ex.getMessage());
            }
        }
    }

    /**
     * Actualiza el dato de tipo libro de la BD.
     * @param book
     * @return Verdadero o falso.
     */
    @Override
    public boolean update(Book book) {
        String sql = "UPDATE Book SET isbn=?, title=?, author=?, publisher=?, year_edition=?, kind=?, cover=? WHERE idBook=?";
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setInt(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setInt(5, book.getYear_edition());
            preparedStatement.setString(6, book.getKind());
            preparedStatement.setString(7, book.getCover());
            preparedStatement.setInt(8, book.getIdBook());
            
            preparedStatement.executeUpdate();
            
            return true;
            
        } catch (SQLException ex) {
            System.out.println("Error updating book." + ex.getMessage());
            ex.printStackTrace();

            return false;
        } finally {
            try {
                preparedStatement.close();
                CONNECTION.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources" + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    /**
     * Elimina un dato de tipo book de la BD.
     * @param book
     * @return Verdadero o falso.
     */
    @Override
    public boolean remove(Book book) {
        String sql = "DELETE FROM Book WHERE idBook=?";
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setInt(1, book.getIdBook());
            
            preparedStatement.executeUpdate();
            
            return true;
        }catch(SQLException ex){
            System.out.println("Error deleting book.");
            ex.printStackTrace();
            return false;
        }finally{
            try {
                preparedStatement.close();
                CONNECTION.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources" + ex.getMessage());
                ex.printStackTrace();
            }
        }
        
    }

    /**
     * Da una lista con los datos de los libros.
     * @return lista de datos de tipo Book.
     */
    @Override
    public List<Book> getList() {
        List<Book> books = new ArrayList<>();
        books.clear();
        String sql = "SELECT idBook, isbn, title, author, publisher, year_edition, kind, cover FROM Book";
        PreparedStatement statment = null;
        ResultSet result = null;

        try {
            statment = CONNECTION.prepareStatement(sql);
            result = statment.executeQuery();
            while (result.next()) {
                Book book = new Book();
                book.setIdBook(Integer.parseInt(result.getString("idBook")));
                book.setIsbn(Integer.parseInt(result.getString("isbn")));
                book.setTitle(result.getString("title"));
                book.setAuthor(result.getString("author"));
                book.setPublisher(result.getString("publisher"));
                book.setYear_edition(Integer.parseInt(result.getString("year_edition")));
                book.setKind(result.getString("kind"));
                book.setCover(result.getString("cover"));

                books.add(book);
            }
        } catch (SQLException ex) {
            System.out.println("The list has not been returned." + ex.getMessage());
            ex.printStackTrace();

            return null;
        } finally {
            try {
                statment.close();
                result.close();
                CONNECTION.close();
            } catch (SQLException ex) {
                System.out.println("The resources could not be closed. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return books;
    }
}
