/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos.imagen;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Esta clase representa la operación de umbralización sobre imágenes.
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class MiUmbralizacionOp extends BufferedImageOpAdapter{
    
    private int umbral;
    
    /**
     * Metodo que crea una operación de umbralización
     * @param umbral el valor de umbral
     */
    public MiUmbralizacionOp(int umbral) {
        this.umbral = umbral;
    }
    
    /**
     * Devuelve una imagen con el filtro de mi opoeracion aplicado
     * @param src imagen fuente
     * @param dest imagen destino
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest){
        // Comprobamos que las imagenes no estén a nulo
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        
        // En caso de no tener una imagen destino, creo una compatible
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        
        // Paso la imagen a nivel de grises
        src = pasar_a_grises(src);
        dest = pasar_a_grises(dest);
        
        // Realizo la operación px a px
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        
        System.out.println("entra");
        
        for (int x = 0; x < srcRaster.getWidth(); x++) {
            for (int y = 0; y < srcRaster.getHeight(); y++) {
                int sample = srcRaster.getSample(x, y, 1);
                
                if(sample >= this.umbral)
                    sample = 255;
                else
                    sample = 0;
                
                destRaster.setSample(x, y, 0, sample);
            }
        }
        
        return dest;
        
    }
    
    /**
     * Devuelve una imagen en escala de grises
     * @param src imagen fuente
     */
    public BufferedImage pasar_a_grises(BufferedImage src){
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp cop = new ColorConvertOp(cs, null);
        return cop.filter(src, null);
    }

}
