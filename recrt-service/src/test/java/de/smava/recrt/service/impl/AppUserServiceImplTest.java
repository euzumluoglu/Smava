package de.smava.recrt.service.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import de.smava.recrt.model.AppUser;
import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.persistence.repository.AppUserRepository;
import de.smava.recrt.service.AppUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { TestServiceConfig.class,
		de.smava.recrt.service.impl.AppUserServiceImpl.class })
public class AppUserServiceImplTest {

	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppUserService appUserService;
	
	@After
	public void teadDown(){
		reset(appUserRepository);		
	}

	@Test
	public void testGetAll() throws Exception {
		List<AppUserEntity> users = new ArrayList<AppUserEntity>();
		AppUserEntity user1 = new AppUserEntity("user1");
		users.add(user1);
		AppUserEntity user2 = new AppUserEntity("user2");
		users.add(user2);
		expect(appUserRepository.findAll()).andReturn(users).anyTimes();
		replay(appUserRepository);

		List<? extends AppUser> all = appUserService.getAll();
		assertThat(all.size(), is(equalTo(2)));
	}

	@Test
	public void testGet() throws Exception {
		
		AppUserEntity user1 = new AppUserEntity("user1");
		expect(appUserRepository.findOne("user1")).andReturn(user1).anyTimes();
		expect(appUserRepository.findOne("user3")).andReturn(null).anyTimes();
		replay(appUserRepository);

		AppUser user = appUserService.get("user3");
		assertThat(user, is(nullValue()));
		user = appUserService.get("user1");
		assertThat(user, is(notNullValue()));
		assertThat(user.getUsername(), is(equalTo("user1")));
	}
}