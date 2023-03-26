package com.example.vertsysSecond.persistence.repository;


import com.example.vertsysSecond.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByUserName(String username);

    void deleteAccountEntityById(Long id);

    AccountEntity findByUserNameAndPassword(String username, String password);


}
