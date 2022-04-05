package com.exemple.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.exemple.orm.Cargo;
import com.exemple.orm.Funcionario;
import com.exemple.orm.UnidadeTrabalho;
import com.exemple.repository.CargoRepository;
import com.exemple.repository.FuncionarioRepository;
import com.exemple.repository.UnidadeTrabalhoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudFuncionarioService {

    private Boolean system = true;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // repositories

    private final CargoRepository cargoRepository;

    private final FuncionarioRepository funcionarioRepository;

    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    // constructors

    public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository, UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.cargoRepository = cargoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

	// methods

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual acao de funcionario deseja executar");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    visualizar(scanner);
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

	private void atualizar(Scanner scanner) {
		System.out.println("Digite o id");

		Integer id = scanner.nextInt();

		System.out.println("Digite o nome");

		String nome = scanner.next();

		System.out.println("Digite o cpf");

		String cpf = scanner.next();

		System.out.println("Digite o salario");

		Double salario = scanner.nextDouble();

		System.out.println("Digite a data de contracao");

		String dataContratacao = scanner.next();

		System.out.println("Digite o cargoId");

		Integer cargoId = scanner.nextInt();

		Funcionario funcionario = new Funcionario();

		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));

		Optional<Cargo> cargo = cargoRepository.findById(cargoId);

		funcionario.setCargo(cargo.get());

		funcionarioRepository.save(funcionario);

		System.out.println("Alterado");
	}

	private void deletar(Scanner scanner) {
		System.out.println("Id");

		int id = scanner.nextInt();

		funcionarioRepository.deleteById(id);

		System.out.println("Deletado");
	}

    private void salvar(Scanner scanner) {
        System.out.println("Digite o nome");

        String nome = scanner.next();

        System.out.println("Digite o cpf");

        String cpf = scanner.next();

        System.out.println("Digite o salario");

        Double salario = scanner.nextDouble();

        System.out.println("Digite a data de contracao");

        String dataContratacao = scanner.next();

        System.out.println("Digite o cargoId");

        Integer idCargo = scanner.nextInt();

        List<UnidadeTrabalho> unidadeTrabalhoList = unidadeTrabalhoList(scanner);

        Funcionario funcionario = new Funcionario();

        funcionario.setNome(nome);
        funcionario.setCpf(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));

        Optional<Cargo> cargo = cargoRepository.findById(idCargo);

        funcionario.setCargo(cargo.get());
        funcionario.setUnidadeTrabalhos(unidadeTrabalhoList);

        funcionarioRepository.save(funcionario);

        System.out.println("Salvo");
    }

    private void visualizar(Scanner scanner) {
        System.out.println("Qual pagina voce deseja visualizar");

        int page = scanner.nextInt();

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "nome"));
        Page<Funcionario> funcionarioList = funcionarioRepository.findAll(pageable);

        System.out.println(funcionarioList);
        System.out.println("Pagina atual " + funcionarioList.getNumber());
        System.out.println("Total elemento " + funcionarioList.getTotalElements());

        for (Funcionario funcionario : funcionarioList) {
            System.out.println(funcionario);
        }
    }

	private List<UnidadeTrabalho> unidadeTrabalhoList(Scanner scanner) {
		boolean isTrue = true;

		List<UnidadeTrabalho> unidadeTrabalhoList = new ArrayList<>();

		while (isTrue) {
			System.out.println("Digite o unidadeId (Para sair digite 0)");

			int idUnidade = scanner.nextInt();

			if (idUnidade != 0) {
				Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(idUnidade);

				unidadeTrabalhoList.add(unidade.get());
			} else {
				isTrue = false;
			}
		}

		return unidadeTrabalhoList;
	}
}
