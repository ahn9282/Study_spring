package spring.studysecurity.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {
    //USER DETAILS 구현체
    //member테이블과 authority테이블이 참조로 분리되었기에
    // 이를 합친 전송객체 UserDTO(MemberDTO + AuthDTO's auth)
   private UserDTO user;

//생성자 주입
    public CustomUserDetails(UserDTO user) {
        this.user = user;
    }

//권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        //해당 메서드는 Collection으로 반환하기에 Collection객체를 생성하고 권한을 넣아야함

        collection.add(new GrantedAuthority() {
            //GrantedAuthority 해당 인터페이스는 구현해줘야함
            //해당 오버라이딩 메서드를 통해 권한을 반환 시켜야함
            //그래서 본인처럼 테이블 2개로 권한을 분리한 경우
            //두 테이블의 컬럼값을 결합한 별도의 UserDTO를 만들어 처리
            @Override
            public String getAuthority() {
                return user.getAuth();
                //이를 통해 해당 Collection 객체에 권한이 추가
            }
        });
        return collection;
    }

    long getId(){
        return user.getId();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
