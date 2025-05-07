export interface UploadResponse {
  success: boolean;
  message?: string;
}

export const uploadFiles = async (
  files: File[],
  recipient: string
): Promise<UploadResponse> => {
  const apiUrl = import.meta.env.VITE_API_URL;
  const formData = new FormData();

  files.forEach((file) => {
    formData.append("files", file);
  });

  formData.append("recipient", recipient);

  try {
    const response = await fetch(`${apiUrl}/upload`, {
      method: "POST",
      body: formData,
      mode: "cors",
    });

    if (!response.ok) {
      const errorMessage = await response.text();
      throw new Error(errorMessage);
    }

    return { success: true };
  } catch (error) {
    return {
      success: false,
      message: error instanceof Error ? error.message : "Upload failed",
    };
  }
};
