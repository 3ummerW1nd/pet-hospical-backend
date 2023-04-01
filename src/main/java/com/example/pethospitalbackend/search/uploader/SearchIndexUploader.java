package com.example.pethospitalbackend.search.uploader;

import com.azure.search.documents.SearchAsyncClient;
import com.example.pethospitalbackend.domain.Checkup;
import com.example.pethospitalbackend.domain.department.Department;
import com.example.pethospitalbackend.domain.Medicine;
import com.example.pethospitalbackend.domain.Personnel;
import com.example.pethospitalbackend.domain.profile.Pet;
import com.example.pethospitalbackend.domain.user.User;
import com.example.pethospitalbackend.util.SearchUtil;

public class SearchIndexUploader {

    private static SearchAsyncClient client = SearchUtil.getSearchClient("searchable-entity");

    private static SearchAsyncClient fkClient = SearchUtil.getSearchClient("searchable-entity-with-fk");

    public static void uploadUser(User user) {

    }

    public static void uploadDepartment(Department department) {

    }

    public static void uploadPersonnel(Personnel personnel) {

    }

    public static void uploadMedicine(Medicine medicine) {

    }

    public static void uploadCheckup(Checkup checkup) {

    }

    public static void uploadPetProfile(Pet petProfile) {

    }
}
