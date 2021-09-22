package VentanaVendedor;

import Clases.Clientes;
import Clases.Productos;
import Clases.Vendedores;
import Clases.Ventas;
import PlantillaPDF.PlantillaFactura;
import VentanaLogin.VentanaLogin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class VentanaVendedor extends JFrame implements ActionListener {


    public JButton botonCerrarSesiónV;
    public JTabbedPane grupoPanelesV;
    public panelNuevaVenta panelnuevaventa = new panelNuevaVenta();
    public panelVentas panelventas = new panelVentas();
    private int indiceVendedor;
    public JLabel mensajeBienvenida;
    public Vendedores[] vendedoresV = new Vendedores[400];
    public Clientes[] clientesV = new Clientes[100];
    public Productos[] productosV = new Productos[200];
    public int contadorClientesV = 0;
    public double totalFactura = 0;
    public long contadorFacturas;
    Date fecha = new Date();
    SimpleDateFormat formatoSimple = new SimpleDateFormat("dd/MM/yyyy");

    /******************************************************************************************************
     * COMPONENTES DEL JDIALOG PARA CREAR NUEVO CLIENTE Y ACTUALIZAR                                      *
     ******************************************************************************************************/
    public JDialog crearNuevoCliente;
    public JLabel encabezadoClienteD,codigoClienteD,NombreClienteD,NitCliente,CorreoCliente,GeneroCliente;
    public JTextField cajaCodigoClienteD,cajaNombreClienteD,cajaNitClienteD, cajaCorreoClienteD, cajaGeneroClienteD;
    public JButton botonAgregarC;

    public VentanaVendedor(int indiceLogin){
        this.setSize(790,670);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.indiceVendedor = indiceLogin;
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        cargarDatosVendedores();
        cargarDatosClientes();
        cargarDatosProductos();
        panelnuevaventa.FechaNV.setText(formatoSimple.format(fecha));
        String nombre = vendedoresV[indiceVendedor].getNombreVendedor();
        mensajeBienvenida = new JLabel("Bienvenido " + nombre);
        mensajeBienvenida.setBounds(400,10,200,40);
        mensajeBienvenida.setFont(new Font("Dialog",Font.BOLD,20));
        this.add(mensajeBienvenida);
        contadorFacturas = vendedoresV[indiceVendedor].getNumeroVentas()+1;
        String contadorFacturasS = String.valueOf(contadorFacturas);
        panelnuevaventa.NoNV.setText("No. " + contadorFacturasS);
        botonCerrarSesiónV = new JButton();
        botonCerrarSesiónV.setBounds(630,10,136,40);
        botonCerrarSesiónV.setText("Cerrar Sesión");
        botonCerrarSesiónV.setOpaque(true);
        botonCerrarSesiónV.setBackground(Color.RED);
        botonCerrarSesiónV.setForeground(Color.BLACK);
        botonCerrarSesiónV.setFont(new Font("Dialog",Font.BOLD,14));
        this.add(botonCerrarSesiónV);
        colocarTabbedPanel();
        colocarListeners();
    }

    private void colocarTabbedPanel() {
        grupoPanelesV = new JTabbedPane();
        grupoPanelesV.setBounds(15,70,746,544);
        grupoPanelesV.add("Nueva Venta",panelnuevaventa);
        grupoPanelesV.add("Ventas",panelventas);
        this.add(grupoPanelesV);
    }
    private  void colocarListeners(){
        panelnuevaventa.aplicarFiltroNV.addActionListener(this);
        panelnuevaventa.nuevoClienteNV.addActionListener(this);
        panelnuevaventa.agregarNV.addActionListener(this);
        panelnuevaventa.venderNV.addActionListener(this);
        panelventas.botonAplicarFiltroNVe.addActionListener(this);
        botonCerrarSesiónV.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == panelnuevaventa.aplicarFiltroNV){
            filtrarClientes();
        }else if(e.getSource() == panelnuevaventa.nuevoClienteNV){
            ventanaCrearNuevoCliente();
        }else if(e.getSource() == botonAgregarC){
            funcionbotonAgregarCliente();
        }else if(e.getSource() == panelnuevaventa.agregarNV){
            funcionAgregarProducto();
        }else if(e.getSource() == panelnuevaventa.venderNV){
              funcionVenderProducto();
        }else if(e.getSource() == panelventas.botonAplicarFiltroNVe){
              filtrarVentas();
        }else if(e.getSource() == botonCerrarSesiónV){
            escribirDatos(vendedoresV);
            escribirDatos(clientesV);
            VentanaLogin ventanaLogin = new VentanaLogin(vendedoresV);
            ventanaLogin.setVisible(true);
            this.dispose();
        }
    }

    public void filtrarClientes(){
        String nombre,nit,correo;
        char genero;
        int itemCont = panelnuevaventa.comboCliente.getItemCount();
        if(itemCont != 0){
            panelnuevaventa.comboCliente.removeAllItems();
        }
        if((!panelnuevaventa.cajaNombreNV.getText().isEmpty())
             && panelnuevaventa.cajaNITNV.getText().isEmpty()
             && panelnuevaventa.cajaCorreoNV.getText().isEmpty()
             && panelnuevaventa.cajaGeneroNV.getText().isEmpty()){
             nombre = panelnuevaventa.cajaNombreNV.getText().trim();
             for(int i = 0; i < clientesV.length;i++){
                 if(clientesV[i] != null){
                     if(clientesV[i].getNombreCliente().equals(nombre)){
                         panelnuevaventa.comboCliente.addItem(clientesV[i]);
                     }
                 }
             }
        }
        if(panelnuevaventa.cajaNombreNV.getText().isEmpty()
                && (!panelnuevaventa.cajaNITNV.getText().isEmpty())
                && panelnuevaventa.cajaCorreoNV.getText().isEmpty()
                && panelnuevaventa.cajaGeneroNV.getText().isEmpty()){
            nit = panelnuevaventa.cajaNITNV.getText().trim();
            for(int i = 0; i < clientesV.length;i++){
                if(clientesV[i] != null){
                    if(clientesV[i].getNitCliente().equals(nit)){
                        panelnuevaventa.comboCliente.addItem(clientesV[i]);
                    }
                }
            }
        }
        if((panelnuevaventa.cajaNombreNV.getText().isEmpty())
                && panelnuevaventa.cajaNITNV.getText().isEmpty()
                && (!panelnuevaventa.cajaCorreoNV.getText().isEmpty())
                && panelnuevaventa.cajaGeneroNV.getText().isEmpty()){
            correo = panelnuevaventa.cajaCorreoNV.getText().trim();
            for(int i = 0; i < clientesV.length;i++){
                if(clientesV[i] != null){
                    if(clientesV[i].getCorreoCliente().equals(correo)){
                        panelnuevaventa.comboCliente.addItem(clientesV[i]);
                    }
                }
            }
        }
        if((panelnuevaventa.cajaNombreNV.getText().isEmpty())
                && panelnuevaventa.cajaNITNV.getText().isEmpty()
                && panelnuevaventa.cajaCorreoNV.getText().isEmpty()
                && (!panelnuevaventa.cajaGeneroNV.getText().isEmpty())){
            genero = panelnuevaventa.cajaGeneroNV.getText().trim().toUpperCase().charAt(0);
            for(int i = 0; i < clientesV.length;i++){
                if(clientesV[i] != null){
                    if(clientesV[i].getGeneroCliente() == genero){
                        panelnuevaventa.comboCliente.addItem(clientesV[i]);
                    }
                }
            }
        }
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
            vendedoresV = ((Vendedores[]) leerDatosVendedores());
        }
    }

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
            clientesV = ((Clientes[]) leerDatosClientes());
            for(int i = 0; i < clientesV.length; i++){
                if(clientesV[i] != null){
                    contadorClientesV++;
                }
            }
        }
    }

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
            productosV = ((Productos[]) leerDatosProductos());
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
        if(buscarCliente(codigoCliente) == -1){
            if(contadorClientesV < 100){
                clientesV[contadorClientesV] = new Clientes(codigoCliente,nombreCliente,nitCliente,
                        correoCliente,generoCliente);
                panelnuevaventa.comboCliente.addItem(clientesV[contadorClientesV]);
                contadorClientesV++;
                cajaCodigoClienteD.setText("");
                cajaNombreClienteD.setText("");
                cajaNitClienteD.setText("");
                cajaCorreoClienteD.setText("");
                cajaGeneroClienteD.setText("");
            }else{
                JOptionPane.showMessageDialog(null,"Ya no se pueden agregar más clientes","Aviso",0);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Este Cliente ya existe","Aviso",0);
        }

    }
    public int buscarCliente(long codigoCliente){
        for(int i = 0; i < clientesV.length;i++){
            if(clientesV[i] != null){
                if(clientesV[i].getCodigoCliente() == codigoCliente){
                    return i;
                }
            }
        }
        return -1;
    }

    public void funcionAgregarProducto(){
        int indiceProducto;
        if(!panelnuevaventa.cajaCodigoNV.getText().isEmpty() &&
                         !panelnuevaventa.cajaCantidad.getText().isEmpty()){
            long codigo = Long.parseLong(panelnuevaventa.cajaCodigoNV.getText().trim());
            indiceProducto = buscarProducto(codigo);
            if(indiceProducto != -1){
                 String codigoT =  String.valueOf(productosV[indiceProducto].getCodigoProducto());
                 String nombreT = String.valueOf(productosV[indiceProducto].getNombreProducto());
                 String cantidadT = panelnuevaventa.cajaCantidad.getText().trim();
                 String precioT = String.valueOf(productosV[indiceProducto].getPrecioProducto());
                 double SubTotalT = Double.parseDouble(precioT)*Double.parseDouble(cantidadT);
                 String[] fila = {codigoT,nombreT,cantidadT,precioT,String.valueOf(SubTotalT)};
                 panelnuevaventa.modeloTabla.addRow(fila);
                 totalFactura += SubTotalT;
                 panelnuevaventa.SubTotalNV.setText(String.valueOf(totalFactura));
                 panelnuevaventa.cajaCodigoNV.setText("");
                 panelnuevaventa.cajaCantidad.setText("");
            }else{
                JOptionPane.showMessageDialog(null,"Producto no se encuentra","Aviso",0);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Rellene ambos campos","Aviso",0);
        }
    }
    public int buscarProducto(long codigo){
        for(int i = 0; i < productosV.length;i++){
            if(productosV[i] != null){
                if(productosV[i].getCodigoProducto() == codigo){
                    return i;
                }
            }
        }
        return -1;
    }

    public void funcionVenderProducto(){
        if(panelnuevaventa.comboCliente.getSelectedItem() != null){
             if(panelnuevaventa.modeloTabla.getRowCount() != 0){
                     Clientes clienteFac = (Clientes)panelnuevaventa.comboCliente.getSelectedItem();
                      long numFactura = contadorFacturas;
                     String nitFac = clienteFac.getNitCliente();
                     String nombreFac = clienteFac.getNombreCliente();
                      String fechaFac = formatoSimple.format(fecha);
                      Double totalFac = totalFactura;
                      //Empieza el arreglo en cero
                      int contadorVentasFac = vendedoresV[indiceVendedor].getContadorFacturasVend();
                      vendedoresV[indiceVendedor].ventas[contadorVentasFac] = new Ventas((int)numFactura,nitFac,nombreFac,fechaFac,totalFac);
                      //Al ingresar una nueva venta suma uno al contador
                       vendedoresV[indiceVendedor].setContadorFacturasVend(1);
                       //Se crea la factura
                      crearFactura((int)contadorFacturas,nitFac,nombreFac,fechaFac,totalFac,panelnuevaventa.modeloTabla);
                      //Le suma 1 a la cantidad de ventas, 1 + la cantidad de ventas que ya habían
                      contadorFacturas++;
                      panelnuevaventa.modeloTabla.setRowCount(0);
                      panelnuevaventa.NoNV.setText("No. " + contadorFacturas);
                      panelnuevaventa.SubTotalNV.setText("");
                      totalFactura = 0;
             }else{
                 JOptionPane.showMessageDialog(null,"No hay productos agregados","Aviso",0);
             }
        }else{
            JOptionPane.showMessageDialog(null,"No hay Cliente seleccionado","Aviso",0);
        }
    }

    public void crearFactura(int contadorFacturas, String nitFac, String nombreFac, String fechaFac, Double totalFac, DefaultTableModel modeloTabla){
        PlantillaFactura plantilla= new PlantillaFactura(new Date().toString(),"blue_logo.jpeg",contadorFacturas, nitFac,nombreFac,fechaFac,totalFac,modeloTabla);
        plantilla.crearPlantilla();
        try{
            File path = new File("Factura.pdf");
            Desktop.getDesktop().open(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filtrarVentas(){
        int noFactura;
        String nombre,nit,fechaFac;
        int itemCont = panelventas.modeloTabla.getRowCount();
        if(itemCont != 0){
             panelventas.modeloTabla.setRowCount(0);
        }
        if((!panelventas.cajaNoFacturaNVe.getText().isEmpty())
                && panelventas.cajaNITNVe.getText().isEmpty()
                && panelventas.cajaNombreNVe.getText().isEmpty()
                && panelventas.cajaFechaNVe.getText().isEmpty()){
            noFactura = Integer.parseInt(panelventas.cajaNoFacturaNVe.getText().trim());
            for(int i = 0; i < vendedoresV[indiceVendedor].ventas.length;i++){
                if(vendedoresV[indiceVendedor].ventas[i] != null){
                    if(vendedoresV[indiceVendedor].ventas[i].getNoFacturaVenta() == noFactura){
                        String[] fila = {String.valueOf(vendedoresV[indiceVendedor].ventas[i].getNoFacturaVenta()),
                                vendedoresV[indiceVendedor].ventas[i].getNitVenta(),
                                vendedoresV[indiceVendedor].ventas[i].getNombreClienteVenta(),
                                vendedoresV[indiceVendedor].ventas[i].getFechaVenta(),
                               String.valueOf(vendedoresV[indiceVendedor].ventas[i].getTotalVenta()),
                                "Visualizar"};
                       panelventas.modeloTabla.addRow(fila);
                    }
                }
            }
        }else if((panelventas.cajaNoFacturaNVe.getText().isEmpty())
                && !panelventas.cajaNITNVe.getText().isEmpty()
                && panelventas.cajaNombreNVe.getText().isEmpty()
                && panelventas.cajaFechaNVe.getText().isEmpty()) {
            nit = panelventas.cajaNITNVe.getText().trim();
            for (int i = 0; i < vendedoresV[indiceVendedor].ventas.length; i++) {
                if (vendedoresV[indiceVendedor].ventas[i] != null) {
                    if (vendedoresV[indiceVendedor].ventas[i].getNitVenta().equals(nit)) {
                        String[] fila = {String.valueOf(vendedoresV[indiceVendedor].ventas[i].getNoFacturaVenta()),
                                vendedoresV[indiceVendedor].ventas[i].getNitVenta(),
                                vendedoresV[indiceVendedor].ventas[i].getNombreClienteVenta(),
                                vendedoresV[indiceVendedor].ventas[i].getFechaVenta(),
                                String.valueOf(vendedoresV[indiceVendedor].ventas[i].getTotalVenta()),
                                "Visualizar"};
                        panelventas.modeloTabla.addRow(fila);
                    }
                }
            }
        }else if((panelventas.cajaNoFacturaNVe.getText().isEmpty())
                && panelventas.cajaNITNVe.getText().isEmpty()
                && !panelventas.cajaNombreNVe.getText().isEmpty()
                && panelventas.cajaFechaNVe.getText().isEmpty()){
            nombre = panelventas.cajaNombreNVe.getText().trim();
            for(int i = 0; i < vendedoresV[indiceVendedor].ventas.length;i++){
                if(vendedoresV[indiceVendedor].ventas[i] != null){
                    if(vendedoresV[indiceVendedor].ventas[i].getNombreClienteVenta().equals(nombre)){
                        String[] fila = {String.valueOf(vendedoresV[indiceVendedor].ventas[i].getNoFacturaVenta()),
                                vendedoresV[indiceVendedor].ventas[i].getNitVenta(),
                                vendedoresV[indiceVendedor].ventas[i].getNombreClienteVenta(),
                                vendedoresV[indiceVendedor].ventas[i].getFechaVenta(),
                                String.valueOf(vendedoresV[indiceVendedor].ventas[i].getTotalVenta()),
                                "Visualizar"};
                        panelventas.modeloTabla.addRow(fila);
                    }
                }
            }
       }else if((panelventas.cajaNoFacturaNVe.getText().isEmpty())
                && panelventas.cajaNITNVe.getText().isEmpty()
                && panelventas.cajaNombreNVe.getText().isEmpty()
                && !panelventas.cajaFechaNVe.getText().isEmpty()){
            fechaFac = panelventas.cajaFechaNVe.getText().trim();
            for(int i = 0; i < vendedoresV[indiceVendedor].ventas.length;i++){
                if(vendedoresV[indiceVendedor].ventas[i] != null){
                    if(vendedoresV[indiceVendedor].ventas[i].getFechaVenta().equals(fechaFac)){
                        String[] fila = {String.valueOf(vendedoresV[indiceVendedor].ventas[i].getNoFacturaVenta()),
                                vendedoresV[indiceVendedor].ventas[i].getNitVenta(),
                                vendedoresV[indiceVendedor].ventas[i].getNombreClienteVenta(),
                                vendedoresV[indiceVendedor].ventas[i].getFechaVenta(),
                                String.valueOf(vendedoresV[indiceVendedor].ventas[i].getTotalVenta()),
                                "Visualizar"};
                        panelventas.modeloTabla.addRow(fila);
                    }
                }
            }
        }
    }

    private void escribirDatos(Object object) {
        if(object.equals(clientesV)){
            serializarClientes(clientesV);
        }else if(object.equals(vendedoresV)){
            serializarVendedores(vendedoresV);
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
