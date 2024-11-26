import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NUEVO extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField text_C;
    private JTextField text_n;
    private JTextField text_d;
    private JTextField text_di;
    private JTextField text_ni;
    private JTextField text_g;

    String url="jdbc:mysql://localhost:3306/ugel_san_roman";
    String usuario_bd="root";
    String password_bd="";

    public NUEVO( Connection conex) {
        setTitle("NUEVO");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400,600);
        setLocationRelativeTo(null);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //primero nos conectamos a bd
                Connection Conexion = null;
                Conexion = conexion();
                if (Conexion != null) {
                    System.out.println("Correcto, la BD ha respondido");

                    String codigo = text_C.getText();
                    String nombre = text_n.getText();
                    String direccion = text_d.getText();
                    String distrito = text_di.getText();
                    String nivel = text_ni.getText();
                    String gestion = text_g.getText();


                    try {
                        String sql ="INSERT INTO institucion(codigo,nombres,direccion,distrito,nivel,gestion) VALUES (?,?,?,?,?,?)";
                        PreparedStatement statement = Conexion.prepareStatement(sql);
                        statement.setString(1, codigo);
                        statement.setString(2, nombre);
                        statement.setString(3, direccion);
                        statement.setString(4, distrito);
                        statement.setString(5, nivel);
                        statement.setString(6, gestion);
                        statement.execute();
                        JOptionPane.showMessageDialog(null, "institucion guardada correctamente");
                        dispose();


                    }catch (SQLException ex){
                        System.out.println(ex.getMessage());
                        System.out.println("Error al guardar el institucion");
                    }


                }else {
                    System.out.println("La BD No ha respondido");

                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /*/private void onOK() {
        // add your code here
        dispose();
    }*/

    public Connection conexion(){
        Connection conex = null;

        try{
            conex= DriverManager.getConnection(url,usuario_bd,password_bd);
        }catch (SQLException e){
            System.out.println("Error al conectar con la base de datos");
        }
        return conex;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
