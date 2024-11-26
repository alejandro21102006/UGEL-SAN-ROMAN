import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FormularioINSTITUCIONES extends JFrame{
    private JTable table1;
    private JButton LISTARButton;
    private JButton MODIFICARButton;
    private JButton ELIMINARButton;
    private JPanel panel;
    private JButton NUEVOButton;

    String url="jdbc:mysql://localhost:3306/ugel_san_roman";
    String usuario_bd="root";
    String password_bd="";
    public FormularioINSTITUCIONES(){
        setContentPane(panel); // Agregarmos el panel principal
        setTitle("Formulario de Libros");//titulo del formulario
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//accion para cerrar la ventana
        setSize(900, 800);//centrado del del forrmilario
        setLocationRelativeTo(null);//centrado del formulario en pantalla
        setResizable(false);//para que el usuario no puede modificar
        setVisible(true);


        LISTARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection PruebaConexion = null;
                PruebaConexion = conexion();
                if(PruebaConexion!=null){
                    System.out.println("Conexion establecida");

                    Statement statement = null;//sirve para instrucciones
                    ResultSet resultado = null;//es para el conjumto de registros

                    String sql = "select * from institucion";//seleciona rodos los registro de la tabla producto

                    try {
                        statement = PruebaConexion.createStatement();
                        resultado = statement.executeQuery(sql);


                        //creamos un modelos en jttablet
                        DefaultTableModel modelo = new DefaultTableModel();
                        modelo.setColumnIdentifiers(new Object[]{"Id","CODIO", "NOMBRE", "DIRECCIONs","DISTRITO","NIVEL","GESTION"});
                        modelo.setRowCount(0);

                        while (resultado.next()) {
                            Object[] fila = new Object[7];
                            fila[0] = resultado.getInt("id");
                            fila[1] = resultado.getString("codigo");
                            fila[2] = resultado.getString("nombres");
                            fila[3] = resultado.getString("direccion");
                            fila[4] = resultado.getString("distrito");
                            fila[5] = resultado.getString("nivel");
                            fila[6] = resultado.getString("gestion");
                            modelo.addRow(fila);
                        }
                        table1.setModel(modelo);

                    }catch (SQLException ex){

                        System.out.println(ex.getErrorCode());
                        System.out.println("Error al acceder a la tabla de institucion ");
                    }


                }else {
                    System.out.println("Conexion no establecida");
                    JOptionPane.showMessageDialog(null, "Conexion no establecida");
                }
            }
        });



        MODIFICARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int FilaSeleccionado = table1.getSelectedRow();
                System.out.println("Seleccionado en la tabla de institucion "+FilaSeleccionado);
                if(FilaSeleccionado >= 0){
                    JOptionPane.showMessageDialog(null, "Seleccione un institucion");
                }
                int id = Integer.parseInt(table1.getValueAt(FilaSeleccionado,0).toString());
                String codigo = table1.getValueAt(FilaSeleccionado,1).toString();
                String nombres = table1.getValueAt(FilaSeleccionado,2).toString();
                String direccion = table1.getValueAt(FilaSeleccionado,3).toString();
                String distrito = table1.getValueAt(FilaSeleccionado,4).toString();
                String nivel = table1.getValueAt(FilaSeleccionado,5).toString();
                String gestion = table1.getValueAt(FilaSeleccionado,6).toString();

                System.out.println(id+" | "+codigo+" | "+nombres+" | "+direccion+" | "+distrito+" | "+nivel+" | "+gestion);

                //debemos crear una neva vetana

                Connection Conexion_para_modificar = null;
                Conexion_para_modificar = conexion();

                Modifiacr Ventanita =new Modifiacr(id,codigo,nombres,direccion,distrito,nivel,gestion,Conexion_para_modificar);
                Ventanita.setVisible(true);
            }
        });




        ELIMINARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection Conexion = null;
                Conexion = conexion();
                if (Conexion != null) {

                    //Mostramos un mensaje para iliminar
                    int confirmacion = JOptionPane.showOptionDialog(null,
                            "Estas seguro de querer eliminar el institucion?",
                            "Cuidado!!",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new Object[]{"Si, Eliminar", "NO"},
                            "no");
                    if (confirmacion != JOptionPane.YES_OPTION) {
                        return;
                    }


                    int FilaSeleccionado = table1.getSelectedRow();
                    System.out.println("Seleccionado en la tabla de institucion " + FilaSeleccionado);
                    int idinstitucion = Integer.parseInt(table1.getValueAt(FilaSeleccionado, 0).toString());
                    try {
                        String sql = "DELETE FROM institucion WHERE id = ?";
                        PreparedStatement statement = Conexion.prepareStatement(sql);
                        statement.setInt(1, idinstitucion);
                        statement.execute();
                        JOptionPane.showMessageDialog(null, "Libro eliminado correctamente");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        NUEVOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //debemos crear una neva vetana
                Connection Conexion_para_nuevo = null;
                Conexion_para_nuevo = conexion();
                NUEVO Ventanita =new NUEVO(Conexion_para_nuevo);
                Ventanita.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {//Inicio del main
        new FormularioINSTITUCIONES();
    }//fin de main

    public Connection conexion(){
        Connection conex = null;

        try{
            conex= DriverManager.getConnection(url,usuario_bd,password_bd);
        }catch (SQLException e){
            System.out.println("Error al conectar con la base de datos");
        }
        return conex;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
