package pl.radical.open.gg;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
	public static final int TEST_UIN_1 = 20239471;
	public static final int TEST_UIN_2 = 20241237;

	private final LoginContext loginContext1 = new LoginContext(TEST_UIN_1, "RadicalEntropy");
	private final LoginContext loginContext2 = new LoginContext(TEST_UIN_2, "RadicalTest");

	private ISession session1;
	private ISession session2;

	@PostConstruct
	public void init() throws GGException {
		session1 = SessionFactory.createSession();
	}
}
