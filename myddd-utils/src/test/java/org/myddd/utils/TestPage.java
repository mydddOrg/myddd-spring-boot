package org.myddd.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TestPage {

    @Test
    void testCreatePageByBuilder(){
        Page<Integer> page =  Page.builder(Integer.class)
                .pageSize(4)
                .stat(0)
                .totalSize(100)
                .data(List.of(0,1,2,3));

        Assertions.assertFalse(page.getData().isEmpty());
        Assertions.assertTrue(page.hasNextPage());
        Assertions.assertFalse(page.hasPreviousPage());
        Assertions.assertEquals(100,page.getResultCount());
        Assertions.assertEquals(25,page.getPageCount());
        Assertions.assertEquals(0,page.getStart());
        Assertions.assertEquals(4,page.getPageSize());

        Assertions.assertEquals("Page[data=[0, 1, 2, 3],pageSize=4,resultCount=100,start=0]",page.toString());
        Assertions.assertEquals(4,Page.getStartOfPage(1,4));


        page =  Page.builder(Integer.class)
                .pageSize(4)
                .stat(100)
                .totalSize(101)
                .data(List.of(0,1,2,3));
        Assertions.assertEquals(26,page.getPageCount());
        Assertions.assertFalse(page.hasNextPage());
        Assertions.assertTrue(page.hasPreviousPage());
    }

}
