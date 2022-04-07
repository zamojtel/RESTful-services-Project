import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RatesView} from "../interfaces/rates-view";

@Injectable({
  providedIn: 'root'
})
export class ExchangeService {
  BACKEND_URL = "http://localhost:8080";

  constructor(private httpClient: HttpClient) {
  }

  getExchangeRates(date: string): Observable<RatesView> {
    let URL = `${this.BACKEND_URL}/exchange/rates`;
    if (date !== "") {
      URL += `?date=${date}`;
    }
    return this.httpClient.get<RatesView>(URL);
  }

  getTestData(): Observable<RatesView> {
    return this.httpClient.get<RatesView>(`${this.BACKEND_URL}/exchange/test`);
  }

  getErrorMessage(): Observable<RatesView> {
    return this.httpClient.get<RatesView>(`${this.BACKEND_URL}/exchange/error`);
  }
}
