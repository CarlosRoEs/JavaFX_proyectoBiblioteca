/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.MemberDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdbc.ConnectionJDBC;
import model.Member;

/**
 *
 * @author Carlos
 */
public class MemberDAO_impl implements MemberDAO {

    /**
     * Variable final que almacena la conexion a la BD.
     */
    private final Connection CONNECTION;
    
    /**
     * Constructor que inicializa la variable CONNECTION
     */
    public MemberDAO_impl() {
        this.CONNECTION = (Connection) new ConnectionJDBC().getConnection();
    }

    /**
     * Añade datos de tipo Member a la BD.
     * @param member
     * @return Verdadero o falso.
     */
    @Override
    public boolean add(Member member) {
        String sql = "INSERT INTO Member (dni, firstName, lastName,birthdate, phone, mobilePhone, email, address, photo) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setString(1, member.getDni());
            preparedStatement.setString(2, member.getFirstName());
            preparedStatement.setString(3, member.getLastName());
            preparedStatement.setString(4, member.getBirthdate());
            preparedStatement.setInt(5, member.getPhone());
            preparedStatement.setInt(6, member.getMobilePhone());
            preparedStatement.setString(7, member.getEmail());
            preparedStatement.setString(8, member.getAddress());
            preparedStatement.setString(9, member.getPhoto());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            System.out.println("Error adding member." + ex.getMessage());
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
     * Actualiza el dato de tipo Member en la BD.
     * @param member
     * @return Verdadero o falso.
     */
    @Override
    public boolean update(Member member) {
        String sql = "UPDATE Member SET dni=?, firstName=?, lastName=?,birthdate=?, phone=?, mobilePhone=?, email=?, address=?, photo=? WHERE idMember=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setString(1, member.getDni());
            preparedStatement.setString(2, member.getFirstName());
            preparedStatement.setString(3, member.getLastName());
            preparedStatement.setString(4, member.getBirthdate());
            preparedStatement.setInt(5, member.getPhone());
            preparedStatement.setInt(6, member.getMobilePhone());
            preparedStatement.setString(7, member.getEmail());
            preparedStatement.setString(8, member.getAddress());
            preparedStatement.setString(9, member.getPhoto());
            preparedStatement.setInt(10, member.getIdMember());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            System.out.println("Error updating member." + ex.getMessage());
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
     * Elimina el dato de tipo Member de la BD.
     * @param member
     * @return Verdadero o falso.
     */
    @Override
    public boolean remove(Member member) {
        String sql = "DELETE FROM Member WHERE idMember=?";
        PreparedStatement preparedStatement = null;
        
        try{
            preparedStatement = CONNECTION.prepareStatement(sql);
            preparedStatement.setInt(1, member.getIdMember());
            
            preparedStatement.executeUpdate();
            
            return true;
        }catch(SQLException ex){
            System.out.println("Error deleting member.");
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
     * Da la lista con los datos de tipo libro.
     * @return Lista de objetos de tipo Member.
     */
    @Override
    public List<Member> getList() {
        List<Member> members = new ArrayList<>();
        members.clear();
        String sql = "SELECT idMember, dni, firstName, lastName, birthdate, phone, mobilePhone, email, address, photo FROM Member";
        PreparedStatement sentencia = null;
        ResultSet rs = null;

        try {
            sentencia = CONNECTION.prepareStatement(sql);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                Member member = new Member();
                member.setIdMember(Integer.parseInt(rs.getString("idMember")));
                member.setDni(rs.getString("dni"));
                member.setFirstName(rs.getString("firstName"));
                member.setLastName(rs.getString("lastName"));
                member.setBirthdate(rs.getString("birthdate"));
                member.setPhone(Integer.parseInt(rs.getString("phone")));
                member.setMobilePhone(Integer.parseInt(rs.getString("mobilePhone")));
                member.setEmail(rs.getString("email"));
                member.setAddress(rs.getString("address"));
                member.setPhoto(rs.getString("photo"));

                members.add(member);

            }
        } catch (Exception e) {
            System.out.println("The list has not been returned."+ e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            try {
                rs.close();
                sentencia.close();
                CONNECTION.close();

            } catch (SQLException ex) {
                System.out.println("The resources could not be closed. " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return members;
    }
 
}
