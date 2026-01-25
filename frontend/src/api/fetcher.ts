const fetcher = async (...args: Parameters<typeof fetch>) => {
  const token = sessionStorage.getItem("token");

  const [url, config] = args;
  const headers = {
    ...(config?.headers || {}),
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
  };

  const res = await fetch(url, {
    ...config,
    headers,
  });

  if (!res.ok) {
    const errorBody = await res.json().catch(() => ({}));
    throw new Error(errorBody.message || "Fetch error");
  }

  return res.json();
};

export default fetcher;
