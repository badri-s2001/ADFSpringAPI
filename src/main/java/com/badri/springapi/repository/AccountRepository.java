package com.badri.springapi.repository;

import com.badri.springapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <Account, Long>{

}
