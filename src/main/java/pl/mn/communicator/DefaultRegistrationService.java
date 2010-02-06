/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator;

import pl.mn.communicator.packet.http.ChangePasswordRequest;
import pl.mn.communicator.packet.http.CommonRegisterResponse;
import pl.mn.communicator.packet.http.GGTokenRequest;
import pl.mn.communicator.packet.http.HttpResponse;
import pl.mn.communicator.packet.http.RegisterGGAccountRequest;
import pl.mn.communicator.packet.http.SendAndRemindPasswordRequest;
import pl.mn.communicator.packet.http.UnregisterGGPasswordRequest;
import pl.mn.communicator.packet.http.GGTokenRequest.GGTokenResponse;
import pl.mn.communicator.packet.http.RegisterGGAccountRequest.RegisterGGAccountResponse;

import java.io.IOException;

/**
 * Created on 2004-11-29
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: DefaultRegistrationService.java,v 1.1 2005/11/05 23:34:52 winnetou25 Exp $
 */
public class DefaultRegistrationService implements IRegistrationService {

	/** reference to session object */
	private Session m_session = null;

	// friendly
	public DefaultRegistrationService(final Session session) {
		if (session == null) {
			throw new NullPointerException("session cannot be null");
		}
		m_session = session;
	}

	/**
	 * @see pl.mn.communicator.IRegistrationService#getRegToken()
	 */
	public GGToken getRegistrationToken() throws GGException {

		GGTokenRequest tokenRequest = null;
		try {
			tokenRequest = new GGTokenRequest(m_session.getGGConfiguration());
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
	 * @see pl.mn.communicator.IRegistrationService#changePassword(int, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void changePassword(final int uin, final String email, final String oldPassword, final String newPassword, final String tokenID, final String tokenVal) throws GGException {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (email == null) {
			throw new NullPointerException("email cannot be null");
		}
		if (oldPassword == null) {
			throw new NullPointerException("oldPassword cannot be null");
		}
		if (newPassword == null) {
			throw new NullPointerException("newPassword cannot be null");
		}
		if (tokenID == null) {
			throw new NullPointerException("tokenID cannot be null");
		}
		if (tokenVal == null) {
			throw new NullPointerException("tokenVal cannot be null");
		}

		ChangePasswordRequest changePasswordRequest = null;
		try {
			changePasswordRequest = new ChangePasswordRequest(m_session.getGGConfiguration(), uin, email, oldPassword, newPassword, tokenID, tokenVal);
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
	 * @see pl.mn.communicator.IRegistrationService#registerAccount(java.lang.String email, java.lang.String password,
	 *      int qa, String answer)
	 */
	public int registerAccount(final String email, final String password, final String tokenID, final String tokenVal) throws GGException {
		if (email == null) {
			throw new NullPointerException("email cannot be null");
		}
		if (password == null) {
			throw new NullPointerException("password cannot be null");
		}
		if (tokenID == null) {
			throw new NullPointerException("password cannot be null");
		}
		if (tokenVal == null) {
			throw new NullPointerException("password cannot be null");
		}

		RegisterGGAccountRequest request = null;
		try {
			request = new RegisterGGAccountRequest(m_session.getGGConfiguration(), email, password, tokenID, tokenVal);
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
	 * @see pl.mn.communicator.IRegistrationService#unregisterAccount(int, java.lang.String)
	 */
	public void unregisterAccount(final int uin, final String password, final String tokenID, final String tokenVal) throws GGException {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (password == null) {
			throw new NullPointerException("password cannot be null");
		}
		if (tokenID == null) {
			throw new NullPointerException("tokenID cannot be null");
		}
		if (tokenVal == null) {
			throw new NullPointerException("tokenVal cannot be null");
		}

		UnregisterGGPasswordRequest unregisterGGPasswordRequest = null;
		try {
			unregisterGGPasswordRequest = new UnregisterGGPasswordRequest(m_session.getGGConfiguration(), uin, password, tokenID, tokenVal);
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
	 * @see pl.mn.communicator.IRegistrationService#remindAndSendPassword(int)
	 */
	public void sendPassword(final int uin, final String email, final String tokenID, final String tokenVal) throws GGException {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (email == null) {
			throw new NullPointerException("email cannot be null");
		}
		if (tokenID == null) {
			throw new NullPointerException("tokenID cannot be null");
		}
		if (tokenVal == null) {
			throw new NullPointerException("tokenVal cannot be null");
		}

		SendAndRemindPasswordRequest request = null;
		try {
			request = new SendAndRemindPasswordRequest(m_session.getGGConfiguration(), uin, email, tokenID, tokenVal);
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
