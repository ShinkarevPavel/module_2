package com.epam.esm.hateos;


import org.springframework.hateoas.RepresentationModel;


public interface LinkBuilder<T extends RepresentationModel<T>> {
    void addLinks(T t);
}
