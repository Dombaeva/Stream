package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.dao.RoleDao;
import com.dmdev.jdbc.starter.dto.RoleFilter;
import com.dmdev.jdbc.starter.entity.Role;

import java.util.List;

public class DaoRunner {
    public static void main(String[] args) {
        var roleFilter = new RoleFilter(2,1);
        var roles = RoleDao.getInstance().findAll(roleFilter);
        System.out.println(roles);


    }

    private static void updateTest() {
        var roleDao = RoleDao.getInstance();
        var maybeRole = roleDao.findById(1);
        System.out.println(maybeRole);
        maybeRole.ifPresent(role -> {
            role.setName("user");
            roleDao.update(role);
        });
    }

    private static void saveTest() {
        var roleDao = RoleDao.getInstance();
        var role = new Role();
        role.setId(3);
        role.setName("admin");

        var savedRole = roleDao.save(role);
        System.out.println(savedRole);

    }

    private static void deleteTest() {
        var roleDao = RoleDao.getInstance();
        var delete = roleDao.delete(1);
        System.out.println(delete + " deleted.");
    }
}
