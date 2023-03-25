package com.example.pethospitalbackend.util;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.search.documents.SearchClient;
import com.azure.search.documents.indexes.SearchIndexClient;
import com.azure.search.documents.indexes.SearchIndexClientBuilder;
import com.azure.search.documents.indexes.models.IndexDocumentsBatch;

import java.util.ArrayList;
import java.util.List;

public class SearchUtil {
    private static String searchServiceEndpoint = "https://pet-hospital-search.search.windows.net";
    private static AzureKeyCredential adminKey = new AzureKeyCredential("MY86O28zbA4Ks7amUBwgzg1mKPtLoT7UidTSlyUmkxAzSeDV0Upo");
    private static SearchIndexClient searchIndexClient = new SearchIndexClientBuilder()
            .endpoint(searchServiceEndpoint)
            .credential(adminKey)
            .buildClient();


    public static SearchClient getSearchClient(String index) {
        return searchIndexClient.getSearchClient(index);
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
