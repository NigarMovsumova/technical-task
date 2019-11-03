package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.security.UserAuthentication;
import az.technical.task.technicaltask.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/transfers")
@Api("Transfer Controller")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @ApiOperation("get all own transfers to any accounts")
    @GetMapping("/own")
    public List<TransferDto> getAllOwnTransfers(@RequestHeader("X-Auth-Token") String token,
                                             UserAuthentication userAuthentication) {

        return transferService.getAllOwnTransfers(userAuthentication.getPrincipal());
    }

    @GetMapping("/sent")
    @ApiOperation("get own sent transfers")
    public List<TransferDto> getSentOwnTransfers(@RequestHeader("X-Auth-Token") String token,
                                              UserAuthentication userAuthentication) {
        return transferService.getSentOwnTransfers(userAuthentication.getPrincipal());
    }

    @GetMapping("/received")
    @ApiOperation("get own received payments")
    public List<TransferDto> getReceivedOwnPayments(@RequestHeader("X-Auth-Token") String token,
                                                    UserAuthentication userAuthentication){
        return transferService.getReceivedOwnTransfers(userAuthentication.getPrincipal());
    }

    @PostMapping
    @ApiOperation("make a transfer to own/other accounts")
    public void makeTransfer(@RequestHeader("X-Auth-Token") String token,
                             @RequestBody TransferDto transferDto,
                             UserAuthentication userAuthentication) {
        transferService.makeTransfer(userAuthentication, transferDto);
    }

}
