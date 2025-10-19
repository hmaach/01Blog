// Mock data for development - will be replaced with API calls

import { Comment } from "../../features/posts/models/comment-model"
import { Post } from "../../features/posts/models/post-model"




export interface User {
  id: string
  username: string
  displayName: string
  avatar?: string
  bio?: string
  readme?: string
  joinDate: string
  role: "user" | "admin"
  stats: {
    totalPosts: number
    totalLikes: number
    subscribers: number
  }
  isSubscribed?: boolean
}

export interface Notification {
  id: string
  type: "new_post" | "comment" | "like" | "subscribe"
  message: string
  relatedUser?: {
    username: string
    displayName: string
    avatar?: string
  }
  relatedPost?: {
    id: string
    title: string
  }
  timestamp: string
  isRead: boolean
}

export interface Report {
  id: string
  reportedUser: {
    id: string
    username: string
    displayName: string
  }
  reporter: {
    id: string
    username: string
    displayName: string
  }
  reason: string
  category: "spam" | "offensive" | "inappropriate" | "other"
  relatedPost?: {
    id: string
    title: string
  }
  status: "waiting" | "approved" | "declined"
  createdAt: string
}

// Mock current user
export const mockCurrentUser: User = {
  id: "1",
  username: "johndoe",
  displayName: "John Doe",
  avatar: "https://avatar.iran.liara.run/public",
  bio: "Full-stack developer learning in public. Passionate about React, TypeScript, and building great UX.",
  readme:
    "# About Me\n\nHi! I'm John, a computer science student documenting my learning journey.\n\n## Current Focus\n- React & Next.js\n- TypeScript\n- System Design\n\n## Goals for 2025\n- Build 12 projects\n- Contribute to open source\n- Master data structures",
  joinDate: "2024-01-15",
  role: "admin",
  stats: {
    totalPosts: 24,
    totalLikes: 156,
    subscribers: 42,
  },
}

// Mock posts
export const mockPosts: Post[] = [
  {
    id: "1",
    title: "Building a Real-Time Chat App with WebSockets",
    body: "Today I learned how to implement WebSockets in a Next.js application. The journey was challenging but incredibly rewarding...",
    author: {
      id: "1",
      username: "johndoe",
      displayName: "John Doe",
    },
    media: [
      {
        type: "image",
        url: "https://picsum.photos/400/240?random=7",
        thumbnail: "https://via.placeholder.com/150x100?text=WebSocket",
      },
    ],
    likesCount: 42,
    commentsCount: 8,
    impressionsCount: 234,
    createdAt: "2025-10-15T10:30:00Z",
  },
  {
    id: "2",
    title: "Understanding React Server Components",
    body: "React Server Components are a game-changer for building modern web applications. Here's what I learned React Server Components are a game-changer for building modern web applications. Here's what I learned React Server Components are a game-changer for building modern web applications. Here's what I learned React Server Components are a game-changer for building modern web applications. Here's what I learned React Server Components are a game-changer for building modern web applications. Here's what I learned React Server Components are a game-changer for building modern web applications. Here's what I learned React Server Components are a game-changer for building modern web applications. Here's what I learned React Server Components are a game-changer for building modern web applications. Here's what I learned     React Server Components are a game-changer for building modern web applications. Here's what I learned React Server Components are a game-changer for building modern web applications. Here's what I learnedReact Server Components are a game-changer for building modern web applications. Here's what I learned",
    author: {
      id: "2",
      username: "sarahchen",
      displayName: "Sarah Chen",
    },
    media: [],
    likesCount: 67,
    commentsCount: 12,
    impressionsCount: 456,
    createdAt: "2025-10-14T14:20:00Z",
  },
  {
    id: "3",
    title: "My Journey Learning TypeScript",
    body: "After 6 months of using TypeScript, here are my thoughts and lessons learned...",
    author: {
      id: "3",
      username: "mikejohnson",
      displayName: "Mike Johnson",
      avatar: "https://avatar.iran.liara.run/public",
    },
    media: [
      {
        type: "image",
        url: "https://picsum.photos/400/240?random=5",
        thumbnail: "https://via.placeholder.com/150x100?text=TS+Code",
      },
    ],
    likesCount: 89,
    commentsCount: 15,
    impressionsCount: 678,
    createdAt: "2025-10-13T09:15:00Z",
  },
  {
    id: "4",
    title: "Building My First Chrome Extension",
    body: "I built a productivity Chrome extension that helps track coding time. Here's the complete process...",
    author: {
      id: "4",
      username: "emilypark",
      displayName: "Emily Park",
      avatar: "https://avatar.iran.liara.run/public",
    },
    likesCount: 54,
    commentsCount: 9,
    impressionsCount: 345,
    createdAt: "2025-10-12T16:45:00Z",
  },
  {
    id: "5",
    title: "Mastering CSS Grid Layout",
    body: "CSS Grid has completely changed how I approach layouts. Here are my favorite patterns and tips...",
    author: {
      id: "5",
      username: "alexrivera",
      displayName: "Alex Rivera",
      avatar: "https://avatar.iran.liara.run/public",
    },
    media: [
      {
        type: "image",
        url: "https://picsum.photos/400/240?random=3",
        thumbnail: "https://via.placeholder.com/150x100?text=CSS+Grid",
      },
    ],
    likesCount: 73,
    commentsCount: 11,
    impressionsCount: 521,
    createdAt: "2025-10-11T11:30:00Z",
  },
  {
    id: "6",
    title: "Deploying to Vercel: A Complete Guide",
    body: "Vercel makes deployment incredibly easy. Here's everything you need to know to get started Vercel makes deployment incredibly easy. Here's everything you need to know to get started Vercel makes deployment incredibly easy. Here's everything you need to know to get started Vercel makes deployment incredibly easy. Here's everything you need to know to get started Vercel makes deployment incredibly easy. Here's everything you need to know to get started Vercel makes deployment incredibly easy. Here's everything you need to know to get started",
    author: {
      id: "1",
      username: "johndoe",
      displayName: "John Doe",
      avatar: "https://avatar.iran.liara.run/public",
    },
    likesCount: 38,
    commentsCount: 6,
    impressionsCount: 289,
    createdAt: "2025-10-10T13:20:00Z",
  },
];


