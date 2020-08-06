/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.iu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.WritableRaster;
import sm.image.EqualizationOp;
import sm.image.ImageTools;
import sm.image.LookupTableProducer;
import sm.image.TintOp;
import sm.tdp.graficos.imagen.MiOperacionPP;
import sm.tdp.graficos.imagen.MiSepiaOp;
import sm.tdp.graficos.imagen.MiOperacionCC;
import sm.tdp.graficos.imagen.MiUmbralizacionOp;

/**
 * Esta clase representa la zona de representación de imágenes.
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class LienzoImagen2D extends MiLienzo2D {

    private BufferedImage img;
    private int img_width;
    private int img_height;
    
    /**
     * Creates new form LienzoImagen2D
     */
    public LienzoImagen2D() {
        initComponents();
    }
    
    /**
     * Pinta el area de la imagen de fondo
     * @param g objeto de tipo Graphics
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;  
        g2d.drawImage(img,0,0,this);
        
        Stroke anterior = g2d.getStroke();
        pintar_borde(g2d);
        set_area_visible(new Rectangle(0, 0, img.getWidth(), img.getHeight()));
        g2d.draw(this.get_area_visible());
        g2d.setStroke(anterior);
    }

    /**
     * Establece la imagen
     * @param img imagen a establecer
     */
    public void setImg(BufferedImage img) {
        this.img = img;
        this.img_width = img.getWidth();
        this.img_height = img.getHeight();
        
        if(this.img != null) {
            setPreferredSize(new Dimension(this.img_width, this.img_height));
            this.set_tam_ancho(this.img_width);
            this.set_tam_alto(this.img_height);
        }
    }
    
    /**
     * Devuelve una imagen con un nuevo tamaño establecido por el usuario
     * @param img_src imagen fuente
     * @param img_dest  imagen destino
     * @return img_dest
     */
    public BufferedImage redimensionar_imagen(BufferedImage img_src, BufferedImage img_dest){
        
        Graphics2D g2d_dest = (Graphics2D) img_dest.createGraphics();
        g2d_dest.drawImage(img_src, 0, 0, img_dest.getWidth(), img_dest.getHeight(), null);

        return img_dest;
    }
    
    /**
     * Devuelve una imagen con las figuras dibujadas
     * @param drawVector si se vuelcan las figuras o no
     * @return img_dest
     */
    public BufferedImage getImagen(boolean drawVector){
        if (drawVector) {
            // Creo una imagen nueva con mismas dimensiones y tipo que la que tenía antes
            BufferedImage img_dibujos = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            Graphics2D g2d = img_dibujos.createGraphics();

            this.paint(g2d);
            
            return img_dibujos;
        }
        
        return null;
    }
    
    /**
     * Devuelve una imagen 
     * @return img
     */
    public BufferedImage getImagen(){
        return this.img;
    }
    
    /**
     * Realiza la operación seno en una imagen
     * @param w seno del ángulo
     * @return slt
     */
    public LookupTable seno(double w){
        double K = 255.0; // Cte de normalización
        byte lt[] = new byte[256];
        double pi_medios = (Math.PI / 2.0);
        
        lt[0] = 0;
        for (int l=1; l<256; l++){
            lt[l] = (byte) (K * Math.abs(Math.sin(Math.toRadians(w * l))));
        }
        
        ByteLookupTable slt = new ByteLookupTable(0, lt);
        
        return slt;
    }
    
    /**
     * Realiza la operación del giro de una imagen
     * @param g valor del grado
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage girarImagen(BufferedImage imgSource, int g){
        double r = Math.toRadians(g);
        Point c = new Point(imgSource.getWidth()/2, imgSource.getHeight()/2);
        AffineTransform at = AffineTransform.getRotateInstance(r,c.x,c.y);
        
        AffineTransformOp atop;
        atop = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);

        return atop.filter(imgSource, null);
    }
    
    /**
     * Realiza la operación del aumento de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage aumentarImagen(BufferedImage imgSource){
        AffineTransform at = AffineTransform.getScaleInstance(1.25,1.25);
        AffineTransformOp atop = new AffineTransformOp(at,
        AffineTransformOp.TYPE_BILINEAR);
        
        return atop.filter(imgSource, null);
    }
    
    /**
     * Realiza la operación de la disminución de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage disminuirImagen(BufferedImage imgSource){
        AffineTransform at = AffineTransform.getScaleInstance(0.75,0.75);
        AffineTransformOp atop = new AffineTransformOp(at,
        AffineTransformOp.TYPE_BILINEAR);
        
        return atop.filter(imgSource, null);
    }
    
    /**
     * Realiza la operación del negativo de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage negativo_op(BufferedImage imgSource){
        // Convierto la imagen a una con transparencia
        BufferedImage img_temp;
        img_temp = ImageTools.convertImageType(imgSource, BufferedImage.TYPE_INT_ARGB);

        byte f[] = new byte[256];
        for (int i=0; i<256; i++)
            f[i] = (byte)(255-i); // Negativo
        
        ByteLookupTable lt = new ByteLookupTable(0, f);
        
        LookupOp lop = new LookupOp(lt, null);
        
        return lop.filter(img_temp, null);
    }
    
    /**
     * Realiza la operación del seno de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage sinusoidal_op(BufferedImage imgSource){
        LookupTable lt;
        double valor = 188.0/ 255.0;
        lt = this.seno(valor);
        
        // Convierto la imagen a una con transparencia
        BufferedImage img_temp;
        img_temp = ImageTools.convertImageType(imgSource, BufferedImage.TYPE_INT_ARGB);

        LookupOp lop = new LookupOp(lt, null);
        return lop.filter(img_temp, null);
    }
    
    /**
     * Realiza la operación del oscurecimiento de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage oscurecer_imagen(BufferedImage imgSource){
        LookupTable lt;
        lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_POWER);
        
        // Convierto la imagen a una con transparencia
        BufferedImage img_temp;
        img_temp = ImageTools.convertImageType(imgSource, BufferedImage.TYPE_INT_ARGB);

        LookupOp lop = new LookupOp(lt, null);
        return lop.filter(img_temp, null);
    }
    
    /**
     * Realiza la operación de la iluminación de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage iluminar_imagen(BufferedImage imgSource){
        LookupTable lt;
        lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_LOGARITHM);
        
        // Convierto la imagen a una con transparencia
        BufferedImage img_temp;
        img_temp = ImageTools.convertImageType(imgSource, BufferedImage.TYPE_INT_ARGB);

        LookupOp lop = new LookupOp(lt, null);
        return lop.filter(img_temp, null);
    }
        
    /**
     * Realiza la operación del contraste de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage contraste(BufferedImage imgSource){
        int type = LookupTableProducer.TYPE_SFUNCION;
        LookupTable lt = LookupTableProducer.createLookupTable(type);
        LookupOp lop = new LookupOp(lt, null);
        
        // Imagen origen y destino iguales
        return lop.filter(imgSource , imgSource);
    }
    
    /**
     * Realiza la operación de extracción de bandas de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage extraer_banda(BufferedImage imgSource, int indice_banda){
        //Creamos el modelo de color de la nueva imagen basado en un espacio de color GRAY
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ComponentColorModel cm = new ComponentColorModel(cs, false, false,
        Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        
        //Creamos el nuevo raster a partir del raster de la imagen original
        int bandList[] = {indice_banda};
        WritableRaster bandRaster = (WritableRaster) imgSource.getRaster().createWritableChild(0,0,
            imgSource.getWidth(), imgSource.getHeight(), 0, 0, bandList);
        
        //Creamos una nueva imagen que contiene como raster el correspondiente a la banda
        return new BufferedImage(cm, bandRaster, false, null);
    }
    
    /**
     * Realiza la operación del tintado de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage tintar_imagen(BufferedImage imgSource){
        Color color_tintado = getColor_pincel();
        
        TintOp tintado = new TintOp(color_tintado, 0.5f);
        
        return tintado.filter(imgSource, imgSource);
    }
    
    /**
     * Realiza la operación de la ecualizción de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage ecualizar_imagen(BufferedImage imgSource){
        EqualizationOp ecualizacion = new EqualizationOp();
        
        return ecualizacion.filter(imgSource, imgSource);
    }
    
    /**
     * Realiza la operación del efecto sepia de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage sepia_op(BufferedImage imgSource){
        MiSepiaOp sepia_op = new MiSepiaOp();

        return sepia_op.filter(imgSource, imgSource);
    }
    
    /**
     * Realiza la operación de mi operación pixel a pixel de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage op_px_a_px(BufferedImage imgSource){
        MiOperacionPP op_px_px = new MiOperacionPP();

        return op_px_px.filter(imgSource, imgSource);
    }
    
    /**
     * Realiza la operación de mi operación componente a componente de una imagen
     * @param imgSource imagen fuente
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage op_com_a_com(BufferedImage imgSource){
        MiOperacionCC op_c_c = new MiOperacionCC();

        return op_c_c.filter(imgSource, imgSource);
    }
    
    /**
     * Realiza la operación de la umbralización de una imagen
     * @param imgSource imagen fuente
     * @param umbral el umbral
     * @return la imagen resultante de aplicar la operación
     */
    public BufferedImage umbralizacion(BufferedImage imgSource, int umbral){
        MiUmbralizacionOp umb_op = new MiUmbralizacionOp(umbral);
        
        System.out.println("umbral" + umbral);

        return umb_op.filter(imgSource, imgSource);
    }
    
    /**
     * Realiza la el dibujado del borde de la zona de dibujo
     * @param g2d objeto Graphics2D
     */
    public void pintar_borde(Graphics2D g2d){
        
        float patron_discontinuo[] = {5.0f, 15.0f};
        Stroke borde_imagen = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
                                        BasicStroke.JOIN_ROUND, 1.0f,
                                        patron_discontinuo, 0.0f);
        
        g2d.setStroke(borde_imagen);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
