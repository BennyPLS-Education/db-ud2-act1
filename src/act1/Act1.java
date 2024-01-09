package act1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <h1>Activitat 1</h1>
 * Crea la connexió jdbc amb la base de dades que està al servidor local. Has de (DriverManager.getConnection("direcció","usuari","contrasenya");)
 * Crea la sentència necessaria per tal de cridar els mètodes on executar les instruccions SQL. (createStatement();)
 * Executa una instrucció SQL de tipus SELECT per veure tota la info de la taula autors. (executeQuery("instrucció"))
 * Amb el ResultSet recollit, mostra per consola tota la informació obtinguda desde la base de dades biblioteca. Per obtenir-la utilitza un 'while'.
 * Tanca tots els recursos.
 */

public class Act1 {
    public static void main(String[] args) throws SQLException {
        try (var statement = getConnection().createStatement()) {
            var result = statement.executeQuery("SELECT * FROM biblioteca.AUTORS");
            printAll(result);
        }
    }

    private static void printAll(ResultSet result) throws SQLException {
        var columnCount = result.getMetaData().getColumnCount();
        while (result.next()) {
            for (int i = 0; i < columnCount; i++) {
                System.out.print(result.getString(i + 1) + " ");
            }
            System.out.println();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "CalaClara21.");
    }
}