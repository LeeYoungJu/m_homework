package kr.co._29cm.m_homework.database;

/**
 * 특정 파일에 있는 데이터를 메모리 저장소에 올리려면 해당 인터페이스를 구현해야 한다.
 * 앱이 시작할 때(ApplicationRunner) 이 인터페이스를 이용해서 데이터를 메모리에 올린다.
 */
public interface DataLoader {
    /**
     * 원본 데이터가 저장된 파일의 확장자 반환
     * @return 파일 확장자
     */
    String getFileExt();

    /**
     * 파일에 저장된 데이터를 메모리 저장소에 올리는 함수
     */
    void loadDataOnMemory();
}
