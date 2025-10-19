export interface Post {
  id: string
  title: string
  body: string
  likesCount: number
  commentsCount: number
  impressionsCount: number
  author: {
    id: string
    username: string
    displayName: string
    avatar?: string
  }
  media?: {
    type: "image" | "video"
    url: string
    thumbnail?: string
  }[]
  createdAt: string
  isHidden?: boolean
}
