package ua.nure.kn.logvinova.usermanagment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class UserTest {

    private static final int YEAR_OF_BIRTH = 1999;
    private static final Long ID = 1L;
    private static final String NAME = "Kateryna";
    private static final String SURNAME = "Logvinova";

    private User user;

    @org.junit.Before
    public void setUp() throws Exception {
        user = new User(ID, NAME, SURNAME, new Date());
    }


    private static final int DAY_OF_BIRTH_1 = 26;
    private static final int MONTH_OF_BIRTH_1 = Calendar.OCTOBER;


    @Test
    public void simpeAgeTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_1, DAY_OF_BIRTH_1);
        Date dateofBirth=calendar.getTime();
        user.setDateofBirth(dateofBirth);
        assertEquals(19, user.getAge());
    }


    private static final int DAY_OF_BIRTH_2 = 5;
    private static final int MONTH_OF_BIRTH_2 = Calendar.MAY;


    @Test
    public void birthdayAlreadyWasAlready(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_2, DAY_OF_BIRTH_2);
        Date dateofBirth=calendar.getTime();
        user.setDateofBirth(dateofBirth);
        assertEquals(19, user.getAge());
    }


    private static final int DAY_OF_BIRTH_3 = 13;
    private static final int MONTH_OF_BIRTH_3 = Calendar.DECEMBER;

    @Test
    public void birthdayWillBe(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_3, DAY_OF_BIRTH_3);
        Date dateofBirth=calendar.getTime();
        user.setDateofBirth(dateofBirth);
        assertEquals(18, user.getAge());
    }


    private static final int DAY_OF_BIRTH_4 = 8;
    private static final int MONTH_OF_BIRTH_4 = Calendar.NOVEMBER;


    @Test
    public void birthdayTheSameMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH,  MONTH_OF_BIRTH_4, DAY_OF_BIRTH_4);
        Date dateofBirth = calendar.getTime();
        user.setDateofBirth(dateofBirth);
        assertEquals(19, user.getAge());
    }


    private static final int DAY_OF_BIRTH_5 = 30;
    private static final int MONTH_OF_BIRTH_5 = Calendar.NOVEMBER;


    @Test
    public void birthdayWillBeThisMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH_5, DAY_OF_BIRTH_5);
        Date dateofBirth=calendar.getTime();
        user.setDateofBirth(dateofBirth);
        assertEquals(18, user.getAge());
    }

    @Test
    public void testGetFullName()  {

        String resultExpected = SURNAME + ", " + NAME;
        assertEquals(resultExpected,user.getFullName());
    }










}