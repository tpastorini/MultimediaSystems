/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos.imagen;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Esta clase representa mi operación propia componente a componente sobre imágenes.
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class MiOperacionCC extends BufferedImageOpAdapter{
    
    /**
     * Metodo constructor por defecto
     */
    public MiOperacionCC(){}
    
    /**
     * Devuelve una imagen con el filtro de mi opoeracion aplicado
     * @param src imagen fuente
     * @param dest imagen destino
     */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest){
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        
        // Componente a componente, porque cada cálculo de componente es
        // independiente de las demás
        for (int x = 0; x < srcRaster.getWidth(); x++) {
            for (int y = 0; y < srcRaster.getHeight(); y++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++){
                    int sample = srcRaster.getSample(x, y, band);
                    
                    if(band == 2)
                        sample = (int) (sample/3 + 255*Math.random());
                    
                    destRaster.setSample(x, y, band, sample);
                }
            }
        }
        
        return dest;
    }
}
