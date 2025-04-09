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
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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

    public void insertarTabla(String tabla, Object[] valores, int flag) throws SQLException {
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
            if (flag == 1) {
                JOptionPane.showMessageDialog(null, "Se inserto correctamente");
            }
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
                JOptionPane.showMessageDialog(null, "Valor incorrecto en alguno de los campos+");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Valor incorrecto en alguno de los campos-");
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

    public Object[] conseguir_tupla_combobox(String tabla, String valorPK, String atributo) throws SQLException {
        String consulta = "";
        if (atributo.contains("id")) {
            consulta = "SELECT * FROM " + tabla + " WHERE " + atributo + " = ?";
        } else {
            if (tabla.equals("pedidos")) {
                tabla = "Pedidos_Con_Nombre_Cliente";
            } else if (tabla.equals("inventario")) {
                tabla = "Inventario_Con_Nombre_Producto";
            } else if (tabla.equals("detalles_pedido")) {
                tabla = "Detalles_Pedidos_Con_Nombre_Producto";
            } else if (tabla.equals("pagos")) {
                tabla = "Pago_Con_Nombre_Cliente";
            }
            consulta = "SELECT * FROM " + tabla + " WHERE " + atributo + " like ?";
        }
        try (PreparedStatement pstmt = con.prepareStatement(consulta)) {
            if (atributo.contains("id")) {
                pstmt.setString(1, valorPK);
            } else {
                pstmt.setString(1, "%" + valorPK + "%");
            }
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
        ResultSetMetaData meta = null;
        try (PreparedStatement pstmt = con.prepareStatement(consulta); ResultSet rs = pstmt.executeQuery()) {

            meta = (ResultSetMetaData) rs.getMetaData();
            int columnCount = meta.getColumnCount();
            System.out.println(columnCount);
            List<Object[]> rows = new ArrayList<>();
            Set<String> columnasMonetarias = Set.of(
                    "limite_credito", "precio_unitario", "subtotal", "monto", "total", "precio_compra", "precio_venta_sugerido"
            );

            while (rs.next() && rows.size() < 5) {
                Object[] values = new Object[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rs.getObject(i);
                    String columnName = meta.getColumnLabel(i).toLowerCase();
                    if (columnasMonetarias.contains(columnName) && valor != null) {
                        values[i - 1] = valor.toString() + " LPS";
                    } else {
                        values[i - 1] = valor;
                    }

                    System.out.println("Column " + i + ": " + values[i - 1]); // Debug each value
                }

                rows.add(values);
            }

            return rows.toArray(new Object[0][]);
        }
    }

    public Object[][] tuplas_select_cincocombobox(String tabla, String valorPK, String atributo) throws SQLException {
        // Query to get first 5 rows (syntax may vary by database)

        if (tabla.equals("pedidos")) {
            tabla = "Pedidos_Con_Nombre_Cliente";
        } else if (tabla.equals("inventario")) {
            tabla = "Inventario_Con_Nombre_Producto";
        } else if (tabla.equals("detalles_pedido")) {
            tabla = "Detalles_Pedidos_Con_Nombre_Producto";
        } else if (tabla.equals("pagos")) {
            tabla = "Pago_Con_Nombre_Cliente";
        }

        String consulta = "SELECT * FROM " + tabla + " where " + atributo + " like " + "\'%" + valorPK + "%\'" + "LIMIT 5";
        ResultSetMetaData meta = null;
        try (PreparedStatement pstmt = con.prepareStatement(consulta); ResultSet rs = pstmt.executeQuery()) {

            meta = (ResultSetMetaData) rs.getMetaData();
            int columnCount = meta.getColumnCount();
            System.out.println(columnCount);
            List<Object[]> rows = new ArrayList<>();
            Set<String> columnasMonetarias = Set.of(
                    "limite_credito", "precio_unitario", "subtotal", "monto", "total", "precio_compra", "precio_venta_sugerido"
            );

            while (rs.next() && rows.size() < 5) {
                Object[] values = new Object[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    Object valor = rs.getObject(i);
                    String columnName = meta.getColumnLabel(i).toLowerCase();
                    if (columnasMonetarias.contains(columnName) && valor != null) {
                        values[i - 1] = valor.toString() + " LPS";
                    } else {
                        values[i - 1] = valor;
                    }

                    System.out.println("Column " + i + ": " + values[i - 1]); // Debug each value
                }

                rows.add(values);
            }

            return rows.toArray(new Object[0][]);
        } catch (Exception e) {
            return null;
        }
    }

    //orden = desc/asc
    //VISTAS PARA Reporte de Ventas por Periodo
    public Object[][] obtenerVentasMes(String condicion, String orden) {
        if (condicion.isEmpty()) {
            return MostrarReporteVista("select * from reporte_ventas_mes_semana order by mes " + orden);
        } else {
            return MostrarReporteVista(
                    "select mes, sum(total_ventas) as Total_Mes "
                    + "from reporte_ventas_mes_semana "
                    + "where mes=" + condicion
                    + " group by mes "
                    + "order by mes " + orden);
        }
    }

    public Object[][] obtenerVentasSemana(String condicion, String orden) {
        if (condicion.isEmpty()) {
            return MostrarReporteVista("select * from reporte_ventas_mes_semana order by semana " + orden);
        } else {
            return MostrarReporteVista(
                    "select semana,sum(total_ventas) as Total_semana from reporte_ventas_mes_semana "
                    + "where semana=" + condicion
                    + " group by semana "
                    + "order by semana " + orden);
        }
    }

    public Object[][] obtenerComparativaPeriodos(String mes_1, String a_1, String mes_2, String a_2, String orden) {
        if (mes_1.isEmpty() || mes_2.isEmpty()) {
            System.out.println("Uno o ambos meses ingresados no son válidos");
            return new Object[][]{{"Error"}, {"Meses no válidos"}};
        }
        return MostrarReporteVista(
                "select * from Comparativa_Periodos_Meses "
                + "where Mes1=" + mes_1
                + " and Mes2=" + mes_2
                + " and Año1=" + a_1
                + " and Año2=" + a_2
                + " order by Diferencia " + orden);
    }

    public Object[][] obtenerProductosMasVendidos(String orden) {
        return MostrarReporteVista(
                "select * from Productos_Mas_Vendidos "
                + "order by No_Veces_Vendido " + orden);
    }

    public Object[][] obtenerVentasPorCategoria(String categoria, String orden) {
        if (categoria.isEmpty()) {
            return MostrarReporteVista("select * from Ventas_Categoria order by Ventas_Categoria " + orden);
        } else {
            return MostrarReporteVista(
                    "select * from Ventas_Categoria "
                    + "where nombre like \'%" + categoria + "%\' "
                    + "order by Ventas_Categoria " + orden);
        }
    }

    //VISTAS PARA Inventario y Stock Crítico
    public Object[][] obtenerProductosStockBajo(String umbral, String orden) {
        return MostrarReporteVista(
                "select * "
                + "from cantidad_producto_disponible where cantidad_disponible <=" + umbral
                + " order by cantidad_disponible " + orden);
    }

    public Object[][] obtenerRotacionInventario(String producto, String orden) {
        if (producto.isEmpty()) {
            return MostrarReporteVista("select * from rotacion_inventario order by rotacion " + orden);
        } else {
            return MostrarReporteVista("select * from rotacion_inventario where nombre like \'%" + producto + "%\' order by rotacion " + orden);
        }
    }

    public Object[][] obtenerProductosProximosACaducar(String orden) {
        return MostrarReporteVista("select * from caducacion_cerca order by fecha_caducidad " + orden);
    }

    public Object[][] obtenerValorTotalInventario() {
        return MostrarReporteVista("select * from total_inventario");
    }

    //VISTAS PARA Análisis de Rendimiento de Vendedores
    public Object[][] obtenerVentasPorVendedor(String vendedor, String orden) {
        if (vendedor.isEmpty()) {
            return MostrarReporteVista("select * from ventas_vendedor order by ventas_vendedor " + orden);
        } else {
            return MostrarReporteVista("select * from ventas_vendedor where nombre like \'%" + vendedor + "%\' order by ventas_vendedor " + orden);
        }
    }

    public Object[][] obtenerTasaConversionVentas(String vendedor, String orden) {
        if (vendedor.isEmpty()) {
            return MostrarReporteVista("select * from tasa_conversion order by radio_ventas " + orden);
        } else {
            return MostrarReporteVista("select * from tasa_conversion where nombre like \'%" + vendedor + "%\' order by radio_ventas " + orden);
        }
    }

    public Object[][] obtenerPedidosProcesados(String vendedor, String orden) {
        if (vendedor.isEmpty()) {
            return MostrarReporteVista("select * from pedidos_procesados order by pedidos_procesados " + orden);
        } else {
            return MostrarReporteVista("select * from pedidos_procesados where nombre like \'%" + vendedor + "%\' order by pedidos_procesados " + orden);
        }
    }

    public Object[][] obtenerValorPromedioPedido(String vendedor, String orden) {
        if (vendedor.isEmpty()) {
            return MostrarReporteVista("select * from ventas_promedio order by ventas_promedio " + orden);
        } else {
            return MostrarReporteVista("select * from ventas_promedio where nombre like \'%" + vendedor + "%\' order by ventas_promedio " + orden);
        }
    }

    //VISTAS PARA Estado de Pedidos y Tiempos de Entrega
    public Object[][] obtenerPedidosPorEstado(String estado, String orden) {
        if (estado.isEmpty()) {
            return MostrarReporteVista("select * from Pedidos_Estado order by Pedidos " + orden);
        } else {
            return MostrarReporteVista("select * from Pedidos_Estado where estado like \'" + estado + "\'");
        }
    }

    public Object[][] obtenerTiempoPromedioEntrega(String orden) {
        return MostrarReporteVista("select * from Tiempo_Promedio_Entrega order by Promedio_Dias " + orden);
    }

    public Object[][] obtenerRutasMayorVolumen(String orden) {
        return MostrarReporteVista("select * from Rutas_Mayor_Volumen order by Transito_Ruta " + orden);
    }

    public Object[][] obtenerPedidosRetrasados(String orden) {
        return MostrarReporteVista("select * from Pedidos_Retrasados order by Dias_Retraso " + orden);
    }

    //VISTAS PARA Rentabilidad por Productos y Categorías
    public Object[][] obtenerGananciaPorProducto(String producto, String orden) {
        if (producto.isEmpty()) {
            return MostrarReporteVista("select * from Ganancia_Producto order by Ganancia " + orden);
        } else {
            return MostrarReporteVista("select * from Ganancia_Producto where nombre like \'%" + producto + "%\' order by Ganancia " + orden);
        }
    }

    public Object[][] obtenerCategoriasMasRentables(String orden) {
        return MostrarReporteVista("select * from Categorias_Rentables order by Ganancia_Total " + orden);
    }

    public Object[][] obtenerAnalisisDescuentos(String producto, String orden) {
        if (producto.isEmpty()) {
            return MostrarReporteVista("select * from Analisis_Descuentos order by Ganancia_Teorica " + orden);
        } else {
            return MostrarReporteVista("select * from Analisis_Descuentos where producto like \'%" + producto + "%\' order by Ganancia_Teorica " + orden);
        }
    }

    public Object[][] obtenerRelacionVentasRentabilidad(String producto, String orden) {
        if (producto.isEmpty()) {
            return MostrarReporteVista("select * from Relacion_Ventas_Rentabilidad order by ganancia_total " + orden);
        } else {
            return MostrarReporteVista("select * from Relacion_Ventas_Rentabilidad where producto like \'%" + producto + "%\' order by ganancia_total " + orden);
        }
    }

    public Object[][] MostrarReporteVista(String sql) {
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql + " LIMIT 11")) {

            ResultSetMetaData meta = (ResultSetMetaData) rs.getMetaData();
            int columnas = meta.getColumnCount();

            List<Object[]> resultList = new ArrayList<>();

            Object[] columnNames = new Object[columnas];
            for (int i = 1; i <= columnas; i++) {
                columnNames[i - 1] = meta.getColumnLabel(i);
            }
            resultList.add(columnNames);

            Set<String> columnasMonetarias = Set.of(
                    "total_ventas", "ventas_mes_1", "ventas_mes_2", "diferencia",
                    "ventas_categoria", "valor_total_inventario", "precio_compra", "precio_venta_sugerido",
                    "ganancia", "ganancia_total", "ganancia_teórica", "ganancia_real",
                    "margen_unitario", "ventas_promedio", "ventas_vendedor", "total_descuento", "total_mes", "total_semana"
            );

            while (rs.next()) {
                Object[] row = new Object[columnas];
                for (int i = 1; i <= columnas; i++) {
                    String columnName = meta.getColumnLabel(i).toLowerCase();
                    Object valor = rs.getObject(i);

                    if (columnasMonetarias.contains(columnName) && valor instanceof Number) {
                        row[i - 1] = valor.toString() + " LPS";
                    } else {
                        row[i - 1] = valor;
                    }
                }
                resultList.add(row);
            }

            return resultList.toArray(new Object[0][]);

        } catch (SQLException e) {
            System.out.println("Error al ejecutar consulta: " + e.getMessage());
            return null;
        }
    }

}
