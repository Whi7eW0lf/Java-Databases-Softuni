package com.spring.spring.utill;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class FileUtilImpl implements FileUtil {

    @Override
    public String[] readFileContent(String path) {

        File file = new File(path);

        Set<String> result = new LinkedHashSet<>();

        try {
            BufferedReader read = new BufferedReader(new FileReader(file));


            String line = read.readLine();

            while (line!=null){

                if (!"".equals(line)){
                    result.add(line.trim());
                }

                line = read.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toArray(String[]::new);
    }
}
