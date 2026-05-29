package principal;

import app.ModuloConsultaDenuncia;
import app.ModuloRegistroDenuncia;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ModuloRegistroDenuncia moduloRegistro = new ModuloRegistroDenuncia(sc);
        ModuloConsultaDenuncia moduloConsulta = new ModuloConsultaDenuncia(sc);

        int opcion = -1;

        do {
            System.out.println("SISTEMA POLICIAL");
            System.out.println("1. Registrar denuncia");
            System.out.println("2. Consultar denuncia");
            System.out.println("3. Actualizar denuncia");
            System.out.println("4. Eliminar denuncia");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opcion no valida.");
                continue;
            }

            switch (opcion) {
                case 1:
                    moduloRegistro.registrarDenuncia();
                    break;
                case 2:
                    moduloConsulta.consultarDenuncia();
                    break;
                case 3:
                    moduloRegistro.actualizarDenuncia();
                    break;
                case 4:
                    moduloRegistro.eliminarDenuncia();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}