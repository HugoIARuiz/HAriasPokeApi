
package com.example.digis01.PokeApi.DTO;

import com.example.digis01.PokeApi.ML.Ability;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbilityDTO {
    
    private boolean is_hidden;
    private int slot;
    public Ability ability;
    
}
