package com.pixvs.spring.services;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @see "https://www.baeldung.com/java-generating-barcodes-qr-codes"
 * @see "https://www.websparrow.org/spring/generate-qr-code-using-spring-boot-rest-api"
 */

@Service
public class QRCodeServiceImpl implements QRCodeService {

    @Override
    public ByteArrayOutputStream generate(String text, int width, int height) {
        return QRCode.from(text).withSize(width, height).to(ImageType.PNG).stream();
    }

    @Override
    public ByteArrayOutputStream generate(String text, int size) {
        return QRCode.from(text).withSize(size, size).to(ImageType.PNG).stream();
    }

    @Override
    public ByteArrayOutputStream generate(String text) {
        return QRCode.from(text).withSize(250, 250).to(ImageType.PNG).stream();
    }

    @Override
    public void download(HttpServletResponse response, ByteArrayOutputStream qr, String nameFile) throws IOException{
        response.setContentType("image/png");
        response.setContentLength(qr.size());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nameFile + ".png");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        OutputStream outStream = response.getOutputStream();
        outStream.write(qr.toByteArray());
        outStream.flush();
        outStream.close();
    }


}
