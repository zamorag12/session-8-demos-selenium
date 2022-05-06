package com.corecodeqa.dataproviders;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginData {
    @DataProvider(name = "data-provider-login")
    public Object[][] getLoginData(){
        Object[][] data = new Object[2][2];

        // 1st row
        data[0][0] = "corecodeqa";
        data[0][1] = "Corecode2022!";

        // 2nd row
        data[1][0] = "corecodeqa";
        data[1][1] = "Pass123!";

        return data;
    }
}
