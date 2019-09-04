package com.rdecky.asmcalc.calculator;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class CalculatorButtonTest {

    private CalculatorButton button;
    private Context context = ApplicationProvider.getApplicationContext();

    @Before
    public void setUp() {
        button = new CalculatorButton(context);
    }
}