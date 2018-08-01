package setra.propulsar.com.sectrab.domainlayer.models;

import android.media.Image;

public class News {

    private String tituloNoticia;
    private String infoNoticia;
    private Image imagenNoticia;

    public News(){

    }

    public String getTituloNoticia() { return tituloNoticia; }

    public void setTituloNoticia(String tituloNoticia) {
        this.tituloNoticia = tituloNoticia;
    }

    public String getInfoNoticia() { return infoNoticia; }

    public void setInfoNoticia(String infoNoticia) {
        this.infoNoticia = infoNoticia;
    }

    public Image getImagenNoticia() { return imagenNoticia; }

    public void setImagenNoticia(Image imagenNoticia) {
        this.imagenNoticia = imagenNoticia;
    }
}