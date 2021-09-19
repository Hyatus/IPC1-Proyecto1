package VentanaVendedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaVendedor extends JFrame implements ActionListener {
    public JButton botonCerrarSesiónV;
    public JTabbedPane grupoPanelesV;
    public panelNuevaVenta panelnuevaventa = new panelNuevaVenta();
    public panelVentas panelventas = new panelVentas();

    public VentanaVendedor(){
        this.setSize(790,670);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        iniciarComponentes();

    }

    private void iniciarComponentes() {
        botonCerrarSesiónV = new JButton();
        botonCerrarSesiónV.setBounds(630,10,136,40);
        botonCerrarSesiónV.setText("Cerrar Sesión");
        botonCerrarSesiónV.setOpaque(true);
        botonCerrarSesiónV.setBackground(Color.RED);
        botonCerrarSesiónV.setForeground(Color.BLACK);
        botonCerrarSesiónV.setFont(new Font("Dialog",Font.BOLD,14));
        this.add(botonCerrarSesiónV);
        colocarTabbedPanel();
    }

    private void colocarTabbedPanel() {
        grupoPanelesV = new JTabbedPane();
        grupoPanelesV.setBounds(15,70,746,544);
        grupoPanelesV.add("Nueva Venta",panelnuevaventa);
        grupoPanelesV.add("Ventas",panelventas);
        this.add(grupoPanelesV);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
