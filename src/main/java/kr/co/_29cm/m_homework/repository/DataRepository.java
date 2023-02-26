package kr.co._29cm.m_homework.repository;

import kr.co._29cm.m_homework.config.PropertyManager;
import kr.co._29cm.m_homework.database.DataTopic;
import kr.co._29cm.m_homework.database.DataVirtualStorage;
import kr.co._29cm.m_homework.database.exception.WrongDataFileExtRuntimeException;
import kr.co._29cm.m_homework.database.exception.IllegalTopicException;
import kr.co._29cm.m_homework.database.exception.NoDataException;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.entity.ProductColumn;
import kr.co._29cm.m_homework.service.reader.BaseFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataRepository {
    private final BaseFileReader baseFileReader;
    private final DataVirtualStorage dataVirtualStorage;
    private final PropertyManager propertyManager;

    private List<String> topicList;

    private String dataFileType;

    public void initData() {
        dataFileType = propertyManager.getProperty("data.file.ext");
        topicList = Arrays.asList(propertyManager.getProperty("data.topic.list").split(","));

        System.out.println("데이터 로딩 중...");

        if(dataFileType.equals("csv")) {
            loadDataFromCSV();
        } else {
            throw new WrongDataFileExtRuntimeException();
        }
    }

    private void loadDataFromCSV() {
        topicList.forEach(topic -> {
            String fileName = topic + "." + dataFileType;
            List<String[]> rows = baseFileReader.readFile(fileName);
            List<String> colNames = Arrays.stream(rows.get(0)).collect(Collectors.toList());

            if(topic.equals(DataTopic.PRODUCT)) {
                List<Product> products = rows.stream().skip(1).map(row -> Product.builder()
                        .id(row[ProductColumn.ID.ordinal()])
                        .name(row[ProductColumn.NAME.ordinal()])
                        .price(Integer.parseInt(row[ProductColumn.PRICE.ordinal()]))
                        .stockAmt(Integer.parseInt(row[ProductColumn.STOCK_AMT.ordinal()]))
                        .build()
                ).sorted(Comparator.comparing(Product::getId)).collect(Collectors.toList());

                dataVirtualStorage.setProductColNames(colNames);
                dataVirtualStorage.setProducts(products);
            }
        });
    }

    public List<String> getColNames(Class<?> targetClass) throws IllegalTopicException {
        if(targetClass == Product.class) {
            return dataVirtualStorage.getProductColNames();
        }

        throw new IllegalTopicException();
    }

    public <T> List<T> getAll(Class<T> targetClass) throws IllegalTopicException {
        if(targetClass == Product.class) {
            return (List<T>) dataVirtualStorage.getProducts();
        }

        throw new IllegalTopicException();
    }

    public <T> T getObjectById(Class<T> targetClass, String id) throws NoDataException, IllegalTopicException {
        if(targetClass == Product.class) {
            int targetIdx = Collections.binarySearch(
                    dataVirtualStorage.getProducts()
                    , Product.builder().id(id).build()
                    , Comparator.comparing(Product::getId)
            );

            if(targetIdx < -1) {
                throw new NoDataException();
            }

            return (T) dataVirtualStorage.getProducts().get(targetIdx);
        }

        throw new IllegalTopicException();
    }
}
