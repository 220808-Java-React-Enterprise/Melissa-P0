package com.revature.strong.services;

import com.revature.strong.daos.EquipmentDOA;
import com.revature.strong.daos.UserDAO;
import com.revature.strong.models.Equipment;
import com.revature.strong.models.User;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EquipmentServiceTest {
    private EquipmentService sut; //sut = system under test
    private final EquipmentDOA mockEquipmentDao = mock(EquipmentDOA.class);

    //jank constructor
    @Before
    public void setup() {
        sut = new EquipmentService(mockEquipmentDao);
    }

    @Test
    public void test_findEquipmentGivenCorrectCredentials() {
    //Arrange
    EquipmentService spiedSut = Mockito.spy(sut);
    String validId = "1A";

    when(mockEquipmentDao.getEquipmentById(validId)).thenReturn(new Equipment());

    //Act
    Equipment equipment = spiedSut.findEquipmentByID(validId);
    //Assert
    Assert.assertNotNull(equipment);
    }
    @Test(expected = InvalidUserException.class)
    public void test_findEquipmentGivenIncorrectCredentials() {
        //Arrange
        EquipmentService spiedSut = Mockito.spy(sut);
        String invalidId = "1*";

        when(mockEquipmentDao.getEquipmentById(invalidId)).thenReturn(null);

        //Act
        sut.findEquipmentByID(invalidId);
    }

}