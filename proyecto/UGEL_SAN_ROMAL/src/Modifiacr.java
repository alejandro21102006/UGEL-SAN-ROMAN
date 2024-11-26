import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Modifiacr extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField text_C;
    private JTextField text_n;
    private JTextField text_d;
    private JTextField text_di;
    private JTextField text_ni;
    private JTextField text_g;
    private JTextField text_I;

    public Modifiacr(int id, String codigo,String nombres,String direccion,String distrito, String nivel,String gestion, Connection conex) {
        setTitle("MODIFICAR");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400,600);
        setLocationRelativeTo(null);

        text_C.setText(codigo);
        text_n.setText(nombres);
        text_d.setText(direccion);
        text_di.setText(distrito);
        text_ni.setText(nivel);
        text_g.setText(gestion);
        text_I.setText(String.valueOf(id));


        System.out.println("id de producto recibido : "+id);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String sql="UPDATE institucion SET codigo=?,nombres=?,direccion=?, distrito=?,nivel=?,gestion=? WHERE id=?";
                    PreparedStatement pst=conex.prepareStatement(sql);
                    pst.setString(1,text_C.getText());
                    pst.setString(2,text_n.getText());
                    pst.setString(3,text_d.getText());
                    pst.setString(4,text_di.getText());
                    pst.setString(5,text_ni.getText());
                    pst.setString(6,text_g.getText());
                    pst.setInt(7,Integer.parseInt(text_I.getText()));
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "MODIFICADO");
                    dispose();

                }catch (SQLException ex) {
                    System.out.println("Error: "+ex);
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

    /*private void onOK() {
        // add your code here
        dispose();
    }*/

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
