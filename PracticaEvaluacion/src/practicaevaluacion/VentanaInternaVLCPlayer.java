/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaevaluacion;


import java.awt.image.BufferedImage;
import java.io.File;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo VentanaInternaVLCPlayer
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class VentanaInternaVLCPlayer extends VentanaInterna {

    private EmbeddedMediaPlayer vlcplayer = null;
    private File fMedia;    
    
    /**
     * Creates new form VentanaInternaVLCPlayer
     */
    private VentanaInternaVLCPlayer(File f) {
        initComponents();
        //this.setPreferredSize(new Dimension(300,300));
        fMedia = f;
        EmbeddedMediaPlayerComponent aVisual = new EmbeddedMediaPlayerComponent();
        getContentPane().add(aVisual,java.awt.BorderLayout.CENTER);
        vlcplayer = aVisual.getMediaPlayer();
        this.tipo_ventana_escritorio = VENTANA_REPRODUCTOR;
    }
    /**
     * Creates new instance of form VentanaInternaVLCPlayer
     * @return  v VentanaInternaVLCPlayer
     */
    public static VentanaInternaVLCPlayer getInstance(File f){
        VentanaInternaVLCPlayer v = new VentanaInternaVLCPlayer(f);
        return (v.vlcplayer!=null?v:null);
    }
     /**
     * Ejecuta el reproductor.
     */
    
    public void play() {
        if (vlcplayer != null) {
            if(vlcplayer.isPlayable()){
                //Si se estaba reproduciendo
                vlcplayer.play();
            } else {
                vlcplayer.playMedia(fMedia.getAbsolutePath());
            }
        }
    }
         /**
     * Para la reproducción.
     */
    public void stop() {
        if (vlcplayer != null) {
            if (vlcplayer.isPlaying()) {
                vlcplayer.pause();
            } else {
                vlcplayer.stop();
            }
        }
    }
    
    public void addMediaPlayerEventListener (MediaPlayerEventListener ml) {
        if (vlcplayer != null) {
            vlcplayer.addMediaPlayerEventListener(ml);
        }
    }
    /**
     * Devuelve una instantánea
     * @return  BufferedImage Instantánea
     */
    
     public BufferedImage getImage(){
        return this.vlcplayer.getSnapshot();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        this.stop();
        this.vlcplayer = null;
    }//GEN-LAST:event_formInternalFrameClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
