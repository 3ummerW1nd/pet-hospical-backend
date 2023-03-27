package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Medicine;
import com.example.pethospitalbackend.domain.PageInfo;
import com.example.pethospitalbackend.domain.Personnel;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.MedicineRepository;
import com.example.pethospitalbackend.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;


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
        return null;
    }

    private CommonResponse getMedicines(Integer offset) {
        Integer count = medicineRepository.getPageCount(10);
        if (offset <= 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        List<Medicine> medicines = medicineRepository.findMedicines(10, offset * 10);
        PageInfo pageInfo = null;
        pageInfo = PageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .data(medicines)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }
}
