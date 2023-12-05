package com.Molndal.WebShopService.Utils;

import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

//Klass som hanterar RSA-nyckelpar.
//Annotationen @Component innebär att Spring kommer att skapa en instans av denna klass när applikationen startar
//och denna instans kan sedan injiceras i andra beans med hjälp av @Autowired.
@Component
public class KeyProperties {

    //Dessa fält representerar RSA-nyckelparet (både offentlig och privat nyckel).
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    //Konstruktor som genererar ett RSA-nyckelpar med hjälp av KeyGenerator.
    //Den offentliga och privata nyckeln extraheras och lagras i de motsvarande fälten.
    public KeyProperties() {
        KeyPair keyPair = KeyGenerator.generateRsaKey();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

}
