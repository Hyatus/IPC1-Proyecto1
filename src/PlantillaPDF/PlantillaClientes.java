package PlantillaPDF;

import Clases.Clientes;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class PlantillaClientes {

        String fecha;
        String rutaImagen;
        Clientes[] clientes;

        Document documento;
        FileOutputStream archivo;
        Paragraph titulo;

    public PlantillaClientes(String fecha, String rutaImagen, Clientes[] clientes) {
        this.fecha = fecha;
        this.rutaImagen = rutaImagen;
        this.clientes = clientes;
        documento = new Document();
        titulo = new Paragraph("Reporte de Clientes");
    }

        public void crearPlantilla(){
        try{
            archivo = new FileOutputStream("Reporte_Clientes.pdf");
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
            PdfPCell nit = new PdfPCell(new Phrase("NIT"));
            nit.setBackgroundColor(BaseColor.WHITE);
            PdfPCell correo = new PdfPCell(new Phrase("Correo"));
            correo.setBackgroundColor(BaseColor.WHITE);
            PdfPCell genero = new PdfPCell(new Phrase("Genero"));
            genero.setBackgroundColor(BaseColor.WHITE);

            tablaProductos.addCell(codigo);
            tablaProductos.addCell(nombre);
            tablaProductos.addCell(nit);
            tablaProductos.addCell(correo);
            tablaProductos.addCell(genero);

            for(Clientes cliente: this.clientes){
                if(cliente != null){
                    Character c = cliente.getGeneroCliente();
                    tablaProductos.addCell(String.valueOf(cliente.getCodigoCliente()));
                    tablaProductos.addCell(cliente.getNombreCliente());
                    tablaProductos.addCell(cliente.getNitCliente());
                    tablaProductos.addCell(cliente.getCorreoCliente());
                    tablaProductos.addCell(c.toString());
                }
            }
            documento.add(tablaProductos);
            documento.add(new Paragraph("Fecha: " + fecha));
            documento.close();
            JOptionPane.showMessageDialog(null,"Reporte clientes creado con éxito");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    }
