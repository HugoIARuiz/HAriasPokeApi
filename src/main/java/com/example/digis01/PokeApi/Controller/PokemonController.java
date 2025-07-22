package com.example.digis01.PokeApi.Controller;

import com.example.digis01.PokeApi.DTO.PokemonTypeDTO;
import com.example.digis01.PokeApi.ML.AbilityResponse;
import com.example.digis01.PokeApi.ML.AbilitySlot;
import com.example.digis01.PokeApi.ML.FlavorText;
import com.example.digis01.PokeApi.ML.Pokemon;
import com.example.digis01.PokeApi.ML.Result;
import com.example.digis01.PokeApi.ML.Species;
import com.example.digis01.PokeApi.ML.TypeResponse;
import com.example.digis01.PokeApi.ML.UrlPokemon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/pokemon")
public class PokemonController {

    private RestTemplate restTemplate = new RestTemplate();
    private final String URL_BASE = "https://pokeapi.co/api/v2/pokemon";

    @GetMapping
    public String Pokedex(Model model) {

        try {

            ResponseEntity<Result<UrlPokemon>> listUrlPokemon = restTemplate.exchange(URL_BASE, HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<UrlPokemon>>() {
            });

            if (listUrlPokemon.getStatusCode().is2xxSuccessful()) {
                Result result = new Result();
                result = listUrlPokemon.getBody();
                List<UrlPokemon> urlPokemon = new ArrayList<>();
                urlPokemon = result.results;
                List<Pokemon> pokemons = new ArrayList<>();
                for (UrlPokemon urls : urlPokemon) {
                    ResponseEntity<Pokemon> getPokemon = restTemplate.exchange(urls.getUrl(), HttpMethod.GET,
                            HttpEntity.EMPTY,
                            new ParameterizedTypeReference<Pokemon>() {
                    });
                    if (getPokemon.getStatusCode().is2xxSuccessful()) {
                        pokemons.add(getPokemon.getBody());
                    }
                }
                Map<String, String> typeColors = new HashMap<>();
                typeColors.put("fire", "#FF5722");
                typeColors.put("water", "#2196F3");
                typeColors.put("grass", "#4CAF50");
                typeColors.put("electric", "#FFC107");
                typeColors.put("ice", "#00BCD4");
                typeColors.put("fighting", "#E91E63");
                typeColors.put("poison", "#9C27B0");
                typeColors.put("ground", "#795548");
                typeColors.put("psychic", "#673AB7");
                typeColors.put("rock", "#6D4C41");
                typeColors.put("bug", "#8BC34A");
                typeColors.put("ghost", "#607D8B");
                typeColors.put("dragon", "#3F51B5");
                typeColors.put("dark", "#424242");
                typeColors.put("steel", "#B0BEC5");
                typeColors.put("fairy", "#F8BBD0");
                typeColors.put("flying", "#92C5FC");
                typeColors.put("normal", "#CDCDCD");

                model.addAttribute("typeColors", typeColors);
                model.addAttribute("listPokemon", pokemons);

                model.addAttribute("results", listUrlPokemon.getBody());
                System.out.println(typeColors);
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return "Pokedex";
    }

    @GetMapping("/{name}")
    public String getByName(@PathVariable String name, Model model) {
        try {
            ResponseEntity<Pokemon> response = restTemplate.exchange(URL_BASE + "/" + name,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Pokemon>() {
            });
            ResponseEntity<Species> responseSpecies = restTemplate.exchange(response.getBody().species.getUrl(),
                    HttpMethod.GET, HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Species>() {
            });
            

            Species species = new Species();
            species = responseSpecies.getBody();
            List<FlavorText> descripcion = new ArrayList<>();
            descripcion = species.flavor_text_entries.stream().map(t -> (FlavorText) t).filter(t -> t.language.getName().equals("es")).collect(Collectors.toList());

            List<String> habilidadesEnEspanol = new ArrayList<>();
            List<String> descripcionEspanol = new ArrayList<>();
            List<String> tipoEspanol = new ArrayList<>();
             Pokemon pokemon = response.getBody();
            for (AbilitySlot abilitySlot : pokemon.getAbilities()) {
                ResponseEntity<AbilityResponse> abilityResponse = restTemplate.exchange(
                        abilitySlot.getAbility().getUrl(),
                        HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<AbilityResponse>() {
                });

                AbilityResponse ability = abilityResponse.getBody();
                ability.getNames().stream()
                        .filter(nameEntry -> "es".equals(nameEntry.getLanguage().getName()))
                        .findFirst()
                        .ifPresent(nameEntry -> habilidadesEnEspanol.add(nameEntry.getName()));
                ability.getFlavor_text_entries().stream()
                        .filter(descripcionAbility -> "es".equals(descripcionAbility.getLanguage().getName()))
                        .findFirst()
                        .ifPresent(descripcionAbility -> descripcionEspanol.add(descripcionAbility.getFlavor_text())); 
            }
            for (PokemonTypeDTO pokemonType : pokemon.getTypes()) {
                ResponseEntity<TypeResponse> typeResponse = restTemplate.exchange(pokemonType.getType().getUrl()
                        , HttpMethod.GET, HttpEntity.EMPTY
                        , new ParameterizedTypeReference<TypeResponse>() {});
                TypeResponse type = typeResponse.getBody();
                type.getNames().stream()
                        .filter(typeEntry -> "es".equals(typeEntry.getLanguage().getName()))
                        .findFirst()
                        .ifPresent(typeEntry -> tipoEspanol.add(typeEntry.getName()));
            }

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, String> typeColors = new HashMap<>();
                typeColors.put("fire", "#FF5722");
                typeColors.put("water", "#2196F3");
                typeColors.put("grass", "#4CAF50");
                typeColors.put("electric", "#FFC107");
                typeColors.put("ice", "#00BCD4");
                typeColors.put("fighting", "#E91E63");
                typeColors.put("poison", "#9C27B0");
                typeColors.put("ground", "#795548");
                typeColors.put("psychic", "#673AB7");
                typeColors.put("rock", "#6D4C41");
                typeColors.put("bug", "#8BC34A");
                typeColors.put("ghost", "#607D8B");
                typeColors.put("dragon", "#3F51B5");
                typeColors.put("dark", "#424242");
                typeColors.put("steel", "#B0BEC5");
                typeColors.put("fairy", "#F8BBD0");
                typeColors.put("flying", "#92C5FC");
                typeColors.put("normal", "#CDCDCD");
                model.addAttribute("typeColors", typeColors);
                model.addAttribute("pokemon", response.getBody());
                model.addAttribute("descripcion", descripcion);
                model.addAttribute("habilidades", habilidadesEnEspanol);
                model.addAttribute("descHabilidades", descripcionEspanol);
                model.addAttribute("tipoEsp", tipoEspanol);
            }

        } catch (HttpStatusCodeException ex) {
            return "ErrorPage";
        }
        return "Pokemon";
    }

    @GetMapping("/pageable")
    public String Pageable(@RequestParam String urlPage, Model model) {
        try {
            ResponseEntity<Result<UrlPokemon>> response = restTemplate.exchange(urlPage,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<UrlPokemon>>() {
            });

            if (response.getStatusCode().is2xxSuccessful()) {
                Result result = new Result();
                result = response.getBody();
                List<UrlPokemon> url = new ArrayList<>();
                url = result.results;
                List<Pokemon> pokemons = new ArrayList<>();

                for (UrlPokemon urls : url) {
                    ResponseEntity<Pokemon> getUniquePokemon = restTemplate.exchange(urls.getUrl(),
                            HttpMethod.GET,
                            HttpEntity.EMPTY,
                            new ParameterizedTypeReference<Pokemon>() {
                    });

                    if (getUniquePokemon.getStatusCode().is2xxSuccessful()) {
                        pokemons.add(getUniquePokemon.getBody());
                    }
                }
                Map<String, String> typeColors = new HashMap<>();
                typeColors.put("fire", "#FF5722");
                typeColors.put("water", "#2196F3");
                typeColors.put("grass", "#4CAF50");
                typeColors.put("electric", "#FFC107");
                typeColors.put("ice", "#00BCD4");
                typeColors.put("fighting", "#E91E63");
                typeColors.put("poison", "#9C27B0");
                typeColors.put("ground", "#795548");
                typeColors.put("psychic", "#673AB7");
                typeColors.put("rock", "#6D4C41");
                typeColors.put("bug", "#8BC34A");
                typeColors.put("ghost", "#607D8B");
                typeColors.put("dragon", "#3F51B5");
                typeColors.put("dark", "#424242");
                typeColors.put("steel", "#B0BEC5");
                typeColors.put("fairy", "#F8BBD0");
                typeColors.put("flying", "#92C5FC");
                typeColors.put("normal", "#CDCDCD");
                model.addAttribute("typeColors", typeColors);

                model.addAttribute("listPokemon", pokemons);
                model.addAttribute("results", response.getBody());
            }

        } catch (HttpStatusCodeException ex) {
        }
        return "Pokedex";
    }

}
