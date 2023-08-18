package com.migusdn.snslogindemo.dto;

import com.migusdn.snslogindemo.model.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author : hyunwoopark
 * @version : 1.0.0
 * @package : SNSLoginDemo
 * @name : SessionUser
 * @date : 2023/08/17 10:23 PM
 * @modifyed : $
 **/
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}