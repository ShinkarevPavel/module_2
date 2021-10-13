package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private TagService tagService;


    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> getAll() {
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public TagDto getById(@PathVariable Long id) {
        return tagService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tagService.delete(id);
    }

    @GetMapping(params = "name")
    public TagDto findByName(@RequestParam(value = "name") String name) {
        return tagService.getByName(name);
    }


}
