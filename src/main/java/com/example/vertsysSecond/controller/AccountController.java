package com.example.vertsysSecond.controller;


import com.example.vertsysSecond.models.Account;
import com.example.vertsysSecond.persistence.entity.AccountEntity;
import com.example.vertsysSecond.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin(origins ="*")
@Component
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accountValidation")
    public Long getAccount (@RequestBody Account user) {
        return accountService.getAccount(user).getId();
    }

    @GetMapping("/getProfileID/{userName}")
    public Long getProfileIdByUsername(@PathVariable String userName){
        return accountService.getProfileID(userName);
    }

    @GetMapping
    public boolean checkUsername(String username){
        return accountService.checkAccount(username);
    }

    //@Validate Annotation vor @RequestBody
    @PostMapping("/")
    public Long createAccount(@RequestBody Account user, BindingResult bindingResult) throws RuntimeException {
        return accountService.createAccount(user.getUserName(), user.getPassword());
    }

    @PutMapping("/{id}")
    public void updateAccount (@RequestBody Account user, @PathVariable Long id){
        accountService.updateAccount(id, user.getUserName(), user.getPassword());
    }

    @DeleteMapping ("/deleteEverythingByAccountID/{id}")
    public void deleteEverythingByID(@PathVariable Long id){
        accountService.deleteEverythingByAccountID(id);
    }

    @GetMapping ("/forgotPasswordCheckUser/{userName}/{email}")
    public boolean checkUserNameAndEmail (String userName, String email){
        return accountService.searchForAccount(userName, email);
    }

    @GetMapping ("/getAccountById/{id}")
    public Account getAccountById (@PathVariable Long id){
        return accountService.getAccountById(id);
    }


    @PostMapping("/newPassword/{userName}/{password}")
    public ResponseEntity<HttpStatus> newPassword (String userName,@RequestBody String password) {
        HttpStatus status = accountService.createNewPassword(userName, password);
        return new ResponseEntity<>(status);
    }
}
