export interface Media {
  id: string;
  url: string;
  mediaType: string;
  uploadedAt: string;
}

export interface UploadedMedia {
  id: string;
  url: string;
  file?: File;
  mediaType?: string;
  status: 'loading' | 'uploaded' | 'failed';
}
