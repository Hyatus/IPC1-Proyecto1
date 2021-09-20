import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;

public class Graficas {

    JFrame graficas;

    public Graficas(){
        graficas = new JFrame();
        DefaultCategoryDataset datasetBarras = new DefaultCategoryDataset();
        //Llenando los datos
        datasetBarras.setValue(5,"Chocolate","Chocolate");
        datasetBarras.setValue(7,"Pizza","Pizza");
        JFreeChart chartBar = ChartFactory.createBarChart("Barras","Dulce Favorito","Cantidad",datasetBarras, PlotOrientation.VERTICAL,true,true,false);
        ChartPanel frame2 = new ChartPanel(chartBar);
        frame2.setBounds(0,0,500,500);
        //panel.add(frame2,BorderLayout.CENTER);

        DefaultPieDataset datasetPie = new DefaultPieDataset();
        datasetPie.setValue("Chocolate",new Integer(5));
        datasetPie.setValue("Pizza ",new Integer(7));
        datasetPie.setValue("Churros",new Integer(8));
        datasetPie.setValue("Mentas",new Integer(10));

        JFreeChart chartPie = ChartFactory.createPieChart("Gráfica de Pie",datasetPie,true,true,false);
        ChartPanel frame = new ChartPanel(chartPie);
        frame.setBounds(0,0,100,100);
        //panel.add(frame,BorderLayout.CENTER);


    }
}
