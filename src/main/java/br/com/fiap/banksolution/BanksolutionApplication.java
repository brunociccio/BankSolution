package br.com.fiap.banksolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@SpringBootApplication
@Controller
public class BanksolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanksolutionApplication.class, args);
	}

	@RequestMapping
	@ResponseBody
	public String home(){
		return "WebApp BankSolution";
	}

}
