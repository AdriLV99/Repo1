/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba1;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javax.swing.JOptionPane;

/**
 *
 * @author Tabatha Parajon
 */
public class FXMLDocumentController implements Initializable {
    Connection conn=null;
private Connection conexion;
    
    String driver = "org.postgresql.Driver";
    String ruta = "jdbc:postgresql://192.168.0.2:5432/Paleteria";
    String user = "postgres";
    String password = "12345678";
    
    @FXML
    private ObservableList<ObservableList> data;
    @FXML
    private Label label;
    @FXML
    private TextField nombre;
    @FXML
    private Button button;
    @FXML
    private TableView Tabla1;
    @FXML
    private TextField edad;
    @FXML
    private TextField color;
    @FXML
    private TextField deporte;
    @FXML
    private TextField but1;
    @FXML
    private TextField edad1;
    @FXML
    private TextField color1;
    @FXML
    private TextField deporte1;
    @FXML
    private TextField but11;
    @FXML
    
    
    
     public void handleButtonActionn(ActionEvent event) throws ClassNotFoundException, SQLException {
        
        Statement st;
        
        Class.forName(driver);
        
        st=conn.createStatement();
            //SQL FOR SELECTING ALL OF CUSTOMER
        String SQL = "INSERT INTO usuarios(Nombre, Edad, Color, Deporte) VALUES ('"
                                           +nombre.getText()+"', '"
                                           +edad.getText()+"', '"
                                           +color.getText()+"', '"
                                           +deporte.getText()+"');";
        st.execute(SQL);
        conn.close();
        JOptionPane.showMessageDialog(null,"Registro Super");
    }
    
    @FXML
    public void handleButtonActionM(ActionEvent event) throws ClassNotFoundException, SQLException {
        
        Class.forName(driver);
        Connection conne=(Connection) DriverManager.getConnection(ruta,user,password);
        Statement consulta=(Statement) conne.createStatement();
        consulta.executeUpdate("update usuarios set Edad= "+edad1.getText()+",Color= '"+color1.getText()+"',Deporte='"+deporte1.getText()+"' where Nombre='"+but1.getText()+"'");   
        JOptionPane.showMessageDialog(null,"Registro Modificado");
     
    }
    
    public void handleButtonActionE(ActionEvent event) throws ClassNotFoundException, SQLException {
        
        Class.forName(driver);
        Connection conne=(Connection) DriverManager.getConnection(ruta,user,password);
        Statement consulta=(Statement) conne.createStatement();
        consulta.executeUpdate("delete from usuarios where Nombre='"+but11.getText()+"'");
        JOptionPane.showMessageDialog(null,"Registro Eliminado");
     
    }
    
    public void handleButtonActionC(ActionEvent event) throws ClassNotFoundException, SQLException {
        Connection c ;
        Tabla1.getColumns().clear();
          data = FXCollections.observableArrayList();
          try{
            Class.forName(driver);
            Connection conne=(Connection) DriverManager.getConnection(ruta,user,password);
            
            String SQL = "SELECT * from usuarios";
            
            ResultSet rs = conne.createStatement().executeQuery(SQL);
     
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
               
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
                Tabla1.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
            }

            while(rs.next()){
                
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);
            }
            
            Tabla1.setItems(data);
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
          }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
    }
}