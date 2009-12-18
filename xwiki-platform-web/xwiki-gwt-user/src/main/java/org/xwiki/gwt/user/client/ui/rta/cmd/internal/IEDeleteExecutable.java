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
package org.xwiki.gwt.user.client.ui.rta.cmd.internal;

import org.xwiki.gwt.dom.client.Range;
import org.xwiki.gwt.dom.client.Selection;
import org.xwiki.gwt.user.client.ui.rta.RichTextArea;

/**
 * Overwrites {@link DeleteExecutable} with a custom implementation for Internet Explorer to overcome a bug in the
 * native delete command which leaves the selection object in an invalid state if we execute it on a control selection
 * that includes a button while the document is rendered in standards mode. Trying to create a range after the button is
 * deleted results in a "Unspecified error" exception being thrown. Strangely, the delete command works fine when an
 * image is selected or if the document is not rendered in standards mode.
 * 
 * @version $Id$
 */
public class IEDeleteExecutable extends DeleteExecutable
{
    /**
     * {@inheritDoc}
     * 
     * @see DeleteExecutable#execute(RichTextArea, String)
     */
    public boolean execute(RichTextArea rta, String parameter)
    {
        Selection selection = rta.getDocument().getSelection();
        if (isControlSelection(selection)) {
            Range range = selection.getRangeAt(0);
            // Delete the selected control object (e.g. image, button).
            range.deleteContents();
            // Update the selection.
            // NOTE: We don't call Selection#removeAllRanges() because the current implementation calls
            // NativeSelection#empty() which fails on an empty control selection.
            selection.addRange(range);
            return true;
        } else {
            return super.execute(rta, parameter);
        }
    }

    /**
     * @param selection the selection object whose type is checked
     * @return {@code true} if the given selection is a control selection, {@code false} otherwise
     */
    private native boolean isControlSelection(Selection selection)
    /*-{
        return selection.@org.xwiki.gwt.dom.client.internal.ie.IESelection::nativeSelection.type == 'Control';
    }-*/;
}
