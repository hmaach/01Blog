import { Component } from '@angular/core';
import { mockPosts } from '../../../../shared/lib/mock-data';
import { Post } from '../../../posts/models/post-model';
import { PostList } from '../../../posts/components/post-list/post-list';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-explore-feed',
  imports: [CommonModule, PostList],
  templateUrl: './explore-feed.html',
  styleUrl: './explore-feed.scss'
})

export class ExploreFeed {
  posts?: Post[] = mockPosts;
}
