import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {interval, Observable, switchMap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogPollingService {

    http = inject(HttpClient);

  getLogList(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/logs/get-logs');
  }

  pollLogs(intervalTime: number): Observable<any[]> {
    return interval(intervalTime).pipe(
      switchMap(() => this.getLogList())
    );
  }

}
