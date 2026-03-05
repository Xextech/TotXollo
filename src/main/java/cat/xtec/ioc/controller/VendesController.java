package cat.xtec.ioc.controller;

import cat.xtec.ioc.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Germán Flores
 */
@Controller
public class VendesController {
    @Autowired
    VendaService vendaService;
    
    @RequestMapping("/vendaXollo/{codi}")
    public String vendre(@PathVariable("codi") String codi) {
        vendaService.processVenda(codi);
        return "redirect:/getXollo/" + codi;
    }
    
    
}
