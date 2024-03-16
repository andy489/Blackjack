package com.casino.blackjack.controller;

import com.casino.blackjack.model.dto.UserRegistrationDTO;
import com.casino.blackjack.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.security.web.context.SecurityContextRepository;

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";

    private final UserService userService;

    private final SecurityContextRepository securityContextRepository;

    public AuthController(UserService userService, SecurityContextRepository securityContextRepository) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
    }

    @ModelAttribute(name = "userRegistrationDTO")
    public UserRegistrationDTO initUserRegisterDto() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {

        return super.view("auth/login");
    }

    @GetMapping("/register")
    public ModelAndView getRegister() {

        return super.view("auth/register");
    }

    @PostMapping("/register")
    public ModelAndView postRegister(
            @Valid @ModelAttribute(name = "userRegistrationDTO") UserRegistrationDTO userRegistrationDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + "userRegistrationDTO",
                    bindingResult);

            return super.redirect("/auth/register");
        }

//        userService.registerAndLogin(userRegistrationDTO, successfulAuth -> {
//            // populating security context
//            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
//
//            SecurityContext context = strategy.createEmptyContext();
//            context.setAuthentication(successfulAuth);
//
//            strategy.setContext(context);
//
//            securityContextRepository.saveContext(context, request, response);
//        });

        return super.redirect("/");
    }

}


