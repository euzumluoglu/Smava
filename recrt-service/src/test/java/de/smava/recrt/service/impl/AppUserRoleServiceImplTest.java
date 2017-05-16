package de.smava.recrt.service.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.util.ReflectionTestUtils;

import de.smava.recrt.model.AppUserRole;
import de.smava.recrt.model.UserRole;
import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.persistence.model.AppUserRoleEntity;
import de.smava.recrt.persistence.repository.AppUserRoleRepository;
import de.smava.recrt.service.AppUserRoleService;
import de.smava.recrt.service.AppUserService;
import de.smava.recrt.service.BankAccountService;
import de.smava.recrt.service.config.CachingConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class , classes = {TestServiceConfig.class,de.smava.recrt.service.impl.AppUserRoleServiceImpl.class})
public class AppUserRoleServiceImplTest {

    @Autowired
    AppUserRoleRepository repository;
    
    @Autowired
    AppUserRoleService appUserRoleService;

	@Test
	public void testGetByAppUser() throws Exception {
		
		AppUserEntity user1 = new AppUserEntity("user1");
		expect(repository.findByKeyAppUser(user1)).andReturn(null).anyTimes();
		replay(repository);
		List<? extends AppUserRole> roles = appUserRoleService.getByAppUser(user1);
    	assertThat(roles, is(nullValue()));
		
	
	}
}