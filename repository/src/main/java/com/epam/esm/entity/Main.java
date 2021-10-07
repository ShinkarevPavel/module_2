package com.epam.esm.entity;

import com.epam.esm.configuration.SpringDataSourceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQueryBuilder;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringDataSourceConfiguration.class);
        GiftCertificateDao dao = context.getBean("giftCertificateDaoImpl", GiftCertificateDaoImpl.class);
        LinkedHashSet<Long> ids = new LinkedHashSet<>();
        ids.add(12l);
        ids.add(2l);
        ids.add(2l);
        ids.add(12l);
        ids.add(12l);
        ids.add(1l);
        ids.add(1l);
        System.out.println(ids);
        LinkedHashSet<String> s = new LinkedHashSet<String>();

//        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();
//        String tagName = "IT";
//        String partSearch = "cool";
//        List<String> searchFields = new ArrayList<>();
//        searchFields.add("gc.name");
//        searchFields.add("description");
//         searchFields.stream().map(String::toUpperCase).collect(Collectors.toList());
//        System.out.println(searchFields);
//        List<String> sortingBy = new ArrayList<>();
//        sortingBy.add("DESC");
//        System.out.println(sqlQueryBuilder.buildQueryForSearchAndSort(tagName, partSearch, searchFields, sortingBy));
//        System.out.println("----------------------------------");
//        System.out.println(dao.findByCertificateFieldAndSort(tagName, partSearch, searchFields, sortingBy));


    }
}
