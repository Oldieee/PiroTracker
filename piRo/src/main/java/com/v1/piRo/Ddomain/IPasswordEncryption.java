package com.v1.piRo.Ddomain;

public interface IPasswordEncryption {
    String encode(String rawPassword);
    boolean matches(String rawPassword,String encodedPassword);
}
