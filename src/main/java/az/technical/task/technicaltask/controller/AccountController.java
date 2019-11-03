package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.dto.AccountDto;
import az.technical.task.technicaltask.security.CustomerAuthentication;
import az.technical.task.technicaltask.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @ApiOperation("get all accounts list by customer id")
    @GetMapping("/by-customer")
    public List<AccountDto> getAccounts(@RequestHeader("X-Auth-Token") String token,
                                        CustomerAuthentication customerAuthentication) {
        return service.getAccounts(customerAuthentication.getPrincipal());
    }

    @ApiOperation("get account information by accountId")
    @GetMapping("/{accountId}")
    public AccountDto getAccount(@RequestHeader("X-Auth-Token") String token,
                                 CustomerAuthentication customerAuthentication,
                                 @PathVariable(name = "accountId") String accountId) {
        return service.getAccount(customerAuthentication.getPrincipal(), accountId);
    }

    @ApiOperation("create a new account")
    @PostMapping("/new")
    public AccountDto createAccount(
            @RequestHeader("X-Auth-Token") String token,
            CustomerAuthentication customerAuthentication) {
        return service.createAccount(customerAuthentication.getPrincipal());
    }

    @ApiOperation("activate a created account")
    @PostMapping("/activate")
    public void activateAccount(
            @RequestHeader("X-Auth-Token") String token,
            CustomerAuthentication customerAuthentication,
            String accountId) {
        service.activateAccount(customerAuthentication, accountId);
    }

    @ApiOperation("delete an existing account")
    @DeleteMapping
    public void deleteAccount(@RequestHeader("X-Auth-Token") String token,
                              CustomerAuthentication customerAuthentication,
                              String accountId) {
        service.deleteAccount(customerAuthentication.getPrincipal(), accountId);
    }

    @ApiOperation("get accounts by email")
    @GetMapping("/by/email/{email}")
    public List<AccountDto> getAccountsByEmail(@RequestHeader("X-Auth-Token") String token,
                                               CustomerAuthentication customerAuthentication,
                                               @PathVariable(name = "email") String email){
       return service.getAccounts(token, customerAuthentication, email);
    }
}


