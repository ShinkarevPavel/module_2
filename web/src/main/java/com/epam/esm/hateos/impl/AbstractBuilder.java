package com.epam.esm.hateos.impl;

import com.epam.esm.hateos.LinkBuilder;
import org.springframework.hateoas.RepresentationModel;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class AbstractBuilder<T extends RepresentationModel<T>> implements LinkBuilder<T> {
    protected static final String DELETE = "delete";
    protected static final String UPDATE = "update";
    protected static final String GET = "get";

    protected void addIdLink(Class<?> controllerClass, T entity, long id, String linkName) {
        entity.add(linkTo(controllerClass).slash(id).withRel(linkName));
    }

    protected void addIdLinks(Class<?> controllerClass, T entity, long id, String... linkNames) {
        Arrays.stream(linkNames).forEach(linkName -> addIdLink(controllerClass, entity, id, linkName));
    }
}
