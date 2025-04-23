package org.example.bcdadosnew;

import java.sql.SQLException;
import java.util.Scanner;

public class ChamadasBancoNovo {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        AcessoBancoNovo abn = new AcessoBancoNovo();
        abn.AcessoBancoNovo();

        //abn.criarTabela();
        //abn.inserirRegTab("TestePar", "TestePar");

        Scanner scanner = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);
//        System.out.println("Digite o código: ");
//        int codigo = scannerInt.nextInt();
//
//        System.out.println("Digite a espécie para alterar");
//        String especie = scanner.nextLine();
//
//        System.out.println("Digite o gênero para alterar");
//        String genero = scanner.nextLine();
//
//        abn.alteraRegistro(codigo, especie, genero);

        System.out.println("Digite o código do dinossauro: ");
        int cod = scannerInt.nextInt();
        abn.deletarRegistro(cod);




    }
}
