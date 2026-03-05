package cat.xtec.ioc.service.impl;

import cat.xtec.ioc.domain.Xollo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cat.xtec.ioc.service.VendaService;
import cat.xtec.ioc.repository.XolloRepository;

/**
 *
 * @author Germán Flores
 */
@Service
public class VendaServiceImpl implements VendaService {

    @Autowired
    private XolloRepository xolloRepository;

    @Override
    public void processVenda(String codiXollo) {
        Xollo r = xolloRepository.getXolloByCodi(codiXollo);

        int num = 1;
        if ((r.getNumeroReserves() + num) > r.getNumeroUnitats()) {
            throw new IllegalArgumentException("No hi ha prou unitats. S'han reservat el total de: " + r.getNumeroReserves());
        }
        r.setNumeroReserves(r.getNumeroReserves() + num);
        xolloRepository.updateXollo(r);
    }

}
