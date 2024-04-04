package br.com.fiap.banksolution.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.banksolution.model.Cadastro;

public interface CadastroRepository extends JpaRepository<Cadastro, Long>{

    Cadastro findByCpfTitular(String cpf);
    
}
