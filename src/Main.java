import Clases.Vendedores;
import VentanaLogin.VentanaLogin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main {

    public static Vendedores[] vendedoresLogin = new Vendedores[400];

    public static void main(String[] args) {
        try{
            File archivoVendedores = new File("vendedores.bin");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        cargarDatosVendedores();
        VentanaLogin login = new VentanaLogin(vendedoresLogin);
        login.setVisible(true);
    }

    public static Object leerDatosVendedores(){
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
    public static void cargarDatosVendedores(){
        if(!(leerDatosVendedores() == null)){
            vendedoresLogin = ((Vendedores[]) leerDatosVendedores());
        }
    }
}
