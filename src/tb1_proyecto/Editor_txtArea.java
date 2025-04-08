/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tb1_proyecto;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author hriverav
 */
public class Editor_txtArea extends DefaultCellEditor {

        private JTextArea textArea = new JTextArea();

        public Editor_txtArea(final JTextField textField) {
            super(textField);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            textArea = new JTextArea(value != null ? value.toString() : "");
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            return new JScrollPane(textArea);
        }

        @Override
        public Object getCellEditorValue() {
            return textArea != null ? textArea.getText() : "";
        }

    }
