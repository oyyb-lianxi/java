package com.example.model.dict;

public interface CommonEnum {
    String getCode(); // 因为code可能是任意类型，所以用Object
    String getDesc();
}
