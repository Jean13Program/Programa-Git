package app;

import dao.DenunciaDAO;
import modelo.Denuncia;

import java.util.Scanner;

public class ModuloRegistroDenuncia {

    private final Scanner sc;
    private final DenunciaDAO dao;

    public ModuloRegistroDenuncia(Scanner sc) {
        this.sc = sc;
        this.dao = new DenunciaDAO();
    }

    public void registrarDenuncia() {
        System.out.println("\n REGISTRO DE DENUNCIA:");
        Denuncia denuncia = leerDatosDenuncia();

        System.out.print("Numero de radicado: ");
        denuncia.setNumeroRadicado(sc.nextLine().trim());

        boolean exito = dao.insertar(denuncia);
        if (exito) {
            System.out.println("La denuncia fue registrada correctamente.");
        } else {
            System.out.println("No fue posible registrar la denuncia.");
        }
    }

    public void actualizarDenuncia() {
        System.out.println("\n ACTUALIZAR DENUNCIA:");
        System.out.print("Numero de radicado de denuncia: ");
        String radicado = sc.nextLine().trim();

        Denuncia denuncia = leerDatosDenuncia();
        denuncia.setNumeroRadicado(radicado);

        boolean exito = dao.actualizar(denuncia);
        if (exito) {
            System.out.println("Denuncia actualizada correctamente.");
        } else {
            System.out.println("No fue posible actualizar la denuncia.");
        }
    }

    public void eliminarDenuncia() {
        System.out.println("\n ELIMINAR DENUNCIA:");
        System.out.print("Numero de radicado de denuncia: ");
        String radicado = sc.nextLine().trim();

        boolean exito = dao.eliminar(radicado);
        if (exito) {
            System.out.println("Denuncia eliminada correctamente.");
        } else {
            System.out.println("No fue posible eliminar la denuncia.");
        }
    }

    private Denuncia leerDatosDenuncia() {
        Denuncia denuncia = new Denuncia();

        System.out.print("Numero de documento del ciudadano: ");
        denuncia.setNumeroDocumento(sc.nextLine().trim());

        System.out.print("Telefono celular: ");
        denuncia.setTelefonoCelular(sc.nextLine().trim());

        System.out.print("Fecha y hora del incidente (yyyy-MM-dd HH:mm:ss): ");
        denuncia.setFechaHoraIncidente(sc.nextLine().trim());

        System.out.print("Lugar del incidente: ");
        denuncia.setLugarIncidente(sc.nextLine().trim());

        denuncia.setLatitud(leerDoubleOpcional("Latitud (Enter si no aplica): "));
        denuncia.setLongitud(leerDoubleOpcional("Longitud (Enter si no aplica): "));

        System.out.print("Descripcion detallada: ");
        denuncia.setDescripcionDetallada(sc.nextLine().trim());

        System.out.print("Ruta de la prueba adjunta (Enter si no aplica): ");
        String ruta = sc.nextLine().trim();
        denuncia.setRutaPruebaAdjunta(ruta.isEmpty() ? null : ruta);

        System.out.print("Estado actual (Enter para 'En proceso'): ");
        String estado = sc.nextLine().trim();
        denuncia.setEstadoActual(estado.isEmpty() ? "En proceso" : estado);

        System.out.print("Descripcion del estado actual (Enter si no aplica): ");
        String descripcionEstado = sc.nextLine().trim();
        denuncia.setDescripcionEstado(descripcionEstado.isEmpty() ? null : descripcionEstado);

        return denuncia;
    }

    private Double leerDoubleOpcional(String mensaje) {
        System.out.print(mensaje);
        String valor = sc.nextLine().trim();

        if (valor.isEmpty()) {
            return null;
        }

        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            System.out.println("Valor no valido.");
            return null;
        }
    }
}