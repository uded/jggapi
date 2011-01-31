package pl.radical.open.gg;

import pl.radical.open.gg.packet.http.ChangePasswordRequest;
import pl.radical.open.gg.packet.http.CommonRegisterResponse;
import pl.radical.open.gg.packet.http.GGTokenRequest;
import pl.radical.open.gg.packet.http.GGTokenRequest.GGTokenResponse;
import pl.radical.open.gg.packet.http.HttpResponse;
import pl.radical.open.gg.packet.http.RegisterGGAccountRequest;
import pl.radical.open.gg.packet.http.RegisterGGAccountRequest.RegisterGGAccountResponse;
import pl.radical.open.gg.packet.http.SendAndRemindPasswordRequest;
import pl.radical.open.gg.packet.http.UnregisterGGPasswordRequest;

import java.io.IOException;

import com.google.common.base.Preconditions;

/**
 * Created on 2004-11-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class DefaultRegistrationService implements IRegistrationService {

	/** reference to session object */
	private Session session = null;

	// friendly
	public DefaultRegistrationService(final Session session) {
		if (session == null) {
			throw new IllegalArgumentException("session cannot be null");
		}
		this.session = session;
	}

	/**
	 * @see pl.radical.open.gg.IRegistrationService#getRegToken()
	 */
	@Override
	public GGToken getRegistrationToken() throws GGException {
		GGTokenRequest tokenRequest = null;
		try {
			tokenRequest = new GGTokenRequest(session.getGGConfiguration());
			tokenRequest.connect();
			final GGTokenRequest.GGTokenResponse response = (GGTokenResponse) tokenRequest.getResponse();

			if (!response.isOKResponse()) {
				throw new GGException("Error occured while requesting Gadu-Gadu token, reason: " + response.getResponseMessage());
			}

			return response.getGGToken();
		} catch (final IOException ex) {
			throw new GGException("Unable to get token", ex);
		} finally {
			if (tokenRequest != null) {
				tokenRequest.disconnect();
			}
		}
	}

	/**
	 * @see pl.radical.open.gg.IRegistrationService#changePassword(int, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void changePassword(final int uin, final String email, final String oldPassword, final String newPassword, final String tokenID, final String tokenVal) throws GGException {
		Preconditions.checkArgument(uin < 0, new IllegalArgumentException("uin cannot be less than 0"));
		Preconditions.checkNotNull(email, new IllegalArgumentException("email cannot be null"));
		Preconditions.checkNotNull(oldPassword, new IllegalArgumentException("oldPassword cannot be null"));
		Preconditions.checkNotNull(newPassword, new IllegalArgumentException("newPassword cannot be null"));
		Preconditions.checkNotNull(tokenID, new IllegalArgumentException("tokenID cannot be null"));
		Preconditions.checkNotNull(tokenVal, new IllegalArgumentException("tokenVal cannot be null"));

		ChangePasswordRequest changePasswordRequest = null;
		try {
			changePasswordRequest = new ChangePasswordRequest(session.getGGConfiguration(), uin, email, oldPassword, newPassword, tokenID, tokenVal);
			changePasswordRequest.connect();
			changePasswordRequest.sendRequest();

			final CommonRegisterResponse response = (CommonRegisterResponse) changePasswordRequest.getResponse();
			if (!response.isOKResponse()) {
				throw new GGException("Error occured while trying to change Gadu-Gadu password, reason: " + response.getResponseMessage());
			}

		} catch (final IOException ex) {
			throw new GGException("Unable to change Gadu-Gadu password", ex);
		} finally {
			if (changePasswordRequest != null) {
				changePasswordRequest.disconnect();
			}
		}
	}

	/**
	 * @see pl.radical.open.gg.IRegistrationService#registerAccount(java.lang.String email, java.lang.String password,
	 *      int qa, String answer)
	 */
	@Override
	public int registerAccount(final String email, final String password, final String tokenID, final String tokenVal) throws GGException {
		Preconditions.checkNotNull(email, new IllegalArgumentException("email cannot be null"));
		Preconditions.checkNotNull(password, new IllegalArgumentException("password cannot be null"));
		Preconditions.checkNotNull(tokenID, new IllegalArgumentException("tokenID cannot be null"));
		Preconditions.checkNotNull(tokenVal, new IllegalArgumentException("tokenVal cannot be null"));

		RegisterGGAccountRequest request = null;
		try {
			request = new RegisterGGAccountRequest(session.getGGConfiguration(), email, password, tokenID, tokenVal);
			request.connect();
			request.sendRequest();
			final RegisterGGAccountRequest.RegisterGGAccountResponse response = (RegisterGGAccountResponse) request.getResponse();

			if (!response.isOKResponse()) {
				throw new GGException("Error occured while requesting to send password, reason: " + response.getResponseMessage());
			}

			return response.getNewUin();
		} catch (final IOException ex) {
			throw new GGException("Unable to remind and send password", ex);
		} finally {
			if (request != null) {
				request.disconnect();
			}
		}
	}

	/**
	 * @see pl.radical.open.gg.IRegistrationService#unregisterAccount(int, java.lang.String)
	 */
	@Override
	public void unregisterAccount(final int uin, final String password, final String tokenID, final String tokenVal) throws GGException {
		Preconditions.checkArgument(uin < 0, new IllegalArgumentException("uin cannot be less than 0"));
		Preconditions.checkNotNull(password, new IllegalArgumentException("password cannot be null"));
		Preconditions.checkNotNull(tokenID, new IllegalArgumentException("tokenID cannot be null"));
		Preconditions.checkNotNull(tokenVal, new IllegalArgumentException("tokenVal cannot be null"));

		UnregisterGGPasswordRequest unregisterGGPasswordRequest = null;
		try {
			unregisterGGPasswordRequest = new UnregisterGGPasswordRequest(session.getGGConfiguration(), uin, password, tokenID, tokenVal);
			unregisterGGPasswordRequest.connect();
			unregisterGGPasswordRequest.sendRequest();

			final CommonRegisterResponse response = (CommonRegisterResponse) unregisterGGPasswordRequest.getResponse();

			if (!response.isOKResponse()) {
				throw new GGException("Error occured while trying to unregister Gadu-Gadu account, reason: " + response
						.getResponseMessage());
			}

		} catch (final IOException ex) {
			throw new GGException("Unable to unregister account", ex);
		} finally {
			if (unregisterGGPasswordRequest != null) {
				unregisterGGPasswordRequest.disconnect();
			}
		}
	}

	/**
	 * @see pl.radical.open.gg.IRegistrationService#remindAndSendPassword(int)
	 */
	@Override
	public void sendPassword(final int uin, final String email, final String tokenID, final String tokenVal) throws GGException {
		Preconditions.checkArgument(uin < 0, new IllegalArgumentException("uin cannot be less than 0"));
		Preconditions.checkNotNull(email, new IllegalArgumentException("email cannot be null"));
		Preconditions.checkNotNull(tokenID, new IllegalArgumentException("tokenID cannot be null"));
		Preconditions.checkNotNull(tokenVal, new IllegalArgumentException("tokenVal cannot be null"));

		SendAndRemindPasswordRequest request = null;
		try {
			request = new SendAndRemindPasswordRequest(session.getGGConfiguration(), uin, email, tokenID, tokenVal);
			request.connect();
			request.sendRequest();

			final HttpResponse response = request.getResponse();
			if (!response.isOKResponse()) {
				throw new GGException("Error occured while requesting to send password, response: " + response.getResponseMessage());
			}
		} catch (final IOException ex) {
			throw new GGException("Unable to remind and send password", ex);
		} finally {
			if (request != null) {
				request.disconnect();
			}
		}
	}

}
