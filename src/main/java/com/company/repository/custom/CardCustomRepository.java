package com.company.repository.custom;

import com.company.dto.request.CardFilterRequestDTO;
import com.company.entity.CardEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardCustomRepository {

    private final EntityManager entityManager;

    public List<CardEntity> filter(CardFilterRequestDTO filter){
        StringBuilder sql = new StringBuilder("SELECT  c FROM  CardEntity as c ");
        if (filter != null) {
            sql.append(" WHERE c.status = '" + filter.getStatus().name() + "'");
        } else {
            sql.append(" WHERE c.status = 'ACTIVE'");
        }

        if (filter.getCardId() != null) {
            sql.append(" AND  c.uuid = '" + filter.getCardId() + "'");
        }
        if (filter.getCardNumber() != null) {
            sql.append(" AND  c.number = '" + filter.getCardNumber()+"'");
        }

        if (filter.getFromBalance() != null && filter.getToBalance() != null) {
            sql.append(" AND  c.balance between " + filter.getFromBalance() + " AND " + filter.getToBalance());
        } else if (filter.getFromBalance() != null) {
            sql.append(" AND  c.balance > " + filter.getFromBalance());
        } else if (filter.getToBalance() != null) {
            sql.append(" AND  c.balance < " + filter.getToBalance());
        }

        if (filter.getProfileName() != null) {
            sql.append(" AND  c.profile_name = '" + filter.getProfileName() + "'");
        }

        Query query = entityManager.createQuery(sql.toString(), CardEntity.class);

        return query.getResultList();
    }
}
