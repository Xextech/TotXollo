package cat.xtec.ioc.service.impl;

import cat.xtec.ioc.domain.Xollo;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cat.xtec.ioc.service.XolloService;
import cat.xtec.ioc.repository.XolloRepository;

/**
 *
 * @author Germán Flores
 */

@Service
public class XolloServiceImpl implements XolloService {
    @Autowired
    private XolloRepository xolloRepository;
    
    @Override
    public Xollo getXolloByCodi(String codi) {
        return xolloRepository.getXolloByCodi(codi);    
    }
   
    @Override
    public void addXollo(Xollo xollo) {
        xolloRepository.addXollo(xollo);
    }
    
}
