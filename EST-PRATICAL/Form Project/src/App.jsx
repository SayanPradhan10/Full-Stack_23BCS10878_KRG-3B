import React, { useState } from 'react';

function PostForm() {
  const [title, setTitle] = useState('');
  const [body, setBody] = useState('');
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!title.trim() || !body.trim()) {
      setError('Please fill in both title and body');
      return;
    }

    setLoading(true);
    setSuccess(false);
    setError('');

    try {

      await new Promise(resolve => setTimeout(resolve, 1200));

      console.log('Submitted:', { title, body });

      setSuccess(true);
      setTitle('');
      setBody('');
    } catch (err) {
      setError('Failed to submit');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: '500px', margin: '2rem auto', padding: '2rem', fontFamily: 'Arial' }}>
      <h2>Create Post</h2>

      {success && (
        <div style={{ padding: '12px', background: '#d4edda', color: '#155724', borderRadius: '6px', marginBottom: '1rem' }}>
          Post submitted successfully!
        </div>
      )}

      {error && (
        <div style={{ padding: '12px', background: '#f8d7da', color: '#721c24', borderRadius: '6px', marginBottom: '1rem' }}>
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '1rem' }}>
          <label><strong>Title</strong></label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
            style={{ width: '100%', padding: '10px', marginTop: '5px', borderRadius: '4px', border: '1px solid #ccc' }}
            placeholder="Enter title"
          />
        </div>

        <div style={{ marginBottom: '1rem' }}>
          <label><strong>Body</strong></label>
          <textarea
            rows="6"
            value={body}
            onChange={(e) => setBody(e.target.value)}
            required
            style={{ width: '100%', padding: '10px', marginTop: '5px', borderRadius: '4px', border: '1px solid #ccc' }}
            placeholder="Write your post..."
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          style={{
            padding: '12px 24px',
            background: loading ? '#999' : '#28a745',
            color: 'white',
            border: 'none',
            borderRadius: '6px',
            fontSize: '16px',
            cursor: loading ? 'not-allowed' : 'pointer'
          }}
        >
          {loading ? 'Submitting...' : 'Submit Post'}
        </button>
      </form>
    </div>
  );
}

export default PostForm;
