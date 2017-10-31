/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.ExchangeRateTable;
import java.math.BigDecimal;
import java.util.Currency;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Tomas Pavuk
 */
public class MainAnnotation {
    
    public static void main(String ... args) {
        springAnnotationContext();
    }

    private static void springAnnotationContext() {
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext("cz.muni.fi.pa165.currency");
        
        CurrencyConvertor currencyConvertor 
                = applicationContext.getBean(CurrencyConvertor.class);

        System.err.println(currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal(1)));
    }
}
