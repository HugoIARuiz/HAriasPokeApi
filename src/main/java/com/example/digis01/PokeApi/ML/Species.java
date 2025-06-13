
package com.example.digis01.PokeApi.ML;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Species {
    private String name;
    private String url;
    public List<FlavorText> flavor_text_entries;
}
