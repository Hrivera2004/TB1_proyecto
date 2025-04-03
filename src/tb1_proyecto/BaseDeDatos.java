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
import java.sql.*;
        
public class BaseDeDatos {

    String url = "jdbc:mysql://localhost:3306/maquillaje";
    String usuario = "root";  // Usuario de MySQL
    String contraseña = "juanjonoob69";   // Contraseña de MySQL 
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection con;

    public void conexion() throws SQLException {
        // Intentamos conectar
        con = conector(driver, usuario, contraseña, url);
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

  public static void mostrar_tabla(Connection con, String tabla) throws SQLException {
        String consulta = "select * from " + tabla;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {

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

    public static void insertarTabla(Connection con, String tabla, Object[] valores) throws SQLException {
        String temp = String.join(", ", new String[valores.length]).replaceAll("[^,]+", "?");
        String insercion = "insert into " + tabla + " values(" + temp + ")";
        try (PreparedStatement pstmt = con.prepareStatement(insercion)) {
            for (int i = 0; i < valores.length; i++) {
                pstmt.setObject(i + 1, valores[i]);
            }
            pstmt.executeUpdate();
            System.out.println("Registro insertado correctamente en " + tabla);
        }
    }
    
    public static void deleteTabla(Connection con, String tabla,int id_fila) throws SQLException {
         String consulta = "select * from " + tabla;
          ResultSetMetaData metaData=null;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {

            metaData = (ResultSetMetaData) rs.getMetaData();
        }
         String eliminar = "delete from " + tabla + " where "+metaData.getColumnName(1)+"="+id_fila;
        try (PreparedStatement pstmt = con.prepareStatement(eliminar)) {
            pstmt.executeUpdate();
            System.out.println("Registro Eliminado correctamente en " + tabla);
        }
    }
    public static void updateTabla(Connection con, String tabla, int id_fila, Object[] valores) throws SQLException {
        String consulta = "select * from " + tabla;
        ResultSetMetaData metaData = null;

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {

            metaData = (ResultSetMetaData) rs.getMetaData();
        }
        String busqueda = "select * from " + tabla + " where " + metaData.getColumnName(1) + "=" + id_fila;
        Boolean existe = false;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(busqueda)) {
            if (rs.next()) {
                existe = true;
            } else {
                existe = false;
            }
        }
        if (existe) {
            String[] columnas = new String[metaData.getColumnCount()];
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                columnas[i] = metaData.getColumnName(i + 1);
            }
            String temp = String.join("=?, ", columnas) + "=?";
            String update = "update " + tabla + " set " + temp + " where " + metaData.getColumnName(1) + "=" + id_fila;
            try (PreparedStatement pstmt = con.prepareStatement(update)) {
                for (int i = 0; i < valores.length; i++) {
                    pstmt.setObject(i + 1, valores[i]);
                }
                pstmt.executeUpdate();
                System.out.println("Registro actualizado correctamente en " + tabla);
            }
        }else{
            System.out.println("No existe el registros");
        }
    }
}
