package VentanaVendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class panelVentas extends JPanel {

    public JLabel Fondo1NV, ListadoGeneral,FiltrarPorVe,NoFacturaNVe,NITNVe,NombreNVe,FechaNVe,FiltradosNVe;
    public JTextField cajaNoFacturaNVe,cajaNITNVe,cajaNombreNVe,cajaFechaNVe;
    public JButton botonAplicarFiltroNVe;
    public JTable tablaNVe;
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

    public panelVentas(){
        this.setLayout(null);
        this.setBackground(Color.gray);
        this.setBounds(0,0,746,544);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        ListadoGeneral = new JLabel("Listado General");
        ListadoGeneral.setOpaque(true);
        ListadoGeneral.setBounds(15,7,150,40);
        ListadoGeneral.setFont(new Font("Dialog",Font.BOLD,18));
        ListadoGeneral.setBorder(BorderFactory.createLineBorder(Color.BLACK,3,false));
        this.add(ListadoGeneral);

        FiltrarPorVe = new JLabel("Filtrar Por: ");
        FiltrarPorVe.setOpaque(true);
        FiltrarPorVe.setBackground(Color.WHITE);
        FiltrarPorVe.setBounds(60,67,95,16);
        FiltrarPorVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(FiltrarPorVe);

        NoFacturaNVe = new JLabel("No. Factura ");
        NoFacturaNVe.setOpaque(true);
        NoFacturaNVe.setBackground(Color.WHITE);
        NoFacturaNVe.setBounds(170,67,95,16);
        NoFacturaNVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(NoFacturaNVe);

        cajaNoFacturaNVe = new JTextField();
        cajaNoFacturaNVe.setBounds(275,67,150,24);
        cajaNoFacturaNVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(cajaNoFacturaNVe);

        NITNVe = new JLabel("NIT ");
        NITNVe.setOpaque(true);
        NITNVe.setBackground(Color.WHITE);
        NITNVe.setBounds(445,67,95,16);
        NITNVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(NITNVe);

        cajaNITNVe = new JTextField();
        cajaNITNVe.setBounds(500,67,150,24);
        cajaNITNVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(cajaNITNVe);

        NombreNVe = new JLabel("Nombre ");
        NombreNVe.setOpaque(true);
        NombreNVe.setBackground(Color.WHITE);
        NombreNVe.setBounds(170,111,95,16);
        NombreNVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(NombreNVe);

        cajaNombreNVe = new JTextField();
        cajaNombreNVe.setBounds(275,111,150,24);
        cajaNombreNVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(cajaNombreNVe);

        FechaNVe = new JLabel("Fecha ");
        FechaNVe.setOpaque(true);
        FechaNVe.setBackground(Color.WHITE);
        FechaNVe.setBounds(445,111,95,16);
        FechaNVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(FechaNVe);

        cajaFechaNVe = new JTextField();
        cajaFechaNVe.setBounds(500,111,150,24);
        cajaFechaNVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(cajaFechaNVe);

        botonAplicarFiltroNVe = new JButton("Aplicar Filtro");
        botonAplicarFiltroNVe.setBounds(170,155,480,24);
        botonAplicarFiltroNVe.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(botonAplicarFiltroNVe);

        FiltradosNVe = new JLabel("Filtrados: ");
        FiltradosNVe.setOpaque(true);
        FiltradosNVe.setBackground(Color.WHITE);
        FiltradosNVe.setBounds(60,200,95,16);
        FiltradosNVe.setFont(new Font("Dialog",Font.PLAIN,16));
        this.add(FiltradosNVe);

        agregarTablaVenta();

        Fondo1NV = new JLabel();
        Fondo1NV.setOpaque(true);
        Fondo1NV.setBounds(15,7,715,500);
        Fondo1NV.setBackground(Color.WHITE);
        Fondo1NV.setBorder(BorderFactory.createLineBorder(Color.BLACK,3,false));
        this.add(Fondo1NV);
    }

    public void agregarTablaVenta(){
        modeloTabla.addColumn("No. Factura");
        modeloTabla.addColumn("NIT");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Total");
        modeloTabla.addColumn("Acciones");

        tablaNVe = new JTable(modeloTabla);
        tablaNVe.getTableHeader().setReorderingAllowed(false);
        tablaNVe.getTableHeader().setResizingAllowed(false);
        tablaNVe.setBounds(60,236,600,200);
        this.add(tablaNVe);
        JScrollPane barra = new JScrollPane(tablaNVe,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        barra.setBounds(60,236,600,200);
        this.add(barra);

    }


}
