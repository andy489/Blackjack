package com.casino.blackjack.controller;

import com.casino.blackjack.model.dto.CreditCardDTO;
import com.casino.blackjack.model.dto.DepositDTO;
import com.casino.blackjack.model.entity.CreditCardEntity;
import com.casino.blackjack.model.user.CustomUserDetails;
import com.casino.blackjack.service.CreditCardService;
import com.casino.blackjack.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/credit-card")
public class CreditCardController extends BaseController {

    private final CreditCardService creditCardService;

    private final WalletService walletService;

    public CreditCardController(CreditCardService creditCardService,
                                WalletService walletService) {

        this.creditCardService = creditCardService;
        this.walletService = walletService;
    }

    @ModelAttribute(name = "depositDTO")
    public DepositDTO initDepositDTO() {
        return new DepositDTO();
    }

    @ModelAttribute(name = "creditCardDTO")
    public CreditCardDTO initCreditCardDTO() {
        return new CreditCardDTO();
    }

    @GetMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getRegisterCardForm(@AuthenticationPrincipal CustomUserDetails currentUser) {

        ModelAndView mav = new ModelAndView();
        List<CreditCardDTO> userRegisteredCreditCards = creditCardService.getRegisteredCreditCards(currentUser.getId());

        mav.addObject("registered_credit_cards", userRegisteredCreditCards);

        return super.view("credit_card/register-card-form", mav);
    }

    @GetMapping("/deposit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getDepositForm() {

        return super.view("credit_card/deposit-form");
    }

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView postCreditCardForm(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @Valid @ModelAttribute(name = "creditCardDTO") CreditCardDTO creditCardDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("creditCardDTO", creditCardDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + "creditCardDTO", bindingResult);

            return super.redirect("/credit-card/register");
        }

        Optional<CreditCardEntity> creditCardEntity =
                creditCardService.registerNewCreditCard(creditCardDTO, currentUser.getId());

        if (creditCardEntity.isEmpty()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("limit_reached", true);
            return redirect("/credit-card/register", mav);

        }

        return redirect("/credit-card/register");
    }

    @PostMapping("/deposit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView postDeposit(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @Valid @ModelAttribute(name = "depositDTO") DepositDTO depositDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("depositDTO", depositDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + "depositDTO", bindingResult);

            return super.redirect("/credit-card/deposit");
        }

        Optional<Long> ownerId = creditCardService.getOwnerId(depositDTO.getCardNumber());

        if (ownerId.isEmpty()) {
            redirectAttributes.addFlashAttribute("depositDTO", depositDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + "depositDTO", bindingResult);
            redirectAttributes.addFlashAttribute("no_owner", true);
            return super.redirect("/credit-card/deposit");
        }

        walletService.deposit(depositDTO.getDepositSum(), ownerId.get());

        // TODO add deposited amount to fade away

        return super.redirect("/credit-card/deposit");
    }
}
