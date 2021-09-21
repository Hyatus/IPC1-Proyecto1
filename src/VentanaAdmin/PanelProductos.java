package VentanaAdmin;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class PanelProductos extends JPanel {
    //Componentes para el Panel
    public JButton crearProducto,cargaProducto,actualizarProducto,eliminarProducto,exportarProductos;
    public JTable tablaProductos;
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


    public PanelProductos(){
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
        ListSelectionListener oyenteTabla = e -> {
            if(e.getValueIsAdjusting()){
                return;
            }
        };
        tablaProductos.getSelectionModel().addListSelectionListener(oyenteTabla);
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










}