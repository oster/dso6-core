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
package org.libresource.so6.core.net;

import org.libresource.so6.core.client.AuthenticationException;
import org.libresource.so6.core.client.LocalException;
import org.libresource.so6.core.client.ServerException;
import org.libresource.so6.core.client.UnableToContactServerException;


public interface DataflowClientI extends DataflowReaderClientI {
    public static final String SO6_CONNECTION_ID = "so6.connection.id";

    public void createWorkspace(String wsId) throws LocalException, ServerException, AuthenticationException, UnableToContactServerException;

    public void removeWorkspace(String wsId) throws LocalException, ServerException, AuthenticationException, UnableToContactServerException;

    public void notifyWorkspaceConnections(String wsId, String[] queueIds)
        throws LocalException, ServerException, AuthenticationException, UnableToContactServerException;

    public String addWsConnection(String wsId, String queueId, String wsName, String wscPath)
        throws LocalException, ServerException, AuthenticationException, UnableToContactServerException;

    public void removeWsConnection(String wsId, String queueId)
        throws LocalException, ServerException, AuthenticationException, UnableToContactServerException;
}
