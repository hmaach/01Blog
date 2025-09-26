package com.blog.modules.media.infrastructure.adapter.out.storage;

import org.springframework.web.multipart.MultipartFile;

import com.blog.modules.media.domain.port.out.FileStorage;

import io.jsonwebtoken.io.IOException;

public class CloudFileStorage implements FileStorage {

    @Override
    public String store(MultipartFile file, String path) throws IOException, java.io.IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'store'");
    }

    @Override
    public void delete(String path) throws IOException, java.io.IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public byte[] retrieve(String path) throws IOException, java.io.IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieve'");
    }

}
