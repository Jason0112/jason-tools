package com.tools.wechat.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author : zhencai.cheng
 * date : 2017/4/27
 * description :写文件
 */
public class WriterFile {

    public static final String UTF_8 = "UTF-8";

    private BufferedWriter bufferedWriter = null;

    private String path;

    public WriterFile(String path) {
        this.path = path;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});//写bom避免microsoft excel打开乱码
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, UTF_8));//避免乱码
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writer(String content) throws IOException {
        bufferedWriter.write(content);
    }

    public void close(){
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
