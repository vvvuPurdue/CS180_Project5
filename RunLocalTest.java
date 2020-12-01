import backend.*;
import frontend.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import static org.junit.Assert.fail;

/**
 * RunLocalTest
 *
 * A set of test cases for the entire project to be run locally.
 *
 * @author Team 15-3, CS-180 Merge
 * @version December 1st, 2020
 */

public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    public static class TestCase {

        // Begin Account Class Testing
        @Test(timeout = 1000)
        public void accountClassDeclarationTest() {
            Class<?> clazz;
            String className;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            // Set the class being tested
            clazz = Account.class;
            className = "Account";

            // Testing

            modifiers = clazz.getModifiers();
            superclass = clazz.getSuperclass();
            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `"+ className +"` is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `"+ className +"` is NOT `abstract`!", Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `"+ className +"` extends `Object`!", Object.class, superclass);
            Assert.assertEquals("Ensure that `"+ className +"` implements only 1 interface!", 1, superinterfaces.length);
            Assert.assertEquals("Ensure that `"+ className +"` implements Serializable!", Serializable.class, superinterfaces[0]);
        }

        //Begin Account Field Testing

        @Test(timeout = 1000)
        public void accountUsernameFieldTest() {
            Class<?> clazz;
            String className = "Account";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "username";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }

        @Test(timeout = 1000)
        public void accountPasswordFieldTest() {
            Class<?> clazz;
            String className = "Account";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "password";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");

                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }

        @Test(timeout = 1000)
        public void accountEmailFieldTest() {
            Class<?> clazz;
            String className = "Account";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "email";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");

                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }

        @Test(timeout = 1000)
        public void accountPhoneNumberFieldTest() {
            Class<?> clazz;
            String className = "Account";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "bio";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");

                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }

        @Test(timeout = 1000)
        public void accountBioFieldTest() {
            Class<?> clazz;
            String className = "Account";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "bio";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");

                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }

        @Test(timeout = 1000)
        public void accountInterestsFieldTest() {
            Class<?> clazz;
            String className = "Account";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "interests";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");

                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }

        @Test(timeout = 1000)
        public void accountFriendsFieldTest() {
            Class<?> clazz;
            String className = "Account";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "friends";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = ArrayList.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");

                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }

        @Test(timeout = 1000)
        public void accountFriendRequestsFieldTest() {
            Class<?> clazz;
            String className = "Account";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "friendRequests";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = ArrayList.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");

                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }

        @Test(timeout = 1000)
        public void accountRequestedFriendsFieldTest() {
            Class<?> clazz;
            String className = "Account";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "requestedFriends";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = ArrayList.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");

                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }

        // End Account Field Testing

        // Begin Account Method Testing

        @Test(timeout = 1000)
        public void accountGetUsernameMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getUsername";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountGetUsernameFunctionTest01() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    "bio", "interests");

            String methodName = "getUsername";
            String className = "Account";
            String testInput = "username";

            Assert.assertEquals("Ensure `" + className + "'s` `" + methodName + "` method returns the username!", testInput, acc.getUsername());
        }

        @Test(timeout = 1000)
        public void accountGetUsernameFunctionTest02() {
            Account acc = new Account(null, "password", "email@email.com", "1234567890",
                    "bio", "interests");

            String methodName = "getUsername";
            String className = "Account";
            String testInput = null;

            Assert.assertNull("Ensure `" + className + "'s` `" + methodName + "` method returns the username!", acc.getUsername());
        }

        @Test(timeout = 1000)
        public void accountGetPasswordMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getPassword";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountGetPasswordFunctionTest01() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    "bio", "interests");

            String methodName = "getPassword";
            String className = "Account";
            String testInput = "password";

            Assert.assertEquals("Ensure `" + className + "'s` `" + methodName + "` method returns the password!", testInput, acc.getPassword());
        }

        @Test(timeout = 1000)
        public void accountGetPasswordFunctionTest02() {
            Account acc = new Account("username", null, "email@email.com", "1234567890",
                    "bio", "interests");

            String methodName = "getPassword";
            String className = "Account";
            String testInput = null;

            Assert.assertNull("Ensure `" + className + "'s` `" + methodName + "` method returns the password!", acc.getPassword());
        }

        @Test(timeout = 1000)
        public void accountSetUsernameMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "setUsername";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameter of type String!");
                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountSetUsernameFunctionTest01() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    "bio", "interests");

            String methodName = "setUsername";
            String className = "Account";
            String testInput = "username2";

            acc.setUsername(testInput);

            Assert.assertEquals("Ensure `" + className + "'s` `" + methodName + "` method sets the username correctly!", testInput, acc.getUsername());
        }

        @Test(timeout = 1000)
        public void accountSetUsernameFunctionTest02() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    "bio", "interests");

            String methodName = "setUsername";
            String className = "Account";
            String testInput = null;

            acc.setUsername(testInput);

            Assert.assertNull("Ensure `" + className + "'s` `" + methodName + "` method sets the username correctly!", acc.getUsername());
        }

        @Test(timeout = 1000)
        public void accountSetPasswordMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "setPassword";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameter of type String!");
                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountSetPasswordFunctionTest01() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    "bio", "interests");

            String methodName = "setPassword";
            String className = "Account";
            String testInput = "password2";

            acc.setPassword(testInput);

            Assert.assertEquals("Ensure `" + className + "'s` `" + methodName + "` method sets the password correctly!", testInput, acc.getPassword());
        }

        @Test(timeout = 1000)
        public void accountSetPasswordFunctionTest02() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    "bio", "interests");

            String methodName = "setPassword";
            String className = "Account";
            String testInput = null;

            acc.setPassword(testInput);

            Assert.assertNull("Ensure `" + className + "'s` `" + methodName + "` method sets the password correctly!", acc.getPassword());
        }

        @Test(timeout = 1000)
        public void accountGetEmailMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getEmail";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountGetEmailFunctionTest01() {
            Account acc = new Account("username", "password", "email2@email.com", "1234567890",
                    "bio", "interests");

            String methodName = "getEmail";
            String className = "Account";
            String testInput = "email2@email.com";

            Assert.assertEquals("Ensure `" + className + "'s` `" + methodName + "` method returns the email!", testInput, acc.getEmail());
        }

        @Test(timeout = 1000)
        public void accountGetEmailFunctionTest02() {
            Account acc = new Account("username", "password", null, "1234567890",
                    "bio", "interests");

            String methodName = "getEmail";
            String className = "Account";
            String testInput = null;

            Assert.assertNull("Ensure `" + className + "'s` `" + methodName + "` method returns the email!", acc.getEmail());
        }

        @Test(timeout = 1000)
        public void accountGetPhoneNumberMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getPhoneNumber";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountGetPhoneNumberFunctionTest01() {
            Account acc = new Account("username", "password", "email@email.com", "1111111111",
                    "bio", "interests");

            String methodName = "getPhoneNumber";
            String className = "Account";
            String testInput = "1111111111";

            Assert.assertEquals("Ensure `" + className + "'s` `" + methodName + "` method returns the phone number!", testInput, acc.getPhoneNumber());
        }

        @Test(timeout = 1000)
        public void accountGetPhoneNumberFunctionTest02() {
            Account acc = new Account("username", "password", "email@email.com", null,
                    "bio", "interests");

            String methodName = "getPhoneNumber";
            String className = "Account";
            String testInput = null;

            Assert.assertNull("Ensure `" + className + "'s` `" + methodName + "` method returns the phone number!", acc.getPhoneNumber());
        }

        @Test(timeout = 1000)
        public void accountGetBioMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getBio";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountGetBioFunctionTest01() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    "bio1", "interests");

            String methodName = "getBio";
            String className = "Account";
            String testInput = "bio1";

            Assert.assertEquals("Ensure `" + className + "'s` `" + methodName + "` method returns the bio!", testInput, acc.getBio());
        }

        @Test(timeout = 1000)
        public void accountGetBioFunctionTest02() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    null, "interests");

            String methodName = "getBio";
            String className = "Account";
            String testInput = null;

            Assert.assertNull("Ensure `" + className + "'s` `" + methodName + "` method returns the bio!", acc.getBio());
        }

        @Test(timeout = 1000)
        public void accountGetInterestsMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getInterests";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = String.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountGetInterestsFunctionTest01() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    "bio", "interests1");

            String methodName = "getInterests";
            String className = "Account";
            String testInput = "interests1";

            Assert.assertEquals("Ensure `" + className + "'s` `" + methodName + "` method returns the interests!", testInput, acc.getInterests());
        }

        @Test(timeout = 1000)
        public void accountGetInterestsFunctionTest02() {
            Account acc = new Account("username", "password", "email@email.com", "1234567890",
                    "bio", null);

            String methodName = "getInterests";
            String className = "Account";
            String testInput = null;

            Assert.assertNull("Ensure `" + className + "'s` `" + methodName + "` method returns the interests!", acc.getInterests());
        }

        @Test(timeout = 1000)
        public void accountGetFriendsMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getFriends";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = ArrayList.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountGetFriendsFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Assert.assertEquals("Ensure that `Account's` getFriends method returns an empty list before adding friends!", new ArrayList<Account>(), acc1.getFriends());
        }

        @Test(timeout = 1000)
        public void accountGetFriendsFunctionTest02() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            acc1.sendFriendRequest(acc2);
            acc2.acceptDeclineFriendRequest(acc1, true);

            ArrayList<Account> testList1 = new ArrayList<>();
            ArrayList<Account> testList2 = new ArrayList<>();
            testList1.add(acc2);
            testList2.add(acc1);

            Assert.assertEquals("Ensure that `Account's` getFriends method returns the list of friends!", testList1, acc1.getFriends());
            Assert.assertEquals("Ensure that `Account's` getFriends method returns the list of friends!", testList2, acc2.getFriends());
        }

        @Test(timeout = 1000)
        public void accountGetFriendRequestsMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getFriendRequests";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = ArrayList.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountGetFriendRequestsFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Assert.assertEquals("Ensure that `Account's` `getFriendRequests` method returns an empty list before receiving friend requests!", new ArrayList<Account>(), acc1.getFriendRequests());
        }

        @Test(timeout = 1000)
        public void accountGetFriendRequestsFunctionTest02() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            ArrayList<Account> testList1 = new ArrayList<>();
            ArrayList<Account> testList2 = new ArrayList<>();
            testList1.add(acc2);
            testList2.add(acc1);

            acc2.sendFriendRequest(acc1);

            Assert.assertEquals("Ensure that `Account's` `getFriendRequests` method returns the list of friend requests!", testList1, acc1.getFriendRequests());

            acc1.acceptDeclineFriendRequest(acc2, false);

            Assert.assertEquals("Ensure that `Account's` `getFriendRequests` method returns the list of friend requests even after accepting or declining!", new ArrayList<Account>(), acc1.getFriendRequests());
        }

        @Test(timeout = 1000)
        public void accountGetRequestedFriendsMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getRequestedFriends";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = ArrayList.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountGetRequestedFriendsFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Assert.assertEquals("Ensure that `Account's` `getRequestedFriends` method returns an empty list before sending friend requests!", new ArrayList<Account>(), acc1.getRequestedFriends());
            Assert.assertEquals("Ensure that `Account's` `getRequestedFriends` method returns an empty list before sending friend requests!", new ArrayList<Account>(), acc2.getRequestedFriends());
        }

        @Test(timeout = 1000)
        public void accountGetRequestedFriendsFunctionTest02() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            ArrayList<Account> testList1 = new ArrayList<>();
            ArrayList<Account> testList2 = new ArrayList<>();
            testList1.add(acc2);
            testList2.add(acc1);

            acc1.sendFriendRequest(acc2);

            Assert.assertEquals("Ensure that `Account's` `getRequestedFriends` method returns the list of requested friends!", testList1, acc1.getRequestedFriends());

            acc2.acceptDeclineFriendRequest(acc1, false);

            Assert.assertEquals("Ensure that `Account's` `getRequestedFriends` method returns the list of friend requests even after accepting or declining!", new ArrayList<Account>(), acc1.getRequestedFriends());
        }

        @Test(timeout = 1000)
        public void accountIsFriendsWithMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "isFriendsWith";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = boolean.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, Account.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameter with type Account!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountIsFriendsWithFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Assert.assertFalse("Ensure that `Account's` `isFriendsWith` method returns false before adding any friends!", acc1.isFriendsWith(acc2));
            Assert.assertFalse("Ensure that `Account's` `isFriendsWith` method returns false before adding any friends!", acc2.isFriendsWith(acc1));
        }

        @Test(timeout = 1000)
        public void accountIsFriendsWithFunctionTest02() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            acc1.sendFriendRequest(acc2);
            acc2.acceptDeclineFriendRequest(acc1, true);

            Assert.assertTrue("Ensure that `Account's` `isFriendsWith` method returns true if the user is friends with the other user!", acc1.isFriendsWith(acc2));
            Assert.assertTrue("Ensure that `Account's` `isFriendsWith` method returns true if the user is friends with the other user!", acc1.isFriendsWith(acc2));
            Assert.assertFalse("Ensure that `Account's` `isFriendsWith` method returns false if it checks if the user is friends with itself", acc1.isFriendsWith(acc1));

            try {
                Assert.assertFalse("Ensure that `Account's` `isFriendsWith` method returns false if passed a null user!", acc1.isFriendsWith(null));
            } catch (NullPointerException e) {
                Assert.fail("Ensure that `Account's` `isFriendsWith` method does not throw a NullPointerException when passed a null user!");
            }
        }

        @Test(timeout = 1000)
        public void accountHasRequestedMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "hasRequested";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = boolean.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, Account.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameter with type Account!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountHasRequestedFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Assert.assertFalse("Ensure that `Account's` `hasRequested` method returns false before sending any friend requests!", acc1.hasRequested(acc2));
            Assert.assertFalse("Ensure that `Account's` `hasRequested` method returns false before sending any friend requests!", acc2.hasRequested(acc1));
        }

        @Test(timeout = 1000)
        public void accountHasRequestedFunctionTest02() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            acc1.sendFriendRequest(acc2);

            Assert.assertTrue("Ensure that `Account's` `hasRequested` method returns true if a request has been sent!", acc1.hasRequested(acc2));
            Assert.assertFalse("Ensure that `Account's` `hasRequested` method returns false before sending any friend requests!", acc2.hasRequested(acc1));

            acc2.acceptDeclineFriendRequest(acc1, false);

            Assert.assertFalse("Ensure that `Account's` `hasRequested` method returns false if the request was accepted or declined", acc1.hasRequested(acc2));

            try {
                Assert.assertFalse("Ensure that `Account's` `hasRequested` method returns false if passed a null user!", acc1.hasRequested(null));
            } catch (NullPointerException e) {
                Assert.fail("Ensure that `Account's` `hasRequested` method does not throw a NullPointerException when passed a null user!");
            }
        }

        @Test(timeout = 1000)
        public void accountSendFriendRequestMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "sendFriendRequest";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = int.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, Account.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameter with type Account!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountSendFriendRequestFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Assert.assertEquals("Ensure that `Account's` `sendFriendRequest` method returns 1 if the user exists and a request hasn't already been sent!", 1,  acc1.sendFriendRequest(acc2));
        }

        @Test(timeout = 1000)
        public void accountSendFriendRequestFunctionTest02() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            acc1.sendFriendRequest(acc2);

            Assert.assertEquals("Ensure that `Account's` `sendFriendRequest` method returns -1 if sending a request to itself!", -1,  acc1.sendFriendRequest(acc1));
            Assert.assertEquals("Ensure that `Account's` `sendFriendRequest` method returns -1 if sending a duplicate request!", -1,  acc1.sendFriendRequest(acc2));
            try {
                Assert.assertEquals("Ensure that `Account's` `sendFriendRequest` method returns -1 if sending a request to a null user!", -1,  acc1.sendFriendRequest(null));
            } catch (NullPointerException e) {
                Assert.fail("Ensure that `Account's` `sendFriendRequest` method does not throw a NullPointerException when sending a request to a null user!");
            }
        }

        @Test(timeout = 1000)
        public void accountCancelFriendRequestMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "cancelFriendRequest";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = int.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, Account.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameter with type Account!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountCancelFriendRequestFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            acc1.sendFriendRequest(acc2);

            Assert.assertEquals("Ensure that `Account's` `cancelFriendRequest` method returns 1 if the request has been sent and has been canceled!", 1,  acc1.cancelFriendRequest(acc2));
        }

        @Test(timeout = 1000)
        public void accountCancelFriendRequestFunctionTest02() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Assert.assertEquals("Ensure that `Account's` `cancelFriendRequest` method returns -1 if the request has not been sent yet!", -1,  acc1.cancelFriendRequest(acc2));
            try {
                Assert.assertEquals("Ensure that `Account's` `cancelFriendRequest` method returns -1 if cancelling a request from a null user", -1, acc1.cancelFriendRequest(null));
            } catch (NullPointerException e) {
                Assert.fail("Ensure that `Account's` `cancelFriendRequest` method doesn't throw a NullPointerException!");
            }
        }

        @Test(timeout = 1000)
        public void accountAcceptDeclineFriendRequestMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "acceptDeclineFriendRequest";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = int.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, Account.class, boolean.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 2 parameters with type Account and boolean!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountAcceptDeclineFriendRequestFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            acc1.sendFriendRequest(acc2);

            Assert.assertEquals("Ensure that `Account's` `acceptDeclineFriendRequest` method returns 1 if the request has been accepted successfully", 1,  acc2.acceptDeclineFriendRequest(acc1, true));

            acc1.removeFriend(acc2);
            acc1.sendFriendRequest(acc2);

            Assert.assertEquals("Ensure that `Account's` `acceptDeclineFriendRequest` method returns 1 if the request has been declined successfully", 1,  acc2.acceptDeclineFriendRequest(acc1, false));
        }

        @Test(timeout = 1000)
        public void accountAcceptDeclineFriendRequestFunctionTest02() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Assert.assertEquals("Ensure that `Account's` `acceptDeclineFriendRequest` method returns -1 if accepting a non-existent request", -1,  acc2.acceptDeclineFriendRequest(acc1, true));
            Assert.assertEquals("Ensure that `Account's` `acceptDeclineFriendRequest` method returns -1 if declining a non-existent request", -1,  acc2.acceptDeclineFriendRequest(acc1, false));
            try {
                Assert.assertEquals("Ensure that `Account's` `acceptDeclineFriendRequest` method returns -1 if accepting/declining a request from a null user", -1, acc2.acceptDeclineFriendRequest(null, true));
            } catch (NullPointerException e) {
                Assert.fail("Ensure that `Account'` `acceptDeclineFriendRequest` method does not throw a NullPointerException when receiving a null user");
            }
        }

        @Test(timeout = 1000)
        public void accountRemoveFriendMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "removeFriend";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = int.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, Account.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameter with type Account!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        @Test(timeout = 1000)
        public void accountRemoveFriendFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            acc1.sendFriendRequest(acc2);
            acc2.acceptDeclineFriendRequest(acc1, false);

            Assert.assertEquals("Ensure that `Account's` `removeFriend` method returns 1 if it successfully removes a friend!", 1, acc1.removeFriend(acc2));
        }

        @Test(timeout = 1000)
        public void accountRemoveFriendFunctionTest02() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Assert.assertEquals("Ensure that `Account's` `removeFriend` method returns -1 if accepting a non-existent request", -1,  acc2.removeFriend(acc1));
            try {
                Assert.assertEquals("Ensure that `Account's` `removeFriend` method returns -1 if attempting to remove a null user", -1, acc2.removeFriend(null));
            } catch (NullPointerException e) {
                Assert.fail("Ensure that `Account'` `removeFriend` method does not throw a NullPointerException when receiving a null user");
            }
        }

        @Test(timeout = 1000)
        public void accountUserInListMethodTest() {
            Class<?> clazz;
            String className = "Account";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "userInList";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = int.class;

            // Set the class being tested
            clazz = Account.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class, ArrayList.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 2 parameter with type String and ArrayList!");
                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + methodName + "` method is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }

        /*
        @Test(timeout = 1000)
        public void accountRemoveFriendFunctionTest01() {
            Account acc1 = new Account("username1", "password1", "email1@email.com", "1111111111",
                    "bio1", "interests1");
            Account acc2 = new Account("username2", "password2", "email2@email.com", "2222222222",
                    "bio2", "interests2");

            Method method;
            try {
                method = Account.class.getDeclaredMethod("userInList", String.class, ArrayList.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("UserInList doesn't exist");
                return;
            }
            method.setAccessible(true);

            ArrayList<Account> testList = new ArrayList<>();
            testList.add(acc1);
            testList.add(acc2);

        }
        */
        

        // End Account Method Testing

        // End Account Class Testing



        //Begin Client Testing
        
        //Begin Client Field Testing
        @Test(timeout = 1000)
        public void clientClassDeclarationTest() {
            Class<?> clazz;
            String className;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            // Set the class being tested
            clazz = Client.class;
            className = "Client";

            // Testing

            modifiers = clazz.getModifiers();
            superclass = clazz.getSuperclass();
            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `"+ className +"` is `public`!", Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `"+ className +"` is NOT `abstract`!", Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `"+ className +"` extends `Object`!", Object.class, superclass);
            Assert.assertEquals("Ensure that `"+ className +"` implements no interfaces!", 0, superinterfaces.length);
        }
        
        @Test(timeout = 1000)
        public void clientAccountNameFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "accountName";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientPassFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "pass";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientUserFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "user";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientCreateSessionFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "createSession";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientCloseSessionFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "closeSession";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `static`!", Modifier.isStatic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientEditProfileButtonFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "editProfileButton";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JButton.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientSaveChangesButtonFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "saveChangesButton";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JButton.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientPhoneNumberLabelFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "phoneNumberLabel";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JLabel.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientPhoneNumberTxtFieldFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "phoneNumberTxtField";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JTextField.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientEmailLabelFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "emailLabel";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JLabel.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientEmailTxtFieldFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "emailTxtField";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JTextField.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientBioLabelFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "bioLabel";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JLabel.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientBioTxtFieldFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "bioTxtField";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JTextField.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientInterestsLabelFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "interestsLabel";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JLabel.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientInterestsTxtFieldFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "interestsTxtField";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JTextField.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientUsernameLabelFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "usernameLabel";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JLabel.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientUsernameTxtFieldFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "usernameTxtField";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JTextField.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientPasswordLabelFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "passwordLabel";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JLabel.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientPasswordTxtFieldFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "passwordTxtField";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = JTextField.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `private`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientSocketFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "socket";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = Socket.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientReaderFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "reader";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = BufferedReader.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientWriterFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "writer";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = PrintWriter.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientObjectInputFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "objectInput";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = ObjectInputStream.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientObjectOutFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "objectOut";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = ObjectOutputStream.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientServerHostFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "serverHost";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = String.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientServerPortFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "serverPort";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = int.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        
        @Test(timeout = 1000)
        public void clientActionListenerFieldTest() {
            Class<?> clazz;
            String className = "Client";
            Field testField;
            int modifiers;
            Class<?> type;

            // Set the field that you want to test
            String fieldName = "actionListener";

            // Set the type of the field you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedType = ActionListener.class;

            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class field
            try {
                testField = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Assert.fail("Ensure that `" + className + "` declares a field named `" + fieldName + "`!");
                return;
            } //end try catch

            // Perform tests

            modifiers = testField.getModifiers();

            type = testField.getType();

            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is `public`!", Modifier.isPrivate(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + fieldName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertFalse("Ensure that `" + className + "`'s `" + fieldName + "` field is NOT `final`!", Modifier.isFinal(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + fieldName + "` field is the correct type!", expectedType, type);
        }
        //End Client Field Testing
        
        //Begin Client Method Testing
        
        @Test(timeout = 1000)
        public void clientSearchMenuMethodTest() {
            Class<?> clazz;
            String className = "Cliend";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "searchMenu";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientFriendMenuMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "friendMenu";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientActionsMenuMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "actionsMenu";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, Account.class, int.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has an Account and int parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientViewProfileMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "viewProfile";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items withing the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientProfileMenuMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "profileMenu";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items withing the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientEditProfileMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 0;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "editProfile";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has an empty `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientLoginUserMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "loginUser";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = boolean.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientCreateAccountMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "createAccount";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = boolean.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientUpdateAccountMethodTest01() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "updateAccount";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class, String.class, String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 4 String parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientUpdateAccountMethodTest02() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "updateAccount";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class, String.class, String.class, String.class, String.class, String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 7 String parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientIsFriendsWithMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 1;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "isFriendsWith";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = boolean.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 2 String parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 1 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientSendFriendRequestMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "sendFriendRequest";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 String parameter!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientCancelFriendRequestMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "cancelFriendRequest";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 String parameter!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientAcceptFriendRequestMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "acceptFriendRequest";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 String parameter!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientDeclineFriendRequestMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "declineFriendRequest";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 String parameter!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientRemoveFriendMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "removeFriend";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 String parameter!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within it's `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientGetUserMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getUser";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = Account.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 1 parameter with type string!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientGetAllUsersMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "getAllUsers";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = ArrayList.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientHasRequestedMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 1;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "hasRequested";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = boolean.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName, String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has 2 String parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 1 item within the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientConnectServerMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "connectServer";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientDisconnectServerMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 1;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "disconnectServer";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 1 item within the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientCloseClientMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 1;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "closeClient";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 1 item within the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientDeleteAccountMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 1;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "deleteAccount";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = boolean.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 1 item within the `throws` clause!", expectedLength, exceptions.length);
        }
        
        @Test(timeout = 1000)
        public void clientUpdateMethodTest() {
            Class<?> clazz;
            String className = "Client";
            Method method;
            int modifiers;
            Class<?> actualReturnType;
            int expectedLength = 2;
            Class<?>[] exceptions;

            // Set the method that you want to test
            String methodName = "update";

            // Set the return type of the method you want to test
            // Use the type + .class
            // For example, String.class or int.class
            Class<?> expectedReturnType = void.class;


            // Set the class being tested
            clazz = Client.class;

            // Attempt to access the class method
            try {
                method = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a method named `" + methodName + "` that" +
                        " has no parameters!");

                return;
            } //end try catch

            // Perform tests

            modifiers = method.getModifiers();

            actualReturnType = method.getReturnType();

            exceptions = method.getExceptionTypes();

            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` method is `public`!", Modifier.isPublic(modifiers));
            Assert.assertTrue("Ensure that `" + className + "`'s `" + methodName + "` field is `static`!", Modifier.isStatic(modifiers));
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has the correct return type!", expectedReturnType, actualReturnType);
            Assert.assertEquals("Ensure that `" + className + "`'s `" + methodName + "` method has 2 items within the `throws` clause!", expectedLength, exceptions.length);
        }
    }
}
