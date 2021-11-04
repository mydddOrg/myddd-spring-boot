package org.myddd.querychannel;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.myddd.domain.mock.User;
import org.myddd.utils.Page;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
class TestQueryChannel extends AbstractTest{

    @Inject
    private QueryChannelService queryChannelService;

    @BeforeEach
    void beforeEach(){
        for (int i = 0;i<= 10;i++){
           randomUser().createLocalUser();
        }
    }

    @Test
    void testSetPageSize(){
        ChannelQuery<User> userChannelQuery = queryChannelService.createJpqlQuery("from User", User.class);

        Assertions.assertThrows(IllegalArgumentException.class,()->userChannelQuery.setPageSize(-1));
        Assertions.assertThrows(IllegalArgumentException.class,()->userChannelQuery.setMaxResult(-1));
        Assertions.assertThrows(IllegalArgumentException.class,()->userChannelQuery.setFirstResult(-1));
        Assertions.assertThrows(IllegalArgumentException.class,()->userChannelQuery.setPage(-1,10));
        Assertions.assertThrows(IllegalArgumentException.class,()->userChannelQuery.setPage(1,-10));
    }

    @Test
    void testQueryChannelNotNULL(){
        Assertions.assertNotNull(queryChannelService);
    }



    @Test
    void testJpqlPageQuery(){
        Page<User> userPage =  queryChannelService.createJpqlQuery("from User", User.class)
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage =  queryChannelService.createJpqlQuery("from User where name LIKE ?1", User.class)
                .setParameters("%")
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage =  queryChannelService.createJpqlQuery("from User where name LIKE ?1", User.class)
                .setParameters(List.of("%"))
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage =  queryChannelService.createJpqlQuery("from User where name LIKE :name", User.class)
                .addParameter("name","%")
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage =  queryChannelService.createJpqlQuery("from User where name LIKE :name", User.class)
                .setParameters(Map.of("name","%"))
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());
    }

    @Test
    void testNamedPageQuery(){
        Page<User> userPage = queryChannelService.createNamedQuery("User.pageList",User.class)
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage = queryChannelService.createNamedQuery("User.pageListWithParamsPositionParam",User.class)
                .setParameters("%")
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage = queryChannelService.createNamedQuery("User.pageListWithParamsPositionParam",User.class)
                .setParameters(List.of("%"))
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage = queryChannelService.createNamedQuery("User.pageListWithParamsNamedParam",User.class)
                .addParameter("name","%")
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage = queryChannelService.createNamedQuery("User.pageListWithParamsNamedParam",User.class)
                .setParameters(Map.of("name","%"))
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());
    }

    @Test
    void testNativeSQLPageQuery(){
        Page<Object[]> userPage =  queryChannelService.createSqlQuery("select * from user_", Object[].class)
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage =  queryChannelService.createSqlQuery("select * from user_ where name like ?1", Object[].class)
                .setParameters("%")
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage =  queryChannelService.createSqlQuery("select * from user_ where name like ?1", Object[].class)
                .setParameters(List.of("%"))
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage =  queryChannelService.createSqlQuery("select * from user_ where name like :name", Object[].class)
                .addParameter("name","%")
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());

        userPage =  queryChannelService.createSqlQuery("select * from user_ where name like :name", Object[].class)
                .setParameters(Map.of("name","%"))
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());
    }

    @Test
    void testSetPageQuery(){
        Page<User> userPage =  queryChannelService.createJpqlQuery("from User", User.class)
                .setPage(0,10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());
    }

    @Test
    void testListQuery(){
        List<User> userList = queryChannelService.createJpqlQuery("from User", User.class)
                .setPage(0,10)
                .list();
        Assertions.assertFalse(userList.isEmpty());
    }

    @Test
    void testSingleQuery(){
        User user = queryChannelService.createJpqlQuery("from User",User.class)
                .setMaxResult(1)
                .singleResult();

        Assertions.assertNotNull(user);
    }

    @Test
    void testPageWithGroupBy(){
        Page<Object[]> userPage =  queryChannelService.createJpqlQuery("select name,count(*) as count from User group by name", Object[].class)
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());
    }

    @Test
    void testPageWithDistinct(){
        Page<Object[]> userPage =  queryChannelService.createJpqlQuery("select distinct(name) as name from User", Object[].class)
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());
    }

    @Test
    void testPageWithCountSQL(){
        Page<User> userPage =  queryChannelService.createJpqlQuery("from User", User.class)
                .setCountSQL("select count(*) as count from User")
                .setFirstResult(0)
                .setPageSize(10)
                .pagedList();

        Assertions.assertTrue(userPage.getResultCount() > 0);
        Assertions.assertFalse(userPage.getData().isEmpty());
    }

}
