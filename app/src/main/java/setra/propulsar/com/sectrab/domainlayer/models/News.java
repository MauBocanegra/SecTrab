package setra.propulsar.com.sectrab.domainlayer.models;

import java.util.Date;

public class News {

    private int idNoticia;
    private String tituloNoticia;
    private String infoNoticia;
    private String linkImagenNoticia;
    private String datetime;

    public News(){

    }

    public int getIdNoticia() { return idNoticia; }

    public void setIdNoticia(int idNoticia) { this.idNoticia = idNoticia; }

    public String getTituloNoticia() { return tituloNoticia; }

    public void setTituloNoticia(String tituloNoticia) {
        this.tituloNoticia = tituloNoticia;
    }

    public String getInfoNoticia() { return infoNoticia; }

    public void setInfoNoticia(String infoNoticia) {
        this.infoNoticia = infoNoticia;
    }

    public String getLinkImagenNoticia() { return linkImagenNoticia; }

    public void setLinkImagenNoticia(String linkImagenNoticia) { this.linkImagenNoticia = linkImagenNoticia; }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}