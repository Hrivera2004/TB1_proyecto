/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tb1_proyecto;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author hriverav
 */
public class FileManager {

    private File csv;

    public FileManager(String directory) {
        csv = new File(directory);
    }

    public FileManager(File file) {
        csv = file;
    }

    public void ReadFile(BaseDeDatos BD) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csv), "UTF-8"))) {
            List<String[]> rows = reader.readAll();
            Path path = Paths.get(csv.getAbsolutePath());
            String fileName = path.getFileName().toString();
            String table_name = fileName.substring(fileName.indexOf("-") + 1, fileName.lastIndexOf("."));
            System.out.println(table_name);

            boolean first = false;
            for (String[] row : rows) {
                if (first) {

                    Object[] list = new Object[row.length - 1];
                    for (int i = 1; i < row.length; i++) {
                        if (row[i].toString().isBlank()) {
                            list[i - 1] = null;
                        } else {
                            list[i - 1] = row[i];
                        }

                        System.out.print(list[i - 1] + ", ");
                    }
                    System.out.println("");
                    BD.insertarTabla(table_name, list);
                    JOptionPane.showMessageDialog(null, "Registros insertado correctamente en: " + table_name);

                } else {
                    first = true;
                }
            }
            JOptionPane.showMessageDialog(null, "Registro insertado correctamente en: " + table_name);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
