package Crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabrielhs
 */
public class EmpleadoDAO implements IDAO<Empleado> {

    private ConexionDB con;
    private ResultSet rs;
    Empleado emple = new Empleado();

    @Override
    public boolean ingresar(Empleado Pojo) {
        String insert = "INSERT INTO empleados (id,nombre,direccion,telefono) VALUES (?,?,?,?)";
        try {
            try (PreparedStatement sentencia = ConexionDB.getInstance().getConnection().prepareStatement(insert)) {
                Long id = Pojo.getId();
                String nombre = Pojo.getNombre();
                String direccion = Pojo.getDireccion();
                String telefono = Pojo.getTelefono();
                sentencia.setLong(1, id);
                sentencia.setString(2, nombre);
                sentencia.setString(3, direccion);
                sentencia.setString(4, telefono);
                sentencia.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean actualizar(Empleado Pojo) {
        String update = "UPDATE empleados SET nombre=?, direccion=?, telefono=? WHERE id=?";
        try {
            try (PreparedStatement sentencia = con.getConnection().prepareStatement(update)) {
                Long id = Pojo.getId();
                String nombre = Pojo.getNombre();
                String direccion = Pojo.getDireccion();
                String telefono = Pojo.getTelefono();

                sentencia.setString(1, nombre);
                sentencia.setString(2, direccion);
                sentencia.setString(3, telefono);
                sentencia.setLong(4, id);

                sentencia.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean eliminar(Long id) {
        String delete = "DELETE FROM empleados WHERE id=?";
        try {
            try (PreparedStatement sentencia = con.getConnection().prepareStatement(delete)) {
                sentencia.setLong(1, id);
                sentencia.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Empleado mostrarById(Long id) {
        String selectAll = "SELECT * FROM empleados WHERE id =?";
        try {
            PreparedStatement sentencia = con.getConnection().prepareStatement(selectAll);
            sentencia.setLong(1, id);
            rs = sentencia.executeQuery();
            if (rs.next()) {
                Empleado p = new Empleado();
                p.setId(rs.getLong(1));
                p.setNombre(rs.getString(2));
                p.setDireccion(rs.getString(3));
                p.setTelefono(rs.getString(4));
                emple = p;
            } else {

                emple = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emple;
    }

    @Override
    public List<Empleado> mostrarAll() {
        String selectAll = "SELECT * FROM empleados ORDER BY id";
        List<Empleado> listaPer = new ArrayList<>();
        try {
            PreparedStatement sentencia = con.getConnection().prepareStatement(selectAll);
            rs = sentencia.executeQuery();
            while (rs.next()) {
                Empleado p = new Empleado();
                p.setId(rs.getLong(1));
                p.setNombre(rs.getString(2));
                p.setDireccion(rs.getString(3));
                p.setTelefono(rs.getString(4));
                listaPer.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPer;
    }

}
