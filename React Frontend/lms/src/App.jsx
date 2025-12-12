import React, { useState, useEffect, useRef } from 'react';
import { BookOpen, Users, ArrowLeft, Plus, Trash2, Eye, LogIn, BookHeadphones } from 'lucide-react';
import './App.css';
// API Base URL - Update this with your actual backend URL
const API_BASE_URL = 'http://localhost:8080';

// Small self-contained Add Book form. Keeps its own local state to avoid focus loss
const AddBookForm = ({ onAdd, loading, setError }) => {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [quantity, setQuantity] = useState('');

  const handleSubmit = async () => {
    if (!title || !author || !quantity) {
      setError && setError('All fields are required');
      return;
    }
    setError && setError('');
    await onAdd({ title, author, quantity });
    setTitle('');
    setAuthor('');
    setQuantity('');
  };

  return (
    <div>
      <h3 className="text-2xl font-bold mb-6">Add New Book</h3>
      <div className="space-y-4">
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Book Title"
          className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="text"
          value={author}
          onChange={(e) => setAuthor(e.target.value)}
          placeholder="Author Name"
          className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="number"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
          placeholder="Quantity"
          min="1"
          className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <button onClick={handleSubmit} disabled={loading} className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-lg transition duration-200 disabled:opacity-50">
          {loading ? 'Adding...' : 'Add Book'}
        </button>
      </div>
    </div>
  );
};


// Small self-contained Add Member form. Keeps its own local state to avoid focus loss
const AddMemberForm = ({ onAdd, loading, setError }) => {
  const [name, setName] = useState('');
  const [contact, setContact] = useState('');

  const handleSubmit = async () => {
    if (!name || !contact) {
      setError && setError('All fields are required');
      return;
    }
    setError && setError('');
    await onAdd({ name, contact });
    setName('');
    setContact('');
  };

  return (
    <div>
      <h3 className="text-2xl font-bold mb-6">Add New Member</h3>
      <div className="space-y-4">
        <input
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Member Name"
          className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
        />
        <input
          type="text"
          value={contact}
          onChange={(e) => setContact(e.target.value)}
          placeholder="Contact Number"
          className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
        />
        <button onClick={handleSubmit} disabled={loading} className="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-3 rounded-lg transition duration-200 disabled:opacity-50">
          {loading ? 'Adding...' : 'Add Member'}
        </button>
      </div>
    </div>
  );
};


