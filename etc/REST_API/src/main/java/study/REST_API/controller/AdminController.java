package study.REST_API.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.REST_API.domain.AdminUser;
import study.REST_API.domain.AdminUserV2;
import study.REST_API.domain.User;
import study.REST_API.exception.UserNotFoundException;
import study.REST_API.service.UserDaoService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserDaoService service;

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieve4Admin(@PathVariable int id){
        User user = service.findOne(id);
        AdminUser adminUser = new AdminUser();
        if(user == null){
            throw new UserNotFoundException("id : " + id+" >> Not Exist");
        }else{
            BeanUtils.copyProperties(user, adminUser);
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn","password");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieve4AdminV1(@PathVariable int id){
        User user = service.findOne(id);
        AdminUser adminUser = new AdminUser();
        if(user == null){
            throw new UserNotFoundException("id : " + id+" >> Not Exist");
        }else{
            BeanUtils.copyProperties(user, adminUser);
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/v2/users/{id}")
    public MappingJacksonValue retrieve4AdminV2(@PathVariable int id){
        User user = service.findOne(id);
        AdminUserV2 adminUserV2 = new AdminUserV2();
        if(user == null){
            throw new UserNotFoundException("id : " + id+" >> Not Exist");
        }else{
            BeanUtils.copyProperties(user, adminUserV2);
            adminUserV2.setGrade("VIP");
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn","grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUserV2);
        mapping.setFilters(filters);
        return mapping;
    }


    @GetMapping(value="/users/{id}", params="version=1")
    public MappingJacksonValue retrieve4AdminParamV1(@PathVariable int id){
        User user = service.findOne(id);
        AdminUser adminUser = new AdminUser();
        if(user == null){
            throw new UserNotFoundException("id : " + id+" >> Not Exist");
        }else{
            BeanUtils.copyProperties(user, adminUser);
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping(value="/users/{id}", params="version=2")
    public MappingJacksonValue retrieve4AdminParamV2(@PathVariable int id){
        User user = service.findOne(id);
        AdminUserV2 adminUserV2 = new AdminUserV2();
        if(user == null){
            throw new UserNotFoundException("id : " + id+" >> Not Exist");
        }else{
            BeanUtils.copyProperties(user, adminUserV2);
            adminUserV2.setGrade("VIP");
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn","grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUserV2);
        mapping.setFilters(filters);
        return mapping;
    }


    @GetMapping(value="/users/{id}", produces="application/vnd.company.appv1+json")
    public MappingJacksonValue retrieve4AdminMimeV1(@PathVariable int id){
        User user = service.findOne(id);
        AdminUser adminUser = new AdminUser();
        if(user == null){
            throw new UserNotFoundException("id : " + id+" >> Not Exist");
        }else{
            BeanUtils.copyProperties(user, adminUser);
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping(value="/users/{id}", produces="application/vnd.company.appv2+json")
    public MappingJacksonValue retrieve4AdminMimeV2(@PathVariable int id){
        User user = service.findOne(id);
        AdminUserV2 adminUserV2 = new AdminUserV2();
        if(user == null){
            throw new UserNotFoundException("id : " + id+" >> Not Exist");
        }else{
            BeanUtils.copyProperties(user, adminUserV2);
            adminUserV2.setGrade("VIP");
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn","grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUserV2);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/users")
    public MappingJacksonValue adminReadAllUser(){
        List<User> all = service.findAll();
        List<AdminUser> users = new ArrayList<>();
        AdminUser adminUser = null;

        for (User user : all) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            users.add(adminUser);
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);
        return mapping;
    }
}
