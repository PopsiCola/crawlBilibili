package com.llb.crawlcontent.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 生成文件工具类
 * @Author llb
 * Date on 2019/12/3
 */
public class FileUtils {

    public static void writeContent(String data) {
        BufferedWriter bw = null;

        File file = new File("content.txt");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            bw = new BufferedWriter(fileWriter);
            bw.write(data);
            bw.newLine();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw != null) {
                   bw.flush();
                   bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
