package dao;

import conexion.Conexion;
import modelo.Denuncia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class DenunciaDAO {

    private Integer obtenerIdCiudadanoPorDocumento(String numeroDocumento) {
        String sql = "SELECT id_usuario FROM usuarios WHERE numero_documento = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, numeroDocumento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_usuario");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el ciudadano: " + e.getMessage());
        }

        return null;
    }

    private void setStringOrNull(PreparedStatement ps, int index, String valor) throws SQLException {
        if (valor == null || valor.isBlank()) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, valor);
        }
    }

    private void setDoubleOrNull(PreparedStatement ps, int index, Double valor) throws SQLException {
        if (valor == null) {
            ps.setNull(index, Types.DECIMAL);
        } else {
            ps.setDouble(index, valor);
        }
    }

    public boolean insertar(Denuncia denuncia) {
        Integer idCiudadano = obtenerIdCiudadanoPorDocumento(denuncia.getNumeroDocumento());

        if (idCiudadano == null) {
            System.out.println("No existe un ciudadano con ese numero de documento.");
            return false;
        }

        String sql = "INSERT INTO denuncias "
                + "(numero_radicado, id_ciudadano, telefono_celular, fecha_hora_incidente, "
                + "lugar_incidente, latitud, longitud, descripcion_detallada, ruta_prueba_adjunta, "
                + "estado_actual, descripcion_estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, denuncia.getNumeroRadicado());
            ps.setInt(2, idCiudadano);
            ps.setString(3, denuncia.getTelefonoCelular());
            ps.setTimestamp(4, Timestamp.valueOf(denuncia.getFechaHoraIncidente()));
            ps.setString(5, denuncia.getLugarIncidente());
            setDoubleOrNull(ps, 6, denuncia.getLatitud());
            setDoubleOrNull(ps, 7, denuncia.getLongitud());
            setStringOrNull(ps, 8, denuncia.getDescripcionDetallada());
            setStringOrNull(ps, 9, denuncia.getRutaPruebaAdjunta());

            String estado = denuncia.getEstadoActual();
            if (estado == null || estado.isBlank()) {
                estado = "En proceso";
            }
            ps.setString(10, estado);

            setStringOrNull(ps, 11, denuncia.getDescripcionEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar denuncia: " + e.getMessage());
            return false;
        }
    }

    public Denuncia buscarPorRadicado(String numeroRadicado) {
        String sql = "SELECT d.id_denuncia, d.numero_radicado, d.id_ciudadano, "
                + "u.numero_documento, u.nombres_apellidos, d.telefono_celular, "
                + "d.fecha_hora_incidente, d.lugar_incidente, d.latitud, d.longitud, "
                + "d.descripcion_detallada, d.ruta_prueba_adjunta, d.estado_actual, "
                + "d.descripcion_estado "
                + "FROM denuncias d "
                + "INNER JOIN usuarios u ON d.id_ciudadano = u.id_usuario "
                + "WHERE d.numero_radicado = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, numeroRadicado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Denuncia d = new Denuncia();
                    d.setIdDenuncia(rs.getInt("id_denuncia"));
                    d.setNumeroRadicado(rs.getString("numero_radicado"));
                    d.setIdCiudadano(rs.getInt("id_ciudadano"));
                    d.setNumeroDocumento(rs.getString("numero_documento"));
                    d.setNombresApellidos(rs.getString("nombres_apellidos"));
                    d.setTelefonoCelular(rs.getString("telefono_celular"));
                    d.setFechaHoraIncidente(rs.getString("fecha_hora_incidente"));
                    d.setLugarIncidente(rs.getString("lugar_incidente"));

                    Object lat = rs.getObject("latitud");
                    Object lon = rs.getObject("longitud");
                    if (lat != null) {
                        d.setLatitud(rs.getDouble("latitud"));
                    }
                    if (lon != null) {
                        d.setLongitud(rs.getDouble("longitud"));
                    }

                    d.setDescripcionDetallada(rs.getString("descripcion_detallada"));
                    d.setRutaPruebaAdjunta(rs.getString("ruta_prueba_adjunta"));
                    d.setEstadoActual(rs.getString("estado_actual"));
                    d.setDescripcionEstado(rs.getString("descripcion_estado"));

                    return d;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar denuncia: " + e.getMessage());
        }

        return null;
    }

    public boolean actualizar(Denuncia denuncia) {
        Integer idCiudadano = obtenerIdCiudadanoPorDocumento(denuncia.getNumeroDocumento());

        if (idCiudadano == null) {
            System.out.println("No existe un ciudadano con ese numero de documento.");
            return false;
        }

        String sql = "UPDATE denuncias SET "
                + "id_ciudadano = ?, "
                + "telefono_celular = ?, "
                + "fecha_hora_incidente = ?, "
                + "lugar_incidente = ?, "
                + "latitud = ?, "
                + "longitud = ?, "
                + "descripcion_detallada = ?, "
                + "ruta_prueba_adjunta = ?, "
                + "estado_actual = ?, "
                + "descripcion_estado = ? "
                + "WHERE numero_radicado = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idCiudadano);
            ps.setString(2, denuncia.getTelefonoCelular());
            ps.setTimestamp(3, Timestamp.valueOf(denuncia.getFechaHoraIncidente()));
            ps.setString(4, denuncia.getLugarIncidente());
            setDoubleOrNull(ps, 5, denuncia.getLatitud());
            setDoubleOrNull(ps, 6, denuncia.getLongitud());
            setStringOrNull(ps, 7, denuncia.getDescripcionDetallada());
            setStringOrNull(ps, 8, denuncia.getRutaPruebaAdjunta());

            String estado = denuncia.getEstadoActual();
            if (estado == null || estado.isBlank()) {
                estado = "En proceso";
            }
            ps.setString(9, estado);

            setStringOrNull(ps, 10, denuncia.getDescripcionEstado());
            ps.setString(11, denuncia.getNumeroRadicado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar denuncia: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String numeroRadicado) {
        String sql = "DELETE FROM denuncias WHERE numero_radicado = ?";

        try (Connection cn = Conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, numeroRadicado);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar denuncia: " + e.getMessage());
            return false;
        }
    }
}