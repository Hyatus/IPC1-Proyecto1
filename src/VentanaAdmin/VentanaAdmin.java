package VentanaAdmin;

import Clases.Clientes;
import Clases.Productos;
import Clases.Sucursales;
import Clases.Vendedores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class VentanaAdmin extends JFrame implements ActionListener {

    public Sucursales[] sucursales = new Sucursales[50];
    public Productos[] productos = new Productos[200];
    public Clientes[] clientes = new Clientes[100];
    public Vendedores[] vendedores = new Vendedores[400];
    public int contadorActualSucursal = 0, contadorActualproductos = 0, ContadorActualClientes = 0,
            ContadorActualVendedores = 0;
        
    //COMPONENTES VENTANA ADMIN
    JButton botonCerrarSesión;
    JTabbedPane grupoPaneles;
    PanelSucursales panelsucursal = new PanelSucursales();
    PanelProductos panelProductos = new PanelProductos();
    PanelClientes panelClientes = new PanelClientes();
    PanelVendedores panelVendedores = new PanelVendedores();

    //COMPONENTES JDIALOG CREAR NUEVA SUCURSAL
    JDialog crearNuevaSucursal;
    JLabel encabezadoD,codigoD,NombreD,direccionD,correoD,telefonoD;
    JTextField cajaCodigoD,cajaNombreD,cajaDireccionD, cajaCorreoD, cajaTelefonoD;
    JButton botonAgregarS, botonActualizarS;


    public VentanaAdmin(){
        try{
            File archivo = new File("sucursales.bin");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.setSize(790,724);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        agregarListeners();
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        botonCerrarSesión = new JButton();
        botonCerrarSesión.setBounds(630,10,136,40);
        botonCerrarSesión.setText("Cerrar Sesión");
        botonCerrarSesión.setFont(new Font("Dialog",Font.BOLD,14));
        this.add(botonCerrarSesión);
        botonCerrarSesión.addActionListener(this);
        cargarDatosSucursal();
        System.out.println(contadorActualSucursal);
        colocarTabbedPanel();
    }


    private  Object leerDatosSucursal(){
        try{
            FileInputStream lectura = new FileInputStream("sucursales.bin");
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

    private void cargarDatosSucursal(){
        if(!(leerDatosSucursal() == null)){
            sucursales = ((Sucursales[]) leerDatosSucursal());
            for(int i = 0; i < sucursales.length ; i++){
                if(sucursales[i] != null){
                    String[] fila = {String.valueOf(sucursales[i].getCodigoSucursal()),
                            sucursales[i].getNombreSucursal(),sucursales[i].getDireccionSucursal(),sucursales[i].getCorreoSucursal(),
                            String.valueOf(sucursales[i].getTelefonoSucursal())};
                    panelsucursal.modeloTabla.addRow(fila);
                    contadorActualSucursal++;
                }
            }
        }
    }

    private void colocarTabbedPanel() {
        grupoPaneles = new JTabbedPane();
        grupoPaneles.setBounds(15,77,745,587);
        grupoPaneles.add("Sucursales",panelsucursal);
        grupoPaneles.add("Productos",panelProductos);
        grupoPaneles.add("Clientes",panelClientes);
        grupoPaneles.add("Vendedores",panelVendedores);
        this.add(grupoPaneles);
    }

    private void agregarListeners(){
        panelsucursal.crearSurcursal.addActionListener(this);
        panelsucursal.actualizarSucursal.addActionListener(this);
        panelsucursal.eliminarSucursal.addActionListener(this);
        panelProductos.crearProducto.addActionListener(this);
        panelProductos.actualizarProducto.addActionListener(this);
        panelClientes.crearCliente.addActionListener(this);
        panelClientes.actualizarCliente.addActionListener(this);
        panelVendedores.crearVendedor.addActionListener(this);
        panelVendedores.actualizarVendedor.addActionListener(this);
    }

    private void escribirDatos(Object object) {
        if(object.equals(sucursales)){
            serializarSucursal(sucursales);
        }
    }

    public void serializarSucursal(Object object){
        try{
            FileOutputStream archivo = new FileOutputStream("sucursales.bin");
            ObjectOutputStream serializar = new ObjectOutputStream(archivo);
            serializar.writeObject(object);
            serializar.close();
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado "  + e);
        }catch (IOException e){
            System.out.println("Error " + e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == panelsucursal.crearSurcursal){
            ventanaCrearNuevaSucursal();
        }else if(e.getSource() == botonAgregarS){
            funcionbotonAgregarSucursal();
        }else if(e.getSource() == panelsucursal.actualizarSucursal){
            cargarDatosVentanaActualizar();
        }else if(e.getSource() == botonActualizarS){
            funcionbotonActualizarSucursal();
        }else if(e.getSource() == panelsucursal.eliminarSucursal){
            eliminarSucursal();
        }
        else if(e.getSource() == panelProductos.crearProducto){
            panelProductos.ventanaCrearNuevoProducto();
            System.out.println("Crear nuevo producto");
        }else if(e.getSource() == panelProductos.actualizarProducto){
            panelProductos.ventanaActualizarProducto();
            System.out.println("Actualizar un producto");
        }else if(e.getSource() == panelClientes.crearCliente){
            panelClientes.ventanaCrearNuevoCliente();
            System.out.println("Crear Nuevo Cliente");
        }else if(e.getSource() == panelClientes.actualizarCliente){
            panelClientes.ventanaActualizarCliente();
            System.out.println("Actualizar Cliente");
        }else if(e.getSource() == panelVendedores.crearVendedor){
            panelVendedores.ventanaCrearNuevoVendedor();
            System.out.println("Crear Vendedor");
        }else if(e.getSource() == panelVendedores.actualizarVendedor){
            panelVendedores.ventanaActualizarVendedor();
            System.out.println("Actualizar un Vendedor");
        }else if(e.getSource() == botonCerrarSesión){
            escribirDatos(sucursales);
            JOptionPane.showMessageDialog(null,"Sesión Terminada", "Cerrar Sesión",JOptionPane.INFORMATION_MESSAGE);
            dispose();
            //VentanaLogin ventanaLogin = new VentanaLogin(vendedores);
            //ventanaLogin.setVisible(true);
        }

    }

    //JDIALOG PARA CREAR NUEVA SUCURSAL, ACTUALIZAR, ELIMINAR
    public void ventanaCrearNuevaSucursal(){
        crearNuevaSucursal = new JDialog();
        crearNuevaSucursal.setLayout(null);
        crearNuevaSucursal.setResizable(false);
        encabezadoD = new JLabel("Crear Nueva Sucursal");
        encabezadoD.setBounds(174,47,185,30);
        encabezadoD.setFont(new Font("Dialog",Font.PLAIN,18));

        codigoD = new JLabel("Código");
        codigoD.setBounds(92,104,66,26);
        codigoD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCodigoD = new JTextField();
        cajaCodigoD.setBounds(197,104,218,26);
        cajaCodigoD.setFont(new Font("Dialog",Font.PLAIN,18));

        NombreD = new JLabel("Nombre");
        NombreD.setBounds(92,156,75,26);
        NombreD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNombreD = new JTextField();
        cajaNombreD.setBounds(197,156,218,26);
        cajaNombreD.setFont(new Font("Dialog",Font.PLAIN,18));

        direccionD = new JLabel("Dirección");
        direccionD.setBounds(92,209,75,26);
        direccionD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaDireccionD = new JTextField();
        cajaDireccionD.setBounds(197,209,218,26);
        cajaDireccionD.setFont(new Font("Dialog",Font.PLAIN,18));

        correoD = new JLabel("Correo");
        correoD.setBounds(92,262,75,26);
        correoD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCorreoD = new JTextField();
        cajaCorreoD.setBounds(197,262,218,26);
        cajaCorreoD.setFont(new Font("Dialog",Font.PLAIN,18));

        telefonoD = new JLabel("Teléfono");
        telefonoD.setBounds(92,315,75,26);
        telefonoD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaTelefonoD = new JTextField();
        cajaTelefonoD.setBounds(197,315,218,26);
        cajaTelefonoD.setFont(new Font("Dialog",Font.PLAIN,18));

        botonAgregarS = new JButton("Agregar");
        botonAgregarS.setBounds(185,361,154,32);
        botonAgregarS.setFont(new Font("Dialog",Font.PLAIN,18));
        botonAgregarS.addActionListener(this);

        crearNuevaSucursal.add(encabezadoD);
        crearNuevaSucursal.add(codigoD);
        crearNuevaSucursal.add(cajaCodigoD);
        crearNuevaSucursal.add(NombreD);
        crearNuevaSucursal.add(cajaNombreD);
        crearNuevaSucursal.add(direccionD);
        crearNuevaSucursal.add(cajaDireccionD);
        crearNuevaSucursal.add(correoD);
        crearNuevaSucursal.add(cajaCorreoD);
        crearNuevaSucursal.add(telefonoD);
        crearNuevaSucursal.add(cajaTelefonoD);
        crearNuevaSucursal.add(botonAgregarS);

        crearNuevaSucursal.setSize(510,479);
        crearNuevaSucursal.setLocationRelativeTo(null);
        crearNuevaSucursal.setVisible(true);
    }
    public void funcionbotonAgregarSucursal(){
        long codigoSucursal = Long.parseLong(cajaCodigoD.getText().trim());
        long telefonoSucursal = Long.parseLong(cajaTelefonoD.getText().trim());
        String nombreSucursal = cajaNombreD.getText();
        String direccionSucursal = cajaDireccionD.getText();
        String correoSucursal = cajaCorreoD.getText();
        if(contadorActualSucursal < 50){
            String[] fila = {cajaCodigoD.getText(),cajaNombreD.getText(),cajaDireccionD.getText(),
                    cajaCorreoD.getText(),cajaTelefonoD.getText()};
            panelsucursal.modeloTabla.addRow(fila);
            sucursales[contadorActualSucursal] = new Sucursales(codigoSucursal,nombreSucursal,direccionSucursal,
                    correoSucursal,telefonoSucursal);
            contadorActualSucursal++;
            cajaTelefonoD.setText("");
            cajaCodigoD.setText("");
            cajaDireccionD.setText("");
            cajaNombreD.setText("");
            cajaCorreoD.setText("");
        }else{
            System.out.println("Ya no se pueden agregar más sucursales");
        }
    }
    public void ventanaActualizarSucursal(){
        crearNuevaSucursal = new JDialog();
        crearNuevaSucursal.setLayout(null);
        crearNuevaSucursal.setResizable(false);
        encabezadoD = new JLabel("Actualizar Datos Sucursales");
        encabezadoD.setBounds(160,47,225,30);
        encabezadoD.setFont(new Font("Dialog",Font.PLAIN,18));

        codigoD = new JLabel("Código");
        codigoD.setBounds(92,104,66,26);
        codigoD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCodigoD = new JTextField();
        cajaCodigoD.setBounds(197,104,218,26);
        cajaCodigoD.setFont(new Font("Dialog",Font.PLAIN,18));

        NombreD = new JLabel("Nombre");
        NombreD.setBounds(92,156,75,26);
        NombreD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNombreD = new JTextField();
        cajaNombreD.setBounds(197,156,218,26);
        cajaNombreD.setFont(new Font("Dialog",Font.PLAIN,18));

        direccionD = new JLabel("Dirección");
        direccionD.setBounds(92,209,75,26);
        direccionD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaDireccionD = new JTextField();
        cajaDireccionD.setBounds(197,209,218,26);
        cajaDireccionD.setFont(new Font("Dialog",Font.PLAIN,18));

        correoD = new JLabel("Correo");
        correoD.setBounds(92,262,75,26);
        correoD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCorreoD = new JTextField();
        cajaCorreoD.setBounds(197,262,218,26);
        cajaCorreoD.setFont(new Font("Dialog",Font.PLAIN,18));

        telefonoD = new JLabel("Teléfono");
        telefonoD.setBounds(92,315,75,26);
        telefonoD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaTelefonoD = new JTextField();
        cajaTelefonoD.setBounds(197,315,218,26);
        cajaTelefonoD.setFont(new Font("Dialog",Font.PLAIN,18));

        botonActualizarS = new JButton("Actualizar");
        botonActualizarS.setBounds(185,361,154,32);
        botonActualizarS.setFont(new Font("Dialog",Font.PLAIN,18));
        botonActualizarS.addActionListener(this);

        crearNuevaSucursal.add(encabezadoD);
        crearNuevaSucursal.add(codigoD);
        crearNuevaSucursal.add(cajaCodigoD);
        crearNuevaSucursal.add(NombreD);
        crearNuevaSucursal.add(cajaNombreD);
        crearNuevaSucursal.add(direccionD);
        crearNuevaSucursal.add(cajaDireccionD);
        crearNuevaSucursal.add(correoD);
        crearNuevaSucursal.add(cajaCorreoD);
        crearNuevaSucursal.add(telefonoD);
        crearNuevaSucursal.add(cajaTelefonoD);
        crearNuevaSucursal.add(botonActualizarS);

        crearNuevaSucursal.setSize(510,479);
        crearNuevaSucursal.setLocationRelativeTo(null);
        crearNuevaSucursal.setVisible(true);
    }
    public void cargarDatosVentanaActualizar(){
        if(!panelsucursal.tablaSucursal.getSelectionModel().isSelectionEmpty()){
            ventanaActualizarSucursal();
            int filaSeleccionada = panelsucursal.tablaSucursal.getSelectedRow();
            //guardamos el nombre de la fila seleccionada
            String codigo = (String)panelsucursal.modeloTabla.getValueAt(filaSeleccionada,0);
            String nombre = (String)panelsucursal.modeloTabla.getValueAt(filaSeleccionada,1);
            String direccion = (String)panelsucursal.modeloTabla.getValueAt(filaSeleccionada,2);
            String correo = (String)panelsucursal.modeloTabla.getValueAt(filaSeleccionada,3);
            String telefono = (String)panelsucursal.modeloTabla.getValueAt(filaSeleccionada,4);
            cajaCodigoD.setText(codigo);
            cajaNombreD.setText(nombre);
            cajaDireccionD.setText(direccion);
            cajaCorreoD.setText(correo);
            cajaTelefonoD.setText(telefono);
        }else{
            System.out.println("No hay nada seleccionado");
        }
    }
    public void eliminarSucursal(){
        if(!panelsucursal.tablaSucursal.getSelectionModel().isSelectionEmpty()){
            int filaSeleccionada = panelsucursal.tablaSucursal.getSelectedRow();
            long codigo = Long.parseLong(((String)panelsucursal.modeloTabla.getValueAt(filaSeleccionada,0)).trim());
            String nombre = ((String)panelsucursal.modeloTabla.getValueAt(filaSeleccionada,1)).trim();
            int indiceBusquedaSucursal = buscarEnArreglo(sucursales,codigo,nombre);
            if(indiceBusquedaSucursal!=-1) {
                sucursales[indiceBusquedaSucursal] = null;
                contadorActualSucursal--;
                panelsucursal.modeloTabla.removeRow(filaSeleccionada);
                System.out.println("Sucursal eliminada con éxito");
                correrValores(sucursales,indiceBusquedaSucursal);
                int indiceNulo = buscarElNulo(sucursales);
                ordenarSucursal(sucursales,indiceNulo);
            }else{
                panelsucursal.modeloTabla.removeRow(filaSeleccionada);
                contadorActualSucursal--;
                System.out.println("Sucursal eliminada con éxito");
            }
        }else{
            System.out.println("No hay nada seleccionado");
        }
    }

    public void funcionbotonActualizarSucursal(){
        int filaSeleccionada = panelsucursal.tablaSucursal.getSelectedRow();
        Long codigoBusqueda = Long.parseLong(((String)panelsucursal.modeloTabla.getValueAt(filaSeleccionada,0)).trim());
        String nombreBusqueda = ((String)panelsucursal.modeloTabla.getValueAt(filaSeleccionada,1)).trim();
        int indiceSucursal = buscarEnArreglo(sucursales,codigoBusqueda,nombreBusqueda);
        String codigo = cajaCodigoD.getText();
        String nombre = cajaNombreD.getText();
        String direccion = cajaDireccionD.getText();
        String correo = cajaCorreoD.getText();
        String telefono = cajaTelefonoD.getText();
        panelsucursal.modeloTabla.setValueAt(codigo,filaSeleccionada,0);
        panelsucursal.modeloTabla.setValueAt(nombre,filaSeleccionada,1);
        panelsucursal.modeloTabla.setValueAt(direccion,filaSeleccionada,2);
        panelsucursal.modeloTabla.setValueAt(correo,filaSeleccionada,3);
        panelsucursal.modeloTabla.setValueAt(telefono,filaSeleccionada,4);
        if(indiceSucursal != -1){
            sucursales[indiceSucursal].setCodigoSucursal(Long.parseLong(codigo.trim()));
            sucursales[indiceSucursal].setNombreSucursal(nombre);
            sucursales[indiceSucursal].setDireccionSucursal(direccion);
            sucursales[indiceSucursal].setCorreoSucursal(direccion);
            sucursales[indiceSucursal].setTelefonoSucursal(Long.parseLong(telefono.trim()));
        }
        crearNuevaSucursal.dispose();
    }

    public int buscarEnArreglo(Object object,long codigo, String nombre){
        int indice;
        if(object.equals(sucursales)){
            for(int i = 0; i < sucursales.length; i++){
                if(sucursales[i] != null){
                    if(sucursales[i].getCodigoSucursal() == codigo &&
                           sucursales[i].getNombreSucursal().equals(nombre)){
                        indice = i;
                        return indice;
                    }
                }
            }
        }
        return -1;
    }

    public void correrValores(Object[] object,int indiceInicio){
        for(int i = indiceInicio ; i < object.length; i++){
            int aux = i+1;
            if(aux < object.length){
                if(object[aux] != null){
                    object[i] = object[aux];
                }else{
                    object[i] = null;
                }
            }
            /*if(object[i] == null){
                int aux = i+1;
                if(aux < object.length){
                    object[i] = object[aux];
                }
            }*/
        }

        for(int i = 0; i < object.length; i++){
            if(object[i] != null){
                System.out.println(object[i].toString());
            }
        }

    }

    public void ordenarSucursal(Object[] object, int indiceNulo){
        if(object.equals(sucursales)){
            int cuentaintercambios = 0;
            for(boolean ordenado = false; !ordenado;){
                for(int i=0; i < indiceNulo-1; i++){
                        if(sucursales[i].getCodigoSucursal() > sucursales[i+1].getCodigoSucursal()){
                            Sucursales sucursalAux = sucursales[i];
                            sucursales[i] = sucursales[i+1];
                            sucursales[i+1]=sucursalAux;
                            cuentaintercambios++;
                        if(cuentaintercambios==0){
                            ordenado = true;
                        }
                        cuentaintercambios=0;
                    }

                }
            }
        }

        for(int i = 0; i < object.length; i++){
            if(object[i] != null){
                System.out.println(object[i].toString());
            }
        }
    }

    public int buscarElNulo(Object[] object){
        int indiceNulo;
      if(object.equals(sucursales)){
          for(int i = 0; i < sucursales.length; i++){
              if(sucursales[i] == null){
                  indiceNulo = i;
                  return indiceNulo;
              }
          }
      }

      indiceNulo = sucursales.length;
      return indiceNulo;
    }



}
