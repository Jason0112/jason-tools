package com.tools.message.business;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/7
 * @description :
 */
public class MyAuthenticator extends Authenticator {

    String userName=null;
    String password=null;

    public MyAuthenticator(){
    }
    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }
}
