package com.adaptive.router.adaptive_rest_router.service;

import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import java.util.Base64;


@Service
public class CompressionService {

    public String comprimir(String body) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);

            gzipOutputStream.write(body.getBytes());
            gzipOutputStream.close();

            System.out.println("body = " + body);
            byte[] compressedBytes = byteArrayOutputStream.toByteArray();
            System.out.println(compressedBytes.length);


            // Codifica em Base64 para manter um texto seguro para JSON
            return Base64.getEncoder().encodeToString(compressedBytes);

        } catch (IOException e) {
            //may be replaced by a more robust logging, when we have a db ...
            e.printStackTrace();
            return body; // fallback
        }
    }
}

