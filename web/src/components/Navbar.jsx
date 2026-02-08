import { Link, useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Navbar() {
  const { logout } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()

  const handleLogout = async () => {
    await logout()
    navigate('/login', { replace: true })
  }

  const isActive = (path) =>
    location.pathname === path
      ? 'text-indigo-700 border-b-2 border-indigo-700'
      : 'text-gray-500 hover:text-gray-700'

  return (
    <nav className="bg-white shadow-sm">
      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo + Nav Links */}
          <div className="flex items-center space-x-8">
            <h1 className="text-xl font-bold text-indigo-700">ResearchCenter</h1>
            <div className="flex space-x-4">
              <Link
                to="/dashboard"
                className={`px-3 py-2 text-sm font-medium transition ${isActive('/dashboard')}`}
              >
                Dashboard
              </Link>
              <Link
                to="/profile"
                className={`px-3 py-2 text-sm font-medium transition ${isActive('/profile')}`}
              >
                Profile
              </Link>
            </div>
          </div>

          {/* Logout Button */}
          <button
            onClick={handleLogout}
            className="px-4 py-2 text-sm font-medium text-red-600 bg-red-50 rounded-lg hover:bg-red-100 transition"
          >
            Logout
          </button>
        </div>
      </div>
    </nav>
  )
}
