/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.LendingDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConnectionJDBC;
import model.Lending;

/**
 *
 * @author Carlos
 */
public class LendingDAO_impl implements LendingDAO{
    
    /**
     * Variable final que almacena la conexion a la BD.
     */
    private final Connection CONNECTION;

    /**
     * Constructor que inicializa la variable CONNECTION
     */
    public LendingDAO_impl() {
        this.CONNECTION = new ConnectionJDBC().getConnection();
    }

    /**
     * Añade un elemento a la BD.
     * @param lending
     * @return Verdadero o falso.
     */
    @Override
    public boolean add(Lending lending) {
        String sql = "INSERT INTO Lending (lendingDate, returnDate, deliverDate, idBook, idMember) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setString(1, lending.getLendingDate());
            preparedStatement.setString(2, lending.getReturnDate());
            preparedStatement.setString(3, lending.getDeliverDate());
            preparedStatement.setInt(4, lending.getIdBook());
            preparedStatement.setInt(5, lending.getIdMember());
            
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
     * Elimina un dato de tipo lending de la BD.
     * @param lending
     * @return Verdadero o falso.
     */
    @Override
    public boolean update(Lending lending) {
            String sql = "UPDATE Lending SET lendingDate=?, deliverDate=?, returnDate=? WHERE idLending=?";
            PreparedStatement preparedStatement = null;
            
            try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setString(1, lending.getLendingDate());
            preparedStatement.setString(2, lending.getDeliverDate());
            preparedStatement.setString(3, lending.getReturnDate());
            preparedStatement.setInt(4, lending.getIdLending());
            
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

    @Override
    public boolean remove(Lending lending) {
        String sql = "DELETE FROM Lending WHERE idLending=?";
        PreparedStatement preparedStatement = null;
        
        try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setInt(1, lending.getIdLending());
            
            preparedStatement.executeUpdate();
            
            return true;
        }catch(SQLException ex){
            System.out.println("Error establishing connection." + ex.getMessage());
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
     * Da una lista con los datos de los prestamos.
     * @return lista de datos de tipo Lending.
     */
    @Override
    public List<Lending> getList() {
        List<Lending> lendings = new ArrayList<>();
        lendings.clear();
        String sql = "SELECT idLending, lendingDate, deliverDate, returnDate, idBook, idMember FROM Lending";
        PreparedStatement statment = null;
        ResultSet result = null;

        try {
            statment = CONNECTION.prepareStatement(sql);
            result = statment.executeQuery();
            while (result.next()) {
                Lending lending = new Lending();
                lending.setIdLending(Integer.parseInt(result.getString("idLending")));
                lending.setLendingDate(result.getString("lendingDate"));
                lending.setDeliverDate(result.getString("deliverDate"));
                lending.setReturnDate(result.getString("returnDate"));
                lending.setIdBook(Integer.parseInt(result.getString("idBook")));
                lending.setIdMember(Integer.parseInt(result.getString("idMember")));

                lendings.add(lending);
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
        return lendings;
    }
    
    
}
