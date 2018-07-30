package setra.propulsar.com.sectrab.domainlayer.models;

import android.widget.ImageButton;

public class Contacts
{
    private String nombreEmpresa;
    private String ubicacionEmpresa;
    private int telefonoEmpresa;
    private ImageButton imageButtonLlamada;

    public Contacts(){

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

    public void setUbicacionEmpresa(String ubicacionEmpresa) {
        this.ubicacionEmpresa = ubicacionEmpresa;
    }

    public int getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(int telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public ImageButton getImageButtonLlamada() {
        return imageButtonLlamada;
    }

    public void setImageButtonLlamada(ImageButton imageButtonLlamada) {
        this.imageButtonLlamada = imageButtonLlamada;
    }
}
