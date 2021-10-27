package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.PageParameterDto;
import com.epam.esm.dto.SearchParameterDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/certificates")
public class GiftController {

    private final GiftCertificateService giftCertificateService;
    private final LinkBuilder<GiftCertificateDto> linkBuilder;

    @Autowired
    public GiftController(GiftCertificateService giftCertificateService, LinkBuilder<GiftCertificateDto> linkBuilder) {
        this.giftCertificateService = giftCertificateService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getById(@PathVariable Long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.getById(id);
        linkBuilder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@Validated(GiftCertificateDto.Create.class) @RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.create(certificateDto);
        linkBuilder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @Validated(GiftCertificateDto.Update.class) @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        giftCertificateService.update(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    @GetMapping
    public List<GiftCertificateDto> findByAttributes(SearchParameterDto searchParameterDto,@Valid PageParameterDto pageParameterDto) {
        List<GiftCertificateDto> giftCertificateDto = giftCertificateService.findByAttributes(searchParameterDto, pageParameterDto);
        giftCertificateDto.forEach(linkBuilder::addLinks);
        return giftCertificateDto;
    }
}