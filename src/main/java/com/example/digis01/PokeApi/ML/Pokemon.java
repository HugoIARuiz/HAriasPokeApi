package com.example.digis01.PokeApi.ML;

import com.example.digis01.PokeApi.DTO.PokemonTypeDTO;
import com.example.digis01.PokeApi.DTO.StatsDTO;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pokemon {

    private int id;
    private String name;
    private int base_experience;
    private double height;
    private boolean is_default;
    private String location_area_encounters;
    private Integer order;
    private double weight;
    public Sprites sprites;
    public List<PokemonTypeDTO> types;
    public Cries cries;
    public List<StatsDTO> stats;

}
