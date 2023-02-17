package com.example.redsocks.services;

import com.example.redsocks.model.Socks;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

public class SocksDeserializer extends KeyDeserializer {
    @Override
    public Socks deserializeKey(String key, DeserializationContext deserializationContext) {
        return new Socks(key);
    }
}
