package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Tag tag = Tag.builder()
                .id(1l)
                .name("IT")
                .build();

        Tag tag1 = Tag.builder()
                .id(1l)
                .name("")
                .build();
        Tag tag2 = Tag.builder()
                .id(2l)
                .name("IT")
                .build();


        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tag);
        tagSet.add(tag1);
        tagSet.add(tag2);
        System.out.println(tagSet);
    }
}
