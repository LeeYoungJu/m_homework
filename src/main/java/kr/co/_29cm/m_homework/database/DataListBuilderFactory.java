package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.exception.DataListBuilderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataListBuilderFactory {
    private final List<DataListBuilder> dataListBuilders;

    public DataListBuilder getBuilder(String topic) {
        Optional<DataListBuilder> opt = dataListBuilders.stream()
                .filter(builder -> builder.getTopic().equals(topic))
                .findFirst();

        if(opt.isEmpty()) {
            throw new DataListBuilderNotFoundException();
        }

        return opt.get();
    }
}
