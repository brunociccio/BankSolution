package br.com.fiap.banksolution.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.banksolution.model.Cadastro;
import br.com.fiap.banksolution.repository.CadastroRepository;

@RestController
@RequestMapping("/contas")
public class CadastroController {

    @Autowired
    private CadastroRepository cadastroRepository;

    // END POINT PARA RETORNAR TODAS AS CONTAS CADASTRADAS
    @GetMapping
    public ResponseEntity<List<Cadastro>> getAllContas() {
        List<Cadastro> contas = cadastroRepository.findAll();
        return ResponseEntity.ok(contas);
    }

    // ENDPOINT PARA RETORNAR UMA CONTA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Cadastro> getContaById(@PathVariable Long id) {
        Cadastro conta = cadastroRepository.findById(id).orElse(null);
        if (conta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conta);
    }

    // ENDPOINT PARA RETORNAR UMA CONTA POR CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cadastro> getContaByCpf(@PathVariable String cpf) {
        Cadastro conta = cadastroRepository.findByCpfTitular(cpf);
        if (conta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conta);
    }

    // ENDPOINT PARA ENCERRAR UMA CONTA
    @PutMapping("/encerrar/{id}")
    public ResponseEntity<Cadastro> encerrarConta(@PathVariable Long id) {
        Cadastro conta = cadastroRepository.findById(id).orElse(null);
        if (conta == null) {
            return ResponseEntity.notFound().build();
        }
        conta.setAtividade(false); // MARCA CONTA COMO INATIVA
        cadastroRepository.save(conta);
        return ResponseEntity.ok(conta);
    }

    // ENDPOINT PARA REALIZAR UM SAQUE DA CONTA
    @PostMapping("/saque")
    public ResponseEntity<Cadastro> saqueConta(@RequestBody SaqueRequest saqueRequest) {
        Cadastro conta = cadastroRepository.findById(saqueRequest.getIdConta()).orElse(null);
        if (conta == null) {
            return ResponseEntity.notFound().build();
        }
        // VERIFICA SE HÁ SALDO
        if (conta.getSaldoInicial() >= saqueRequest.getValorSaque()) {
            conta.setSaldoInicial(conta.getSaldoInicial() - saqueRequest.getValorSaque());
            cadastroRepository.save(conta);
            return ResponseEntity.ok(conta);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // ENDPOINT PARA CADASTRAR UMA NOVA CONTA
    @PostMapping
    public ResponseEntity<Cadastro> cadastrarConta(@RequestBody Cadastro novaConta) {
        Cadastro contaCadastrada = cadastroRepository.save(novaConta);
        return ResponseEntity.status(HttpStatus.CREATED).body(contaCadastrada);
    }


    // Endpoint para realizar um PIX
    @PostMapping("/pix")
    public ResponseEntity<Cadastro> realizarPIX(@RequestBody PIXRequest pixRequest) {
        Cadastro contaOrigem = cadastroRepository.findById(pixRequest.getIdContaOrigem()).orElse(null);
        Cadastro contaDestino = cadastroRepository.findById(pixRequest.getIdContaDestino()).orElse(null);

        if (contaOrigem == null || contaDestino == null) {
            return ResponseEntity.notFound().build();
        }

        // Verificar se há saldo suficiente na conta de origem para a transferência
        if (contaOrigem.getSaldoInicial() >= pixRequest.getValorPIX()) {
            contaOrigem.setSaldoInicial(contaOrigem.getSaldoInicial() - pixRequest.getValorPIX());
            contaDestino.setSaldoInicial(contaDestino.getSaldoInicial() + pixRequest.getValorPIX());

            cadastroRepository.save(contaOrigem);
            cadastroRepository.save(contaDestino);

            return ResponseEntity.ok(contaOrigem);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // ENDPOINT PARA REALIZAR UM DEPÓSITO NA CONTA
@PostMapping("/deposito")
public ResponseEntity<Cadastro> realizarDeposito(@RequestBody DepositoRequest depositoRequest) {
    Cadastro conta = cadastroRepository.findById(depositoRequest.getIdConta()).orElse(null);
    if (conta == null) {
        return ResponseEntity.notFound().build();
    }
    
    // Adicionar o valor do depósito ao saldo da conta
    double novoSaldo = conta.getSaldoInicial() + depositoRequest.getValorDeposito();
    conta.setSaldoInicial(novoSaldo);
    
    // Salvar a conta atualizada
    cadastroRepository.save(conta);
    
    return ResponseEntity.ok(conta);
}

        // CLASSE DE REQUISIÇÃO DE SAQUE
        static class SaqueRequest {
            private Long idConta;
            private Double valorSaque;
            public Long getIdConta() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getIdConta'");
            }
            public Double getValorSaque() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getValorSaque'");
            }
        }

            // Classe para representar a requisição de PIX
            static class PIXRequest {
                private Long idContaOrigem;
                private Long idContaDestino;
                private Double valorPIX;
            
                public Long getIdContaOrigem() {
                    return idContaOrigem;
                }
            
                public void setIdContaOrigem(Long idContaOrigem) {
                    this.idContaOrigem = idContaOrigem;
                }
            
                public Double getValorPIX() {
                    return valorPIX;
                }
            
                public void setValorPIX(Double valorPIX) {
                    this.valorPIX = valorPIX;
                }
            
                public Long getIdContaDestino() {
                    return idContaDestino;
                }
            
                public void setIdContaDestino(Long idContaDestino) {
                    this.idContaDestino = idContaDestino;
                }
            }
            

            static class DepositoRequest {
                private Long idConta;
                private Double valorDeposito;
            
                public Double getValorDeposito() {
                    return valorDeposito;
                }
            
                public void setValorDeposito(Double valorDeposito) {
                    this.valorDeposito = valorDeposito;
                }
            
                public Long getIdConta() {
                    return idConta;
                }
            
                public void setIdConta(Long idConta) {
                    this.idConta = idConta;
                }
            }
            
}