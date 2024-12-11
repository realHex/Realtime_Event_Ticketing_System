import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {interval, Observable, switchMap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CountPollingService {

  http = inject(HttpClient);

  pollTotalTickets(intervalMs: number): Observable<number> {
    return interval(1000).pipe(
      switchMap(() => this.http.get<number>('http://localhost:8080/api/ticketpool/total-tickets'))
    );
  }

  pollAddedTickets(intervalMs: number): Observable<number> {
    return interval(1000).pipe(
      switchMap(() => this.http.get<number>('http://localhost:8080/api/ticketpool/added-tickets'))
    );
  }

  pollPurchasedTickets(intervalMs: number): Observable<number> {
    return interval(1000).pipe(
      switchMap(() => this.http.get<number>('http://localhost:8080/api/ticketpool/purchased-tickets'))
    );
  }

  pollMaxTicketCapacity(intervalMs: number): Observable<number> {
    return interval(1000).pipe(
      switchMap(() => this.http.get<number>('http://localhost:8080/api/ticketpool/max-ticket-capacity'))
    );
  }

}
