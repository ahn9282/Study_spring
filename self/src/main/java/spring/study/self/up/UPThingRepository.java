package spring.study.self.up;

import org.springframework.stereotype.Repository;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UPThingRepository {

    private final Map<Long, UpThing> store = new HashMap<>();
    private long sequence = 0L;

    public UpThing save(UpThing upThing) {
        upThing.setId(++sequence);
        store.put(upThing.getId(), upThing);
        return upThing;
    }

    public UpThing findById(Long id) {
        return store.get(id);
    }
}
