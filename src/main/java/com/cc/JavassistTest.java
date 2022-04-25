package com.cc;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;

import java.io.IOException;

public class JavassistTest {

    public static void main(String[] args) throws CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();

        CtClass hello = classPool.makeClass("com.cc.Hello");
        hello.writeFile("./");
    }
}
