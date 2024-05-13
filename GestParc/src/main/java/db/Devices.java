package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Devices {

    public List<Device> getAllDevices() {
        List<Device> devices = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestparc", "root", "");
            String query = "SELECT d.id, d.name, d.serial_number, d.operating_system, d.model, u.username AS user_username FROM devices AS d JOIN users AS u ON d.user_id = u.id";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Device device = new Device();
                device.setId(rs.getInt("id"));
                device.setName(rs.getString("name"));
                device.setSerialNumber(rs.getString("serial_number"));
                device.setOperatingSystem(rs.getString("operating_system"));
                device.setModel(rs.getString("model"));
                device.setUserUsername(rs.getString("user_username"));
                devices.add(device);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return devices;
    }

    public String add(String name, String serial_number, String operating_system, String model, String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String message = "";
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestparc", "root", "");
            // Récupérer l'ID de l'utilisateur à partir de son nom d'utilisateur
            String getUserIdQuery = "SELECT id FROM users WHERE username = ?";
            stmt = conn.prepareStatement(getUserIdQuery);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt("id");
            } else {
                message = "Utilisateur non existant";
                return(message);
            }
            // Insérer une nouvelle entrée dans la table devices avec l'ID de l'utilisateur
            String insertDeviceQuery = "INSERT INTO devices (name, serial_number, operating_system, model, user_id) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertDeviceQuery);
            stmt.setString(1, name);
            stmt.setString(2, serial_number);
            stmt.setString(3, operating_system);
            stmt.setString(4, model);
            stmt.setInt(5, userId);
            int update = stmt.executeUpdate();
            if (update > 0) {
                message = "La machine a été ajoutée avec succès";
            } else {
                message = "Il y a eu un problème lors de l'ajout";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public void updateDevice(Device device) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestparc", "root", "");
            String query = "UPDATE devices SET name = ?, serial_number = ?, operating_system = ?, model = ?, user_id = ? WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, device.getName());
            stmt.setString(2, device.getSerialNumber());
            stmt.setString(3, device.getOperatingSystem());
            stmt.setString(4, device.getModel());
            stmt.setString(5, device.getUserUsername());
            stmt.setInt(6, device.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDevice(Device device) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestparc", "root", "");
            String query = "DELETE FROM devices WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, device.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public int getDeviceCountForUser(int userId) {
        int deviceCount = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestparc", "root", "");
            String query = "SELECT COUNT(*) AS deviceCount FROM devices WHERE user_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                deviceCount = rs.getInt("deviceCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return deviceCount;
    }
    public List<Device> getDevicesByUserId(int userId) {
        List<Device> devices = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestparc", "root", "");
            String query = "SELECT * FROM devices WHERE user_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Device device = new Device();
                device.setId(rs.getInt("id"));
                device.setName(rs.getString("name"));
                device.setSerialNumber(rs.getString("serial_number"));
                device.setOperatingSystem(rs.getString("operating_system"));
                device.setModel(rs.getString("model"));
                devices.add(device);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return devices;
    }
}

