package com.tools.wechat.controller;

import com.tools.wechat.bean.Core;
import com.tools.wechat.bean.Message;
import com.tools.wechat.bean.ResponseResult;
import com.tools.wechat.exception.WechatException;
import com.tools.wechat.service.LoginService;
import com.tools.wechat.service.MessageService;
import com.tools.wechat.util.FileUtil;
import com.tools.wechat.util.SleepUtil;
import com.tools.wechat.util.ToolsUtil;
import com.tools.wechat.util.WechatTools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
@Controller
@RequestMapping("/")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    LoginService loginService;
    @Autowired
    MessageService messageService;

    private static Core core = Core.getInstance();

    @RequestMapping("/login")
    public ResponseEntity login() throws WechatException {
        if (core.isAlive()) {
            logger.info("已登录");
            throw new WechatException("已登录");
        }
        while (true) {
            for (int count = 0; count < 10; count++) {
                logger.info("获取UUID");
                while (loginService.getUuid() == null) {
                    logger.info("1. 获取微信UUID");
                    while (loginService.getUuid() == null) {
                        logger.warn("1.1. 获取微信UUID失败，两秒后重新获取");
                        SleepUtil.sleep(2000);
                    }
                }
                logger.info("2. 获取登陆二维码图片");
                if (loginService.getQR()) {
                    break;
                } else if (count == 9) {
                    logger.error("2.2. 获取登陆二维码图片失败，系统退出");
                    throw new WechatException("获取登陆二维码图片失败,请重试");
                }
            }
            logger.info("3. 请扫描二维码图片，并在手机上确认");
            if (!core.isAlive()) {
                loginService.login();
                core.setAlive(true);
                logger.info(("登陆成功"));
                break;
            }
            logger.info("4. 登陆超时，请重新扫描二维码图片");
        }

        logger.info("5. 登陆成功，微信初始化");
        if (!loginService.webWxInit()) {
            logger.info("6. 微信初始化异常");
            throw new WechatException("微信初始化异常,请重试");
        }

        logger.info("6. 开启微信状态通知");
        loginService.wxStatusNotify();

        logger.info("6. 清除。。。。");
        ToolsUtil.clearScreen();
        logger.info("欢迎回来， {}", core.getNickName());

        logger.info("7. 获取联系人信息");
        loginService.webWxGetContact();
        loginService.startReceiving();

        logger.info("8. 缓存本次登陆好友相关消息");
        WechatTools.setUserInfo();
        System.setProperty("jsse.enableSNIExtension", "true");
        // 登陆成功后缓存本次登陆好友相关消息（NickName, UserName）
        return new ResponseEntity<>(new ResponseResult(), HttpStatus.OK);
    }


    @RequestMapping("/sendMessage")
    public ResponseEntity sendMessage(Message message) throws WechatException {

        if (StringUtils.isBlank(message.getContent())) {
            throw new WechatException("message不能为空");
        }
        if (message.getCode() == null) {
            throw new WechatException("code不能为空");
        }
        messageService.send(message);
        return new ResponseEntity<>(message.getTraceID(), HttpStatus.OK);
    }

    @RequestMapping("/sendFileMessage")
    public ResponseEntity sendFileMessage(Message message, MultipartFile multipartFile) throws WechatException {
        String path = FileUtil.uploadFile(multipartFile);
        message.setFilePath(path);
        if (message.getCode() == null) {
            throw new WechatException("message不能为空");
        }
        if (StringUtils.isBlank(message.getContent())) {
            throw new WechatException("code不能为空");
        }
        messageService.send(message);
        return new ResponseEntity<>(new ResponseResult(), HttpStatus.OK);
    }

    @RequestMapping("/logout")
    public ResponseEntity logout() {
        loginService.logout();
        return new ResponseEntity<>(new ResponseResult(), HttpStatus.OK);
    }

}
