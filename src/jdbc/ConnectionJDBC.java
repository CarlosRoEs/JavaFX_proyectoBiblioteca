/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connecta con la BD
 * @author Carlos
 */
public class ConnectionJDBC {
    
    /**
     * Da la conexión a la BD.
     * @return Nos retorna la conexion.
     */
    public Connection getConnection(){
        Connection connection = null;
        String url = "jdbc:sqlite:ProyectoBiblioteca.db";

        try {
//            Cargamos el driver SQLite
            Class.forName("org.sqlite.JDBC");
//           Nos conectamos.
            connection = (Connection) DriverManager.getConnection(url);
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return connection;
    }
}
