import { Component } from '@angular/core';
import { mockPosts } from '../../../../shared/lib/mock-data';
import { Post } from '../../../posts/models/post-model';
import { CommonModule } from '@angular/common';
import { PostCard } from '../../../posts/components/post-card/post-card';

@Component({
  selector: 'app-feed',
  imports: [CommonModule, PostCard],
  templateUrl: './feed.html',
  styleUrl: './feed.scss'
})
export class Feed {
  posts?: Post[] = mockPosts;


}
