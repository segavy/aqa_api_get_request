package by.intexsoft.api.models;

import lombok.Data;

import java.util.List;

@Data
public class RegionalBloc{
    private String acronym;
    private String name;
    private List<String> otherAcronyms;
}
