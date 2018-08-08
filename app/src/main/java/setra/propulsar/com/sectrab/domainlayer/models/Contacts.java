package setra.propulsar.com.sectrab.domainlayer.models;

import android.widget.ImageButton;

public class Contacts
{
    private String nombreEmpresa;
    private String ubicacionEmpresa;
    private String telefonoEmpresa;
    private ImageButton imageButtonLlamada;

    public Contacts(){

    }

    public Contacts(String nombreEmpresa, String ubicacion, String telefono){
        this.nombreEmpresa=nombreEmpresa; this.ubicacionEmpresa=ubicacion; this.telefonoEmpresa=telefono;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getUbicacionEmpresa() {

        return ubicacionEmpresa;
    }

    public void setUbicacionEmpresa(String ubicacionEmpresa) { this.ubicacionEmpresa = ubicacionEmpresa; }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public ImageButton getImageButtonLlamada() {

        return imageButtonLlamada;
    }

    public void setImageButtonLlamada(ImageButton imageButtonLlamada) {
        this.imageButtonLlamada = imageButtonLlamada;
    }
}
