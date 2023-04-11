package com.example.pethospitalbackend.search.entity;

import com.azure.search.documents.indexes.SearchableField;
import com.azure.search.documents.indexes.SimpleField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchableEntity implements Searchable{
    @JsonProperty("id")
    @SimpleField(isKey = true)
    private String id;
    @JsonProperty("name")
    @SearchableField()
    private String name;
    @JsonProperty("phoneNumber")
    @SearchableField()
    private String phoneNumber;
    @JsonProperty("introduction")
    @SearchableField()
    private String introduction;
    @JsonProperty("type")
    @SimpleField(isFilterable = true)
    private String type;
    @JsonProperty("other")
    @SearchableField()
    private String other;
}
