package VentanaAdmin;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelSucursales extends JPanel {

    public JButton crearSurcursal,cargaMasivaSucursal,actualizarSucursal,eliminarSucursal,exportarSucursal;
    public JLabel graficaSucursal;
    public JTable tablaSucursal;

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
    public JDialog crearNuevaSucursal;
    public JLabel encabezadoD,codigoD,NombreD,direccionD,correoD,telefonoD;
    public JTextField cajaCodigoD,cajaNombreD,cajaDireccionD, cajaCorreoD, cajaTelefonoD;
    public JButton botonAgregarS, botonActualizarS;

    public PanelSucursales(){
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
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Teléfono");
        tablaSucursal = new JTable(modeloTabla);
        tablaSucursal.getTableHeader().setReorderingAllowed(false);
        tablaSucursal.getTableHeader().setResizingAllowed(false);
        tablaSucursal.setBounds(10,38,431,497);
        this.add(tablaSucursal);
        JScrollPane barra = new JScrollPane(tablaSucursal,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        barra.setBounds(10,38,431,497);
        this.add(barra);
        ListSelectionListener oyenteTabla = e -> {
            if(e.getValueIsAdjusting()){
                //retorna verdadero si suceden eventos múltiples
                System.out.println("Fila seleccionada");
            }
        };
        tablaSucursal.getSelectionModel().addListSelectionListener(oyenteTabla);
    }

    public void agregarBotones(){
        crearSurcursal = new JButton("Crear");
        crearSurcursal.setBounds(450,38,117,48);
        crearSurcursal.setFont(new Font("Dialog",Font.BOLD,18));
        this.add(crearSurcursal);

        cargaMasivaSucursal = new JButton("Carga Masiva ");
        cargaMasivaSucursal.setBounds(583,38,150,48);
        cargaMasivaSucursal.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(cargaMasivaSucursal);

        actualizarSucursal = new JButton("Actualizar");
        actualizarSucursal.setBounds(450,129,117,48);
        actualizarSucursal.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(actualizarSucursal);

        eliminarSucursal = new JButton("Eliminar");
        eliminarSucursal.setBounds(583,129,150,48);
        eliminarSucursal.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(eliminarSucursal);

        exportarSucursal = new JButton("Exportar Listado a PDF");
        exportarSucursal.setBounds(450,210,281,44);
        exportarSucursal.setFont(new Font("Dialog",Font.BOLD,16));
        this.add(exportarSucursal);

    }



}
