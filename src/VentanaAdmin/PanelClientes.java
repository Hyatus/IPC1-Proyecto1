package VentanaAdmin;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
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



    public PanelClientes(){
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
        ListSelectionListener oyenteTabla = e -> {
            if(e.getValueIsAdjusting()){
               return;
            }
        };
        tablaCliente.getSelectionModel().addListSelectionListener(oyenteTabla);
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






}
