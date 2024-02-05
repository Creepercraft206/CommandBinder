package Utils.SQL;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;

public abstract class MySQLHandler {
    protected Connection con;
    protected String host;
    protected String port;
    protected String database;
    protected String username;
    protected String password;

    public MySQLHandler(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        connect();
        createTables();
    }

    private void connect() {
        if (!this.isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
                System.out.println("Die MySQL Verbindung wurde erfolgreich aufgebaut.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (isConnected()) {
            try {
                con.close();
                System.out.println("Die MySQL Verbindung wurde erfolgreich geschlossen.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return con != null;
    }

    public void update(String query) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert(String table, String[] values) {
        try {
            String in = "?";

            for(int i = 1; i < values.length; ++i) {
                in += ", ?";
            }

            PreparedStatement State = con.prepareStatement("INSERT INTO " + table + " values(" + in + ");");

            for(int index = 1; index <= values.length; ++index) {
                State.setString(index, values[index - 1]);
            }

            State.execute();
            State.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(String table, String[] columns, String[] values) {
        try {
            ResultSet rs = getResult("SELECT * FROM " + table + " LIMIT 1");
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String[] allColumns = new String[columnCount];
            String[] allValues = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                allColumns[i - 1] = rsmd.getColumnName(i);
                if (rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.BIGINT || rsmd.getColumnType(i) == Types.SMALLINT || rsmd.getColumnType(i) == Types.TINYINT || rsmd.getColumnType(i) == Types.BIT) {
                    allValues[i - 1] = "1";
                } else {
                    allValues[i - 1] = "NULL";
                }
            }

            for (int i = 0; i < columns.length; i++) {
                columns[i] = "'" + columns[i] + "'";
                int index = Arrays.asList(allColumns).indexOf(columns[i]);
                if (index != -1) {
                    allValues[index] = values[i];
                }
            }

            String cols = String.join(", ", allColumns);
            String placeholders = String.join(", ", allValues);

            PreparedStatement State = con.prepareStatement("INSERT INTO " + table + " (" + cols + ") VALUES (" + placeholders + ")");
            System.out.println("INSERT INTO " + table + " (" + cols + ") VALUES (" + placeholders + ")");

            State.execute();
            State.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String table, String[] values) {
        try {
            String query = "DELETE FROM " + table + " WHERE ";
            for (int i = 0; i < values.length; i++) {
                query += values[i] + " = ?";
                if (i < values.length - 1) {
                    query += " AND ";
                }
            }

            PreparedStatement State = con.prepareStatement(query);

            for(int index = 1; index <= values.length; ++index) {
                State.setString(index, values[index - 1]);
            }

            State.execute();
            State.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String table, String[] columns, String[] values) {
        try {
            if (columns.length != values.length) {
                throw new IllegalArgumentException("Columns and values arrays must have the same length");
            }

            String query = "DELETE FROM " + table + " WHERE ";
            for (int i = 0; i < columns.length; i++) {
                query += columns[i] + " = ?";
                if (i < columns.length - 1) {
                    query += " AND ";
                }
            }

            PreparedStatement State = con.prepareStatement(query);

            for(int index = 1; index <= values.length; ++index) {
                State.setString(index, values[index - 1]);
            }

            State.execute();
            State.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult(String query) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isRegistered(String query) {
        boolean bool = false;

        try {
            ResultSet rs = getResult(query);

            try {
                bool = rs.next();
            } catch (SQLException var8) {
                bool = false;
            } finally {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bool;
    }

    public abstract void createTables();
}