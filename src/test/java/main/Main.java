package main;

import Api.ApiClient;
import model.ExchangeRates;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            ApiClient apiClient = new ApiClient(); // Instancia o cliente da API
            String jsonResponse = apiClient.getExchangeRates(); // Obtém as taxas de câmbio
            ExchangeRates exchangeRates = apiClient.parseExchangeRates(jsonResponse); // Converte a resposta JSON para um objeto Java

            Scanner scanner = new Scanner(System.in); // Scanner para entrada de dados

            // Mapeia as opções de conversão
            Map<String, String> opcoesConversao = Map.of(
                    "1", "BRL para USD",
                    "2", "BRL para EUR",
                    "3", "BRL para AED",
                    "4", "BRL para EGP",
                    "5", "BRL para AFN",
                    "6", "BRL para JPY"
            );

            while (true) { // Loop principal do menu
                System.out.println("Escolha uma opção de conversão de moeda:");
                opcoesConversao.forEach((key, value) -> System.out.println(key + ". " + value));
                System.out.print("Digite o número da opção escolhida (ou 0 para sair): ");
                String opcaoEscolhida = scanner.nextLine();

                if (opcaoEscolhida.equals("0")) { // Condição de saída
                    System.out.println("Saindo do programa. Até logo!");
                    break;
                }

                if (!opcoesConversao.containsKey(opcaoEscolhida)) { // Verifica se a opção é válida
                    System.out.println("Opção inválida! Tente novamente.");
                    continue;
                }

                System.out.print("Digite o valor a ser convertido: ");
                double valor = scanner.nextDouble();
                scanner.nextLine(); // Limpa o buffer do scanner

                String moedaDestino = opcoesConversao.get(opcaoEscolhida).split(" para ")[1];
                double valorConvertido = converterMoeda(exchangeRates, moedaDestino, valor); // Agora converte de BRL para a moeda destino
                System.out.printf("Valor convertido: %.2f %s%n", valorConvertido, moedaDestino);
            }

            scanner.close(); // Fecha o scanner
        } catch (Exception e) {
            e.printStackTrace(); // Tratamento de exceções
        }
    }

    // Método para converter de BRL para a moeda de destino
    public static double converterMoeda(ExchangeRates exchangeRates, String moedaDestino, double valor) {
        Map<String, Double> rates = exchangeRates.getRates();
        Double taxaBRL = rates.get("BRL"); // Taxa de conversão de BRL
        Double taxaDestino = rates.get(moedaDestino); // Taxa de conversão para a moeda de destino

        // Verifica se as taxas de BRL e da moeda destino estão disponíveis
        if (taxaBRL == null) {
            throw new IllegalArgumentException("Taxa de câmbio para BRL não encontrada.");
        }

        if (taxaDestino == null) {
            throw new IllegalArgumentException("Taxa de câmbio para " + moedaDestino + " não encontrada.");
        }

        // Conversão de BRL para a moeda de destino
        return valor / taxaBRL * taxaDestino;
    }
}
