package net.lanet.tabelafipe.main;

import net.lanet.tabelafipe.model.*;
import net.lanet.tabelafipe.service.ConsumeApi;
import net.lanet.tabelafipe.service.ConvertsData;
import net.lanet.tabelafipe.utils.ValidNumber;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);
    ValidNumber validNumber = new ValidNumber();
    ConsumeApi consumeApi = new ConsumeApi();
    ConvertsData convertsData = new ConvertsData();

    public void viewMenu() {

        final String OPTIONS_MENU = """
                ***************************
                *  T A B E L A   F I P E  *
                ***************************
                *  1  |  Carro            *
                *  2  |  Moto             *
                *  3  |  Caminhão         *
                *  4  |  Sair             *
                ***************************
                Escolha uma opção válida para consultar:""";
        final int OPTIONS_LIMIT = 4;
        final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

        String json = "";
        String addressMarca = "";
        String addressModelo = "";
        String addressAno = "";
        int optionMenu = 0;
        while (optionMenu != OPTIONS_LIMIT) {
            System.out.println(OPTIONS_MENU);

            addressMarca = "";
            optionMenu = validNumber.getValidInteger(scanner, OPTIONS_LIMIT);

            switch (optionMenu) {
                case 1:
                    // 1 | Carro
                    addressMarca = URL_BASE.concat("carros/marcas");
                    break;
                case 2:
                    // 2 | Moto
                    addressMarca = URL_BASE.concat("motos/marcas");
                    break;
                case 3:
                    // 3 | Caminhão
                    addressMarca = URL_BASE.concat("caminhoes/marcas");
                    break;
                case 4:
                    // Sair;
                    System.out.println("Consulta finalizada!");
                    break;
                default:
                    // Opção inválida
                    System.out.println("Opção inválida!");
                    break;
            }

            if (!addressMarca.isEmpty()) {

                json = consumeApi.getData(addressMarca);
                if (!json.isEmpty()) {
                    List<Marcas> listaMarcas = convertsData.getList(json, Marcas.class);
                    listaMarcas.stream()
                            .sorted(Comparator.comparing(Marcas::nome))
                            .forEach(e ->
                                    System.out.println("%6s  |  %s"
                                            .formatted(e.codigo(), e.nome())));
                }

                int codigoMarca = 0;
                while (codigoMarca == 0) {
                    System.out.println("Escolha uma opção válida de marca para consultar:");
                    codigoMarca = validNumber.getValidInteger(scanner, null);

                    addressModelo = addressMarca.concat("/").concat(String.valueOf(codigoMarca)).concat("/modelos");
                    json = consumeApi.getData(addressModelo);
                    if (!json.isEmpty()) {
                        Modelos modeloLista = convertsData.getData(json, Modelos.class);
                        modeloLista.modelos().stream()
                                .sorted(Comparator.comparing(ModelosLista::nome))
                                .forEach(e ->
                                        System.out.println("%6s  |  %s"
                                                .formatted(e.codigo(), e.nome())));
                    } else {
                        codigoMarca = 0;
                    }
                }

                int codigoModelo = 0;
                while (codigoModelo == 0) {
                    System.out.println("Escolha uma opção válida de modelo para consultar:");
                    codigoModelo = validNumber.getValidInteger(scanner, null);

                    addressAno = addressModelo.concat("/").concat(String.valueOf(codigoModelo)).concat("/anos");
                    json = consumeApi.getData(addressAno);
                    if (!json.isEmpty()) {
                        String addressVeiculo = addressAno;
                        List<Veiculos> listaVeiculos = new ArrayList<>();
                        List<Anos> listaAnos = convertsData.getList(json, Anos.class);
                        listaAnos.stream()
                                .sorted(Comparator.comparing(Anos::codigoParseInteger).reversed())
                                .forEach(e -> {

                                    String jsonVeiculo = consumeApi.getData(addressVeiculo.concat("/").concat(e.codigo()));
                                    if (!jsonVeiculo.isEmpty()) {
                                        Veiculos veiculo = convertsData.getData(jsonVeiculo, Veiculos.class);
                                        listaVeiculos.add(veiculo);
                                    }

                                });
                        if (!listaVeiculos.isEmpty()) {
                            System.out.println(listaVeiculos.get(0).marca().concat(" ").concat(listaVeiculos.get(0).modelo()));
                            listaVeiculos.forEach(item -> {
                                System.out.println("%6s  |  %s"
                                        .formatted(item.ano(), item.valor()));
                            });
                        } else {
                            System.out.println("Nenhum ano encontrado para as opções escolhidas!");
                        }
                        System.out.println();
                    } else {
                        codigoModelo = 0;
                    }
                }

            }
        }

        scanner.close();

    }
}
