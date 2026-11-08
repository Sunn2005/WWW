package fit.iuh.shoppingcart.controller;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String HomePage(Model model) {
        model.addAttribute("message", "Welcome to Shopping Cart");
        model.addAttribute("date", LocalDate.now());
        return "home";
    }
}
