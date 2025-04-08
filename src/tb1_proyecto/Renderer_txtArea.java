/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tb1_proyecto;

import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author hriverav
 */
public class Renderer_txtArea extends DefaultTableCellRenderer {
        private JTextArea textArea = new JTextArea();

        public Renderer_txtArea() {
            super();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            textArea.setText(value != null ? value.toString() : "");
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            return new JScrollPane(textArea);
        }

    }
