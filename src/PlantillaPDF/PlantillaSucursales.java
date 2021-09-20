package PlantillaPDF;

import Clases.Sucursales;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class PlantillaSucursales {

    String fecha;
    String rutaImagen;
    Sucursales[] sucursales;

    Document documento;
    FileOutputStream archivo;
    Paragraph titulo;

    public PlantillaSucursales(String fecha, String rutaImagen, Sucursales[] sucursales) {
        this.fecha = fecha;
        this.rutaImagen = rutaImagen;
        this.sucursales = sucursales;
        documento = new Document();
        titulo = new Paragraph("Reporte de Sucursales");
    }


    public void crearPlantilla(){
        try{
            archivo = new FileOutputStream("Reporte_Sucursales.pdf");
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

            PdfPTable tablaSucursales = new PdfPTable(5);
            tablaSucursales.setWidthPercentage(100);
            PdfPCell codigo = new PdfPCell(new Phrase("Código"));
            codigo.setBackgroundColor(BaseColor.WHITE);
            PdfPCell nombre = new PdfPCell(new Phrase("Nombre"));
            nombre.setBackgroundColor(BaseColor.WHITE);
            PdfPCell direccion = new PdfPCell(new Phrase("Dirección"));
            direccion.setBackgroundColor(BaseColor.WHITE);
            PdfPCell correo = new PdfPCell(new Phrase("Correo"));
            correo.setBackgroundColor(BaseColor.WHITE);
            PdfPCell telefono = new PdfPCell(new Phrase("Teléfono"));
            telefono.setBackgroundColor(BaseColor.WHITE);

            tablaSucursales.addCell(codigo);
            tablaSucursales.addCell(nombre);
            tablaSucursales.addCell(direccion);
            tablaSucursales.addCell(correo);
            tablaSucursales.addCell(telefono);

            for(Sucursales sucursal: this.sucursales){
                if(sucursal != null){
                    tablaSucursales.addCell(String.valueOf(sucursal.getCodigoSucursal()));
                    tablaSucursales.addCell(sucursal.getNombreSucursal());
                    tablaSucursales.addCell(sucursal.getDireccionSucursal());
                    tablaSucursales.addCell(sucursal.getCorreoSucursal());
                    tablaSucursales.addCell(String.valueOf(sucursal.getTelefonoSucursal()));
                }
            }
            documento.add(tablaSucursales);
            documento.add(new Paragraph("Fecha: " + fecha));
            documento.close();
            JOptionPane.showMessageDialog(null,"Reporte Sucursales creado con éxito");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
