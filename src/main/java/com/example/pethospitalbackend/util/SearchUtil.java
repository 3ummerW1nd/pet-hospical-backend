package com.example.pethospitalbackend.util;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.rest.PagedFlux;
import com.azure.search.documents.SearchAsyncClient;
import com.azure.search.documents.SearchClient;
import com.azure.search.documents.implementation.models.SearchResult;
import com.azure.search.documents.indexes.SearchIndexAsyncClient;
import com.azure.search.documents.indexes.SearchIndexClient;
import com.azure.search.documents.indexes.SearchIndexClientBuilder;
import com.azure.search.documents.indexes.models.IndexDocumentsBatch;
import com.azure.search.documents.indexes.models.SearchIndex;
import com.azure.search.documents.indexes.models.SearchSuggester;
import com.azure.search.documents.models.SearchOptions;
import com.azure.search.documents.util.SearchPagedFlux;
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
import java.util.concurrent.CompletableFuture;

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

    public CompletableFuture<List<SearchableEntity>> search(String keywords, String type, Integer offset) {
        List<SearchableEntity> searchableEntityList = new ArrayList<>();
        CompletableFuture<List<SearchableEntity>> future = new CompletableFuture<>();
        SearchOptions options = new SearchOptions();
        options.setIncludeTotalCount(true);
        options.setFilter("type eq '" + type + "'");
        options.setSkip(offset * 10);
        options.setTop(10);
        SearchPagedFlux searchPagedFlux = client.search(keywords, options);
        searchPagedFlux.byPage().subscribe(searchPagedResponse -> {
            searchPagedResponse.getValue().forEach(searchResult -> {
                System.out.println(searchResult.getDocument(SearchableEntity.class));
                searchableEntityList.add(searchResult.getDocument(SearchableEntity.class));
                future.complete(searchableEntityList);
            });
        });
        return future;
    }
}
