package study.ajs_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.ajs_1.domain.item.Item;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/templates/validation/v1//////")
public class ValidationControllerV1 {

    @GetMapping("/form")
    public String addItem(){
        return "/form/addForm.html";
    }

    @PostMapping("/form")
    public String validItemV1(@ModelAttribute Item item, Model model) {
        Map<String, String> errors = new HashMap<>();

        if (!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.put("quantity", "수량은 최대 9,999개 까지 허용합니다.");

        }
//특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
            }
        }

        //검증 실패 시 다시 입력폼
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "templates/validation/v1/form";
        }

        //성공 로직
        return "redirect:index.html";
    }


}
