import { Component, OnInit } from '@angular/core';
import { AuthService } from "../auth/auth.service";
import { Http, Response, RequestOptions, ResponseContentType } from "@angular/http";
import { saveAs } from "file-saver";
import { Router } from "@angular/router";

@Component({
  selector: 'app-stories',
  templateUrl: './stories.component.html',
  styleUrls: ['./stories.component.css']
})
export class StoriesComponent implements OnInit {

  constructor(private authService: AuthService,
              private router: Router,
              private http: Http) {}

  ngOnInit() {
  }

  showReportGenerator() {
    return this.authService.isAdminAuthenticated() && this.router.url === '/stories';
  }

  onGenerateReport() {
    let options = new RequestOptions({responseType: ResponseContentType.Blob});
    this.http.get('http://localhost:8080/report/pdf', options).subscribe(
      (response: Response) => {
        var blob = response.blob();
        saveAs(blob, "applicationReport");
      },
      (error) => console.log(error)
    );
  }
}
