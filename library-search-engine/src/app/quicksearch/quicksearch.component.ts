import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-quicksearch',
  templateUrl: './quicksearch.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['./quicksearch.component.scss']
})
export class QuicksearchComponent implements OnInit {

  p: number = 1;
  shouldIDisplayResults: Promise <boolean>;

  results = [
    {
        "title": "",
        "authors": [
            ""
        ],
        "content": "",
        "image": ""
    }
]

  constructor(private router: Router,private httpClient: HttpClient) {
    this.shouldIDisplayResults = new Promise<boolean> ((resolve, reject) => {});
   }


  ngOnInit(): void {
  }

  onSubmit(form: NgForm): void {
    this.httpClient
      .get('http://localhost:8081/basicsearch/' + form.value.search)
      .subscribe(
        (response:any) => {
          this.results = response;
          this.shouldIDisplayResults = Promise.resolve(true);
        },
        (error) => {
          console.log('Erreur ! : ' + error);
        }
      );

  }

  goToLink(url: string){
    window.open(url, "_blank");
}

}
