package com.tools.redis.util;


import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/18
 * @description :
 */

public class SerializeUtil {

    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new RuntimeException("serialize object [" + obj
                    + "] exception");
        }
        byte[] bytes = null;
        LinkedBuffer buffer = LinkedBuffer
                .allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());

            bytes = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("serialize object [" + obj.getClass()
                    + "] error...", e);
        } finally {
            buffer.clear();
        }

        return bytes;
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param targetObj
     * @return
     */
    public static <T> T deserialize(byte[] bytes, Class<T> targetObj) {
        T instance = null;
        if (bytes != null && bytes.length > 0) {
            try {
                instance = targetObj.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("deserialize failed. ", e);
            }
            Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(targetObj);
            ProtostuffIOUtil.mergeFrom(bytes, instance, schema);
        }
        return instance;
    }

    /**
     * 序列化list
     *
     * @param list
     * @return
     */
    public static <T> byte[] serializeList(List<T> list) {
        if (list == null || list.size() == 0) {
            throw new RuntimeException("paramter [" + list + "] exception.");
        }
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(list.get(0)
                .getClass());
        LinkedBuffer buffer = LinkedBuffer
                .allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, list, schema, buffer);
            bytes = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("serialize object list [" + list
                    + "] error", e);
        } finally {
            buffer.clear();
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 反序列化List
     *
     * @param bytes
     * @param targetObj
     * @return
     */
    public static <T> List<T> deserializeList(byte[] bytes, Class<T> targetObj) {
        List<T> result = null;
        if (bytes != null && bytes.length > 0) {
            Schema<T> schema = RuntimeSchema.getSchema(targetObj);
            try {
                result = ProtostuffIOUtil.parseListFrom(
                        new ByteArrayInputStream(bytes), schema);
            } catch (Exception e) {
                throw new RuntimeException("deserialize object list error. ", e);
            }
        }
        return result;
    }

}