package com.casino.blackjack.controller;

import com.casino.blackjack.model.dto.PayCardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pay-card")
public class PayCardController extends BaseController {

    private final ObjectMapper objectMapper;

    public PayCardController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ModelAttribute(name = "payCardDTO")
    public PayCardDTO initPayCardDTO() {
        return new PayCardDTO();
    }

    @ModelAttribute(name = "payCardJSON")
    public String initPayCardJSON() {
        return "{}";
    }

    @GetMapping("/register")
    public ModelAndView getPayCardForm() {
        return super.view("pay_card/pay-card-form");
    }

    @PostMapping("/register")
    public ModelAndView postPayCardForm(
            @Valid @ModelAttribute(name = "payCardDTO") PayCardDTO payCardDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("payCardDTO", payCardDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + "payCardDTO", bindingResult);

            return super.redirect("/pay-card/register");
        }

        return redirect("/");
    }
}
