package espreetests.helper;

import com.github.javafaker.Faker;

public final class UserFields {
    private UserFields() {
    }

    public static Faker faker = new Faker();

    public static String userEmail(){
        return faker.internet().emailAddress();
    }

    public static String userPassword(){
        return faker.internet().password(6, 128, false, false, false);
    }
}
