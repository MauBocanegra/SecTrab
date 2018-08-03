package setra.propulsar.com.sectrab.domainlayer.models;

public class Jobs
{
    private String imageuUrl;
    private String nombreEmpresa;
    private String infoEmpresa;
    private String puestoEmpresa;
    private String descripcionEmpleo;
    private String ubicacionEmpleo;

    public Jobs(){

    }

    public String getImageuUrl() {
        return imageuUrl;
    }

    public void setImageuUrl(String imageuUrl) { this.imageuUrl = imageuUrl; }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }

    public String getInfoEmpresa() {
        return infoEmpresa;
    }

    public void setInfoEmpresa(String infoEmpresa) { this.infoEmpresa = infoEmpresa; }

    public String getPuestoEmpresa() {
        return puestoEmpresa;
    }

    public void setPuestoEmpresa(String puestoEmpresa) { this.puestoEmpresa = puestoEmpresa; }

    public String getDescripcionEmpleo() {
        return descripcionEmpleo;
    }

    public void setDescripcionEmpleo(String descripcionEmpleo) { this.descripcionEmpleo = descripcionEmpleo; }

    public String getUbicacionEmpleo() {
        return ubicacionEmpleo;
    }

    public void setUbicacionEmpleo(String ubicacionEmpleo) { this.ubicacionEmpleo = ubicacionEmpleo; }
}
