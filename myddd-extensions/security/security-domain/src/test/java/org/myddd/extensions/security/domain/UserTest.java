package org.myddd.extensions.security.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.regex.Pattern;

@Transactional
class UserTest extends AbstractTest {


    @Test
    void testEquals(){
        var user = randomUser();
        var anotherUser = new User();
        anotherUser.setUserId(user.getUserId());
        anotherUser.setUserType(user.getUserType());

        Assertions.assertEquals(user,anotherUser);
    }

    @Test
    void testRegisterUser(){
        var created = randomRegisterUser().registerUser();
        Assertions.assertNotNull(created);
    }

    @Test
    void testCreateUser(){
        var randomUser = new User();
        Assertions.assertThrows(UserNameEmptyException.class, randomUser::createLocalUser);


        User noUserIdUser = new User();
        noUserIdUser.setName(randomId());
        noUserIdUser.setPassword(randomId());
        Assertions.assertThrows(UserIdEmptyException.class, noUserIdUser::createLocalUser);

        var user = randomUser();
        user.setPhone("18620501006");
        User createdUser = user.createLocalUser();
        Assertions.assertNotNull(createdUser);
        Assertions.assertNotNull(createdUser.getName());
        Assertions.assertEquals(UserType.LOCAL,createdUser.getUserType());
        Assertions.assertFalse(createdUser.isDisabled());
        Assertions.assertTrue(createdUser.getCreated() > 0);
        Assertions.assertEquals(0,createdUser.getUpdated());
        Assertions.assertNotNull(createdUser.getEncodePassword());

        var emailExistsUser = randomUser();
        emailExistsUser.setEmail(createdUser.getEmail());
        Assertions.assertThrows(EmailExistsException.class, emailExistsUser::createLocalUser);


        var phoneExistsUser = randomUser();
        phoneExistsUser.setPhone(createdUser.getPhone());
        Assertions.assertThrows(PhoneExistsException.class, phoneExistsUser::createLocalUser);

        var randomNoEmailAndPhoneUser = randomNoEmailAndPhoneUser();
        Assertions.assertThrows(EmailOrPhoneMustHaveOneException.class, randomNoEmailAndPhoneUser::createLocalUser);

        var randomBadEmailUser = randomBadEmailUser();
        Assertions.assertThrows(IllegalEmailFormatException.class, randomBadEmailUser::createLocalUser);

        var randomBadPhoneUser = randomBadPhoneUser();
        Assertions.assertThrows(IllegalPhoneFormatException.class, randomBadPhoneUser::createLocalUser);
    }


    @Test
    void testQueryUserByUserId(){

        User notExists = User.queryLocalUserById(randomId());
        Assertions.assertNull(notExists);

        User createdUser = randomUser().createLocalUser();
        Assertions.assertNotNull(createdUser);

        User query = User.queryLocalUserById(createdUser.getUserId());
        Assertions.assertNotNull(query);
    }

    @Test
    void testQueryByEmailOrPhone(){
        var notExists = User.queryLocalByEmailOrPhone(randomId());
        Assertions.assertNull(notExists);

        var randomUser = randomUser();
        randomUser.setPhone("18620501006");
        var createdUser = randomUser.createLocalUser();

        Assertions.assertNotNull(User.queryLocalByEmailOrPhone(createdUser.getEmail()));
        Assertions.assertNotNull(User.queryLocalByEmailOrPhone(createdUser.getPhone()));
    }

    @Test
    void testEnableUser(){
        var randomUser = randomUser();
        Assertions.assertThrows(UserNotFoundException.class, randomUser::enable);

        User createdUser = randomUser().createLocalUser();
        createdUser.disable();
        Assertions.assertTrue(createdUser.isDisabled());

        createdUser.enable();
        Assertions.assertFalse(createdUser.isDisabled());
    }

    @Test
    void testBatchInsertUsers(){
        var lists = List.of(randomUser(),randomUser());
        Assertions.assertDoesNotThrow(()->User.batchInsertUsers(lists,UserType.LOCAL));
        Assertions.assertDoesNotThrow(()->User.batchInsertUsers(lists,UserType.LOCAL));
    }

    @Test
    void testIsValidEmail(){
        var emailAddress = "username@domain.com";
        var regexPattern = "^(.+)@(\\S+)$";
        Assertions.assertTrue(patternMatches(emailAddress,regexPattern));

        Assertions.assertFalse(patternMatches(randomId(),regexPattern));
    }

    @Test
    void testIsValidPhone(){
        var regexPattern = "^\\d{11}$";
        Assertions.assertTrue(patternMatches("18620501006",regexPattern));
        Assertions.assertFalse(patternMatches(randomId(),regexPattern));
    }


    private static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    private User randomNoEmailAndPhoneUser(){
        return mockNotValidUser(null,null);
    }

    private User randomRegisterUser(){
        return mockNotValidUser(randomId() + "@taoofcoding.tech",null);
    }

    private User randomBadEmailUser(){
        return mockNotValidUser(randomId(),null);
    }

    private User randomBadPhoneUser(){
        return mockNotValidUser(randomId() + "@taoofcoding.tech",randomId());
    }

    private User mockNotValidUser(String email,String phone){
        var user = new User();
        user.setUserId(randomId());
        user.setPassword(randomId());
        user.setName(randomId());
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }
}
