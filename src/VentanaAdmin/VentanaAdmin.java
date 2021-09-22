package VentanaAdmin;

import Clases.Clientes;
import Clases.Productos;
import Clases.Sucursales;
import Clases.Vendedores;
import PlantillaPDF.PlantillaClientes;
import PlantillaPDF.PlantillaProductos;
import PlantillaPDF.PlantillaSucursales;
import PlantillaPDF.PlantillaVendedores;
import VentanaLogin.VentanaLogin;
import com.google.gson.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Date;

public class VentanaAdmin extends JFrame implements ActionListener {

    public Sucursales[] sucursales = new Sucursales[50];
    public Productos[] productos = new Productos[200];
    public Clientes[] clientes = new Clientes[100];
    public Vendedores[] vendedores = new Vendedores[400];
    public int contadorActualSucursal = 0, contadorActualproductos = 0, contadorActualClientes = 0,
            contadorActualVendedores = 0;
    public ChartPanel frame, frameC, frameV;

    /******************************************************************************************************
     * COMPONENTES DE LA VENTANA ADMINISTRADOR                                                            *
     ******************************************************************************************************/
    JButton botonCerrarSesión;
    JTabbedPane grupoPaneles;
    PanelSucursales panelsucursal = new PanelSucursales();
    PanelProductos panelProductos = new PanelProductos();
    PanelClientes panelClientes = new PanelClientes();
    PanelVendedores panelVendedores = new PanelVendedores();

    /******************************************************************************************************
     * COMPONENTES DEL JDIALOG PARA CREAR NUEVA SUCURSAL Y ACTUALIZAR SUCURSAL                            *
     ******************************************************************************************************/
    JDialog crearNuevaSucursal;
    JLabel encabezadoD,codigoD,NombreD,direccionD,correoD,telefonoD;
    JTextField cajaCodigoD,cajaNombreD,cajaDireccionD, cajaCorreoD, cajaTelefonoD;
    JButton botonAgregarS, botonActualizarS;

    /******************************************************************************************************
     * COMPONENTES DEL JDIALOG PARA CREAR NUEVO PRODUCTO Y ACTUALIZAR                                     *
     ******************************************************************************************************/
    public JDialog crearNuevoProducto;
    public JLabel encabezadoProdD,codigoProdD,NombreProdD,DescripcionProd,CantidadProd,PrecioProd;
    public JTextField cajaCodigoProdD,cajaNombreProdD,cajaDescripcionD, cajaCantidadD, cajaPrecioD;
    public JButton botonAgregarP,botonActualizarP;

    /******************************************************************************************************
     * COMPONENTES DEL JDIALOG PARA CREAR NUEVO CLIENTE Y ACTUALIZAR                                      *
     ******************************************************************************************************/
    public JDialog crearNuevoCliente;
    public JLabel encabezadoClienteD,codigoClienteD,NombreClienteD,NitCliente,CorreoCliente,GeneroCliente;
    public JTextField cajaCodigoClienteD,cajaNombreClienteD,cajaNitClienteD, cajaCorreoClienteD, cajaGeneroClienteD;
    public JButton botonAgregarC,botonActualizarC;

    /******************************************************************************************************
     * COMPONENTES DEL JDIALOG PARA CREAR NUEVO VENDEDOR Y ACTUALIZAR                                      *
     ******************************************************************************************************/
    public JDialog crearNuevoVendedor;
    public JLabel encabezadoVendD,codigoVendD,NombreVendD,CajaD,VentasD,GeneroVendD,PasswordVendD;
    public JTextField cajaCodigoVendD,cajaNombreVendD,cajaCajaVendD, cajaVentasD, cajaGeneroVendD;
    public JPasswordField cajaPasswordVendD;
    public JButton botonAgregarV,botonActualizarV;


    public VentanaAdmin(){
        try{
            File archivoSucursales = new File("sucursales.bin");
            File archivoProductos = new File("productos.bin");
            File archivoClientes = new File("clientes.bin");
            File archivoVendedores = new File("vendedores.bin");
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
        cargarDatosProductos();
        cargarDatosClientes();
        cargarDatosVendedores();
        colocarTabbedPanel();
        agregarGraficaProductos(productos);
        agregarGraficaClientes(clientes);
        agregarGraficaVendedores(vendedores);
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
        panelsucursal.cargaMasivaSucursal.addActionListener(this);
        panelsucursal.exportarSucursal.addActionListener(this);

        panelProductos.crearProducto.addActionListener(this);
        panelProductos.actualizarProducto.addActionListener(this);
        panelProductos.eliminarProducto.addActionListener(this);
        panelProductos.cargaProducto.addActionListener(this);
        panelProductos.exportarProductos.addActionListener(this);

        panelClientes.crearCliente.addActionListener(this);
        panelClientes.actualizarCliente.addActionListener(this);
        panelClientes.eliminarCliente.addActionListener(this);
        panelClientes.cargaCliente.addActionListener(this);
        panelClientes.exportarCliente.addActionListener(this);

        panelVendedores.crearVendedor.addActionListener(this);
        panelVendedores.actualizarVendedor.addActionListener(this);
        panelVendedores.eliminarVendedor.addActionListener(this);
        panelVendedores.cargaVendedor.addActionListener(this);
        panelVendedores.exportarVendedor.addActionListener(this);
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
        }else if(e.getSource() == panelsucursal.cargaMasivaSucursal){
              cargaMasivaSucursal();
        }else if(e.getSource() == panelsucursal.exportarSucursal){
              crearReporteSucursales();
        }

        else if(e.getSource() == panelProductos.crearProducto){
              ventanaCrearNuevoProducto();
        }else if(e.getSource() == botonAgregarP){
              funcionbotonAgregarProducto();
        }else if(e.getSource() == panelProductos.actualizarProducto){
              cargarDatosVentanaActualizarProducto();
        }else if(e.getSource() == botonActualizarP){
              funcionbotonActualizarProducto();
        }else if(e.getSource() == panelProductos.eliminarProducto){
              eliminarProducto();
        }else if(e.getSource() == panelProductos.cargaProducto){
              cargaMasivaProductos();
        }else if(e.getSource() == panelProductos.exportarProductos){
            crearReporteProductos();
        }

        else if(e.getSource() == panelClientes.crearCliente){
               ventanaCrearNuevoCliente();
        }else if(e.getSource() == botonAgregarC){
               funcionbotonAgregarCliente();
        }else if(e.getSource() == panelClientes.actualizarCliente){
               cargarDatosVentanaActualizarCliente();
        }else if(e.getSource() == botonActualizarC){
              funcionbotonActualizarCliente();
        }else if(e.getSource() == panelClientes.eliminarCliente){
              eliminarCliente();
        }else if(e.getSource() == panelClientes.cargaCliente){
              cargaMasivaClientes();
        }else if(e.getSource() == panelClientes.exportarCliente){
              crearReporteClientes();
        }
        else if(e.getSource() == panelVendedores.crearVendedor){
                ventanaCrearNuevoVendedor();
        }else if(e.getSource() == botonAgregarV){
               funcionbotonAgregarVendedor();
        }else if(e.getSource() == panelVendedores.actualizarVendedor){
                cargarDatosVentanaActualizarVendedor();
        }else if(e.getSource() == botonActualizarV){
               funcionbotonActualizarVendedor();
        }else if(e.getSource() == panelVendedores.eliminarVendedor){
               eliminarVendedor();
        }else if(e.getSource() == panelVendedores.cargaVendedor){
              cargaMasivaVendedores();
        }else if(e.getSource() == panelVendedores.exportarVendedor){
              crearReporteVendedores();
        }
        else if(e.getSource() == botonCerrarSesión){
            ordenarSucursal();
            escribirDatos(sucursales);
            ordenarProductos();
            escribirDatos(productos);
            ordenarClientes();
            escribirDatos(clientes);
            ordenarVendedores();
            escribirDatos(vendedores);
            JOptionPane.showMessageDialog(null,"Sesión Terminada", "Cerrar Sesión",JOptionPane.INFORMATION_MESSAGE);
            dispose();
            VentanaLogin ventanaLogin = new VentanaLogin(vendedores);
            ventanaLogin.setVisible(true);
        }

    }

    /****************************************************************************************************************
     * INVOCAR VENTANA PARA CREAR Y ACTUALIZAR NUEVA SUCURSAL Y FUNCIONES: ACTUALIZAR, ELIMINAR, CARGA MASIVA Y PDF *
     ****************************************************************************************************************/

