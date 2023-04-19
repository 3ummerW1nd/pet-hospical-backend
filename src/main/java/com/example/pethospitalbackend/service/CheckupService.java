package com.example.pethospitalbackend.service;

import com.example.pethospitalbackend.domain.Checkup;
import com.example.pethospitalbackend.domain.page.CheckupPageInfo;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.repository.CheckupRepository;
import com.example.pethospitalbackend.repository.PetProfileRepository;
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
public class CheckupService {
    @Autowired
    private CheckupRepository checkupRepository;

    @Autowired
    private SearchUtil searchUtil;

    @Autowired
    private PetProfileRepository petProfileRepository;

    @Transactional
    public CommonResponse createOrUpdateCheckup(Integer id, String name, String introduction, Double price) {
        Checkup checkup = null;
        if (id != null) {
            Boolean exist = checkupRepository.existsById(id);
            if (!exist) {
                return CommonResponse.builder()
                        .code(1)
                        .message("id不存在，请检查")
                        .build();
            }
            checkup = Checkup.builder()
                    .id(id)
                    .name(name)
                    .introduction(introduction)
                    .price(price)
                    .build();
        } else {
            checkup = Checkup.builder()
                    .name(name)
                    .introduction(introduction)
                    .price(price)
                    .build();
        }
        checkupRepository.save(checkup);
        searchUtil.upload(SearchEntityConverter.getSearchableEntity(checkup));
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(checkup)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResponse deleteCheckupById(Integer id) {
        Optional<Checkup> optionalPersonnel = checkupRepository.findById(id);
        if (!optionalPersonnel.isPresent()) {
            return CommonResponse.builder()
                    .code(1)
                    .message("检查不存在，请检查id")
                    .build();
        }
        if (petProfileRepository.isExistCheckup(id) > 0) {
            return CommonResponse.builder()
                    .code(1)
                    .message("有真实病例引用，不可被删除")
                    .build();
        }
        Checkup checkup = optionalPersonnel.get();
        checkupRepository.deleteById(id);
        searchUtil.delete(SearchEntityConverter.getSearchableEntity(checkup));
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(checkup)
                .build();
    }

    public CommonResponse getAllCheckups(Integer offset, String content) {
        if (content.isEmpty()) {
            return getCheckups(offset);
        }
        return searchCheckups(offset, content);
    }

    private CommonResponse searchCheckups(Integer offset, String content) {
        List<Checkup> searchResult = null;
        try {
            Result result = searchUtil.search(content, "checkup", offset - 1).get();
            List<SearchableEntity> list = result.getSearchableEntityList();
            searchResult = new ArrayList<>(SearchEntityConverter.getCheckupFromSearchableEntity(list));
            CheckupPageInfo pageInfo = CheckupPageInfo.builder()
                .currentPage(offset)
                .totalPages((int) Math.ceil(result.getTotalCount().doubleValue() / 10.0))
                .checkups(searchResult)
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

    private CommonResponse getCheckups(Integer offset) {
        if (offset == 0) {
            List<Checkup> allCheckups = (List<Checkup>) checkupRepository.findAll();
            return CommonResponse.builder()
                    .code(0)
                    .message("success")
                    .result(allCheckups)
                    .build();
        }
        Integer count = checkupRepository.getPageCount(10);
        if (offset < 0 || offset > count) {
            return CommonResponse.builder()
                    .code(1)
                    .message("合法页号范围：(" + 1 + ", " + count + ").")
                    .build();
        }
        offset -= 1;
        List<Checkup> checkups = checkupRepository.findCheckups(10, offset * 10);
        CheckupPageInfo pageInfo = null;
        pageInfo = CheckupPageInfo.builder()
                .currentPage(offset + 1)
                .totalPages(count)
                .checkups(checkups)
                .build();
        return CommonResponse.builder()
                .code(0)
                .message("success")
                .result(pageInfo)
                .build();
    }
}
