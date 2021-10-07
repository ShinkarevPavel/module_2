package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/v1/certificates")
public class GiftController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getById(@PathVariable Long id) {
        return giftCertificateService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto certificateDto) {
        return giftCertificateService.create(certificateDto);
    }

    @PatchMapping //
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateService.update(giftCertificateDto);
    }

    @GetMapping
    public List<GiftCertificateDto> findByAttributes(@RequestParam(required = false, name = "tagName") String tagName,
                                                     @RequestParam(required = false, name = "searchPart") String searchPart,
                                                     @RequestParam(required = false, name = "fieldsForSort") List<String> fieldsForSort,
                                                     @RequestParam(required = false, name = "orderSort") List<String> orderSort) {
        return giftCertificateService.findByAttributes(tagName, searchPart, fieldsForSort, orderSort);
    }
}