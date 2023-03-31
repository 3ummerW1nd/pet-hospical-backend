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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchUtil {
    private static String searchServiceEndpoint = "https://pet-hospital-search.search.windows.net";
    private static AzureKeyCredential adminKey = new AzureKeyCredential("MY86O28zbA4Ks7amUBwgzg1mKPtLoT7UidTSlyUmkxAzSeDV0Upo");
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
