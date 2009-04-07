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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Properties;


/**
 * @author smack
 */
public class LibresourceDataflowUriResolver implements WebResolver {
    public final static String BASE_URL_POINT = "so6.dataflow.libresource.url.endpoint";
    private String baseUrl;

    public LibresourceDataflowUriResolver() {
        baseUrl = System.getProperty(BASE_URL_POINT);
    }

    public String getUrlFromProperties(Properties props) {
        try {
            URI uri = new URI(props.getProperty("uri"));

            return baseUrl + uri.getPath();
        } catch (URISyntaxException e) {
            //
        }

        return null;
    }
}
