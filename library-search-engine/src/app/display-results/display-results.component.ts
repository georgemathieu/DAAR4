import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-display-results',
  templateUrl: './display-results.component.html',
  styleUrls: ['./display-results.component.scss']
})
export class DisplayResultsComponent implements OnInit {

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

  constructor() { }

  ngOnInit(): void {
  }

}
