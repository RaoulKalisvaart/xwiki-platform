/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.xwiki.extension.repository.xwiki.internal;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.xwiki.component.annotation.Component;
import org.xwiki.extension.repository.xwiki.Resources;
import org.xwiki.extension.repository.xwiki.model.jaxb.Extensions;
import org.xwiki.query.QueryException;

/**
 * @version $Id$
 * @since 3.2M3
 */
@Component("org.xwiki.extension.repository.xwiki.internal.ExtensionsRESTResource")
@Path(Resources.EXTENSIONS)
public class ExtensionsRESTResource extends AbstractExtensionRESTResource
{
    @GET
    public Extensions getExtensions(@QueryParam(Resources.QPARAM_SEARCH_START) @DefaultValue("0") Integer offset,
        @QueryParam(Resources.QPARAM_SEARCH_NUMBER) @DefaultValue("-1") int number) throws QueryException
    {
        return getExtensionSummaries(createExtensionsSummariesQuery(null, null, offset, number, false));
    }
}
