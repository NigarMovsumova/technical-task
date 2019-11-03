package az.technical.task.technicaltask.utils;

import az.technical.task.technicaltask.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountUtil {
    private final AccountRepository accountRepository;

    public AccountUtil(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String generateAccountId() {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(16);

        for (int i = 0; i < 16; i++) {
            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }

        if (accountRepository.getAllAccountIds().contains(sb.toString())) {
            generateAccountId();
        }
        return sb.toString();
    }
}
