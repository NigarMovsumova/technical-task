package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.OperationRequest;
import az.technical.task.technicaltask.model.dto.OperationDto;
import az.technical.task.technicaltask.security.UserAuthentication;
import az.technical.task.technicaltask.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/accounts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
public class OperationController {

    private final OperationService service;

    @CrossOrigin
    @GetMapping("/{accountId}/operations")
    public List<OperationDto> getOperations(@RequestHeader("X-Auth-Token") String token,
                                            UserAuthentication userAuthentication,
                                            @PathVariable String accountId) {
        return service.getOperations(accountId, userAuthentication.getPrincipal());
    }

    @PostMapping("/filter-operations")
    public List<OperationDto> getOperations(
            @RequestHeader("X-Auth-Token") String token,
            UserAuthentication userAuthentication,
            @RequestParam String accountId,
            @RequestBody OperationRequest operationRequest) {
        return service.getOperations(operationRequest, accountId, userAuthentication.getPrincipal());
    }
}





