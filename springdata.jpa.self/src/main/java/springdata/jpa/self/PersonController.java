package springdata.jpa.self;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springdata.jpa.self.domain.Person;
import springdata.jpa.self.repository.PersonRepository;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {
    private final PersonRepository personRepository;

    @GetMapping("/count")
    public long getPersonCount(){
        return personRepository.count();
    }

    @GetMapping("/check")
    public boolean checkDupleName(@RequestParam String name) {
        return personRepository.existsByName(name);
    }
    @GetMapping("/all")
    public List<Person> listPagingAndSorting(){
       // return personRepository.findAll();
       return personRepository.findAll();


    }

        @GetMapping("/find")
        public Person findByNameasd(@RequestParam("firstname") String firstname,
                                    @RequestParam("lastname") String lastname ){
            log.info("firstname : {}, lastname : {}", firstname, lastname);
            Person findPerson = personRepository.findByLastnameOrFirstname(firstname, lastname);
            log.info("here {}", findPerson);
            return findPerson;
        }

    @GetMapping("/find/paging")
    public Window<Person> findPagingPerson(@RequestParam("lange") long offset,
                                           @RequestParam("lastname") String lastname) {
        log.info("offset : {}, lastname : {}", offset, lastname);
        Window<Person> result = personRepository.findFirst15ByLastnameOrderByFirstname(lastname, ScrollPosition.offset(offset));

        log.info("result {}", result);
        return result;
    }
    @GetMapping("/paging")
    public Window<Person> findPagingPerson132(@RequestParam("lange") long offset,
                                           @RequestParam("lastname") String lastname) {
        log.info("offset : {}, lastname : {}", offset, lastname);
        Window<Person> result = personRepository.findFirst15ByLastnameNotOrderByFirstname( lastname, ScrollPosition.offset(offset));

        log.info("result {}", result);
        return result;
    }
    }

