package com.tools.wechat.util;

import com.tools.wechat.exception.WechatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * date: 2017/6/12
 * description :
 *
 * @author : zhencai.cheng
 */
public final class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(File.class);
    private static final String TEMP_DIR = "/temp/";

    public static String uploadFile(MultipartFile file) throws WechatException {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = file.getInputStream();
            String sourceFile = file.getOriginalFilename();
            String targetFile = UUID.randomUUID().toString();
            String saveFileName = TEMP_DIR + targetFile + "." + sourceFile.substring(sourceFile.lastIndexOf(".") + 1);
            // 保存文件
            fos = new FileOutputStream(saveFileName);
            int byteCount;
            byte[] bytes = new byte[4096];
            while ((byteCount = is.read(bytes)) != -1) {
                fos.write(bytes, 0, byteCount);
            }
            return saveFileName;
        } catch (IOException e) {
            throw new WechatException("保存文件异常", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("关闭流异常:{}", e);
            }
        }
    }
}
