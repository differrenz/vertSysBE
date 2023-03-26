package com.example.vertsysSecond.service;

import com.example.vertsysSecond.models.Account;
import com.example.vertsysSecond.persistence.entity.AccountEntity;
import com.example.vertsysSecond.persistence.entity.ProfileEntity;
import com.example.vertsysSecond.persistence.repository.AccountRepository;
import com.example.vertsysSecond.persistence.repository.PictureRepository;
import com.example.vertsysSecond.persistence.repository.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;
    private final PictureRepository pictureRepository;

    public AccountService(AccountRepository accountRepository, ProfileRepository profileRepository,
                          PictureRepository pictureRepository) {
        this.accountRepository = accountRepository;
        this.profileRepository = profileRepository;
        this.pictureRepository = pictureRepository;
    }

    public boolean checkAccount(String username) {
        if (accountRepository.findByUserName(username) != null) {
            return true;
        } else {
            return false;
        }
    }

    public AccountEntity getAccount(Account user) {
        return accountRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
    }

    public Account getAccountById(Long id) {
         Optional<AccountEntity> tempAccountEntity = accountRepository.findById(id);
         Account tempAccount = new Account();
        if (tempAccountEntity.isPresent()) {
            tempAccount.setUserName(tempAccountEntity.get().getUserName());
            tempAccount.setPassword(tempAccountEntity.get().getPassword());
        }

        return tempAccount;
    }

    public Long createAccount(String name, String password) throws RuntimeException {
        AccountEntity account = new AccountEntity();
        if (checkAccount(name)) {
            throw new RuntimeException("User already exists!");
        }
        account.setUserName(name);
        account.setPassword(password);
        return accountRepository.save(account).getId();
    }


    public Long getProfileID(String userName) {
        ProfileEntity pe = profileRepository.findProfileEntityByAccount_UserName(userName);
        return pe.getId();
    }

    public void updateAccount(Long id, String username, String password) {
        AccountEntity updAccount = accountRepository.getById(id);
        if (username != null && !username.equals("string") && !username.equals("")) {
            updAccount.setUserName(username);
        }
        if (password != null && !password.equals("string") && !password.equals("")) {
            updAccount.setPassword(password);
        }
        accountRepository.save(updAccount);
    }

    @Transactional
    public void deleteEverythingByAccountID(Long id) {
        ProfileEntity profileEntity = profileRepository.findProfileEntityByAccount_Id(id);
        pictureRepository.deletePictureEntityByProfileEntity(profileEntity);
        profileRepository.deleteProfileEntityByAccount_Id(id);
        accountRepository.deleteAccountEntityById(id);
    }


    public boolean searchForAccount(String userName, String email) {
        try {
            AccountEntity ae = accountRepository.findByUserName(userName);
            Long AccountId = ae.getId();
            ProfileEntity em = profileRepository.findByEmailAndAccount_Id(email, AccountId);
            if (ae != null && em != null) {
                return (true);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public HttpStatus createNewPassword(String userName, String password) {
        try {
            AccountEntity ace = accountRepository.findByUserName(userName);
            ace.setPassword(password);
            accountRepository.save(ace);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.CONFLICT;
        }
    }
}
