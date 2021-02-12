package me.langner.jonas.game.storage;

import java.sql.*;
import java.util.*;

/**
 * Handles connection between plugin and database.
 * @author Jonas Langner
 * @version Alpha
 * @since 12.02.2021 (Alpha)
 */
public abstract class MySQL {

    private Connection connection;

    /**
     * Creates a connection to the database.
     * @param host The host.
     * @param port The port.
     * @param database The name of the database.
     * @param user The username.
     * @param password The password of the user.
     * @throws SQLException Throws this exception if connection failed.
     */
    public MySQL(String host, int port, String database, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://" +
                        host +
                        ":" +
                        port +
                        "/" +
                        database +
                        "?autoReconnect=true",
                user,
                password
        );

        /* zu sql-set hinzufügen */
        mySQLSet.add(this);

        /* Tabellen erstellen */
        createTables();
    }

    /**
     * Creates all necessary tables.
     */
    public abstract void createTables();

    /**
     * Closes the connection between plugin and database.
     * @throws SQLException Throws this exception if error occurred while disconnecting.
     */
    public void disconnect() throws SQLException {
        connection.close();
    }

    /**
     * Executes query and tries to put the result into a list.
     * @param preparedStatement The statement/request.
     * @return Returns the result as mapping or null. <br/>
     * The collection stores the records, the objects are the values of each record.
     * @throws SQLException Throws this exception if statement is not a query.
     */
    private List<Object[]> executeQuery(PreparedStatement preparedStatement) throws SQLException {
        ResultSet set = preparedStatement.executeQuery();

        List<Object[]> records = new ArrayList<>();

        /* wenn es eine nächste Zeile gibt */
        while (set.next()) {
            // es gibt eine nächste Zeile
            Object[] values = new Object[set.getMetaData().getColumnCount()];

            /* für jede Spalte Daten einsetzen */
            for (int columnIndex = 1; columnIndex <= values.length; columnIndex++) {
                            /* speichern
                            Verschiebung um 1 beachten!
                             */

                values[columnIndex - 1] = set.getObject(columnIndex);
            }

            /* speichern */
            records.add(values);
        }

        /* Liste ausgeben */
        return records;
    }

    /**
     * Sends a statement or request to the database.
     * @param syntax The statement/request.
     * @param ttl Amount of attempts. It could happen, that some request fail at the first time. Max attempts: 5.
     * @return Returns the result as mapping or null. <br/>
     * The collection stores the records, the objects are the values of each record.
     * @throws SQLException Throws this, if syntax is wrong.
     */
    public List<Object[]> query(String syntax, int ttl) throws SQLException {

        /* maximale Versuche sind 5 -> auf 5 setzen, wenn zu viele */
        if (ttl > 5)
            ttl = 5;

        /* ermitteln, ob noch lebend */
        if (ttl > 0) {
            /* Anweisung erstellen */
            try {
                PreparedStatement statement = connection.prepareStatement(syntax);

                /* erstmal versuchen, Daten zu bekommen */
                try {
                    // Es kamen tatsächlich Daten zurück
                    return executeQuery(statement);
                } catch (Exception e) {
                    // Nur Anweisung
                    statement.executeUpdate();
                }

            } catch (SQLException ex) {
                throw ex;
            }

        }

        /* ermitteln, ob es sinn hat, noch einmal drüber zu gehen */
        if (ttl > 1) {
            // hat sinn: noch einmal probieren
            return query(syntax, --ttl);
        }

        // keinen Sinn mehr: null zurückgeben
        return null;
    }

    /**
     * Sends a statement or request to the database. <br/>Tries a second time, if the result is null.
     * @param syntax The statement/request.
     * @return Returns the result as mapping or null. <br/>
     * The collection stores the records, the objects are the values of each record.
     * @throws SQLException Throws this, if syntax is wrong.
     */
    public List<Object[]> query(String syntax) throws SQLException {
        /* 2 Versuche wegen Disconnect */
        return query(syntax,2);
    }



    private static Set<MySQL> mySQLSet = new HashSet<>();

    public static Set<MySQL> getMySQLSet() {
        return Collections.unmodifiableSet(mySQLSet);
    }
}
