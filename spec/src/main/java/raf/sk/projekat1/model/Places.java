package raf.sk.projekat1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Places {
    private String name;
    private Map<String,String> additional;

    public Places(String name) {
        this.name = name;
        additional = new HashMap<>();
    }

}