// Mock comments
export const mockComments: Comment[] = [
  {
    id: "1",
    postId: "1",
    author: {
      id: "2",
      username: "sarahchen",
      displayName: "Sarah Chen",
    },
    content: "Great explanation! I've been wanting to learn WebSockets. Do you have any resources you'd recommend?",
    createdAt: "2025-10-15T11:00:00Z",
  },
  {
    id: "2",
    postId: "1",
    author: {
      id: "3",
      username: "mikejohnson",
      displayName: "Mike Johnson",
      avatar: "/male-programmer.jpg",
    },
    content: "This is exactly what I needed! Thanks for sharing your experience.",
    createdAt: "2025-10-15T12:30:00Z",
  },
]

// Mock notifications
export const mockNotifications: Notification[] = [
  {
    id: "1",
    type: "comment",
    message: "commented on your post",
    relatedUser: {
      username: "sarahchen",
      displayName: "Sarah Chen",
    },
    relatedPost: {
      id: "1",
      title: "Building a Real-Time Chat App with WebSockets",
    },
    timestamp: "2025-10-15T11:00:00Z",
    isRead: false,
  },
  {
    id: "2",
    type: "like",
    message: "liked your post",
    relatedUser: {
      username: "mikejohnson",
      displayName: "Mike Johnson",
      avatar: "/male-programmer.jpg",
    },
    relatedPost: {
      id: "1",
      title: "Building a Real-Time Chat App with WebSockets",
    },
    timestamp: "2025-10-15T10:45:00Z",
    isRead: false,
  },
  {
    id: "3",
    type: "subscribe",
    message: "subscribed to your blog",
    relatedUser: {
      username: "emilypark",
      displayName: "Emily Park",
      avatar: "/asian-developer.jpg",
    },
    timestamp: "2025-10-14T16:20:00Z",
    isRead: true,
  },
]

// Mock reports for admin
export const mockReports: Report[] = [
  {
    id: "1",
    reportedUser: {
      id: "6",
      username: "spammer123",
      displayName: "Spam Account",
    },
    reporter: {
      id: "2",
      username: "sarahchen",
      displayName: "Sarah Chen",
    },
    reason: "This user is posting spam links in multiple posts",
    category: "spam",
    relatedPost: {
      id: "7",
      title: "Check out this amazing deal!!!",
    },
    status: "waiting",
    createdAt: "2025-10-15T09:30:00Z",
  },
  {
    id: "2",
    reportedUser: {
      id: "7",
      username: "rudeuser",
      displayName: "Rude User",
    },
    reporter: {
      id: "3",
      username: "mikejohnson",
      displayName: "Mike Johnson",
    },
    reason: "Offensive language and harassment in comments",
    category: "offensive",
    status: "approved",
    createdAt: "2025-10-14T14:15:00Z",
  },
]

// Mock users for admin panel
export const mockUsers: User[] = [
  mockCurrentUser,
  {
    id: "2",
    username: "sarahchen",
    displayName: "Sarah Chen",
    avatar: "/female-developer.png",
    bio: "Frontend developer passionate about React and design systems",
    joinDate: "2024-02-20",
    role: "user",
    stats: {
      totalPosts: 18,
      totalLikes: 234,
      subscribers: 67,
    },
  },
  {
    id: "3",
    username: "mikejohnson",
    displayName: "Mike Johnson",
    avatar: "/male-programmer.jpg",
    bio: "Backend engineer learning cloud architecture",
    joinDate: "2024-03-10",
    role: "user",
    stats: {
      totalPosts: 12,
      totalLikes: 145,
      subscribers: 34,
    },
  },
]
