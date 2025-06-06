
package com.example.digis01.PokeApi.ML;

import java.util.List;


public class Result<T> {
    
    public List<T> results;

    

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
