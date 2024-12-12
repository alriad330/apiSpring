package com.example.products.controller;

import com.example.products.dto.AccountDto;
import com.example.products.dto.TokenDto;
import com.example.products.exceptionHandler.AccountAlreadyExistException;
import com.example.products.exceptionHandler.ErrorResponse;
import com.example.products.exceptionHandler.TokenNotValidException;
import com.example.products.models.Account;
import com.example.products.services.AccountService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;

@RestController
public class AccountController {

    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Create Account
     * @param accountDto
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = Account.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping("/account")
    public ResponseEntity<Object> saveAccount(@Valid @RequestBody AccountDto accountDto){

        var bCryptEncoder = new BCryptPasswordEncoder();

        Account account = new Account();
        account.setFirstname(accountDto.getFirstname());
        account.setUsername(accountDto.getUsername());
        account.setEmail(accountDto.getEmail());
        account.setPassword(bCryptEncoder.encode(accountDto.getPassword()));

        try {
            var otherAccount = accountService.getAccountByUserName(accountDto.getUsername());
            if (otherAccount !=  null) {
                return ResponseEntity.badRequest().body("Username already used");
            }

            otherAccount = accountService.getAccountByEmail(accountDto.getEmail());
            if (otherAccount !=  null) {
                return ResponseEntity.badRequest().body("Email already used");
            }

            accountService.saveAccount(account);

            String jwtToken = createJwtToken(account);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("account", account);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new AccountAlreadyExistException("Account Already Exist");
        }
    }

    /**
     * Allow to connect the Application
     * @param tokenDto
     * @return
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = TokenDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping("/token")
    public ResponseEntity<Object> createToken(@Valid @RequestBody TokenDto tokenDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            tokenDto.getEmail(),
                            tokenDto.getPassword()
                    )
            );

            Account account = accountService.getAccountByEmail(tokenDto.getEmail());

            String jwtToken = createJwtToken(account);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("account", account);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new TokenNotValidException("Bad Username or Password");
        }

    }

    /**
     * To test authentication restriction using token
     * @param authentication
     * @return
     */
    @GetMapping("/session")
    public ResponseEntity<Object> session(Authentication authentication) {
        var response = new HashMap<String, Object>();
        response.put("Username", authentication.getName());

        var account = accountService.getAccountByUserName(authentication.getName());
        response.put("Account", account);

        return ResponseEntity.ok(response);

    }


    private String createJwtToken(Account account) {
        Instant now = Instant.now();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24*3600))
                .subject(account.getUsername())
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claimsSet);

        return encoder.encode(params).getTokenValue();
    }
}
