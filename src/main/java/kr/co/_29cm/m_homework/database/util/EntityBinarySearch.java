package kr.co._29cm.m_homework.database.util;

import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class EntityBinarySearch {
    private List<BaseEntity> list;
    public EntityBinarySearch(List<BaseEntity> list) {
        this.list = list;
    }

    public int binarySearchOneTarget(String id, int low, int high) throws EntityNotFoundException {
        int mid;

        while(low <= high) {
            mid = (low + high) / 2;

            if(id.equals(list.get(mid).getId())) {
                return mid;
            } else if(id.compareTo(list.get(mid).getId()) > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        throw new EntityNotFoundException();
    }

    public List<Integer> binarySearchMultiTarget(List<BaseEntity> list, List<String> ids)
            throws EntityNotFoundException {

        return ids
                .stream()
                .map(id -> binarySearchOneTarget(id, 0, list.size()-1))
                .collect(Collectors.toList());
    }
}
