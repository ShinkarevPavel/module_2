package com.epam.esm.linkbuilder;

import org.springframework.hateoas.RepresentationModel;

public interface LinkBuilder<T extends RepresentationModel<T>> {
    void addLinks(T t);
}
