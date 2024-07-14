package study.ajs_1.domain.item;

import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ItemTestValidation {

    @Test
    void beanValidation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator  =factory.getValidator();

        Item item = new Item();
        item.setItemName("   ");
        item.setPrice(0);
        item.setQuantity(1000000);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        for (ConstraintViolation<Item> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation's message = " + violation.getMessage());
        }

    }

}