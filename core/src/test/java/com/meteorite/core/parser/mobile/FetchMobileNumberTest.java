package com.meteorite.core.parser.mobile;

import com.metaui.core.parser.mobile.FetchMobileNumber;
import org.junit.Test;

public class FetchMobileNumberTest {

    @Test
    public void testFetch() throws Exception {
        FetchMobileNumber fetchMobileNumber = new FetchMobileNumber(null);
        fetchMobileNumber.fetch();
    }
}