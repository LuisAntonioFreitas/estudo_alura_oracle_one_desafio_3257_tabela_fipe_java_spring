package net.lanet.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Anos(
        @JsonAlias("codigo")
        String codigo,
        @JsonAlias("nome")
        String nome
) {
    public int codigoParseInteger() {
        return Integer.valueOf(codigoClean());
    }

    public String codigoClean() {
        return this.codigo.substring(0,4);
    }
}
