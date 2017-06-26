package com.tools.common.util;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author ：cheng.zhencai
 * @date ：2017/04/23
 * @description :文件工具类
 */

public class FileUtil {

    public static void copyFile(String sourceFile, String target) throws IOException {
        File targetFile = new File(target);
        targetFile.mkdirs();
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        copyFile(input, output);
    }

    /**
     * 复制文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        copyFile(input, output);
    }


    private static void copyFile(FileInputStream input, FileOutputStream output) throws IOException {
        BufferedInputStream inBuff = new BufferedInputStream(input);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();
        // 关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

    public static void deleteFile(File file) throws IOException {
        if (!file.isDirectory()) {
            file.delete();
        } else {
            throw new IOException("No permission to delete directory");
        }
    }

    public static void download(String urlString, String filename, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(10 * 1000);
        // 输入流
        InputStream is = con.getInputStream();
        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + File.separator + filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }


}