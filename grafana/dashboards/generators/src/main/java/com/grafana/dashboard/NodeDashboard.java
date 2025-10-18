package com.grafana.dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.grafana.foundation.dashboard.Dashboard;
import com.grafana.foundation.common.Constants;
import com.grafana.foundation.dashboard.DashboardBuilder;
import com.grafana.foundation.dashboard.DashboardDashboardTimeBuilder;
import com.grafana.foundation.dashboard.RowBuilder;
import com.grafana.foundation.prometheus.DataqueryBuilder;
import com.grafana.foundation.timeseries.PanelBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import picocli.CommandLine;
import picocli.CommandLine.Option;

/**
 * NodeDashboard - A simple example using Grafana Foundation SDK to generate a dashboard
 * and print it as JSON.
 */
public class NodeDashboard {
    public static class Args {
        @Option(names = {"-o", "--output"})
        String output;
    }
    
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println("Generating Node Dashboard using Grafana Foundation SDK...");
        Args opts = new Args();
        new CommandLine(opts).parseArgs(args);
        
        NodeDashboard generator = new NodeDashboard();
        String dashboardJson = generator.generateDashboard();
        
        if (opts.output != null) {
            try (FileWriter writer = new FileWriter(opts.output)) {
                writer.write(dashboardJson);
                System.out.println("✅ Dashboard JSON saved to: " + opts.output);
            } catch (IOException e) {
                System.err.println("❌ Error writing to file " + opts.output + ": " + e.getMessage());
                System.exit(1);
            }
        } else {
            System.out.println("=== Generated Dashboard JSON ===");
            System.out.println(dashboardJson);
        }
    }
    
    /**
     * Generates a Node Exporter dashboard using Grafana Foundation SDK
     * @return JSON string representation of the dashboard
     */
    public String generateDashboard() throws JsonProcessingException {
        Dashboard dashboard = new DashboardBuilder("Node Exporter Dashboard")
                .uid("node-exporter-dashboard")
                .tags(List.of("node-exporter", "monitoring", "generated"))
                .refresh("5s")
                .time(new DashboardDashboardTimeBuilder()
                        .from("now-1h")
                        .to("now")
                )
                .timezone(Constants.TimeZoneBrowser)
                .withRow(new RowBuilder("System Overview"))
                .withPanel(createCpuUsagePanel())
                .build();

        return dashboard.toJSON();
    }
    
    /**
     * Creates a CPU Usage panel using Timeseries panel type
     */
    private PanelBuilder createCpuUsagePanel() {
        return new PanelBuilder()
                .title("CPU Usage")
                .unit("percent")
                .min(0)
                .max(100)
                .withTarget(new DataqueryBuilder()
                        .expr("cpu_usage * 100")
                        .refId("A")
                        .legendFormat("CPU Usage")
                );
    }
}
