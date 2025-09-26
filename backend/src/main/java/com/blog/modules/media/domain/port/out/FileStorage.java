package com.blog.modules.media.domain.port.out;

import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;

public interface FileStorage {

    String store(MultipartFile file, String path) throws IOException, java.io.IOException;

    void delete(String path) throws IOException, java.io.IOException;

    byte[] retrieve(String path) throws IOException, java.io.IOException;
}
