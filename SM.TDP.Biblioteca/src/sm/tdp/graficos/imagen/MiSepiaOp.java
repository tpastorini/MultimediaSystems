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
 * Esta clase representa la operación de sepia sobre imágenes.
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class MiSepiaOp extends BufferedImageOpAdapter{

    /**
     * Metodo constructor por defecto
     */
    public MiSepiaOp(){}
    
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
        
        // Operación pixel a pixel
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                
                int[] pixelComp = null;
                pixelComp = srcRaster.getPixel(x, y, pixelComp);
                
                //sepiaR = min(255, 0.393·R + 0.769·G + 0.189·B)
                //sepiaG = min(255, 0.349·R + 0.686·G + 0.168·B)
                //sepiaB = min(255, 0.272·R + 0.534·G + 0.131·B)
                
                pixelComp[0] = (int) Math.min( 255, 0.393*pixelComp[0] + 0.769*pixelComp[1] + 0.189*pixelComp[2] );
                pixelComp[1] = (int) Math.min( 255, 0.349*pixelComp[0] + 0.686*pixelComp[1] + 0.168*pixelComp[2] );
                pixelComp[2] = (int) Math.min( 255, 0.272*pixelComp[0] + 0.534*pixelComp[1] + 0.131*pixelComp[2] );
                
                destRaster.setPixel(x, y, pixelComp);
            }
        }
        
        return dest;
    }
}