    public Object leerDatosSucursal(){
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
            JOptionPane.showMessageDialog(null,"Ya no se pueden crear más sucursales","Aviso",0);
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
            JOptionPane.showMessageDialog(null,"No hay nada seleccionado","Aviso",1);
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
                JOptionPane.showMessageDialog(null,"Sucursal eliminada con éxito","Aviso",0);
                correrValores(sucursales,indiceBusquedaSucursal);
            }else{
                panelsucursal.modeloTabla.removeRow(filaSeleccionada);
                contadorActualSucursal--;
                JOptionPane.showMessageDialog(null,"Sucursal eliminada con éxito","Aviso",0);
            }
        }else{
            JOptionPane.showMessageDialog(null,"No hay nada seleccionado","Aviso",0);
        }
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
        }else if(object.equals(productos)){
            for(int i = 0; i < productos.length; i++){
                if(productos[i] != null){
                    if(productos[i].getCodigoProducto() == codigo &&
                            productos[i].getNombreProducto().equals(nombre)){
                        indice = i;
                        return indice;
                    }
                }
            }
        }else if(object.equals(clientes)){
            for(int i = 0; i < clientes.length; i++){
                if(clientes[i] != null){
                    if(clientes[i].getCodigoCliente() == codigo &&
                            clientes[i].getNombreCliente().equals(nombre)){
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
        }
    }

    public void cargaMasivaSucursal(){
        String ruta = escogerArchivo();
        String content = getContentOfFile(ruta);
        JsonParser parser = new JsonParser();
        JsonArray arreglo = parser.parse(content).getAsJsonArray();
        int contador = 0;
        do{
            for(int i = 0; i < arreglo.size();i++){
                contador+=1;
                JsonObject objeto = arreglo.get(i).getAsJsonObject();
                long codigo = objeto.get("codigo").getAsLong();
                String nombre = objeto.get("nombre").getAsString();
                String direccion = objeto.get("direccion").getAsString();
                String correo = objeto.get("correo").getAsString();
                long telefono = objeto.get("telefono").getAsLong();
                if(!buscarCodigoSucursal(sucursales,codigo)){
                    if(contadorActualSucursal < 50){
                        Sucursales sucursal = new Sucursales(codigo,nombre,direccion,correo,telefono);
                        sucursales[contadorActualSucursal] = sucursal;
                        contadorActualSucursal++;
                    }
                }
            }
        }while(contador<50);

        panelsucursal.modeloTabla.setRowCount(0);
        for(int i = 0; i < sucursales.length;i++){
            if(sucursales[i] != null){
                long codigo = sucursales[i].getCodigoSucursal();
                String nombre = sucursales[i].getNombreSucursal();
                String direccion = sucursales[i].getDireccionSucursal();
                String correo = sucursales[i].getCorreoSucursal();
                long telefono = sucursales[i].getTelefonoSucursal();
                String[] fila = {String.valueOf(codigo),nombre,direccion,correo,String.valueOf(telefono)};
                panelsucursal.modeloTabla.addRow(fila);
            }
        }

    }
    public String escogerArchivo(){
        JFileChooser f = new JFileChooser();
        f.showOpenDialog(this);
        File archivo = f.getSelectedFile();
        String ruta = archivo.getAbsolutePath();
        return ruta;
    }
    public String getContentOfFile(String pathname) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(pathname);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String content = "";
            String linea;
            while ((linea = br.readLine()) != null) {
                content += linea + "\n";
            }
            return content;
        }catch (FileNotFoundException fnfe) {
            System.err.println("No se encontró el archivo. Inténtelo de nuevo");
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            }catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return "";
    }
    public boolean buscarCodigoSucursal(Sucursales[] sucursales, long codigo){
        for(int i = 0; i < sucursales.length;i++){
            if(sucursales[i] != null){
                if(sucursales[i].getCodigoSucursal() == codigo){
                    return true;
                }
            }
        }
        return false;
    }

    public void crearReporteSucursales(){
        PlantillaSucursales plantilla = new PlantillaSucursales(new Date().toString(),"blue_logo.jpeg",this.sucursales);
        plantilla.crearPlantilla();
        try{
            File path = new File("Reporte_Sucursales.pdf");
            Desktop.getDesktop().open(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /****************************************************************************************************************
     * INVOCAR VENTANA PARA CREAR Y ACTUALIZAR PRODUCTOS Y FUNCIONES: ACTUALIZAR, ELIMINAR, CARGA MASIVA Y PDF *
     ****************************************************************************************************************/
    public Object leerDatosProductos(){
        try{
            FileInputStream lectura = new FileInputStream("productos.bin");
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
    private void cargarDatosProductos(){
        if(!(leerDatosProductos() == null)){
            productos = ((Productos[]) leerDatosProductos());
            for(int i = 0; i < productos.length ; i++){
                if(productos[i] != null){
                    String[] fila = {String.valueOf(productos[i].getCodigoProducto()),
                            productos[i].getNombreProducto(),productos[i].getDescripcionProducto(),String.valueOf(productos[i].getCantidadProducto()),
                            String.valueOf(productos[i].getPrecioProducto())};
                    panelProductos.modeloTabla.addRow(fila);
                    contadorActualproductos++;
                }
            }
        }
    }

    public void ventanaCrearNuevoProducto(){
        crearNuevoProducto = new JDialog();
        crearNuevoProducto.setLayout(null);
        crearNuevoProducto.setResizable(false);
        encabezadoProdD = new JLabel("Crear Nuevo Producto");
        encabezadoProdD.setBounds(164,47,190,30);
        encabezadoProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        codigoProdD = new JLabel("Código");
        codigoProdD.setBounds(92,104,66,26);
        codigoProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCodigoProdD = new JTextField();
        cajaCodigoProdD.setBounds(197,104,218,26);
        cajaCodigoProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        NombreProdD = new JLabel("Nombre");
        NombreProdD.setBounds(92,156,75,26);
        NombreProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNombreProdD = new JTextField();
        cajaNombreProdD.setBounds(197,156,218,26);
        cajaNombreProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        DescripcionProd = new JLabel("Descripción");
        DescripcionProd.setBounds(88,209,95,26);
        DescripcionProd.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaDescripcionD = new JTextField();
        cajaDescripcionD.setBounds(197,209,218,26);
        cajaDescripcionD.setFont(new Font("Dialog",Font.PLAIN,18));

        CantidadProd = new JLabel("Cantidad");
        CantidadProd.setBounds(92,262,75,26);
        CantidadProd.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCantidadD = new JTextField();
        cajaCantidadD.setBounds(197,262,218,26);
        cajaCantidadD.setFont(new Font("Dialog",Font.PLAIN,18));

        PrecioProd = new JLabel("Precio");
        PrecioProd.setBounds(92,315,75,26);
        PrecioProd.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaPrecioD = new JTextField();
        cajaPrecioD.setBounds(197,315,218,26);
        cajaPrecioD.setFont(new Font("Dialog",Font.PLAIN,18));

        botonAgregarP = new JButton("Agregar");
        botonAgregarP.setBounds(185,361,154,32);
        botonAgregarP.setFont(new Font("Dialog",Font.PLAIN,18));

        botonAgregarP.addActionListener(this);

        crearNuevoProducto.add(encabezadoProdD);
        crearNuevoProducto.add(codigoProdD);
        crearNuevoProducto.add(cajaCodigoProdD);
        crearNuevoProducto.add(NombreProdD);
        crearNuevoProducto.add(cajaNombreProdD);
        crearNuevoProducto.add(DescripcionProd);
        crearNuevoProducto.add(cajaDescripcionD);
        crearNuevoProducto.add(CantidadProd);
        crearNuevoProducto.add(cajaCantidadD);
        crearNuevoProducto.add(PrecioProd);
        crearNuevoProducto.add(cajaPrecioD);
        crearNuevoProducto.add(botonAgregarP);

        crearNuevoProducto.setSize(510,479);
        crearNuevoProducto.setLocationRelativeTo(null);
        crearNuevoProducto.setVisible(true);

    }
    public void funcionbotonAgregarProducto(){
        long codigoProducto;
        String nombreProducto;
        String descripcionProducto;
        long cantidadProducto;
        double precioProducto;

        codigoProducto = Long.parseLong(cajaCodigoProdD.getText().trim());
        nombreProducto = cajaNombreProdD.getText().trim();
        descripcionProducto = cajaDescripcionD.getText().trim();
        cantidadProducto = Long.parseLong(cajaCantidadD.getText().trim());
        precioProducto = Double.parseDouble(cajaPrecioD.getText().trim());

        if(contadorActualproductos < 200){
            String[] fila = {cajaCodigoProdD.getText(),cajaNombreProdD.getText(),cajaDescripcionD.getText(),
                    cajaCantidadD.getText().trim(),cajaPrecioD.getText().trim()};
            panelProductos.modeloTabla.addRow(fila);
            productos[contadorActualproductos] = new Productos(codigoProducto,nombreProducto,descripcionProducto,
                    cantidadProducto,precioProducto);
            contadorActualproductos++;
            cajaCodigoProdD.setText("");
            cajaNombreProdD.setText("");
            cajaDescripcionD.setText("");
            cajaCantidadD.setText("");
            cajaPrecioD.setText("");
            panelProductos.remove(frame);
            agregarGraficaProductos(productos);
        }else{
            JOptionPane.showMessageDialog(null,"Ya no se pueden agregar más productos","Aviso",0);
        }
    }

    public void ventanaActualizarProducto(){
        crearNuevoProducto = new JDialog();
        crearNuevoProducto.setLayout(null);
        crearNuevoProducto.setResizable(false);
        encabezadoProdD = new JLabel("Actualizar Producto");
        encabezadoProdD.setBounds(164,47,190,30);
        encabezadoProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        codigoProdD = new JLabel("Código");
        codigoProdD.setBounds(92,104,66,26);
        codigoProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCodigoProdD = new JTextField();
        cajaCodigoProdD.setBounds(197,104,218,26);
        cajaCodigoProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        NombreProdD = new JLabel("Nombre");
        NombreProdD.setBounds(92,156,75,26);
        NombreProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNombreProdD = new JTextField();
        cajaNombreProdD.setBounds(197,156,218,26);
        cajaNombreProdD.setFont(new Font("Dialog",Font.PLAIN,18));

        DescripcionProd = new JLabel("Descripción");
        DescripcionProd.setBounds(88,209,95,26);
        DescripcionProd.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaDescripcionD = new JTextField();
        cajaDescripcionD.setBounds(197,209,218,26);
        cajaDescripcionD.setFont(new Font("Dialog",Font.PLAIN,18));

        CantidadProd = new JLabel("Cantidad");
        CantidadProd.setBounds(92,262,75,26);
        CantidadProd.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCantidadD = new JTextField();
        cajaCantidadD.setBounds(197,262,218,26);
        cajaCantidadD.setFont(new Font("Dialog",Font.PLAIN,18));

        PrecioProd = new JLabel("Precio");
        PrecioProd.setBounds(92,315,75,26);
        PrecioProd.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaPrecioD = new JTextField();
        cajaPrecioD.setBounds(197,315,218,26);
        cajaPrecioD.setFont(new Font("Dialog",Font.PLAIN,18));

        botonActualizarP = new JButton("Actualizar");
        botonActualizarP.setBounds(185,361,154,32);
        botonActualizarP.setFont(new Font("Dialog",Font.PLAIN,18));

        botonActualizarP.addActionListener(this);

        crearNuevoProducto.add(encabezadoProdD);
        crearNuevoProducto.add(codigoProdD);
        crearNuevoProducto.add(cajaCodigoProdD);
        crearNuevoProducto.add(NombreProdD);
        crearNuevoProducto.add(cajaNombreProdD);
        crearNuevoProducto.add(DescripcionProd);
        crearNuevoProducto.add(cajaDescripcionD);
        crearNuevoProducto.add(CantidadProd);
        crearNuevoProducto.add(cajaCantidadD);
        crearNuevoProducto.add(PrecioProd);
        crearNuevoProducto.add(cajaPrecioD);
        crearNuevoProducto.add(botonActualizarP);

        crearNuevoProducto.setSize(510,479);
        crearNuevoProducto.setLocationRelativeTo(null);
        crearNuevoProducto.setVisible(true);

    }
    public void cargarDatosVentanaActualizarProducto(){
        if(!panelProductos.tablaProductos.getSelectionModel().isSelectionEmpty()){
            ventanaActualizarProducto();
            int filaSeleccionada = panelProductos.tablaProductos.getSelectedRow();
            String codigo = (String)panelProductos.modeloTabla.getValueAt(filaSeleccionada,0);
            String nombre = (String)panelProductos.modeloTabla.getValueAt(filaSeleccionada,1);
            String descripcion = (String)panelProductos.modeloTabla.getValueAt(filaSeleccionada,2);
            String cantidad = (String)panelProductos.modeloTabla.getValueAt(filaSeleccionada,3);
            String precio = (String)panelProductos.modeloTabla.getValueAt(filaSeleccionada,4);
            cajaCodigoProdD.setText(codigo);
            cajaNombreProdD.setText(nombre);
            cajaDescripcionD.setText(descripcion);
            cajaCantidadD.setText(cantidad);
            cajaPrecioD.setText(precio);
        }else{
            JOptionPane.showMessageDialog(null,"No hay nada seleccionado","Aviso",1);
        }
    }
    public void funcionbotonActualizarProducto(){
        int filaSeleccionada = panelProductos.tablaProductos.getSelectedRow();
        //TOMAMOS LOS PRIMEROS DOS VALORES DE LA TABLA PARA BUSCARLOS DENTRO DEL ARREGLO
        Long codigoBusqueda = Long.parseLong(((String)panelProductos.modeloTabla.getValueAt(filaSeleccionada,0)).trim());
        String nombreBusqueda = ((String)panelProductos.modeloTabla.getValueAt(filaSeleccionada,1)).trim();
        int indiceProducto = buscarEnArreglo(productos,codigoBusqueda,nombreBusqueda);
        String codigo = cajaCodigoProdD.getText();
        String nombre = cajaNombreProdD.getText();
        String descripcion = cajaDescripcionD.getText();
        String cantidad = cajaCantidadD.getText();
        String precio = cajaPrecioD.getText();
        panelProductos.modeloTabla.setValueAt(codigo,filaSeleccionada,0);
        panelProductos.modeloTabla.setValueAt(nombre,filaSeleccionada,1);
        panelProductos.modeloTabla.setValueAt(descripcion,filaSeleccionada,2);
        panelProductos.modeloTabla.setValueAt(cantidad,filaSeleccionada,3);
        panelProductos.modeloTabla.setValueAt(precio,filaSeleccionada,4);
        //SI EL PRODUCTO NO SE ENCUENTRA EN EL ARREGLO NO SE MODIFICA
        if(indiceProducto != -1){
            productos[indiceProducto].setCodigoProducto(Long.parseLong(codigo.trim()));
            productos[indiceProducto].setNombreProducto(nombre);
            productos[indiceProducto].setDescripcionProducto(descripcion);
            productos[indiceProducto].setCantidadProducto(Long.parseLong(cantidad.trim()));
            productos[indiceProducto].setPrecioProducto(Double.parseDouble(precio.trim()));
            panelProductos.remove(frame);
            agregarGraficaProductos(productos);
        }
        //AL TERMINAR DE ACTUALIZAR CERRAMOS LA VENTANA DE DIALOGO
        crearNuevoProducto.dispose();
    }

    public void eliminarProducto(){
        if(!panelProductos.tablaProductos.getSelectionModel().isSelectionEmpty()){
            int filaSeleccionada = panelProductos.tablaProductos.getSelectedRow();
            long codigo = Long.parseLong(((String)panelProductos.modeloTabla.getValueAt(filaSeleccionada,0)).trim());
            String nombre = ((String)panelProductos.modeloTabla.getValueAt(filaSeleccionada,1)).trim();
            int indiceBusquedaProducto = buscarEnArreglo(productos,codigo,nombre);
            if(indiceBusquedaProducto!=-1) {
                productos[indiceBusquedaProducto] = null;
                contadorActualproductos--;
                panelProductos.modeloTabla.removeRow(filaSeleccionada);
                JOptionPane.showMessageDialog(null,"Producto eliminado con éxito","Aviso",1);
                correrValores(productos,indiceBusquedaProducto);
                panelProductos.remove(frame);
                agregarGraficaProductos(productos);
            }else{
                panelProductos.modeloTabla.removeRow(filaSeleccionada);
                contadorActualproductos--;
                JOptionPane.showMessageDialog(null,"Producto eliminado con éxito","Aviso",1);
                panelProductos.remove(frame);
                agregarGraficaProductos(productos);
            }
        }else{
            JOptionPane.showMessageDialog(null,"No hay nada seleccionado","Aviso",1);
        }
    }

    public void cargaMasivaProductos(){
        String ruta = escogerArchivo();
        String content = getContentOfFile(ruta);
        JsonParser parser = new JsonParser();
        JsonArray arreglo = parser.parse(content).getAsJsonArray();
        int contador = 0;
        do{
            for(int i = 0; i < arreglo.size();i++){
                contador+=1;
                JsonObject objeto = arreglo.get(i).getAsJsonObject();
                long codigo = objeto.get("codigo").getAsLong();
                String nombre = objeto.get("nombre").getAsString();
                String descripcion = objeto.get("descripcion").getAsString();
                long cantidad = objeto.get("cantidad").getAsLong();
                double precio = objeto.get("precio").getAsDouble();
                if(!buscarCodigoProducto(productos,codigo)){
                    if(contadorActualproductos < 200){
                        Productos producto = new Productos(codigo,nombre,descripcion,cantidad,precio);
                        productos[contadorActualproductos] = producto;
                        contadorActualproductos++;
                    }
                }
            }
        }while(contador<200);

        panelProductos.modeloTabla.setRowCount(0);
        for(int i = 0; i < productos.length;i++){
            if(productos[i] != null){
                long codigo = productos[i].getCodigoProducto();
                String nombre = productos[i].getNombreProducto();
                String descripcion = productos[i].getDescripcionProducto();
                long cantidad = productos[i].getCantidadProducto();
                double precio = productos[i].getPrecioProducto();
                String[] fila = {String.valueOf(codigo),nombre,descripcion,String.valueOf(cantidad),String.valueOf(precio)};
                panelProductos.modeloTabla.addRow(fila);
            }
        }
        agregarGraficaProductos(productos);
    }
    public boolean buscarCodigoProducto(Productos[] productos, long codigo){
        for(int i = 0; i < productos.length;i++){
            if(productos[i] != null){
                if(productos[i].getCodigoProducto() == codigo){
                    return true;
                }
            }
        }
        return false;
    }

    public void crearReporteProductos(){
        PlantillaProductos plantilla = new PlantillaProductos(new Date().toString(),"blue_logo.jpeg",this.productos);
        plantilla.crearPlantilla();
        try{
            File path = new File("Reporte_Productos.pdf");
            Desktop.getDesktop().open(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarGraficaProductos(Productos[] productos){
        long cantidadMayor=0,cantidadMedia=0,cantidadMin=0;
        int indice1 = 0,indice2 = 0,indice3=0;

        for(int i= 0; i < productos.length ;i++){
            if(productos[i] != null){
                if(productos[i].getCantidadProducto() > cantidadMayor){
                    cantidadMayor = productos[i].getCantidadProducto();
                    indice1 = i;
                }
            }
        }

        for(int i= 0; i < productos.length ;i++){
            if(productos[i] != null){
                if(i != indice1){
                    if(productos[i].getCantidadProducto() > cantidadMedia){
                        cantidadMedia = productos[i].getCantidadProducto();
                        indice2 = i;
                    }
                }
            }
        }

        for(int i= 0; i < productos.length ;i++){
            if(productos[i] != null){
                if(i == indice1 || i == indice2){
                    continue;
                }else{
                    if(productos[i].getCantidadProducto() > cantidadMin){
                        cantidadMin = productos[i].getCantidadProducto();
                        indice3 = i;
                    }
                }
            }
        }
        DefaultCategoryDataset datasetBarras = new DefaultCategoryDataset();
        if(productos[indice1] != null){
            datasetBarras.setValue(cantidadMayor,productos[indice1].getNombreProducto(),productos[indice1].getNombreProducto());
            datasetBarras.setValue(cantidadMedia,productos[indice2].getNombreProducto(),productos[indice2].getNombreProducto());
            datasetBarras.setValue(cantidadMin,productos[indice3].getNombreProducto(),productos[indice3].getNombreProducto());
            JFreeChart chartBar = ChartFactory.createBarChart("Top 3 productos","Producto","Cantidad",datasetBarras, PlotOrientation.VERTICAL,true,true,false);
            frame = new ChartPanel(chartBar);
            frame.setBounds(450,274,281,253);
            panelProductos.add(frame);
        }
    }
   /****************************************************************************************************************
    * INVOCAR VENTANA PARA CREAR Y ACTUALIZAR CLIENTES Y FUNCIONES: ACTUALIZAR, ELIMINAR, CARGA MASIVA Y PDF *
    ****************************************************************************************************************/
   public Object leerDatosClientes(){
       try{
           FileInputStream lectura = new FileInputStream("clientes.bin");
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
   private void cargarDatosClientes(){
        if(!(leerDatosClientes() == null)){
            clientes = ((Clientes[]) leerDatosClientes());
            for(int i = 0; i < clientes.length; i++){
                if(clientes[i] != null){
                    String[] fila = {String.valueOf(clientes[i].getCodigoCliente()),
                            clientes[i].getNombreCliente(),String.valueOf(clientes[i].getNitCliente()),
                            clientes[i].getCorreoCliente(),
                            String.valueOf(clientes[i].getGeneroCliente()).toUpperCase()};
                    panelClientes.modeloTabla.addRow(fila);
                    contadorActualClientes++;
                }
            }
        }
    }

   public void ventanaCrearNuevoCliente(){
        crearNuevoCliente = new JDialog();
        crearNuevoCliente.setLayout(null);
        crearNuevoCliente.setResizable(false);
        encabezadoClienteD = new JLabel("Crear Nuevo Cliente");
        encabezadoClienteD.setBounds(164,47,190,30);
        encabezadoClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        codigoClienteD = new JLabel("Código");
        codigoClienteD.setBounds(92,104,66,26);
        codigoClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCodigoClienteD = new JTextField();
        cajaCodigoClienteD.setBounds(197,104,218,26);
        cajaCodigoClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        NombreClienteD = new JLabel("Nombre");
        NombreClienteD.setBounds(92,156,75,26);
        NombreClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNombreClienteD = new JTextField();
        cajaNombreClienteD.setBounds(197,156,218,26);
        cajaNombreClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        NitCliente = new JLabel("NIT");
        NitCliente.setBounds(88,209,95,26);
        NitCliente.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNitClienteD = new JTextField();
        cajaNitClienteD.setBounds(197,209,218,26);
        cajaNitClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        CorreoCliente = new JLabel("Correo");
        CorreoCliente.setBounds(92,262,75,26);
        CorreoCliente.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCorreoClienteD = new JTextField();
        cajaCorreoClienteD.setBounds(197,262,218,26);
        cajaCorreoClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        GeneroCliente = new JLabel("Género");
        GeneroCliente.setBounds(92,315,75,26);
        GeneroCliente.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaGeneroClienteD = new JTextField();
        cajaGeneroClienteD.setBounds(197,315,218,26);
        cajaGeneroClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        botonAgregarC = new JButton("Agregar");
        botonAgregarC.setBounds(185,361,154,32);
        botonAgregarC.setFont(new Font("Dialog",Font.PLAIN,18));
        botonAgregarC.addActionListener(this);

        crearNuevoCliente.add(encabezadoClienteD);
        crearNuevoCliente.add(codigoClienteD);
        crearNuevoCliente.add(cajaCodigoClienteD);
        crearNuevoCliente.add(NombreClienteD);
        crearNuevoCliente.add(cajaNombreClienteD);
        crearNuevoCliente.add(NitCliente);
        crearNuevoCliente.add(cajaNitClienteD);
        crearNuevoCliente.add(CorreoCliente);
        crearNuevoCliente.add(cajaCorreoClienteD);
        crearNuevoCliente.add(GeneroCliente);
        crearNuevoCliente.add(cajaGeneroClienteD);
        crearNuevoCliente.add(botonAgregarC);

        crearNuevoCliente.setSize(510,479);
        crearNuevoCliente.setLocationRelativeTo(null);
        crearNuevoCliente.setVisible(true);

    }
   public void funcionbotonAgregarCliente(){
        long codigoCliente = Long.parseLong(cajaCodigoClienteD.getText().trim());
        String nombreCliente = cajaNombreClienteD.getText().trim();
        String nitCliente = cajaNitClienteD.getText().trim();
        String correoCliente = cajaCorreoClienteD.getText().trim();
        char generoCliente = cajaGeneroClienteD.getText().trim().toUpperCase().charAt(0);
        if(contadorActualClientes < 100){
            String[] fila = {cajaCodigoClienteD.getText(),cajaNombreClienteD.getText(),cajaNitClienteD.getText(),
                    cajaCorreoClienteD.getText(),cajaGeneroClienteD.getText()};
            panelClientes.modeloTabla.addRow(fila);
            clientes[contadorActualClientes] = new Clientes(codigoCliente,nombreCliente,nitCliente,
                    correoCliente,generoCliente);
            contadorActualClientes++;
            cajaCodigoClienteD.setText("");
            cajaNombreClienteD.setText("");
            cajaNitClienteD.setText("");
            cajaCorreoClienteD.setText("");
            cajaGeneroClienteD.setText("");
            panelClientes.remove(frameC);
            agregarGraficaClientes(clientes);
        }else{
            JOptionPane.showMessageDialog(null,"Ya no se pueden agregar más clientes","Aviso",0);
        }
    }

   public void ventanaActualizarCliente(){
        crearNuevoCliente = new JDialog();
        crearNuevoCliente.setLayout(null);
        crearNuevoCliente.setResizable(false);
        encabezadoClienteD = new JLabel("Actualizar Cliente");
        encabezadoClienteD.setBounds(164,47,190,30);
        encabezadoClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        codigoClienteD = new JLabel("Código");
        codigoClienteD.setBounds(92,104,66,26);
        codigoClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCodigoClienteD = new JTextField();
        cajaCodigoClienteD.setBounds(197,104,218,26);
        cajaCodigoClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        NombreClienteD = new JLabel("Nombre");
        NombreClienteD.setBounds(92,156,75,26);
        NombreClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNombreClienteD = new JTextField();
        cajaNombreClienteD.setBounds(197,156,218,26);
        cajaNombreClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        NitCliente = new JLabel("NIT");
        NitCliente.setBounds(88,209,95,26);
        NitCliente.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNitClienteD = new JTextField();
        cajaNitClienteD.setBounds(197,209,218,26);
        cajaNitClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        CorreoCliente = new JLabel("Correo");
        CorreoCliente.setBounds(92,262,75,26);
        CorreoCliente.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCorreoClienteD = new JTextField();
        cajaCorreoClienteD.setBounds(197,262,218,26);
        cajaCorreoClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        GeneroCliente = new JLabel("Género");
        GeneroCliente.setBounds(92,315,75,26);
        GeneroCliente.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaGeneroClienteD = new JTextField();
        cajaGeneroClienteD.setBounds(197,315,218,26);
        cajaGeneroClienteD.setFont(new Font("Dialog",Font.PLAIN,18));

        botonActualizarC = new JButton("Actualizar");
        botonActualizarC.setBounds(185,361,154,32);
        botonActualizarC.setFont(new Font("Dialog",Font.PLAIN,18));
        botonActualizarC.addActionListener(this);

        crearNuevoCliente.add(encabezadoClienteD);
        crearNuevoCliente.add(codigoClienteD);
        crearNuevoCliente.add(cajaCodigoClienteD);
        crearNuevoCliente.add(NombreClienteD);
        crearNuevoCliente.add(cajaNombreClienteD);
        crearNuevoCliente.add(NitCliente);
        crearNuevoCliente.add(cajaNitClienteD);
        crearNuevoCliente.add(CorreoCliente);
        crearNuevoCliente.add(cajaCorreoClienteD);
        crearNuevoCliente.add(GeneroCliente);
        crearNuevoCliente.add(cajaGeneroClienteD);
        crearNuevoCliente.add(botonActualizarC);

        crearNuevoCliente.setSize(510,479);
        crearNuevoCliente.setLocationRelativeTo(null);
        crearNuevoCliente.setVisible(true);
    }
   public void cargarDatosVentanaActualizarCliente(){
        if(!panelClientes.tablaCliente.getSelectionModel().isSelectionEmpty()){
            ventanaActualizarCliente();
            int filaSeleccionada = panelClientes.tablaCliente.getSelectedRow();
            String codigo = (String)panelClientes.modeloTabla.getValueAt(filaSeleccionada,0);
            String nombre = (String)panelClientes.modeloTabla.getValueAt(filaSeleccionada,1);
            String nit = (String)panelClientes.modeloTabla.getValueAt(filaSeleccionada,2);
            String correo = (String)panelClientes.modeloTabla.getValueAt(filaSeleccionada,3);
            String genero = (String)panelClientes.modeloTabla.getValueAt(filaSeleccionada,4);
            cajaCodigoClienteD.setText(codigo);
            cajaNombreClienteD.setText(nombre);
            cajaNitClienteD.setText(nit);
            cajaCorreoClienteD.setText(correo);
            cajaGeneroClienteD.setText(genero);
        }else{
            JOptionPane.showMessageDialog(null,"No hay nada seleccionado","Aviso",1);
        }
    }
   public void funcionbotonActualizarCliente(){
        int filaSeleccionada = panelClientes.tablaCliente.getSelectedRow();
        //TOMAMOS LOS PRIMEROS DOS VALORES DE LA TABLA PARA BUSCARLOS DENTRO DEL ARREGLO
        Long codigoBusqueda = Long.parseLong(((String)panelClientes.modeloTabla.getValueAt(filaSeleccionada,0)).trim());
        String nombreBusqueda = ((String)panelClientes.modeloTabla.getValueAt(filaSeleccionada,1)).trim();
        int indiceCliente = buscarEnArreglo(clientes,codigoBusqueda,nombreBusqueda);
        String codigo = cajaCodigoClienteD.getText().trim();
        String nombre = cajaNombreClienteD.getText().trim();
        String nit = cajaNitClienteD.getText().trim();
        String correo = cajaCorreoClienteD.getText().trim();
        String genero = cajaGeneroClienteD.getText().toUpperCase();
        panelClientes.modeloTabla.setValueAt(codigo,filaSeleccionada,0);
        panelClientes.modeloTabla.setValueAt(nombre,filaSeleccionada,1);
        panelClientes.modeloTabla.setValueAt(nit,filaSeleccionada,2);
        panelClientes.modeloTabla.setValueAt(correo,filaSeleccionada,3);
        panelClientes.modeloTabla.setValueAt(genero,filaSeleccionada,4);
        //SI EL CLIENTE NO SE ENCUENTRA EN EL ARREGLO NO SE MODIFICA
        if(indiceCliente != -1){
            clientes[indiceCliente].setCodigoCliente(Long.parseLong(codigo.trim()));
            clientes[indiceCliente].setNombreCliente(nombre);
            clientes[indiceCliente].setNitCliente(nit.trim());
            clientes[indiceCliente].setCorreoCliente(correo);
            clientes[indiceCliente].setGeneroCliente(genero.charAt(0));
            panelClientes.remove(frameC);
            agregarGraficaClientes(clientes);
        }
        //AL TERMINAR DE ACTUALIZAR CERRAMOS LA VENTANA DE DIALOGO
        crearNuevoCliente.dispose();
    }

   public void eliminarCliente(){
        if(!panelClientes.tablaCliente.getSelectionModel().isSelectionEmpty()){
            int filaSeleccionada = panelClientes.tablaCliente.getSelectedRow();
            long codigo = Long.parseLong(((String)panelClientes.modeloTabla.getValueAt(filaSeleccionada,0)).trim());
            String nombre = ((String)panelClientes.modeloTabla.getValueAt(filaSeleccionada,1)).trim();
            int indiceBusquedaCliente = buscarEnArreglo(clientes,codigo,nombre);
            if(indiceBusquedaCliente!=-1) {
                clientes[indiceBusquedaCliente] = null;
                contadorActualClientes--;
                panelClientes.modeloTabla.removeRow(filaSeleccionada);
                JOptionPane.showMessageDialog(null,"Cliente eliminado con éxito","Aviso",1);
                correrValores(clientes,indiceBusquedaCliente);
                panelClientes.remove(frameC);
                agregarGraficaClientes(clientes);
            }else{
                panelClientes.modeloTabla.removeRow(filaSeleccionada);
                contadorActualClientes--;
                JOptionPane.showMessageDialog(null,"Cliente eliminado con éxito","Aviso",1);
                panelClientes.remove(frameC);
                agregarGraficaClientes(clientes);
            }
        }else{
            JOptionPane.showMessageDialog(null,"No hay nada seleccionado","Aviso",1);
        }
    }

   public void cargaMasivaClientes(){
        String ruta = escogerArchivo();
        String content = getContentOfFile(ruta);
        JsonParser parser = new JsonParser();
        JsonArray arreglo = parser.parse(content).getAsJsonArray();
        int contador = 0;
        do{
            for(int i = 0; i < arreglo.size();i++){
                contador+=1;
                JsonObject objeto = arreglo.get(i).getAsJsonObject();
                long codigo = objeto.get("codigo").getAsLong();
                String nombre = objeto.get("nombre").getAsString().trim();
                String nit = objeto.get("nit").getAsString().trim();
                String correo = objeto.get("correo").getAsString().trim();
                char genero = objeto.get("genero").getAsCharacter();
                if(!buscarCodigoCliente(clientes,codigo)){
                    if(contadorActualClientes < 100){
                        Clientes cliente = new Clientes(codigo,nombre,nit,correo,genero);
                        clientes[contadorActualClientes] = cliente;
                        contadorActualClientes++;
                    }
                }
            }
        }while(contador<100);

        panelClientes.modeloTabla.setRowCount(0);
        for(int i = 0; i < clientes.length;i++){
            if(clientes[i] != null){
                long codigo = clientes[i].getCodigoCliente();
                String nombre = clientes[i].getNombreCliente();
                String nit  = clientes[i].getNitCliente();
                String correo = clientes[i].getCorreoCliente();
                char genero = clientes[i].getGeneroCliente();
                String[] fila = {String.valueOf(codigo),nombre,nit,correo, String.valueOf(genero)};
                panelClientes.modeloTabla.addRow(fila);
            }
        }
        panelClientes.remove(frameC);
        agregarGraficaClientes(clientes);
    }
   public boolean buscarCodigoCliente(Clientes[] clientes, long codigo){
        for(int i = 0; i < clientes.length;i++){
            if(clientes[i] != null){
                if(clientes[i].getCodigoCliente() == codigo){
                    return true;
                }
            }
        }
        return false;
    }

   public void crearReporteClientes(){
        PlantillaClientes plantilla = new PlantillaClientes(new Date().toString(),"blue_logo.jpeg",this.clientes);
        plantilla.crearPlantilla();
        try{
            File path = new File("Reporte_Clientes.pdf");
            Desktop.getDesktop().open(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarGraficaClientes(Clientes[] clientes){
        double contadorM = 0, contadorF = 0;

        for(int i= 0; i < clientes.length ;i++){
            if(clientes[i] != null){
                if(clientes[i].getGeneroCliente() == 'M'){
                    contadorM++;
                }else if(clientes[i].getGeneroCliente() == 'F'){
                    contadorF++;
                }
            }
        }

        float porcentajeM = Math.round((float)((contadorM*100)/contadorActualClientes));
        float porcentajeF = Math.round ((float)((contadorF*100)/contadorActualClientes));
        DefaultPieDataset datasetPie = new DefaultPieDataset();
        datasetPie.setValue("%Masculino "+ porcentajeM,new Float(porcentajeM));
        datasetPie.setValue("%Femenino " + porcentajeF,new Float(porcentajeF));
        JFreeChart chartPie = ChartFactory.createPieChart("Género de Clientes",datasetPie,false,true,false);
        frameC = new ChartPanel(chartPie);
        frameC.setBounds(450,274,281,253);
        panelClientes.add(frameC);
    }

    /****************************************************************************************************************
     * INVOCAR VENTANA PARA CREAR Y ACTUALIZAR VENDEDORES Y FUNCIONES: ACTUALIZAR, ELIMINAR, CARGA MASIVA Y PDF *
     ****************************************************************************************************************/
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
            vendedores = ((Vendedores[]) leerDatosVendedores());
            for(int i = 0; i < vendedores.length; i++){
                if(vendedores[i] != null){
                    String[] fila = {String.valueOf(vendedores[i].getCodigoVendedor()),
                            vendedores[i].getNombreVendedor(),String.valueOf(vendedores[i].getCajaVendedor()),
                            String.valueOf(vendedores[i].getNumeroVentas()),
                            String.valueOf(vendedores[i].getGeneroVendedor()).toUpperCase()};
                    panelVendedores.modeloTabla.addRow(fila);
                    contadorActualVendedores++;
                }
            }
        }
    }

    public void ventanaCrearNuevoVendedor(){
        crearNuevoVendedor = new JDialog();
        crearNuevoVendedor.setLayout(null);
        crearNuevoVendedor.setResizable(false);

        encabezadoVendD = new JLabel("Crear Nuevo Vendedor");
        encabezadoVendD.setBounds(164,47,190,30);
        encabezadoVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        codigoVendD = new JLabel("Código");
        codigoVendD.setBounds(92,104,66,26);
        codigoVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCodigoVendD = new JTextField();
        cajaCodigoVendD.setBounds(197,104,218,26);
        cajaCodigoVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        NombreVendD = new JLabel("Nombre");
        NombreVendD.setBounds(92,156,75,26);
        NombreVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNombreVendD = new JTextField();
        cajaNombreVendD.setBounds(197,156,218,26);
        cajaNombreVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        CajaD = new JLabel("Caja");
        CajaD.setBounds(88,209,95,26);
        CajaD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCajaVendD = new JTextField();
        cajaCajaVendD.setBounds(197,209,218,26);
        cajaCajaVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        VentasD = new JLabel("Ventas");
        VentasD.setBounds(92,262,75,26);
        VentasD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaVentasD = new JTextField();
        cajaVentasD.setBounds(197,262,218,26);
        cajaVentasD.setFont(new Font("Dialog",Font.PLAIN,18));

        GeneroVendD = new JLabel("Género");
        GeneroVendD.setBounds(92,315,75,26);
        GeneroVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaGeneroVendD = new JTextField();
        cajaGeneroVendD.setBounds(197,315,218,26);
        cajaGeneroVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        PasswordVendD = new JLabel("Password");
        PasswordVendD.setBounds(85,361,90,26);
        PasswordVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaPasswordVendD = new JPasswordField();
        cajaPasswordVendD.setBounds(197,361,218,26);
        cajaPasswordVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        botonAgregarV = new JButton("Agregar");
        botonAgregarV.setBounds(185,410,154,32);
        botonAgregarV.setFont(new Font("Dialog",Font.PLAIN,18));
        botonAgregarV.addActionListener(this);

        crearNuevoVendedor.add(encabezadoVendD);
        crearNuevoVendedor.add(codigoVendD);
        crearNuevoVendedor.add(cajaCodigoVendD);
        crearNuevoVendedor.add(NombreVendD);
        crearNuevoVendedor.add(cajaNombreVendD);
        crearNuevoVendedor.add(CajaD);
        crearNuevoVendedor.add(cajaCajaVendD);
        crearNuevoVendedor.add(VentasD);
        crearNuevoVendedor.add(cajaVentasD);
        crearNuevoVendedor.add(GeneroVendD);
        crearNuevoVendedor.add(cajaGeneroVendD);
        crearNuevoVendedor.add(PasswordVendD);
        crearNuevoVendedor.add(cajaPasswordVendD);
        crearNuevoVendedor.add(botonAgregarV);

        crearNuevoVendedor.setSize(510,510);
        crearNuevoVendedor.setLocationRelativeTo(null);
        crearNuevoVendedor.setVisible(true);
    }
    public void funcionbotonAgregarVendedor(){
        long codigoVendedor = Long.parseLong(cajaCodigoVendD.getText().trim());
        String nombreVendedor = cajaNombreVendD.getText().trim();
        long cajaVendedor =  Long.parseLong(cajaCajaVendD.getText().trim());
        long numeroVentas = Long.parseLong(cajaVentasD.getText().trim());
        char generoVendedor = cajaGeneroVendD.getText().trim().toUpperCase().charAt(0);
        String passwordVendedor = retornarPasswordVendedor();
        if(contadorActualVendedores < 400){
            String[] fila = {cajaCodigoVendD.getText(),cajaNombreVendD.getText(),cajaCajaVendD.getText(),
                    cajaVentasD.getText(),cajaGeneroVendD.getText()};
            panelVendedores.modeloTabla.addRow(fila);
            vendedores[contadorActualVendedores] = new Vendedores(codigoVendedor,nombreVendedor,cajaVendedor,
                    numeroVentas,generoVendedor,passwordVendedor);
            contadorActualVendedores++;
            cajaCodigoVendD.setText("");
            cajaNombreVendD.setText("");
            cajaCajaVendD.setText("");
            cajaVentasD.setText("");
            cajaGeneroVendD.setText("");
            cajaPasswordVendD.setText("");
            panelVendedores.remove(frameV);
            agregarGraficaVendedores(vendedores);
        }else{
            JOptionPane.showMessageDialog(null,"Ya no se pueden crear más vendedores","Aviso",0);
        }
    }
    public String retornarPasswordVendedor(){
        String contraseña = "";
        for (int i = 0; i < cajaPasswordVendD.getPassword().length; i++) {
            contraseña += cajaPasswordVendD.getPassword()[i];
        }
        return contraseña;
    }

    public void ventanaActualizarVendedor(){
        crearNuevoVendedor = new JDialog();
        crearNuevoVendedor.setLayout(null);
        crearNuevoVendedor.setResizable(false);
        encabezadoVendD = new JLabel("Actualizar Vendedor");
        encabezadoVendD.setBounds(164,47,190,30);
        encabezadoVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        codigoVendD = new JLabel("Código");
        codigoVendD.setBounds(92,104,66,26);
        codigoVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCodigoVendD = new JTextField();
        cajaCodigoVendD.setBounds(197,104,218,26);
        cajaCodigoVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        NombreVendD = new JLabel("Nombre");
        NombreVendD.setBounds(92,156,75,26);
        NombreVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaNombreVendD = new JTextField();
        cajaNombreVendD.setBounds(197,156,218,26);
        cajaNombreVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        CajaD = new JLabel("Caja");
        CajaD.setBounds(88,209,95,26);
        CajaD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaCajaVendD = new JTextField();
        cajaCajaVendD.setBounds(197,209,218,26);
        cajaCajaVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        VentasD = new JLabel("Ventas");
        VentasD.setBounds(92,262,75,26);
        VentasD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaVentasD = new JTextField();
        cajaVentasD.setBounds(197,262,218,26);
        cajaVentasD.setFont(new Font("Dialog",Font.PLAIN,18));

        GeneroVendD = new JLabel("Género");
        GeneroVendD.setBounds(92,315,75,26);
        GeneroVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaGeneroVendD = new JTextField();
        cajaGeneroVendD.setBounds(197,315,218,26);
        cajaGeneroVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        PasswordVendD = new JLabel("Password");
        PasswordVendD.setBounds(85,361,90,26);
        PasswordVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        cajaPasswordVendD = new JPasswordField();
        cajaPasswordVendD.setBounds(197,361,218,26);
        cajaPasswordVendD.setFont(new Font("Dialog",Font.PLAIN,18));

        botonActualizarV = new JButton("Actualizar");
        botonActualizarV.setBounds(185,410,154,32);
        botonActualizarV.setFont(new Font("Dialog",Font.PLAIN,18));
        botonActualizarV.addActionListener(this);

        crearNuevoVendedor.add(encabezadoVendD);
        crearNuevoVendedor.add(codigoVendD);
        crearNuevoVendedor.add(cajaCodigoVendD);
        crearNuevoVendedor.add(NombreVendD);
        crearNuevoVendedor.add(cajaNombreVendD);
        crearNuevoVendedor.add(CajaD);
        crearNuevoVendedor.add(cajaCajaVendD);
        crearNuevoVendedor.add(VentasD);
        crearNuevoVendedor.add(cajaVentasD);
        crearNuevoVendedor.add(GeneroVendD);
        crearNuevoVendedor.add(cajaGeneroVendD);
        crearNuevoVendedor.add(PasswordVendD);
        crearNuevoVendedor.add(cajaPasswordVendD);
        crearNuevoVendedor.add(botonActualizarV);

        crearNuevoVendedor.setSize(510,510);
        crearNuevoVendedor.setLocationRelativeTo(null);
        crearNuevoVendedor.setVisible(true);

    }
    public void cargarDatosVentanaActualizarVendedor(){
        if(!panelVendedores.tablaVendedor.getSelectionModel().isSelectionEmpty()){
            ventanaActualizarVendedor();
            int filaSeleccionada = panelVendedores.tablaVendedor.getSelectedRow();
            String codigo = (String)panelVendedores.modeloTabla.getValueAt(filaSeleccionada,0);
            String nombre = (String)panelVendedores.modeloTabla.getValueAt(filaSeleccionada,1);
            String caja = (String)panelVendedores.modeloTabla.getValueAt(filaSeleccionada,2);
            String ventas = (String)panelVendedores.modeloTabla.getValueAt(filaSeleccionada,3);
            String genero = (String)panelVendedores.modeloTabla.getValueAt(filaSeleccionada,4);
            cajaCodigoVendD.setText(codigo);
            cajaNombreVendD.setText(nombre);
            cajaCajaVendD.setText(caja);
            cajaVentasD.setText(ventas);
            cajaGeneroVendD.setText(genero);
            cajaPasswordVendD.setText(buscarPasswordVendedor(vendedores,Long.parseLong(codigo.trim()),Long.parseLong(caja.trim())));
        }else{
            JOptionPane.showMessageDialog(null,"No hay nada seleccionado","Aviso",1);
        }
    }
    public String buscarPasswordVendedor(Vendedores[] vendedores, long codigo, long caja){
        for(int i = 0; i < vendedores.length;i++){
            if(vendedores[i] != null){
                if((vendedores[i].getCodigoVendedor() == codigo) && (vendedores[i].getCajaVendedor() == caja)){
                    return vendedores[i].getPasswordVendedor();
                }
            }
        }
        return null;
    }
    public void funcionbotonActualizarVendedor(){
        int filaSeleccionada = panelVendedores.tablaVendedor.getSelectedRow();
        //TOMAMOS LOS PRIMEROS DOS VALORES DE LA TABLA PARA BUSCARLOS DENTRO DEL ARREGLO
        Long codigoBusqueda = Long.parseLong(((String)panelVendedores.modeloTabla.getValueAt(filaSeleccionada,0)).trim());
        String cajaVendedorBusqueda = ((String)panelVendedores.modeloTabla.getValueAt(filaSeleccionada,2)).trim();
        int indiceVendedor = buscarVendedor(vendedores,codigoBusqueda,Long.parseLong(cajaVendedorBusqueda));
        String codigo = cajaCodigoVendD.getText().trim();
        String nombre = cajaNombreVendD.getText().trim();
        String caja = cajaCajaVendD.getText().trim();
        String ventas = cajaVentasD.getText().trim();
        String genero = cajaGeneroVendD.getText().toUpperCase();
        String password = retornarPasswordVendedor();
        panelVendedores.modeloTabla.setValueAt(codigo,filaSeleccionada,0);
        panelVendedores.modeloTabla.setValueAt(nombre,filaSeleccionada,1);
        panelVendedores.modeloTabla.setValueAt(caja,filaSeleccionada,2);
        panelVendedores.modeloTabla.setValueAt(ventas,filaSeleccionada,3);
        panelVendedores.modeloTabla.setValueAt(genero,filaSeleccionada,4);
        //SI EL VENDEDOR NO SE ENCUENTRA EN EL ARREGLO NO SE MODIFICA
        if(indiceVendedor != -1){
            vendedores[indiceVendedor].setCodigoVendedor(Long.parseLong(codigo.trim()));
            vendedores[indiceVendedor].setNombreVendedor(nombre);
            vendedores[indiceVendedor].setCajaVendedor(Long.parseLong(caja.trim()));
            vendedores[indiceVendedor].setNumeroVentas(Long.parseLong(ventas.trim()));
            vendedores[indiceVendedor].setGeneroVendedor(genero.toUpperCase().charAt(0));
            vendedores[indiceVendedor].setPasswordVendedor(password);
            panelVendedores.remove(frameV);
            agregarGraficaVendedores(vendedores);
        }
        //AL TERMINAR DE ACTUALIZAR CERRAMOS LA VENTANA DE DIALOGO
        crearNuevoVendedor.dispose();
    }
    public int buscarVendedor(Vendedores[] vendedores, long codigo, long caja){
        for(int i = 0; i < vendedores.length;i++){
            if(vendedores[i] != null){
                if((vendedores[i].getCodigoVendedor() == codigo) && (vendedores[i].getCajaVendedor() == caja)){
                    return i;
                }
            }
        }
        return -1;
    }

    public void eliminarVendedor(){
        if(!panelVendedores.tablaVendedor.getSelectionModel().isSelectionEmpty()){
            int filaSeleccionada = panelVendedores.tablaVendedor.getSelectedRow();
            long codigo = Long.parseLong(((String)panelVendedores.modeloTabla.getValueAt(filaSeleccionada,0)).trim());
            String caja = ((String)panelVendedores.modeloTabla.getValueAt(filaSeleccionada,2)).trim();
            int indiceBusquedaVendedor = buscarVendedor(vendedores,codigo,Long.parseLong(caja));
            if(indiceBusquedaVendedor!=-1) {
                vendedores[indiceBusquedaVendedor] = null;
                contadorActualVendedores--;
                panelVendedores.modeloTabla.removeRow(filaSeleccionada);
                JOptionPane.showMessageDialog(null,"Vendedor elimnado con éxito","Aviso",1);
                correrValores(vendedores,indiceBusquedaVendedor);
                panelVendedores.remove(frameV);
                agregarGraficaVendedores(vendedores);
            }else{
                panelVendedores.modeloTabla.removeRow(filaSeleccionada);
                contadorActualVendedores--;
                JOptionPane.showMessageDialog(null,"Vendedor elimnado con éxito","Aviso",1);
                panelVendedores.remove(frameV);
                agregarGraficaVendedores(vendedores);
            }
        }else{
            JOptionPane.showMessageDialog(null,"No hay nada seleccionado","Aviso",1);
        }
    }

    public void cargaMasivaVendedores(){
        String ruta = escogerArchivo();
        String content = getContentOfFile(ruta);
        JsonParser parser = new JsonParser();
        JsonArray arreglo = parser.parse(content).getAsJsonArray();
        int contador = 0;
        do{
            for(int i = 0; i < arreglo.size();i++){
                contador+=1;
                JsonObject objeto = arreglo.get(i).getAsJsonObject();
                long codigo = objeto.get("codigo").getAsLong();
                String nombre = objeto.get("nombre").getAsString().trim();
                long caja = objeto.get("caja").getAsLong();
                long ventas = objeto.get("ventas").getAsLong();
                char genero = objeto.get("genero").getAsCharacter();
                String password = objeto.get("password").getAsString();
                if(!buscarCodigoVendedor(vendedores,codigo,caja)){
                    if(contadorActualVendedores < 400){
                        Vendedores vendedor = new Vendedores(codigo,nombre,caja,ventas,genero,password);
                        vendedores[contadorActualVendedores] = vendedor;
                        contadorActualVendedores++;
                    }
                }
            }
        }while(contador<400);

        panelVendedores.modeloTabla.setRowCount(0);
        for(int i = 0; i < vendedores.length;i++){
            if(vendedores[i] != null){
                long codigo = vendedores[i].getCodigoVendedor();
                String nombre = vendedores[i].getNombreVendedor();
                String caja  = String.valueOf(vendedores[i].getCajaVendedor());
                String ventas = String.valueOf(vendedores[i].getNumeroVentas());
                char genero = vendedores[i].getGeneroVendedor();
                String[] fila = {String.valueOf(codigo),nombre,caja,ventas, String.valueOf(genero)};
                panelVendedores.modeloTabla.addRow(fila);
            }
        }
        agregarGraficaVendedores(vendedores);
    }
    public boolean buscarCodigoVendedor(Vendedores[] vendedores, long codigo, long caja){
        for(int i = 0; i < vendedores.length;i++){
            if(vendedores[i] != null){
                if((vendedores[i].getCodigoVendedor() == codigo) && (vendedores[i].getCajaVendedor() == caja) ){
                    return true;
                }
            }
        }
        return false;
    }

    public void crearReporteVendedores(){
        PlantillaVendedores plantilla = new PlantillaVendedores(new Date().toString(),"blue_logo.jpeg",this.vendedores);
        plantilla.crearPlantilla();
        try{
            File path = new File("Reporte_Vendedores.pdf");
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregarGraficaVendedores(Vendedores[] vendedores){
        long cantidadMayor=0,cantidadMedia=0,cantidadMin=0;
        int indice1 = 0,indice2 = 0,indice3=0;

        for(int i= 0; i < vendedores.length ;i++){
            if(vendedores[i] != null){
                if(vendedores[i].getNumeroVentas() > cantidadMayor){
                    cantidadMayor = vendedores[i].getNumeroVentas();
                    indice1 = i;
                }
            }
        }

        for(int i= 0; i < vendedores.length ;i++){
            if(vendedores[i] != null){
                if(i != indice1){
                    if(vendedores[i].getNumeroVentas() > cantidadMedia){
                        cantidadMedia = vendedores[i].getNumeroVentas();
                        indice2 = i;
                    }
                }
            }
        }

        for(int i= 0; i < vendedores.length ;i++){
            if(vendedores[i] != null){
                if(i == indice1 || i == indice2){
                    continue;
                }else{
                    if(vendedores[i].getNumeroVentas() > cantidadMin){
                        cantidadMin = vendedores[i].getNumeroVentas();
                        indice3 = i;
                    }
                }
            }
        }
        DefaultCategoryDataset datasetBarras = new DefaultCategoryDataset();
        if(vendedores[indice1] != null){
            datasetBarras.setValue(cantidadMayor,vendedores[indice1].getNombreVendedor(),vendedores[indice1].getNombreVendedor());
            datasetBarras.setValue(cantidadMedia,vendedores[indice2].getNombreVendedor(),vendedores[indice2].getNombreVendedor());
            datasetBarras.setValue(cantidadMin,vendedores[indice3].getNombreVendedor(),vendedores[indice3].getNombreVendedor());
            JFreeChart chartBar = ChartFactory.createBarChart("Top 3 Vendedores","Vendedor","Ventas",datasetBarras, PlotOrientation.VERTICAL,true,true,false);
            frameV = new ChartPanel(chartBar);
            frameV.setBounds(450,274,281,253);
            panelVendedores.add(frameV);
        }

    }
    /******************************************************************************************************
     * FUNCIONES QUE SE APLICAN AL FINALIZAR LA SESIÓN                                                    *
     ******************************************************************************************************/
    public Sucursales[] copiarSucursal(){
        Sucursales[] sucursalCopia = new Sucursales[contadorActualSucursal];
        for(int i = 0; i < contadorActualSucursal ; i++){
            sucursalCopia[i] = sucursales[i];
        }
        return sucursalCopia;
    }
    public void ordenarSucursal(){
        Sucursales[] c = copiarSucursal();
        for(int i = 0; i < contadorActualSucursal; i++){
            for(int j = 0; j < contadorActualSucursal; j++){
                if(c[i].getCodigoSucursal() < c[j].getCodigoSucursal()){
                    Sucursales aux = c[i];
                    c[i] = c[j];
                    c[j] = aux;
                }
            }
        }

        for(int i = 0; i < c.length ; i++){
            sucursales[i] = c[i];
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
    private void escribirDatos(Object object) {
        if(object.equals(sucursales)){
            serializarSucursal(sucursales);
        }else if(object.equals(productos)){
            serializarProductos(productos);
        }else if(object.equals(clientes)){
            serializarClientes(clientes);
        }else if(object.equals(vendedores)){
            serializarVendedores(vendedores);
        }
    }

    public Productos[] copiarProductos(){
        Productos[] productosCopia = new Productos[contadorActualproductos];
        for(int i = 0; i < contadorActualproductos; i++){
            productosCopia[i] = productos[i];
        }
        return productosCopia;
    }
    public void ordenarProductos(){
        Productos[] c = copiarProductos();
        for(int i = 0; i < contadorActualproductos; i++){
            for(int j = 0; j < contadorActualproductos; j++){
                if(c[i].getCodigoProducto() < c[j].getCodigoProducto()){
                    Productos aux = c[i];
                    c[i] = c[j];
                    c[j] = aux;
                }
            }
        }

        for(int i = 0; i < c.length ; i++){
            productos[i] = c[i];
        }

    }
    public void serializarProductos(Object object){
        try{
            FileOutputStream archivo = new FileOutputStream("productos.bin");
            ObjectOutputStream serializar = new ObjectOutputStream(archivo);
            serializar.writeObject(object);
            serializar.close();
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado "  + e);
        }catch (IOException e){
            System.out.println("Error " + e);
        }
    }

    public Clientes[] copiarClientes(){
        Clientes[] clientesCopia = new Clientes[contadorActualClientes];
        for(int i = 0; i < contadorActualClientes; i++){
            clientesCopia[i] = clientes[i];
        }
        return clientesCopia;
    }
    public void ordenarClientes(){
        Clientes[] c = copiarClientes();
        for(int i = 0; i < contadorActualClientes; i++){
            for(int j = 0; j < contadorActualClientes; j++){
                if(c[i].getCodigoCliente() < c[j].getCodigoCliente()){
                    Clientes aux = c[i];
                    c[i] = c[j];
                    c[j] = aux;
                }
            }
        }

        for(int i = 0; i < c.length ; i++){
            clientes[i] = c[i];
        }

    }
    public void serializarClientes(Object object){
        try{
            FileOutputStream archivo = new FileOutputStream("clientes.bin");
            ObjectOutputStream serializar = new ObjectOutputStream(archivo);
            serializar.writeObject(object);
            serializar.close();
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado "  + e);
        }catch (IOException e){
            System.out.println("Error " + e);
        }
    }

    public Vendedores[] copiarVendedores(){
        Vendedores[] vendedoresCopia = new Vendedores[contadorActualVendedores];
        for(int i = 0; i < contadorActualVendedores; i++){
            vendedoresCopia[i] = vendedores[i];
        }
        return vendedoresCopia;
    }
    public void ordenarVendedores(){
        Vendedores[] c = copiarVendedores();
        for(int i = 0; i < contadorActualVendedores; i++){
            for(int j = 0; j < contadorActualVendedores; j++){
                if(c[i].getCodigoVendedor() < c[j].getCodigoVendedor()){
                    Vendedores aux = c[i];
                    c[i] = c[j];
                    c[j] = aux;
                }
            }
        }

        for(int i = 0; i < c.length ; i++){
            vendedores[i] = c[i];
        }

    }
    public void serializarVendedores(Object object){
        try{
            FileOutputStream archivo = new FileOutputStream("vendedores.bin");
            ObjectOutputStream serializar = new ObjectOutputStream(archivo);
            serializar.writeObject(object);
            serializar.close();
        }catch (FileNotFoundException e){
            System.out.println("Archivo no encontrado "  + e);
        }catch (IOException e){
            System.out.println("Error " + e);
        }
    }

}
