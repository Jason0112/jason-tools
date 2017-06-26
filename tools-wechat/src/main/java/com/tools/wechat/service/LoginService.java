package com.tools.wechat.service;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public interface LoginService {

    /**
     * 登陆
     *
     * @return 是否登录
     */
    boolean login();

    /**
     * 获取UUID
     *
     * @return uuid
     */
    String getUuid();

    /**
     * 获取二维码图片
     *
     * @return 是否
     */
    boolean getQR();

    /**
     * web初始化
     *
     * @return 是否初始化
     */
    boolean webWxInit();

    /**
     * 微信状态通知
     */
    void wxStatusNotify();


    /**
     * 获取微信联系人
     */
    void webWxGetContact();

    void startReceiving();
    /**
     * 退出登录
     */
    void logout();


}
