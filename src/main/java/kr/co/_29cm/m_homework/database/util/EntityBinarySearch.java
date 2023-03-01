package kr.co._29cm.m_homework.database.util;

import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * Entity 리스트 탐색 시 시간복잡도를 O(n)에서 O(log n)으로 변경하기 위해서 사용되는 클래스
 * id를 기준으로 정렬된 엔티티 리스트만 사용 가능함.
 * </pre>
 */
public class EntityBinarySearch {
    public static final String ASC = "ASC";
    public static final String DSC = "DSC";

    public String sortDirection;


    private List<BaseEntity> list;

    public EntityBinarySearch(List<BaseEntity> list) {
        this.list = list;
        this.sortDirection = DSC;
    }
    public EntityBinarySearch(List<BaseEntity> list, String sortDirection) {
        this.list = list;
        this.sortDirection = sortDirection;
    }

    /**
     * 엔티티 리스트에서 이진탐색으로 검색한 후 검색된 엔티티의 index 를 반환한다.
     * @param id 검색할 엔티티의 아이디
     * @param low 검색 인텍스 시작점
     * @param high 검색 인텍스 끝점
     * @return 검색한 엔티티의 인덱스 값
     * @throws EntityNotFoundException
     */
    public int binarySearchOneTarget(String id, int low, int high) throws EntityNotFoundException {
        int mid;

        while(low <= high) {
            mid = (low + high) / 2;

            if(id.equals(list.get(mid).getId())) {
                return mid;
            } else if(getCondition(id, mid)) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        throw new EntityNotFoundException();
    }

    /**
     * 엔티티 리스트를 검색해서 복수의 원하는 엔티티들의 index 값을 리스트 형태로 반환해준다.
     * @param ids 검색할 엔티티 아이디 리스트
     * @return 검색된 엔티티 리스트의 index 값 리스트
     * @throws EntityNotFoundException
     */
    public List<Integer> binarySearchMultiTarget(List<String> ids)
            throws EntityNotFoundException {

        return ids
                .stream()
                .map(id -> binarySearchOneTarget(id, 0, list.size()-1))
                .collect(Collectors.toList());
    }

    /**
     * 이진탐색 시 데이터 정렬이 오름차순인지 내림차순인지에 따라 값 비교 조건이 달라져야 한다.
     * @param id 검색하는 아이디 값
     * @param mid 인덱스 중간 값
     * @return
     */
    private boolean getCondition(String id, int mid) {
        if(sortDirection.equals(DSC)) {
            return id.compareTo(list.get(mid).getId()) > 0;
        } else {
            return id.compareTo(list.get(mid).getId()) < 0;
        }
    }
}
