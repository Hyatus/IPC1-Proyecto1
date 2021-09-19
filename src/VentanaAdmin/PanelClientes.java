package VentanaAdmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelClientes extends JPanel {

    public JButton crearCliente,cargaCliente,actualizarCliente,eliminarCliente,exportarCliente;
    public JLabel graficaCliente;
    public JTable tablaCliente;
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
    public JDialog crearNuevoCliente;
    public JLabel encabezadoClienteD,codigoClienteD,NombreClienteD,NitCliente,CorreoCliente,GeneroCliente;
    public JTextField cajaCodigoClienteD,cajaNombreClienteD,cajaNitClienteD, cajaCorreoClienteD, cajaGeneroClienteD;
    public JButton botonAgregarC;

    public PanelClientes(){
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
        modeloTabla.addColumn("NIT");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Genero");
        tablaCliente = new JTable(modeloTabla);
        tablaCliente.getTableHeader().setReorderingAllowed(false);
        tablaCliente.getTableHeader().setResizingAllowed(false);
        tablaCliente.setBounds(10,38,431,497);
        this.add(tablaCliente);
        JScrollPane barra = new JScrollPane(tablaCliente,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        barra.setBounds(10,38,431,497);
        this.add(barra);
    }

    public void agregarBotones(){
        crearCliente = new JButton("Crear");
        crearCliente.setBounds(450,38,117,48);
        crearCliente.setFont(new Font("Dialog",Font.BOLD,18));
        this.add(crearCliente);

        cargaCliente = new JButton("Carga Masiva ");
        cargaCliente.setBounds(583,38,150,48);
        cargaCliente.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(cargaCliente);

        actualizarCliente = new JButton("Actualizar");
        actualizarCliente.setBounds(450,129,117,48);
        actualizarCliente.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(actualizarCliente);

        eliminarCliente = new JButton("Eliminar");
        eliminarCliente.setBounds(583,129,150,48);
        eliminarCliente.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(eliminarCliente);

        exportarCliente = new JButton("Exportar Listado a PDF");
        exportarCliente.setBounds(450,210,281,44);
        exportarCliente.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(exportarCliente);

    }

    public void agregarGrafica(){
        graficaCliente = new JLabel();
        graficaCliente.setBounds(450,274,281,253);
        graficaCliente.setOpaque(true);
        graficaCliente.setBackground(Color.RED);
        this.add(graficaCliente);
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

        ActionListener oyenteBoton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Agregar Cliente");
            }
        };

        botonAgregarC.addActionListener(oyenteBoton);

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

        botonAgregarC = new JButton("Actualizar");
        botonAgregarC.setBounds(185,361,154,32);
        botonAgregarC.setFont(new Font("Dialog",Font.PLAIN,18));

        ActionListener oyenteBoton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Actualizar");
            }
        };

        botonAgregarC.addActionListener(oyenteBoton);

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

}
