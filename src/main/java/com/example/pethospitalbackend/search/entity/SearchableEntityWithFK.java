package com.example.pethospitalbackend.search.entity;

import com.azure.search.documents.indexes.SearchableField;
import com.azure.search.documents.indexes.SimpleField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchableEntityWithFK extends SearchableEntity{
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
    @JsonProperty("FKName")
    @SearchableField()
    private String fkName;
    @JsonProperty("FKId")
    @SearchableField()
    private String fkId;

    public SearchableEntityWithFK(String id, String name, String phoneNumber, String introduction, String type, String id1, String name1, String phoneNumber1, String introduction1, String type1) {
        super(id, name, phoneNumber, introduction, type);
        this.id = id1;
        this.name = name1;
        this.phoneNumber = phoneNumber1;
        this.introduction = introduction1;
        this.type = type1;
    }

    public SearchableEntityWithFK(String id, String name, String phoneNumber, String introduction, String type, String id1, String name1, String phoneNumber1, String introduction1, String type1, String fkName, String fkId) {
        super(id, name, phoneNumber, introduction, type);
        this.id = id1;
        this.name = name1;
        this.phoneNumber = phoneNumber1;
        this.introduction = introduction1;
        this.type = type1;
        this.fkName = fkName;
        this.fkId = fkId;
    }
}
