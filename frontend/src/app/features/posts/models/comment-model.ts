export interface Comment {
  id: string
  postId: string
  author: {
    id: string
    username: string
    name: string
    avatar?: string
  }
  content: string
  createdAt: string
}
