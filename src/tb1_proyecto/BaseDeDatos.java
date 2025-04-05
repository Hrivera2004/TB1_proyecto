/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tb1_proyecto;

/**
 *
 * @author jjlm1
 */
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class BaseDeDatos {

    ///BASE DE DATOS
    String url = "jdbc:mysql://localhost:3306/proyecto"; //[maquillaje = nombre de su base de datos]
    String usuario = "root";  // Usuario de MySQL [USUARIO PROPIO]
    String contraseña = "Deltarune22";   // Contraseña de MySQL [CONTRASENA PROPIA]
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection con;

    public void conexion() throws SQLException {
        // Intentamos conectar
        con = conector(driver, usuario, contraseña, url);
    }

    public Connection conector(String driver, String user, String pass, String url) {
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

    public void mostrar_tabla(String tabla) throws SQLException {
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

    public void insertarTabla(String tabla, Object[] valores) throws SQLException {
        String temp = String.join(", ", new String[valores.length]).replaceAll("[^,]+", "?");
        String insercion = "insert into " + tabla + " values(" + temp + ")";
        try (PreparedStatement pstmt = con.prepareStatement(insercion)) {
            for (int i = 0; i < valores.length; i++) {
                pstmt.setObject(i + 1, valores[i]);
            }
            pstmt.executeUpdate();
           JOptionPane.showMessageDialog(null, "Registro insertado correctamente en: "+tabla);
        }catch (SQLIntegrityConstraintViolationException ex){
            JOptionPane.showMessageDialog(null, "No se pudo insertar en la base de datos.Posibles causas:\n"
                    + "1. Llave primaria esta repetida\n"
                    + "2. Una de las llaves foraneas referencia un dato inexistente\n"
                    + "3. Uno de los campos nulos no es permitido");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Valor incorrecto en alguno de los campos");
        }
    }

    public void deleteTabla(String tabla, int id_fila) throws SQLException {
        String consulta = "select * from " + tabla;
        ResultSetMetaData metaData = null;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {

            metaData = (ResultSetMetaData) rs.getMetaData();
        }
        String eliminar = "delete from " + tabla + " where " + metaData.getColumnName(1) + "=" + id_fila;
        try (PreparedStatement pstmt = con.prepareStatement(eliminar)) {
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Registro Eliminado correctamente en " + tabla);
        } catch (SQLIntegrityConstraintViolationException ex) {
            String[] partes = ex.getMessage().split("fails \\(");
            String tabla_r = null;
            if (partes.length > 1) {
                // Paso 2: separar por coma y quedarte con la primera parte que tiene el nombre de la tabla
                String dentroParentesis = partes[1].split(",")[0]; // esto da: "ventas"
                tabla_r = dentroParentesis.replace("`", ""); // limpia las comillas invertidas
                String[] partes1 = tabla_r.split("\\.");
                tabla_r = partes1[1];
            }
            JOptionPane.showMessageDialog(null,"No se puede eliminar porque hay registros que usan la llave primaria en : " + tabla_r);
        }
    }

    

    public void updateTabla(String tabla, int id_fila, Object[] valores) throws SQLException {
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
                JOptionPane.showMessageDialog(null, "Registro actualizado correctamente en " + tabla);
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar porque referencia a un registro no existente");
            }catch(MysqlDataTruncation ex){
                 JOptionPane.showMessageDialog(null, "Valor incorrecto en alguno de los campos");
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Valor incorrecto en alguno de los campos");
            }
        } else {
            System.out.println("No existe el registros");
        }
    }

    public boolean mostrarTupla(String tabla, String valorPK) throws SQLException {
        String columnaPK = null;
        boolean bandera = false;
        DatabaseMetaData dbMeta = con.getMetaData();
        try (ResultSet pkRs = dbMeta.getPrimaryKeys(null, null, tabla)) {
            if (pkRs.next()) {
                columnaPK = pkRs.getString("COLUMN_NAME");
            } else {
                System.out.println("No se encontró clave primaria para la tabla: " + tabla);
                return false;  // No hay clave primaria
            }
        }

        String consulta = "SELECT * FROM " + tabla + " WHERE " + columnaPK + " = ?";

        try (PreparedStatement pstmt = con.prepareStatement(consulta)) {
            pstmt.setString(1, valorPK);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bandera = true;
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                System.out.println("Registro en " + tabla + " con " + columnaPK + " = " + valorPK + ":");

                do {
                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        Object value = rs.getObject(i);
                        row.append(columnName).append(": ").append(value).append(", ");
                    }
                    System.out.println(row.substring(0, row.length() - 2));
                } while (rs.next());
            } else {
                System.out.println("No se encontró un registro con " + columnaPK + " = " + valorPK);
            }
        }

        return bandera;
    }

    public Object[] conseguir_tupla(String tabla, String valorPK) throws SQLException {
        String columnaPK = null;
        DatabaseMetaData dbMeta = con.getMetaData();
        try (ResultSet pkRs = dbMeta.getPrimaryKeys(null, null, tabla)) {
            if (pkRs.next()) {
                columnaPK = pkRs.getString("COLUMN_NAME");
            } else {
                return null;
            }
        }
        String consulta = "SELECT * FROM " + tabla + " WHERE " + columnaPK + " = ?";
        try (PreparedStatement pstmt = con.prepareStatement(consulta)) {
            pstmt.setString(1, valorPK);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                Object[] values = new Object[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    values[i - 1] = rs.getObject(i);
                }

                return values;
            }
        }
        return null;
    }

    public Object[][] tuplas_select_cinco(String tabla) throws SQLException {
        // Query to get first 5 rows (syntax may vary by database)
        String consulta = "SELECT * FROM " + tabla + " LIMIT 5";

        try (PreparedStatement pstmt = con.prepareStatement(consulta); ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println(columnCount);
            List<Object[]> rows = new ArrayList<>();

            while (rs.next() && rows.size() < 5) {
                Object[] values = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    values[i] = rs.getObject(i + 1);
                    System.out.println("Column " + (i + 1) + ": " + values[i]); // Debug each value

                }
                rows.add(values);

            }

            return rows.toArray(new Object[0][]);
        }
    }
}
