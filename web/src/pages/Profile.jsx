import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'

export default function Profile() {
  const { user } = useAuth()

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-100 via-white to-purple-100">
      <Navbar />

      <main className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
        {/* Profile Card */}
        <div className="bg-white rounded-2xl shadow-lg overflow-hidden">
          <div className="px-8 py-6 border-b border-gray-100">
            <h3 className="text-lg font-semibold text-gray-800">Profile Information</h3>
            <p className="text-sm text-gray-500">Your personal details and account info.</p>
          </div>

          <div className="p-8">
            {/* Avatar + Name */}
            <div className="flex items-center mb-8">
              <div className="h-20 w-20 rounded-full bg-indigo-100 flex items-center justify-center text-indigo-600 text-2xl font-bold">
                {user?.firstName?.charAt(0)}{user?.lastName?.charAt(0)}
              </div>
              <div className="ml-6">
                <h4 className="text-xl font-semibold text-gray-800">
                  {user?.firstName} {user?.lastName}
                </h4>
                <p className="text-gray-500">{user?.email}</p>
              </div>
            </div>

            {/* Detail Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="bg-gray-50 rounded-xl p-5">
                <p className="text-xs font-medium text-gray-400 uppercase tracking-wide">User ID</p>
                <p className="mt-1 text-lg font-semibold text-gray-800">{user?.userId}</p>
              </div>
              <div className="bg-gray-50 rounded-xl p-5">
                <p className="text-xs font-medium text-gray-400 uppercase tracking-wide">Email</p>
                <p className="mt-1 text-lg font-semibold text-gray-800">{user?.email}</p>
              </div>
              <div className="bg-gray-50 rounded-xl p-5">
                <p className="text-xs font-medium text-gray-400 uppercase tracking-wide">First Name</p>
                <p className="mt-1 text-lg font-semibold text-gray-800">{user?.firstName}</p>
              </div>
              <div className="bg-gray-50 rounded-xl p-5">
                <p className="text-xs font-medium text-gray-400 uppercase tracking-wide">Last Name</p>
                <p className="mt-1 text-lg font-semibold text-gray-800">{user?.lastName}</p>
              </div>
              <div className="bg-gray-50 rounded-xl p-5 md:col-span-2">
                <p className="text-xs font-medium text-gray-400 uppercase tracking-wide">Account Created</p>
                <p className="mt-1 text-lg font-semibold text-gray-800">
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
          </div>
        </div>
      </main>
    </div>
  )
}
