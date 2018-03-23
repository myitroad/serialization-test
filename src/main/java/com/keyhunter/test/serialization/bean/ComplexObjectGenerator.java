package com.keyhunter.test.serialization.bean;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * @auther yingren
 * Created on 2017/2/24.
 */
public class ComplexObjectGenerator {

    private static final int LOOP_SIZE = 150;

    public static void main(String[] args) throws IOException, URISyntaxException {
        ComplexObjectGenerator generator = new ComplexObjectGenerator();
        generator.generateSourceFile();
    }

    /***
     * 使用反射机制，填充对象实例成员的代码
     * @return ComplexObject
     */
    public ComplexObject generate() {
        ComplexObject complexObject = new ComplexObject();
        try {
            Class clazz = ComplexObject.class;
            for (int i = 0; i < LOOP_SIZE; i++) {
                Method setJustAName = clazz.getDeclaredMethod("setJustAName" + i, String.class);
                setJustAName.setAccessible(true);
                setJustAName.invoke(complexObject, "This is just a name of sequence "+ i);
                Method setJustANumber = clazz.getDeclaredMethod("setJustANumber" + i, int.class);
                setJustANumber.setAccessible(true);
                setJustANumber.invoke(complexObject, Integer.valueOf(new Random().nextInt()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return complexObject;
    }

    /**
     * 生成复杂对象Java类的代码ComplexObject.java
     * @throws IOException
     * @throws URISyntaxException
     */
    public void generateSourceFile() throws IOException, URISyntaxException {
        String path = ComplexObjectGenerator.class.getClassLoader().getResource("").toString().replace("target/classes/", "/src/main/java/com/keyhunter/test/serialization/bean/ComplexObject.java");
        File file = new File(new URI(path));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(out);
        bufferedWriter.write("package com.keyhunter.test.serialization.bean;\n" +
                "\n" +
                "import java.io.Serializable;\n" +
                "\n" +
                "/**\n" +
                " * Created by yingren on 2016/12/7.\n" +
                " */\n" +
                "public class ComplexObject implements Serializable {\n" +
                "private static final long serialVersionUID = 1L;\n");
        for (int i = 0; i < LOOP_SIZE; i++) {
            bufferedWriter.write("private String justAName" + i + ";\n");
            bufferedWriter.write("private int justANumber" + i + ";\n");
        }
        for (int i = 0; i < LOOP_SIZE; i++) {
            bufferedWriter.write("public void setJustAName" + i + "(String justAName){\n" +
                    "   this.justAName" + i + "=justAName;\n}\n");
            bufferedWriter.write("public String getJustAName" + i + "(){\n" +
                    "   return this.justAName" + i + ";\n}\n ");
            bufferedWriter.write("public void setJustANumber" + i + "(int justANumber){\n" +
                    "   this.justANumber" + i + "=justANumber;\n}\n");
            bufferedWriter.write("public int getJustANumber" + i + "(){\n" +
                    "   return this.justANumber" + i + ";\n}\n ");
        }
        bufferedWriter.write("}");
        bufferedWriter.flush();
        bufferedWriter.close();

        fileOutputStream.close();
        out.close();
    }
}
