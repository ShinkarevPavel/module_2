package com.pavelshinkarev.controller;

import com.pavelshinkarev.entity.GiftCertificate;
import com.pavelshinkarev.service.BaseService;
import com.pavelshinkarev.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gifts")
public class GiftController {

    private final BaseService giftCertificateService;

    @Autowired
    public GiftController(GiftCertificateServiceImpl giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> showAllCertificates() {
        List<GiftCertificate> certificates = giftCertificateService.getAll();
        System.out.println(certificates);
        return certificates;
    }
}
