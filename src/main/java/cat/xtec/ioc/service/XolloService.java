package cat.xtec.ioc.service;

import cat.xtec.ioc.domain.Xollo;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Germán Flores
 */
public interface XolloService {
    
    void addXollo(Xollo recurs);
    
    Xollo getXolloByCodi(String codi);
  
}
