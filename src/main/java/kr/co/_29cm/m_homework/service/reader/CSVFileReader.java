package kr.co._29cm.m_homework.service.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <pre>
 * BaseFileReader 인터페이스를 구현한 클래스
 * CSV 파일의 데이터를 읽어서 리스트 형태로 반환한다.
 * </pre>
 */
@Component
@Primary
public class CSVFileReader implements BaseFileReader {
    @Override
    public List<String[]> readFile(String fileName) {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileName);
        InputStreamReader streamReader = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8);

        try {
            CSVReader csvReader = new CSVReader(streamReader);
            return csvReader.readAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

}
