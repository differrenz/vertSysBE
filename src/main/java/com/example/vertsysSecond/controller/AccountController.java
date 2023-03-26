package com.example.vertsysSecond.controller;


import com.example.vertsysSecond.models.Account;
import com.example.vertsysSecond.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins ="*")
@Component
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "validateAccount", description = "validates the users input and checks, if his credentials are valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Input is valid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Input is not valid", content = @Content),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content)
    })
    @PostMapping("/accountValidation")
    public Long getAccount (@RequestBody Account user) {
        try{
            return accountService.getAccount(user).getId();
        } catch (Exception e){
            return 0L;
        }
    }

    @Operation(summary = "getProfileID", description = "uses the username to find the mathing id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "id was found", content = @Content),
            @ApiResponse(responseCode = "404", description = "id not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content)
    })

    @GetMapping("/getProfileID/{userName}")
    public Long getProfileIdByUsername(@PathVariable String userName){
        return accountService.getProfileID(userName);
    }

    @Operation(summary = "checkUsername", description = "validates the users input and checks if his username is valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Username is valid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Username is not valid", content = @Content),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content)
    })

    @GetMapping
    public boolean checkUsername(String username){
        return accountService.checkAccount(username);
    }

    //@Validate Annotation vor @RequestBody
    @Operation(summary = "createAccount", description = "creates account with entered username and password, if they are valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account was created", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account was not created", content = @Content),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content)
    })
    @PostMapping("/")
    public Long createAccount(@RequestBody Account user) throws RuntimeException {
        return accountService.createAccount(user.getUserName(), user.getPassword());
    }

    @Operation(summary = "updateAccount", description = "updates account information based on account's id, if input is valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account was updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Account could not be updated", content = @Content),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content)
    })

    @PutMapping("/{id}")
    public void updateAccount (@RequestBody Account user, @PathVariable Long id){
        accountService.updateAccount(id, user.getUserName(), user.getPassword());
    }

    @Operation(summary = "deleteAccount", description = "deletes an account based on it's id, if valid id was entered")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account was deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Could not delete account", content = @Content),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content)
    })

    @DeleteMapping ("/deleteEverythingByAccountID/{id}")
    public void deleteEverythingByID(@PathVariable Long id){
        accountService.deleteEverythingByAccountID(id);
    }


    @Operation(summary = "getAccount", description = "returns account information based on entered id, if id is valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returned account", content = @Content),
            @ApiResponse(responseCode = "404", description = "Could not return account", content = @Content),
            @ApiResponse(responseCode = "500", description = "internal error", content = @Content)
    })

    @GetMapping ("/getAccountById/{id}")
    public Account getAccountById (@PathVariable Long id){
        return accountService.getAccountById(id);
    }
}