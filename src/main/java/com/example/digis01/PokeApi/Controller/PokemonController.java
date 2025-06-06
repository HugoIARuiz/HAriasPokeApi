
package com.example.digis01.PokeApi.Controller;

import com.example.digis01.PokeApi.DTO.PokemonDTO;
import com.example.digis01.PokeApi.DTO.TypesDTO;
import com.example.digis01.PokeApi.ML.Pokemon;
import com.example.digis01.PokeApi.ML.Result;
import com.example.digis01.PokeApi.ML.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {
    private RestTemplate restTemplate = new RestTemplate();
    private final String URL_BASE = "https://pokeapi.co/api/v2/pokemon";
    @GetMapping
    public String Pokedex(Model model){
        
        try {
            
            ResponseEntity<Result<PokemonDTO>> listUrlPokemon = restTemplate.exchange(URL_BASE, HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<PokemonDTO>>(){});
            
            if (listUrlPokemon.getStatusCode().is2xxSuccessful()) {
                Result result = new Result();
                result = listUrlPokemon.getBody();
                List<PokemonDTO> urlPokemon = new ArrayList<>();
                urlPokemon = result.results;
                List<Pokemon> pokemons = new ArrayList<>();
                for(PokemonDTO urls : urlPokemon){
                    ResponseEntity<Pokemon> getPokemon = restTemplate.exchange(urls.getUrl(), HttpMethod.GET, 
                            HttpEntity.EMPTY, 
                            new ParameterizedTypeReference<Pokemon>(){});
                    if(getPokemon.getStatusCode().is2xxSuccessful()){
                        pokemons.add(getPokemon.getBody());
                    }
                }
                List<TypesDTO> urlTypes = new ArrayList<>();
                List<Types> tipos = new ArrayList<>();
                for(TypesDTO urls : urlTypes){
                    ResponseEntity<Types> getTypes = restTemplate.exchange(urls.getUrl(), HttpMethod.GET, 
                            HttpEntity.EMPTY, 
                            new ParameterizedTypeReference<Types>(){});
                    if(getTypes.getStatusCode().is2xxSuccessful()){
                        tipos.add(getTypes.getBody());
                    }
                }
                model.addAttribute("listPokemon", pokemons);
                model.addAttribute("listTypes", tipos);
            }
          
            
            
            
            return "Pokedex";
        } catch (Exception e) {
        }
        
        
        return null;
    }
    
    
    
}
