package PlantillaPDF;

import Clases.Productos;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class PlantillaProductos {

    String fecha;
    String rutaImagen;
    Productos[] productos;

    Document documento;
    FileOutputStream archivo;
    Paragraph titulo;

    public PlantillaProductos(String fecha, String rutaImagen, Productos[] productos) {
        this.fecha = fecha;
        this.rutaImagen = rutaImagen;
        this.productos = productos;
        documento = new Document();
        titulo = new Paragraph("Reporte de Productos");
    }


    public void crearPlantilla(){
        try{
            archivo = new FileOutputStream("Reporte_Productos.pdf");
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
            PdfPCell codigo = new PdfPCell(new Phrase("Código"));
            codigo.setBackgroundColor(BaseColor.WHITE);
            PdfPCell nombre = new PdfPCell(new Phrase("Nombre"));
            nombre.setBackgroundColor(BaseColor.WHITE);
            PdfPCell descripcion = new PdfPCell(new Phrase("Descripción"));
            descripcion.setBackgroundColor(BaseColor.WHITE);
            PdfPCell cantidad = new PdfPCell(new Phrase("Cantidad"));
            cantidad.setBackgroundColor(BaseColor.WHITE);
            PdfPCell precio = new PdfPCell(new Phrase("Precio"));
            precio.setBackgroundColor(BaseColor.WHITE);

            tablaProductos.addCell(codigo);
            tablaProductos.addCell(nombre);
            tablaProductos.addCell(descripcion);
            tablaProductos.addCell(cantidad);
            tablaProductos.addCell(precio);

            for(Productos producto: this.productos){
                if(producto != null){
                    tablaProductos.addCell(String.valueOf(producto.getCodigoProducto()));
                    tablaProductos.addCell(producto.getNombreProducto());
                    tablaProductos.addCell(producto.getDescripcionProducto());
                    tablaProductos.addCell(String.valueOf(producto.getCantidadProducto()));
                    tablaProductos.addCell(String.valueOf(producto.getPrecioProducto()));
                }
            }
            documento.add(tablaProductos);
            documento.add(new Paragraph("Fecha: " + fecha));
            documento.close();
            JOptionPane.showMessageDialog(null,"Reporte productos creado con éxito");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
