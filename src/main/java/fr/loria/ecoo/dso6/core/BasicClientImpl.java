package fr.loria.ecoo.dso6.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.libresource.so6.core.client.AuthenticationException;
import org.libresource.so6.core.client.ClientI;
import org.libresource.so6.core.client.ConnectionException;
import org.libresource.so6.core.client.InvalidTicketException;
import org.libresource.so6.core.client.LocalException;
import org.libresource.so6.core.client.PatchNotFoundException;
import org.libresource.so6.core.client.ServerException;
import org.libresource.so6.core.client.UnableToContactServerException;

public class BasicClientImpl implements ClientI {

	public final static String DSO6_COMMIT_CAPABILITY = "dso6.commit.capability";
	public final static String DSO6_UPDATE_CAPABILITY = "dso6.update.capability";
	public final static String DSO6_ENDPOINT_URI = "dso6.endpoint.uri";
	private final static String DEFAULT_BINEXT = "class pdf ps eps exe zip jar gif jpg png";
	// private final static String PROP_FILE = "so6.properties";
	// private final static String LAST_TICKET = "so6.last.ticket";
	// private final static String BINEXT = "so6.bin.ext";

	private final static String TICKET = "ticket";
	private final static String PATCHES = "patches";
	private final static String ALL = "all";

	private DefaultHttpClient httpclient;
	private String commitServiceURI;
	private String updateServiceURI;
	private String queueId;

	public BasicClientImpl(Properties props) throws Exception {
		this.commitServiceURI = props.getProperty(DSO6_ENDPOINT_URI) + props.getProperty(DSO6_COMMIT_CAPABILITY);
		this.updateServiceURI = props.getProperty(DSO6_ENDPOINT_URI) + props.getProperty(DSO6_UPDATE_CAPABILITY);
		this.queueId = props.getProperty(ClientI.SO6_QUEUE_ID);

		if (this.queueId == null) {
			throw new RuntimeException("Properties are missing for BasicClientImpl initialisation");
		}

		this.httpclient = new DefaultHttpClient();
	}

	@Override
	public long getLastTicket() throws AuthenticationException, UnableToContactServerException, ServerException,
			ConnectionException, LocalException {
		String endPoint = this.updateServiceURI + "/" + TICKET;
		HttpGet method = new HttpGet(endPoint);

		try {
			HttpResponse response = httpclient.execute(method);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new ServerException(response.getStatusLine().getReasonPhrase());
			} else {
				HttpEntity entity = response.getEntity();
				Scanner scan = new Scanner(entity.getContent());
				int ticket = scan.nextInt();
				return ticket;
			}
		} catch (ClientProtocolException e) {
			throw new ServerException(e);
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	@Override
	public long[][] listPatch() throws AuthenticationException, UnableToContactServerException, ServerException,
			ConnectionException, LocalException {
		String endPoint = this.updateServiceURI + "/" + PATCHES + "/" + ALL;
		HttpGet method = new HttpGet(endPoint);
		try {
			HttpResponse response = httpclient.execute(method);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new ServerException(response.getStatusLine().getReasonPhrase());
			} else {
				HttpEntity entity = response.getEntity();
				Scanner scan = new Scanner(entity.getContent());
				List<long[]> patches = new ArrayList<long[]>();
				while (scan.hasNext()) {
					scan.next(); // skip patch-id
					long fromTicket = scan.nextLong();
					long toTicket = scan.nextLong();
					long size = scan.nextLong();
					patches.add(new long[] { fromTicket, toTicket, size });
				}

				long[][] res = new long[patches.size()][];
				for (int i = 0; i < patches.size(); i++) {
					res[i] = patches.get(i);
				}
				return res;
			}
		} catch (ClientProtocolException e) {
			throw new ServerException(e);
		} catch (IOException e) {
			throw new ServerException(e);
		}

	}

	@Override
	public void sendPatch(long ticket, long lastTicket, String patchFile, boolean validate)
			throws AuthenticationException, InvalidTicketException, UnableToContactServerException, ServerException,
			ConnectionException, LocalException {
		String ticketCode = ticket + "." + lastTicket;
		String endPoint = this.commitServiceURI + "/" + PATCHES + "/" + ticketCode;
		HttpPost method = new HttpPost(endPoint);

		try {
			MultipartEntity entity = new MultipartEntity();
			entity.addPart("fromTicket", new StringBody(Long.toString(ticket), Charset.forName("UTF-8")));
			entity.addPart("toTicket", new StringBody(Long.toString(lastTicket), Charset.forName("UTF-8")));
			entity.addPart("validate", new StringBody(Boolean.toString(validate), Charset.forName("UTF-8")));
			FileBody fileBody = new FileBody(new File(patchFile));
			entity.addPart("patchfile", fileBody);
			method.setEntity(entity);
			HttpResponse response = httpclient.execute(method);
			switch (response.getStatusLine().getStatusCode()) {
			case 200:
				break;
			case 404:
				throw new InvalidTicketException(response.getStatusLine().getReasonPhrase());
			default:
				throw new ServerException(response.getStatusLine().getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			throw new ServerException(e);
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	@Override
	public String getPatch(long ticket) throws AuthenticationException, PatchNotFoundException,
			UnableToContactServerException, ServerException, ConnectionException, LocalException {
		return this.getPatch(ticket, -1);
	}

	@Override
	public String getPatch(long fromTicket, long toTicket) throws AuthenticationException, PatchNotFoundException,
			UnableToContactServerException, ServerException, ConnectionException, LocalException {
		String ticketCode = (toTicket == -1) ? fromTicket + "" : fromTicket + "." + toTicket;
		String endPoint = this.updateServiceURI + "/" + PATCHES + "/" + ticketCode;
		HttpGet method = new HttpGet(endPoint);
		try {
			HttpResponse response = httpclient.execute(method);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new ServerException(response.getStatusLine().getReasonPhrase());
			} else {
				BufferedHttpEntity bentity = new BufferedHttpEntity(response.getEntity());
				File tmpFile = File.createTempFile("so6", ".patch");
				tmpFile.deleteOnExit();
				String tmpFilePatch = tmpFile.getAbsolutePath();
				bentity.writeTo(new FileOutputStream(new File(tmpFilePatch)));
				return tmpFilePatch;
			}
		} catch (ClientProtocolException e) {
			throw new ServerException(e);
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	@Override
	public void addBinExt(String ext) throws AuthenticationException, UnableToContactServerException, ServerException,
			ConnectionException, LocalException {
		throw new LocalException("addBinExt not yet implemented");
	}

	@Override
	public String getBinExt() throws AuthenticationException, UnableToContactServerException, ServerException,
			ConnectionException, LocalException {
		return DEFAULT_BINEXT;
	}

	@Override
	public void removeBinExt(String ext) throws AuthenticationException, UnableToContactServerException,
			ServerException, ConnectionException, LocalException {
		throw new LocalException("removeBinExt not yet implemented");
	}
}
