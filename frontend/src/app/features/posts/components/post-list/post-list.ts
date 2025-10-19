import { Component, Input } from '@angular/core';
import { PostCard } from '../post-card/post-card';
import { Post } from '../../models/post-model';

@Component({
  selector: 'app-post-list',
  imports: [PostCard],
  templateUrl: './post-list.html',
  styleUrl: './post-list.scss'
})
export class PostList {
  @Input() posts?: Post[];
}
