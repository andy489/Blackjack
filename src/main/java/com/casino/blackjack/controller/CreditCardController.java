package com.casino.blackjack.controller;

import com.casino.blackjack.model.dto.CreditCardDTO;
import com.casino.blackjack.model.dto.DepositDTO;
import com.casino.blackjack.model.entity.CreditCardEntity;
import com.casino.blackjack.model.user.CustomUserDetails;
import com.casino.blackjack.model.view.CreditCardsManageView;
import com.casino.blackjack.service.CreditCardService;
import com.casino.blackjack.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
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
    public ModelAndView getRegisterCardForm() {

        return super.view("credit_card/register-card-form");
    }

    @GetMapping("/deposit")
    public ModelAndView getDepositForm(@AuthenticationPrincipal CustomUserDetails currentUser,
                                       ModelAndView mav) {

        List<CreditCardDTO> userRegisteredCreditCards = creditCardService.getRegisteredCreditCards(currentUser.getId());

        mav.addObject("registered_credit_cards", userRegisteredCreditCards);

        return super.view("credit_card/deposit-form", mav);
    }

    @PostMapping("/register")
    public ModelAndView postCreditCardForm(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @Valid @ModelAttribute(name = "creditCardDTO") CreditCardDTO creditCardDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("creditCardDTO", creditCardDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + "creditCardDTO", bindingResult);

            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError currErr : allErrors) {
                String code = currErr.getCode();

                if (Objects.requireNonNull(code).startsWith("MaxCreditCardRegisteredLimitReached")) {
                    redirectAttributes.addFlashAttribute("modalLimitReached", true);
                    break;
                }
            }

            return super.redirect("/credit-card/register");
        }

        Optional<CreditCardEntity> creditCardEntity =
                creditCardService.registerNewCreditCard(creditCardDTO, currentUser.getId());

        if (creditCardEntity.isEmpty()) {
            redirectAttributes.addFlashAttribute("modalLimitReached", true);
            return redirect("/credit-card/register");
        }

        redirectAttributes.addFlashAttribute("modalSucRegCreditCard", true);

        return redirect("/credit-card/register");
    }

    @PostMapping("/deposit")
    public ModelAndView postDeposit(
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

        redirectAttributes.addFlashAttribute("modalSucDep", true);

        return super.redirect("/credit-card/deposit");
    }

    @GetMapping("/manage")
    public ModelAndView getManageCreditCards(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            ModelAndView modelAndView) {

        List<CreditCardsManageView> byOwnerId = creditCardService.getByOwnerId(currentUser.getId());

        modelAndView.addObject("cards", byOwnerId);

        return super.view("credit_card/card-management", modelAndView);
    }
}
