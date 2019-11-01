package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.AccountRequest;
import az.technical.task.technicaltask.model.dto.AccountDto;
import az.technical.task.technicaltask.security.UserAuthentication;
import az.technical.task.technicaltask.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class AccountController {

    private final AccountService service;

    @GetMapping("/by-customer")
    public List<AccountDto> getAccounts(@RequestHeader("X-Auth-Token") String token,
                                        UserAuthentication userAuthentication) {
        System.out.println(token);
        System.out.println("here"+userAuthentication.getPrincipal()+"here");
        return service.getAccounts(userAuthentication.getPrincipal());
    }

    @PostMapping("/filter")
    public List<AccountDto> getAccounts(
            @RequestHeader("X-Auth-Token") String token,
            @RequestBody AccountRequest accountRequest,
            UserAuthentication userAuthentication) {
        System.out.println(accountRequest.toString());
        System.out.println(userAuthentication.toString());
        return service.getAccounts(accountRequest, userAuthentication.getPrincipal());
    }

    @PostMapping("/new")
    public void createAccount(
            @RequestHeader("X-Auth-Token") String token,
            UserAuthentication userAuthentication) {
        System.out.println("entered");
        service.createAccount(userAuthentication.getPrincipal());
    }

    @PostMapping("/activate")
    public void activateAccount(
            @RequestHeader("X-Auth-Token") String token,
            UserAuthentication userAuthentication,
            String accountId) {
        service.activateAccount(userAuthentication, accountId);
    }

    @DeleteMapping
    public void deleteAccount(@RequestHeader("X-Auth-Token") String token,
                              AccountRequest accountRequest,
                              String accountId) {
        service.deleteAccount(accountRequest, accountId);
    }
}


