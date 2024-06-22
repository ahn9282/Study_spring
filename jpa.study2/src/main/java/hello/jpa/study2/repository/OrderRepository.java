package hello.jpa.study2.repository;

import hello.jpa.study2.domain.Order;
import hello.jpa.study2.repository.OrderSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }


    public List<Order> findAll(OrderSearch orderSearch) {

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and ";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and ";
            }
            jpql += "m.name like :name";
        }


        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);//최대 1000건

        //파라미터 바인딩
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    //JPA Criteria
    public List<Order> findByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();
        if (orderSearch.getOrderStatus() != null) {
           Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);

        return query.getResultList();
    }
    public List<Order> findAllWithMemberDelivery(){
       return  em.createQuery(
               "select o from Order o join fetch o.member m join fetch o.delivery d",
                       Order.class)
               .setFirstResult(1)
               .setMaxResults(1) //컬렉션 조회에서 member, delivery 모두 ~ToOne 관계라 페이징이 문제업이 되긴한다.
                .getResultList();
    }
    //오버로딩한 페이징 메서드
    public List<Order> findAllWithMemberDelivery(int offset, int limit){
       return  em.createQuery(
               "select o from Order o join fetch o.member m join fetch o.delivery d",
                       Order.class)
               .setFirstResult(offset)
               .setMaxResults(limit) //컬렉션 조회에서 member, delivery 모두 ~ToOne 관계라 페이징이 문제업이 되긴한다.
                .getResultList();
    }


    public List<Order> findAllWithItem() {
        return em.createQuery(
                        "select distinct o from Order o " +
                                " join fetch o.member m " +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i ", Order.class)
                .setFirstResult(1)//컬렉션 조회 페이징의 경우 ~ToOne 관계는 그냥 fetch join 해도됨 ToMany 관계에서 일반적인 페이징이 불가능함
                .setMaxResults(10)
                .getResultList();
    }
}
