package com.spring.fileReaders.impl;

import com.spring.fileReaders.interfaces.FileInputReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileInputReaderImpl implements FileInputReader {

    @Override
    public List<String> readFile(String path) {

        try {

            return Files.readAllLines(Path.of(path))
                    .stream()
                    .filter(e->!e.isEmpty())
                    .collect(Collectors.toList());

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }
}
