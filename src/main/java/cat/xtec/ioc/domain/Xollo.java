
package cat.xtec.ioc.domain;

/**
 *
 * @author Germán Flores
 */
public class Xollo {

    protected String codi;
    protected Integer numeroUnitats;
    protected Integer numeroReserves;
    protected String titol;
    protected String descripcio;

    public Xollo(String codi, Integer numeroUnitats, Integer numeroReserves, String titol, String descripcio) {
        this.codi = codi;
        this.numeroUnitats = numeroUnitats;
        this.numeroReserves = numeroReserves;
        this.titol = titol;
        this.descripcio = descripcio;
    }

    public Xollo() {
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public Integer getNumeroUnitats() {
        return numeroUnitats;
    }

    public void setNumeroUnitats(Integer numeroUnitats) {
        this.numeroUnitats = numeroUnitats;
    }

    public Integer getNumeroReserves() {
        return numeroReserves;
    }

    public void setNumeroReserves(Integer numeroReserves) {
        this.numeroReserves = numeroReserves;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

   
    
}
