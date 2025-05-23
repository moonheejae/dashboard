package com.cknb.htPlatform.common;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AesConverter implements AttributeConverter<String, String> {

    private final Aes256Utils aes256;

    public AesConverter() throws Exception {
        aes256 = new Aes256Utils();
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return aes256.encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return aes256.decrypt(dbData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt", e);
        }
    }
}
