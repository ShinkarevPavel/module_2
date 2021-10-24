package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/certificates")
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
    public GiftCertificateDto create(@Validated(GiftCertificateDto.Create.class) @RequestBody GiftCertificateDto certificateDto) {
        return giftCertificateService.create(certificateDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id,@Validated(GiftCertificateDto.Update.class) @RequestBody GiftCertificateDto giftCertificateDto)  {
        giftCertificateDto.setId(id);
        try {
            giftCertificateService.update(giftCertificateDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        giftCertificateService.delete(id);
    }

    @GetMapping
    public List<GiftCertificateDto> findByAttributes(@RequestParam(required = false, name = "tagName") List <String> tagName,
                                                     @RequestParam(required = false, name = "searchPart") String searchPart,
                                                     @RequestParam(required = false, name = "fieldsForSort") List<String> fieldsForSort,
                                                     @RequestParam(required = false, name = "orderSort") List<String> orderSort) {
        return giftCertificateService.findByAttributes(tagName, searchPart, fieldsForSort, orderSort);
    }
}