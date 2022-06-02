package com.company.repository;

import com.company.entity.CardEntity;
import com.company.enums.StatusEnum;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.persistence.metamodel.SingularAttribute;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, String> {
    Optional<CardEntity> findByNumber(String number);
    @Transactional
    @Modifying
    @Query("update CardEntity set status = :status where id = :cid")
    int chengStatus(@Param("status") StatusEnum status,@Param("cid") String id);

    Optional<CardEntity> findByIdAndStatus(String id, StatusEnum active);



    List<CardEntity> findByClientIdAndStatus(String cid, StatusEnum active);

    List<CardEntity> findByPhoneAndStatus(String phone, StatusEnum active);

    @Transactional
    @Modifying
    @Query("update CardEntity set phone = :phone where id = :cid")
    int assignPhone(@Param("phone") String phone, @Param("cid") String cid);

    @Query("select c.balance from CardEntity as c where c.number=:number")
    public Optional<Long> getBalance(@Param("number") String number);

    @Transactional
    @Modifying
    @Query("update CardEntity set balance = balance-:amount where number = :number")
    int paymentMinus(@Param("amount") Long amount, @Param("number") String number);

    @Transactional
    @Modifying
    @Query("update CardEntity set balance = balance+:amount where id = :cid")
    int paymentPlus(@Param("amount") Long amount, @P("cid") String cid);

    List<CardEntity> findByStatus(StatusEnum status);
}