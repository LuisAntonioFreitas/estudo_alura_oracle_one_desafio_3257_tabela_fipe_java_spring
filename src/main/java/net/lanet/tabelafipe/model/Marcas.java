package net.lanet.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Marcas(
        @JsonAlias("codigo")
        String codigo,
        @JsonAlias("nome")
        String nome
) {
    public int codigoParseInteger() {
        return Integer.valueOf(this.codigo);
    }
}
