package com.example.pethospitalbackend.util;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.search.documents.SearchAsyncClient;
import com.azure.search.documents.SearchClient;
import com.azure.search.documents.indexes.SearchIndexAsyncClient;
import com.azure.search.documents.indexes.SearchIndexClient;
import com.azure.search.documents.indexes.SearchIndexClientBuilder;
import com.azure.search.documents.indexes.models.IndexDocumentsBatch;
import com.azure.search.documents.indexes.models.SearchIndex;
import com.azure.search.documents.indexes.models.SearchSuggester;
import com.example.pethospitalbackend.search.entity.SearchableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class SearchUtil {
    @Value("${SEARCH_ENDPOINT}")
    private String searchServiceEndpoint;

    @Value("${SEARCH_KEY}")
    private String searchServiceKey;

    private AzureKeyCredential adminKey;
    private SearchIndexAsyncClient searchIndexClient;

    private static volatile SearchUtil searchUtil;

    private SearchAsyncClient client;

    private SearchUtil() {
    }

    @PostConstruct
    public void init() {
        adminKey = new AzureKeyCredential(searchServiceKey);
        searchIndexClient = new SearchIndexClientBuilder()
                .endpoint(searchServiceEndpoint)
                .credential(adminKey)
                .buildAsyncClient();
        client = searchIndexClient.getSearchAsyncClient("searchable-entity");
    }


    public static SearchUtil getSearchUtil() {
        if (searchUtil == null) {
            synchronized (SearchUtil.class) {
                if (searchUtil == null) {
                    searchUtil = new SearchUtil();
                }
            }
        }
        return searchUtil;
    }

    public SearchAsyncClient getSearchClient(String index) {
        return searchIndexClient.getSearchAsyncClient(index);
    }

    public void upload(SearchableEntity searchableEntity) {
        IndexDocumentsBatch<SearchableEntity> batch = new IndexDocumentsBatch<>();
        batch.addMergeOrUploadActions(Collections.singletonList(searchableEntity));
        try
        {
            client.indexDocuments(batch).subscribe(
                    success -> {
                        System.out.println("success");
                },
                    error -> {
                        System.out.println("error");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(SearchableEntity searchableEntity) {
        try
        {
            client.deleteDocuments(Collections.singletonList(searchableEntity)).subscribe(
                    success -> {
                        System.out.println("success");
                        },
                    error -> {
                        System.out.println("error");
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
