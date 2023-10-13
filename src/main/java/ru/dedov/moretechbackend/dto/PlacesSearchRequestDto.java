package ru.dedov.moretechbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlacesSearchRequestDto {

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("q")
    private String q;

    @JsonProperty("type")
    private String type;

    @JsonProperty("lon")
    private Double lon;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("fields")
    private String fields;

    @JsonProperty("key")
    private String key;
}
