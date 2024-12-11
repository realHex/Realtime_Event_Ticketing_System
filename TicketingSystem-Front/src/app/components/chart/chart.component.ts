import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { ChartModule, UIChart } from 'primeng/chart';
import { PrimeTemplate } from 'primeng/api';
import { ProgressBarModule } from 'primeng/progressbar';
import { CountPollingService } from '../../services/polling/count-polling/count-polling.service';

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
  data: any;
  options: any;

  time: number = 0;
  maxDataPoints: number = 20;
  currentYMax: number = 100;

  polling = inject(CountPollingService);

  @ViewChild('chart') chart: UIChart | undefined;

  ngOnInit() {
    this.initializeChart();
    this.startPolling();
  }

  private initializeChart() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    this.data = {
      labels: Array(this.maxDataPoints).fill(''),
      datasets: [
        {
          label: 'Tickets Added',
          data: Array(this.maxDataPoints).fill(null),
          fill: false,
          tension: 0.4,
          borderColor: documentStyle.getPropertyValue('--green-500'),
        },
        {
          label: 'Tickets Purchased',
          data: Array(this.maxDataPoints).fill(null),
          fill: false,
          borderColor: documentStyle.getPropertyValue('--orange-500'),
          tension: 0.4,
        },
      ],
    };

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
          suggestedMin: 0,
          suggestedMax: this.currentYMax,
        },
      },
      animation: {
        duration: 0
      },
      transitions: {
        active: {
          animation: {
            duration: 0
          }
        }
      },
      responsiveAnimationDuration: 0,
    };
  }

  private startPolling(): void {
    this.polling.pollAddedTickets(1000).subscribe((addedTickets) => {
      this.updateDataset(0, addedTickets);
    });

    this.polling.pollPurchasedTickets(1000).subscribe((purchasedTickets) => {
      this.updateDataset(1, purchasedTickets);
    });
  }

  private updateDataset(datasetIndex: number, newData: number): void {
    this.time++;


    this.data.datasets[datasetIndex].data.shift();
    this.data.datasets[datasetIndex].data.push(newData);


    this.data.labels.shift();
    this.data.labels.push(`${this.time}s`);


    this.updateYAxisScale(newData);


    if (this.chart?.chart) {
      this.chart.chart.update();
    }
  }

  private updateYAxisScale(newValue: number): void {
    if (newValue > this.currentYMax) {
      this.currentYMax = Math.ceil(newValue / 10) * 10;
      this.options.scales.y.suggestedMax = this.currentYMax;


      if (this.chart?.chart) {
        this.chart.chart.scales.y.max = this.currentYMax;
      }
    }
  }
}

