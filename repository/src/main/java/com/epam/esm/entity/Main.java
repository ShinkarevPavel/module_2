package com.epam.esm.entity;

import com.epam.esm.configuration.SpringDataSourceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main  {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringDataSourceConfiguration.class);
        GiftCertificateDao dao = context.getBean("giftCertificateDaoImpl", GiftCertificateDaoImpl.class);
////        TagDao tagDao = context.getBean("tagDaoImpl", TagDao.class);
//        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
//        LocalDateTime dateTime = LocalDateTime.now();
//        String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
//        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
//        System.out.println(dateTime.format(format));
    }
}
