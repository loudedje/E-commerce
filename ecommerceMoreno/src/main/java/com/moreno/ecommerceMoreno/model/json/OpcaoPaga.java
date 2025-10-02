package com.moreno.ecommerceMoreno.model.json;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OpcaoPaga {
    private String chave;
    private String nomeExibicao;
    private BigDecimal precoAdicional;
}