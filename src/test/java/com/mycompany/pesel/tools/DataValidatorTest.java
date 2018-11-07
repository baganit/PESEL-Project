package com.mycompany.pesel.tools;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.junit.Test;

import javax.xml.crypto.Data;

public class DataValidatorTest
{
    /* Data in those tests should be generated randomly, but...
    * didn't have enough time to do this :( */
    @Test
    public void invalidPeselShouldBeRejected() {
        FastList<String> invalidPesels = FastList.newList(0);
        invalidPesels.add("2222222");
        invalidPesels.add("1234567891011");
        invalidPesels.add("34567891011");

        for (int i = 0; i < invalidPesels.size(); i++)
            assertFalse(DataValidator.peselValid(invalidPesels.get(i)));
    }

    @Test
    public void validPeselShouldBeAccepted() {
        FastList<String> validPesels = FastList.newList(0);
        validPesels.add("62030132726");
        validPesels.add("90072516642");
        validPesels.add("49070832959");
        assertTrue(DataValidator.peselValid("98100200578"));
    }
}
