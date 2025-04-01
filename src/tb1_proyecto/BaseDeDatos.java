/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tb1_proyecto;

/**
 *
 * @author jjlm1
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class BaseDeDatos {
    
    String url = "jdbc:mysql://localhost:3306/maquillaje";
    String usuario = "root";  // Usuario de MySQL
    String contraseña = "juanjonoob69";   // Contraseña de MySQL 
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection con;
    
    public void conexion() throws SQLException{
         // Intentamos conectar
        con = conector(driver, usuario, contraseña, url);
        
        // Verificamos si la conexión se estableció
        if (con != null) {
            System.out.println("Conexión establecida correctamente.");
        } else {
            System.out.println("No se pudo establecer la conexión.");
        }
         // Crear y ejecutar consulta
        mostrar_tabla(con, "producto");
    }
    
    public static Connection conector(String driver, String user, String pass, String url) {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión establecida exitosamente.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return con; // Retornamos la conexión
    }
    
    public static void mostrar_tabla(Connection con,String tabla) throws SQLException{
        String consulta = "SELECT * FROM " + tabla;
    try (Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(consulta)) {
        
        ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        System.out.println("Listado de " + tabla + ":");

        while (rs.next()) {
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                Object value = rs.getObject(i);
                row.append(columnName).append(": ").append(value).append(", ");
            }
            System.out.println(row.substring(0, row.length() - 2)); // Elimina la última coma y espacio
        }
    }
    }
}
