import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { PostList } from '../../../posts/components/post-list/post-list';
import { mockPosts } from '../../../../shared/lib/mock-data';
import { Post } from '../../../posts/models/post-model';

@Component({
  selector: 'app-profile-block',
  imports: [CommonModule, PostList],
  templateUrl: './profile-block.html',
  styleUrl: './profile-block.scss'
})
export class ProfileBlock {
  posts?: Post[] = mockPosts;
}
