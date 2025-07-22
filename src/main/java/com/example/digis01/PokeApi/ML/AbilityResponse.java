
package com.example.digis01.PokeApi.ML;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbilityResponse {
    private int id;
    private String name;
    List<NameEntry> names;
    List<FlavorText> flavor_text_entries;
    
}
