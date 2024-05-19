package com.casino.blackjack.controller;

import com.casino.blackjack.model.dto.CreditCardDTO;
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
@RequestMapping("/credit-card")
public class CreditCardController extends BaseController {

    public CreditCardController() {
    }

    @ModelAttribute(name = "creditCardDTO")
    public CreditCardDTO initCreditCardDTO() {
        return new CreditCardDTO();
    }

    @GetMapping("/register")
    public ModelAndView getCreditCardForm() {
        return super.view("credit_card/credit-card-form");
    }

    @PostMapping("/register")
    public ModelAndView postCreditCardForm(
            @Valid @ModelAttribute(name = "creditCardDTO") CreditCardDTO creditCardDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("creditCardDTO", creditCardDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + "creditCardDTO", bindingResult);

            return super.redirect("/credit-card/register");
        }

        return redirect("/");
    }
}
