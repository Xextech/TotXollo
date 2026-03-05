package cat.xtec.ioc.repository.impl;

import cat.xtec.ioc.domain.Xollo;
import cat.xtec.ioc.repository.XolloRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import java.util.Date;

/**
 *
 * @author Germán Flores
 */
@Repository
public class InMemoryXolloRepository implements XolloRepository {

    private List<Xollo> llista = new ArrayList<Xollo>();

    public InMemoryXolloRepository() {

        Integer codi = 0;

        for (Integer i = 0; i < 3; i++) {
            Xollo xollo = new Xollo(codi.toString(), 10, 0, "xolloTitol" + codi, "xolloDescripcio" + codi);
            llista.add(xollo);
            codi++;
            System.out.println(xollo.getCodi());
        }
    }

    @Override
    public Xollo getXolloByCodi(String codi) {
        Xollo xolloByCodi = null;
        for (Xollo r : llista) {
            if (r != null && r.getCodi() != null
                    && r.getCodi().equals(codi)) {
                xolloByCodi = r;
                break;
            }
        }
        if (xolloByCodi == null) {
            throw new IllegalArgumentException(
                    "No s'ha trobat el xollo amb el codi: " + codi);
        }
        return xolloByCodi;
    }

    @Override
    public void addXollo(Xollo recurs) {
        for (Xollo xollo : llista) {
            if(xollo.getCodi().equals(recurs.getCodi())) {
                throw new IllegalArgumentException(
                        "Ja existeix el recurs amb el codi: " + recurs.getCodi());
            }
        }
        recurs.setNumeroReserves(0);
        this.llista.add(recurs);

    }

    @Override
    public void updateXollo(Xollo xollo) {
        
    }

    @Override
    public List<Xollo> getAllXollos() {
        return llista;
    }

}
