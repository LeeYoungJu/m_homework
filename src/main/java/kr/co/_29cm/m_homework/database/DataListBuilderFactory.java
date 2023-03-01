package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.exception.DataListBuilderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * DataListBuilder 인터페이스를 구현한 클래스들을 모두 주입받아서
 * 그 중 특정 토픽의 데이터를 메모리 저장소에 올리는 클래스를 찾아서 반환해준다.
 */
@Component
@RequiredArgsConstructor
public class DataListBuilderFactory {
    private final List<DataListBuilder> dataListBuilders;

    /**
     * 특정 토픽의 데이터를 메모리 저장소에 올리는 빌더를 찾아서 반환한다.
     * @param topic 토픽 이름
     * @return DataListBuilder 인터페이스를 구현한 클래스
     */
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
