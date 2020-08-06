/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo Propiedad
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class Propiedad {
    
    protected static int PROPIEDAD_TRAZO = 0;
    protected static int PROPIEDAD_TRANSPARENCIA = 1;
    protected static int PROPIEDAD_ALISADO = 2;
    
    protected int tipo_propiedad;
    
    /**
     * Metodo constructor por defecto
     */
    public Propiedad(){}
    
    /**
     * Metodo para devolver un tipo de propiedad
     * @return tipo_propiedad es el tipo de la propiedad
     */
    public int getTipoPropiedad(){
        return tipo_propiedad;
    }
    
    /**
     * Establece el tipo de propiedad
     * @param tipo de la propiedad
     */
    public void setTipoPropiedad(int tipo){
        this.tipo_propiedad = tipo;
    }
}
