package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Medicine;
import com.example.pethospitalbackend.domain.page.MedicinePageInfo;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.MedicineRepository;
import com.example.pethospitalbackend.search.converter.SearchEntityConverter;
import com.example.pethospitalbackend.search.entity.Result;
import com.example.pethospitalbackend.search.entity.SearchableEntity;
import com.example.pethospitalbackend.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private SearchUtil searchUtil;

    @Transactional
    public CommonResponse createOrUpdateMedicine(Integer id, String name, String introduction, Double price, Integer quantity) {
        Medicine medicine = null;
        if (id != null) {
            Boolean exist = medicineRepository.existsById(id);
            if (!exist) {
                return CommonResponse.builder()
                        .code(1)
                        .message("id不存在，请检查")
                        .build();
            }
            medicine = Medicine.builder()
                    .id(id)
                    .name(name)
                    .introduction(introduction)
                    .price(price)
                    .quantity(quantity)
                    .build();
        } else {
            medicine = Medicine.builder()
                    .name(name)
                    .introduction(introduction)
                    .price(price)
                    .quantity(quantity)
                    .build();
        }
        searchUtil.upload(SearchEntityConverter.getSearchableEntity(medicine));
        medicineRepository.save(medicine);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(medicine)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse deleteMedicineById(Integer id) {
        Optional<Medicine> optionalPersonnel = medicineRepository.findById(id);
        if (!optionalPersonnel.isPresent()) {
            return CommonResponse.builder()
                    .code(1)
                    .message("药品不存在，请检查id")
                    .build();
        }
        Medicine medicine = optionalPersonnel.get();
        searchUtil.delete(SearchEntityConverter.getSearchableEntity(medicine));
        medicineRepository.deleteById(id);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(medicine)
                .build();
    }

    public CommonResponse getAllMedicines(Integer offset, String content) {
        if (content.isEmpty()) {
            return getMedicines(offset);
        }
        return searchMedicines(offset, content);
    }

    private CommonResponse searchMedicines(Integer offset, String content) {
        List<Medicine> searchResult = null;
        try {
            Result result = searchUtil.search(content, "user", offset - 1).get();
            List<SearchableEntity> list = result.getSearchableEntityList();
            searchResult = new ArrayList<>(SearchEntityConverter.getMedicineFromSearchableEntity(list));
            MedicinePageInfo pageInfo = MedicinePageInfo.builder()
                .currentPage(offset)
                .totalPages((int) Math.ceil(result.getTotalCount().doubleValue() / 10.0))
                .medicines(searchResult)
                .build();
            return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private CommonResponse getMedicines(Integer offset) {
        if (offset == 0) {
            List<Medicine> allMedicines = (List<Medicine>) medicineRepository.findAll();
            return CommonResponse.builder()
                    .code(0)
                    .message("success")
                    .result(allMedicines)
                    .build();
        }
        Integer count = medicineRepository.getPageCount(10);
        if (offset <= 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        List<Medicine> medicines = medicineRepository.findMedicines(10, offset * 10);
        MedicinePageInfo pageInfo = null;
        pageInfo = MedicinePageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .medicines(medicines)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }
}
