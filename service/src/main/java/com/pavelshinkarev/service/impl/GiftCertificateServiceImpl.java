package com.pavelshinkarev.service.impl;

import com.pavelshinkarev.dao.GiftCertificateDao;
import com.pavelshinkarev.entity.GiftCertificate;
import com.pavelshinkarev.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Builder
@AllArgsConstructor
public class GiftCertificateServiceImpl implements BaseService {

    private GiftCertificateDao giftCertificateDao;

    @Override
    public List<GiftCertificate> getAll() {
        return giftCertificateDao.findAll();
    }
}
