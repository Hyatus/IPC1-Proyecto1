package VentanaLogin;

import Clases.Vendedores;
import VentanaAdmin.VentanaAdmin;
import VentanaVendedor.VentanaVendedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class VentanaLogin extends JFrame implements ActionListener {

    private JPanel panelLogin;
    private JTextField cajaUsuario;
    private JPasswordField cajaContrasenia;
    private JButton botonIniciarSesion;
    private String passwordAdmin = "admin";
    private String usuarioAdmin = "admin";
    private Vendedores[] vendedoresLogin;

    public VentanaLogin(Vendedores[] vendedores){
        try{
            File archivoVendedores = new File("vendedores.bin");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.setSize(424,333);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Iniciar Sesión");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        iniciarComponentes();
        this.vendedoresLogin = vendedores;
    }

    private void iniciarComponentes() {
        cargarDatosVendedores();
        panelLogin = new JPanel();
        panelLogin.setLayout(null);
        this.getContentPane().add(panelLogin);

        JLabel Epos  = new JLabel("POS");
        Epos.setOpaque(true);
        Epos.setBounds(177,40,80,47);
        Epos.setFont(new Font("Dialog",Font.BOLD,36));
        panelLogin.add(Epos);

        JLabel eCodigo  = new JLabel("Código");
        eCodigo.setOpaque(true);
        eCodigo.setBounds(50,99,60,22);
        eCodigo.setFont(new Font("Dialog",Font.BOLD,14));
        panelLogin.add(eCodigo);

        cajaUsuario = new JTextField();
        cajaUsuario.setBounds(142,99,175,24);
        cajaUsuario.setFont(new Font("Dialog",Font.BOLD,14));
        panelLogin.add(cajaUsuario);

        JLabel eContrasenia  = new JLabel("Contraseña");
        eContrasenia.setOpaque(true);
        eContrasenia.setBounds(50,169,90,22);
        eContrasenia.setFont(new Font("Dialog",Font.BOLD,14));
        panelLogin.add(eContrasenia);

        cajaContrasenia = new JPasswordField();
        cajaContrasenia.setBounds(140,181,175,24);
        cajaContrasenia.setFont(new Font("Dialog",Font.BOLD,14));
        panelLogin.add(cajaContrasenia);

        botonIniciarSesion = new JButton();
        botonIniciarSesion.setBounds(140,231,172,27);
        botonIniciarSesion.setFont(new Font("Dialog",Font.BOLD,14));
        botonIniciarSesion.setText("Iniciar Sesión");
        botonIniciarSesion.addActionListener(this);
        panelLogin.add(botonIniciarSesion);
    }

    public Object leerDatosVendedores(){
        try{
            FileInputStream lectura = new FileInputStream("vendedores.bin");
            ObjectInputStream leerdatos = new ObjectInputStream(lectura);
            Object data  = leerdatos.readObject();
            leerdatos.close();
            return data;
        } catch (IOException e) {
            System.out.println("error " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("Error " + e);
        }
        return null;
    }
    private void cargarDatosVendedores(){
        if(!(leerDatosVendedores() == null)){
            vendedoresLogin = ((Vendedores[]) leerDatosVendedores());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String contraseña;
        if(e.getSource() == botonIniciarSesion){
            if(cajaUsuario.getText().equals(usuarioAdmin)){
                contraseña = retornarPasswordUsuario();
                if(passwordAdmin.equals(contraseña)){
                    VentanaAdmin ventanaadmin = new VentanaAdmin();
                    ventanaadmin.setVisible(true);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"Contraseña incorrecta", "Error!",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                verificarUsuario();
            }
        }
    }

    public int busquedaCodigoLogin(Vendedores[] vendedores){
            for (int i = 0; i < vendedores.length; i++) {
                if(vendedores[i] != null){
                    if(vendedores[i].getCodigoVendedor() == Long.parseLong(cajaUsuario.getText().trim())){
                        return i;
                    }
                }
            }
        return -1;
    }

    public String retornarPasswordUsuario(){
        String contraseña = "";
        for (int i = 0; i < cajaContrasenia.getPassword().length; i++) {
            contraseña += cajaContrasenia.getPassword()[i];
        }
        return contraseña;
    }

    public void verificarUsuario(){
        long codigoVendedor;
        String passwordVendedor;
        int indiceUsuario = busquedaCodigoLogin(vendedoresLogin);
        if(indiceUsuario != -1){
            codigoVendedor = vendedoresLogin[indiceUsuario].getCodigoVendedor();
            passwordVendedor = retornarPasswordUsuario();
            if(passwordVendedor.equals(vendedoresLogin[indiceUsuario].getPasswordVendedor())){
                System.out.println("Bienvenido " + vendedoresLogin[indiceUsuario].getNombreVendedor());
                VentanaVendedor ventanavendedor = new VentanaVendedor(indiceUsuario);
                ventanavendedor.setVisible(true);
                dispose();
            }else{
                JOptionPane.showMessageDialog(null,"Contraseña Incorrecta", "Error!",JOptionPane.ERROR_MESSAGE);
                System.out.println(cajaContrasenia.getPassword());
                System.out.println(cajaUsuario.getText());
            }
        }else{
            JOptionPane.showMessageDialog(null,"Usuario incorrecto", "Error!",JOptionPane.ERROR_MESSAGE);
            System.out.println(cajaContrasenia.getPassword());
            System.out.println(cajaUsuario.getText());
        }
    }

}
