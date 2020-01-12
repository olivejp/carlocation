import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {LocationDto} from "../domain/location";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private readonly locationUrl: string;

  constructor(private http: HttpClient) {
    this.locationUrl = environment.locationUrl;
  }

  public generateXlsx(location: LocationDto): Observable<any> {
    return this.http.post(`${this.locationUrl}/generate`, location, {responseType: 'blob'});
  }
}
