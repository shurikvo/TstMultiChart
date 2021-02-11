import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ChartComponent extends JFrame {
    private String fileName, title;
    private XYSeriesCollection dataset;

    public int createDataset(String sKey) {
        XYSeries series = new XYSeries(sKey);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String sB;

            while ((sB = br.readLine()) != null) {
                String [] sP = sB.split(":");
                String sLock = sP[0].substring(0,4);
                if (!sLock.equals(sKey))
                    continue;
                System.out.println(sB);
                System.out.println(sLock);
                series.add(Double.parseDouble(sP[0].substring(4)), Double.parseDouble(sP[1]));

            }
        } catch(IOException ex) {
            System.out.println(ex);
            return -1;
        }
        dataset.addSeries(series);

        return 0;
    }

    public void initUI() {
        JFreeChart chart = createChart(dataset);

        this.setLayout( new FlowLayout() );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        this.add(chartPanel);

        this.pack();
        this.setTitle("Line chart");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Months",
                "Card Quantity",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(title, new Font("Serif", java.awt.Font.BOLD, 18)));

        return chart;
    }

    public ChartComponent(String sTitle, String sFile) {
        this.fileName = sFile;
        this.title = sTitle;
        this.dataset = new XYSeriesCollection();
    }
}
