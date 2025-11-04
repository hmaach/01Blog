export interface Media {
  id: string;
  url: string;
  mediaType: string;
  uploadedAt: string;
}

export interface UploadedMedia {
  id: string;
  url: string;
  file: File;
  status: 'loading' | 'uploaded' | 'failed';
}
