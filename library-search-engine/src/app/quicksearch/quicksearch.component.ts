import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-quicksearch',
  templateUrl: './quicksearch.component.html',
  styleUrls: ['./quicksearch.component.scss']
})
export class QuicksearchComponent implements OnInit {

  shouldIDisplayResults: boolean = false;

  results = [
    {
        "id": 547,
        "title": "Project Trinity, 1945-1946",
        "authors": [
            "Rohrer, Steve",
            "Maag, Carl R."
        ],
        "content": "https://www.gutenberg.org/ebooks/548.html.images",
        "image": "https://www.gutenberg.org/cache/epub/548/pg548.cover.small.jpg"
    },
    {
        "id": 683,
        "title": "Worldwide Effects of Nuclear War: Some Perspectives",
        "authors": [
            "United States. Arms Control and Disarmament Agency"
        ],
        "content": "https://www.gutenberg.org/files/684/684-h/684-h.htm",
        "image": "https://www.gutenberg.org/cache/epub/684/pg684.cover.medium.jpg"
    }
]

  constructor(private router: Router,private httpClient: HttpClient) { }


  ngOnInit(): void {
  }

  onSubmit(form: NgForm): void {
    console.log(form.value);
    this.httpClient
      .get('http://localhost:8081/basicsearch/',form.value)
      .subscribe(
        (response:any) => {
          this.results = response;
          console.log(response);
        },
        (error) => {
          console.log('Erreur ! : ' + error);
        }
      );
    this.shouldIDisplayResults = true;

  }

}
