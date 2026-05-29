package app;

import dao.DenunciaDAO;
import modelo.Denuncia;

import java.util.Scanner;

public class ModuloConsultaDenuncia {

    private final Scanner sc;
    private final DenunciaDAO dao;

    public ModuloConsultaDenuncia(Scanner sc) {
        this.sc = sc;
        this.dao = new DenunciaDAO();
    }

    public void consultarDenuncia() {
        System.out.println("\n CONSULTA DE DENUNCIA:");
        System.out.print("Numero de radicado: ");
        String radicado = sc.nextLine().trim();

        Denuncia denuncia = dao.buscarPorRadicado(radicado);

        if (denuncia == null) {
            System.out.println("No se encontro ninguna denuncia con ese radicado.");
            return;
        }

        System.out.println("\n RESULTADO DE CONSULTA:");
        System.out.println("Radicado: " + denuncia.getNumeroRadicado());
        System.out.println("Ciudadano: " + denuncia.getNombresApellidos());
        System.out.println("Documento: " + denuncia.getNumeroDocumento());
        System.out.println("Telefono: " + denuncia.getTelefonoCelular());
        System.out.println("Fecha y hora: " + denuncia.getFechaHoraIncidente());
        System.out.println("Lugar: " + denuncia.getLugarIncidente());
        System.out.println("Latitud: " + denuncia.getLatitud());
        System.out.println("Longitud: " + denuncia.getLongitud());
        System.out.println("Descripcion: " + denuncia.getDescripcionDetallada());
        System.out.println("Ruta prueba: " + denuncia.getRutaPruebaAdjunta());
        System.out.println("Estado actual: " + denuncia.getEstadoActual());
        System.out.println("Descripcion del estado: " + denuncia.getDescripcionEstado());
    }
}