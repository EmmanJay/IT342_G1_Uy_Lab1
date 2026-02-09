import { useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Navbar() {
  const { logout } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()
  const [showLogoutModal, setShowLogoutModal] = useState(false)

  const handleLogout = async () => {
    setShowLogoutModal(false)
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
            onClick={() => setShowLogoutModal(true)}
            className="px-4 py-2 text-sm font-medium text-red-600 bg-red-50 rounded-lg hover:bg-red-100 transition"
          >
            Logout
          </button>
        </div>
      </div>

      {/* Logout Confirmation Modal */}
      {showLogoutModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm">
          <div className="bg-white rounded-2xl shadow-2xl p-8 max-w-sm w-full mx-4 text-center">
            <div className="mx-auto mb-4 flex h-14 w-14 items-center justify-center rounded-full bg-red-100">
              <svg className="h-7 w-7 text-red-600" fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 9V5.25A2.25 2.25 0 0 0 13.5 3h-6a2.25 2.25 0 0 0-2.25 2.25v13.5A2.25 2.25 0 0 0 7.5 21h6a2.25 2.25 0 0 0 2.25-2.25V15m3-3h-9m9 0-3-3m3 3-3 3" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-gray-800 mb-2">Sign Out?</h3>
            <p className="text-sm text-gray-500 mb-6">Are you sure you want to sign out of your account?</p>
            <div className="flex gap-3">
              <button
                onClick={() => setShowLogoutModal(false)}
                className="flex-1 px-4 py-2.5 border border-gray-300 text-gray-700 font-semibold rounded-xl hover:bg-gray-50 transition-colors duration-200 cursor-pointer"
              >
                Cancel
              </button>
              <button
                onClick={handleLogout}
                className="flex-1 px-4 py-2.5 bg-red-500 hover:bg-red-600 text-white font-semibold rounded-xl transition-colors duration-200 cursor-pointer"
              >
                Sign Out
              </button>
            </div>
          </div>
        </div>
      )}
    </nav>
  )
}
