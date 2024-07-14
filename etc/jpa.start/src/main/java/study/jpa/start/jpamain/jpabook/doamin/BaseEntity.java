package study.jpa.start.jpamain.jpabook.doamin;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass//공통 적용 부분에서는 해당 MappedSuperClass 활용하여 적용할 수 있다. 그리고 적용하고자 하는 Entity가 상속하면 적용됨.
public abstract class BaseEntity {

    @Column(name="INSERT_MEMBER")
    private String createBy;
    private LocalDateTime createdDate;
    @Column(name="UPDATE_MEMBER")
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
