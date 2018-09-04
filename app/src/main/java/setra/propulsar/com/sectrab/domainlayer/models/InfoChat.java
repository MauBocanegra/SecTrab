package setra.propulsar.com.sectrab.domainlayer.models;

public class InfoChat {

    private String titulo;
    private String contenido;

    public InfoChat(){

    }

    public InfoChat(String titulo, String contenido){
        this.titulo=titulo; this.contenido =contenido;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
