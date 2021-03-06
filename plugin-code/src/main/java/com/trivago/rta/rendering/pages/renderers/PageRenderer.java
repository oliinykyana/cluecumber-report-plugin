/*
 * Copyright 2018 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trivago.rta.rendering.pages.renderers;

import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.rendering.pages.pojos.pagecollections.PageCollection;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

class PageRenderer {

    String processedContent(final Template template, final PageCollection pageCollection)
            throws CluecumberPluginException {

        Writer stringWriter = new StringWriter();
        try {
            template.process(pageCollection, stringWriter);
        } catch (TemplateException | IOException e) {
            throw new CluecumberPluginException("Could not render page content: " + e.getMessage());
        }
        return stringWriter.toString();
    }
}
