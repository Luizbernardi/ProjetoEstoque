package com.estoque.estoque.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

@Component
public class StringToDoubleConverter implements Converter<String, Double> {

    private static final DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.of("pt", "BR")));
    private static final double MAX_VALUE = 1_000_000.00; // Limite de um Milhão

    @Override
    public Double convert(@NonNull String source) {
        if (source.isEmpty()) {
            return null;
        }
        try {
            double value = df.parse(source).doubleValue();
            if (value > MAX_VALUE) {
                throw new IllegalArgumentException("O valor não pode ser maior que um Milhão: " + source);
            }
            return value;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Erro ao converter o preço: " + source, e);
        }
    }
}