package study.REST_API.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Date joinDate;



}
