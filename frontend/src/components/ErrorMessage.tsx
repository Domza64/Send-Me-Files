function ErrorMessage({ error }: { error: string }) {
  return (
    <div className="text-rose-500 border-rose-700 bg-rose-950/60 rounded-md p-2 mb-4 border-dashed border-2">
      {error}
    </div>
  );
}

export default ErrorMessage;
