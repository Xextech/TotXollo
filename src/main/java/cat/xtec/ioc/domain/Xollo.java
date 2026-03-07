
package cat.xtec.ioc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Germán Flores
 */
@Entity
@Table(name = "xollos")
public class Xollo implements Serializable {

    @Id
    @Column(name = "codi", nullable = false, length = 100)
    protected String codi;

    @Column(name = "numeroUnitats", nullable = false)
    protected Integer numeroUnitats;

    @Column(name = "numeroReserves", nullable = false)
    protected Integer numeroReserves;

    @Column(name = "titol", nullable = false, length = 250)
    protected String titol;

    @Column(name = "descripcio", nullable = false, length = 250)
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
