package act4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <h1>Activitat 4</h1>
 * Prepara un string on incloguis la instrucció sql, que ha de permetre actualitzar el nom de l'autor de la ID que s'indiqui.
 * Crear el Prepared Statement, per tal de precompilar la sentència SQL del punt anterior. (prepareStatement(string sentenciasqlprepared);)
 * Usa la sentencia precompilada, passant-li els paràmetres que pertoqui per tal d'actualitzar el ID=1000 amb el nom de John Brown. Per això, a l'exercici 3, torna a afegir els tres autors que havies eliminat en bloc. (executeUpdate();)
 * Tanca tots els recursos.
 */

public class Act4 {
    public static void main(String[] args) throws SQLException {
        try (var connection = getConnection()) {
            var statm = connection.prepareCall(
                    "UPDATE biblioteca.AUTORS SET NOM_AUT = ? WHERE ID_AUT = ?"
            );

            statm.setString(1, "John Brown");
            statm.setInt(2, 1000);

            var affected = statm.executeUpdate();

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