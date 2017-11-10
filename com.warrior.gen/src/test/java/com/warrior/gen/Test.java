package com.warrior.gen;

import com.google.gson.Gson;
import com.warrior.gen.model.Config;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class Test {

    public static void main(String args []) throws Exception{
        String json = FileUtils.readFileToString(new File(Test.class.getClassLoader().getResource("config.json").getFile()),"UTF-8");
        Config config = new Gson().fromJson(json,Config.class);
        CodeGen codeGen = new CodeGen();
        codeGen.genCode(config);

    }
}
