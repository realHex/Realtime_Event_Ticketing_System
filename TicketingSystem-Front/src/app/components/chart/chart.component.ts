import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {ChartModule} from 'primeng/chart';
import {PrimeTemplate} from 'primeng/api';
import {ProgressBarModule} from 'primeng/progressbar';
import {CountPollingService} from '../../services/polling/count-polling/count-polling.service';
import {Chart} from 'chart.js';

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

  polling = inject(CountPollingService);

  @ViewChild('chart') chart: Chart | undefined;

  ngOnInit() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');


    this.data = {
      labels: [], //Time Interval
      datasets: [
        {
          label: 'Tickets Added',
          data: [],
          fill: false,
          tension: 0.4,
          borderColor: documentStyle.getPropertyValue('--green-500'),
        },
        {
          label: 'Tickets Purchased',
          data: [],
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
        },
      },
    };

    this.startPolling();
  }

  private startPolling(): void {
    this.polling.pollAddedTickets(1000).subscribe((addedTickets) => {
      this.updateDataset(this.data.datasets[0].data, addedTickets);
    });

    this.polling.pollPurchasedTickets(1000).subscribe((purchasedTickets) => {
      this.updateDataset(this.data.datasets[1].data, purchasedTickets);
    });
  }

  private updateDataset(dataset: number[], newData: number): void {
    this.time++;


    dataset.push(newData);

    if (dataset.length > 10) {
      dataset.shift();
    }

    if (this.data.labels.length < dataset.length) {
      this.data.labels.push(`${this.time}s`);
    } else {
      this.data.labels.shift();
      this.data.labels.push(`${this.time}s`);
    }

    this.chart?.update();
  }
}
