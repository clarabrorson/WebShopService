package com.Molndal.WebShopService.Utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Hjälp-klass som innehåller en statisk metod som genererar ett RSA-nyckelpar.
 * Nyckelparet används för att signera och verifiera JWT-token.
 *
 * @author Fredrik
 */

public class KeyGenerator {

    /**
     * Denna metod genererar ett RSA-nyckelpar.
     *
     * @throws IllegalStateException om något går fel.
     * @return ett RSA-nyckelpar.
     */
    public static KeyPair generateRsaKey() {

        KeyPair keyPair;

        try {
            //Skapar en KeyPaiGenerator instans för RSA-algoritmen.
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            //Initialiserar KeyPairGenerator med en nyckelstorlek på 2048 bitar.
            keyPairGenerator.initialize(2048);
            //Genererar ett nyckelpar.
            keyPair = keyPairGenerator.genKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException();
        }
        return keyPair;
    }
}
