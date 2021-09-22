package PlantillaPDF;

import Clases.Productos;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class PlantillaFactura {

    String fecha;
    String rutaImagen;

    Document documento;
    FileOutputStream archivo;
    Paragraph titulo;
    long numFactura;
    String nitFac;
    String nombreFac;
    String fechaFac;
    Double totalFac;
    DefaultTableModel modeloTabla;

    public PlantillaFactura(String fecha, String rutaImagen, long numFactura, String nitFac, String nombreFac, String fechaFac, Double totalFac, DefaultTableModel modeloTabla) {
        this.fecha = fecha;
        this.rutaImagen = rutaImagen;
        this.numFactura = numFactura;
        this.nitFac = nitFac;
        this.nombreFac = nombreFac;
        this.fechaFac = fechaFac;
        this.totalFac = totalFac;
        documento = new Document();
        titulo = new Paragraph("Factura No." + numFactura);
        this.modeloTabla = modeloTabla;
    }

    public void crearPlantilla(){
        try{
            archivo = new FileOutputStream("Factura.pdf");
            PdfWriter.getInstance(documento,archivo);
            documento.open();
            titulo.setAlignment(1);

            Image image = null;
            try{
                image = Image.getInstance(rutaImagen);
                image.scaleAbsolute(100,100);
                image.setAbsolutePosition(415,740);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            documento.add(image);
            documento.add(titulo);
            documento.add(Chunk.NEWLINE);
            documento.add(Chunk.NEWLINE);
            documento.add(Chunk.NEWLINE);
            documento.add(Chunk.NEWLINE);

            PdfPTable tablaProductos = new PdfPTable(5);
            tablaProductos.setWidthPercentage(100);
            PdfPCell noFactura = new PdfPCell(new Phrase("No. Factura"));
            noFactura.setBackgroundColor(BaseColor.WHITE);
            PdfPCell nit = new PdfPCell(new Phrase("NIT"));
            nit.setBackgroundColor(BaseColor.WHITE);
            PdfPCell nombre = new PdfPCell(new Phrase("Nombre"));
            nombre.setBackgroundColor(BaseColor.WHITE);
            PdfPCell fecha = new PdfPCell(new Phrase("Fecha"));
            fecha.setBackgroundColor(BaseColor.WHITE);
            PdfPCell total = new PdfPCell(new Phrase("Total"));
            total.setBackgroundColor(BaseColor.WHITE);

            tablaProductos.addCell(noFactura);
            tablaProductos.addCell(nit);
            tablaProductos.addCell(nombre);
            tablaProductos.addCell(fecha);
            tablaProductos.addCell(total);

            tablaProductos.addCell(String.valueOf(numFactura));
            tablaProductos.addCell(nitFac);
            tablaProductos.addCell(nombreFac);
            tablaProductos.addCell(fechaFac);
            tablaProductos.addCell(String.valueOf(totalFac));


            PdfPTable tablaDetalle = new PdfPTable(5);
            tablaProductos.setWidthPercentage(100);
            PdfPCell codigoProd = new PdfPCell(new Phrase("Código"));
            noFactura.setBackgroundColor(BaseColor.WHITE);
            PdfPCell nombreProd = new PdfPCell(new Phrase("Nombre"));
            nit.setBackgroundColor(BaseColor.WHITE);
            PdfPCell cantidadProd = new PdfPCell(new Phrase("Cantidad"));
            nombre.setBackgroundColor(BaseColor.WHITE);
            PdfPCell Precio = new PdfPCell(new Phrase("Precio"));
            fecha.setBackgroundColor(BaseColor.WHITE);
            PdfPCell Subtotal = new PdfPCell(new Phrase("Subtotal"));
            total.setBackgroundColor(BaseColor.WHITE);

            tablaDetalle.addCell(codigoProd);
            tablaDetalle.addCell(nombreProd);
            tablaDetalle.addCell(cantidadProd);
            tablaDetalle.addCell(Precio);
            tablaDetalle.addCell(Subtotal);

            int numeroFilas = modeloTabla.getRowCount();
            if(numeroFilas != 0){
                for(int i  = 0; i < numeroFilas; i++){
                    tablaDetalle.addCell((String)modeloTabla.getValueAt(i,0));
                    tablaDetalle.addCell((String)modeloTabla.getValueAt(i,1));
                    tablaDetalle.addCell((String)modeloTabla.getValueAt(i,2));
                    tablaDetalle.addCell((String)modeloTabla.getValueAt(i,3));
                    tablaDetalle.addCell((String)modeloTabla.getValueAt(i,4));
                }
            }
            Paragraph texto1 = new Paragraph("Datos del Comprador: " );
            texto1.setAlignment(1);
            documento.add(texto1);
            documento.add(Chunk.NEWLINE);
            documento.add(Chunk.NEWLINE);
            documento.add(Chunk.NEWLINE);
            documento.add(tablaProductos);
            documento.add(Chunk.NEWLINE);
            documento.add(Chunk.NEWLINE);
            documento.add(Chunk.NEWLINE);
            Paragraph texto2 = new Paragraph("Detalle de la compra: ");
            texto2.setAlignment(1);
            texto2.setFont(FontFactory.getFont("Dialog",30));
            documento.add(texto2);
            documento.add(Chunk.NEWLINE);
            documento.add(tablaDetalle);
            documento.close();

            JOptionPane.showMessageDialog(null,"Venta realizada con éxito");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
