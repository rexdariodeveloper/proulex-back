package com.pixvs.spring.services;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @see "https://www.baeldung.com/java-generating-barcodes-qr-codes"
 * @see "https://www.websparrow.org/spring/generate-qr-code-using-spring-boot-rest-api"
 */
public interface QRCodeService {

    ByteArrayOutputStream generate(String text, int width, int height);

    ByteArrayOutputStream  generate(String text, int size);

    ByteArrayOutputStream  generate(String text);

    void download(HttpServletResponse response, ByteArrayOutputStream qr, String nameFile) throws IOException;

}
