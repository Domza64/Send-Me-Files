import useSWR from "swr";
import fetcher from "../api/fetcher";
import { createContext, useContext, useState } from "react";
import UserData from "../model/UserData";

interface UserContextType {
  userData: UserData | null;
  isLoading: boolean;
  loggedIn: boolean;
  login: (token: string) => void;
  logout: () => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export function UserProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(
    sessionStorage.getItem("token"),
  );

  const {
    data: userData,
    error,
    isLoading,
    mutate,
  } = useSWR<UserData | null>(
    token ? "http://localhost:8080/api/user/data" : null,
    fetcher,
    {
      shouldRetryOnError: false,
      revalidateOnFocus: false,
    },
  );

  const loggedIn = !!userData && !error;

  const login = (newToken: string) => {
    sessionStorage.setItem("token", newToken);
    setToken(newToken);
    mutate();
  };

  const logout = () => {
    sessionStorage.removeItem("token");
    setToken(null);
    mutate(null, false);
  };

  return (
    <UserContext.Provider
      value={{ userData: userData || null, isLoading, loggedIn, login, logout }}
    >
      {children}
    </UserContext.Provider>
  );
}

export function useUserContext() {
  const context = useContext(UserContext);
  if (context === undefined) {
    throw new Error("useUserContext must be used within a UserProvider");
  }
  return context;
}
