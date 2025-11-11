import React, { Component } from 'react';
import axios from 'axios';
import swal from 'sweetalert2';
import { withRouter } from 'react-router-dom';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
    };
  }

  componentDidMount() {
    // ✅ Clear any old localStorage/session when user reaches login page
    localStorage.clear();

    // Optionally, check if already logged in via backend session
    axios
      .get('http://localhost:8080/isLoggedIn', { withCredentials: true })
      .then((res) => {
        if (res.data && res.data.successMsg) {
          swal.fire({
            icon: 'info',
            title: 'Already Logged In',
            text: 'Redirecting to dashboard...',
          });
          this.props.history.push('/dashboard');
        }
      })
      .catch((err) => console.error('Login check failed:', err));
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  handleSubmit = (e) => {
    e.preventDefault();

    const { email, password } = this.state;
    if (!email || !password) {
      swal.fire({
        icon: 'warning',
        title: 'Missing Fields',
        text: 'Please enter both email and password.',
      });
      return;
    }

    const loginPayload = { email, password };
    axios.post(
        'http://localhost:8080/login',
        JSON.stringify(loginPayload),
        {
            headers: { 'Content-Type': 'application/json' },
            withCredentials: true
        }
        )
      .then((res) => {
        if (res.data.errorMsg === '') {
          const user = res.data.data;

          // ✅ Save minimal details
          localStorage.setItem('id', user.id);
          localStorage.setItem('name', user.name);
          localStorage.setItem('email', user.email);

          swal
            .fire({
              icon: 'success',
              title: 'Login Successful',
              text: `Welcome, ${user.name}!`,
              timer: 1200,
              showConfirmButton: false,
            })
            .then(() => {
              this.props.history.push('/home');
            });
        } else {
          swal.fire({
            icon: 'error',
            title: 'Login Failed',
            text: res.data.errorMsg || 'Invalid credentials',
          });
        }
      })
      .catch((err) => {
        console.error('Login error:', err);
        swal.fire({
          icon: 'error',
          title: 'Server Error',
          text: 'Unable to login right now. Please try again later.',
        });
      });
  };

  render() {
    return (
      <div className="container mt-5">
        <h2 className="text-center mb-4">Login</h2>
        <form onSubmit={this.handleSubmit} className="col-md-6 mx-auto">
          <div className="form-group mb-3">
            <label>Email</label>
            <input
              type="email"
              name="email"
              className="form-control"
              value={this.state.email}
              onChange={this.handleChange}
              placeholder="Enter your email"
              required
            />
          </div>
          <div className="form-group mb-4">
            <label>Password</label>
            <input
              type="password"
              name="password"
              className="form-control"
              value={this.state.password}
              onChange={this.handleChange}
              placeholder="Enter your password"
              required
            />
          </div>
          <button type="submit" className="btn btn-primary w-100">
            Login
          </button>
        </form>
      </div>
    );
  }
}

export default withRouter(Login);
