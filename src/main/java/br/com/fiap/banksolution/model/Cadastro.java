package br.com.fiap.banksolution.model;

import java.util.Date;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O número da agência é obrigatório")
    private String numeroAgencia;

    @NotBlank(message = "O nome do titular é obrigatório")
    private String nomeTitular;

    @CPF(message = "CPF inválido")
    private String cpfTitular;

    @NotNull(message = "A data de abertura é obrigatória")
    @FutureOrPresent(message = "A data de abertura não pode estar no futuro")
    private Date dataAbertura;

    @NotNull(message = "O saldo inicial é obrigatório")
    @Positive(message = "O saldo inicial deve ser positivo")
    private Double saldoInicial;

    @NotNull(message = "A atividade da conta é obrigatória")
    private Boolean atividade;

    @NotNull(message = "O tipo de conta é obrigatório")
    private TipoConta tipoConta;

    @AssertTrue(message = "Tipo de conta inválido")
    private boolean isTipoContaValido() {
        return tipoConta != null;
    }

    public enum TipoConta {
        CORRENTE,
        POUPANCA,
        SALARIO
    }
}
