package act2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <h1>Activitat 2</h1>
 * Crea un string, on puguis insertar un nou autor amb ID = 1139, de nom Olívia Riutort, nascuda l'1 d'octubre de 2007, i nacionalitat polaca. A continuació, executa la instrucció per tal d'afegir aquest informació a la base de dades  i comprova que s'ha insertat de forma satisfactòria (INSERT, executeUpdate(instruccióSql);)
 * Ara que ja tens el nou autor insertat, crea un string on puguis actualitzar del mateix autor el nom, Arnau Codina. Executa la instrucció i comprova que s'ha realitzat correctament. (UPDATE)
 * Finalment, esborra de la base de dades  l'autor creat. Per això, genera un string que contingui la instrucció SQL, i a continuació executa-la. (molta cura al executar aquest tipus d'instrucció, ens hem d'assegurar que borrarem només la informació desitjada). (DELETE)
 * Tanca tots els recursos.
 */

public class Act2 {
    public static void main(String[] args) throws SQLException {
        try (var statement = getConnection().createStatement()) {
            var affected = statement.executeUpdate("INSERT INTO biblioteca.AUTORS VALUES (1139, 'Olívia Riutort', '2007-10-1', 'POLACA', null)");

            isSuccessful(affected);

            affected = statement.executeUpdate("UPDATE biblioteca.AUTORS SET NOM = 'Arnau Codina' WHERE ID_AUT = 1139");

            isSuccessful(affected);

            affected = statement.executeUpdate("DELETE FROM biblioteca.AUTORS WHERE ID_AUT = 1139");

            isSuccessful(affected);
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


}