import { createContext, useContext, useEffect, useState } from "react";
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
  const [userData, setUserData] = useState<UserData | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [loggedIn, setLoggedIn] = useState(false);

  const fetchUserData = async () => {
    setIsLoading(true);
    const token = sessionStorage.getItem("token");

    if (!token) {
      setUserData(null);
      setIsLoading(false);
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/user/userData", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      });

      if (response.ok) {
        const data = await response.json();
        setUserData({
          username: data.username,
          recievedUploads: data.receivedUploads,
        });
        setLoggedIn(true);
      } else {
        console.log(response);
        setUserData(null);
      }
    } catch (error) {
      setUserData(null);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchUserData();
  }, []);

  const login = (token: string) => {
    // TODO: use httponly cookie with CSRF tokens instead of saving JWT to sessionStorage, this is temp soluttion
    sessionStorage.setItem("token", token);
    fetchUserData();
    setLoggedIn(true);
  };

  const logout = () => {
    sessionStorage.removeItem("token");
    setUserData(null);
    setLoggedIn(false);
  };

  return (
    <UserContext.Provider
      value={{ userData, isLoading, loggedIn, login, logout }}
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
