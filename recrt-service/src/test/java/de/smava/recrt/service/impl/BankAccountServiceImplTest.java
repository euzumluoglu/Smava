package de.smava.recrt.service.impl;

import static org.easymock.EasyMock.anyObject;
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

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import de.smava.recrt.model.BankAccount;
import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.persistence.model.BankAccountEntity;
import de.smava.recrt.persistence.repository.AppUserRepository;
import de.smava.recrt.persistence.repository.BankAccountRepository;
import de.smava.recrt.service.BankAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { TestServiceConfig.class,
		de.smava.recrt.service.impl.BankAccountServiceImpl.class })
public class BankAccountServiceImplTest {

	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	private BankAccountService bankAccountService;
	
	@After
	public void tearDown(){
		reset(appUserRepository);
		reset(bankAccountRepository);
	}

	@Test
	public void testGetByAppUser() throws Exception {
		
		AppUserEntity user1 = new AppUserEntity("user1");
		AppUserEntity user2 = new AppUserEntity("user2");
		AppUserEntity user3 = new AppUserEntity("user3");
		
		BankAccountEntity account1 = new BankAccountEntity("00 11 22 33", "user1-bic", user1);
		List<BankAccountEntity> accounts = new ArrayList<BankAccountEntity>();
		accounts.add(account1);
		
		expect(appUserRepository.findOne("user1")).andReturn(user1).anyTimes();
		expect(appUserRepository.findOne("user2")).andReturn(user2).anyTimes();
		expect(appUserRepository.findOne("user3")).andReturn(null).anyTimes();
		replay(appUserRepository);
		
		expect(bankAccountRepository.findByAppUser(user1)).andReturn(accounts).anyTimes();
		expect(bankAccountRepository.findByAppUser(user2)).andReturn(null).anyTimes();
		replay(bankAccountRepository);
		
		List<? extends BankAccount> users = bankAccountService.getByAppUser(user3.getUsername());
		assertThat(users, is(notNullValue()));
		assertThat(users.size(), is(equalTo(0)));

		users = bankAccountService.getByAppUser(user2.getUsername());
		assertThat(users, is(nullValue()));

		users = bankAccountService.getByAppUser(user1.getUsername());
		assertThat(users, is(notNullValue()));
		assertThat(users.size(), is(equalTo(1)));
		assertThat(users.get(0).getBic(), is(equalTo("user1-bic")));

	}

	@Test
	public void testCreate() throws Exception {
		
		
		AppUserEntity user1 = new AppUserEntity("user1");
		AppUserEntity user2 = new AppUserEntity("user2");
		
		expect(appUserRepository.findOne("user1")).andReturn(user1).anyTimes();
		expect(appUserRepository.findOne("user2")).andReturn(null).anyTimes();
		replay(appUserRepository);
		
		BankAccountEntity account1 = new BankAccountEntity("44 55 66 77", "user2-bic", user1);
		BankAccountEntity account2 = new BankAccountEntity("44 55 66 77", "user2-bic", user2);
		
//		expect(bankAccountRepository.findByAppUser(user1)).andReturn(accounts).anyTimes();
//		expect(bankAccountRepository.findByAppUser(user2)).andReturn(null).anyTimes();
//		
		expect(bankAccountRepository.save(anyObject(BankAccountEntity.class))).andReturn(account1).anyTimes();
		replay(bankAccountRepository);
		
		BankAccount account = bankAccountService.create(account2);
		assertThat(account, is(nullValue()));

		account = bankAccountService.create(account1);
		assertThat(account, is(notNullValue()));
		assertThat(account.getBic(), is(equalTo("user2-bic")));
	}
}