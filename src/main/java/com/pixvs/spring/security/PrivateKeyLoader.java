package com.pixvs.spring.security;

import com.google.common.io.ByteStreams;
import com.pixvs.spring.handler.exceptions.KeyException;
import org.apache.commons.ssl.PKCS8Key;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateKeyLoader implements KeyLoader {

    PrivateKey key;

    public PrivateKeyLoader(String privateKeyLocation, String keyPassword) {
        this.setPrivateKey(privateKeyLocation, keyPassword);
    }


    public PrivateKeyLoader(InputStream privateKeyInputStream, String keyPassword) {
        this.setPrivateKey(privateKeyInputStream, keyPassword);
    }


    /**
     * @param privateKeyLocation    private key located in filesystem
     * @param keyPassword           private key password
     *
     * @throws KeyException thrown when any security exception occurs
     */
    public void setPrivateKey(String privateKeyLocation, String keyPassword) {

        InputStream privateKeyInputStream = null;

        try {
            privateKeyInputStream = new FileInputStream(privateKeyLocation);
        }catch (FileNotFoundException fnfe){
            throw new KeyException("La ubicación del archivo de la llave privada es incorrecta", fnfe.getCause());
        }

        this.setPrivateKey(privateKeyInputStream, keyPassword);
    }


    /**
     *
     * @param privateKeyInputStream private key's input stream
     * @param keyPassword           private key password
     *
     * @throws KeyException thrown when any security exception occurs
     */
    public void setPrivateKey(InputStream privateKeyInputStream, String keyPassword) {

        byte[] privateKeyByte = this.extractProtectedPrivateKey(privateKeyInputStream, keyPassword);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);

        try {
            this.key = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        }catch (GeneralSecurityException gse) {
            throw new KeyException(
                    "Error al obtener la información del certificado debido a su codificación",
                    gse.getCause());
        }
    }




    private byte[] extractProtectedPrivateKey(InputStream privateKeyInputStream, String keyPassword) {
        byte[] bytes;

        try {
            if(keyPassword == null) {
                bytes = ByteStreams.toByteArray(privateKeyInputStream);
            } else {
                bytes = new PKCS8Key(privateKeyInputStream, keyPassword.toCharArray()).getDecryptedBytes();
            }
        } catch (GeneralSecurityException e) {
            throw new KeyException("La contraseña del certificado no es correcta", e.getCause());
        } catch (IOException ioe){
            throw new KeyException(ioe.getMessage(), ioe.getCause());
        }

        return bytes;
    }

    @Override
    public <T> T getKey() {
        return (T) this.key;
    }
}
