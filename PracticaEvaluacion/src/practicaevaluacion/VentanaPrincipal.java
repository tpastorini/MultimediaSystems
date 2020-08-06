
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaevaluacion;


import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import sm.image.ImageTools;
import sm.image.KernelProducer;
import sm.image.LookupTableProducer;
import sm.sound.SMClipPlayer;
import sm.sound.SMSoundRecorder;
import sm.tdp.graficos.MiFigura2D;
import sm.tdp.iu.Formas;
import sm.tdp.iu.RenderCeldaColor;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo VentanaPrincipal
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class VentanaPrincipal extends javax.swing.JFrame implements ActionListener{
    
    private BufferedImage imgSource;
    private int num_copia;
    private SMClipPlayer player_clip;
    private SMSoundRecorder recorder;
    private File fichero_tmp_grabacion;
    private ManejadorCamara mc;
    private ArrayList<MiFigura2D> figuras_dibujadas;
    
    private double p1_x;
    private double p1_y;
    private double p2_x;
    private double p2_y;
    
    private PanelRelleno r;
    
    VentanaInternaImagenListener vi_img_listener = new VentanaInternaImagenListener();
    GestionEventosNuevaFigura evt_nueva_figura = new GestionEventosNuevaFigura();
    /**
    * Constructor por defecto.
    *
    */
   
    public VentanaPrincipal(){
        initComponents();
        this.setTitle("Paint 2D - Ev. Final");
        
        this.num_copia = 1;
        this.player_clip = null;
        this.recorder = null;
        this.string_forma_activa.setText("¡Dibuja una figura!");
        
        this.lista_colores.setRenderer(new RenderCeldaColor());  
        
        btn_play.setEnabled(false);
        btn_stop.setEnabled(false);
        capturar_imagen.setEnabled(false);
        
        this.nuevo.addActionListener(this);
        this.nuevo_archivo.addActionListener(this);
        this.nuevo.setActionCommand("n");
        this.nuevo_archivo.setActionCommand("n");
        
        this.abrir.addActionListener(this);
        this.abrir_archivo.addActionListener(this);
        this.abrir.setActionCommand("a");
        this.abrir_archivo.setActionCommand("a");
        
        this.guardar.addActionListener(this);
        this.guardar_archivo.addActionListener(this);
        this.guardar.setActionCommand("g");
        this.guardar_archivo.setActionCommand("g");
        
        this.abrir_camara.addActionListener(this);
        this.abrir_camara.setActionCommand("c");
        this.encender_cam.addActionListener(this);
        this.encender_cam.setActionCommand("c");
        
        this.btn_grabar.addActionListener(this);
        this.btn_grabar.setActionCommand("r");
        this.grabar_sonido.addActionListener(this);
        this.grabar_sonido.setActionCommand("r");
        
        this.lista_figuras.setSelectedIndex(-1);
        
        this.mc = new ManejadorCamara();
        this.figuras_dibujadas = new ArrayList<>();
        
        this.valor_x_p1.setEnabled(false);
        this.valor_y_p1.setEnabled(false);
    }
    /**
     * Establece las coordenadas del ratón
     * @param coords  Coordenadas
     */
        
    public void setCoordenadasRaton(String coords){
        this.coordenadas_raton.setText(coords);
    }
    /**
     * Devuelve la componente.
     * @return round_rect  Componente
     */
        
    public JToggleButton getBotonLapiz(){
        return this.round_rect;
    }
    /**
     * Devuelve la componente.
     * @return linea  Componente
     */
    
    public JToggleButton getBotonLinea(){
        return this.linea;
    }
     /**
     * Devuelve la componente.
     * @return rectangulo  Componente
     */
    
    public JToggleButton getBotonRectangulo(){
        return this.rectangulo;
    }
      /**
     * Devuelve la componente.
     * @return ovalo  Componente
     */
    
    public JToggleButton getBotonOvalo(){
        return this.ovalo;
    }
      /**
     * Devuelve la componente.
     * @return editar  Componente
     */
    
    public JToggleButton getBotonEdicion(){
        return this.editar;
    }
      /**
     * Devuelve la componente.
     * @return alisado  Componente
     */
    public JToggleButton getBotonAlisado(){
        return this.alisado;
    }
  /**
    * Metodo que obtiene un componente.
    * @return spinner_grosor
    */
    public JSpinner getSpinnerGrosor(){
        return this.spinner_grosor;
    }
    /**
    * Metodo que grabara un audio.
    *
    */
    public void grabar_audio(){
        fichero_tmp_grabacion = new File("fichero_tmp.au");
        
        if(fichero_tmp_grabacion != null){
            this.recorder = new SMSoundRecorder(fichero_tmp_grabacion);
            this.recorder.record();
            this.recorder.addLineListener(new ManejadorAudio());
        }
    }
     /**
    * Metodo que creara y abrirá un lienzo en el cual dibujar.
    *
    */
    public void nuevo(){
        // Abro el dialogo para que el usuario escoja el tamaño del lienzo
        EstablecerTamLienzo nuevo_tam = new EstablecerTamLienzo(this, true);
        nuevo_tam.setVisible(true);
        
        if(nuevo_tam.isCreacion_lienzo()){
            VentanaInternaImagen vi_img = VentanaInternaImagen.getInstance();
            
            vi_img.getLienzo().addMouseListener(evt_nueva_figura);

            BufferedImage img;
            img = new BufferedImage(nuevo_tam.getValorAncho(),nuevo_tam.getValorAlto(),BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d_img = img.createGraphics();
            float patron_discontinuo[] = {10.0f, 15.0f};
            g2d_img.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
                                            BasicStroke.JOIN_ROUND, 1.0f,
                                            patron_discontinuo, 0.0f));

            g2d_img.fillRect(0, 0, img.getWidth(), img.getHeight());

            
            vi_img.addInternalFrameListener(vi_img_listener);
            vi_img.getLienzo().setImg(img);
            escritorio.add(vi_img);

            vi_img.getLienzo().setFigura_seleccionada(Formas.ROUNDRECT);
            vi_img.setVisible(true);
            vi_img.setTitle("Nueva imagen");
        }
    }
    /**
    * Metodo que abrira las imágenes, audio o videos, de archivos externos.
    *
    */
    public void abrir(){
        JFileChooser dlg = new JFileChooser();
        
        aniadir_filtros_imagenes(dlg);
        aniadir_filtros_audio(dlg);
        aniadir_filtros_video(dlg);
        
        int resp = dlg.showOpenDialog(this);
                
        if( resp == JFileChooser.APPROVE_OPTION) {
            try {
                File f = dlg.getSelectedFile();
                //System.out.println("fichero escogido == " + f.getName());
                
                String nombre = f.getName();
                String ext = nombre.substring(nombre.lastIndexOf(".") + 1, nombre.length());
                
                // Se trata de un fichero de audio
                if( ext.equals("au") || ext.equals("wav") || ext.equals("mp3") ){
                    File fichero_audio = new File( f.getAbsolutePath() ){
                        @Override
                        public String toString(){
                            return this.getName();
                        }
                    };
                    lista_audios.addItem(fichero_audio);
                    btn_play.setEnabled(true);
                }
                else if( ext.equals("mp4") || ext.equals("mpg") || ext.equals("avi") ){
                    VentanaInternaVLCPlayer vi = VentanaInternaVLCPlayer.getInstance(f);
                    vi.addMediaPlayerEventListener(new VideoListener());
                    vi.setTitle(f.getName());
                    escritorio.add(vi);
                    vi.setVisible(true);
                    btn_play.setEnabled(true);
                }
                // Estamos ante una imagen
                else{
                    VentanaInternaImagen vi = VentanaInternaImagen.getInstance();
                    BufferedImage img = ImageIO.read(f);
                    vi.getLienzo().setImg(img);
                    vi.setTitle(f.getName());
                    escritorio.add(vi);
                    vi.setVisible(true);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error intentando abrir la imagen...", "Error", JOptionPane.WARNING_MESSAGE);
            }            
        }
    }
    /**
    * Metodo que guardar las imágenes y o lienzo con nuestras formas, en archivos.
    *
    */
    
    public void guardar(){
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if (vi != null){
            JFileChooser dlg = new JFileChooser();
            
            if(vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN)
                aniadir_filtros_imagenes(dlg);
            else
                aniadir_filtros_audio(dlg);
            
            int resp = dlg.showSaveDialog(this);
            if (resp == JFileChooser.APPROVE_OPTION) {
                try {
                    File f = dlg.getSelectedFile();
                    // Obtengo el nombre escrito por el usuario
                    String nombre = f.getName();

                    // Cojo la extensión extraída del nombre escrito por el usuario
                    String extension = nombre.substring(nombre.lastIndexOf(".") + 1, nombre.length());
                    
                    if(vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
                        VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
                        BufferedImage img = vi_img.getLienzo().getImagen(true);
                        
                        if(img != null){
                            // Si el usuario no ha escrito extension, o no es la aceptada, 
                            // o la imagen tiene alfa y no es png, 
                            // entonces por defecto pongo "jpg"
                            if( extension.equals(nombre) || !extension_aceptada_imagenes(extension)){
                                if(img.getColorModel().hasAlpha()){
                                    JOptionPane.showMessageDialog(null, "La imagen "
                                        + "tiene transparencia, se guardará automáticamente en .PNG", "Aviso al guardar la imagen", JOptionPane.INFORMATION_MESSAGE);
                                    extension = "png";
                                }
                                else{
                                    extension = "jpg";
                                }
                                
                                f = new File(f.getAbsoluteFile() + "." + extension);
                            }
                            else if( img.getColorModel().hasAlpha() && ("png".equals(extension)) ){
                                f = new File(f.getAbsoluteFile(),"");
                            }
                            else if( img.getColorModel().hasAlpha() && !("png".equals(extension)) ){
                                JOptionPane.showMessageDialog(null, "La imagen tiene"
                                        + " transparencia, se perderá información.",
                                        "Aviso al guardar la imagen", JOptionPane.INFORMATION_MESSAGE);

                                f = new File(f.getAbsoluteFile() + "");
                            }

                            ImageIO.write(img, extension, f);
                            JOptionPane.showMessageDialog(null, "Guardado con éxito.",
                                    "Guardar imagen", JOptionPane.INFORMATION_MESSAGE);
                            vi.setTitle(f.getName());
                        }
                    }
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error intentando guardar"
                            + " la imagen...", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    /**
    * Metodo que creará una instancia de la camara y la ejecutara.
    *
    */
    
    public void abrir_camara(){
        VentanaInternaCamara vi_cam = VentanaInternaCamara.getInstance();
        escritorio.add(vi_cam);
        vi_cam.setVisible(true);
        capturar_imagen.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        switch(e.getActionCommand()){
            case "n":
                nuevo();
                break;
            case "a":
                abrir();
                break;
            case "g":
                guardar();
                break;
            case "c":
                abrir_camara();
                break;
            case "r":
                grabar_audio();
                break;
        }
    }
        
    private class VideoListener extends MediaPlayerEventAdapter {
        
        public void playing(MediaPlayer mediaPlayer) {
            btn_stop.setEnabled(true);
            btn_play.setEnabled(false);
            capturar_imagen.setEnabled(true);
            //btn_play.setIcon(new ImageIcon(getClass().getResource("/iconos/Pausa.png")));
        }
        
        public void paused(MediaPlayer mediaPlayer) {
            btn_stop.setEnabled(false);
            btn_play.setEnabled(true);
            capturar_imagen.setEnabled(false);
            //btn_play.setIcon(new ImageIcon(getClass().getResource("/iconos/Play.png")));
        }
        
        public void finished(MediaPlayer mediaPlayer) {
            this.paused(mediaPlayer);
            //btn_play.setIcon(new ImageIcon(getClass().getResource("/iconos/Pausa.png")));
            capturar_imagen.setEnabled(false);
        }
    }
    
    // Para grabar
    private class ManejadorAudio implements LineListener {
        
        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.START) {
                btn_play.setEnabled(false);
                btn_stop.setEnabled(true);
            }
            
            if (event.getType() == LineEvent.Type.STOP) {
                btn_play.setEnabled(true);
                btn_stop.setEnabled(false);
                guardar_grabacion();
            }
            
            if (event.getType() == LineEvent.Type.CLOSE) {
                //btn_play.setEnabled(false);
                //btn_stop.setEnabled(false);
            }
        }
    }
        
    /**
    *  Clase que gestiona la captura de instantaneas con la deteccion del movimiento.
    
    */
    private class ManejadorCamara implements WebcamMotionListener{
        private WebcamMotionDetector detector;
        public ManejadorCamara(){}
        
        @Override
        public void motionDetected(WebcamMotionEvent wme) {
            VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
            
            if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_CAMARA){
                VentanaInternaCamara vi_cam = (VentanaInternaCamara) vi;

                VentanaInternaImagen vi_img = VentanaInternaImagen.getInstance();
                vi_img.getLienzo().setImg(vi_cam.getImage());
                vi_img.setTitle("¡Te pillé!");
                escritorio.add(vi_img);
                vi_img.setVisible(true);
                
            }
        }

        public WebcamMotionDetector getDetector() {
            return this.detector;
        }

        public void setDetector(Webcam cam) {
            this.detector = new WebcamMotionDetector(cam.getDefault());
            this.detector.addMotionListener(this);
        }
    }
    
    /**
    *  Clase que gestiona el evento de cambio de ventanas internas.
    *  Establecera los valores de los componentes de la ventana con los valores seleccionados 
    *  en cada lienzo cuando se active la ventana interna que lo contiene. 
    */
    private class VentanaInternaImagenListener implements InternalFrameListener{

        VentanaInternaImagen vi_img;
        ArrayList<MiFigura2D> figuras;
        
        @Override
        public void internalFrameOpened(InternalFrameEvent e) {}

        @Override
        public void internalFrameClosing(InternalFrameEvent e) {}

        @Override
        public void internalFrameClosed(InternalFrameEvent e) {}

        @Override
        public void internalFrameIconified(InternalFrameEvent e) {}

        @Override
        public void internalFrameDeiconified(InternalFrameEvent e) {}

        @Override
        public void internalFrameActivated(InternalFrameEvent e) {
            this.vi_img = (VentanaInternaImagen) e.getInternalFrame();
            
            // Activar opciones de dibujo
            activar_propiedades_lienzo_actual(vi_img);
            cargar_lista_lienzo(vi_img);
            
            valor_x_p1.setEnabled(false);
            valor_y_p1.setEnabled(false);
        }

        @Override
        public void internalFrameDeactivated(InternalFrameEvent e) {}
        
        
        public void cargar_lista_lienzo(VentanaInternaImagen vi_img){
            lista_figuras.setModel(new DefaultListModel<>());

            int tam_vector = vi_img.getLienzo().getFigurasDibujadas().size();
            if( tam_vector > 0 ){
                figuras = vi_img.getLienzo().getFigurasDibujadas();
                for(int i=0; i<tam_vector; i++)
                    ((DefaultListModel)lista_figuras.getModel()).addElement(figuras.get(i));
            }
        }
        
        public void activar_propiedades_lienzo_actual(VentanaInternaImagen vi_img){
            lista_colores.setSelectedItem(this.vi_img.getLienzo().getColor_pincel());
            set_forma_activa(this.vi_img.getLienzo().getFigura_seleccionada());
            grado_transparencia.setValue( (int) (this.vi_img.getLienzo().getGrado_transparencia() * 100.0f));
            alisado.setSelected(this.vi_img.getLienzo().getAlisado());
            spinner_grosor.setValue(this.vi_img.getLienzo().getTamGrosor());
        }
    
        public void set_forma_activa(Formas forma_activa){
            if(forma_activa == Formas.ROUNDRECT){
                round_rect.setSelected(true);
            }
            else if(forma_activa == Formas.LINEA){
                linea.setSelected(true);
            }
            if(forma_activa == Formas.RECTANGULO){
                rectangulo.setSelected(true);
            }
            else if(forma_activa == Formas.OVALO){
                ovalo.setSelected(true);
            }
        }
    }
    
    /**  
    * Para actualizar la lista de figuras cuando se crea una nueva.

    */
    private class GestionEventosNuevaFigura implements MouseListener{

        VentanaInternaImagen vi_img;
        ArrayList<MiFigura2D> figuras;
        
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {
            VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
            if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
                vi_img = (VentanaInternaImagen) vi;
                
                lista_figuras.setModel(new DefaultListModel<>());

                int tam_vector = vi_img.getLienzo().getFigurasDibujadas().size();
                if( tam_vector > 0 ){
                    figuras = vi_img.getLienzo().getFigurasDibujadas();
                    for(int i=0; i<tam_vector; i++)
                        ((DefaultListModel)lista_figuras.getModel()).addElement(figuras.get(i));
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
    /**
    * Metodo que guarda el audio grabado.
    *
    */
    public void guardar_grabacion(){
        JFileChooser dlg = new JFileChooser();
        
        int resp = dlg.showSaveDialog(this);
        
        if (resp == JFileChooser.APPROVE_OPTION) {
            File f_final = dlg.getSelectedFile();
            
            boolean renombrado = renombrar_fichero(f_final, fichero_tmp_grabacion);
            
            if(renombrado){
                JOptionPane.showMessageDialog(null, "Grabación guardada con éxito.");
                this.lista_audios.addItem(f_final);
            }else{
                JOptionPane.showMessageDialog(null, "No se ha podido guardar la grabación.");
            }
        }
        else if (resp == JFileChooser.CANCEL_OPTION) {
            fichero_tmp_grabacion.delete();
        }
    }
     /**
    * Metodo que renombra el fichero de audio grabado.
    * @param f_final
    * @param f_tmp
    * @return f_final
    */
    
    public boolean renombrar_fichero(File f_final, File f_tmp){
        return f_tmp.renameTo(f_final);
    }
     /**
    * Metodo que añade un filtros para formatos de imagenes.
    * @param dlg
    * 
    */
    
    public void aniadir_filtros_imagenes(JFileChooser dlg){
        Map<String, String> hash_map_extension = new HashMap<String, String>();

        for(int i=0; i<ImageIO.getReaderFormatNames().length; i++){
            hash_map_extension.put("Imágenes en ." + ImageIO.getReaderFormatNames()[i], ImageIO.getReaderFormatNames()[i]);
        }
        
        // Recorremos el Map con un Iterator
        Iterator it = hash_map_extension.keySet().iterator();
        
        while(it.hasNext()){
            String key = (String) it.next();
            dlg.setFileFilter(new FileNameExtensionFilter(key, hash_map_extension.get(key)));
        }
    }
    /**
    * Metodo que añade un filtros para formatos de audio.
    * @param dlg
    * 
    */
    public void aniadir_filtros_audio(JFileChooser dlg){
        Map<String, String> hash_map_extension = new HashMap<String, String>();

        hash_map_extension.put("Audios en .wav", "wav");
        hash_map_extension.put("Audios en .mp3", "mp3");
        hash_map_extension.put("Audios en .au", "au");
        
        // Recorremos el Map con un Iterator
        Iterator it = hash_map_extension.keySet().iterator();
        
        while(it.hasNext()){
            String key = (String) it.next();
            dlg.setFileFilter(new FileNameExtensionFilter(key, hash_map_extension.get(key)));
        }
    }
    /**
    * Metodo que añade un filtros para formatos de video.
    * @param dlg
    * 
    */
    public void aniadir_filtros_video(JFileChooser dlg){
        Map<String, String> hash_map_extension = new HashMap<String, String>();

        hash_map_extension.put("Videos en .mpg", "mpg");
        hash_map_extension.put("Videos en .mp4", "mp4");
        hash_map_extension.put("Videos en .avi", "avi");
        
        // Recorremos el Map con un Iterator
        Iterator it = hash_map_extension.keySet().iterator();
        
        while(it.hasNext()){
            String key = (String) it.next();
            dlg.setFileFilter(new FileNameExtensionFilter(key, hash_map_extension.get(key)));
        }
    }
    /**
    * Comprueba extensión.
    * @param extension
    * 
    */
    public Boolean extension_aceptada_imagenes(String extension){
        if( (!"bmp".equals(extension)) && (!"wbmp".equals(extension)) && 
             (!"gif".equals(extension)) && (!"jpg".equals(extension)) && 
             (!"jpeg".equals(extension)) && (!"png".equals(extension)) )
             
            return false;
        else
            return true;
    }
    /**
    * Comprueba extensión.
    * @param extension
    * 
    */
    public Boolean extension_aceptada_audios(String extension){
        if( (!"wav".equals(extension)) && (!"au".equals(extension)) && 
             (!"mp3".equals(extension)) )
             
            return false;
        else
            return true;
    }
    /**
    * Comprueba extensión.
    * @param extension
    * 
    */
    public Boolean extension_aceptada_videos(String extension){
        if( (!"mpg".equals(extension)) && (!"mp4".equals(extension)) && 
             (!"avi".equals(extension)) )
             
            return false;
        else
            return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        formas_dibujo = new javax.swing.ButtonGroup();
        colores = new javax.swing.ButtonGroup();
        jMenuItem1 = new javax.swing.JMenuItem();
        herramientas_superior = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        nuevo = new javax.swing.JButton();
        abrir = new javax.swing.JButton();
        guardar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        round_rect = new javax.swing.JToggleButton();
        linea = new javax.swing.JToggleButton();
        rectangulo = new javax.swing.JToggleButton();
        ovalo = new javax.swing.JToggleButton();
        editar = new javax.swing.JToggleButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        lista_colores = new javax.swing.JComboBox<>();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        spinner_grosor = new javax.swing.JSpinner();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        tipo_trazo = new javax.swing.JComboBox<>();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        relleno_figura = new javax.swing.JButton();
        alisado = new javax.swing.JToggleButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jPanel8 = new javax.swing.JPanel();
        btn_play = new javax.swing.JButton();
        btn_stop = new javax.swing.JButton();
        btn_grabar = new javax.swing.JButton();
        lista_audios = new javax.swing.JComboBox<>();
        encender_cam = new javax.swing.JButton();
        te_pille = new javax.swing.JToggleButton();
        capturar_imagen = new javax.swing.JButton();
        zona_dibujo = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        escritorio = new javax.swing.JDesktopPane();
        jPanel11 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_figuras = new javax.swing.JList<>();
        jPanel42 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        grado_transparencia = new javax.swing.JSlider();
        jPanel41 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        valor_x_p1 = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        valor_y_p1 = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        actualizar_p1 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        barra_atributos = new javax.swing.JPanel();
        conjunto_herramientas = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        brillo = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        slider_brillo = new javax.swing.JSlider();
        filtros_img = new javax.swing.JPanel();
        lista_filtros_img = new javax.swing.JComboBox<>();
        filtros_img1 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        contraste = new javax.swing.JButton();
        iluminar = new javax.swing.JButton();
        oscurecer = new javax.swing.JButton();
        filtros_img2 = new javax.swing.JPanel();
        sinusoidal = new javax.swing.JButton();
        sepia_op = new javax.swing.JButton();
        ecualizar_op = new javax.swing.JButton();
        tintado_op = new javax.swing.JButton();
        filtros_img3 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        bandas_img = new javax.swing.JButton();
        listado_espacios_color = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        slider_rotacion = new javax.swing.JSlider();
        jPanel7 = new javax.swing.JPanel();
        rotacion90 = new javax.swing.JButton();
        rotacion180 = new javax.swing.JButton();
        rotacion270 = new javax.swing.JButton();
        filtros_img4 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        disminuir = new javax.swing.JButton();
        aumentar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        slider_umbralizacion = new javax.swing.JSlider();
        jSeparator1 = new javax.swing.JSeparator();
        zona_info = new javax.swing.JPanel();
        coordenadas_raton = new javax.swing.JLabel();
        string_forma_activa = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        mi_menu = new javax.swing.JMenuBar();
        grabar_sonido = new javax.swing.JMenu();
        nuevo_archivo = new javax.swing.JMenuItem();
        abrir_archivo = new javax.swing.JMenuItem();
        guardar_archivo = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        abrir_audio = new javax.swing.JMenuItem();
        grabar_audio = new javax.swing.JMenuItem();
        abrir_camara = new javax.swing.JMenuItem();
        abrir_video = new javax.swing.JMenuItem();
        duplicar_ventana = new javax.swing.JMenuItem();
        edicion = new javax.swing.JMenu();
        ver_barra_estado = new javax.swing.JCheckBoxMenuItem();
        ver_barra_formas = new javax.swing.JCheckBoxMenuItem();
        ver_barra_atributos = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();
        redimensionar_lienzo = new javax.swing.JMenuItem();
        rescale_op_img = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        cop_propia_1 = new javax.swing.JMenuItem();
        cop_propia_2 = new javax.swing.JMenuItem();
        affineTransformOp = new javax.swing.JMenuItem();
        lookUpOp = new javax.swing.JMenuItem();
        bandCombineOp = new javax.swing.JMenuItem();
        colorConvertOp = new javax.swing.JMenuItem();
        negativo_op = new javax.swing.JMenuItem();
        op_px_px = new javax.swing.JMenuItem();
        op_com_com = new javax.swing.JMenuItem();
        mi_lookup_op = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PaintBásico");

        herramientas_superior.setRollover(true);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        nuevo.setToolTipText("Nuevo");
        nuevo.setBorderPainted(false);
        nuevo.setContentAreaFilled(false);
        nuevo.setDefaultCapable(false);
        nuevo.setFocusable(false);
        nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nuevo.setMaximumSize(new java.awt.Dimension(31, 31));
        nuevo.setMinimumSize(new java.awt.Dimension(31, 31));
        nuevo.setPreferredSize(new java.awt.Dimension(31, 31));
        nuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel2.add(nuevo);

        abrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir.png"))); // NOI18N
        abrir.setToolTipText("Abrir");
        abrir.setContentAreaFilled(false);
        abrir.setDefaultCapable(false);
        abrir.setMaximumSize(new java.awt.Dimension(31, 31));
        abrir.setMinimumSize(new java.awt.Dimension(31, 31));
        abrir.setPreferredSize(new java.awt.Dimension(31, 31));
        jPanel2.add(abrir);

        guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        guardar.setToolTipText("Guardar");
        guardar.setContentAreaFilled(false);
        guardar.setDefaultCapable(false);
        guardar.setMaximumSize(new java.awt.Dimension(31, 31));
        guardar.setMinimumSize(new java.awt.Dimension(31, 31));
        guardar.setPreferredSize(new java.awt.Dimension(31, 31));
        jPanel2.add(guardar);

        herramientas_superior.add(jPanel2);
        herramientas_superior.add(jSeparator2);

        formas_dibujo.add(round_rect);
        round_rect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/roundrect.png"))); // NOI18N
        round_rect.setToolTipText("Rectángulo redondeado");
        round_rect.setFocusable(false);
        round_rect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        round_rect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        round_rect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                round_rectActionPerformed(evt);
            }
        });
        herramientas_superior.add(round_rect);

        formas_dibujo.add(linea);
        linea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/linea.png"))); // NOI18N
        linea.setToolTipText("Línea");
        linea.setFocusable(false);
        linea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        linea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        linea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineaActionPerformed(evt);
            }
        });
        herramientas_superior.add(linea);

        formas_dibujo.add(rectangulo);
        rectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rectangulo.png"))); // NOI18N
        rectangulo.setToolTipText("Rectángulo");
        rectangulo.setFocusable(false);
        rectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectanguloActionPerformed(evt);
            }
        });
        herramientas_superior.add(rectangulo);

        formas_dibujo.add(ovalo);
        ovalo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/elipse.png"))); // NOI18N
        ovalo.setToolTipText("Elipse");
        ovalo.setFocusable(false);
        ovalo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ovalo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ovalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ovaloActionPerformed(evt);
            }
        });
        herramientas_superior.add(ovalo);

        formas_dibujo.add(editar);
        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/seleccion.png"))); // NOI18N
        editar.setToolTipText("Editar figura");
        editar.setFocusable(false);
        editar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });
        herramientas_superior.add(editar);
        herramientas_superior.add(jSeparator3);

        lista_colores.setModel(new javax.swing.DefaultComboBoxModel<>(new Color[] { Color.RED, Color.BLUE, Color.WHITE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.ORANGE, Color.PINK, Color.CYAN, Color.DARK_GRAY, Color.MAGENTA }));
        lista_colores.setToolTipText("Colores disponibles");
        lista_colores.setMinimumSize(new java.awt.Dimension(50, 32));
        lista_colores.setPreferredSize(new java.awt.Dimension(50, 32));
        lista_colores.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                lista_coloresItemStateChanged(evt);
            }
        });
        herramientas_superior.add(lista_colores);
        herramientas_superior.add(jSeparator4);

        spinner_grosor.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spinner_grosor.setToolTipText("Grosor de trazo");
        spinner_grosor.setMaximumSize(new java.awt.Dimension(50, 30));
        spinner_grosor.setMinimumSize(new java.awt.Dimension(50, 30));
        spinner_grosor.setPreferredSize(new java.awt.Dimension(50, 30));
        spinner_grosor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinner_grosorStateChanged(evt);
            }
        });
        herramientas_superior.add(spinner_grosor);
        herramientas_superior.add(jSeparator5);

        tipo_trazo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trazo contínuo", "Trazo punteado", "Trazo punto y raya" }));
        tipo_trazo.setToolTipText("Tipos de trazo");
        tipo_trazo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tipo_trazoItemStateChanged(evt);
            }
        });
        herramientas_superior.add(tipo_trazo);
        herramientas_superior.add(jSeparator10);

        relleno_figura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rellenar.png"))); // NOI18N
        relleno_figura.setFocusable(false);
        relleno_figura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        relleno_figura.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        relleno_figura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relleno_figuraActionPerformed(evt);
            }
        });
        herramientas_superior.add(relleno_figura);

        alisado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/alisar.png"))); // NOI18N
        alisado.setToolTipText("Alisado");
        alisado.setFocusable(false);
        alisado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        alisado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        alisado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alisadoActionPerformed(evt);
            }
        });
        herramientas_superior.add(alisado);
        herramientas_superior.add(jSeparator6);

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));
        herramientas_superior.add(jPanel8);

        btn_play.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Play.png"))); // NOI18N
        btn_play.setToolTipText("Play");
        btn_play.setFocusable(false);
        btn_play.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_play.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_play.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_playActionPerformed(evt);
            }
        });
        herramientas_superior.add(btn_play);

        btn_stop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Stop.png"))); // NOI18N
        btn_stop.setToolTipText("Stop");
        btn_stop.setFocusable(false);
        btn_stop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_stop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stopActionPerformed(evt);
            }
        });
        herramientas_superior.add(btn_stop);

        btn_grabar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/record24x24.png"))); // NOI18N
        btn_grabar.setToolTipText("Grabar audio");
        btn_grabar.setFocusable(false);
        btn_grabar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_grabar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        herramientas_superior.add(btn_grabar);

        lista_audios.setToolTipText("Lista de reproducción");
        lista_audios.setMaximumSize(new java.awt.Dimension(120, 32));
        lista_audios.setMinimumSize(new java.awt.Dimension(120, 32));
        lista_audios.setPreferredSize(new java.awt.Dimension(120, 32));
        herramientas_superior.add(lista_audios);

        encender_cam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Camara.png"))); // NOI18N
        encender_cam.setToolTipText("Webcam");
        encender_cam.setFocusable(false);
        encender_cam.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        encender_cam.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        herramientas_superior.add(encender_cam);

        te_pille.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/visor.png"))); // NOI18N
        te_pille.setToolTipText("Capturar movimiento cámara");
        te_pille.setFocusable(false);
        te_pille.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        te_pille.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        te_pille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                te_pilleActionPerformed(evt);
            }
        });
        herramientas_superior.add(te_pille);

        capturar_imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Capturar.png"))); // NOI18N
        capturar_imagen.setToolTipText("Captura la webbam");
        capturar_imagen.setFocusable(false);
        capturar_imagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        capturar_imagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        capturar_imagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capturar_imagenActionPerformed(evt);
            }
        });
        herramientas_superior.add(capturar_imagen);

        getContentPane().add(herramientas_superior, java.awt.BorderLayout.PAGE_START);

        zona_dibujo.setLayout(new java.awt.BorderLayout());

        jPanel10.setLayout(new java.awt.BorderLayout());
        zona_dibujo.add(jPanel10, java.awt.BorderLayout.LINE_END);

        escritorio.setToolTipText("Escritorio");
        escritorio.setMinimumSize(new java.awt.Dimension(800, 465));
        escritorio.setPreferredSize(new java.awt.Dimension(1400, 465));

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 524, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(escritorio);

        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel14.setLayout(new java.awt.BorderLayout());
        jPanel11.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        jPanel15.setLayout(new java.awt.BorderLayout());

        lista_figuras.setBackground(new java.awt.Color(240, 240, 240));
        lista_figuras.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lista_figuras.setModel(new DefaultListModel() {
            String[] strings = {""};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lista_figuras.setMaximumSize(new java.awt.Dimension(40, 150));
        lista_figuras.setMinimumSize(new java.awt.Dimension(40, 150));
        lista_figuras.setPreferredSize(new java.awt.Dimension(40, 150));
        lista_figuras.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lista_figurasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lista_figuras);

        jPanel15.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel42.setLayout(new java.awt.BorderLayout());

        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder("Opacidad"));
        jPanel40.setToolTipText("Transparencia de la figura");
        jPanel40.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel40.setMaximumSize(new java.awt.Dimension(60, 90));
        jPanel40.setMinimumSize(new java.awt.Dimension(60, 90));
        jPanel40.setPreferredSize(new java.awt.Dimension(60, 90));
        jPanel40.setLayout(new java.awt.BorderLayout());

        grado_transparencia.setMinorTickSpacing(10);
        grado_transparencia.setPaintLabels(true);
        grado_transparencia.setToolTipText("Grado de transparencia");
        grado_transparencia.setValue(100);
        grado_transparencia.setMaximumSize(new java.awt.Dimension(60, 26));
        grado_transparencia.setMinimumSize(new java.awt.Dimension(60, 26));
        grado_transparencia.setPreferredSize(new java.awt.Dimension(60, 26));
        grado_transparencia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                grado_transparenciaStateChanged(evt);
            }
        });
        jPanel40.add(grado_transparencia, java.awt.BorderLayout.PAGE_START);

        jPanel41.setLayout(new java.awt.BorderLayout());

        jPanel45.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("0%");
        jPanel45.add(jLabel4, java.awt.BorderLayout.LINE_START);

        jLabel6.setText("100%");
        jPanel45.add(jLabel6, java.awt.BorderLayout.LINE_END);

        jPanel41.add(jPanel45, java.awt.BorderLayout.CENTER);

        jPanel40.add(jPanel41, java.awt.BorderLayout.PAGE_END);

        jPanel42.add(jPanel40, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel42, java.awt.BorderLayout.PAGE_END);

        jPanel19.setLayout(new javax.swing.BoxLayout(jPanel19, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel24.setLayout(new java.awt.BorderLayout());

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Coordenadas Esquina Superior Izquierda");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel8.setMaximumSize(new java.awt.Dimension(265, 46));
        jLabel8.setMinimumSize(new java.awt.Dimension(265, 46));
        jLabel8.setPreferredSize(new java.awt.Dimension(265, 46));
        jPanel24.add(jLabel8, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel24);

        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel22.setLayout(new java.awt.BorderLayout());

        jPanel21.setMaximumSize(new java.awt.Dimension(392, 130));
        jPanel21.setMinimumSize(new java.awt.Dimension(392, 130));
        jPanel21.setPreferredSize(new java.awt.Dimension(392, 30));
        jPanel21.setLayout(new javax.swing.BoxLayout(jPanel21, javax.swing.BoxLayout.LINE_AXIS));

        jPanel32.setMaximumSize(new java.awt.Dimension(30, 100));
        jPanel32.setMinimumSize(new java.awt.Dimension(30, 100));
        jPanel32.setPreferredSize(new java.awt.Dimension(30, 100));
        jPanel32.setLayout(new java.awt.BorderLayout());
        jPanel21.add(jPanel32);

        jLabel3.setText("X1 = ");
        jPanel21.add(jLabel3);

        valor_x_p1.setToolTipText("Valor de la coord X");
        valor_x_p1.setMaximumSize(new java.awt.Dimension(60, 30));
        valor_x_p1.setMinimumSize(new java.awt.Dimension(60, 30));
        valor_x_p1.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel21.add(valor_x_p1);

        jPanel27.setMaximumSize(new java.awt.Dimension(30, 100));
        jPanel27.setMinimumSize(new java.awt.Dimension(30, 100));
        jPanel27.setPreferredSize(new java.awt.Dimension(30, 100));
        jPanel27.setLayout(new java.awt.BorderLayout());
        jPanel21.add(jPanel27);

        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setText("Y1 = ");
        jPanel23.add(jLabel5);

        valor_y_p1.setToolTipText("Valor de la coord Y");
        valor_y_p1.setMaximumSize(new java.awt.Dimension(60, 30));
        valor_y_p1.setMinimumSize(new java.awt.Dimension(60, 30));
        valor_y_p1.setPreferredSize(new java.awt.Dimension(60, 30));
        jPanel23.add(valor_y_p1);

        jPanel21.add(jPanel23);

        jPanel34.setMaximumSize(new java.awt.Dimension(30, 100));
        jPanel34.setMinimumSize(new java.awt.Dimension(30, 100));
        jPanel34.setPreferredSize(new java.awt.Dimension(30, 100));
        jPanel34.setLayout(new java.awt.BorderLayout());
        jPanel21.add(jPanel34);

        actualizar_p1.setText("Actualizar");
        actualizar_p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizar_p1ActionPerformed(evt);
            }
        });
        jPanel21.add(actualizar_p1);

        jPanel43.setMaximumSize(new java.awt.Dimension(30, 100));
        jPanel43.setMinimumSize(new java.awt.Dimension(30, 100));
        jPanel43.setPreferredSize(new java.awt.Dimension(30, 100));
        jPanel43.setLayout(new java.awt.BorderLayout());
        jPanel21.add(jPanel43);

        jPanel22.add(jPanel21, java.awt.BorderLayout.PAGE_END);

        jPanel20.add(jPanel22);

        jPanel19.add(jPanel20);

        jPanel37.setMaximumSize(new java.awt.Dimension(2147483647, 30));
        jPanel37.setMinimumSize(new java.awt.Dimension(0, 30));
        jPanel37.setPreferredSize(new java.awt.Dimension(0, 30));
        jPanel37.setLayout(new java.awt.BorderLayout());
        jPanel19.add(jPanel37);

        jPanel25.setLayout(new java.awt.BorderLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Coordenadas Esquina Inferior Derecha");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel7.setMaximumSize(new java.awt.Dimension(265, 46));
        jLabel7.setMinimumSize(new java.awt.Dimension(265, 46));
        jLabel7.setPreferredSize(new java.awt.Dimension(265, 46));
        jPanel25.add(jLabel7, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel25);

        jPanel26.setLayout(new java.awt.BorderLayout());
        jPanel19.add(jPanel26);

        jPanel39.setLayout(new java.awt.BorderLayout());

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Coordenadas Esquina Inferior Derecha");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel12.setMaximumSize(new java.awt.Dimension(265, 46));
        jLabel12.setMinimumSize(new java.awt.Dimension(265, 46));
        jLabel12.setPreferredSize(new java.awt.Dimension(265, 46));
        jPanel39.add(jLabel12, java.awt.BorderLayout.CENTER);
        jPanel39.add(jSeparator9, java.awt.BorderLayout.PAGE_START);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Figuras dibujadas");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel13.setMaximumSize(new java.awt.Dimension(265, 46));
        jLabel13.setMinimumSize(new java.awt.Dimension(265, 46));
        jLabel13.setPreferredSize(new java.awt.Dimension(265, 46));
        jPanel39.add(jLabel13, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel39);

        jPanel15.add(jPanel19, java.awt.BorderLayout.PAGE_START);

        jPanel28.setMaximumSize(new java.awt.Dimension(30, 2147483647));
        jPanel28.setMinimumSize(new java.awt.Dimension(30, 0));
        jPanel28.setPreferredSize(new java.awt.Dimension(30, 434));
        jPanel28.setLayout(new java.awt.BorderLayout());
        jPanel15.add(jPanel28, java.awt.BorderLayout.LINE_END);

        jPanel33.setMaximumSize(new java.awt.Dimension(30, 2147483647));
        jPanel33.setMinimumSize(new java.awt.Dimension(30, 0));
        jPanel33.setPreferredSize(new java.awt.Dimension(30, 434));
        jPanel33.setLayout(new java.awt.BorderLayout());
        jPanel15.add(jPanel33, java.awt.BorderLayout.LINE_START);

        jPanel11.add(jPanel15, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel11);

        zona_dibujo.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(zona_dibujo, java.awt.BorderLayout.CENTER);

        barra_atributos.setLayout(new java.awt.BorderLayout());

        conjunto_herramientas.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        brillo.setBorder(javax.swing.BorderFactory.createTitledBorder("Brillo"));
        brillo.setMaximumSize(new java.awt.Dimension(110, 100));
        brillo.setMinimumSize(new java.awt.Dimension(110, 100));
        brillo.setPreferredSize(new java.awt.Dimension(110, 100));
        brillo.setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.X_AXIS));
        brillo.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));
        brillo.add(jPanel13, java.awt.BorderLayout.PAGE_END);

        slider_brillo.setMaximum(255);
        slider_brillo.setMinimum(-255);
        slider_brillo.setPaintTicks(true);
        slider_brillo.setToolTipText("Brillo");
        slider_brillo.setValue(0);
        slider_brillo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_brilloStateChanged(evt);
            }
        });
        slider_brillo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                slider_brilloFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                slider_brilloFocusLost(evt);
            }
        });
        brillo.add(slider_brillo, java.awt.BorderLayout.CENTER);

        jPanel5.add(brillo);

        filtros_img.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros imagen"));
        filtros_img.setMaximumSize(new java.awt.Dimension(180, 100));
        filtros_img.setMinimumSize(new java.awt.Dimension(180, 100));
        filtros_img.setPreferredSize(new java.awt.Dimension(180, 100));
        filtros_img.setLayout(new javax.swing.BoxLayout(filtros_img, javax.swing.BoxLayout.LINE_AXIS));

        lista_filtros_img.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Media", "Media (5x5)", "Media (7x7)", "Binomial", "Enfoque", "Relieve", "Fronteras Laplaciano" }));
        lista_filtros_img.setToolTipText("Filtros imagen");
        lista_filtros_img.setMaximumSize(new java.awt.Dimension(140, 30));
        lista_filtros_img.setMinimumSize(new java.awt.Dimension(140, 30));
        lista_filtros_img.setPreferredSize(new java.awt.Dimension(140, 30));
        lista_filtros_img.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lista_filtros_imgActionPerformed(evt);
            }
        });
        filtros_img.add(lista_filtros_img);

        jPanel5.add(filtros_img);

        filtros_img1.setBorder(javax.swing.BorderFactory.createTitledBorder("Contraste"));
        filtros_img1.setMaximumSize(new java.awt.Dimension(180, 100));
        filtros_img1.setMinimumSize(new java.awt.Dimension(180, 100));
        filtros_img1.setPreferredSize(new java.awt.Dimension(180, 100));
        filtros_img1.setLayout(new java.awt.BorderLayout());

        jPanel16.setMaximumSize(new java.awt.Dimension(200, 20));
        jPanel16.setMinimumSize(new java.awt.Dimension(200, 22));
        jPanel16.setPreferredSize(new java.awt.Dimension(200, 22));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        contraste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/contraste.png"))); // NOI18N
        contraste.setToolTipText("Contraste");
        contraste.setMaximumSize(new java.awt.Dimension(49, 35));
        contraste.setMinimumSize(new java.awt.Dimension(49, 35));
        contraste.setPreferredSize(new java.awt.Dimension(49, 35));
        contraste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasteActionPerformed(evt);
            }
        });
        jPanel16.add(contraste);

        iluminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iluminar.png"))); // NOI18N
        iluminar.setToolTipText("Iluminar");
        iluminar.setMaximumSize(new java.awt.Dimension(49, 35));
        iluminar.setMinimumSize(new java.awt.Dimension(49, 35));
        iluminar.setPreferredSize(new java.awt.Dimension(49, 35));
        iluminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iluminarActionPerformed(evt);
            }
        });
        jPanel16.add(iluminar);

        oscurecer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/oscurecer.png"))); // NOI18N
        oscurecer.setToolTipText("Oscurecer");
        oscurecer.setMaximumSize(new java.awt.Dimension(49, 35));
        oscurecer.setMinimumSize(new java.awt.Dimension(49, 35));
        oscurecer.setPreferredSize(new java.awt.Dimension(49, 35));
        oscurecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oscurecerActionPerformed(evt);
            }
        });
        jPanel16.add(oscurecer);

        filtros_img1.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel5.add(filtros_img1);

        filtros_img2.setBorder(javax.swing.BorderFactory.createTitledBorder("Sinusoidal"));
        filtros_img2.setMaximumSize(new java.awt.Dimension(240, 100));
        filtros_img2.setMinimumSize(new java.awt.Dimension(240, 100));
        filtros_img2.setPreferredSize(new java.awt.Dimension(240, 100));
        filtros_img2.setLayout(new javax.swing.BoxLayout(filtros_img2, javax.swing.BoxLayout.LINE_AXIS));

        sinusoidal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sinusoidal.png"))); // NOI18N
        sinusoidal.setToolTipText("Sinusoidal");
        sinusoidal.setMaximumSize(new java.awt.Dimension(57, 33));
        sinusoidal.setMinimumSize(new java.awt.Dimension(57, 33));
        sinusoidal.setPreferredSize(new java.awt.Dimension(57, 33));
        sinusoidal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sinusoidalActionPerformed(evt);
            }
        });
        filtros_img2.add(sinusoidal);

        sepia_op.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sepia.png"))); // NOI18N
        sepia_op.setToolTipText("Efecto Sepia");
        sepia_op.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sepia_opActionPerformed(evt);
            }
        });
        filtros_img2.add(sepia_op);

        ecualizar_op.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ecualizar.png"))); // NOI18N
        ecualizar_op.setToolTipText("Ecualizar");
        ecualizar_op.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ecualizar_opActionPerformed(evt);
            }
        });
        filtros_img2.add(ecualizar_op);

        tintado_op.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/tintar.png"))); // NOI18N
        tintado_op.setToolTipText("Tintar");
        tintado_op.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tintado_opActionPerformed(evt);
            }
        });
        filtros_img2.add(tintado_op);

        jPanel5.add(filtros_img2);

        filtros_img3.setBorder(javax.swing.BorderFactory.createTitledBorder("Color"));
        filtros_img3.setMaximumSize(new java.awt.Dimension(160, 100));
        filtros_img3.setMinimumSize(new java.awt.Dimension(180, 100));
        filtros_img3.setPreferredSize(new java.awt.Dimension(180, 100));
        filtros_img3.setLayout(new java.awt.BorderLayout());

        jPanel17.setMaximumSize(new java.awt.Dimension(200, 20));
        jPanel17.setMinimumSize(new java.awt.Dimension(200, 22));
        jPanel17.setPreferredSize(new java.awt.Dimension(200, 22));
        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        bandas_img.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/bandas.png"))); // NOI18N
        bandas_img.setToolTipText("Extraer bandas");
        bandas_img.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bandas_imgActionPerformed(evt);
            }
        });
        jPanel17.add(bandas_img);

        listado_espacios_color.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RGB", "YCC", "GREY" }));
        listado_espacios_color.setToolTipText("Espacios de color");
        listado_espacios_color.setMaximumSize(new java.awt.Dimension(32767, 35));
        listado_espacios_color.setMinimumSize(new java.awt.Dimension(57, 42));
        listado_espacios_color.setPreferredSize(new java.awt.Dimension(57, 42));
        listado_espacios_color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listado_espacios_colorActionPerformed(evt);
            }
        });
        jPanel17.add(listado_espacios_color);

        filtros_img3.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPanel5.add(filtros_img3);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Rotación"));
        jPanel9.setToolTipText("");
        jPanel9.setMaximumSize(new java.awt.Dimension(331, 100));
        jPanel9.setMinimumSize(new java.awt.Dimension(331, 100));
        jPanel9.setPreferredSize(new java.awt.Dimension(331, 100));
        jPanel9.setLayout(new java.awt.BorderLayout());

        slider_rotacion.setMajorTickSpacing(90);
        slider_rotacion.setMaximum(270);
        slider_rotacion.setMinorTickSpacing(90);
        slider_rotacion.setPaintLabels(true);
        slider_rotacion.setPaintTicks(true);
        slider_rotacion.setToolTipText("Rotación");
        slider_rotacion.setValue(0);
        slider_rotacion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_rotacionStateChanged(evt);
            }
        });
        slider_rotacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                slider_rotacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                slider_rotacionFocusLost(evt);
            }
        });
        jPanel9.add(slider_rotacion, java.awt.BorderLayout.CENTER);

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        rotacion90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rotacion90.png"))); // NOI18N
        rotacion90.setToolTipText("Girar 90");
        rotacion90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotacion90ActionPerformed(evt);
            }
        });
        jPanel7.add(rotacion90);

        rotacion180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rotacion180.png"))); // NOI18N
        rotacion180.setToolTipText("Girar 180");
        rotacion180.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotacion180ActionPerformed(evt);
            }
        });
        jPanel7.add(rotacion180);

        rotacion270.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rotacion270.png"))); // NOI18N
        rotacion270.setToolTipText("Girar 270");
        rotacion270.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotacion270ActionPerformed(evt);
            }
        });
        jPanel7.add(rotacion270);

        jPanel9.add(jPanel7, java.awt.BorderLayout.LINE_END);

        jPanel5.add(jPanel9);

        filtros_img4.setBorder(javax.swing.BorderFactory.createTitledBorder("Escala"));
        filtros_img4.setMaximumSize(new java.awt.Dimension(150, 100));
        filtros_img4.setMinimumSize(new java.awt.Dimension(150, 100));
        filtros_img4.setPreferredSize(new java.awt.Dimension(150, 100));
        filtros_img4.setLayout(new java.awt.BorderLayout());

        jPanel18.setMaximumSize(new java.awt.Dimension(200, 20));
        jPanel18.setMinimumSize(new java.awt.Dimension(200, 22));
        jPanel18.setPreferredSize(new java.awt.Dimension(200, 22));
        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.LINE_AXIS));

        disminuir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/disminuir.png"))); // NOI18N
        disminuir.setToolTipText("Disminuir imagen");
        disminuir.setMaximumSize(new java.awt.Dimension(49, 35));
        disminuir.setMinimumSize(new java.awt.Dimension(49, 35));
        disminuir.setPreferredSize(new java.awt.Dimension(49, 35));
        disminuir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disminuirActionPerformed(evt);
            }
        });
        jPanel18.add(disminuir);

        aumentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aumentar.png"))); // NOI18N
        aumentar.setToolTipText("Aumentar imagen");
        aumentar.setMaximumSize(new java.awt.Dimension(49, 35));
        aumentar.setMinimumSize(new java.awt.Dimension(49, 35));
        aumentar.setPreferredSize(new java.awt.Dimension(49, 35));
        aumentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aumentarActionPerformed(evt);
            }
        });
        jPanel18.add(aumentar);

        filtros_img4.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel5.add(filtros_img4);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Umbralización"));
        jPanel3.setLayout(new java.awt.BorderLayout());

        slider_umbralizacion.setToolTipText("Umbralización");
        slider_umbralizacion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_umbralizacionStateChanged(evt);
            }
        });
        slider_umbralizacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                slider_umbralizacionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                slider_umbralizacionFocusLost(evt);
            }
        });
        jPanel3.add(slider_umbralizacion, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel3);

        conjunto_herramientas.add(jPanel5, java.awt.BorderLayout.PAGE_START);
        conjunto_herramientas.add(jSeparator1, java.awt.BorderLayout.CENTER);

        zona_info.setLayout(new java.awt.BorderLayout());

        coordenadas_raton.setText("(0,0)");
        zona_info.add(coordenadas_raton, java.awt.BorderLayout.LINE_END);

        string_forma_activa.setText("Forma activa");
        zona_info.add(string_forma_activa, java.awt.BorderLayout.LINE_START);

        jPanel1.setMaximumSize(new java.awt.Dimension(77, 100));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 30));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel6.setMaximumSize(new java.awt.Dimension(1100, 2147483647));
        jPanel6.setMinimumSize(new java.awt.Dimension(500, 20));
        jPanel6.setPreferredSize(new java.awt.Dimension(500, 20));
        jPanel6.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel6);

        jPanel4.setMaximumSize(new java.awt.Dimension(20, 2147483647));
        jPanel4.setMinimumSize(new java.awt.Dimension(20, 0));
        jPanel4.setPreferredSize(new java.awt.Dimension(20, 100));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel4);

        zona_info.add(jPanel1, java.awt.BorderLayout.CENTER);

        conjunto_herramientas.add(zona_info, java.awt.BorderLayout.PAGE_END);

        barra_atributos.add(conjunto_herramientas, java.awt.BorderLayout.CENTER);

        getContentPane().add(barra_atributos, java.awt.BorderLayout.PAGE_END);

        mi_menu.setMaximumSize(new java.awt.Dimension(300, 32769));

        grabar_sonido.setText("Archivo");

        nuevo_archivo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        nuevo_archivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        nuevo_archivo.setText("Nuevo archivo");
        grabar_sonido.add(nuevo_archivo);

        abrir_archivo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        abrir_archivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir.png"))); // NOI18N
        abrir_archivo.setText("Abrir archivo");
        grabar_sonido.add(abrir_archivo);

        guardar_archivo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        guardar_archivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        guardar_archivo.setText("Guardar archivo");
        grabar_sonido.add(guardar_archivo);
        grabar_sonido.add(jSeparator7);

        abrir_audio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        abrir_audio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/openAudio24x24.png"))); // NOI18N
        abrir_audio.setText("Abrir audio");
        abrir_audio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrir_audioActionPerformed(evt);
            }
        });
        grabar_sonido.add(abrir_audio);

        grabar_audio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        grabar_audio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/record24x24.png"))); // NOI18N
        grabar_audio.setText("Grabar audio");
        grabar_audio.setToolTipText("Grabar audio");
        grabar_sonido.add(grabar_audio);

        abrir_camara.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        abrir_camara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Camara.png"))); // NOI18N
        abrir_camara.setText("Abrir cámara");
        grabar_sonido.add(abrir_camara);

        abrir_video.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        abrir_video.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/AbrirVideo.png"))); // NOI18N
        abrir_video.setText("Abrir vídeo");
        abrir_video.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrir_videoActionPerformed(evt);
            }
        });
        grabar_sonido.add(abrir_video);

        duplicar_ventana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/duplicar.png"))); // NOI18N
        duplicar_ventana.setText("Duplicar Imagen");
        duplicar_ventana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                duplicar_ventanaActionPerformed(evt);
            }
        });
        grabar_sonido.add(duplicar_ventana);

        mi_menu.add(grabar_sonido);

        edicion.setText("Edición");

        ver_barra_estado.setSelected(true);
        ver_barra_estado.setText("Ver barra de estado");
        ver_barra_estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ver_barra_estadoActionPerformed(evt);
            }
        });
        edicion.add(ver_barra_estado);

        ver_barra_formas.setSelected(true);
        ver_barra_formas.setText("Ver barra de formas");
        ver_barra_formas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ver_barra_formasActionPerformed(evt);
            }
        });
        edicion.add(ver_barra_formas);

        ver_barra_atributos.setSelected(true);
        ver_barra_atributos.setText("Ver barra de atributos");
        ver_barra_atributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ver_barra_atributosActionPerformed(evt);
            }
        });
        edicion.add(ver_barra_atributos);

        mi_menu.add(edicion);

        jMenu2.setText("Imagen");

        redimensionar_lienzo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        redimensionar_lienzo.setText("Redimensionar Lienzo");
        redimensionar_lienzo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redimensionar_lienzoActionPerformed(evt);
            }
        });
        jMenu2.add(redimensionar_lienzo);

        rescale_op_img.setText("RescaleOp");
        rescale_op_img.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rescale_op_imgActionPerformed(evt);
            }
        });
        jMenu2.add(rescale_op_img);

        jMenu3.setText("ConvolveOp");

        cop_propia_1.setText("Convolución propia 1");
        cop_propia_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cop_propia_1ActionPerformed(evt);
            }
        });
        jMenu3.add(cop_propia_1);

        cop_propia_2.setText("Convolución propia 2");
        cop_propia_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cop_propia_2ActionPerformed(evt);
            }
        });
        jMenu3.add(cop_propia_2);

        jMenu2.add(jMenu3);

        affineTransformOp.setText("AffineTransformOp");
        affineTransformOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                affineTransformOpActionPerformed(evt);
            }
        });
        jMenu2.add(affineTransformOp);

        lookUpOp.setText("LookupOp");
        lookUpOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lookUpOpActionPerformed(evt);
            }
        });
        jMenu2.add(lookUpOp);

        bandCombineOp.setText("BandCombineOp");
        bandCombineOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bandCombineOpActionPerformed(evt);
            }
        });
        jMenu2.add(bandCombineOp);

        colorConvertOp.setText("ColorConvertOp");
        colorConvertOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorConvertOpActionPerformed(evt);
            }
        });
        jMenu2.add(colorConvertOp);

        negativo_op.setText("Imagen en negativo");
        negativo_op.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                negativo_opActionPerformed(evt);
            }
        });
        jMenu2.add(negativo_op);

        op_px_px.setText("Operación Pixel a Pixel");
        op_px_px.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                op_px_pxActionPerformed(evt);
            }
        });
        jMenu2.add(op_px_px);

        op_com_com.setText("Operación Componente a Componente");
        op_com_com.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                op_com_comActionPerformed(evt);
            }
        });
        jMenu2.add(op_com_com);

        mi_lookup_op.setText("Mi LookupOp");
        mi_lookup_op.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mi_lookup_opActionPerformed(evt);
            }
        });
        jMenu2.add(mi_lookup_op);

        mi_menu.add(jMenu2);

        setJMenuBar(mi_menu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void round_rectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_round_rectActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            vi_img.getLienzo().setFigura_seleccionada(Formas.ROUNDRECT);
            this.round_rect.setSelected(true);
            vi_img.getLienzo().setEdicion(false);
            this.string_forma_activa.setText("Rectángulo Redondeado");
            
            this.valor_x_p1.setEnabled(false);
            this.valor_y_p1.setEnabled(false);
            
            this.lista_figuras.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_round_rectActionPerformed

    private void lineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineaActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            vi_img.getLienzo().setFigura_seleccionada(Formas.LINEA);
            this.linea.setSelected(true);
            vi_img.getLienzo().setEdicion(false);
            this.string_forma_activa.setText("Línea");
            
            this.valor_x_p1.setEnabled(false);
            this.valor_y_p1.setEnabled(false);
            
            this.lista_figuras.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_lineaActionPerformed

    private void rectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectanguloActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            vi_img.getLienzo().setFigura_seleccionada(Formas.RECTANGULO);
            this.rectangulo.setSelected(true);
            vi_img.getLienzo().setEdicion(false);
            this.string_forma_activa.setText("Rectángulo");
            
            this.valor_x_p1.setEnabled(false);
            this.valor_y_p1.setEnabled(false);
            
            this.lista_figuras.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_rectanguloActionPerformed

    private void ovaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ovaloActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            vi_img.getLienzo().setFigura_seleccionada(Formas.OVALO);
            this.ovalo.setSelected(true);
            vi_img.getLienzo().setEdicion(false);
            this.string_forma_activa.setText("Elipse");
            
            this.valor_x_p1.setEnabled(false);
            this.valor_y_p1.setEnabled(false);
            
            this.lista_figuras.setSelectedIndex(-1);
        }
    }//GEN-LAST:event_ovaloActionPerformed

    private void ver_barra_estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ver_barra_estadoActionPerformed
        this.string_forma_activa.setVisible(this.ver_barra_estado.isSelected());
    }//GEN-LAST:event_ver_barra_estadoActionPerformed

    private void ver_barra_formasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ver_barra_formasActionPerformed
        this.herramientas_superior.setVisible(this.ver_barra_formas.isSelected());
    }//GEN-LAST:event_ver_barra_formasActionPerformed

    private void ver_barra_atributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ver_barra_atributosActionPerformed
        this.barra_atributos.setVisible(this.ver_barra_atributos.isSelected());
    }//GEN-LAST:event_ver_barra_atributosActionPerformed

    private void spinner_grosorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinner_grosorStateChanged
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            vi_img.getLienzo().setTamGrosor((Integer) spinner_grosor.getValue());
        }
    }//GEN-LAST:event_spinner_grosorStateChanged
    
    private void alisadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alisadoActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            vi_img.getLienzo().setAlisado(this.alisado.isSelected());
        }
    }//GEN-LAST:event_alisadoActionPerformed

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            vi_img.getLienzo().setEdicion(this.editar.isSelected());
        }
    }//GEN-LAST:event_editarActionPerformed

    // Para cambiar el brillo mientras deslizo el slider del brillo
    private void slider_brilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_brilloStateChanged
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            
            if(imgSource!= null){
                try{
                    RescaleOp rop;
                    float[] scaleFactors = {0.8f, 0.8f, 0.8f, 0.8f};
                    float[] offset = {slider_brillo.getValue(), slider_brillo.getValue(), slider_brillo.getValue(), 0.5f};

                    // Si la imagen tiene canal alfa
                    if(imgSource.getColorModel().hasAlpha()){
                        rop = new RescaleOp(scaleFactors, offset, null);
                    }
                    else{
                        rop = new RescaleOp(1.0f, slider_brillo.getValue(), null);
                    }

                    if(rop != null){
                        rop.filter(imgSource, vi_img.getLienzo().getImagen());
                        escritorio.repaint();
                    }

                } catch(IllegalArgumentException e){
                    JOptionPane.showMessageDialog(null, "Error: No se puede modificar el brillo.", "Modificación del brillo en la imagen", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_slider_brilloStateChanged

    private void lista_filtros_imgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lista_filtros_imgActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;

            try{
                ConvolveOp cop = null;
                BufferedImage img_entrada = vi_img.getLienzo().getImagen();
                BufferedImage img_salida;
                
                String filtro_img_escogido = lista_filtros_img.getSelectedItem().toString();
                
                switch(filtro_img_escogido){
                    case "Media":
                        cop = new ConvolveOp(new Kernel(3, 3, KernelProducer.MASCARA_MEDIA_3x3),ConvolveOp.EDGE_NO_OP,null);
                        break;
                    case "Media (5x5)":
                        float m_5x5[] = { 0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 
                              0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 
                              0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 
                              0.2f, 0.2f, 0.2f, 0.2f, 0.2f, 
                              0.2f, 0.2f, 0.2f, 0.2f, 0.2f } ;
                        Kernel k_5x5 = new Kernel(5,5,m_5x5);
                        cop = new ConvolveOp(k_5x5);
                        break;
                    case "Media (7x7)":
                        float m_7x7[] = { 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f,
                              0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 
                              0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f,
                              0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f,
                              0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f,
                              0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f,
                              0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f} ;
                        Kernel k_7x7 = new Kernel(7,7,m_7x7);
                        cop = new ConvolveOp(k_7x7);
                        break;
                    case "Binomial":
                        cop = new ConvolveOp(new Kernel(3, 3, KernelProducer.MASCARA_BINOMIAL_3x3),ConvolveOp.EDGE_NO_OP,null);
                        break;
                    case "Enfoque":
                        cop = new ConvolveOp(new Kernel(3, 3, KernelProducer.MASCARA_ENFOQUE_3x3),ConvolveOp.EDGE_NO_OP,null);
                        break;
                    case "Relieve":
                        cop = new ConvolveOp(new Kernel(3, 3, KernelProducer.MASCARA_RELIEVE_3x3),ConvolveOp.EDGE_NO_OP,null);
                        break;
                    case "Fronteras Laplaciano":
                        cop = new ConvolveOp(new Kernel(3, 3, KernelProducer.MASCARA_LAPLACIANA_3x3),ConvolveOp.EDGE_NO_OP,null);
                        break;
                }
                
                if(cop != null){
                    img_salida = cop.filter(img_entrada, null);
                    vi_img.getLienzo().setImg(img_salida);
                    vi_img.getLienzo().repaint();
                }
                                
            }catch(IllegalArgumentException e){
                JOptionPane.showMessageDialog(null, "Error: No se puede aplicar el filtro seleccionado.", "Aplicación de filtro en la imagen", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_lista_filtros_imgActionPerformed

    private void rescale_op_imgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rescale_op_imgActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            
            if(imgSource != null){
                try{
                    RescaleOp rop = new RescaleOp(1.0F, 100.0F, null);
                    rop.filter(imgSource, imgSource);
                    vi_img.getLienzo().repaint();
                } catch(IllegalArgumentException e){
                    JOptionPane.showMessageDialog(null, "Error: No se puede aplicar la operación RescaleOp.", "Operación RescaleOp", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_rescale_op_imgActionPerformed

    private void slider_brilloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slider_brilloFocusGained
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;

            ColorModel cm = vi_img.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi_img.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi_img.getLienzo().getImagen().isAlphaPremultiplied();
            imgSource = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_slider_brilloFocusGained

    private void slider_brilloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slider_brilloFocusLost
        imgSource = null;
        slider_brillo.setValue(0);
    }//GEN-LAST:event_slider_brilloFocusLost

    private void cop_propia_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cop_propia_1ActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            
            BufferedImage img_entrada = vi_img.getLienzo().getImagen();
            BufferedImage img_salida;
            
            try{
                ConvolveOp cop;
                float m[] = {0.3f, 0.0f, 0.3f, 0.0f, 0.3f, 0.0f, 0.3f, 0.0f, 0.3f};
                Kernel k = new Kernel(3,3,m);
                cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP,null);
                
                img_salida = cop.filter(img_entrada, null);
                vi_img.getLienzo().setImg(img_salida);
                vi_img.getLienzo().repaint();
                
            }catch(IllegalArgumentException e){
                JOptionPane.showMessageDialog(null, "Error: No se puede aplicar la convolución propia (1).", "Convolución propia 1", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_cop_propia_1ActionPerformed

    private void cop_propia_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cop_propia_2ActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            
            BufferedImage img_entrada = vi_img.getLienzo().getImagen();
            BufferedImage img_salida;
            
            try{
                ConvolveOp cop;
                float m[] = {-0.2f, 0.0f, -0.2f, 0.0f, -0.2f, 0.0f, -0.2f,
                            0.1f, 0.0f, 0.1f, 0.0f, 0.1f, 0.0f, 0.1f, 
                            0.3f, 0.0f, 0.3f, 0.0f, 0.3f, 0.0f, 0.3f, 
                            -0.2f, 0.0f, -0.2f, 0.0f, -0.2f, 0.0f, -0.2f, 
                            0.1f, 0.0f, 0.1f, 0.0f, 0.1f, 0.0f, 0.1f, 
                            0.3f, 0.0f, 0.3f, 0.0f, 0.3f, 0.0f, 0.3f, 
                            -0.2f, 0.0f, -0.2f, 0.0f, -0.2f, 0.0f, -0.2f};
                Kernel k = new Kernel(7,7,m);
                cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP,null);
                
                img_salida = cop.filter(img_entrada, null);
                vi_img.getLienzo().setImg(img_salida);
                vi_img.getLienzo().repaint();
                
            }catch(IllegalArgumentException e){
                JOptionPane.showMessageDialog(null, "Error: No se puede aplicar la convolución propia (2)", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_cop_propia_2ActionPerformed

    private void affineTransformOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_affineTransformOpActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            AffineTransform at = AffineTransform.getScaleInstance(1.25,1.25);
            try{
                imgSource = vi_img.getLienzo().getImagen();
                AffineTransformOp atop = new AffineTransformOp(at,
                AffineTransformOp.TYPE_BILINEAR);
                BufferedImage imgdest = atop.filter(imgSource, null);
                
                if(imgdest != null){
                    vi_img.getLienzo().setImg(imgdest);
                    vi_img.getLienzo().repaint();
                }
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se ha podido aplicarla el escalado a la imagen.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_affineTransformOpActionPerformed

    private void lookUpOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lookUpOpActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            LookupTable lt;
            lt = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_SFUNCION);
            try{
                // Convierto la imagen a una con transparencia
                BufferedImage img = ImageTools.convertImageType(vi_img.getLienzo().getImagen(), BufferedImage.TYPE_INT_ARGB);
                
                LookupOp lop = new LookupOp(lt, null);
                BufferedImage imgdest = lop.filter(img, null);
                
                if(imgdest != null){
                    vi_img.getLienzo().setImg(imgdest);
                    vi_img.getLienzo().repaint();
                }
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se ha podido aplicar la función S.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_lookUpOpActionPerformed

    // Botón del Menu->Imagen->BandCombineOp
    private void bandCombineOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bandCombineOpActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            
            if(imgSource != null){
                try{
                    // Suponiendo que estamos en RGB
                    for(int i=0; i<imgSource.getRaster().getNumBands(); i++){
                        BufferedImage banda_i = vi_img.getLienzo().extraer_banda(imgSource, i);
                        VentanaInternaImagen vi_banda_i = VentanaInternaImagen.getInstance();
                        vi_banda_i.setTitle(vi.getTitle() + ", [Banda " + i + "]");
                        vi_banda_i.getLienzo().setImg(banda_i);
                        escritorio.add(vi_banda_i);
                        vi_banda_i.setVisible(true);
                    }                    
                } catch(IllegalArgumentException e){
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_bandCombineOpActionPerformed

    // Botón del Menu->Imagen->ColorConvertOp
    private void colorConvertOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorConvertOpActionPerformed
         VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            try{
                String espacio_color = listado_espacios_color.getSelectedItem().toString();
                BufferedImage img_fuente = vi_img.getLienzo().getImagen();
                BufferedImage img_salida = null;
                VentanaInternaImagen vi_img_nueva = null;
                
                switch(espacio_color){
                    case "RGB":
                        // Si la imagen ya está en RGB, no la paso.
                        if (img_fuente.getColorModel().getColorSpace().isCS_sRGB()) {
                            JOptionPane.showMessageDialog(null, "La imagen ya está en RGB, no se va a convertir.", "Conversión a RGB", JOptionPane.INFORMATION_MESSAGE);
                        }
                        // La imagen está en YCC, la paso a RGB
                        else if(!img_fuente.getColorModel().getColorSpace().isCS_sRGB() && img_fuente.getData().getNumBands() > 1){
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                            ColorConvertOp cop = new ColorConvertOp(cs, null);
                            img_salida = cop.filter(img_fuente, null);
                            vi_img_nueva = VentanaInternaImagen.getInstance();
                            
                            vi_img_nueva.setTitle(vi.getTitle().split(",")[0] + ", [RGB]");
                        }
                        // No tiene sentido pasar una imagen en escala de grises a RGB.
                        break;
                    case "GREY":
                        // Si la img entrada es RGB, la paso a GRAY
                        if (img_fuente.getColorModel().getColorSpace().isCS_sRGB()) {
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                            ColorConvertOp cop = new ColorConvertOp(cs, null);
                            img_salida = cop.filter(img_fuente, null);
                            vi_img_nueva = VentanaInternaImagen.getInstance();
                            vi_img_nueva.setTitle((vi.getTitle().split(",")[0]).split(" ")[0] + ", [GREY]");
                        }
                        // Si la img entrada es YCC, es porque no es RGB y el número de bandas es 1, por tanto es GRaY y la paso a YCC
                        else if(!img_fuente.getColorModel().getColorSpace().isCS_sRGB() &&
                                img_fuente.getData().getNumBands() > 1){ // Si la img entrada es GREY, y no es YCC, la paso a YCC
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                            ColorConvertOp cop = new ColorConvertOp(cs, null);
                            img_salida = cop.filter(img_fuente, null);
                            vi_img_nueva = VentanaInternaImagen.getInstance();
                            vi_img_nueva.setTitle((vi.getTitle().split(",")[0]).split(" ")[0] + ", [GREY]");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "La imagen ya está en GRISES, no se va a convertir.", "Conversión a escala de grises", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                        
                    case "YCC":
                        //System.out.println("num bandas = " + img_fuente.getData().getNumBands());
                        // Si la img entrada es RGB, la paso a YCC, y si es GREY, también
                        if (img_fuente.getColorModel().getColorSpace().isCS_sRGB() || img_fuente.getData().getNumBands() == 1){
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                            ColorConvertOp cop = new ColorConvertOp(cs, null);
                            img_salida = cop.filter(img_fuente, null);
                            vi_img_nueva = VentanaInternaImagen.getInstance();
                            vi_img_nueva.setTitle((vi.getTitle().split(",")[0]).split(" ")[0] + " [YCC]");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "La imagen ya está en YCC, no se va a convertir.", "Conversión a YCC", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                }
                
                if(img_salida != null && vi_img_nueva != null){
                    vi_img_nueva.getLienzo().setImg(img_salida);
                    escritorio.add(vi_img_nueva);
                    vi_img_nueva.setVisible(true);
                }
            }catch(Exception e){
                System.err.println("Error");
            }
        }
    }//GEN-LAST:event_colorConvertOpActionPerformed

    private void contrasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasteActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().contraste(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se puede aplicar el contraste a la imagen.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_contrasteActionPerformed

    private void iluminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iluminarActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().iluminar_imagen(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se puede iluminar la imagen.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_iluminarActionPerformed

    private void oscurecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oscurecerActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().oscurecer_imagen(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se puede oscurecer la imagen.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_oscurecerActionPerformed

    private void sinusoidalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sinusoidalActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().sinusoidal_op(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se puede aplicar la operación sinusoidal.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_sinusoidalActionPerformed

    private void slider_rotacionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_rotacionStateChanged
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().girarImagen(imgSource, slider_rotacion.getValue()));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se puede aplicar la rotación.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_slider_rotacionStateChanged

    private void rotacion90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotacion90ActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().girarImagen(imgSource, 90));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se ha podido rotar 90º.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_rotacion90ActionPerformed

    private void rotacion180ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotacion180ActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().girarImagen(imgSource, 180));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se ha podido rotar 180º.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_rotacion180ActionPerformed

    private void rotacion270ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotacion270ActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().girarImagen(imgSource, 270));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se ha podido rotar 270º.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_rotacion270ActionPerformed

    private void slider_rotacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slider_rotacionFocusGained
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            ColorModel cm = vi_img.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi_img.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi_img.getLienzo().getImagen().isAlphaPremultiplied();
            imgSource = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_slider_rotacionFocusGained

    private void slider_rotacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slider_rotacionFocusLost
        imgSource = null;
    }//GEN-LAST:event_slider_rotacionFocusLost

    private void duplicar_ventanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_duplicar_ventanaActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_fuente = (VentanaInternaImagen) vi;
            try{
                VentanaInternaImagen vi_copia = VentanaInternaImagen.getInstance();
                BufferedImage img_copia = vi_fuente.getLienzo().getImagen(true);
                vi_copia.getLienzo().setImg(img_copia);

                String titulo_img = vi.getTitle();
                String[] partes = null;

                if(titulo_img.contains(".")){
                    partes = titulo_img.split("\\.");

                    if(partes[0].contains("_")){
                        String[] s = partes[0].split("_");
                        vi_copia.setTitle(s[0] + "_" + this.num_copia + "." + partes[1]);
                    }
                    else{
                        vi_copia.setTitle(partes[0] + "_" + this.num_copia + "." + partes[1]);
                    }
                }else
                    vi_copia.setTitle("Copia (" + this.num_copia + ")");

                this.num_copia++;

                this.escritorio.add(vi_copia);
                vi_copia.setVisible(true);
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se ha podido duplicar la ventana.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_duplicar_ventanaActionPerformed

    private void negativo_opActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negativo_opActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().negativo_op(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se ha podido aplicar el filtro negativo.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_negativo_opActionPerformed

    // Le voy a poner un tamaño fijo
    private void redimensionar_lienzoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redimensionar_lienzoActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            
            RedimensionLienzo tam_redimension = new RedimensionLienzo(this, true);
            tam_redimension.setVisible(true);
            
            BufferedImage img_dest = new BufferedImage(tam_redimension.getValorRedimensionAncho(), 
                                                       tam_redimension.getValorRedimensionAlto(), 
                                                       BufferedImage.TYPE_INT_ARGB);

            
            vi_img.getLienzo().setImg(vi_img.getLienzo().redimensionar_imagen(vi_img.getLienzo().getImagen(true), img_dest));
            vi_img.getLienzo().repaint();
        }
    }//GEN-LAST:event_redimensionar_lienzoActionPerformed

    private void disminuirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disminuirActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().disminuirImagen(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error al disminuir la imagen.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_disminuirActionPerformed

    private void aumentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aumentarActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().aumentarImagen(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error al aumentar la imagen.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_aumentarActionPerformed

    private void sepia_opActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sepia_opActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().sepia_op(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error al aumentar la imagen.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_sepia_opActionPerformed

    private void listado_espacios_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listado_espacios_colorActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            try{
                String espacio_color = listado_espacios_color.getSelectedItem().toString();
                BufferedImage img_fuente = vi_img.getLienzo().getImagen();
                BufferedImage img_salida = null;
                VentanaInternaImagen vi_img_nueva = null;
                
                switch(espacio_color){
                    case "RGB":
                        // Si la imagen ya está en RGB, no la paso.
                        if (img_fuente.getColorModel().getColorSpace().isCS_sRGB()) {
                            JOptionPane.showMessageDialog(null, "La imagen ya está en RGB, no se va a convertir.", "Conversión a RGB", JOptionPane.INFORMATION_MESSAGE);
                        }
                        // La imagen está en YCC, la paso a RGB
                        else if(!img_fuente.getColorModel().getColorSpace().isCS_sRGB() && img_fuente.getData().getNumBands() > 1){
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                            ColorConvertOp cop = new ColorConvertOp(cs, null);
                            img_salida = cop.filter(img_fuente, null);
                            vi_img_nueva = VentanaInternaImagen.getInstance();
                            
                            vi_img_nueva.setTitle(vi.getTitle().split(",")[0] + ", [RGB]");
                        }
                        // No tiene sentido pasar una imagen en escala de grises a RGB.
                        break;
                    case "GREY":
                        // Si la img entrada es RGB, la paso a GRAY
                        if (img_fuente.getColorModel().getColorSpace().isCS_sRGB()) {
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                            ColorConvertOp cop = new ColorConvertOp(cs, null);
                            img_salida = cop.filter(img_fuente, null);
                            vi_img_nueva = VentanaInternaImagen.getInstance();
                            vi_img_nueva.setTitle((vi.getTitle().split(",")[0]).split(" ")[0] + ", [GREY]");
                        }
                        // Si la img entrada es YCC, es porque no es RGB y el número de bandas es 1, por tanto es GRaY y la paso a YCC
                        else if(!img_fuente.getColorModel().getColorSpace().isCS_sRGB() &&
                                img_fuente.getData().getNumBands() > 1){ // Si la img entrada es GREY, y no es YCC, la paso a YCC
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                            ColorConvertOp cop = new ColorConvertOp(cs, null);
                            img_salida = cop.filter(img_fuente, null);
                            vi_img_nueva = VentanaInternaImagen.getInstance();
                            vi_img_nueva.setTitle((vi.getTitle().split(",")[0]).split(" ")[0] + ", [GREY]");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "La imagen ya está en GRISES, no se va a convertir.", "Conversión a escala de grises", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                        
                    case "YCC":
                        //System.out.println("num bandas = " + img_fuente.getData().getNumBands());
                        // Si la img entrada es RGB, la paso a YCC, y si es GREY, también
                        if (img_fuente.getColorModel().getColorSpace().isCS_sRGB() || img_fuente.getData().getNumBands() == 1){
                            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                            ColorConvertOp cop = new ColorConvertOp(cs, null);
                            img_salida = cop.filter(img_fuente, null);
                            vi_img_nueva = VentanaInternaImagen.getInstance();
                            vi_img_nueva.setTitle((vi.getTitle().split(",")[0]).split(" ")[0] + " [YCC]");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "La imagen ya está en YCC, no se va a convertir.", "Conversión a YCC", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                }
                
                if(img_salida != null && vi_img_nueva != null){
                    vi_img_nueva.getLienzo().setImg(img_salida);
                    escritorio.add(vi_img_nueva);
                    vi_img_nueva.setVisible(true);
                }
            }catch(Exception e){
                System.err.println("Error");
            }
        }
    }//GEN-LAST:event_listado_espacios_colorActionPerformed

    private void bandas_imgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bandas_imgActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            
            if(imgSource != null){
                try{
                    // Suponiendo que estamos en RGB
                    for(int i=0; i<imgSource.getRaster().getNumBands(); i++){
                        BufferedImage banda_i = vi_img.getLienzo().extraer_banda(imgSource, i);
                        VentanaInternaImagen vi_banda_i = VentanaInternaImagen.getInstance();
                        vi_banda_i.setTitle(vi.getTitle() + ", [Banda " + i + "]");
                        vi_banda_i.getLienzo().setImg(banda_i);
                        escritorio.add(vi_banda_i);
                        vi_banda_i.setVisible(true);
                    }                    
                } catch(IllegalArgumentException e){
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_bandas_imgActionPerformed

    private void tintado_opActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tintado_opActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().tintar_imagen(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error al tintar la imagen.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_tintado_opActionPerformed

    private void ecualizar_opActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ecualizar_opActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().ecualizar_imagen(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error al tintar la imagen.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_ecualizar_opActionPerformed

    private void abrir_audioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrir_audioActionPerformed
        JFileChooser dlg = new JFileChooser();
        
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            File f = dlg.getSelectedFile();
            
            File fichero_audio = new File( f.getAbsolutePath() ){
                @Override
                public String toString(){
                    return this.getName();
                }
            };
            
            lista_audios.addItem(fichero_audio);
        }
    }//GEN-LAST:event_abrir_audioActionPerformed

    private void btn_playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_playActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_REPRODUCTOR){
            VentanaInternaVLCPlayer reproductor = (VentanaInternaVLCPlayer) vi;
            reproductor.play();
        }
        else{
            File f = (File) lista_audios.getSelectedItem();
        
            if(f != null){
                this.player_clip = new SMClipPlayer(f);
                if (this.player_clip != null) {
                    this.player_clip.play();
                }
            }
        }
    }//GEN-LAST:event_btn_playActionPerformed

    private void btn_stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stopActionPerformed
        
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_REPRODUCTOR){
            VentanaInternaVLCPlayer reproductor = (VentanaInternaVLCPlayer) vi;
            reproductor.stop();
        }
        else if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_CAMARA){
            VentanaInternaCamara vi_cam = (VentanaInternaCamara) vi;
            vi_cam.close();
            escritorio.remove(vi_cam);
            vi_cam.setVisible(false);
        }
        else if (this.player_clip != null) {
            //System.out.println("stop....");
            this.player_clip.stop();
        }
        else if(this.recorder != null){
            this.recorder.stop();
        }
    }//GEN-LAST:event_btn_stopActionPerformed
    
    private void capturar_imagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capturar_imagenActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_CAMARA){
            VentanaInternaCamara vi_cam = (VentanaInternaCamara) vi;
                        
            VentanaInternaImagen vi_img = VentanaInternaImagen.getInstance();
            vi_img.getLienzo().setImg(vi_cam.getImage());
            vi_img.setTitle("Captura de la cámara");
            escritorio.add(vi_img);
            vi_img.setVisible(true);
            
        }
        else if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_REPRODUCTOR){
            VentanaInternaVLCPlayer reproductor = (VentanaInternaVLCPlayer) vi;
            
            VentanaInternaImagen vi_img = VentanaInternaImagen.getInstance();
            vi_img.getLienzo().setImg(reproductor.getImage());
            vi_img.setTitle("Captura del vídeo");
            escritorio.add(vi_img);
            vi_img.setVisible(true);
        }
    }//GEN-LAST:event_capturar_imagenActionPerformed

    private void lista_coloresItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_lista_coloresItemStateChanged
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            
            vi_img.getLienzo().setColor_pincel((Color)this.lista_colores.getSelectedItem());
        }
    }//GEN-LAST:event_lista_coloresItemStateChanged

    private void abrir_videoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrir_videoActionPerformed
        JFileChooser dlg = new JFileChooser();
        
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
            File f = dlg.getSelectedFile();
            
            VentanaInternaVLCPlayer vi = VentanaInternaVLCPlayer.getInstance(f);
            escritorio.add(vi);
            vi.setVisible(true);
        }
    }//GEN-LAST:event_abrir_videoActionPerformed

    // Operación que aumenta el color rojo de una imagen cualquiera:
    private void op_px_pxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op_px_pxActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().op_px_a_px(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se puedemanchar la imagen.", "Aviso manchar imagen", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_op_px_pxActionPerformed

    private void op_com_comActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_op_com_comActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            imgSource = vi_img.getLienzo().getImagen();
            
            try{
                if(imgSource != null){
                    vi_img.getLienzo().setImg(vi_img.getLienzo().op_com_a_com(imgSource));
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se puede calcular la imagen media", "Aviso calcular imagen media", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_op_com_comActionPerformed

    private void mi_lookup_opActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mi_lookup_opActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;

            byte f[] = new byte[256];
            for (int i=0; i<256; i++)
                f[i] = (byte) (Math.min(128%(i+1), 143)*3);
            
            ByteLookupTable lt = new ByteLookupTable(0, f);
            
            try{
                // Convierto la imagen a una con transparencia
                BufferedImage img = ImageTools.convertImageType(vi_img.getLienzo().getImagen(), BufferedImage.TYPE_INT_ARGB);
                
                LookupOp lop = new LookupOp(lt, null);
                BufferedImage imgdest = lop.filter(img, null);
                
                if(imgdest != null){
                    vi_img.getLienzo().setImg(imgdest);
                    vi_img.getLienzo().repaint();
                }
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se ha podido aplicar la función S.", "Ok", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_mi_lookup_opActionPerformed

    // Cuando selecciono una figura, debo poder modificar sus propiedades en el momento
    private void lista_figurasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lista_figurasValueChanged
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            
            int pos = -1;
            
            if(evt.getValueIsAdjusting() == false){
                if(this.lista_figuras.getSelectedIndex() != -1){
                    this.valor_x_p1.setEnabled(true);
                    this.valor_y_p1.setEnabled(true);
                    pos = this.lista_figuras.getSelectedIndex();
                    vi_img.getLienzo().set_figura_a_editar(pos);
                    vi_img.getLienzo().setEdicion(true);
                    
                    // Cargo las coordenadas de esa figura en las casillas
                    Point2D p1 = vi_img.getLienzo().coords_p1_figura();
                    
                    this.valor_x_p1.setText(""+p1.getX());
                    this.valor_y_p1.setText(""+p1.getY());
                }
            }
        }
    }//GEN-LAST:event_lista_figurasValueChanged

    private void tipo_trazoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tipo_trazoItemStateChanged
        //System.out.println("Valor de trazo === " + tipo_trazo.getSelectedItem());
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            
            vi_img.getLienzo().setTipo_trazo(this.tipo_trazo.getSelectedItem().toString());
        }
    }//GEN-LAST:event_tipo_trazoItemStateChanged

    private void grado_transparenciaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_grado_transparenciaStateChanged
        //System.out.println("Valor de transparencia === " + grado_transparencia.getValue()/100.0);
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            vi_img.getLienzo().setGrado_transparencia((grado_transparencia.getValue()/100.0f));
        }
    }//GEN-LAST:event_grado_transparenciaStateChanged

    private void te_pilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_te_pilleActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_CAMARA){
            VentanaInternaCamara vi_cam = (VentanaInternaCamara) vi;

            this.mc.setDetector(vi_cam.getCamara());
            if(this.te_pille.isSelected()){
                this.mc.getDetector().start();
            }
            else{
                this.mc.getDetector().stop();
                this.mc = null;
            }
        }
    }//GEN-LAST:event_te_pilleActionPerformed

    private void relleno_figuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relleno_figuraActionPerformed
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            
            this.r = new PanelRelleno(this,true, vi_img.getLienzo());
            this.r.setVisible(true);
        }
    }//GEN-LAST:event_relleno_figuraActionPerformed

    private void actualizar_p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizar_p1ActionPerformed
        try{            
            this.p1_x = Double.parseDouble(this.valor_x_p1.getText() );
            this.p1_y = Double.parseDouble(this.valor_y_p1.getText() );
            
            VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
            if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
                VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;

                vi_img.getLienzo().set_nuevas_coordenadas_figura(p1_x, p1_y);
                vi_img.getLienzo().setEdicion(true);
            }

        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(rootPane, "Introduce valores correctos.");
        }
    }//GEN-LAST:event_actualizar_p1ActionPerformed

    private void slider_umbralizacionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_umbralizacionStateChanged
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            
            try{
                this.imgSource = vi_img.getLienzo().getImagen();
                if(this.imgSource != null){
                     System.out.println("slider activo");
                    vi_img.getLienzo().setImg(vi_img.getLienzo().umbralizacion(imgSource, slider_umbralizacion.getValue()));
                     System.out.println("slider activo");
                    vi_img.getLienzo().repaint();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error: No se puede aplicar la umbralización.", "Error en umbralización", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_slider_umbralizacionStateChanged

    private void slider_umbralizacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slider_umbralizacionFocusGained
        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        
        if(vi != null && vi.getTipoVentanaEscritorio() == VentanaInterna.VENTANA_IMAGEN){
            VentanaInternaImagen vi_img = (VentanaInternaImagen) vi;
            ColorModel cm = vi_img.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi_img.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi_img.getLienzo().getImagen().isAlphaPremultiplied();
            imgSource = new BufferedImage(cm,raster,alfaPre,null);
        }
    }//GEN-LAST:event_slider_umbralizacionFocusGained

    private void slider_umbralizacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slider_umbralizacionFocusLost
        imgSource = null;
    }//GEN-LAST:event_slider_umbralizacionFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrir;
    private javax.swing.JMenuItem abrir_archivo;
    private javax.swing.JMenuItem abrir_audio;
    private javax.swing.JMenuItem abrir_camara;
    private javax.swing.JMenuItem abrir_video;
    private javax.swing.JButton actualizar_p1;
    private javax.swing.JMenuItem affineTransformOp;
    private javax.swing.JToggleButton alisado;
    private javax.swing.JButton aumentar;
    private javax.swing.JMenuItem bandCombineOp;
    private javax.swing.JButton bandas_img;
    private javax.swing.JPanel barra_atributos;
    private javax.swing.JPanel brillo;
    private javax.swing.JButton btn_grabar;
    private javax.swing.JButton btn_play;
    private javax.swing.JButton btn_stop;
    private javax.swing.JButton capturar_imagen;
    private javax.swing.JMenuItem colorConvertOp;
    private javax.swing.ButtonGroup colores;
    private javax.swing.JPanel conjunto_herramientas;
    private javax.swing.JButton contraste;
    private javax.swing.JLabel coordenadas_raton;
    private javax.swing.JMenuItem cop_propia_1;
    private javax.swing.JMenuItem cop_propia_2;
    private javax.swing.JButton disminuir;
    private javax.swing.JMenuItem duplicar_ventana;
    private javax.swing.JButton ecualizar_op;
    private javax.swing.JMenu edicion;
    private javax.swing.JToggleButton editar;
    private javax.swing.JButton encender_cam;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.JPanel filtros_img;
    private javax.swing.JPanel filtros_img1;
    private javax.swing.JPanel filtros_img2;
    private javax.swing.JPanel filtros_img3;
    private javax.swing.JPanel filtros_img4;
    private javax.swing.ButtonGroup formas_dibujo;
    private javax.swing.JMenuItem grabar_audio;
    private javax.swing.JMenu grabar_sonido;
    private javax.swing.JSlider grado_transparencia;
    private javax.swing.JButton guardar;
    private javax.swing.JMenuItem guardar_archivo;
    private javax.swing.JToolBar herramientas_superior;
    private javax.swing.JButton iluminar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToggleButton linea;
    private javax.swing.JComboBox<File> lista_audios;
    private javax.swing.JComboBox<Color> lista_colores;
    private javax.swing.JList<String> lista_figuras;
    private javax.swing.JComboBox<String> lista_filtros_img;
    private javax.swing.JComboBox<String> listado_espacios_color;
    private javax.swing.JMenuItem lookUpOp;
    private javax.swing.JMenuItem mi_lookup_op;
    private javax.swing.JMenuBar mi_menu;
    private javax.swing.JMenuItem negativo_op;
    private javax.swing.JButton nuevo;
    private javax.swing.JMenuItem nuevo_archivo;
    private javax.swing.JMenuItem op_com_com;
    private javax.swing.JMenuItem op_px_px;
    private javax.swing.JButton oscurecer;
    private javax.swing.JToggleButton ovalo;
    private javax.swing.JToggleButton rectangulo;
    private javax.swing.JMenuItem redimensionar_lienzo;
    private javax.swing.JButton relleno_figura;
    private javax.swing.JMenuItem rescale_op_img;
    private javax.swing.JButton rotacion180;
    private javax.swing.JButton rotacion270;
    private javax.swing.JButton rotacion90;
    private javax.swing.JToggleButton round_rect;
    private javax.swing.JButton sepia_op;
    private javax.swing.JButton sinusoidal;
    private javax.swing.JSlider slider_brillo;
    private javax.swing.JSlider slider_rotacion;
    private javax.swing.JSlider slider_umbralizacion;
    private javax.swing.JSpinner spinner_grosor;
    private javax.swing.JLabel string_forma_activa;
    private javax.swing.JToggleButton te_pille;
    private javax.swing.JButton tintado_op;
    private javax.swing.JComboBox<String> tipo_trazo;
    private javax.swing.JTextField valor_x_p1;
    private javax.swing.JTextField valor_y_p1;
    private javax.swing.JCheckBoxMenuItem ver_barra_atributos;
    private javax.swing.JCheckBoxMenuItem ver_barra_estado;
    private javax.swing.JCheckBoxMenuItem ver_barra_formas;
    private javax.swing.JPanel zona_dibujo;
    private javax.swing.JPanel zona_info;
    // End of variables declaration//GEN-END:variables
}
