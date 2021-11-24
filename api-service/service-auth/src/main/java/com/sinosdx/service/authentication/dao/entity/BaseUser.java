package com.sinosdx.service.authentication.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wendy
 * @date 2020/11/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String account;
    private String mobile;
    private String email;
    private String clientId;
    private List<String> roles = new ArrayList<>();
}
