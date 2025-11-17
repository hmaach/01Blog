import { Component } from '@angular/core';
import { MarkdownComponent } from 'ngx-markdown';

@Component({
  selector: 'app-about-us',
  imports: [MarkdownComponent],
  templateUrl: './about-us.html',
  styleUrl: './about-us.scss',
})
export class AboutUs {
  readmeUrl = 'https://raw.githubusercontent.com/hmaach/hmaach/main/README.md';
}
