package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.security.UserAuthentication;
import az.technical.task.technicaltask.service.TransferService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/transfers")
@Api("Transfer Controller")
public class TransferController {

    private TransferService transferService;

    @GetMapping("/all")
    public List<TransferDto> getOwnTransfers(@RequestHeader("X-Auth-Token") String token,
                                             UserAuthentication userAuthentication) {
        return transferService.getOwnTransfers(userAuthentication.getDetails().getCustomerId());
    }

    @GetMapping("/own")
    public List<TransferDto> getSentTransfers(@RequestHeader("X-Auth-Token") String token,
                                             UserAuthentication userAuthentication){
        return transferService.getSentTransfers(userAuthentication.getDetails().getCustomerId());
    }

    @PostMapping
    public void makeTransfer(@RequestHeader("X-Auth-Token") String token,
                             @RequestBody TransferDto transferDto,
                             UserAuthentication userAuthentication) {
        transferService.makeTransfer(userAuthentication, transferDto);
    }

}
