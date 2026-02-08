import { createContext, useContext, useState, useEffect } from 'react'
import { loginUser as apiLogin, registerUser as apiRegister, logoutUser as apiLogout, getProfile } from '../api/axios'

const AuthContext = createContext(null)

export const useAuth = () => useContext(AuthContext)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [token, setToken] = useState(localStorage.getItem('token'))
  const [loading, setLoading] = useState(true)

  // On mount or token change, fetch user profile
  useEffect(() => {
    const fetchUser = async () => {
      if (token) {
        try {
          const res = await getProfile()
          setUser(res.data)
        } catch {
          // Token invalid – clear it
          localStorage.removeItem('token')
          setToken(null)
          setUser(null)
        }
      }
      setLoading(false)
    }
    fetchUser()
  }, [token])

  const login = async (email, password) => {
    const res = await apiLogin({ email, password })
    const jwt = res.data.token
    localStorage.setItem('token', jwt)
    setToken(jwt)
    return res.data
  }

  const register = async (firstName, lastName, email, password) => {
    const res = await apiRegister({ firstName, lastName, email, password })
    return res.data
  }

  const logout = async () => {
    try {
      await apiLogout()
    } catch {
      // even if server call fails, clear local state
    }
    localStorage.removeItem('token')
    setToken(null)
    setUser(null)
  }

  const value = {
    user,
    token,
    loading,
    isAuthenticated: !!token && !!user,
    login,
    register,
    logout,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
