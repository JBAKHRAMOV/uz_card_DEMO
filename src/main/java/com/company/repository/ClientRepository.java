package com.company.repository;

import com.company.entity.ClientEntity;
import com.company.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {
    @Transactional
    @Modifying
    @Query("update ClientEntity set status = :status where id = :id")
    int changeStatus(@Param("status") StatusEnum status, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ClientEntity set phone = :phone where id = :id")
    int changePhone(@Param("phone") String phone, @Param("id") String id);

    @Transactional
    @Modifying
    @Query("update ClientEntity set name = :name,surname=:surname,middleName=:middlea where id = :id")
    int update(@Param("name") String name, @Param("surname") String surname, @Param("middlea") String middleName, @Param("id") String cid);

    Optional<ClientEntity> findByPhone(String phone);
}