package com.casino.blackjack.controller;

import com.casino.blackjack.model.dto.RecaptchaResponseDTO;
import com.casino.blackjack.model.dto.UserRegistrationDTO;
import com.casino.blackjack.service.RecaptchaService;
import com.casino.blackjack.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.security.web.context.SecurityContextRepository;

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";

    private final UserService userService;

    private final RecaptchaService recaptchaService;

    private final SecurityContextRepository securityContextRepository;

    private final LocaleResolver localeResolver;

    public AuthController(UserService userService,
                          RecaptchaService recaptchaService,
                          SecurityContextRepository securityContextRepository,
                          LocaleResolver localeResolver) {

        this.userService = userService;
        this.recaptchaService = recaptchaService;
        this.securityContextRepository = securityContextRepository;
        this.localeResolver = localeResolver;
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
            HttpServletResponse response,
            @RequestParam("g-recaptcha-response") String recaptchaResponse) {

        boolean isBot = !recaptchaService.verify(recaptchaResponse)
                .map(RecaptchaResponseDTO::isSuccess)
                .orElse(false);

        if (isBot) {
            LOGGER.warn("reCAPTCHA protected your website from spam and abuse");
            return super.redirect("/");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + "userRegistrationDTO",
                    bindingResult);

            return super.redirect("/auth/register");
        }

        userService.registerAndLogin(userRegistrationDTO, localeResolver.resolveLocale(request), successfulAuth -> {

            // populating security context
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(successfulAuth);
            strategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        });

        return super.redirect("register-success");
    }

    @GetMapping("/register-success")
    public ModelAndView registerSuccess() {
        return super.view("auth/register-success");
    }

    @ModelAttribute(name = "userRegistrationDTO")
    public UserRegistrationDTO initUserRegisterDto() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {

        return super.view("auth/login");
    }

    @PostMapping("/login-error")
    public ModelAndView onFailedLogin(
            RedirectAttributes redirectAttributes,
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username) {

        redirectAttributes.addFlashAttribute("bad_credentials", true);
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);

        return super.redirect("/auth/login");
    }
}


