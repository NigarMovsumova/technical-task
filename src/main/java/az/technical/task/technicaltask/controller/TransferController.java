package az.technical.task.technicaltask.controller;

import az.technical.task.technicaltask.model.dto.TransferDto;
import az.technical.task.technicaltask.security.CustomerAuthentication;
import az.technical.task.technicaltask.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    @ApiOperation("Get all own transfers to any accounts")
    @GetMapping("/own")
    public List<TransferDto> getAllOwnTransfers(@RequestHeader("X-Auth-Token") String token,
                                             CustomerAuthentication customerAuthentication) {
        logger.debug("Get own transfers of customerId {} start", customerAuthentication.getPrincipal());
        return transferService.getAllOwnTransfers(customerAuthentication.getPrincipal());
    }

    @ApiOperation("Get own sent transfers")
    @GetMapping("/sent")
    public List<TransferDto> getSentOwnTransfers(@RequestHeader("X-Auth-Token") String token,
                                              CustomerAuthentication customerAuthentication) {
        logger.debug("Get own sent transfers of customerId {} start", customerAuthentication.getPrincipal());
        return transferService.getSentOwnTransfers(customerAuthentication.getPrincipal());
    }

    @GetMapping("/received")
    @ApiOperation("Get own received payments")
    public List<TransferDto> getReceivedOwnPayments(@RequestHeader("X-Auth-Token") String token,
                                                    CustomerAuthentication customerAuthentication){
        logger.debug("Get own received transfers of customerId {} start", customerAuthentication.getPrincipal());
        return transferService.getReceivedOwnTransfers(customerAuthentication.getPrincipal());
    }

    @ApiOperation("Make a transfer to own/other accounts")
    @PostMapping
    public void makeTransfer(@RequestHeader("X-Auth-Token") String token,
                             @RequestBody TransferDto transferDto,
                             CustomerAuthentication customerAuthentication) {
        logger.debug("Make a transfer of customerId {} start", customerAuthentication.getPrincipal());
        transferService.makeTransfer(customerAuthentication, transferDto);
        logger.debug("Make a transfer of customerId {} end", customerAuthentication.getPrincipal());
    }
}
