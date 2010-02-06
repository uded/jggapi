/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg.packet.http;

import pl.radical.open.gg.IGGConfiguration;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created on 2005-01-27
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: HttpRequest.java,v 1.2 2007/05/07 16:22:30 winnetou25 Exp $
 */
public abstract class HttpRequest {

	protected final IGGConfiguration m_ggconfiguration;

	protected final HttpURLConnection m_huc;

	protected HttpRequest(final IGGConfiguration configuration) throws IOException {
		if (configuration == null) {
			throw new IllegalArgumentException("configuration cannot be null");
		}
		m_ggconfiguration = configuration;

		final URL url = new URL(getURL());
		m_huc = (HttpURLConnection) url.openConnection();
		// available only in JDK 1.5
		// m_huc.setConnectTimeout(m_ggconfiguration.getSocketTimeoutInMiliseconds());
		// m_huc.setReadTimeout(m_ggconfiguration.getSocketTimeoutInMiliseconds());

		m_huc.setRequestMethod("POST");
		m_huc.setDoInput(true);
		if (wannaWrite()) {
			m_huc.setDoOutput(true);
		}
		m_huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		m_huc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98)");
	}

	public HttpURLConnection connect() throws IOException {
		m_huc.setRequestProperty("Content-Length", String.valueOf(getRequestBody().length()));
		m_huc.connect();

		return m_huc;
	}

	public HttpURLConnection sendRequest() throws IOException {
		if (wannaWrite()) {
			final PrintWriter out = new PrintWriter(m_huc.getOutputStream(), true);

			out.println(getRequestBody());
			out.close();
		}

		return m_huc;
	}

	public HttpURLConnection disconnect() {
		if (m_huc == null) {
			throw new IllegalStateException("must call connect() and sendRequest() first");
		}
		m_huc.disconnect();

		return m_huc;
	}

	public abstract HttpResponse getResponse() throws IOException;

	protected abstract String getURL();

	protected abstract String getRequestBody() throws UnsupportedEncodingException;

	protected abstract boolean wannaWrite();

}