const App = () => {
  const [view, setView] = useState('home'); // home, staff, member
  const [memberId, setMemberId] = useState('');
  const [currentMember, setCurrentMember] = useState(null);
  const [books, setBooks] = useState([]);
  const [members, setMembers] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(false);
  // Transactions-specific loading/guard to avoid global loading toggles causing UI flicker
  const [isLoadingTransactions, setIsLoadingTransactions] = useState(false);
  const isFetchingTransactionsRef = useRef(false);
  // Tracks whether we've already attempted an initial fetch for transactions
  const transactionsFetchedRef = useRef(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  // hoisted UI state for nested views (avoid reset when App re-renders)
  const [staffAction, setStaffAction] = useState(null);
  const [memberAction, setMemberAction] = useState(null);

  // Book form state
  const [bookForm, setBookForm] = useState({ title: '', author: '', quantity: '' });
  
  // Member form state
  const [memberForm, setMemberForm] = useState({ name: '', contact: '' });

  // Fetch functions
  const isFetchingRef = useRef(false); // guard to avoid overlapping fetches

  const fetchBooks = async () => {
    if (isFetchingRef.current) return; // already fetching
    isFetchingRef.current = true;
    setLoading(true);
    setError('');
    try {
      const response = await fetch(`${API_BASE_URL}/books`);
      const status = await response.json();
      if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`||'Failed to fetch books');
      setBooks(status.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
      isFetchingRef.current = false;
    }
  };

  const fetchMembers = async () => {
    if (isFetchingRef.current) return;
    isFetchingRef.current = true;
    setLoading(true);
    setError('');
    try {
      const response = await fetch(`${API_BASE_URL}/members`);
      const status = await response.json();
      if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`||'Failed to fetch members');
      setMembers(status.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
      isFetchingRef.current = false;
    }
  };

  const fetchTransactions = async (force = false) => {
    // If we've already fetched once and caller didn't force, skip to avoid loops
    if (!force && transactionsFetchedRef.current) return;
    if (isFetchingTransactionsRef.current) return;
    isFetchingTransactionsRef.current = true;
    setIsLoadingTransactions(true);
    setError('');
    try {
      const response = await fetch(`${API_BASE_URL}/transactions`);
      const status = await response.json();
      if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`||'Failed to fetch transactions');
    
      setTransactions(status.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoadingTransactions(false);
      isFetchingTransactionsRef.current = false;
      transactionsFetchedRef.current = true;
    }
  };

  const verifyMember = async (id) => {
    setLoading(true);
    setError('');
    try {
      const response = await fetch(`${API_BASE_URL}/members/${id}`);
      const status = await response.json();

      if (!response.ok) {
        throw new Error(`ERROR ${status.code}:  ${status.message}`||'Member not found');
      }
      
      setCurrentMember(status.data);
      setMemberId(id); // Update the App-level state
      setView('member');
    } catch (err) {
      setError('Member not found. Please try again.');
      setMemberId('');
    } finally {
      setLoading(false);
    }
  };

  // allow optional payload so external/local forms can call this without relying on App-level bookForm
  const addBook = async (payload) => {
    const data = payload || bookForm;
    if (!data.title || !data.author || !data.quantity) {
      setError('All fields are required');
      return;
    }
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await fetch(`${API_BASE_URL}/books`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
      });
      const status = await response.json();
      if (!response.ok) {
        throw new Error(`ERROR ${status.code}:  ${status.message}`|| 'Failed to add book');
      }

      // clear App-level form only if we used it
      if (!payload) setBookForm({ title: '', author: '', quantity: '' });
      setSuccess(`SUCCESS ${status.code}:  ${status.message}`||'Book added successfully!');
      setTimeout(() => setSuccess(''), 5000); // Clear after 5 seconds
      fetchBooks();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const removeBook = async (id) => {
    if (!window.confirm('Are you sure you want to remove this book?')) return;
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await fetch(`${API_BASE_URL}/books/${id}`, {
        method: 'DELETE'
      });
      const status = await response.json();
      if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`|| 'Failed to remove book');
      setSuccess(`SUCCESS ${status.code}:  ${status.message}`||'Book removed successfully!');
      setTimeout(() => setSuccess(''), 5000); // Clear after 5 seconds
      fetchBooks();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };
  const removeOneBook = async (id) => {
    if (!window.confirm('Are you sure you want to remove this book?')) return;
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await fetch(`${API_BASE_URL}/books/${id}/one`, {
        method: 'PUT'
      });
      const status = await response.json();
      if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`|| 'Failed to remove book');
      setSuccess(`SUCCESS ${status.code}:  ${status.message}`||'Book removed successfully!');
        
      setTimeout(() => setSuccess(''), 5000); // Clear after 5 seconds
      fetchBooks();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // accept optional payload so local AddMemberForm can call without relying on App-level state
  const addMember = async (payload) => {
    const data = payload || memberForm;
    if (!data.name || !data.contact) {
      setError('All fields are required');
      return;
    }
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await fetch(`${API_BASE_URL}/members`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
      });
      const status = await response.json();
      if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`||'Failed to add member');
      if (!payload) setMemberForm({ name: '', contact: '' });
      setSuccess(`SUCCESS ${status.code}:  ${status.message}`||'Member added successfully!');
      setTimeout(() => setSuccess(''), 5000); // Clear after 5 seconds
      fetchMembers();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const removeMember = async (id) => {
    if (!window.confirm('Are you sure you want to remove this member?')) return;
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await fetch(`${API_BASE_URL}/members/${id}`, {
        method: 'DELETE'
      });
      const status = await response.json();
      if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`||'Failed to remove member');
      setSuccess(`SUCCESS ${status.code}:  ${status.message}`||'Member removed successfully!');
      setTimeout(() => setSuccess(''), 5000); // Clear after 5 seconds
      fetchMembers();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const issueBook = async (bookId) => {
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await fetch(`${API_BASE_URL}/transactions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          transactionType: 'ISSUE',
          memberId: currentMember.id,
          bookId: bookId
        })
      });
      const status = await response.json();
      if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`||'Failed to issue book');
      setSuccess(`SUCCESS ${status.code}:  ${status.message}`||'Book issued successfully!');
      setTimeout(() => setSuccess(''), 5000); // Clear after 5 seconds
      // Refresh books and transactions so the UI updates immediately
      await fetchBooks();
      await fetchTransactions(true);
      // Refresh current member data
      
      const memberResponse = await fetch(`${API_BASE_URL}/members/${currentMember.id}`);
      const memberData = await memberResponse.json();
      if (!memberResponse.ok) throw new Error(`ERROR ${memberData.code}:  ${memberData.message}`||`Can't refresh current member`);
      setCurrentMember(memberData.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const returnBook = async (bookId) => {
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const response = await fetch(`${API_BASE_URL}/transactions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          transactionType: 'RETURN',
          memberId: currentMember.id,
          bookId: bookId
        })
      });
      const status = await response.json();
      if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`||'Failed to return book');
      setSuccess(`SUCCESS ${status.code}:  ${status.message}`||'Book returned successfully!');
      setTimeout(() => setSuccess(''), 5000); // Clear after 5 seconds
      // Refresh books and transactions so the UI updates immediately
      await fetchBooks();
      await fetchTransactions(true);
      // Refresh current member data
      const memberResponse = await fetch(`${API_BASE_URL}/members/${currentMember.id}`);
      const memberData = await memberResponse.json();
      if (!memberResponse.ok) throw new Error(`ERROR ${memberData.code}:  ${memberData.message}`||`Can't refresh current member`);
      setCurrentMember(memberData.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // Home View
  const HomeView = () => (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl shadow-2xl p-8 max-w-md w-full">
        <div className="text-center mb-8">
          <BookOpen className="w-16 h-16 text-indigo-600 mx-auto mb-4" />
          <h1 className="text-3xl font-bold text-gray-800 mb-2">Library Management System</h1>
          <p className="text-gray-600">Select your role to continue</p>
        </div>
        <div className="space-y-4">
          <button
            onClick={() => setView('staff')}
            className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-4 rounded-lg transition duration-200 flex items-center justify-center gap-2"
          >
            <Users className="w-5 h-5" />
            STAFF
          </button>
          <button
            onClick={() => setView('memberLogin')}
            className="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-4 rounded-lg transition duration-200 flex items-center justify-center gap-2"
          >
            <LogIn className="w-5 h-5" />
            MEMBER
          </button>
        </div>
      </div>
    </div>
  );

  // Member Login Form Component - keeps its own state to avoid focus issues
  const MemberLoginForm = ({ onLogin, loading, setError, error }) => {
    const [localMemberId, setLocalMemberId] = useState('');

    const handleSubmit = (e) => {
      e.preventDefault();
      if (!localMemberId.trim()) {
        setError('Please enter a member ID');
        return;
      }
      onLogin(localMemberId);
    };

    return (
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          type="text"
          value={localMemberId}
          onChange={(e) => setLocalMemberId(e.target.value)}
          placeholder="Enter Member ID"
          className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
        />
        <button
          type="submit"
          disabled={loading}
          className="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-3 rounded-lg transition duration-200 disabled:opacity-50"
        >
          {loading ? 'Verifying...' : 'Login'}
        </button>
      </form>
    );
  };

  // Member Login View
  const MemberLoginView = () => (
    <div className="min-h-screen bg-gradient-to-br from-green-50 to-emerald-100 flex items-center justify-center p-4">
      <div className="bg-white rounded-2xl shadow-2xl p-8 max-w-md w-full">
        <button
          onClick={() => { setView('home'); setError(''); setMemberId(''); }}
          className="mb-6 text-gray-600 hover:text-gray-800 flex items-center gap-2"
        >
          <ArrowLeft className="w-4 h-4" />
          Back to Home
        </button>
        <div className="text-center mb-8">
          <LogIn className="w-16 h-16 text-green-600 mx-auto mb-4" />
          <h2 className="text-2xl font-bold text-gray-800 mb-2">Member Login</h2>
          <p className="text-gray-600">Enter your member ID to continue</p>
        </div>
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
            {error}
          </div>
        )}
        <MemberLoginForm 
          onLogin={verifyMember} 
          loading={loading} 
          setError={setError}
          error={error}
        />
      </div>
    </div>
  );

  // Staff View
  const StaffView = () => {
    const [memberBooksMap, setMemberBooksMap] = useState({});
    const [isLoadingMemberBooks, setIsLoadingMemberBooks] = useState(false);
    const isFetchingMemberBooksRef = useRef(false);

    const fetchMemberBooks = async (memberId) => {
      try {
        const response = await fetch(`${API_BASE_URL}/members/${memberId}/books`);
        const status = await response.json();
        if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`||'Failed to fetch member books');
        return status.data;
      } catch (err) {
        console.error(`Error fetching books for member ${memberId}:`, err);
        return [];
      }
    };

    const loadAllMemberBooks = async () => {
      if (isFetchingMemberBooksRef.current) return;
      isFetchingMemberBooksRef.current = true;
      setIsLoadingMemberBooks(true);
      try {
        const booksMap = {};
        for (const member of members) {
          const data = await fetchMemberBooks(member.id);
          booksMap[member.id] = data
          
        }
        setMemberBooksMap(booksMap);
      } finally {
        setIsLoadingMemberBooks(false);
        isFetchingMemberBooksRef.current = false;
      }
    };

    useEffect(() => {
      // Only fetch when entering a view and data isn't already loaded.
      // Also fetch when opening the Remove Book view since it needs the books list.
      if ((staffAction === 'viewBooks' || staffAction === 'removeBook') && books.length === 0) fetchBooks();
      if ((staffAction === 'viewMembers' || staffAction === 'removeMember') && members.length === 0) fetchMembers();
      if (staffAction === 'viewTransactions' && transactions.length === 0) fetchTransactions();
      
      // Load member books when viewing members or remove member view
      if ((staffAction === 'viewMembers' || staffAction === 'removeMember') && members.length > 0) {
        loadAllMemberBooks();
      }
    }, [staffAction, members.length]);

    if (!staffAction) {
      return (
        <div className="min-h-screen bg-gradient-to-br from-indigo-50 to-purple-100 p-4">
          <div className="max-w-4xl mx-auto">
            <button
              onClick={() => setView('home')}
              className="mb-6 text-gray-600 hover:text-gray-800 flex items-center gap-2"
            >
              <ArrowLeft className="w-4 h-4" />
              Back to Home
            </button>
            <div className="bg-white rounded-2xl shadow-2xl p-8">
              <h2 className="text-3xl font-bold text-gray-800 mb-8 text-center">Staff Dashboard</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <button onClick={() => setStaffAction('addBook')} className="bg-blue-500 hover:bg-blue-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <Plus className="w-5 h-5" /> ADD BOOK
                </button>
                <button onClick={() => setStaffAction('removeBook')} className="bg-red-500 hover:bg-red-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <Trash2 className="w-5 h-5" /> REMOVE BOOK
                </button>
                <button onClick={() => setStaffAction('addMember')} className="bg-green-500 hover:bg-green-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <Plus className="w-5 h-5" /> ADD MEMBER
                </button>
                <button onClick={() => setStaffAction('removeMember')} className="bg-orange-500 hover:bg-orange-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <Trash2 className="w-5 h-5" /> REMOVE MEMBER
                </button>
                <button onClick={() => setStaffAction('viewBooks')} className="bg-purple-500 hover:bg-purple-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <Eye className="w-5 h-5" /> VIEW ALL BOOKS
                </button>
                <button onClick={() => setStaffAction('viewMembers')} className="bg-teal-500 hover:bg-teal-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <Eye className="w-5 h-5" /> VIEW ALL MEMBERS
                </button>
                <button onClick={() => setStaffAction('viewTransactions')} className="bg-indigo-500 hover:bg-indigo-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2 md:col-span-2">
                  <Eye className="w-5 h-5" /> VIEW ALL TRANSACTIONS
                </button>
              </div>
            </div>
          </div>
        </div>
      );
    }

    return (
      <div className="min-h-screen bg-gradient-to-br from-indigo-50 to-purple-100 p-4">
        <div className="max-w-6xl mx-auto">
          <button
            onClick={() => { setStaffAction(null); setError(''); setSuccess(''); }}
            className="mb-6 text-gray-600 hover:text-gray-800 flex items-center gap-2"
          >
            <ArrowLeft className="w-4 h-4" />
            Back to Staff Dashboard
          </button>
          <div className="bg-white rounded-2xl shadow-2xl p-8">
            {error && (
              <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                {error}
              </div>
            )}
            {success && (
              <div className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
                {success}
              </div>
            )}

            {staffAction === 'addBook' && (
              <AddBookForm onAdd={addBook} loading={loading} setError={setError} />
            )}

            {staffAction === 'removeBook' && (
              <div>
                <h3 className="text-2xl font-bold mb-6">Remove Book</h3>
                <button onClick={fetchBooks} className="mb-4 bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600">
                  Refresh Books
                </button>
                {loading ? (
                  <p>Loading...</p>
                ) : books.length === 0 ? (
                  <p className="text-gray-600">No books found</p>
                ) : (
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="bg-gray-100">
                          <th className="border p-3 text-left">ID</th>
                          <th className="border p-3 text-left">Title</th>
                          <th className="border p-3 text-left">Author</th>
                          <th className="border p-3 text-left">Quantity</th>
                          <th className="border p-3 text-left">Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        {books.map(book => (
                          <tr key={book.id} className="hover:bg-gray-50">
                            <td className="border p-3">{book.id}</td>
                            <td className="border p-3">{book.title}</td>
                            <td className="border p-3">{book.author}</td>
                            <td className="border p-3">{book.quantity}</td>
                            <td className="border p-3">
                              <button
                                onClick={() => removeBook(book.id)}
                                className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded"
                              >
                                Remove
                              </button>
                              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;
                              <button
                                onClick={() => removeOneBook(book.id)}
                                disabled={book.quantity === 0}
                                className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded"
                              >
                                Remove 1
                              </button>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            )}

            {staffAction === 'addMember' && (
              <AddMemberForm onAdd={addMember} loading={loading} setError={setError} />
            )}

            {staffAction === 'removeMember' && (
              <div>
                <h3 className="text-2xl font-bold mb-6">Remove Member</h3>
                <button 
                  onClick={() => {
                    fetchMembers();
                    loadAllMemberBooks();
                  }}
                  disabled={isLoadingMemberBooks}
                  className="mb-4 bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 disabled:opacity-50"
                >
                  {isLoadingMemberBooks ? 'Refreshing...' : 'Refresh Members'}
                </button>
                {loading ? (
                  <p>Loading...</p>
                ) : members.length === 0 ? (
                  <p className="text-gray-600">No members found</p>
                ) : (
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="bg-gray-100">
                          <th className="border p-3 text-left">ID</th>
                          <th className="border p-3 text-left">Name</th>
                          <th className="border p-3 text-left">Contact</th>
                          <th className="border p-3 text-left">Books Owned</th>
                          
                          <th className="border p-3 text-left">Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        {members.map(member => (
                          <tr key={member.id} className="hover:bg-gray-50">
                            <td className="border p-3">{member.id}</td>
                            <td className="border p-3">{member.name}</td>
                            <td className="border p-3">{member.contact}</td>
                            <td className="border p-3">
                              <div className="space-y-1">
                                <div>{memberBooksMap[member.id]?.length || 0} books</div>
                                {memberBooksMap[member.id]?.map((item, idx) => (
                                  <div key={idx} className="text-sm text-gray-600">
                                    {item.book.title} ({item.quantity})
                                  </div>
                                ))}
                              </div>
                            </td>
                            
                            <td className="border p-3">
                              <button
                                onClick={() => removeMember(member.id)}
                                className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded"
                              >
                                Remove
                              </button>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            )}

            {staffAction === 'viewBooks' && (
              <div>
                <h3 className="text-2xl font-bold mb-6">All Books</h3>
                <button onClick={fetchBooks} className="mb-4 bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600">
                  Refresh
                </button>
                {loading ? (
                  <p>Loading...</p>
                ) : books.length === 0 ? (
                  <p className="text-gray-600">No books found</p>
                ) : (
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="bg-gray-100">
                          <th className="border p-3 text-left">ID</th>
                          <th className="border p-3 text-left">Title</th>
                          <th className="border p-3 text-left">Author</th>
                          <th className="border p-3 text-left">Quantity</th>
                        </tr>
                      </thead>
                      <tbody>
                        {books.map(book => (
                          <tr key={book.id} className="hover:bg-gray-50">
                            <td className="border p-3">{book.id}</td>
                            <td className="border p-3">{book.title}</td>
                            <td className="border p-3">{book.author}</td>
                            <td className="border p-3">{book.quantity}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            )}

            {staffAction === 'viewMembers' && (
              <div>
                <h3 className="text-2xl font-bold mb-6">All Members</h3>
                <button 
                  onClick={() => {
                    fetchMembers();
                    loadAllMemberBooks();
                  }} 
                  disabled={isLoadingMemberBooks}
                  className="mb-4 bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 disabled:opacity-50"
                >
                  {isLoadingMemberBooks ? 'Refreshing...' : 'Refresh'}
                </button>
                {loading ? (
                  <p>Loading...</p>
                ) : members.length === 0 ? (
                  <p className="text-gray-600">No members found</p>
                ) : (
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="bg-gray-100">
                          <th className="border p-3 text-left">ID</th>
                          <th className="border p-3 text-left">Name</th>
                          <th className="border p-3 text-left">Contact</th>
                          <th className="border p-3 text-left">Books Owned</th>
                        </tr>
                      </thead>
                      <tbody>
                        {members.map(member => (
                          <tr key={member.id} className="hover:bg-gray-50">
                            <td className="border p-3">{member.id}</td>
                            <td className="border p-3">{member.name}</td>
                            <td className="border p-3">{member.contact}</td>
                            <td className="border p-3">
                              <div className="space-y-1">
                                <div>{memberBooksMap[member.id]?.length || 0} books</div>
                                {memberBooksMap[member.id]?.map((item, idx) => (
                                  <div key={idx} className="text-sm text-gray-600">
                                    {item.book.title} ({item.quantity})
                                  </div>
                                ))}
                              </div>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            )}

            {staffAction === 'viewTransactions' && (
              <div>
                <h3 className="text-2xl font-bold mb-6">All Transactions</h3>
                <button
                  onClick={() => fetchTransactions(true)}
                  disabled={isLoadingTransactions}
                  className="mb-4 bg-indigo-500 text-white px-4 py-2 rounded-lg hover:bg-indigo-600 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {isLoadingTransactions ? 'Refreshing...' : 'Refresh'}
                </button>
                {isLoadingTransactions ? (
                  <p>Loading...</p>
                ) : transactions.length === 0 ? (
                  <p className="text-gray-600">No transactions found</p>
                ) : (
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="bg-gray-100">
                          <th className="border p-3 text-left">Transaction ID</th>
                          <th className="border p-3 text-left">Type</th>
                          <th className="border p-3 text-left">Date</th>
                          <th className="border p-3 text-left">Member ID</th>
                          <th className="border p-3 text-left">Book ID</th>
                        </tr>
                      </thead>
                      <tbody>
                        {[...transactions]
                          .sort((a, b) => {
                            // Sort only by the numeric portion of the transactionId (4th to 17th characters)
                            const idSliceA = (a.transactionId || '').substring(3, 17);
                            const idSliceB = (b.transactionId || '').substring(3, 17);
                            const numA = parseInt(idSliceA, 10) || 0;
                            const numB = parseInt(idSliceB, 10) || 0;
                            return numB - numA; // descending
                          })
                          .map(transaction => (
                          <tr key={transaction.transactionId} className="hover:bg-gray-50">
                            <td className="border p-3">{transaction.transactionId}</td>
                            <td className="border p-3">
                              <span className={`px-2 py-1 rounded ${transaction.transactionType === 'ISSUE' ? 'bg-blue-100 text-blue-800' : 'bg-green-100 text-green-800'}`}>
                                {transaction.transactionType}
                              </span>
                            </td>
                            <td className="border p-3">{transaction.transactionDate.toLocaleString()}</td>
                            <td className="border p-3">{transaction.memberId}</td>
                            <td className="border p-3">{transaction.bookId}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            )}
          </div>
        </div>
      </div>
    );
  };

  // Member View
  const MemberView = () => {
    const [memberBooks, setMemberBooks] = useState([]);
    const [isLoadingMemberBooks, setIsLoadingMemberBooks] = useState(false);
    const isFetchingMemberBooksRef = useRef(false);
    const memberBooksFetchedRef = useRef(false);

    useEffect(() => {
      // Avoid redundant fetches: only fetch if data is not already loaded.
      // Also fetch member-specific books when opening either 'viewMyBooks' or 'returnBook'
      // so the user doesn't have to manually refresh.
      // Additionally fetch when opening 'issueBook' because the Issue view lists available books.
      if ((memberAction === 'viewAllBooks' || memberAction === 'issueBook') && books.length === 0) fetchBooks();

      // When the member dashboard (welcome screen) is shown (memberAction === null)
      // we also want to fetch the member's books so the "Books Currently Owned"
      // counter is accurate. Only fetch if we have a currentMember and haven't already attempted a fetch.
      if ((memberAction === null || memberAction === 'viewMyBooks' || memberAction === 'returnBook') 
          && !memberBooksFetchedRef.current && currentMember) {
        fetchMemberBooks();
      }
    }, [memberAction, books.length, currentMember?.id]);

    const fetchMemberBooks = async () => {
      if (isFetchingMemberBooksRef.current) return; // prevent concurrent fetches
      isFetchingMemberBooksRef.current = true;
      setIsLoadingMemberBooks(true);
      setError('');
      try {
        const response = await fetch(`${API_BASE_URL}/members/${currentMember.id}/books`);
        const status = await response.json();
        if (!response.ok) throw new Error(`ERROR ${status.code}:  ${status.message}`||'Failed to fetch your books');
        setMemberBooks(status.data);
        memberBooksFetchedRef.current = true; // Mark as fetched after successful load
      } catch (err) {
        setError(err.message);
      } finally {
        setIsLoadingMemberBooks(false);
        isFetchingMemberBooksRef.current = false;
      }
    };

    if (!memberAction) {
      return (
        <div className="min-h-screen bg-gradient-to-br from-green-50 to-emerald-100 p-4">
          <div className="max-w-4xl mx-auto">
            <button
              onClick={() => { setView('home'); setCurrentMember(null); setMemberId(''); }}
              className="mb-6 text-gray-600 hover:text-gray-800 flex items-center gap-2"
            >
              <ArrowLeft className="w-4 h-4" />
              Logout
            </button>
            <div className="bg-white rounded-2xl shadow-2xl p-8">
              <div className="mb-8 text-center">
                <h2 className="text-3xl font-bold text-gray-800">Welcome, {currentMember.name}!</h2>
                <p className="text-gray-600">Member ID: {currentMember.id}</p>
                
                <p className="text-gray-600">Books Currently Owned: {memberBooks.reduce((total, item) => total + item.quantity, 0)}</p>
                
              </div>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <button onClick={() => setMemberAction('viewAllBooks')} className="bg-blue-500 hover:bg-blue-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <Eye className="w-5 h-5" /> VIEW ALL BOOKS
                </button>
                <button onClick={() => setMemberAction('viewMyBooks')} className="bg-purple-500 hover:bg-purple-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <BookOpen className="w-5 h-5" /> VIEW MY BOOKS
                </button>
                <button onClick={() => setMemberAction('issueBook')} className="bg-green-500 hover:bg-green-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <Plus className="w-5 h-5" /> ISSUE BOOK
                </button>
                <button onClick={() => setMemberAction('returnBook')} className="bg-orange-500 hover:bg-orange-600 text-white p-6 rounded-lg font-semibold flex items-center justify-center gap-2">
                  <ArrowLeft className="w-5 h-5" /> RETURN BOOK
                </button>
              </div>
            </div>
          </div>
        </div>
      );
    }

    return (
      <div className="min-h-screen bg-gradient-to-br from-green-50 to-emerald-100 p-4">
        <div className="max-w-6xl mx-auto">
          <button
            onClick={() => { setMemberAction(null); setError(''); setSuccess(''); }}
            className="mb-6 text-gray-600 hover:text-gray-800 flex items-center gap-2"
          >
            <ArrowLeft className="w-4 h-4" />
            Back to Member Dashboard
          </button>
          <div className="bg-white rounded-2xl shadow-2xl p-8">
            {error && (
              <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                {error}
              </div>
            )}
            {success && (
              <div className="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
                {success}
              </div>
            )}

            {memberAction === 'viewAllBooks' && (
              <div>
                <h3 className="text-2xl font-bold mb-6">All Available Books</h3>
                <button onClick={fetchBooks} className="mb-4 bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600">
                  Refresh
                </button>
                {loading ? (
                  <p>Loading...</p>
                ) : books.length === 0 ? (
                  <p className="text-gray-600">No books available</p>
                ) : (
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="bg-gray-100">
                          <th className="border p-3 text-left">ID</th>
                          <th className="border p-3 text-left">Title</th>
                          <th className="border p-3 text-left">Author</th>
                          <th className="border p-3 text-left">Available Quantity</th>
                        </tr>
                      </thead>
                      <tbody>
                        {books.map(book => (
                          <tr key={book.id} className="hover:bg-gray-50">
                            <td className="border p-3">{book.id}</td>
                            <td className="border p-3">{book.title}</td>
                            <td className="border p-3">{book.author}</td>
                            <td className="border p-3">
                              <span className={`px-2 py-1 rounded ${book.quantity > 0 ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
                                {book.quantity}
                              </span>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            )}

            {memberAction === 'viewMyBooks' && (
              <div>
                <h3 className="text-2xl font-bold mb-6">My Books</h3>
                <button 
                  onClick={fetchMemberBooks} 
                  disabled={isLoadingMemberBooks}
                  className="mb-4 bg-purple-500 text-white px-4 py-2 rounded-lg hover:bg-purple-600 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {isLoadingMemberBooks ? 'Refreshing...' : 'Refresh'}
                </button>
                {isLoadingMemberBooks ? (
                  <p>Loading...</p>
                ) : memberBooks.length === 0 ? (
                  <p className="text-gray-600">You haven't borrowed any books yet</p>
                ) : (
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>                                                                   
                        <tr className="bg-gray-100">
                          <th className="border p-3 text-left">Book ID</th>
                          <th className="border p-3 text-left">Title</th>
                          <th className="border p-3 text-left">Author</th>
                          <th className="border p-3 text-left">Quantity</th>
                          
                        </tr>
                      </thead>
                      <tbody>
                        {memberBooks.map((item,index) => (
                          <tr key={index} className="hover:bg-gray-50">
                            <td className="border p-3">{item.book.id}</td>
                            <td className="border p-3">{item.book.title}</td>
                            <td className="border p-3">{item.book.author}</td>
                            <td className="border p-3">{item.quantity}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            )}

            {memberAction === 'issueBook' && (
              <div>
                <h3 className="text-2xl font-bold mb-6">Issue a Book</h3>
                <button onClick={fetchBooks} className="mb-4 bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600">
                  Refresh Books
                </button>
                {loading ? (
                  <p>Loading...</p>
                ) : books.length === 0 ? (
                  <p className="text-gray-600">No books available</p>
                ) : (
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="bg-gray-100">
                          <th className="border p-3 text-left">ID</th>
                          <th className="border p-3 text-left">Title</th>
                          <th className="border p-3 text-left">Author</th>
                          <th className="border p-3 text-left">Available</th>
                          <th className="border p-3 text-left">Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        {books.map(book => (
                          <tr key={book.id} className="hover:bg-gray-50">
                            <td className="border p-3">{book.id}</td>
                            <td className="border p-3">{book.title}</td>
                            <td className="border p-3">{book.author}</td>
                            <td className="border p-3">{book.quantity}</td>
                            <td className="border p-3">
                              <button
                                onClick={() => issueBook(book.id)}
                                disabled={book.quantity === 0}
                                className="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded disabled:opacity-50 disabled:cursor-not-allowed"
                              >
                                Issue
                              </button>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            )}

            {memberAction === 'returnBook' && (
              <div>
                <h3 className="text-2xl font-bold mb-6">Return a Book</h3>
                <button 
                  onClick={fetchMemberBooks} 
                  disabled={isLoadingMemberBooks}
                  className="mb-4 bg-orange-500 text-white px-4 py-2 rounded-lg hover:bg-orange-600 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {isLoadingMemberBooks ? 'Refreshing...' : 'Refresh My Books'}
                </button>
                {isLoadingMemberBooks ? (
                  <p>Loading...</p>
                ) : memberBooks.length === 0 ? (
                  <p className="text-gray-600">You don't have any books to return</p>
                ) : (
                  <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                      <thead>
                        <tr className="bg-gray-100">
                          <th className="border p-3 text-left">Book ID</th>
                          <th className="border p-3 text-left">Title</th>
                          <th className="border p-3 text-left">Author</th>
                          <th className="border p-3 text-left">Quantity</th>
                          <th className="border p-3 text-left">Action</th>
                        </tr>
                      </thead>
                      <tbody>
                        {memberBooks.map((item,index) => (
                          <tr key={index} className="hover:bg-gray-50">
                            <td className="border p-3">{item.book.id}</td>
                            <td className="border p-3">{item.book.title}</td>
                            <td className="border p-3">{item.book.author}</td>
                            <td className="border p-3">{item.quantity}</td>
                            <td className="border p-3">
                              <button
                                onClick={() => returnBook(item.book.id)}
                                className="bg-orange-500 hover:bg-orange-600 text-white px-4 py-2 rounded"
                              >
                                Return
                              </button>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              </div>
            )}
          </div>
        </div>
      </div>
    );
  };

  // Main render
  return (
    <div>
      {view === 'home' && <HomeView />}
      {view === 'memberLogin' && <MemberLoginView />}
      {view === 'staff' && <StaffView />}
      {view === 'member' && <MemberView />}
    </div>
  );
};

export default App;