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
import com.example.pethospitalbackend.search.entity.SearchableEntityWithFK;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchUtil {
    @Value("${azure.search.endpoint}")
    private static String searchServiceEndpoint;

    @Value("${azure.search.key}")
    private static String searchServiceKey;

    private static AzureKeyCredential adminKey = new AzureKeyCredential(searchServiceKey);
    private static SearchIndexAsyncClient searchIndexClient = new SearchIndexClientBuilder()
            .endpoint(searchServiceEndpoint)
            .credential(adminKey)
            .buildAsyncClient();


    public static SearchAsyncClient getSearchClient(String index) {
        return searchIndexClient.getSearchAsyncClient(index);
    }

    public static < T > void uploadDocuments(List<T> data, SearchClient searchClient) {
        IndexDocumentsBatch<T> batch = new IndexDocumentsBatch<>();
        batch.addMergeOrUploadActions(data);
        try
        {
            searchClient.indexDocuments(batch);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
