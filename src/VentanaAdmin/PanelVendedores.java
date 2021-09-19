package VentanaAdmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelVendedores extends JPanel {

    public JButton crearVendedor,cargaVendedor,actualizarVendedor,eliminarVendedor,exportarVendedor;
    public JLabel graficaVendedor;
    public JTable tablaVendedor;
    public DefaultTableModel modeloTabla = new DefaultTableModel(){
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
    public JDialog crearNuevoVendedor;
    public JLabel encabezadoVendD,codigoVendD,NombreVendD,CajaD,VentasD,GeneroVendD;
    public JTextField cajaCodigoVendD,cajaNombreVendD,cajaCajaVendD, cajaVentasD, cajaGeneroVendD;
    public JButton botonAgregarV;

    public PanelVendedores(){
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
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Caja");
        modeloTabla.addColumn("Ventas");
        modeloTabla.addColumn("Genero");
        tablaVendedor = new JTable(modeloTabla);
        tablaVendedor.getTableHeader().setReorderingAllowed(false);
        tablaVendedor.getTableHeader().setResizingAllowed(false);
        tablaVendedor.setBounds(10,38,431,497);
        this.add(tablaVendedor);
        JScrollPane barra = new JScrollPane(tablaVendedor,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        barra.setBounds(10,38,431,497);
        this.add(barra);
    }

    public void agregarBotones(){
        crearVendedor = new JButton("Crear");
        crearVendedor.setBounds(450,38,117,48);
        crearVendedor.setFont(new Font("Dialog",Font.BOLD,18));
        this.add(crearVendedor);

        cargaVendedor = new JButton("Carga Masiva ");
        cargaVendedor.setBounds(583,38,150,48);
        cargaVendedor.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(cargaVendedor);

        actualizarVendedor = new JButton("Actualizar");
        actualizarVendedor.setBounds(450,129,117,48);
        actualizarVendedor.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(actualizarVendedor);

        eliminarVendedor = new JButton("Eliminar");
        eliminarVendedor.setBounds(583,129,150,48);
        eliminarVendedor.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(eliminarVendedor);

        exportarVendedor = new JButton("Exportar Listado a PDF");
        exportarVendedor.setBounds(450,210,281,44);
        exportarVendedor.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(exportarVendedor);

    }

    public void agregarGrafica(){
        graficaVendedor = new JLabel();
        graficaVendedor.setBounds(450,274,281,253);
        graficaVendedor.setOpaque(true);
        graficaVendedor.setBackground(Color.CYAN);
        this.add(graficaVendedor);
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

        botonAgregarV = new JButton("Agregar");
        botonAgregarV.setBounds(185,361,154,32);
        botonAgregarV.setFont(new Font("Dialog",Font.PLAIN,18));

        ActionListener oyenteBoton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Agregar Vendedor");
            }
        };

        botonAgregarV.addActionListener(oyenteBoton);

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
        crearNuevoVendedor.add(botonAgregarV);

        crearNuevoVendedor.setSize(510,479);
        crearNuevoVendedor.setLocationRelativeTo(null);
        crearNuevoVendedor.setVisible(true);

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

        botonAgregarV = new JButton("Actualizar");
        botonAgregarV.setBounds(185,361,154,32);
        botonAgregarV.setFont(new Font("Dialog",Font.PLAIN,18));

        ActionListener oyenteBoton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Actualizar Vendedor");
            }
        };

        botonAgregarV.addActionListener(oyenteBoton);

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
        crearNuevoVendedor.add(botonAgregarV);

        crearNuevoVendedor.setSize(510,479);
        crearNuevoVendedor.setLocationRelativeTo(null);
        crearNuevoVendedor.setVisible(true);

    }





}