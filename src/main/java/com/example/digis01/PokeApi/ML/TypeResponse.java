
package com.example.digis01.PokeApi.ML;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeResponse {
    private int id;
    private String name;
    public List<NameEntry> names;
    
}
