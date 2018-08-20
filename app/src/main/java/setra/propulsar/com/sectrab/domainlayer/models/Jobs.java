package setra.propulsar.com.sectrab.domainlayer.models;

public class Jobs
{
    private int idEmpleo;
    private String linkLogoEmpresa;
    private String nombreEmpresa;
    private String sectorEmpresa;
    private String titlePuestoEmpresa;
    private String descripcionEmpleo;
    private String ubicacionEmpleo;
    private String telefonoEmpresa;
    private String emailEmpresa;
    private String datetime;

    public Jobs(){

    }

    public String getLinkLogoEmpresa() {
        return linkLogoEmpresa;
    }

    public void setLinkLogoEmpresa(String linkLogoEmpresa) { this.linkLogoEmpresa = linkLogoEmpresa; }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }

    public String getSectorEmpresa() {
        return sectorEmpresa;
    }

    public void setSectorEmpresa(String sectorEmpresa) { this.sectorEmpresa = sectorEmpresa; }

    public String getTitlePuestoEmpresa() {
        return titlePuestoEmpresa;
    }

    public void setTitlePuestoEmpresa(String titlePuestoEmpresa) { this.titlePuestoEmpresa = titlePuestoEmpresa; }

    public String getDescripcionEmpleo() {
        return descripcionEmpleo;
    }

    public void setDescripcionEmpleo(String descripcionEmpleo) { this.descripcionEmpleo = descripcionEmpleo; }

    public String getUbicacionEmpleo() {
        return ubicacionEmpleo;
    }

    public void setUbicacionEmpleo(String ubicacionEmpleo) { this.ubicacionEmpleo = ubicacionEmpleo; }

    public int getIdEmpleo() { return idEmpleo; }

    public void setIdEmpleo(int idEmpleo) { this.idEmpleo = idEmpleo; }

    public String getTelefonoEmpresa() { return telefonoEmpresa; }

    public void setTelefonoEmpresa(String telefonoEmpresa) { this.telefonoEmpresa = telefonoEmpresa; }

    public String getEmailEmpresa() { return emailEmpresa; }

    public void setEmailEmpresa(String emailEmpresa) { this.emailEmpresa = emailEmpresa; }

    public String getDatetime() { return datetime; }

    public void setDatetime(String datetime) { this.datetime = datetime; }
}
