package com.example.mapara.mylab;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;

/**
 * Created by mapara on 4/13/16.
 */
public class GraphActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(graphHtml, "text/html", "UTF-8");
        setContentView(webView);
    }

    String graphHtml = "<html>\n" +
            "  <head>\n" +
            "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
            "    <script type=\"text/javascript\">\n" +
            "      google.charts.load('current', {'packages':['corechart']});\n" +
            "      google.charts.setOnLoadCallback(drawChart);\n" +
            "      function drawChart() {\n" +
            "\n" +
            "        var data = google.visualization.arrayToDataTable([\n" +
            "          ['Task', 'Hours per Day'],\n" +
            "          ['Work',     11],\n" +
            "          ['Eat',      2],\n" +
            "          ['Commute',  2],\n" +
            "          ['Watch TV', 2],\n" +
            "          ['Sleep',    7]\n" +
            "        ]);\n" +
            "\n" +
            "        var options = {\n" +
            "          title: 'My Daily Activities'\n" +
            "        };\n" +
            "\n" +
            "        var chart = new google.visualization.PieChart(document.getElementById('piechart'));\n" +
            "\n" +
            "        chart.draw(data, options);\n" +
            "      }\n" +
            "    </script>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <div id=\"piechart\" style=\"width: 900px; height: 500px;\"></div>\n" +
            "  </body>\n" +
            "</html>";
}
