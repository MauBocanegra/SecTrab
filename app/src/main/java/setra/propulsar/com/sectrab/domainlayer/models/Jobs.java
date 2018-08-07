package setra.propulsar.com.sectrab.domainlayer.models;

public class Jobs
{
    private String linkImagenEmpresa;
    private String nombreEmpresa;
    private String infoEmpresa;
    private String puestoEmpresa;
    private String descripcionEmpleo;
    private String ubicacionEmpleo;

    public Jobs(){

    }

    public String getLinkImagenEmpresa() {
        return linkImagenEmpresa;
    }

    public void setLinkImagenEmpresa(String linkImagenEmpresa) { this.linkImagenEmpresa = linkImagenEmpresa; }

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
