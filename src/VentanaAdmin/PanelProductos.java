package VentanaAdmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelProductos extends JPanel {
    //Componentes para el Panel
    public JButton crearProducto,cargaProducto,actualizarProducto,eliminarProducto,exportarProductos;
    public JLabel graficaProducto;
    public JTable tablaProductos;
    private DefaultTableModel modeloTabla = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column) {
            if(column == 5){
                return true;
            }else{
                return false;
            }
        }
    };

    //Componentes para ventana emergente
    public JDialog crearNuevoProducto;
    public JLabel encabezadoProdD,codigoProdD,NombreProdD,DescripcionProd,CantidadProd,PrecioProd;
    public JTextField cajaCodigoProdD,cajaNombreProdD,cajaDescripcionD, cajaCantidadD, cajaPrecioD;
    public JButton botonAgregarP;

    public PanelProductos(){
        this.setLayout(null);
        this.setBackground(Color.gray);
        this.setBounds(0,0,745,587);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        agregarPanel();
        agregarBotones();
        agregarGrafica();
    }

    public void agregarPanel(){
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombres");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Precio");
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.getTableHeader().setReorderingAllowed(false);
        tablaProductos.getTableHeader().setResizingAllowed(false);
        tablaProductos.setBounds(10,38,431,497);
        this.add(tablaProductos);
        JScrollPane barra = new JScrollPane(tablaProductos,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        barra.setBounds(10,38,431,497);
        this.add(barra);
    }

    public void agregarBotones(){
        crearProducto = new JButton("Crear");
        crearProducto.setBounds(450,38,117,48);
        crearProducto.setFont(new Font("Dialog",Font.BOLD,18));
        this.add(crearProducto);

        cargaProducto = new JButton("Carga Masiva ");
        cargaProducto.setBounds(583,38,150,48);
        cargaProducto.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(cargaProducto);

        actualizarProducto = new JButton("Actualizar");
        actualizarProducto.setBounds(450,129,117,48);
        actualizarProducto.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(actualizarProducto);

        eliminarProducto = new JButton("Eliminar");
        eliminarProducto.setBounds(583,129,150,48);
        eliminarProducto.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(eliminarProducto);

        exportarProductos = new JButton("Exportar Listado a PDF");
        exportarProductos.setBounds(450,210,281,44);
        exportarProductos.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(exportarProductos);

    }

    public void agregarGrafica(){
        graficaProducto = new JLabel();
        graficaProducto.setBounds(450,274,281,253);
        graficaProducto.setOpaque(true);
        graficaProducto.setBackground(Color.BLUE);
        this.add(graficaProducto);
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

        ActionListener oyenteBoton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Agregar Producto");
            }
        };

        botonAgregarP.addActionListener(oyenteBoton);

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

        botonAgregarP = new JButton("Actualizar");
        botonAgregarP.setBounds(185,361,154,32);
        botonAgregarP.setFont(new Font("Dialog",Font.PLAIN,18));

        ActionListener oyenteBoton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Actualizar Producto");
            }
        };

        botonAgregarP.addActionListener(oyenteBoton);

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


}