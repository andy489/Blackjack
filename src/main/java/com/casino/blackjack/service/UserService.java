package com.casino.blackjack.service;

import com.casino.blackjack.model.dto.UserRegistrationDTO;
import com.casino.blackjack.model.entity.RoleEntity;
import com.casino.blackjack.model.entity.UserEntity;
import com.casino.blackjack.model.enumerated.UserRoleEnum;
import com.casino.blackjack.model.event.UserRegisteredEvent;
import com.casino.blackjack.repo.RoleRepository;
import com.casino.blackjack.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final UserDetailsService userDetailsService;

    private final BlackjackUserDetailsService blackjackUserDetailsService;

    private final Boolean autoLogin;

    private final ApplicationEventPublisher appEventPublisher;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder encoder,
                       UserDetailsService userDetailsService,
                       @Value("${auth.register.auto-login}") Boolean autoLogin,
                       ApplicationEventPublisher appEventPublisher) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
        this.autoLogin = autoLogin;

        blackjackUserDetailsService = new BlackjackUserDetailsService(userRepository);
        this.appEventPublisher = appEventPublisher;
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerAndLogin(UserRegistrationDTO userRegistrationDTO,
                                 Locale locale,
                                 Consumer<Authentication> successfulLoginProcessor) {
        // START: map birthDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(userRegistrationDTO.getBirthDate(), formatter);

        OffsetDateTime odt = OffsetDateTime.now(ZoneId.systemDefault());
        ZoneOffset zoneOffset = odt.getOffset();

        Date date = Date.from(localDate.atStartOfDay().toInstant(zoneOffset));
        // EO: map birthDate

        // START: map roles
        RoleEntity regularRole = roleRepository.findByRole(UserRoleEnum.REGULAR)
                .orElseThrow(() -> new RuntimeException("Failed to register regular user"));
        // EO: map roles

        UserEntity newUser = new UserEntity()
                .setUsername(userRegistrationDTO.getUsername())
                .setPassword(encoder.encode(userRegistrationDTO.getPassword()))
                .setEmail(userRegistrationDTO.getEmail())
                .setBirthDate(date)
                .setRoles(Set.of(regularRole))
                .setFirstName(userRegistrationDTO.getFirstName())
                .setLastName(userRegistrationDTO.getLastName())
                .setIsActive(false)
                .setMyGame(null)
                .setMyWallet(null);

        newUser = userRepository.save(newUser);

        UserRegisteredEvent userRegisteredEvent = new UserRegisteredEvent(getClass().getName(),
                userRegistrationDTO.getEmail(), userRegistrationDTO.getFullName(), locale);

        appEventPublisher.publishEvent(userRegisteredEvent);

        if (autoLogin) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getUsername());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, // principal
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );

            successfulLoginProcessor.accept(authentication);
        }
    }

    public void createUserIfNotExist(String username, String email, String firstName, String lastName) {
        // Create manually a user in the database
        // password not necessary (random uuid)
        // make user change his password on first login

        Optional<UserEntity> userWithEmail = userRepository.findByEmail(email);
        if (userWithEmail.isEmpty()) {

            Optional<UserEntity> userWithUsername = userRepository.findByUsername(username);

            if (userWithUsername.isPresent()) {
                username = username + "_" + UUID.randomUUID();
            }

            UserEntity userEntity = new UserEntity()
                    .setUsername(username)
                    .setEmail(email)
                    .setPassword(UUID.randomUUID().toString())
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setIsActive(true);

            userRepository.save(userEntity);
        }
    }

    public Authentication login(String username) {
        UserDetails userDetails = blackjackUserDetailsService.loadUserByUsername(username);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }
}
