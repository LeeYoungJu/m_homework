package kr.co._29cm.m_homework.service.reader;

import java.util.List;

/**
 * 원본 데이터가 저장된 파일을 읽을 수 있는 함수 형태를 정의하는 인터페이스
 */
public interface BaseFileReader {

    /**
     * 데이터 저장 파일명을 받아서 읽은 후 List 형태로 반환한다.
     * @param fileName 파일명
     * @return 파일 전체 데이터
     */
    List<String[]> readFile(String fileName);
}
