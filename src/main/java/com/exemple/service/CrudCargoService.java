package com.exemple.service;

import org.springframework.stereotype.Service;
import com.exemple.orm.Cargo;
import com.exemple.repository.CargoRepository;

import java.util.Scanner;

@Service
public class CrudCargoService {

    private Boolean system = true;

    private final CargoRepository cargoRepository;

    // constructors

    public CrudCargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    // methods

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual acao de cargo deseja executar");
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
                    visualizar();
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
        System.out.println("Id");

        int id = scanner.nextInt();

        System.out.println("Descricao do Cargo");

        String descricao = scanner.next();

        Cargo cargo = new Cargo();

        cargo.setId(id);
        cargo.setDescricao(descricao);

        cargoRepository.save(cargo);

        System.out.println("Atualizado");
    }

    private void deletar(Scanner scanner) {
        System.out.println("Id");

        int id = scanner.nextInt();

        cargoRepository.deleteById(id);

        System.out.println("Deletado");
    }

    private void salvar(Scanner scanner) {
        System.out.println("Descricao do cargo");

        String descricao = scanner.next();

        Cargo cargo = new Cargo();

        cargo.setDescricao(descricao);

        cargoRepository.save(cargo);

        System.out.println("Salvo");
    }

    private void visualizar() {
        for (Cargo cargo : cargoRepository.findAll()) {
            System.out.println(cargo);
        }
    }
}
