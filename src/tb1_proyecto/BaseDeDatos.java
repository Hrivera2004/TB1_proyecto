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
    String url = "jdbc:mysql://localhost:3306/maquillaje"; //[maquillaje = nombre de su base de datos]
    String usuario = "root";  // Usuario de MySQL [USUARIO PROPIO]
    String contraseña = "Hect@R1213";
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
        String consulta = "SELECT * FROM " + tabla + " LIMIT 1"; // limitar a 1 fila, más eficiente
        String[] columnas = null;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            columnas = new String[columnCount - 1];
            for (int i = 2; i <= columnCount; i++) {
                columnas[i - 2] = metaData.getColumnLabel(i); // empieza desde 2 (índice 1 es id)
            }
        }
        String temp = String.join(", ", new String[valores.length]).replaceAll("[^,]+", "?");
        String columnasStr = String.join(", ", columnas);
        String insercion = "insert into " + tabla + " (" + columnasStr + ") values(" + temp + ")";
        try (PreparedStatement pstmt = con.prepareStatement(insercion)) {
            for (int i = 0; i < valores.length; i++) {
                pstmt.setObject(i + 1, valores[i]);
            }
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro insertado correctamente en: " + tabla);
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo insertar en la base de datos. Posibles causas:\n"
                    + "1. Llave primaria está repetida\n"
                    + "2. Una de las llaves foráneas referencia un dato inexistente\n"
                    + "3. Uno de los campos nulos no es permitido");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Valor incorrecto en alguno de los campos: " + ex.getMessage());
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
            JOptionPane.showMessageDialog(null, "Registro Eliminado correctamente en " + tabla);
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
            JOptionPane.showMessageDialog(null, "No se puede eliminar porque hay registros que usan la llave primaria en : " + tabla_r);
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

            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar porque referencia a un registro no existente");
            } catch (MysqlDataTruncation ex) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto en alguno de los campos");
            } catch (SQLException ex) {
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

    //VISTAS PARA Reporte de Ventas por Periodo
    public void obtenerVentasMes(String condicion, String orden) {
        if (condicion.isEmpty()) {
            MostrarReporteVista("select * from reporte_ventas_mes_semana group by mes " + orden);
        } else {
            MostrarReporteVista(
                    "select mes, sum(total_ventas) as Total_Mes "
                    + "from reporte_ventas_mes_semana "
                    + "where mes=" + condicion
                    + " group by mes "
                    + "order by mes " + orden);
        }
    }

    public void obtenerVentasSemana(String condicion, String orden) {
        if (condicion.isEmpty()) {
            MostrarReporteVista("select * from reporte_ventas_mes_semana order by semana " + orden);
        } else {
            MostrarReporteVista("select * from reporte_ventas_mes_semana where semana=" + condicion + " group by mes " + "order by mes " + orden);
        }
    }

    public void obtenerComparativaPeriodos(String mes_1, String mes_2, String orden) {
        if (mes_1.isEmpty() || mes_2.isEmpty()) {
            System.out.println("Uno o ambos meses ingresados no son válidos");
        } else {
            MostrarReporteVista("select * from Comparativa_Periodos_Meses where Mes1=" + mes_1 + " and Mes2=" + mes_2 + " order by Diferencia " + orden);
        }
    }

    public void obtenerProductosMasVendidos(String orden) {
        MostrarReporteVista("select * from Productos_Mas_Vendidos order by No_Veces_Vendido " + orden);
    }

    public void obtenerVentasPorCategoria(String categoria, String orden) {
        if (categoria.isEmpty()) {
            MostrarReporteVista("select * from Ventas_Categoría order by Ventas_Categoría " + orden);
        } else {
            MostrarReporteVista("select * from Ventas_Categoría where categoria=\'" + categoria + "\' order by Ventas_Categoría " + orden);
        }
    }

    //VISTAS PARA Inventario y Stock Crítico
    public void obtenerProductosStockBajo(String orden) {
        MostrarReporteVista("select * from Cantidad_Producto_Disponible order by cantidad_disponible " + orden);
    }

    public void obtenerRotacionInventario(String producto, String orden) {
        if (producto.isEmpty()) {
            MostrarReporteVista("select * from Rotacion_Inventario order by Rotación " + orden);
        } else {
            MostrarReporteVista("select * from Rotacion_Inventario where nombre=\'" + producto + "\'" + " order by Rotación " + orden);
        }
    }

    public void obtenerProductosProximosACaducar(String orden) {
        MostrarReporteVista("select * from Caducacion_Cerca order by fecha_caducidad " + orden);
    }

    public void obtenerValorTotalInventario() {
        MostrarReporteVista("select * from Total_Inventario");
    }

    //VISTAS PARA Análisis de Rendimiento de Vendedores
    public void obtenerVentasPorVendedor(String vendedor, String orden) {
        if (vendedor.isEmpty()) {
            MostrarReporteVista("select * from Ventas_Vendedor order by Ventas_Vendedor " + orden);
        } else {
            MostrarReporteVista("select * from Ventas_Vendedor where nombre=\'" + vendedor + "\'" + " order by Ventas_Vendedor " + orden);
        }
    }

    public void obtenerTasaConversionVentas(String vendedor, String orden) {
        if (vendedor.isEmpty()) {
            MostrarReporteVista("select * from Tasa_Conversion order by Radio_Ventas " + orden);
        } else {
            MostrarReporteVista("select * from Tasa_Conversion where nombre=\'" + vendedor + "\'");
        }
    }

    public void obtenerPedidosProcesados(String vendedor, String orden) {
        if (vendedor.isEmpty()) {
            MostrarReporteVista("select * from Pedidos_Procesados order by Pedidos_Procesados " + orden);
        } else {
            MostrarReporteVista("select * from Pedidos_Procesados where nombre=\'" + vendedor + "\'");
        }
    }

    public void obtenerValorPromedioPedido(String vendedor, String orden) {
        if (vendedor.isEmpty()) {
            MostrarReporteVista("select * from Ventas_Promedio order by Ventas_Promedio " + orden);
        } else {
            MostrarReporteVista("select * from Ventas_Promedio where nombre=\'" + vendedor + "\'");
        }
    }

    //VISTAS PARA Estado de Pedidos y Tiempos de Entrega
    public void obtenerPedidosPorEstado(String estado, String orden) {
        if (estado.isEmpty()) {
            MostrarReporteVista("select * from Pedidos_Estado order by Pedidos " + orden);
        } else {
            MostrarReporteVista("select * from Pedidos_Estado where estado=\'" + estado + "\'");
        }
    }

    public void obtenerTiempoPromedioEntrega(String orden) {
        MostrarReporteVista("select * from Tiempo_Promedio_Entrega order by Promedio_Dias" + orden);
    }

    public void obtenerRutasMayorVolumen(String orden) {
        MostrarReporteVista("select * from Rutas_Mayor_Volumen order by Tránsito_Ruta " + orden);
    }

    public void obtenerPedidosRetrasados(String orden) {
        MostrarReporteVista("select * from Pedidos_Retrasados order by Días_Retraso " + orden);
    }

    //VISTAS PARA Rentabilidad por Productos y Categorías
    public void obtenerGananciaPorProducto(String producto, String orden) {
        if (producto.isEmpty()) {
            MostrarReporteVista("select * from Ganancia_Producto order by Ganancia " + orden);
        } else {
            MostrarReporteVista("select * from Ganancia_Producto where nombre=\'" + producto + "\'");
        }
    }

    public void obtenerCategoriasMasRentables(String categoria, String orden) {
        MostrarReporteVista("select * from Categorias_Rentables order by Ganancia_Total " + orden);
    }

    public void obtenerAnalisisDescuentos(String producto, String orden) {
        if (producto.isEmpty()) {
            MostrarReporteVista("select * from Analisis_Descuentos order by Ganancia_Teórica " + orden);
        } else {
            MostrarReporteVista("select * from Analisis_Descuentos where producto=\'" + producto + "\' order by Ganancia_Teórica" + orden);
        }
    }

    public void obtenerRelacionVentasRentabilidad(String producto, String orden) {
        if (producto.isEmpty()) {
            MostrarReporteVista("select * from Relacion_Ventas_Rentabilidad order by ganancia_total " + orden);
        } else {
            MostrarReporteVista("select * from Relacion_Ventas_Rentabilidad where producto=\'" + producto + "\'");
        }
    }

    public void MostrarReporteVista(String sql) {
        try (
                Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = (ResultSetMetaData) rs.getMetaData();
            int columnas = meta.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnas; i++) {
                    System.out.print(meta.getColumnLabel(i) + ": " + rs.getObject(i) + "  ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar consulta: " + e.getMessage());
        }
    }
}
