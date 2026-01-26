export default interface UserData {
  username: string;
  receivedUploads: UploadInfo[];
}

export interface UploadInfo {
  uploadId: string;
  message: string;
  uploadDate: string;
  fileCount: number;
}
