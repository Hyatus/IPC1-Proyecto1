package VentanaVendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class panelNuevaVenta extends JPanel {

    public JButton aplicarFiltroNV,nuevoClienteNV,agregarNV,venderNV;
    public JLabel Fondo1NV, Fondo2NV, SeleccinarCliente,FiltradosPorNV,NombreNV,NITNV,CorreoNV,GeneroNV,FiltradosNV, ClienteNV,
            AgregarProductosNV,FechaNV,NoNV,CodigoNV,CantidadNV,TotalNV,SubTotalNV;
    public JTextField cajaNombreNV,cajaNITNV,cajaCorreoNV,cajaGeneroNV,cajaCodigoNV,cajaCantidad;
    public JComboBox comboCliente;
    public JTable tablaNuevaVenta;
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

    public panelNuevaVenta(){
        this.setLayout(null);
        this.setBackground(Color.gray);
        this.setBounds(0,0,746,544);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        colocarSeccionSeleccionarCliente();
        colocarSeccionAgregarProductos();
    }

    public void colocarSeccionSeleccionarCliente(){
        SeleccinarCliente = new JLabel("Seleccionar Cliente");
        SeleccinarCliente.setOpaque(true);
        SeleccinarCliente.setBounds(15,7,150,30);
        SeleccinarCliente.setFont(new Font("Dialog",Font.BOLD,14));
        SeleccinarCliente.setBorder(BorderFactory.createLineBorder(Color.BLACK,3,false));
        this.add(SeleccinarCliente);

        FiltradosPorNV = new JLabel("Filtrar por: ");
        FiltradosPorNV.setOpaque(true);
        FiltradosPorNV.setBackground(Color.WHITE);
        FiltradosPorNV.setBounds(20,42,95,26);
        FiltradosPorNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(FiltradosPorNV);

        NombreNV = new JLabel("Nombre ");
        NombreNV.setOpaque(true);
        NombreNV.setBackground(Color.WHITE);
        NombreNV.setBounds(120,45,63,22);
        NombreNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(NombreNV);

        cajaNombreNV = new JTextField();
        cajaNombreNV.setBounds(203,45,150,24);
        cajaNombreNV.setFont(new Font("Dialog",Font.PLAIN,14));
        this.add(cajaNombreNV);

        NITNV = new JLabel("NIT ");
        NITNV.setOpaque(true);
        NITNV.setBackground(Color.WHITE);
        NITNV.setBounds(383,45,63,22);
        NITNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(NITNV);

        cajaNITNV = new JTextField();
        cajaNITNV.setBounds(466,45,150,24);
        cajaNITNV.setFont(new Font("Dialog",Font.PLAIN,14));
        this.add(cajaNITNV);

        CorreoNV = new JLabel("Correo ");
        CorreoNV.setOpaque(true);
        CorreoNV.setBackground(Color.WHITE);
        CorreoNV.setBounds(120,97,63,22);
        CorreoNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(CorreoNV);

        cajaCorreoNV = new JTextField();
        cajaCorreoNV.setBounds(203,97,150,24);
        cajaCorreoNV.setFont(new Font("Dialog",Font.PLAIN,14));
        this.add(cajaCorreoNV);

        GeneroNV = new JLabel("Género ");
        GeneroNV.setOpaque(true);
        GeneroNV.setBackground(Color.WHITE);
        GeneroNV.setBounds(383,97,63,22);
        GeneroNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(GeneroNV);

        cajaGeneroNV = new JTextField();
        cajaGeneroNV.setBounds(466,97,150,24);
        cajaGeneroNV.setFont(new Font("Dialog",Font.PLAIN,14));
        this.add(cajaGeneroNV);

        aplicarFiltroNV = new JButton("Aplicar Filtro");
        aplicarFiltroNV.setBounds(203,141,413,30);
        aplicarFiltroNV.setFont(new Font("Dialog",Font.BOLD,14));
        this.add(aplicarFiltroNV);

        FiltradosNV = new JLabel("Filtrados: ");
        FiltradosNV.setBounds(120,184,70,23);
        FiltradosNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(FiltradosNV);

        ClienteNV = new JLabel("Cliente: ");
        ClienteNV.setBounds(210,184,60,24);
        ClienteNV.setFont(new Font("Dialog",Font.PLAIN,14));
        this.add(ClienteNV);

        agregarComboBoxClientes();
        agregarBotonNuevoCliente();

        Fondo1NV = new JLabel();
        Fondo1NV.setOpaque(true);
        Fondo1NV.setBounds(15,7,715,232);
        Fondo1NV.setBackground(Color.WHITE);
        Fondo1NV.setBorder(BorderFactory.createLineBorder(Color.BLACK,3,false));
        this.add(Fondo1NV);
    }

    public void agregarComboBoxClientes(){
        comboCliente = new JComboBox();
        comboCliente.setBounds(280,184,210,26);
        this.add(comboCliente);
        JScrollPane barra= new JScrollPane(comboCliente,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        barra.setBounds(280,184,210,30);
        this.add(barra);
    }

    public void agregarBotonNuevoCliente(){
        nuevoClienteNV = new JButton("Nuevo Cliente");
        nuevoClienteNV.setBounds(500,184,130,30);
        nuevoClienteNV.setFont(new Font("Dialog",Font.PLAIN,14));
        this.add(nuevoClienteNV);

    }

    public void colocarSeccionAgregarProductos(){
        AgregarProductosNV = new JLabel("Agregar Productos");
        AgregarProductosNV.setOpaque(true);
        AgregarProductosNV.setBounds(15,249,150,30);
        AgregarProductosNV.setFont(new Font("Dialog",Font.BOLD,14));
        AgregarProductosNV.setBorder(BorderFactory.createLineBorder(Color.BLACK,3,false));
        this.add(AgregarProductosNV);

        FechaNV = new JLabel("Fecha: 22/09/2021");
        FechaNV.setOpaque(true);
        FechaNV.setBounds(394,253,123,19);
        FechaNV.setBackground(Color.WHITE);
        FechaNV.setFont(new Font("Dialog",Font.BOLD,14));
        this.add(FechaNV);

        NoNV = new JLabel("No. 000");
        NoNV.setOpaque(true);
        NoNV.setBounds(527,253,123,19);
        NoNV.setBackground(Color.WHITE);
        NoNV.setFont(new Font("Dialog",Font.BOLD,14));
        this.add(NoNV);

        CodigoNV = new JLabel("Código");
        CodigoNV.setOpaque(true);
        CodigoNV.setBackground(Color.WHITE);
        CodigoNV.setBounds(120,310,63,22);
        CodigoNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(CodigoNV);

        cajaCodigoNV = new JTextField();
        cajaCodigoNV.setBounds(203,310,150,24);
        cajaCodigoNV.setFont(new Font("Dialog",Font.PLAIN,14));
        this.add(cajaCodigoNV);

        CantidadNV = new JLabel("Cantidad");
        CantidadNV.setOpaque(true);
        CantidadNV.setBackground(Color.WHITE);
        CantidadNV.setBounds(373,310,70,22);
        CantidadNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(CantidadNV);

        cajaCantidad = new JTextField();
        cajaCantidad.setBounds(463,310,150,24);
        cajaCantidad.setFont(new Font("Dialog",Font.PLAIN,14));
        this.add(cajaCantidad);

        agregarNV = new JButton();
        agregarNV = new JButton("Agregar");
        agregarNV.setBounds(623,310,95,24);
        agregarNV.setFont(new Font("Dialog",Font.PLAIN,14));
        this.add(agregarNV);

        agregarTabla();

        venderNV = new JButton();
        venderNV = new JButton("Vender");
        venderNV.setBounds(299,445,100,26);
        venderNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(venderNV);

        TotalNV = new JLabel("Total: ");
        TotalNV.setOpaque(true);
        TotalNV.setBackground(Color.WHITE);
        TotalNV.setBounds(461,445,70,22);
        TotalNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(TotalNV);

        SubTotalNV = new JLabel("400.00");
        SubTotalNV.setOpaque(true);
        SubTotalNV.setBackground(Color.WHITE);
        SubTotalNV.setBounds(541,445,70,22);
        SubTotalNV.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(SubTotalNV);

        Fondo2NV = new JLabel();
        Fondo2NV.setOpaque(true);
        Fondo2NV.setBounds(15,249,715,250);
        Fondo2NV.setBackground(Color.WHITE);
        Fondo2NV.setBorder(BorderFactory.createLineBorder(Color.BLACK,3,false));
        this.add(Fondo2NV);
    }

    public void agregarTabla(){
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Subtotal");

        tablaNuevaVenta = new JTable(modeloTabla);
        tablaNuevaVenta.getTableHeader().setReorderingAllowed(false);
        tablaNuevaVenta.getTableHeader().setResizingAllowed(false);
        tablaNuevaVenta.setBounds(23,344,702,91);
        this.add(tablaNuevaVenta);
        //Primero tabla luego la barra
        JScrollPane barra = new JScrollPane(tablaNuevaVenta,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        barra.setBounds(23,344,702,91);
        this.add(barra);
    }


}

