export interface Comment {
  id: string
  postId: string
  author: {
    id: string
    username: string
    displayName: string
    avatar?: string
  }
  content: string
  createdAt: string
}
