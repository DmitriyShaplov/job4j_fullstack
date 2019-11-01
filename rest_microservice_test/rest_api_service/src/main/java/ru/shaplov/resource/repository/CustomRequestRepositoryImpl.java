package ru.shaplov.resource.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.shaplov.resource.domain.Request;
import ru.shaplov.resource.dto.RequestCriteria;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shaplov
 * @since 01.11.2019
 */
@Repository
public class CustomRequestRepositoryImpl implements CustomRequestRepository {

    @Autowired
    private EntityManager em;

    @Override
    public List<Request> findAllSorted(RequestCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Request> q = cb.createQuery(Request.class);
        Root<Request> root = q.from(Request.class);
        q.select(root);
        Predicate predicate = getRestrictions(cb, root, criteria);
        q.where(predicate);
        q = getSort(cb, root, q, criteria);
        return em.createQuery(q).getResultList();
    }

    private Predicate getRestrictions(CriteriaBuilder cb,
                                                   Root<Request> root,
                                                   RequestCriteria criteria) {
        Predicate predicate;
        List<Predicate> predicates = new ArrayList<>();
        if (criteria != null) {
            if (criteria.getDate() != null) {
                predicates.add(cb.between(root.get("created"),
                        criteria.getDate().atStartOfDay(),
                        criteria.getDate().plusDays(1).atStartOfDay()));
            }
            if (criteria.getService() != null) {
                predicates.add(cb.equal(root.get("serviceName"), criteria.getService()));
            }
            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }
            if (criteria.getSurname() != null) {
                predicates.add(cb.equal(root.get("passport").get("surname"), criteria.getSurname()));
            }
            if (criteria.getName() != null) {
                predicates.add(cb.equal(root.get("passport").get("name"), criteria.getName()));
            }
            if (criteria.getPatronymic() != null) {
                predicates.add(cb.equal(root.get("passport").get("patronymic"), criteria.getPatronymic()));
            }
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private CriteriaQuery<Request> getSort(CriteriaBuilder cb,
                         Root<Request> root,
                         CriteriaQuery<Request> q,
                         RequestCriteria criteria) {
        List<Order> orders = new ArrayList<>();
        if (criteria != null) {
            if (criteria.isDateSort()) {
                orders.add(cb.asc(root.get("created")));
            }
            if (criteria.isServiceSort()) {
                orders.add(cb.asc(root.get("serviceName")));
            }
            if (criteria.isStatusSort()) {
                orders.add(cb.asc(root.get("status")));
            }
            if (criteria.isSurnameSort()) {
                orders.add(cb.asc(root.get("passport").get("surname")));
            }
            if (criteria.isNameSort()) {
                orders.add(cb.asc(root.get("passport").get("name")));
            }
            if (criteria.isPatronymicSort()) {
                orders.add(cb.asc(root.get("patronymic").get("patronymic")));
            }
        }
        return q.orderBy(orders);
    }
}
