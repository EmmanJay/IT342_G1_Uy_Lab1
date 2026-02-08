import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'

export default function Dashboard() {
  const { user } = useAuth()

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-100 via-white to-purple-100">
      <Navbar />

      <main className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
        {/* Welcome Banner */}
        <div className="bg-indigo-600 rounded-2xl p-8 text-white mb-8">
          <h2 className="text-2xl font-bold">
            Welcome back, {user?.firstName} {user?.lastName}! 👋
          </h2>
          <p className="mt-2 text-indigo-200">
            This is your dashboard. Navigate using the menu above.
          </p>
        </div>

        {/* User Info Cards — data fetched from database */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="bg-white rounded-2xl shadow-lg p-6">
            <p className="text-xs font-medium text-gray-400 uppercase tracking-wide mb-1">User ID</p>
            <p className="text-2xl font-bold text-gray-800">{user?.userId}</p>
          </div>

          <div className="bg-white rounded-2xl shadow-lg p-6">
            <p className="text-xs font-medium text-gray-400 uppercase tracking-wide mb-1">Email</p>
            <p className="text-lg font-semibold text-gray-800">{user?.email}</p>
          </div>

          <div className="bg-white rounded-2xl shadow-lg p-6">
            <p className="text-xs font-medium text-gray-400 uppercase tracking-wide mb-1">First Name</p>
            <p className="text-lg font-semibold text-gray-800">{user?.firstName}</p>
          </div>

          <div className="bg-white rounded-2xl shadow-lg p-6">
            <p className="text-xs font-medium text-gray-400 uppercase tracking-wide mb-1">Last Name</p>
            <p className="text-lg font-semibold text-gray-800">{user?.lastName}</p>
          </div>

          <div className="bg-white rounded-2xl shadow-lg p-6 md:col-span-2">
            <p className="text-xs font-medium text-gray-400 uppercase tracking-wide mb-1">Account Created</p>
            <p className="text-lg font-semibold text-gray-800">
              {user?.createdAt ? new Date(user.createdAt).toLocaleDateString('en-US', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
              }) : '—'}
            </p>
          </div>
        </div>
      </main>
    </div>
  )
}
