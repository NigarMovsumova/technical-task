package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.AccountRequest;
import az.technical.task.technicaltask.model.dto.AccountDto;
import az.technical.task.technicaltask.security.UserAuthentication;
import az.technical.task.technicaltask.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
@Api("Account Controller")
public class AccountController {

    private final AccountService service;

    @GetMapping("/by-customer")
    @ApiOperation("get all accounts list by customer id")
    public List<AccountDto> getAccounts(@RequestHeader("X-Auth-Token") String token,
                                        UserAuthentication userAuthentication) {
        return service.getAccounts(userAuthentication.getPrincipal());
    }

    @GetMapping("/{accountId}")
    @ApiOperation("get account information by accountId")
    public AccountDto getAccount(@RequestHeader("X-Auth-Token") String token,
                                 UserAuthentication userAuthentication,
                                 @PathVariable(name = "accountId") String accountId) {
        return service.getAccount(userAuthentication.getPrincipal(), accountId);
    }

    @PostMapping("/filter")
    @ApiOperation("filter accounts by different parameters")
    public List<AccountDto> getAccounts(
            @RequestHeader("X-Auth-Token") String token,
            @RequestBody AccountRequest accountRequest,
            UserAuthentication userAuthentication) {
        return service.getAccounts(accountRequest, userAuthentication.getPrincipal());
    }

    @PostMapping("/new")
    @ApiOperation("create a new account")
    public AccountDto createAccount(
            @RequestHeader("X-Auth-Token") String token,
            UserAuthentication userAuthentication) {
        return service.createAccount(userAuthentication.getPrincipal());
    }

    @PostMapping("/activate")
    @ApiOperation("activate a created account")
    public void activateAccount(
            @RequestHeader("X-Auth-Token") String token,
            UserAuthentication userAuthentication,
            String accountId) {
        service.activateAccount(userAuthentication, accountId);
    }

    @DeleteMapping
    @ApiOperation("delete an existing account")
    public void deleteAccount(@RequestHeader("X-Auth-Token") String token,
                              UserAuthentication userAuthentication,
                              String accountId) {
        service.deleteAccount(userAuthentication.getPrincipal(), accountId);
    }
}


