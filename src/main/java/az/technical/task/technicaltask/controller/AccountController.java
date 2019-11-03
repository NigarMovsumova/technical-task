package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.AccountRequest;
import az.technical.task.technicaltask.model.dto.AccountDto;
import az.technical.task.technicaltask.security.UserAuthentication;
import az.technical.task.technicaltask.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                                        UserAuthentication userAuthentication) {
        return service.getAccounts(userAuthentication.getPrincipal());
    }

    @ApiOperation("get account information by accountId")
    @GetMapping("/{accountId}")
    public AccountDto getAccount(@RequestHeader("X-Auth-Token") String token,
                                 UserAuthentication userAuthentication,
                                 @PathVariable(name = "accountId") String accountId) {
        return service.getAccount(userAuthentication.getPrincipal(), accountId);
    }

    @ApiOperation("filter accounts by different parameters")
    @PostMapping("/filter")
    public List<AccountDto> getAccounts(
            @RequestHeader("X-Auth-Token") String token,
            @RequestBody AccountRequest accountRequest,
            UserAuthentication userAuthentication) {
        return service.getAccounts(accountRequest, userAuthentication.getPrincipal());
    }

    @ApiOperation("create a new account")
    @PostMapping("/new")
    public AccountDto createAccount(
            @RequestHeader("X-Auth-Token") String token,
            UserAuthentication userAuthentication) {
        return service.createAccount(userAuthentication.getPrincipal());
    }

    @ApiOperation("activate a created account")
    @PostMapping("/activate")
    public void activateAccount(
            @RequestHeader("X-Auth-Token") String token,
            UserAuthentication userAuthentication,
            String accountId) {
        service.activateAccount(userAuthentication, accountId);
    }

    @ApiOperation("delete an existing account")
    @DeleteMapping
    public void deleteAccount(@RequestHeader("X-Auth-Token") String token,
                              UserAuthentication userAuthentication,
                              String accountId) {
        service.deleteAccount(userAuthentication.getPrincipal(), accountId);
    }

    @ApiOperation("get accounts by email")
    @GetMapping("/by/email/{email}")
    public List<AccountDto> getAccountsByEmail(@RequestHeader("X-Auth-Token") String token,
                                               UserAuthentication userAuthentication,
                                               @PathVariable(name = "email") String email){
       return service.getAccounts(token, userAuthentication, email);
    }
}


