package com.example.law.component;

import cn.dev33.satoken.stp.StpInterface;
import com.example.law.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    private UserRepository userRepository;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<>();
        list.add("101");
        list.add("user.add");
        list.add("user.update");
        list.add("user.get");
        // list.add("user.delete");
        list.add("art.*");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        userRepository.findById((String) loginId).ifPresent(user -> {
            switch (user.getRole()) {
                case ADMIN:
                    list.add("admin");
                    break;
                case STAFF:
                    list.add("staff");
                    break;
                case USER:
                    list.add("user");
                    break;
                default:
                    break;
            }
        });
        return list;
    }
}
