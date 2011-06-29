/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package deploy.JND;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Will
 */
public class JNDExperimentConfFrameTableModel extends DefaultTableModel implements TableModel{

    protected String CONF_DELIMITER = " = ";

    public void setConfDelimiter(String delimiter){
        CONF_DELIMITER = delimiter;
    }

    public void clear(){
        dataVector.clear();
        this.fireTableDataChanged();
    }

    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0:
                return "Description";
            case 1:
                return "Value";
            case 2:
                return "Type";
            case 3:
                return "Conf. Name";
            case 4:
                return "Min";
            case 5:
                return "Max";
            default:
                return "Unsupported";
        }
    }

    public int getColumnCount(){
        return 6;
    }

    public String getProposedConf(){
        String temp = "";
        for(int i=0; i<this.getRowCount(); i++){
            temp = temp + this.getValueAt(i, 3) + CONF_DELIMITER + this.getValueAt(i, 1).toString() + "\n";
        }
        return temp;
    }

    public Object getValueAt(int row, int column) {
        Vector rowVector = (Vector)dataVector.elementAt(row);
        if(column == 2){
            String className = ((Class)rowVector.elementAt(column)).toString();
            Pattern p = Pattern.compile("\\.\\w*\\z");
            Matcher m = p.matcher(className);
            m.find();
            return className.substring(m.start()+1);
        }
        return rowVector.elementAt(column);
    }

    public boolean isSetValueValid(Object aValue, int row){
        Vector rowVector = (Vector)dataVector.elementAt(row);
        String myClass = rowVector.elementAt(2).toString();

        if(myClass.equals(Double.class.toString())){
            try{
                double dVal = Double.parseDouble(aValue.toString());
                if(dVal < ((Double)getValueAt(row, 4)).doubleValue()) return false;
                if(dVal > ((Double)getValueAt(row, 5)).doubleValue()) return false;
                return true;
            }catch(Exception e){
                return false;
            }
        }else if(myClass.equals(Integer.class.toString())){
            try{
                int iVal = Integer.parseInt(aValue.toString());
                if(iVal < ((Integer)getValueAt(row, 4)).intValue()) return false;
                if(iVal > ((Integer)getValueAt(row, 5)).intValue()) return false;
                return true;
            }catch(Exception e){
                return false;
            }
        } else if(myClass.equals(Boolean.class.toString())){
            if(aValue.toString().toLowerCase().equals("true") ||
               aValue.toString().toLowerCase().equals("false") ||
               aValue.toString().toLowerCase().equals("0") ||
               aValue.toString().toLowerCase().equals("1")) return true;
            else return false;
        }

        return false;
    }

    public void setValueAt(Object aValue, int row, int column) {
        if(isSetValueValid(aValue, row)){
            Vector rowVector = (Vector)dataVector.elementAt(row);
            rowVector.setElementAt(aValue, column);
            fireTableCellUpdated(row, column);
        }
    }

    
    public Class getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 1) return true;
        else return false;
    }

    public void addConfRow(String title, Object value, Class type, String hiddenConfTitle, Object hiddenMin, Object hiddenMax){
        this.addRow(new Object[]{title, value, type, hiddenConfTitle, hiddenMin, hiddenMax});
    }
}
