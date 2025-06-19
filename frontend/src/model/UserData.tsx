export default interface UserData {
  username: string;
  recievedUploads: UploadInfo[];
}

interface UploadInfo {
  uploadId: string;
  title: string;
}
