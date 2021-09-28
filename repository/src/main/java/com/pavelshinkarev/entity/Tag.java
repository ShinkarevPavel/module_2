package com.pavelshinkarev.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
//@Scope("prototype") ToDo
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private long id;
    private String name;
}
