package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/api/gifts")
public class GiftController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificateDto> showAllCertificates() {
        List<GiftCertificateDto> certificates = giftCertificateService.getAll();
        return certificates;
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

    @PutMapping
    public GiftCertificateDto update(@RequestBody GiftCertificateDto giftCertificateDto) {
        System.out.println(giftCertificateDto);
        return giftCertificateService.update(giftCertificateDto);
    }
}