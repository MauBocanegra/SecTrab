package setra.propulsar.com.sectrab.domainlayer.models;

public class News {

    private int idNoticia;
    private String tituloNoticia;
    private String infoNoticia;
    private String linkImagenNoticia;


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

    public void setLinkImagenNoticia(String linkImagenNoticia) {
        this.linkImagenNoticia = linkImagenNoticia;
    }
}