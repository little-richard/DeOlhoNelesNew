package com.example.ricardomendes.deolhonelesnew.Interfaces.Helper;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
    String[] ESTADOS = new String[]{"BR", "AC", "AL","AP", "AM","BA", "CE","DF", "ES","GO", "MA","MT", "MS", "MG", "PA","PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    String URL_API = "https://dadosabertos.camara.leg.br/api/v2/";
    String URL_DEPUTADOS_ESTADO = URL_API + "deputados?siglaUf=";
    String ORDENAR_NOME = "&ordem=ASC&ordenarPor=nome";
    String ORDENAR_ID = "&ordem=ASC&ordenarPor=id";
    String URL_DEPUTADOS_ALL = URL_API + "deputados?itens=100";
    String URL_DEPUTADO_DETAIL = URL_API + "deputados/";
    String URL_LEGISLATURA = URL_API + "legislaturas/";
    String URL_PROPOSICOES_ID = URL_API + "proposicoes?idAutor=";
    String URL_PARTIDOS_ALL = URL_API + "partidos?itens=50&ordem=ASC&ordenarPor=sigla";
    String URL_DEPUTADO_PARTIDO = URL_API + "deputados?siglaPartido=";
    String[] SIGLAS_PROPOSICOES = new String[]{"CON", "EMC", "EMP", "EMC", "EMS", "INC", "MSC"};
}
