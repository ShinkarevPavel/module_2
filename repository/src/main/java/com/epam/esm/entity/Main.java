package com.epam.esm.entity;

import com.epam.esm.configuration.SpringDataSourceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQueryBuilder;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class Main  {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringDataSourceConfiguration.class);
        GiftCertificateDao dao = context.getBean("giftCertificateDaoImpl", GiftCertificateDaoImpl.class);
        Map<String, Object> map = new HashMap<>();
        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
        map.put(queryBuilder.getPRICE(), 500);
        map.put(queryBuilder.getLAST_UPDATE_DATE(), LocalDateTime.now());

        System.out.println(queryBuilder.buildQueryForUpdate(map));
////        TagDao tagDao = context.getBean("tagDaoImpl", TagDao.class);
//        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
//        LocalDateTime dateTime = LocalDateTime.now();
//        String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
//        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
//        System.out.println(dateTime.format(format));

//
//        Map<String, String> map = new HashMap<>();
//        List<String> mappa = map.keySet().stream().collect(Collectors.toList());
//        Set<String> strings = new HashSet<>();
//        strings.add("name");
//        strings.add("id");
//        strings.add("description");
//
//        List<String> stringList = strings.stream().toList();
//        System.out.println(stringList.get(1));

    }
}
