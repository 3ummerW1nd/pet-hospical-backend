package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Checkup;
import com.example.pethospitalbackend.domain.Medicine;
import com.example.pethospitalbackend.domain.PageInfo;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.CheckupRepository;
import com.example.pethospitalbackend.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CheckupService {
    @Autowired
    private CheckupRepository checkupRepository;


    public CommonResponse createOrUpdateCheckup(Integer id, String name, String introduction, Double price) {
        Checkup medicine = null;
        if (id != null) {
            Boolean exist = checkupRepository.existsById(id);
            if (!exist) {
                return CommonResponse.builder()
                        .code(1)
                        .message("id不存在，请检查")
                        .build();
            }
            medicine = Checkup.builder()
                    .id(id)
                    .name(name)
                    .introduction(introduction)
                    .price(price)
                    .build();
        } else {
            medicine = Checkup.builder()
                    .name(name)
                    .introduction(introduction)
                    .price(price)
                    .build();
        }
        checkupRepository.save(medicine);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(medicine)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse deleteCheckupById(Integer id) {
        Optional<Checkup> optionalPersonnel = checkupRepository.findById(id);
        if (!optionalPersonnel.isPresent()) {
            return CommonResponse.builder()
                    .code(1)
                    .message("药品不存在，请检查id")
                    .build();
        }
        Checkup checkup = optionalPersonnel.get();
        checkupRepository.deleteById(id);
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(checkup)
                .build();
    }

    public CommonResponse getAllCheckups(Integer offset, String content) {
        Integer count = checkupRepository.getPageCount(10);
        if (offset <= 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        List<Checkup> checkups = null;
        if (content == null || content.isEmpty()) {
            checkups = checkupRepository.findCheckups(10, offset * 10);
        } else {
            checkups = checkupRepository.findCheckups(10, offset * 10);
            //todo:搜索
        }
        PageInfo pageInfo = null;
        pageInfo = PageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .data(checkups)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }
}
