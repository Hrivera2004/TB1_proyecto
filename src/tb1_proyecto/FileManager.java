/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tb1_proyecto;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void ReadFile() {
        try (CSVReader reader = new CSVReader(new FileReader(csv))) {
            List<String[]> rows = reader.readAll();
            String table_name = (csv.getName()).substring(csv.getName().indexOf("-") + 1, csv.getName().indexOf("."));
            System.out.println(table_name);
            for (String[] row : rows) {
                Object[] list = row;
                for (String string : row) {
                    System.out.print(string + ", ");
                }
                System.out.println("");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CsvException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
