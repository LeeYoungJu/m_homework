package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.exception.DataLoaderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataLoaderFactory {
    private final List<DataLoader> dataLoaders;

    public DataLoader getLoader(String fileExt) {
        Optional<DataLoader> opt = dataLoaders.stream()
                .filter(loader -> loader.getFileExt().equals(fileExt))
                .findFirst();

        if(opt.isEmpty()) {
            throw new DataLoaderNotFoundException();
        }

        return opt.get();
    }
}
