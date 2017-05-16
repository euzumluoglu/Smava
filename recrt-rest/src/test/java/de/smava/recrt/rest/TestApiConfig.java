package de.smava.recrt.rest;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.smava.recrt.jms.BankAccountJmsProducer;
import de.smava.recrt.service.AppUserRoleService;
import de.smava.recrt.service.AppUserService;
import de.smava.recrt.service.BankAccountService;

@Configuration
public class TestApiConfig {

    @Bean(name = "appUserService")
    public AppUserService getAppUserService() {
        return EasyMock.mock(AppUserService.class);
    }

    @Bean
    public AppUserRoleService getAppUserRoleService() {
        return EasyMock.mock(AppUserRoleService.class);
    }

    @Bean(name = "bankAccountPersistenceService")
    public BankAccountService getBankAccountService() {
        return EasyMock.mock(BankAccountService.class);
    }
    
    @Bean(name = "bankAccountJmsProducer")
    public BankAccountService getBankAccountJmsService() {
        return EasyMock.mock(BankAccountService.class);
    }
}
