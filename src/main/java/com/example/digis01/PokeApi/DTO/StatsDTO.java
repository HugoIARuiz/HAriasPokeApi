package com.example.digis01.PokeApi.DTO;

import com.example.digis01.PokeApi.ML.Stat;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StatsDTO {

    private int base_stat;
    private int effort;
    public Stat stat;
}
