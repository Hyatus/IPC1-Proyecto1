package VentanaAdmin;

import Clases.Clientes;
import Clases.Productos;
import Clases.Sucursales;
import Clases.Vendedores;
import PlantillaPDF.PlantillaProductos;
import PlantillaPDF.PlantillaSucursales;
import com.google.gson.*;

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
    public int contadorActualSucursal = 0, contadorActualproductos = 0, ContadorActualClientes = 0,
            ContadorActualVendedores = 0;

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


    public VentanaAdmin(){
        try{
            File archivoSucursales = new File("sucursales.bin");
            File archivoProductos = new File("productos.bin");
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
        System.out.println("Sucursales al inicio " + contadorActualSucursal);
        System.out.println("Productos al inicio " + contadorActualproductos);
        colocarTabbedPanel();
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

        panelVendedores.crearVendedor.addActionListener(this);
        panelVendedores.actualizarVendedor.addActionListener(this);
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
            panelClientes.ventanaCrearNuevoCliente();
        }else if(e.getSource() == panelClientes.actualizarCliente){
            panelClientes.ventanaActualizarCliente();
        }
        else if(e.getSource() == panelVendedores.crearVendedor){
            panelVendedores.ventanaCrearNuevoVendedor();
        }else if(e.getSource() == panelVendedores.actualizarVendedor){
            panelVendedores.ventanaActualizarVendedor();
        }
        else if(e.getSource() == botonCerrarSesión){
            ordenarSucursal();
            escribirDatos(sucursales);
            ordenarProductos();
            escribirDatos(productos);
            JOptionPane.showMessageDialog(null,"Sesión Terminada", "Cerrar Sesión",JOptionPane.INFORMATION_MESSAGE);
            dispose();
            //VentanaLogin ventanaLogin = new VentanaLogin(vendedores);
            //ventanaLogin.setVisible(true);
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
                System.out.println("Sucursal eliminada con éxito");
                correrValores(sucursales,indiceBusquedaSucursal);
            }else{
                panelsucursal.modeloTabla.removeRow(filaSeleccionada);
                contadorActualSucursal--;
                System.out.println("Sucursal eliminada con éxito");
            }
        }else{
            System.out.println("No hay nada seleccionado");
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

       /* for(int i = 0; i < object.length; i++){
            if(object[i] != null){
                System.out.println(object[i].toString());
            }
        }*/

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
        }else{
            System.out.println("Ya no se pueden agregar más productos ");
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
            System.out.println("No hay nada seleccionado");
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
                System.out.println("Producto eliminado con éxito");
                correrValores(productos,indiceBusquedaProducto);
            }else{
                panelProductos.modeloTabla.removeRow(filaSeleccionada);
                contadorActualproductos--;
                System.out.println("Producto eliminado con éxito");
            }
        }else{
            System.out.println("No hay nada seleccionado");
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

        for(int i = 0; i < c.length; i++){
                System.out.println(c[i].toString());
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



}
