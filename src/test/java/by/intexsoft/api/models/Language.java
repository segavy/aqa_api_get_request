package by.intexsoft.api.models;

import lombok.Data;

@Data
public class Language{
    private String iso639_1;
    private String iso639_2;
    private String name;
    private String nativeName;
}