package PlantillaPDF;

import Clases.Clientes;
import Clases.Vendedores;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class PlantillaVendedores {

    String fecha;
    String rutaImagen;
    Vendedores[] vendedores;

    Document documento;
    FileOutputStream archivo;
    Paragraph titulo;

    public PlantillaVendedores(String fecha, String rutaImagen, Vendedores[] vendedores) {
        this.fecha = fecha;
        this.rutaImagen = rutaImagen;
        this.vendedores = vendedores;
        documento = new Document();
        titulo = new Paragraph("Reporte de Vendedores");
    }

    public void crearPlantilla(){
        try{
            archivo = new FileOutputStream("Reporte_Vendedores.pdf");
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
            PdfPCell caja = new PdfPCell(new Phrase("Caja"));
            caja.setBackgroundColor(BaseColor.WHITE);
            PdfPCell ventas = new PdfPCell(new Phrase("Ventas"));
            ventas.setBackgroundColor(BaseColor.WHITE);
            PdfPCell genero = new PdfPCell(new Phrase("Género"));
            genero.setBackgroundColor(BaseColor.WHITE);

            tablaProductos.addCell(codigo);
            tablaProductos.addCell(nombre);
            tablaProductos.addCell(caja);
            tablaProductos.addCell(ventas);
            tablaProductos.addCell(genero);

            for(Vendedores vendedor: this.vendedores){
                if(vendedor != null){
                    Character c = vendedor.getGeneroVendedor();
                    tablaProductos.addCell(String.valueOf(vendedor.getCodigoVendedor()));
                    tablaProductos.addCell(vendedor.getNombreVendedor());
                    tablaProductos.addCell(String.valueOf(vendedor.getCajaVendedor()));
                    tablaProductos.addCell(String.valueOf(vendedor.getNumeroVentas()));
                    tablaProductos.addCell(c.toString());
                }
            }
            documento.add(tablaProductos);
            documento.add(new Paragraph("Fecha: " + fecha));
            documento.close();
            JOptionPane.showMessageDialog(null,"Reporte vendedores creado con éxito");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
