/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaevaluacion;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo VentanaInterna
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */

public class VentanaInterna extends javax.swing.JInternalFrame {

    protected static int VENTANA_IMAGEN = 0;
    protected static int VENTANA_REPRODUCTOR = 1;
    protected static int VENTANA_CAMARA = 2;
    
    protected int tipo_ventana_escritorio; 
    
    /**
     * Constructor por defecto.
     */
    public VentanaInterna() {
        initComponents();
        this.setPreferredSize(new Dimension(300,300));
    }
    
    public int getTipoVentanaEscritorio(){
        return tipo_ventana_escritorio;
    }
     /**
     *  Métodos a sobrecargar en función de la ventana interna que estemos usando.
     */
    
    public BufferedImage getImage(){
        return null;
    }
    
    public Webcam getWebCam(){
        return null;
    }
    
    public EmbeddedMediaPlayer getReproductor(){
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
