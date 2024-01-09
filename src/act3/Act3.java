package act3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <h1>Activitat 3</h1>
 * Es volen afegir en bloc els següents actors: primer autor ID=1000, Jaume Ximenes, nascut el 21 de novembre de 1997 i nacionalitat irlandesa, segon autor, ID=1001, Antoni Villalonga, nascut el 30 de juny de 1980 i nacionalitat catalana, i finalment, el darrer autor, , ID=1002, Mary Smith, nascuda el 5 de febrer de 1989 i nacionalitat anglesa. (INSERT, addBatch("instrucciosql");)
 * Executa les operacions afegides al buffer. (executeBatch();)
 * Fes el mateix que els punts a i b anteriors, del mateix exercici, però amb borrat en bloc dels autors que hagis creat abans.
 * Tanca tots els recursos.
 */


public class Act3 {
    public static void main(String[] args) throws SQLException {
        try (var statement = getConnection().createStatement()) {

            statement.addBatch("INSERT INTO biblioteca.AUTORS VALUES (1000, 'Jaume Ximenes', '1997-11-21', 'POLACA', null)");
            statement.addBatch("INSERT INTO biblioteca.AUTORS VALUES (1001, 'Antoni Villalonga', '1980-7-30', 'CATALANA', null)");
            statement.addBatch("INSERT INTO biblioteca.AUTORS VALUES (1002, 'Mary Smith', '1989-2-5', 'ANGLESA', null)");

            var affected = statement.executeBatch();

            areSuccessful(affected);

            statement.addBatch("DELETE FROM biblioteca.AUTORS WHERE ID_AUT = 1000");
            statement.addBatch("DELETE FROM biblioteca.AUTORS WHERE ID_AUT = 1001");
            statement.addBatch("DELETE FROM biblioteca.AUTORS WHERE ID_AUT = 1002");

            affected = statement.executeBatch();

            areSuccessful(affected);
        }

    }

    private static void areSuccessful(int[] affected) {
        for (int i : affected) {
            isSuccessful(i);
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