package kr.co._29cm.m_homework.service.reader;

import java.util.List;

public interface BaseFileReader {
    List<String[]> readFile(String fileName);
}
