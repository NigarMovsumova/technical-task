package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.security.UserAuthentication;
import az.technical.task.technicaltask.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/transfers")
public class TransferController {

    private TransferService transferService;

    @PostMapping("")
    public List<TransferDto> getAllTransfers(@RequestHeader("X-Auth-Token") String token,
                                             UserAuthentication userAuthentication){
        return transferService.getAllTransfers(userAuthentication.getDetails().getCustomerId());
    }
}
