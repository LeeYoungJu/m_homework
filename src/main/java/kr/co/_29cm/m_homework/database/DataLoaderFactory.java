package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.config.PropertyManager;
import kr.co._29cm.m_homework.database.consts.DataFileExt;
import kr.co._29cm.m_homework.exception.DataLoaderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * DataLoader 인터페이스를 구현한 클래스를 모두 주입받아서
 * 그 중 설정파일에 저장된 파일확장자를 다루는 클래스를 찾아서 반환해주는 클래스
 */
@Component
@RequiredArgsConstructor
public class DataLoaderFactory {
    private final List<DataLoader> dataLoaders;
    private final PropertyManager propertyManager;

    /**
     * 설정파일에 저장된 파일 확장자를 읽어서 이 확장자를 다루는 DataLoader 클래스를 찾아서 반환한다.
     * @return DataLoader 인터페이스를 구현한 클래스
     */
    public DataLoader getLoader() {
        String fileExt = propertyManager.getProperty("data.file.ext", DataFileExt.CSV);

        Optional<DataLoader> opt = dataLoaders.stream()
                .filter(loader -> loader.getFileExt().equals(fileExt))
                .findFirst();

        if(opt.isEmpty()) {
            throw new DataLoaderNotFoundException();
        }

        return opt.get();
    }
}
