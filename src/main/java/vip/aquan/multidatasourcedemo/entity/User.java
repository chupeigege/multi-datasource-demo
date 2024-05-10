package vip.aquan.multidatasourcedemo.entity;

import lombok.Data;

/**
 * @date 2024/4/29 17:32
 */

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String phone;
}
