package act5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

/**
 * <h1>Activitat 4</h1>
 * Prepara un string on incloguis la instrucció sql, que ha de permetre actualitzar el nom de l'autor de la ID que s'indiqui.
 * Crear el Prepared Statement, per tal de precompilar la sentència SQL del punt anterior. (prepareStatement(string sentenciasqlprepared);)
 * Usa la sentencia precompilada, passant-li els paràmetres que pertoqui per tal d'actualitzar el ID=1000 amb el nom de John Brown. Per això, a l'exercici 3, torna a afegir els tres autors que havies eliminat en bloc. (executeUpdate();)
 * Tanca tots els recursos.
 */

public class Act5 {
    public static void main(String[] args) throws SQLException {
        try (var connection = getConnection()) {
            connection.setAutoCommit(false);

            var preparedStatement = connection.prepareCall(
                    "INSERT biblioteca.AUTORS VALUE (?, ?, ?, 'CATALANA', null)"
            );

            try (var statement = connection.createStatement()) {
                var result = statement.executeQuery("SELECT NOM_AUT FROM biblioteca.AUTORS");

                while (result.next()) {
                    System.out.println(result.getString("NOM_AUT"));
                }
            }

            preparedStatement.setInt(1, new Random().nextInt(Integer.MAX_VALUE));
            preparedStatement.setString(2, "Alex");
            preparedStatement.setString(3, "2000-01-01");

            System.out.println("Quantitat d'autors: " + autorLength(connection));

            var affected = preparedStatement.executeUpdate();

            isSuccessful(affected);

            System.out.println("Quantitat d'autors: " + autorLength(connection));

            System.out.println("Rolling back...");
            connection.rollback();

            System.out.println("Quantitat d'autors: " + autorLength(connection));

            affected = preparedStatement.executeUpdate();
            isSuccessful(affected);

            System.out.println("Committing...");
            connection.commit();

            System.out.println("Quantitat d'autors: " + autorLength(connection));

            preparedStatement.setInt(1, new Random().nextInt(Integer.MAX_VALUE));
            preparedStatement.setString(2, "Andreu Garcia");
            affected = preparedStatement.executeUpdate();
            isSuccessful(affected);

            System.out.println("Created Savepoint");
            var savepointAndreu = connection.setSavepoint();

            System.out.println("Quantitat d'autors: " + autorLength(connection));

            preparedStatement.setInt(1, new Random().nextInt(Integer.MAX_VALUE));
            preparedStatement.setString(2, "Marc");
            affected = preparedStatement.executeUpdate();
            isSuccessful(affected);
            System.out.println("Quantitat d'autors: " + autorLength(connection));

            System.out.println("Rolling back");
            connection.rollback(savepointAndreu);

            System.out.println("Quantitat d'autors: " + autorLength(connection));
        }
    }

    private static void isSuccessful(int affected) {
        if (affected == 1) {
            System.out.println("Insertion successful");
        } else {
            System.out.println("Insertion failed");
            System.exit(1);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "CalaClara21.");
    }

    private static int autorLength(Connection connection) throws SQLException {
        try (var statement = connection.createStatement()) {
            var result = statement.executeQuery("SELECT NOM_AUT FROM biblioteca.AUTORS");

            int count = 0;
            while (result.next()) {
                count++;
            }

            return count;
        }
    }
}
