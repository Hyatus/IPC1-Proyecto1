package VentanaAdmin;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
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



    public PanelVendedores(){
        this.setLayout(null);
        this.setBackground(Color.gray);
        this.setBounds(0,0,745,587);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        agregarPanel();
        agregarBotones();
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
        ListSelectionListener oyenteTabla = e -> {
            if(e.getValueIsAdjusting()){
                return;
            }
        };
        tablaVendedor.getSelectionModel().addListSelectionListener(oyenteTabla);
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










}