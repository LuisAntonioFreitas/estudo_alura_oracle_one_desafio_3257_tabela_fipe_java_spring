package net.lanet.tabelafipe.model;

public record ModelosLista(
        String codigo,
        String nome
) {
    public int codigoParseInteger() {
        return Integer.valueOf(this.codigo);
    }
}
