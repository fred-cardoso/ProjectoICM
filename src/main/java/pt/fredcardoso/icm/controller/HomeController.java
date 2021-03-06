package pt.fredcardoso.icm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
 
   @RequestMapping(method = RequestMethod.GET)
   public String printHello(ModelMap model) {      
      //System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

      return "index.html";
   }
   
   @RequestMapping(value = "/test", method = RequestMethod.GET)
   public String test(ModelMap model) {      
      //System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());

      return "test.html";
   }

}