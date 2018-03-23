package com.keyhunter.test.serialization.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.keyhunter.test.serialization.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @auther yingren
 * Created on 2017/2/23.
 */
public class KryoSerializer implements Serializer {

    private Kryo kryo = new Kryo();

    @Override
    public <T> byte[] serialize(T object) {
        //使用Try-with-resources方式，使得try语句块中的代码执行完或者发生异常时，都会关闭圆括号中打开的资源
        //注意，需要资源实现java.lang.AutoCloseable接口，参见https://blog.csdn.net/wtopps/article/details/71108342
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Output output = new Output(outputStream)) {
            kryo.writeObject(output, object);
            output.flush();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream);) {
            return kryo.readObject(input, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
