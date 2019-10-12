package com.rdecky.asmcalc.calculator;

import androidx.lifecycle.LiveData;

import com.rdecky.asmcalc.calculator.value.DecButtonValue;
import com.rdecky.asmcalc.calculator.value.OperatorValue;
import com.rdecky.asmcalc.calculator.value.SpecialButtonValue;
import com.rdecky.asmcalc.data.UserEntry;
import com.rdecky.asmcalc.data.source.UserEntryDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class CalculatorViewModelTest {

    private CalculatorViewModel calculatorViewModel;

    @Before
    public void setUp() {
        UserEntryDaoStub userEntryDaoStub = new UserEntryDaoStub();
        calculatorViewModel = new CalculatorViewModel(userEntryDaoStub);
    }

    @Test
    public void currentValue_initialValue_Zero() {
        assertThat(calculatorViewModel.getCurrentValue(), is(0L));
    }

    @Test
    public void inputText_initialValue_Zero() {
        assertThat(calculatorViewModel.inputText.getValue(), is("0"));
    }

    @Test
    public void decText_initialValue_Zero() {
        assertThat(calculatorViewModel.decText.getValue(), is("0"));
    }

    @Test
    public void hexText_initialValue_Zero() {
        assertThat(calculatorViewModel.hexText.getValue(), is("0"));
    }

    @Test
    public void binTextTop_initialValue_ThirtyTwoZeros() {
        assertThat(calculatorViewModel.binTextTop.getValue(), is("0000 0000 0000 0000 0000 0000 0000 0000"));
    }

    @Test
    public void binTextBottom_initialValue_ThirtyTwoZeros() {
        assertThat(calculatorViewModel.binTextBottom.getValue(), is("0000 0000 0000 0000 0000 0000 0000 0000"));
    }

    @Test
    public void setCurrentValueAsDec_updatesDecText() {
        calculatorViewModel.setCurrentValueAsDec(123456789L);

        assertThat(calculatorViewModel.decText.getValue(), is("123,456,789"));
    }

    @Test
    public void setCurrentValueAsDec_updatesHexText() {
        calculatorViewModel.setCurrentValueAsDec(8987178L);

        assertThat(calculatorViewModel.hexText.getValue(), is("89 222A"));
    }

    @Test
    public void setCurrentValueAsDec_updatesBinTextBottom() {
        calculatorViewModel.setCurrentValueAsDec(918273645L);

        assertThat(calculatorViewModel.binTextTop.getValue(), is("0000 0000 0000 0000 0000 0000 0000 0000"));
        assertThat(calculatorViewModel.binTextBottom.getValue(), is("0011 0110 1011 1011 1011 1110 0110 1101"));
    }

    @Test
    public void setCurrentValueAsDec_updatesBinText() {
        calculatorViewModel.setCurrentValueAsDec(76918273645L);

        assertThat(calculatorViewModel.binTextTop.getValue(), is("0000 0000 0000 0000 0000 0000 0001 0001"));
        assertThat(calculatorViewModel.binTextBottom.getValue(), is("1110 1000 1010 1111 1011 0110 0110 1101"));
    }

    @Test
    public void setCurrentValue_inputFormatDec_updatesCurrentValue() {
        calculatorViewModel.setInputFormat(InputFormatClickListener.InputFormat.DEC);
        calculatorViewModel.setCurrentValue("1092837465");

        assertThat(calculatorViewModel.getCurrentValue(), is(1092837465L));
    }

    @Test
    public void setCurrentValue_inputFormatDec_handlesMaxValue() {
        calculatorViewModel.setInputFormat(InputFormatClickListener.InputFormat.DEC);
        calculatorViewModel.setCurrentValue(Long.toString(Long.MAX_VALUE));

        assertThat(calculatorViewModel.getCurrentValue(), is(Long.MAX_VALUE));
    }

    @Test
    public void setCurrentValue_inputFormatHex_updatesCurrentValue() {
        calculatorViewModel.setInputFormat(InputFormatClickListener.InputFormat.HEX);
        calculatorViewModel.setCurrentValue("FFFF9F9F");

        assertThat(calculatorViewModel.getCurrentValue(), is(4294942623L));
    }

    @Test
    public void setCurrentValue_inputFormatHex_handlesMaxValue() {
        calculatorViewModel.setInputFormat(InputFormatClickListener.InputFormat.HEX);
        calculatorViewModel.setCurrentValue("FFFFFFFFFFFFFFFF");

        assertThat(calculatorViewModel.getCurrentValue(), is(-1L));
    }

    @Test
    public void setCurrentValue_inputFormatHex_handlesSecondLargestValue() {
        calculatorViewModel.setInputFormat(InputFormatClickListener.InputFormat.HEX);
        calculatorViewModel.setCurrentValue("EFFFFFFFFFFFFFFF");

        assertThat(calculatorViewModel.getCurrentValue(), is(-1152921504606846977L));
    }

    @Test
    public void setCurrentValue_inputFormatBin_updatesCurrentValue() {
        calculatorViewModel.setInputFormat(InputFormatClickListener.InputFormat.BIN);
        calculatorViewModel.setCurrentValue("0001110001010001010101");

        assertThat(calculatorViewModel.getCurrentValue(), is(463957L));
    }

    @Test
    public void setCurrentValue_inputFormatBin_handlesMinValue() {
        calculatorViewModel.setInputFormat(InputFormatClickListener.InputFormat.BIN);
        calculatorViewModel.setCurrentValue("1000000000000000000000000000000000000000000000000000000000000000");

        assertThat(calculatorViewModel.getCurrentValue(), is(Long.MIN_VALUE));
    }

    @Test
    public void setCurrentValue_inputFormatBin_handlesMaxValue() {
        calculatorViewModel.setInputFormat(InputFormatClickListener.InputFormat.BIN);
        calculatorViewModel.setCurrentValue("1111111111111111111111111111111111111111111111111111111111111111");

        assertThat(calculatorViewModel.getCurrentValue(), is(-1L));
    }

    @Test
    public void regularButtonPressed_updatesCurrentValue() {
        DecButtonValue nine = new DecButtonValue("9");
        calculatorViewModel.setCurrentValueAsDec(12L);
        calculatorViewModel.regularButtonPressed(nine);

        assertThat(calculatorViewModel.getCurrentValue(), is(129L));
    }

    @Test
    public void specialButton_invert() {
        SpecialButtonValue special = new SpecialButtonValue("+/-");
        calculatorViewModel.setCurrentValueAsDec(123L);
        calculatorViewModel.specialButtonPressed(special);

        assertThat(calculatorViewModel.getCurrentValue(), is(-123L));
    }

    @Test
    public void specialButton_backspace() {
        SpecialButtonValue special = new SpecialButtonValue("bksp");
        calculatorViewModel.setCurrentValueAsDec(9876L);
        calculatorViewModel.specialButtonPressed(special);

        assertThat(calculatorViewModel.getCurrentValue(), is(987L));
    }

    @Test
    public void specialButton_clear() {
        SpecialButtonValue special = new SpecialButtonValue("clear");
        calculatorViewModel.setCurrentValueAsDec(1234L);
        calculatorViewModel.specialButtonPressed(special);

        assertThat(calculatorViewModel.getCurrentValue(), is(0L));
    }

    @Test
    public void specialButton_clearEntry() {
        SpecialButtonValue special = new SpecialButtonValue("ce");
        calculatorViewModel.setCurrentValueAsDec(111L);
        calculatorViewModel.specialButtonPressed(special);

        assertThat(calculatorViewModel.getCurrentValue(), is(0L));
    }

    @Test
    public void operatorButtonPressed_clearsEntry() {
        calculatorViewModel.setCurrentValueAsDec(1234L);
        calculatorViewModel.operatorButtonPressed();

        assertThat(calculatorViewModel.getCurrentValue(), is(0L));
    }

    class UserEntryDaoStub implements UserEntryDao {

        @Override
        public LiveData<List<UserEntry>> getUserEntries() {
            return null;
        }

        @Override
        public UserEntry getUserEntryById(int id) {
            return null;
        }

        @Override
        public void insert(UserEntry userEntry) {

        }

        @Override
        public void update(UserEntry userEntry) {

        }

        @Override
        public void deleteAll(List<UserEntry> userEntries) {

        }
    }
}