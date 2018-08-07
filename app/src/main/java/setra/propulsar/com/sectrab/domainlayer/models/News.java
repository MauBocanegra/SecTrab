package setra.propulsar.com.sectrab.domainlayer.models;

public class News {

    private String tituloNoticia;
    private String infoNoticia;
    private String linkImagenNoticia;

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

    public String getLinkImagenNoticia() { return linkImagenNoticia; }

    public void setLinkImagenNoticia(String linkImagenNoticia) {
        this.linkImagenNoticia = linkImagenNoticia;
    }
}