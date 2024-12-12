import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { ChartModule, UIChart } from 'primeng/chart';
import { PrimeTemplate } from 'primeng/api';
import { ProgressBarModule } from 'primeng/progressbar';
import { CountPollingService } from '../../services/polling/count-polling/count-polling.service';

/**
 * Component that displays a real-time chart of the number of tickets added and purchased over time.
 * It uses polling to fetch data periodically and updates the chart with new data points.
 */
@Component({
  selector: 'app-chart',
  standalone: true,
  imports: [
    ChartModule,
    PrimeTemplate,
    ProgressBarModule
  ],
  templateUrl: './chart.component.html',
  styleUrl: './chart.component.css'
})
export class ChartComponent implements OnInit {
  // Data structure for the chart, including datasets for added and purchased tickets
  data: any;
  options: any;

  // Time in seconds to display on the X-axis
  time: number = 0;
  // Maximum number of data points to display on the chart
  maxDataPoints: number = 20;
  // Maximum Y-axis value (tickets) to scale the chart dynamically
  currentYMax: number = 100;


  polling = inject(CountPollingService);

  // Reference to the chart element for updating the chart dynamically
  @ViewChild('chart') chart: UIChart | undefined;

  /**
   * Initializes the chart and starts polling to fetch real-time data.
   * Called once the component is initialized.
   */
  ngOnInit() {
    this.initializeChart(); // Initialize chart with initial settings and style
    this.startPolling(); // Start polling for real-time ticket data
  }

  /**
   * Initializes the chart with default options, data, and custom styles.
   * This method sets up the X and Y axes, chart colors, labels, and other configurations.
   */
  private initializeChart() {

    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    // Define initial data structure for the chart
    this.data = {
      labels: Array(this.maxDataPoints).fill(''),  // Empty labels to fill the chart's X-axis
      datasets: [
        {
          label: 'Tickets Added',
          data: Array(this.maxDataPoints).fill(null),  // Initialize dataset with null values
          fill: false,
          tension: 0.4,
          borderColor: documentStyle.getPropertyValue('--green-500'),
        },
        {
          label: 'Tickets Purchased',
          data: Array(this.maxDataPoints).fill(null),  // Initialize dataset with null values
          fill: false,
          borderColor: documentStyle.getPropertyValue('--orange-500'),
          tension: 0.4,
        },
      ],
    };

    // Define chart options, including styling for the axes, labels, and grid lines
    this.options = {
      maintainAspectRatio: false,
      aspectRatio: 0.6,
      plugins: {
        legend: {
          labels: {
            color: textColor,
          },
        },
      },
      scales: {
        x: {
          title: {
            display: true,
            text: 'Time (seconds)',
            color: textColor,
          },
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
          },
        },
        y: {
          title: {
            display: true,
            text: 'Number of Tickets',
            color: textColor,
          },
          ticks: {
            color: textColorSecondary,
          },
          grid: {
            color: surfaceBorder,
          },
          suggestedMin: 0,  // Set minimum value for Y-axis
          suggestedMax: this.currentYMax,  // Dynamically updated maximum Y-value
        },
      },
      animation: {
        duration: 0,
      },
      transitions: {
        active: {
          animation: {
            duration: 0,
          }
        }
      },
      responsiveAnimationDuration: 0,
    };
  }

  /**
   * Starts polling to fetch real-time data for added and purchased tickets.
   * It uses the CountPollingService to fetch data at regular intervals and updates the chart.
   */
  private startPolling(): void {
    // Poll for the number of tickets added every second (1000 ms)
    this.polling.pollAddedTickets(1000).subscribe((addedTickets) => {
      this.updateDataset(0, addedTickets);
    });

    // Poll for the number of tickets purchased every second (1000 ms)
    this.polling.pollPurchasedTickets(1000).subscribe((purchasedTickets) => {
      this.updateDataset(1, purchasedTickets);
    });
  }

  /**
   * Updates the chart's dataset with new data, shifting old data out and adding new data.
   * This ensures that the chart always displays the latest data and scrolls to the right.
   *
   * @param datasetIndex - The index of the dataset to update (0 for added tickets, 1 for purchased tickets)
   * @param newData - The new data point to add to the dataset
   */
  private updateDataset(datasetIndex: number, newData: number): void {
    this.time++;

    // Shift the existing data in the dataset to the left to make space for new data
    this.data.datasets[datasetIndex].data.shift();

    this.data.datasets[datasetIndex].data.push(newData);

    // Shift the labels on the X-axis to the left and add the new time label
    this.data.labels.shift();
    this.data.labels.push(`${this.time}s`);

    // Update the Y-axis scale dynamically based on the new data
    this.updateYAxisScale(newData);

    // If the chart exists, update it with the new data
    if (this.chart?.chart) {
      this.chart.chart.update();
    }
  }

  /**
   * Dynamically adjusts the Y-axis scale if the new data exceeds the current maximum value.
   * It increases the Y-axis max value in increments of 10, ensuring the chart is always scaled properly.
   *
   * @param newValue - The new data value to check against the current Y-axis maximum
   */
  private updateYAxisScale(newValue: number): void {
    // If the new data exceeds the current Y-axis maximum, update it
    if (newValue > this.currentYMax) {
      // Increase the Y-axis maximum by rounding up the value to the next multiple of 10
      this.currentYMax = Math.ceil(newValue / 10) * 10;
      this.options.scales.y.suggestedMax = this.currentYMax;  // Update the suggested max value for Y-axis

      // Apply the new max value to the chart's Y-axis scale
      if (this.chart?.chart) {
        this.chart.chart.scales.y.max = this.currentYMax;  // Update the Y-axis max scale
      }
    }
  }
}
