package springdata.jpa.self.repository;

import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springdata.jpa.self.domain.Person;

@Repository
@EnableJpaRepositories
public interface PersonRepository extends JpaRepository<Person, Long> {


    boolean existsByName(String name);

    @Query("select p from Person p where p.firstname = :firstname or p.lastname = :lastname")
    Person findByLastnameOrFirstname( @Param("firstname") String firstname, @Param("lastname") String lastname);
    Window<Person> findFirst15ByLastnameOrderByFirstname(String lastname, OffsetScrollPosition position);
    Window<Person> findFirst15ByLastnameNotOrderByFirstname(String lastname, OffsetScrollPosition position);
}
