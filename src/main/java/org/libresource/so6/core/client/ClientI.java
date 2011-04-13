/**
 * LibreSource
 * Copyright (C) 2004-2008 Artenum SARL / INRIA
 * http://www.libresource.org - contact@artenum.com
 *
 * This file is part of the LibreSource software, 
 * which can be used and distributed under license conditions.
 * The license conditions are provided in the LICENSE.TXT file 
 * at the root path of the packaging that enclose this file. 
 * More information can be found at 
 * - http://dev.libresource.org/home/license
 *
 * Initial authors :
 *
 * Guillaume Bort / INRIA
 * Francois Charoy / Universite Nancy 2
 * Julien Forest / Artenum
 * Claude Godart / Universite Henry Poincare
 * Florent Jouille / INRIA
 * Sebastien Jourdain / INRIA / Artenum
 * Yves Lerumeur / Artenum
 * Pascal Molli / Universite Henry Poincare
 * Gerald Oster / INRIA
 * Mariarosa Penzi / Artenum
 * Gerard Sookahet / Artenum
 * Raphael Tani / INRIA
 *
 * Contributors :
 *
 * Stephane Bagnier / Artenum
 * Amadou Dia / Artenum-IUP Blois
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package org.libresource.so6.core.client;

public interface ClientI {
    public static final String SO6_QUEUE_ID = "so6.queue.id";
    public static final String SO6_XML_DETECT = "so6.xml.autodetect";
    public final static String SO6_LOGIN = "so6.user.login";
    public final static String SO6_PASSWORD = "so6.user.password";

    /**
     * Return the last ticket for that queue
     *
     * @return the last ticket for that queue
     * @throws Exception
     */
    public long getLastTicket() throws AuthenticationException, UnableToContactServerException, ServerException, ConnectionException, LocalException;

    /**
     * Return the path of a patch file that is defined with the specified
     * timestamp in the global history.
     *
     * @param ticket
     *            Timestamp for the global history
     * @return The file path of that patch
     *
     * @throws Exception
     */
    public String getPatch(long ticket)
        throws AuthenticationException, PatchNotFoundException, UnableToContactServerException, ServerException, ConnectionException, LocalException;

    /**
     * Return the path of the patch file that is defined with the specified
     * fromTicket timestamp and go to the toTicket timstamp on the global
     * history.
     *
     * @param fromTicket
     *            Timestamp for the global history (from)
     * @param toTicket
     *            Timestamp for the global history (to)
     * @return The file path of that patch
     *
     * @throws Exception
     */
    public String getPatch(long fromTicket, long toTicket)
        throws AuthenticationException, PatchNotFoundException, UnableToContactServerException, ServerException, ConnectionException, LocalException;

    /**
     * Send patch file to the queue.
     *
     * @param ticket
     *            Base patch timestamp
     * @param lastticket
     *            End patch timestamp
     * @param patchfile
     *            Path of the new patch file
     *
     * @throws Exception
     */
    public void sendPatch(long ticket, long lastticket, String patchfile, boolean validate)
        throws AuthenticationException, InvalidTicketException, UnableToContactServerException, ServerException, ConnectionException, LocalException;

    /**
     * List the patch as an array of long. <b>listPatch()[n][3] </b> <br>
     * For each patch we have a triplet made of {fromTicket, toTicket,
     * patchSize}
     *
     * @return <b>listPatch()[n][3] </b>
     * @throws Exception
     */
    public long[][] listPatch() throws AuthenticationException, UnableToContactServerException, ServerException, ConnectionException, LocalException;

    /**
     * Add a binary extention in the queue definition
     *
     * @param ext
     *            String that represent the file extention
     *
     * @throws Exception
     */
    public void addBinExt(String ext) throws AuthenticationException, UnableToContactServerException, ServerException, ConnectionException, LocalException;

    /**
     * Remove a binary extention in the queue definition
     *
     * @param ext
     *            String that represent the file extention
     *
     * @throws Exception
     */
    public void removeBinExt(String ext) throws AuthenticationException, UnableToContactServerException, ServerException, ConnectionException, LocalException;

    /**
     * Return the concatenation of all binary extention with a space as
     * separator.
     *
     * @return the concatenation of all binary extention with a space as
     *         separator.
     * @throws Exception
     */
    public String getBinExt() throws AuthenticationException, UnableToContactServerException, ServerException, ConnectionException, LocalException;

	// TODO: write javadoc ;)
	public void setTicketConsumerByUser(long ticket, String userId)
		throws AuthenticationException, InvalidTicketException, UnableToContactServerException, ServerException,
		ConnectionException, LocalException ;

}
