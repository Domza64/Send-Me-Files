export default interface UserData {
  username: string;
  recievedUploads: UploadInfo[];
}

export interface UploadInfo {
  uploadId: string;
  message: string;
  uploadDate: string;
  fileCount: number;
}
