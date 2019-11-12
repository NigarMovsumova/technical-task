package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.dto.AccountDto;
import az.technical.task.technicaltask.security.CustomerAuthentication;
import az.technical.task.technicaltask.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/accounts")
@Api("Account Controller")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @ApiOperation("Get all accounts list by customer id")
    @GetMapping
    public List<AccountDto> getAccounts(@RequestHeader("X-Auth-Token") String token,
                                        CustomerAuthentication customerAuthentication) {
        logger.debug("Get Accounts of customerId {} start", customerAuthentication.getPrincipal());
        return service.getAccounts(customerAuthentication.getPrincipal());
    }

    @ApiOperation("Get account information by accountId")
    @GetMapping("/{accountId}")
    public AccountDto getAccount(@RequestHeader("X-Auth-Token") String token,
                                 CustomerAuthentication customerAuthentication,
                                 @PathVariable(name = "accountId") String accountId) {
        logger.debug("Get customer of id {} start", customerAuthentication.getPrincipal());
        return service.getAccount(customerAuthentication.getPrincipal(), accountId);
    }

    @ApiOperation("Create a new account")
    @PostMapping
    public AccountDto createAccount(
            @RequestHeader("X-Auth-Token") String token,
            CustomerAuthentication customerAuthentication) {
        logger.debug("Create new account for customerId {} start", customerAuthentication.getPrincipal());
        return service.createAccount(customerAuthentication.getPrincipal());
    }

    @ApiOperation("Activate a created account")
    @PostMapping("/activate")
    public void activateAccount(
            @RequestHeader("X-Auth-Token") String token,
            CustomerAuthentication customerAuthentication,
            String accountId) {
        logger.debug("Activate account of accountId {} start", accountId);
        service.activateAccount(customerAuthentication, accountId);
        logger.debug("Activate account of accountId {} end", accountId);
    }

    @ApiOperation("Delete an existing account")
    @DeleteMapping
    public void deleteAccount(@RequestHeader("X-Auth-Token") String token,
                              CustomerAuthentication customerAuthentication,
                              String accountId) {
        logger.debug("Delete account of accountId {} start", accountId);
        service.deleteAccount(customerAuthentication.getPrincipal(), accountId);
        logger.debug("Delete account of accountId {} end", accountId);
    }

    @ApiOperation("Get accounts by email")
    @GetMapping("/by/email/{email}")
    public List<AccountDto> getAccountsByEmail(@RequestHeader("X-Auth-Token") String token,
                                               CustomerAuthentication customerAuthentication,
                                               @PathVariable(name = "email") String email){
        logger.debug("Get accounts of email {} by {} start", email, customerAuthentication.getPrincipal());
        return service.getAccounts(token, customerAuthentication, email);
    }
}