package com.epam.esm.controller;

import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.UnacceptableRemoveEntityException;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tags")
@Validated
public class TagController {

    private TagService tagService;
    private LinkBuilder<TagDto> linkBuilder;

    @Autowired
    public TagController(TagService tagService, LinkBuilder<TagDto> linkBuilder) {
        this.tagService = tagService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping
    public List<TagDto> getAll(@Valid PageParameterDto pageParameterDto) {
        return tagService.getAll(pageParameterDto).stream()
                .peek(linkBuilder::addLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TagDto getById(@PathVariable Long id) {
        TagDto tagDto = tagService.getById(id);
        linkBuilder.addLinks(tagDto);
        return tagDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @PathVariable Long id) {
        try {
            tagService.delete(id);
        } catch (RuntimeException e) {
            throw new UnacceptableRemoveEntityException("entity.remove.tag");
        }
    }

    @GetMapping("/name")
    public TagDto findByName(@RequestParam(value = "name") String name) {
        TagDto tagDto = tagService.getByName(name);
        linkBuilder.addLinks(tagDto);
        return tagDto;
    }

    @GetMapping("/widest")
    public TagDto getWidelyUsedTagWithHighestOrderCost() {
        TagDto tagDto = tagService.getWidelyUsedTagWithHighestOrderCost();
        linkBuilder.addLinks(tagDto);
        return tagDto;
    }
}
