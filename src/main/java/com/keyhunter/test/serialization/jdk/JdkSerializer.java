package com.keyhunter.test.serialization.jdk;

import com.keyhunter.test.serialization.Serializer;

import java.io.*;

/**
 * JDK自带的序列化
 * Created by yingren on 2016/12/2.
 */
public class JdkSerializer implements Serializer {

    @Override
    public <T> byte[] serialize(T object) {
        byte[] bytes = null;
        //使用Try-with-resources方式，使得try语句块中的代码执行完或者发生异常时，都会关闭圆括号中打开的资源
        //注意，需要资源实现java.lang.AutoCloseable接口，参见https://blog.csdn.net/wtopps/article/details/71108342
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            objectOutputStream.writeObject(object);
            bytes = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            Object object = objectInputStream.readObject();
            return (T) object;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
