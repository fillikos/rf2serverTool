package de.fillikos.rf2.server.tool.view.swing.table.model;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableModelLiveView extends DefaultListModel implements TableModel {

    public String[] columnNames;

    private TableModel tableModel = new AbstractTableModel() {

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getRowCount() {
            fireTableDataChanged();
            return size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String[] rowData = (String [])elementAt(rowIndex);
            return rowData[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    };

    public TableModelLiveView(String[] columnNames) {
        super();
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return tableModel.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return tableModel.getColumnCount();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return tableModel.getColumnName(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return tableModel.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return tableModel.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableModel.getValueAt(rowIndex, columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        tableModel.setValueAt(aValue,rowIndex,columnIndex);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        tableModel.addTableModelListener(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        tableModel.removeTableModelListener(l);
    }
}
