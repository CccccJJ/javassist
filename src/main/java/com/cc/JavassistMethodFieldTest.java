package com.cc;

import javassist.*;

import java.io.IOException;
import java.net.URL;

public class JavassistMethodFieldTest {

    public static void main(String[] args) throws CannotCompileException, IOException {
        URL resource = JavassistMethodFieldTest.class.getClassLoader().getResource("");
        String file = resource.getFile();
        System.out.println("文件存储路径： " + file);

        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("com.cc.Hello");

        // 创建一个方法名为 hello，传递参数为顺序为(int, double)，没有返回值的方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "hello", new CtClass[]{CtClass.intType, CtClass.doubleType}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctClass.addMethod(ctMethod);

        // 添加一个 int 类型，名字为 value 的变量
        CtField ctField = new CtField(CtClass.intType, "value", ctClass);
        ctField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(ctField);

        // 为 value 变量添加 set 方法
        CtMethod setValue = new CtMethod(CtClass.voidType, "setValue", new CtClass[]{CtClass.intType}, ctClass);
        setValue.setModifiers(Modifier.PUBLIC);
        setValue.setBody("this.value = $1;");
        ctClass.addMethod(setValue);

        // 为 value 变量添加 get 方法
        CtMethod getValue = new CtMethod(CtClass.intType, "getValue", new CtClass[]{}, ctClass);
        getValue.setModifiers(Modifier.PUBLIC);
        getValue.setBody("return this.value;");
        ctClass.addMethod(getValue);

        CtMethod ctMethod1 = new CtMethod(CtClass.voidType, "hello1", new CtClass[]{CtClass.intType, CtClass.doubleType}, ctClass);
        ctMethod1.setModifiers(Modifier.PUBLIC);
        ctMethod1.setBody("this.value = $1 + $2;");
        ctMethod1.insertBefore("System.out.println(\"insert before\");");
        ctMethod1.insertAfter("System.out.println(\"insert after\");");
        ctClass.addMethod(ctMethod1);

        ctClass.writeFile(file);
    }
}
