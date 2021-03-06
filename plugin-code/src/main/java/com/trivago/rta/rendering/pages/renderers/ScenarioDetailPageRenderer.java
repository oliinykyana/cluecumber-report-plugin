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

import be.ceau.chart.BarChart;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.XAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import com.trivago.rta.constants.ChartColor;
import com.trivago.rta.exceptions.CluecumberPluginException;
import com.trivago.rta.json.pojo.Step;
import com.trivago.rta.rendering.pages.pojos.pagecollections.DetailPageCollection;
import freemarker.template.Template;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ScenarioDetailPageRenderer extends PageRenderer {

    public String getRenderedContent(final DetailPageCollection detailPageCollection, final Template template)
            throws CluecumberPluginException {

        addChartJsonToReportDetails(detailPageCollection);
        return processedContent(template, detailPageCollection);
    }

    private void addChartJsonToReportDetails(final DetailPageCollection detailPageCollection) {
        BarDataset barDataSet = new BarDataset();

        BarData barData = new BarData();
        int stepCounter = 1;

        for (Step step : detailPageCollection.getElement().getSteps()) {
            barData.addLabel("Step " + stepCounter);
            barDataSet.addData(step.getResult().getDurationInMilliseconds());
            barDataSet.addBackgroundColor(ChartColor.getChartColorByStatus(step.getStatus()));
            stepCounter++;
        }
        barDataSet.setLabel("Step runtime");
        barData.addDataset(barDataSet);

        BarScale barScale = new BarScale();
        List<XAxis<LinearTicks>> xAxisList = new ArrayList<>();
        xAxisList.add(new XAxis<LinearTicks>().setTicks(new LinearTicks().setMin(0)));
        barScale.setxAxes(xAxisList);

        BarOptions barOptions = new BarOptions().setScales(barScale);

        detailPageCollection.getReportDetails().setChartJson(new BarChart(barData, barOptions).toJson());
    }
}
